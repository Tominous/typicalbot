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

package com.typicalbot.util;

import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;

public class SentryUtil {
    public static void capture(Exception e, Class clazz) {
        EventBuilder builder = new EventBuilder()
            .withMessage(e.getMessage())
            .withLevel(Event.Level.ERROR)
            .withLogger(clazz.getName())
            .withSentryInterface(new ExceptionInterface(e));

        Sentry.capture(builder);
    }
}
