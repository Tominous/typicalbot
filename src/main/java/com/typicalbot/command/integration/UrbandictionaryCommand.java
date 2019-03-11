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
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

@CommandConfiguration(category = CommandCategory.INTEGRATION, aliases = "urbandictionary")
public class UrbandictionaryCommand implements Command {
    @Override
    public boolean nsfw() {
        return true;
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        String url;

        if (argument.has()) {
            url = "http://api.urbandictionary.com/v0/define?term=" + argument.toString();
        } else {
            url = "http://api.urbandictionary.com/v0/random";
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            JSONArray list = new JSONObject(new JSONTokener(response.body().byteStream())).getJSONArray("list");
            JSONObject term = list.getJSONObject(0);

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(term.getString("word"), term.getString("permalink"));
            builder.setDescription(term.getString("definition").replaceAll("\\[", "").replaceAll("]", ""));
            builder.setColor(CommandContext.TYPICALBOT_SUCCESS);
            builder.addField("Example", term.getString("example").replaceAll("\\[", "").replaceAll("]", ""), false);
            builder.addField("Rating", ":arrow_up: `" + term.getInt("thumbs_up") + "` :arrow_down: `" + term.getInt("thumbs_down") + "`", false);

            context.sendEmbed(builder.build());
        } catch (IOException ex) {
            context.sendMessage("Failed to retrieve result from Urban Dictionary.");
        }
    }
}
