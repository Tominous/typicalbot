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

@CommandConfiguration(category = CommandCategory.FUN, aliases = {"bunny", "rabbit"})
public class BunnyCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(BunnyCommand.class);

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        String type = Math.random() <= 0.25 ? "gif" : "poster";

        OkHttpClient http = new OkHttpClient();
        Request request = new Request.Builder().url(String.format("https://api.bunnies.io/v2/loop/random/?media=%s", type)).build();

        try {
            Response response = http.newCall(request).execute();

            context.sendMessage(new JSONObject(response.body().string()).getJSONObject("media").get(type).toString());
        } catch (IOException ex) {
            LOGGER.error("Bunny API has either been updated or is down for maintenance.");
            context.sendMessage("Failed to make a request.");
        }
    }
}
