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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;

public class ExtensionClassLoader extends URLClassLoader {
    private static final Map<String, Class<?>> CLASS_MAP = Maps.newConcurrentMap();

    private final Set<Class<?>> classes = Sets.newConcurrentHashSet();

    public ExtensionClassLoader(File extension) throws MalformedURLException {
        super(new URL[]{extension.toURI().toURL()}, Extension.class.getClassLoader());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> cls = CLASS_MAP.get(name);

        if (cls != null) {
            return cls;
        }

        cls = super.findClass(name);
        this.classes.add(cls);
        CLASS_MAP.put(name, cls);

        return cls;
    }

    @Override
    public void close() throws IOException {
        super.close();

        for (Class<?> cls : this.classes) {
            if (CLASS_MAP.remove(cls.getName()) == null) {
                throw new ExtensionException("Failed to cleanup after class.");
            }
        }

        this.classes.clear();
    }

    public Set<Class<?>> getClasses() {
        return this.classes;
    }
}
