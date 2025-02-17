package com.youssgm3o8.noafk;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.scheduler.Task;
import cn.nukkit.Player;

public class Main extends PluginBase implements Listener {

    private AFKManager afkManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Create AFK manager with config values
        afkManager = new AFKManager(
            this,
            getConfig().getInt("kick-after-seconds", 300),
            getConfig().getInt("warning-before-seconds", 30),
            getConfig().getString("warning-message", "You will be kicked for being AFK soon!")
        );

        // Load ignored players from config
        afkManager.loadIgnoredPlayers();

        getServer().getPluginManager().registerEvents(this, this);

        // Schedule repeating task to check for AFK players
        getServer().getScheduler().scheduleRepeatingTask(this, new Task() {
            @Override
            public void onRun(int currentTick) {
                afkManager.checkAFKPlayers();
            }
        }, 20); // runs every second

        getLogger().info("NOafk enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("NOafk disabled.");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        afkManager.updateActivity(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        afkManager.updateActivity(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        afkManager.playerQuit(event.getPlayer());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("afkignore")) {
            if (args.length != 1) {
                return false;
            }

            String playerName = args[0];
            if (afkManager.isPlayerIgnored(playerName)) {
                afkManager.removeIgnoredPlayer(playerName);
                sender.sendMessage(playerName + " is no longer ignored.");
            } else {
                afkManager.addIgnoredPlayer(playerName);
                sender.sendMessage(playerName + " is now ignored.");
            }
            return true;
        }
        return false;
    }
}