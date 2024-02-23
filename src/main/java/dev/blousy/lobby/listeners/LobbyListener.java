package dev.blousy.lobby.listeners;

import dev.blousy.lobby.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        World lobbyWorld = Bukkit.getWorld("lobby");
        if (lobbyWorld == null) {
            Bukkit.getConsoleSender().sendMessage("[ERROR] NO LOBBY WORLD PRESENT!");
            return;
        }

        Location destination = lobbyWorld.getSpawnLocation();
        event.getPlayer().teleport(destination);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        // implement database, save coordinates and timer for saving coordinates
        String world = event.getPlayer().getWorld().getName();
        Location playerLocation = event.getPlayer().getLocation();

        DatabaseManager.saveCoordinates(event.getPlayer().getUniqueId(), world, (int) playerLocation.getX(), (int) playerLocation.getY(), (int) playerLocation.getZ());
    }
}
