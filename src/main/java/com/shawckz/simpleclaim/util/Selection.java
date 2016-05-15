/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
@Setter
@AllArgsConstructor
public class Selection {

    private static Map<String, Selection> selections = new HashMap<>();

    public static Selection getSelection(Player player) {
        if(!selections.containsKey(player.getName())) {
            selections.put(player.getName(), new Selection());
        }
        return selections.get(player.getName());
    }

    private Location pos1 = null;
    private Location pos2 = null;

    public Selection() {

    }



}
