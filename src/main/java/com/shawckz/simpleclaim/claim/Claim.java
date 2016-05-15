/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.claim;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Location;

@Getter
@Setter
public class Claim {

    public static final String INSERT = "INSERT INTO Claims (minX,maxX,minY,maxY,minZ,maxZ) VALUES(?,?,?,?,?,?)";
    public static final String SELECT = "SELECT id,minX,maxX,minY,maxY,minZ,maxZ FROM Claims";
    public static final String SAVE = "UPDATE Claims SET minX=?,maxX=?,minY=?,maxY=?,minZ=?,maxZ=? WHERE id=?";

    private int id;
    private int minX;
    private int maxX;

    private int minY;
    private int maxY;

    private int minZ;
    private int maxZ;

    private boolean inserted = true;

    public Claim(Location min, Location max) {
        int minX = Math.min(min.getBlockX(), max.getBlockX());
        int maxX = Math.max(min.getBlockX(), max.getBlockX());
        int minY = Math.min(min.getBlockY(), max.getBlockY());
        int maxY = Math.max(min.getBlockY(), max.getBlockY());
        int minZ = Math.min(min.getBlockZ(), max.getBlockZ());
        int maxZ = Math.max(min.getBlockZ(), max.getBlockZ());

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public Claim(int id, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this.id = id;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public Claim(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public boolean within(final Location loc) {
        final int x = loc.getBlockX();
        final int z = loc.getBlockZ();
        final int y = loc.getBlockY();

        return (x <= maxX) && (x >= minX) && (z <= maxZ) && (z >= minZ) && (y <= maxY) && (y >= minY);
    }

}
