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
package com.typicalbot.nxt.command.integration;

import com.typicalbot.command.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

@CommandConfiguration(category = CommandCategory.INTEGRATION, aliases = "strawpoll")
public class StrawpollCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "strawpoll [question] | [choice]; [choice]; [choice]",
            "strawpoll -m [question] | [choice]; [choice]; [choice]"
        };
    }

    @Override
    public String description() {
        return "Create a strawpoll vote.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        // TODO(nsylke): Redo this after release... it's just bad.
        CommandCheck.checkArguments(argument);

        boolean multi = false;

        if (argument.get(0).equals("-m")) {
            multi = true;
            argument.getArguments().remove(0);
        }

        String[] args = argument.toString().split("\\|");

        JSONObject object = new JSONObject();
        object.put("title", args[0]);
        object.put("options", args[1].split(";"));
        object.put("multi", multi);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.strawpoll.me/api/v2/polls").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString())).build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject poll = new JSONObject(new JSONTokener(response.body().byteStream()));

            context.sendMessage("Your Strawpoll has been successfully created. <https://strawpoll.me/{0}>", Integer.toString(poll.getInt("id")));
        } catch (IOException ex) {
            context.sendMessage("Failed to create poll on Strawpoll.");
        }
    }
}
