/**
 * TypicalBot - A multipurpose discord bot
 * Copyright (C) 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.typicalbot;

import com.typicalbot.common.Container;
import com.typicalbot.common.Logger;
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

        ShardManager.register(1);
    }
}
