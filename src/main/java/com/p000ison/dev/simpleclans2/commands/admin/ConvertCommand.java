/*
 * This file is part of SimpleClans2 (2012).
 *
 *     SimpleClans2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SimpleClans2 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SimpleClans2.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     Last modified: 29.10.12 22:42
 */

package com.p000ison.dev.simpleclans2.commands.admin;

import com.p000ison.dev.simpleclans2.SimpleClans;
import com.p000ison.dev.simpleclans2.commands.GenericConsoleCommand;
import com.p000ison.dev.simpleclans2.database.Database;
import com.p000ison.dev.simpleclans2.language.Language;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;

/**
 * Represents a ConvertCommand
 */
public class ConvertCommand extends GenericConsoleCommand {

    public ConvertCommand(SimpleClans plugin)
    {
        super("ConvertCommand", plugin);
        setArgumentRange(0, 0);
        setUsages(MessageFormat.format(Language.getTranslation("usage.convert"), plugin.getSettingsManager().getClanCommand()));
        setIdentifiers(Language.getTranslation("command.convert"));
        setPermission("simpleclans.admin.convert");
    }

    @Override
    public String getMenu()
    {
        return MessageFormat.format(Language.getTranslation("menu.convert"), plugin.getSettingsManager().getClanCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        String action = args[0];
        Database database = null;

        if (action.equalsIgnoreCase("mysql")) {
//            database = new MySQLDatabase();
        }
    }
}
