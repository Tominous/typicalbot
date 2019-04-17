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
package com.typicalbot.command.core;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@CommandConfiguration(category = CommandCategory.CORE, aliases = "changelog")
public class ChangelogCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "changelog"
        };
    }

    @Override
    public String description() {
        return "See the changelog for the current version of TypicalBot.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        OkHttpClient client = new OkHttpClient();

        // https://developer.github.com/v3/repos/releases/#get-the-latest-release
        Request request = new Request.Builder().url("https://api.github.com/repos/typicalbot/typicalbot/releases/latest").build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject object = new JSONObject(response.body().string());

            if (context.getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
                EmbedBuilder builder = new EmbedBuilder();

                builder.setTitle("TypicalBot Changelog", object.getString("html_url"));
                builder.setDescription(object.getString("body"));
                builder.setColor(CommandContext.TYPICALBOT_BLUE);

                context.sendEmbed(builder.build());
            } else {
                context.sendMessage("TypicalBot Changelog - {0}\n\n{1}", object.getString("html_url"), object.getString("body"));
            }
        } catch (IOException | JSONException ex) {
            context.sendMessage("Unable to retrieve changelog from GitHub.");
        }
    }
}
