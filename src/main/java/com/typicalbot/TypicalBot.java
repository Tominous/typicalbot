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
package com.typicalbot;

import com.typicalbot.common.Container;
import com.typicalbot.common.Logger;
import com.typicalbot.common.command.CommandManager;
import com.typicalbot.common.facade.ContainerFacade;
import com.typicalbot.shard.ShardManager;
import org.apache.log4j.Level;

import javax.security.auth.login.LoginException;

public class TypicalBot {
    public static void main(String[] args) throws LoginException, InterruptedException {
        // We will have to move this container facade outside of the main method
        // because we will need two different facades. The first one, this, will
        // be the global facade to handle various services that will be used in
        // different sections of the bot, and then the second one, shard, will be
        // used to handle minor services that are not used frequently. This should
        // essentially stop services from stalling when a shard stops unexpectedly.
        ContainerFacade container = new ContainerFacade();
        Container.swap(container);

        Logger.init(Level.INFO);

        // Temporary.
        CommandManager commandManager = new CommandManager();
        Container.put(CommandManager.class, commandManager);

        ShardManager.register(1);
    }
}
