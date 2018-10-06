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
