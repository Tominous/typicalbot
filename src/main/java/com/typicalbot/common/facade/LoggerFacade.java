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
package com.typicalbot.common.facade;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class LoggerFacade implements Logger {
    private final Logger logger;

    public LoggerFacade(Logger logger) {
        this.logger = logger;
    }

    private String parse(String item) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < item.length(); i++) {
            char c = item.charAt(i);

            builder.append(c);
        }

        return builder.toString();
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        logger.trace(parse(s));
    }

    @Override
    public void trace(String s, Object o) {
        logger.trace(parse(s), o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        logger.trace(parse(s), o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        logger.trace(parse(s), objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        logger.trace(parse(s), throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String s) {
        logger.trace(marker, parse(s));
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        logger.trace(marker, parse(s));
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        logger.trace(marker, parse(s), o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        logger.trace(marker, parse(s), objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        logger.trace(marker, parse(s), throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        logger.debug(parse(s));
    }

    @Override
    public void debug(String s, Object o) {
        logger.debug(parse(s), o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        logger.debug(parse(s), o);
    }

    @Override
    public void debug(String s, Object... objects) {
        logger.debug(parse(s), objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(parse(s), throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        logger.debug(marker, parse(s));
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        logger.debug(marker, parse(s), o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        logger.debug(marker, parse(s), o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        logger.debug(marker, parse(s), objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        logger.debug(marker, parse(s), throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        logger.info(parse(s));
    }

    @Override
    public void info(String s, Object o) {
        logger.info(parse(s), o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        logger.info(parse(s), o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        logger.info(parse(s), objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.info(parse(s), throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        logger.info(marker, parse(s));
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        logger.info(marker, parse(s), o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        logger.info(marker, parse(s), o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        logger.info(marker, parse(s), objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        logger.info(marker, parse(s), throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        logger.warn(parse(s));
    }

    @Override
    public void warn(String s, Object o) {
        logger.warn(parse(s), o);
    }

    @Override
    public void warn(String s, Object... objects) {
        logger.warn(parse(s), objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        logger.warn(parse(s), o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(parse(s), throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String s) {
        logger.warn(marker, parse(s));
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        logger.warn(marker, parse(s), o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        logger.warn(marker, parse(s), o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        logger.warn(marker, parse(s), objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        logger.warn(marker, parse(s), throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        logger.error(parse(s));
    }

    @Override
    public void error(String s, Object o) {
        logger.error(parse(s), o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        logger.error(parse(s), o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        logger.error(parse(s), objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(parse(s), throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        logger.error(marker, parse(s));
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        logger.error(marker, parse(s), o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        logger.error(marker, parse(s), o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        logger.error(marker, parse(s), objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        logger.error(marker, parse(s), throwable);
    }
}
