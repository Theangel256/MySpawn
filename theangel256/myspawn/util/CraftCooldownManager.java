package theangel256.myspawn.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import javax.annotation.Nonnull;
import theangel256.myspawn.MySpawn;

public class CraftCooldownManager implements CooldownManager {
    private final Map<UUID, Double> cooldowns;
    private final double defaultCooldown;

    public CraftCooldownManager(final MySpawn plugin) {
        this.cooldowns = new HashMap<>();
        this.defaultCooldown = plugin.getConfig().getDouble("Options.Spawn-Cooldown");
    }

    @Override
    public double getDefaultCooldown() {
        return this.defaultCooldown;
    }

    @Override
    public double getCooldown(@Nonnull final UUID playerUUID) {
        return this.cooldowns.getOrDefault(playerUUID, 0.0);
    }

    @Override
    public void setCooldown(@Nonnull final UUID playerUUID, final double time) {
        if (time <= 0.0) {
            this.cooldowns.remove(playerUUID);
        } else {
            this.cooldowns.put(playerUUID, time);
        }
    }
}
