/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.listener;

import com.shawckz.simpleclaim.util.Selection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerSelect implements Listener{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSelect(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(p.getItemInHand().getType() == Material.GOLD_AXE) {
                if(p.hasPermission("shaded.claim")) {
                    Selection selection = Selection.getSelection(p);

                    if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        selection.setPos1(e.getClickedBlock().getLocation());
                        p.sendMessage(ChatColor.GREEN + "Set position 1 for selection.");
                    }
                    else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        selection.setPos2(e.getClickedBlock().getLocation());
                        p.sendMessage(ChatColor.GREEN + "Set position 2 for selection.");
                    }

                    e.setCancelled(true);
                }
            }
        }
    }

}
