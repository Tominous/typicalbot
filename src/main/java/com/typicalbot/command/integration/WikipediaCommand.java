/*
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
package com.typicalbot.command.integration;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

@CommandConfiguration(category = CommandCategory.INTEGRATION, aliases = "wikipedia")
public class WikipediaCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("Incorrect usage.");
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://wikipedia.org/w/api.php?format=json&action=query&prop=extracts&redirects=1&exintro=&explaintext=&titles=" + argument.toString()).build();

        try {
            Response response = client.newCall(request).execute();

            // Why wikipedia...
            JSONObject object = new JSONObject(new JSONTokener(response.body().byteStream()));
            JSONObject pages = object.getJSONObject("query").getJSONObject("pages");
            String pageNumber = pages.keySet().iterator().next();

            if (pageNumber.equals("-1")) {
                context.sendMessage("Failed to retrieve result from Wikipedia.");
                return;
            }

            JSONObject page = pages.getJSONObject(pageNumber);

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(page.getString("title"));
            builder.setDescription((page.getString("extract").length() > 2048 ? page.getString("extract").substring(0, 2000) + "..." : page.getString("extract")));

            context.sendEmbed(builder.build());
        } catch (IOException ex) {
            context.sendMessage("Failed to retrieve results from Wikipedia.");
        }
    }
}
