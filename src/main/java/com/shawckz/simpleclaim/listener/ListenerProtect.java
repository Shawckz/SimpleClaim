/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.listener;

import com.shawckz.simpleclaim.SimpleClaim;
import com.shawckz.simpleclaim.claim.LandBoard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerProtect implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        if(e.isCancelled()) return;
        if(e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockZ() == e.getFrom().getBlockZ() && e.getTo().getBlockY() == e.getFrom().getBlockY()) return;
        Player p = e.getPlayer();

        LandBoard landBoard = SimpleClaim.getInstance().getLandBoard();

        boolean wasIn = landBoard.isClaimed(e.getFrom());
        boolean isIn = landBoard.isClaimed(e.getTo());

        if(!wasIn && isIn) {
            p.sendMessage(ChatColor.YELLOW + "Now entering protected land.");
        }
        else if (wasIn && !isIn) {
            p.sendMessage(ChatColor.YELLOW + "Now exiting protected land.");
        }
    }

    //Could also add block break/place prevention, damage prevention, etc...

}
