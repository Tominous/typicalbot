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
package com.typicalbot.command.moderation;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "channels")
public class ChannelsCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "channels create [text/voice/category] [name]",
            "channels delete [name]",
            "channels clone [channel] [name]",
            "channels edit [channel] [property] [value]"
        };
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        /*
         * channels create text <name>
         * channels create voice <name>
         * channels create category <name>
         *
         * channels create -t <name>
         * channels create -v <name>
         * channels create -c <name>
         *
         * channels delete <channel>
         *
         * channels clone <channel> <name>
         *
         * channels edit <channel> <prop> <value>  ->  channels edit #general name #lounge
         *
         * Properties:
         *  - name
         *  - topic
         *  - slowmode (or use $slowmode)
         *  - nsfw
         *  - bitrate
         *  - userlimit
         *
         *
         * For advanced users
         *
         * channels permissions #general everyone +read_messages -send_messages
         * channels permissions #general sync
         *
         * + Allow
         * / Default
         * - Disallow
         *
         * sync Sync permissions with category
         */
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MANAGE_CHANNEL);
        CommandCheck.checkArguments(argument);

        if (argument.get(0).equalsIgnoreCase("create")) {
            if (argument.length() < 3) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            String name = argument.get(2);

            switch (argument.get(1)) {
                case "text":
                case "-t":
                    context.getGuild().getController().createTextChannel(name).queue(o -> {
                        context.sendMessage("Successfully created a text channel: {0}.", name);
                    });
                    break;
                case "voice":
                case "-v":
                    context.getGuild().getController().createVoiceChannel(name).queue(o -> {
                        context.sendMessage("Successfully created a voice channel: {0}.", name);
                    });
                    break;
                case "category":
                case "-c":
                    context.getGuild().getController().createCategory(name).queue(o -> {
                        context.sendMessage("Successfully created a category: {0}.", name);
                    });
                    break;
            }
        } else if (argument.get(0).equalsIgnoreCase("delete")) {
            if (argument.length() < 2) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            String name = argument.get(1);

            context.getChannel(name).delete().queue(o -> {
                context.sendMessage("Successfully deleted {0} channel.", name);
            });
        } else if (argument.get(0).equalsIgnoreCase("clone")) {
            if (argument.length() < 3) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            Channel channel = context.getChannel(argument.get(1));

            if (channel == null) {
                context.sendMessage("Channel doesn't exist.");
                return;
            }

            String name = argument.get(2);

            context.getGuild().getController().createCopyOfChannel(channel).setName(name).queue(o -> {
                context.sendMessage("Successfully cloned channel {0} to {1}.", channel.getName(), name);
            });
        } else if (argument.get(0).equalsIgnoreCase("edit")) {
            if (argument.length() < 4) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            String name = argument.get(1);
            String property = argument.get(2);

            Channel channel = context.getChannel(name);

            if (channel == null) {
                context.sendMessage("Channel doesn't exist.");
                return;
            }


            switch (property) {
                case "name":
                    String cname = argument.get(3);
                    channel.getManager().setName(cname).queue(o -> {
                        context.sendMessage("Successfully updated name to {0} for {1}.", cname, channel.getName());
                    });

                    break;
                case "topic":
                    if (!(channel instanceof TextChannel)) {
                        context.sendMessage("Channel is not a text channel.");
                        return;
                    }

                    String topic = String.join(" ", argument.getArguments().subList(3, argument.length()));
                    channel.getManager().setTopic(topic).queue(o -> {
                        context.sendMessage("Successfully updated topic to {0} for {1}.", topic, channel.getName());
                    });

                    break;
                case "slowmode":
                    if (!(channel instanceof TextChannel)) {
                        context.sendMessage("Channel is not a text channel.");
                        return;
                    }


                    try {
                        int slowmode = Integer.parseInt(argument.get(3));

                        channel.getManager().setSlowmode(slowmode).queue(o -> {
                            context.sendMessage("Successfully updated slowmode to {0} for {1}.", slowmode, channel.getName());
                        });
                    } catch (NumberFormatException ex) {
                        context.sendMessage("That is not a number.");
                    }

                    break;
                case "nsfw":
                    if (!(channel instanceof TextChannel)) {
                        context.sendMessage("Channel is not a text channel.");
                        return;
                    }

                    boolean nsfw = Boolean.parseBoolean(argument.get(3));
                    channel.getManager().setNSFW(nsfw).queue(o -> {
                        context.sendMessage("Successfully updated nsfw to {0} for {1}.", nsfw, channel.getName());
                    });

                    break;
                case "bitrate":
                    if (!(channel instanceof VoiceChannel)) {
                        context.sendMessage("Channel is not a voice channel.");
                        return;
                    }

                    try {
                        int bitrate = Integer.parseInt(argument.get(3));
                        channel.getManager().setUserLimit(bitrate).queue(o -> {
                            context.sendMessage("Successfully updated bitrate to {0} for {1}.", bitrate, channel.getName());
                        });
                    } catch (NumberFormatException ex) {
                        context.sendMessage("That is not a number.");
                    }

                    break;
                case "userlimit":
                    if (!(channel instanceof VoiceChannel)) {
                        context.sendMessage("Channel is not a voice channel.");
                        return;
                    }

                    try {
                        int userlimit = Integer.parseInt(argument.get(3));
                        channel.getManager().setUserLimit(userlimit).queue(o -> {
                            context.sendMessage("Successfully updated userlimit to {0} for {1}.", userlimit, channel.getName());
                        });
                    } catch (NumberFormatException ex) {
                        context.sendMessage("That is not a number.");
                    }

                    break;
                default:
                    context.sendMessage("Property doesn't exist.");
                    break;
            }
        }

        // throw new UnsupportedOperationException("This command has not been implemented yet.");
    }
}
