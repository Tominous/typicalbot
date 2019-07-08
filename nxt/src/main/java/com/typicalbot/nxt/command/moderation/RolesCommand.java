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
package com.typicalbot.nxt.command.moderation;

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandCheck;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.data.mongo.dao.GuildDAO;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.LinkedList;
import java.util.List;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "roles")
public class RolesCommand implements Command {
    public String[] usage() {
        return new String[]{
            "roles create [name]",
            "roles delete [name]",
            "roles clone [role] [name]",
            "roles give [user] [role]",
            "roles take [user] [role]"
        };
    }

    @Override
    public String description() {
        return "Manage or view roles in a server.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();
        /*
         * roles create <name> [bits]  ->  roles create Mod 268435478 (role has manage_channels, manage_roles, kick, ban)
         *
         * roles delete <role>
         *
         * roles clone <role> <name>
         *
         * roles give <user> <role>
         * roles take <user> <role>
         *
         * roles public add <role>
         * roles public remove <role>
         * roles public list
         *
         * roles reaction add <messageId> <emote> <role>
         * roles reaction remove <messageId> <emote>
         * roles reaction list
         *
         * roles list
         *
         * roles edit <role> <prop> <value>  ->  roles edit @Mod name Moderator
         *
         * Properties:
         *  - name
         *  - color
         *  - position
         *  - hoist
         *  - mention
         *  - bits [For advanced users]
         *
         * For advanced users
         *
         * roles permissions @Mod +kick_members -ban_members
         *
         * + Allow
         * - Disallow
         */
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MANAGE_ROLES);
        CommandCheck.checkArguments(argument);

        if (argument.get(0).equalsIgnoreCase("create")) {
            if (argument.length() < 2) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            String name = argument.get(1);

            context.getGuild().createRole().setName(name).queue(o -> {
                context.sendMessage("Successfully created a role: {0}.", name);
            });
        } else if (argument.get(0).equalsIgnoreCase("delete")) {
            if (argument.length() < 2) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            Role role = context.getRole(argument.get(1));

            if (role == null) {
                context.sendMessage("Role doesn't exist.");
                return;
            }

            role.delete().queue(o -> {
                context.sendMessage("Successfully deleted {0} role.", argument.get(1));
            });
        } else if (argument.get(0).equalsIgnoreCase("clone")) {
            if (argument.length() < 3) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            Role role = context.getRole(argument.get(1));

            if (role == null) {
                context.sendMessage("Role doens't exist.");
                return;
            }

            String name = argument.get(2);

            context.getGuild().createCopyOfRole(role).setName(name).queue(o -> {
                context.sendMessage("Successfully cloned role {0} to {1}.", role.getName(), name);
            });
        } else if (argument.get(0).equalsIgnoreCase("give")) {
            if (argument.length() < 3) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            User user = context.getUser(argument.get(1));

            if (user == null) {
                context.sendMessage("User doesn't exist.");
                return;
            }

            Role role = context.getRole(argument.get(2));

            if (role == null) {
                context.sendMessage("Role doesn't exist.");
                return;
            }

            context.getGuild().addRoleToMember(context.getGuild().getMember(user), role).queue(o -> {
                context.sendMessage("Successfully given {0} to {1}.", role.getName(), user.getAsTag());
            });
        } else if (argument.get(0).equalsIgnoreCase("take")) {
            if (argument.length() < 3) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            User user = context.getUser(argument.get(1));

            if (user == null) {
                context.sendMessage("User doesn't exist.");
                return;
            }

            Role role = context.getRole(argument.get(2));

            if (role == null) {
                context.sendMessage("Role doesn't exist.");
                return;
            }

            context.getGuild().removeRoleFromMember(context.getGuild().getMember(user), role).queue(o -> {
                context.sendMessage("Successfully taken {0} from {1}.", role.getName(), user.getAsTag());
            });
        } else if (argument.get(0).equalsIgnoreCase("public")) {
            if (argument.length() < 2) {
                context.sendMessage("Incorrect usage.");
                return;
            }

            if (argument.get(1).equalsIgnoreCase("add")) {
                if (argument.length() < 3) {
                    context.sendMessage("Incorrect usage.");
                    return;
                }

                Role role = context.getRole(argument.get(2));

                if (role == null) {
                    context.sendMessage("Role doesn't exist.");
                    return;
                }

                long[] roles = object.getGuildSettings().getRoles().getPublicRoles() != null ? object.getGuildSettings().getRoles().getPublicRoles() : new long[0];

                List<Long> temp = new LinkedList<>();

                for (long r : roles) {
                    temp.add(r);
                }

                if (temp.contains(role.getIdLong())) {
                    context.sendMessage("Role is already public");
                    return;
                } else {
                    temp.add(role.getIdLong());
                }

                object.getGuildSettings().getRoles().setPublicRoles(temp.stream().mapToLong(Long::longValue).toArray());
                dao.update(object);
                context.sendMessage("Successfully added {0} to public roles.", role.getName());
            } else if (argument.get(1).equalsIgnoreCase("remove")) {
                if (argument.length() < 3) {
                    context.sendMessage("Incorrect usage.");
                    return;
                }

                Role role = context.getRole(argument.get(2));

                if (role == null) {
                    context.sendMessage("Role doesn't exist.");
                    return;
                }

                long[] roles = object.getGuildSettings().getRoles().getPublicRoles() != null ? object.getGuildSettings().getRoles().getPublicRoles() : new long[0];

                List<Long> temp = new LinkedList<>();

                for (long r : roles) {
                    if (r != role.getIdLong()) {
                        temp.add(r);
                    }
                }

                object.getGuildSettings().getRoles().setPublicRoles(temp.stream().mapToLong(Long::longValue).toArray());
                dao.update(object);
                context.sendMessage("Successfully removed {0} from public roles.");
            } else if (argument.get(1).equalsIgnoreCase("list")) {
                long[] roles = object.getGuildSettings().getRoles().getPublicRoles() != null ? object.getGuildSettings().getRoles().getPublicRoles() : new long[0];

                if (roles.length == 0) {
                    context.sendMessage("There are no public roles.");
                    return;
                }

                StringBuilder builder = new StringBuilder();

                builder.append("Available roles: ").append("\n");

                for (long r : roles) {
                    Role role = context.getRole(String.valueOf(r));

                    if (role != null) {
                        builder.append(" - ").append(role.getName()).append("\n");
                    }
                }

                context.sendMessage(builder.toString());
            }

            return;
        }
    }
}
