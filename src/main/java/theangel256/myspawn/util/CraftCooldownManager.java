package theangel256.myspawn.util;

import theangel256.myspawn.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftCooldownManager implements CooldownManager {
    private final Map<UUID, Double> cooldowns;
    private final double defaultCooldown;

    public CraftCooldownManager(final Main plugin) {
        cooldowns = new HashMap<>();
        defaultCooldown = plugin.getConfig().getDouble("Spawn-Teleport.Cooldown-Time");
    }

    @Override
    public double getDefaultCooldown() {
        return defaultCooldown;
    }

    @Override
    public double getCooldown(final UUID playerUUID) {
        return cooldowns.getOrDefault(playerUUID, 0.0);
    }

    @Override
    public void setCooldown(final UUID playerUUID, final double time) {
        if (time <= 0.0) {
            cooldowns.remove(playerUUID);
        } else {
            cooldowns.put(playerUUID, time);
        }
    }
}
