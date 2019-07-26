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
package com.typicalbot.nxt.command.utility;

import com.typicalbot.command.*;
import com.typicalbot.nxt.util.SentryUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = "hex")
public class HexCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        Color color;
        try {
            color = Color.decode(argument.toString());
        } catch (NumberFormatException ex) {
            context.sendMessage("Unable to decode color.");
            return;
        }

        if (color == null) {
            context.sendMessage("Unable to decode color.");
            return;
        }

        context.sendFile(drawHex(color));
    }

    private File drawHex(Color color) {
        BufferedImage bi = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2d = bi.createGraphics();
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(color);
        g2d.fillRect(0, 0, 120, 120);

        g2d.setFont(g2d.getFont().deriveFont(24f));
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()), 5, 25);

        File f = new File("hex.png");

        try {
            ImageIO.write(bi, "png", f);
        } catch (IOException ex) {
            SentryUtil.capture(ex, HexCommand.class);
        }

        return f;
    }
}
