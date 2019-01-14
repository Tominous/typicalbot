/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    /**
     * Copy a resource from the jar into the system.
     *
     * @param dest The destination of the copy
     * @param resource The path to the resource
     */
    public static void copy(Path dest, String resource) {
        InputStream stream = FileUtil.class.getResourceAsStream(resource);
        try {
            Files.copy(stream, dest);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
