package land.jay.floristics.compat;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import land.jay.floristics.Floristics;

/** Wrapper class for WorldGuard API calls. */
public class WorldGuardWrapper {

    /** Custom flag for growth permission. */
    private static StateFlag FLAG = new StateFlag("floristics", false);
    
    public static void onLoad() {
        
        Floristics.info("WorldGuard is present, adding flag to registry.");
        WorldGuard.getInstance().getFlagRegistry().register(FLAG);
    }
    
    public static boolean canGrow(Location location) {
        
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        return query.getApplicableRegions(BukkitAdapter.adapt(location)).queryValue(null, FLAG) == State.ALLOW;
    }
}
