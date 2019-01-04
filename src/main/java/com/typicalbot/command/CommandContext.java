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
package com.typicalbot.command;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class CommandContext {
    private final Message message;

    public CommandContext(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }

    public void sendMessage(String message, Object... params) {
        this.message.getChannel().sendMessage(String.format(message, params)).queue();
    }

    public void sendEmbed(MessageEmbed embed) {
        this.message.getChannel().sendMessage(embed).queue();
    }
}
