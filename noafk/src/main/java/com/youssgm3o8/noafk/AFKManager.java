package com.youssgm3o8.noafk;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class AFKManager {

    private final PluginBase plugin;
    private final int kickAfterSeconds;
    private final int warningBeforeSeconds;
    private final String warningMessage;

    private final Map<String, Long> lastActivityMap = new HashMap<>();
    private final Set<String> ignoredPlayers = new HashSet<>();
    private final Set<String> warnedPlayers = new HashSet<>(); // Add this line

    public AFKManager(PluginBase plugin, int kickAfterSeconds, int warningBeforeSeconds, String warningMessage) {
        this.plugin = plugin;
        this.kickAfterSeconds = kickAfterSeconds;
        this.warningBeforeSeconds = warningBeforeSeconds;
        this.warningMessage = warningMessage;
    }

    public void updateActivity(Player player) {
        lastActivityMap.put(player.getName(), System.currentTimeMillis());
        warnedPlayers.remove(player.getName());
    }

    public void checkAFKPlayers() {
        long currentTime = System.currentTimeMillis();
        for (Player player : plugin.getServer().getOnlinePlayers().values()) {
            if (ignoredPlayers.contains(player.getName())) {
                continue;
            }
            long lastActive = lastActivityMap.getOrDefault(player.getName(), currentTime);
            int inactiveSeconds = (int) ((currentTime - lastActive) / 1000);

            if (inactiveSeconds >= (kickAfterSeconds - warningBeforeSeconds) && inactiveSeconds < kickAfterSeconds) {
                // Send warning message
                if (!warnedPlayers.contains(player.getName())) {
                    player.sendTip(warningMessage);
                    warnedPlayers.add(player.getName());
                }
                
                // Show countdown in last 10 seconds
                int remainingSeconds = kickAfterSeconds - inactiveSeconds;
                if (remainingSeconds <= 10) {
                    player.sendTitle(
                        "§cAFK Warning", 
                        "§eKick in: §c" + remainingSeconds + " §eseconds",
                        0, 20, 0
                    );
                }
            }

            if (inactiveSeconds >= kickAfterSeconds) {
                // Kick for AFK
                warnedPlayers.remove(player.getName());
                lastActivityMap.remove(player.getName()); // Remove player from activity tracking
                player.kick("You have been kicked for being AFK too long.", false);
            }
        }
    }

    public void playerQuit(Player player) {
        lastActivityMap.remove(player.getName());
        warnedPlayers.remove(player.getName());
    }

    public void addIgnoredPlayer(String playerName) {
        ignoredPlayers.add(playerName);
        saveIgnoredPlayers();
    }

    public void removeIgnoredPlayer(String playerName) {
        ignoredPlayers.remove(playerName);
        saveIgnoredPlayers();
    }

    private void saveIgnoredPlayers() {
        plugin.getConfig().set("ignored-players", new ArrayList<>(ignoredPlayers));
        plugin.getConfig().save();
    }

    public void loadIgnoredPlayers() {
        ignoredPlayers.clear();
        List<String> saved = plugin.getConfig().getStringList("ignored-players");
        ignoredPlayers.addAll(saved);
    }

    public boolean isPlayerIgnored(String playerName) {
        return ignoredPlayers.contains(playerName);
    }
}