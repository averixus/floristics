/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics.compat;

import org.bukkit.Location;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import land.jay.floristics.Floristics;

/** Wrapper class for WorldGuard API calls. */
public class WorldGuardWrapper {

    /** Custom flag for growth permission. */
    private static StateFlag FLAG = new StateFlag("floristics", false);
    
    /** @return Whether compatibility was successfully set up. */
    public static boolean onLoad() {
        
        Floristics.info("WorldGuard is present, adding flag to registry.");
        try {
            WorldGuard.getInstance().getFlagRegistry().register(FLAG);
            return true;
        } catch (FlagConflictException ex) {
            Floristics.error("Someone has already registered a floristics flag for WorldGuard, this should never happen!\n" +
                    "WorldGuard compatibility will be DISABLED.", ex);
            return false;
        }
    }
    
    public static boolean canGrow(Location location) {
        
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location)).queryValue(null, FLAG) == State.ALLOW;
    }
}
