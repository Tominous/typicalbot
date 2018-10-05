package com.typicalbot.common.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows to add a single or several command alias(es) with the following
 * syntax "one|two|three". You can register as many aliases as wanted.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CommandAlias {
    /**
     * Available alias(es) for a command.
     *
     * @return the alias(es)
     */
    String value();
}
