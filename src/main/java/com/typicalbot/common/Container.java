/**
 * TypicalBot - A multipurpose discord bot
 * Copyright (C) 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
