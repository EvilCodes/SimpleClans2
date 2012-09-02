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


package com.p000ison.dev.simpleclans2.teleportation;

import com.p000ison.dev.simpleclans2.Language;
import com.p000ison.dev.simpleclans2.SimpleClans;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Handles teleporting with cooldown
 */
public class TeleportManager {
    private SimpleClans plugin;
    private HashMap<String, TeleportState> waitingPlayers = new HashMap<String, TeleportState>();

    /**
     *
     */
    public TeleportManager(SimpleClans plugin)
    {
        this.plugin = plugin;
        startCounter();
    }

    /**
     * Add player to teleport waiting queue
     *
     * @param player      The player.
     * @param destination The destionation.
     * @param msg         The message.
     */
    public void addPlayer(Player player, Location destination, String msg)
    {
        int secs = plugin.getSettingsManager().getTimeUntilTeleport();

        waitingPlayers.put(player.getName(), new TeleportState(plugin, player, destination, msg, secs));

        if (secs > 0) {
            player.sendMessage(ChatColor.AQUA + MessageFormat.format(Language.getTranslation("waiting.for.teleport.stand.still.for.0.seconds"), secs));
        }
    }

    private void dropItems(Player player)
    {

        Inventory inv = player.getInventory();
        ItemStack[] contents = inv.getContents();

        for (ItemStack item : contents) {

            if (item == null) {
                continue;
            }

            if (plugin.getSettingsManager().dropItemOnTeleport(item.getType())) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
                inv.remove(item);
            }
        }
    }

    public static boolean isLocationEqual(Location location1, Location location2, double fuzzy)
    {
        if (Math.abs(location1.getX() - location2.getX()) > fuzzy) {
            return false;
        }

        if (Math.abs(location1.getY() - location2.getY()) > fuzzy) {
            return false;
        }

        if (Math.abs(location1.getZ() - location2.getZ()) > fuzzy) {
            return false;
        }

        return true;
    }

    private void startCounter()
    {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run()
            {
                Iterator players = waitingPlayers.values().iterator();

                while (players.hasNext()) {
                    TeleportState state = (TeleportState) players.next();

                    if (state.isProcessing()) {
                        continue;
                    }

                    state.setProcessing(true);

                    Player player = state.getPlayer();

                    if (player == null) {
                        players.remove();
                        continue;
                    }

                    boolean isLocationEqual = isLocationEqual(player.getLocation(), state.getLocation(), plugin.getSettingsManager().getTeleportFuzzyness());

                    if (state.isTeleportTime()) {
                        if (isLocationEqual) {
                            Location loc = state.getDestination();

                            if (plugin.getSettingsManager().dropItems() && !player.hasPermission("simpleclans.mod.keep-items")) {
                                dropItems(player);
                            }

                            player.teleport(new Location(loc.getWorld(), loc.getBlockX() + .5, loc.getBlockY(), loc.getBlockZ() + .5));

                            player.sendMessage(state.getMessage());
                        } else {
                            player.sendMessage(ChatColor.RED + Language.getTranslation("you.moved.teleport.cancelled"));
                        }

                        players.remove();
                    } else if (!isLocationEqual) {
                        player.sendMessage(ChatColor.RED + Language.getTranslation("you.moved.teleport.cancelled"));
                        players.remove();
                    } else {
                        player.sendMessage(ChatColor.AQUA.toString() + state.getCounter());
                    }

                    state.setProcessing(false);
                }
            }
        }, 0, 20L);
    }
}