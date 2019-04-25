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

package com.typicalbot.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

import java.util.Collection;

public class CommandCheck {
    public static void checkArguments(CommandArgument argument) {
        checkArguments(argument.getArguments());
    }

    public static void checkArguments(Collection<?> coll) {
        checkArguments(coll, "Not enough arguments.");
    }

    public static void checkArguments(Collection<?> coll, String message) {
        if (coll.isEmpty())
            throw new IllegalArgumentException(message);
    }

    public static void checkPermission(Member member, Permission permission) {
        checkPermission(member, permission, String.format("%s cannot perform this command due to lack of permissions. Missing permission: %s", member.getAsMention(), permission.toString()));
    }

    public static void checkPermission(Member member, Permission permission, String message) {
        if (!member.hasPermission(permission))
            throw new InsufficientPermissionException(permission, message);
    }
}
