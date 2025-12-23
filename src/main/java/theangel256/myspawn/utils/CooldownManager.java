package theangel256.myspawn.utils;

import theangel256.myspawn.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Double> cooldowns;
    private final double defaultCooldown;

    public CooldownManager(final Main plugin) {
        cooldowns = new HashMap<>();
        defaultCooldown = plugin.getConfig().getDouble("Spawn-Teleport.Cooldown-Time");
    }

    public double getDefaultCooldown() {
        return defaultCooldown;
    }

    public double getCooldown(final UUID playerUUID) {
        return cooldowns.getOrDefault(playerUUID, 0.0);
    }

    public void setCooldown(final UUID playerUUID, final double time) {
        if (time <= 0.0) {
            cooldowns.remove(playerUUID);
        } else {
            cooldowns.put(playerUUID, time);
        }
    }
}
