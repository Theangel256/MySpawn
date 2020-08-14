// 
// Decompiled by Procyon v0.5.36
// 

package theangel256.myspawn.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import theangel256.myspawn.MySpawn;

public class CraftCooldownManager implements CooldownManager
{
    private final MySpawn plugin;
    private Map<UUID, Double> cooldowns;
    private final double defaultCooldown;
    
    public CraftCooldownManager(final MySpawn plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<UUID, Double>();
        this.defaultCooldown = this.plugin.getConfig().getDouble("Options.Spawn-Cooldown");
    }
    
    @Override
    public double getDefaultCooldown() {
        return this.defaultCooldown;
    }
    
    @Override
    public double getCooldown(final UUID playerUUID) {
        return this.cooldowns.getOrDefault(playerUUID, 0.0);
    }
    
    @Override
    public void setCooldown(final UUID playerUUID, final double time) {
        if (time <= 0.0) {
            this.cooldowns.remove(playerUUID);
        }
        else {
            this.cooldowns.put(playerUUID, time);
        }
    }
}
