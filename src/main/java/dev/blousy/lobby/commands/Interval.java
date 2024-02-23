package dev.blousy.lobby.commands;

import dev.blousy.lobby.Lobby;
import org.bukkit.entity.Player;

import static dev.blousy.lobby.methods.Utils.msgPlayer;

public class Interval extends Command {
    public Interval(Lobby main) {
        super(main, "interval");
    }

    long interval = 100;

    @Override
    protected void execute(Player player, String[] args) {
        setInterval(Long.parseLong(args[0]));
        msgPlayer(player, "&aSuccessfully set interval to &b" + args[0]);
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getInterval() {
        return this.interval;
    }
}
