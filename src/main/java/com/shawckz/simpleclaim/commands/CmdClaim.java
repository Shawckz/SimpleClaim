/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.commands;

import com.shawckz.simpleclaim.SimpleClaim;
import com.shawckz.simpleclaim.claim.Claim;
import com.shawckz.simpleclaim.command.GCmd;
import com.shawckz.simpleclaim.command.GCmdArgs;
import com.shawckz.simpleclaim.command.GCommand;
import com.shawckz.simpleclaim.util.Selection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdClaim implements GCommand {

    @GCmd(name = "claim", usage = "/claim", permission = "shaded.claim", playerOnly = true)
    public void onCmdClaim(GCmdArgs args) {
        Player p = args.getSender().getPlayer();
        Selection selection = Selection.getSelection(p);

        if(selection.getPos1() != null && selection.getPos2() != null) {
            Claim base = new Claim(selection.getPos1(), selection.getPos2());
            base.setInserted(false);
            SimpleClaim.getInstance().getLandBoard().registerClaim(base);
            //Do not insert into database now - will be automatically inserted/updated every 15 minutes

            p.sendMessage(ChatColor.GREEN + "Claim created.");
        }
        else{
            p.sendMessage(ChatColor.RED + "You must make a selection using a gold axe first (right and left click two points)");
        }
    }

    @GCmd(name = "claim vert", usage = "/claim vert", permission = "shaded.claim", playerOnly = true)
    public void onCmdClaimVert(GCmdArgs args) {
        Player p = args.getSender().getPlayer();
        Selection selection = Selection.getSelection(p);

        if(selection.getPos1() != null && selection.getPos2() != null) {
            Claim base = new Claim(selection.getPos1(), selection.getPos2());
            base.setMinY(0);
            base.setMaxY(Bukkit.getWorlds().stream().findFirst().get().getMaxHeight());
            base.setInserted(false);
            SimpleClaim.getInstance().getLandBoard().registerClaim(base);
            //Do not insert into database now - will be automatically inserted/updated every 15 minutes

            p.sendMessage(ChatColor.GREEN + "Claim created.");
        }
        else{
            p.sendMessage(ChatColor.RED + "You must make a selection using a gold axe first (right and left click two points)");
        }
    }

}
