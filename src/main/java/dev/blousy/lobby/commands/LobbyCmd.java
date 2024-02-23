package dev.blousy.lobby.commands;

import dev.blousy.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import static dev.blousy.lobby.methods.Utils.msgPlayer;

public class LobbyCmd extends Command {
    public LobbyCmd(Lobby main) {
        super(main, "lobby");
    }

    @Override
    protected void execute(Player player, String[] args) {
        sendToLobby(player);
    }

    public void sendToLobby(Player player) {
        if (isPlayerInLobby(player)) {
            msgPlayer(player, "&cYou are already in the lobby.");
        }
        else {
            Bukkit.dispatchCommand(player, "world tp lobby");
        }
    }

    public boolean isPlayerInLobby(Player player) {
        World lobbyWorld = Bukkit.getWorld("lobby");
        Location playerLocation = player.getLocation();
        return playerLocation.getWorld() == lobbyWorld;
    }
}
