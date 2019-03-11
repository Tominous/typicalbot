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
package com.typicalbot.command;

public interface Command {
    /**
     * The usage of the command, if none provided, it will say: "No usage available."
     *
     *
     * @return command usage
     */
    default String[] usage() {
        return new String[]{"No usage available."};
    }

    /**
     * The description of the command, if none provided, it will say: "No description available."
     *
     * @return command description
     */
    default String description() {
        return "No description available.";
    }

    /**
     * Determines whether or not if the command requires the channel to be in NSFW mode.
     *
     * @return command nsfw mode
     */
    default boolean nsfw() { return false; }

    /**
     * The permission required to use the command.
     *
     * @return command permission
     */
    CommandPermission permission();

    /**
     * The execution of the command.
     *
     * @param context the command context
     * @param argument the command arguments
     */
    void execute(CommandContext context, CommandArgument argument);

    /**
     * The configuration of the command.
     *
     * @return command configuration
     */
    default CommandConfiguration getConfiguration() {
        return this.getClass().getAnnotation(CommandConfiguration.class);
    }
}
