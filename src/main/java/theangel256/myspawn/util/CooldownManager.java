package theangel256.myspawn.util;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface CooldownManager
{
    double getDefaultCooldown();
    
    double getCooldown(@Nonnull final UUID p0);
    
    void setCooldown(@Nonnull final UUID p0, final double p1);
}
