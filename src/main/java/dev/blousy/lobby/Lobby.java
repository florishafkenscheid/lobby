package dev.blousy.lobby;

import dev.blousy.lobby.commands.Interval;
import dev.blousy.lobby.commands.LobbyCmd;
import dev.blousy.lobby.commands.SwitchWorld;
import dev.blousy.lobby.listeners.LobbyListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import dev.blousy.lobby.managers.DatabaseManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

import static org.bukkit.Bukkit.getWorlds;


public class Lobby extends JavaPlugin {
    private LobbyCmd lobbyCmd;
    private Interval interval;
    private SwitchWorld switchWorld;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("[Lobby] Lobby enabled.");
        loadWorlds();

        lobbyCmd = new LobbyCmd(this);
        interval = new Interval(this);
        switchWorld = new SwitchWorld(this);
        getServer().getPluginManager().registerEvents(new LobbyListener(), this);

        DatabaseManager.initialize();
        periodicSave();
    }

    public LobbyCmd getLobbyCmd() {
        return lobbyCmd;
    }

    public SwitchWorld getSwitchWorld() {

        return switchWorld;
    }

    public void periodicSave() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for (World world : getWorlds())
                for (Player player : world.getPlayers()) {
                    String playerWorld = player.getWorld().getName();
                    Location playerLocation = player.getLocation();
                    DatabaseManager.saveCoordinates(player.getUniqueId(), playerWorld, (int) playerLocation.getX(), (int) playerLocation.getY(), (int) playerLocation.getZ());
                }
        }, 0, interval.getInterval()); // run every 20 ticks (1 second)
    }

    public void loadWorlds() {
        World lobbyWorld = Bukkit.getWorld("lobby");
        if (lobbyWorld == null) {
            lobbyWorld = Bukkit.createWorld(new WorldCreator("lobby"));
            Bukkit.getWorlds().add(lobbyWorld);
        }
    }
}
