/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim;

import com.shawckz.simpleclaim.claim.LandBoard;
import com.shawckz.simpleclaim.command.GCommandHandler;
import com.shawckz.simpleclaim.commands.CmdClaim;
import com.shawckz.simpleclaim.database.DatabaseManager;
import com.shawckz.simpleclaim.listener.ListenerProtect;
import com.shawckz.simpleclaim.listener.ListenerSelect;
import com.shawckz.simpleclaim.util.SaveTask;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleClaim extends JavaPlugin {

    private static SimpleClaim instance;

    private DatabaseManager databaseManager;
    private LandBoard landBoard;

    @Override
    public void onEnable() {
        instance = this;
        databaseManager = new DatabaseManager(this);
        landBoard = new LandBoard();

        databaseManager.loadClaims(landBoard::registerClaims);

        getServer().getScheduler().runTaskTimer(this, new SaveTask(), (900 * 20), (900 * 20));

        getServer().getPluginManager().registerEvents(new ListenerProtect(), this);
        getServer().getPluginManager().registerEvents(new ListenerSelect(), this);


        GCommandHandler commandHandler = new GCommandHandler(this);
        commandHandler.registerCommands(new CmdClaim());
        //No need to register commands in plugin.yml, library registers them into the command map automatically
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static SimpleClaim getInstance() {
        return instance;
    }

    public LandBoard getLandBoard() {
        return landBoard;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
