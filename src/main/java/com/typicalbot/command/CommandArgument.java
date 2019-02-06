/**
 * Copyright 2019 Bryan Pikaard & Nicholas Sylke
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
package com.typicalbot.command;

import java.util.List;

/**
 * @author TypicalBot
 * @since 3.0.0-alpha
 */
public class CommandArgument {
    private final List<String> arguments;

    public CommandArgument(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean has() {
        return this.arguments.size() > 0;
    }

    public String get(int index) {
        return this.arguments.get(index);
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return String.join(" ", this.arguments);
    }
}
