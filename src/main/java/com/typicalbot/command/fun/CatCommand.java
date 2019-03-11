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
package com.typicalbot.command.fun;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@CommandConfiguration(category = CommandCategory.FUN, aliases = {"cat", "kitty", "kitten"})
public class CatCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatCommand.class);

    @Override
    public String[] usage() {
        return new String[]{
            "cat"
        };
    }

    @Override
    public String description() {
        return "Gives you a random cat picture.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        OkHttpClient http = new OkHttpClient();
        Request request = new Request.Builder().url("https://aws.random.cat/meow").build();

        try {
            Response response = http.newCall(request).execute();

            context.sendMessage(new JSONObject(response.body().string()).get("file").toString());
        } catch (IOException ex) {
            LOGGER.error("Cat API has either been updated or is down for maintenance.");
            context.sendMessage("Failed to make a request.");
        }
    }
}
