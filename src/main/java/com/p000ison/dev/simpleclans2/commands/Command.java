/*
 * This file is part of SimpleClans2 (2012).
 *
 *     SimpleClans2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foobar is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     Created: 02.09.12 18:29
 */


package com.p000ison.dev.simpleclans2.commands;

import com.p000ison.dev.simpleclans2.clanplayer.ClanPlayer;
import org.bukkit.command.CommandSender;

/**
 * Represents a Command
 */
public interface Command {

    public String getName();

    public String[] getUsages();

    public void setIdentifiers(String... identifiers);

    public boolean isIdentifier(String cmd);

    public void setUsages(String... text);

    public String getMenu(ClanPlayer cp, CommandSender sender);

    public int getMaxArguments();

    public int getMinArguments();

    public void setArgumentRange(int min, int max);
}