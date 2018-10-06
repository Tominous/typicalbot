package com.typicalbot.common.facade;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ContainerFacade {
    private final Map<Class<?>, Object> container;

    public ContainerFacade() {
        this.container = new HashMap<>();
    }

    public <T> T get(Class<T> clazz) {
        if (!this.container.containsKey(clazz)) {
            throw new IllegalArgumentException("No mapping was found for class " + clazz.getName());
        }

        Object obj = this.container.get(clazz);

        if (obj instanceof Supplier) {
            return cast(((Supplier) obj).get());
        } else {
            return cast(obj);
        }
    }

    public <T> Optional<T> optional(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Clazz is null");
        }

        if (!this.container.containsKey(clazz)) {
            return Optional.empty();
        }

        Object obj = this.container.get(clazz);

        if (obj instanceof Supplier) {
            return Optional.ofNullable(cast(((Supplier) obj).get()));
        } else {
            return Optional.ofNullable(cast(obj));
        }
    }

    public <T> void put(Class<T> clazz, T instance) {
        this.container.put(clazz, instance);
    }

    public <T> void put(Class<T> clazz, Supplier<T> factory) {
        this.container.put(clazz, factory);
    }

    public Collection<Object> getAll() {
        return Collections.unmodifiableCollection(this.container.values());
    }

    private <T> T cast(Object obj) {
        return (T) obj;
    }

    public void clear() {
        this.container.clear();
    }
}
