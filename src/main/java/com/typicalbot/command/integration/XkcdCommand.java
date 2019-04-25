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
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Random;

@CommandConfiguration(category = CommandCategory.INTEGRATION, aliases = "xkcd")
public class XkcdCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "xkcd",
            "xkcd [comic]"
        };
    }

    @Override
    public String description() {
        return "Find a comic on XKCD.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        int num;

        if (argument.has()) {
            int temp = Integer.parseInt(argument.get(0));

            if (temp > 2115 || temp < 1) {
                context.sendMessage("Invalid comic number.");
                return;
            }

            num = temp;
        } else {
            num = new Random().nextInt(2115);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://xkcd.com/" + num + "/info.0.json").build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject object = new JSONObject(new JSONTokener(response.body().byteStream()));

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(object.getString("title"));
            builder.setDescription(object.getString("alt"));
            builder.setImage(object.getString("img"));
            builder.setColor(CommandContext.TYPICALBOT_SUCCESS);

            context.sendEmbed(builder.build());
        } catch (IOException ex) {
            context.sendMessage("Unable to retrieve XKCD comic.");
        }
    }
}
