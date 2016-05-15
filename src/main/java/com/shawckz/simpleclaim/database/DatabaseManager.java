/*
 * Copyright (c) Jonah Seguin (Shawckz) 2016.  You may not copy, re-sell, distribute, modify, or use any code contained in this document or file, collection of documents or files, or project.  Thank you.
 */

package com.shawckz.simpleclaim.database;

import com.shawckz.simpleclaim.SimpleClaim;
import com.shawckz.simpleclaim.claim.Claim;
import com.shawckz.simpleclaim.configuration.Configuration;
import com.shawckz.simpleclaim.configuration.annotations.ConfigData;
import com.shawckz.simpleclaim.util.ClaimException;
import com.shawckz.simpleclaim.util.XCallback;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

public class DatabaseManager extends Configuration{

    private HikariDataSource hikari;

    @ConfigData("host")
    private String host = "localhost";

    @ConfigData("port")
    private int port = 1234;

    @ConfigData("database")
    private String database = "someDatabase";

    @ConfigData("user")
    private String user = "username";

    @ConfigData("password")
    private String password = "somePassword123";

    public DatabaseManager(SimpleClaim instance) {
        super(instance, "database.yml");
        load();
        save();

        setup();
        setupTables();
    }

    private void setup() {
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", password);
    }

    private void setupTables() {
        try(Connection connection = hikari.getConnection();
            Statement statement = connection.createStatement();){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Claims(id int NOT NULL AUTO_INCREMENT, minX int, maxX int, minY int, maxY int, minZ int, maxZ int, PRIMARY KEY(id))");
        } catch (SQLException e) {
            throw new ClaimException("Could not setup tables", e);
        }
    }

    /* Claim data */

    public void loadClaims(XCallback<List<Claim>> callback) {
        new BukkitRunnable(){
            @Override
            public void run() {
                List<Claim> claims = new ArrayList<>();
                try (Connection connection = hikari.getConnection();
                     PreparedStatement select = connection.prepareStatement(Claim.SELECT)) {

                    ResultSet result = select.executeQuery();
                    while (result.next()) {
                        int id = result.getInt("id");

                        int minX = result.getInt("minX");
                        int maxX = result.getInt("maxX");

                        int minY = result.getInt("minY");
                        int maxY = result.getInt("maxY");

                        int minZ = result.getInt("minZ");
                        int maxZ = result.getInt("maxZ");

                        claims.add(new Claim(id, minX, maxX, minY, maxY, minZ, maxZ));
                    }
                    result.close();

                    callback.call(claims);
                } catch (SQLException e) {
                    throw new ClaimException("Could not load claims", e);
                }
            }
        }.runTaskAsynchronously(SimpleClaim.getInstance());
    }

    public void createClaim(Claim claim, XCallback<Claim> callback) {
        new BukkitRunnable(){
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     //INSERT INTO Claims (minX,maxX,minY,maxY,minZ,maxZ) VALUES(?,?,?,?,?,?)
                     PreparedStatement insert = connection.prepareStatement(Claim.INSERT, Statement.RETURN_GENERATED_KEYS)) {


                    insert.setInt(1, claim.getMinX());
                    insert.setInt(2, claim.getMaxX());
                    insert.setInt(3, claim.getMinY());
                    insert.setInt(4, claim.getMaxY());
                    insert.setInt(5, claim.getMinZ());
                    insert.setInt(6, claim.getMaxZ());

                    insert.executeUpdate();

                    ResultSet tableKeys = insert.getGeneratedKeys();
                    tableKeys.next();
                    int id = tableKeys.getInt(1);

                    callback.call(new Claim(id, claim.getMinX(), claim.getMaxX(), claim.getMinY(), claim.getMaxY(), claim.getMinZ(), claim.getMaxZ()));
                } catch (SQLException e) {
                    throw new ClaimException("Could not create claim", e);
                }
            }
        }.runTaskAsynchronously(SimpleClaim.getInstance());
    }

    public void updateClaim(final Claim claim, XCallback<Integer> callback) {
        new BukkitRunnable(){
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     //UPDATE Claims SET minX=?,maxX=?,minY=?,maxY=?,minZ=?,maxZ=? WHERE id=?
                     PreparedStatement update = connection.prepareStatement(Claim.SAVE)) {

                    update.setInt(1, claim.getMinX());
                    update.setInt(2, claim.getMaxX());
                    update.setInt(3, claim.getMinY());
                    update.setInt(4, claim.getMaxY());
                    update.setInt(5, claim.getMinZ());
                    update.setInt(6, claim.getMaxZ());

                    callback.call(update.executeUpdate());

                } catch (SQLException e) {
                    throw new ClaimException("Could not update claim", e);
                }
            }
        }.runTaskAsynchronously(SimpleClaim.getInstance());
    }


}
