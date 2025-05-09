package theangel256.myspawn.util;

import java.util.UUID;

public interface CooldownManager
{
    double getDefaultCooldown();
    
    double getCooldown(final UUID p0);
    
    void setCooldown(final UUID p0, final double p1);
}
