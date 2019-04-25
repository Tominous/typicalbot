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
package com.typicalbot.command.utility;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.util.Pageable;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = "bots")
public class BotsCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.carbonitex.net/discord/api/listedbots").build();

        try {
            Response response = client.newCall(request).execute();

            // TODO(nsylke): This can be simplified.
            JSONArray raw = new JSONArray(response.body().string());
            List<JSONObject> servers = new ArrayList<>();

            for (int i = 0; i < raw.length(); i++) {
                servers.add(raw.getJSONObject(i));
            }

            // Sorts based on server count, then reverses the list so it's highest to lowest.
            servers.sort(Comparator.comparingInt(o -> o.getInt("servercount")));
            Collections.reverse(servers);

            Pageable<JSONObject> obj = new Pageable<>(servers);

            if (argument.has()) {
                obj.setPage(Integer.parseInt(argument.get(0)));
            } else {
                obj.setPage(1);
            }

            EmbedBuilder builder = new EmbedBuilder();
            DecimalFormat format = new DecimalFormat("#,###");

            builder.setTitle("Bot List - Provided by Carbonitex");
            builder.setColor(CommandContext.TYPICALBOT_BLUE);
            builder.setThumbnail("https://cdn.discordapp.com/icons/112319935652298752/8ab710cbc981f639bf355911be48adf7.png"); // Carbonitex server logo

            for (JSONObject server : obj.getListForPage()) {
                builder.addField(server.getString("name").replaceAll("[^A-Za-z0-9]", "") + (server.getInt("compliant") == 1 ? " (Compliant)" : ""), format.format(server.getInt("servercount")) + " Guilds", true);
            }

            builder.setFooter("Page " + obj.getPage() + " / " + obj.getMaxPages(), null);

            context.sendEmbed(builder.build());
        } catch (IOException ex) {
            context.sendMessage("Unable to retrieve bot list from Carbonitex.");
        }
    }
}
