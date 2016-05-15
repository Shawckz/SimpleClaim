/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.claim;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

public class LandBoard {

    private final Set<Claim> claims = new HashSet<>();

    public boolean isClaimed(Location loc) {
        for (Claim claim : claims) {
            if (claim.within(loc)) {
                return true;
            }
        }
        return false;
    }

    public Set<Claim> getClaims() {
        return claims;
    }

    public void registerClaims(Collection<Claim> claims) {
        this.claims.addAll(claims);
    }

    public void unclaim(Claim claim) {
        if(claims.contains(claim)) {
            claims.remove(claim);
        }
    }

    public Claim getClaim(Location loc) {
        for (Claim claim : claims) {
            if (claim.within(loc)) {
                return claim;
            }
        }
        return null;
    }

    public void registerClaim(Claim claim) {
        claims.add(claim);
    }

}
