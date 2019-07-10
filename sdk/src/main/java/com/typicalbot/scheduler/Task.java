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
package com.typicalbot.scheduler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface Task {
    UUID getUniqueId();

    long getDelay();

    long getInterval();

    Object getExtension();

    Consumer<Task> getConsumer();

    interface Builder {
        default Builder execute(Runnable runnable) {
            return this.execute(task -> runnable.run());
        }

        Builder execute(Consumer<Task> executor);

        Builder delay(long delay, TimeUnit unit);

        Builder interval(long interval, TimeUnit unit);

        Task submit(Object extension);
    }
}
