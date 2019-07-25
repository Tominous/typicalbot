/*
 * Copyright 2019 Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.extension;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.typicalbot.command.Command;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtensionLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);
    private static final ClassPool EXTENSION_CP = new ClassPool(true);

    private final Map<String, Extension> loaded = Maps.newConcurrentMap();
    private final List<Command> commandMap = Lists.newArrayList();

    public void loadAll() {
        this.loadAll(Sets.newHashSet());
    }

    public void loadAll(Set<Path> skip) {
        try {
            Files.walkFileTree(Extension.EXTENSION_DIR, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (skip.contains(file)) {
                        return FileVisitResult.CONTINUE;
                    }

                    ExtensionLoader.this.load(file, skip);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new ExtensionException(e);
        }
    }

    public Extension load(Path path) {
        return this.load(path, Sets.newHashSet());
    }

    private Extension load(Path path, Set<Path> skip) {
        skip.add(path);

        File extensionFile = path.toFile();

        Set<String> names = Sets.newHashSet();
        try (JarFile jarFile = new JarFile(extensionFile)) {
            EXTENSION_CP.appendClassPath(extensionFile.getPath());

            ExtensionManifest manifest = null;
            String main = null;

            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = entries.nextElement();

                if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                    continue;
                }

                String name = entry.getName().replace(".class", "").replace('/', '.');
                names.add(name);

                CtClass cls = EXTENSION_CP.get(name);
                ExtensionManifest mani = (ExtensionManifest) cls.getAnnotation(ExtensionManifest.class);

                if (mani != null && (mani.id().equals("typicalbot") || mani.id().equals("discord"))) {
                    throw new ExtensionException("ID has illegal name");
                }

                if (mani != null && cls.getSuperclass().getName().equals(Extension.class.getName())) {
                    if (manifest != null) {
                        throw new ExtensionException("Extension cannot have more than two extension main classes");
                    }

                    main = name;
                    manifest = mani;
                }
            }

            if (main == null) {
                throw new ExtensionException("Extension does not have a main class");
            }

            if (this.loaded.containsKey(manifest.id())) {
                throw new ExtensionException("Extension with ID \"" + manifest.name() + "\" has already been loaded");
            }

            LOGGER.info("Loading {} v{}", manifest.name(), manifest.version());

            ExtensionClassLoader loader = new ExtensionClassLoader(extensionFile);
            Class<? extends Extension> extensionClass = null;

            for (String name : names) {
                Class<?> cls = loader.loadClass(name);
                if (name.equals(main)) {
                    extensionClass = cls.asSubclass(Extension.class);
                }
            }

            Extension extension = extensionClass.getConstructor().newInstance();
            extension.init(path, manifest, loader);

            this.loaded.put(manifest.id(), extension);
            extension.onLoad();
            extension.onEnable();

            this.commandMap.addAll(extension.getCommands());

            LOGGER.info("Loaded {} v{}", manifest.name(), manifest.version());

            return extension;
        } catch (IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException | NotFoundException e) {
            throw new ExtensionException(e);
        }
    }

    public boolean unload(String extension) {
        Extension e = this.loaded.remove(extension);
        if (e == null) {
            return false;
        }

        ExtensionManifest manifest = e.getManifest();
        LOGGER.info("Unloading {} v{}", manifest.name(), manifest.version());

        e.onDisable();
        e.release();
        return true;
    }

    public boolean unloadAll() {
        for (Extension extension : this.loaded.values()) {
            extension.onDisable();
            extension.release();
        }

        this.loaded.clear();
        return true;
    }

    public void reload() {
        if (this.unloadAll()) {
            this.loadAll();
            for (Extension extension : this.loaded.values()) {
                extension.onEnable();
            }
        } else {
            LOGGER.error("Unloading extensions failed");
        }
    }

    public Map<String, Extension> getLoaded() {
        return this.loaded;
    }

    public List<Command> getCommandMap() {
        return this.commandMap;
    }
}
