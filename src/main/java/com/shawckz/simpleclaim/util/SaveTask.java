/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.util;

import com.shawckz.simpleclaim.SimpleClaim;
import com.shawckz.simpleclaim.claim.Claim;

import java.util.Iterator;

public class SaveTask implements Runnable {

    @Override
    public void run() {
        int updated = 0;
        Iterator<Claim> it = SimpleClaim.getInstance().getLandBoard().getClaims().iterator();
        while(it.hasNext()) {
            Claim claim = it.next();
            if(claim.isInserted()) {
                SimpleClaim.getInstance().getDatabaseManager().updateClaim(claim, result -> {});
            }
            else{
                SimpleClaim.getInstance().getDatabaseManager().createClaim(claim, result -> {
                    SimpleClaim.getInstance().getLandBoard().unclaim(claim);
                    claim.setInserted(true);
                    result.setInserted(true);
                    SimpleClaim.getInstance().getLandBoard().registerClaim(result);
                });
            }
            updated++;
        }
        SimpleClaim.getInstance().getLogger().info("[Save] Saved " + updated + " claims to the database.");
    }
}
