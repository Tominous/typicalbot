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
package com.typicalbot.common;

import com.typicalbot.common.facade.ContainerFacade;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class Container {
    private static ContainerFacade container;

    public static <T> T get(Class<T> clazz) {
        return container.get(clazz);
    }

    public static <T> Optional<T> optional(Class<T> clazz) {
        return container.optional(clazz);
    }

    public static <T> void put(Class<T> clazz, T instance) {
        container.put(clazz, instance);
    }

    public static <T> void put(Class<T> clazz, Supplier<T> factory) {
        container.put(clazz, factory);
    }

    public static Collection<Object> getAll() {
        return container.getAll();
    }

    public static void swap(ContainerFacade instance) {
        container = instance;
    }
}
