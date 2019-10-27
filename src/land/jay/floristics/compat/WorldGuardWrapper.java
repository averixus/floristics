package land.jay.floristics.compat;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.RegionQuery;

/** Wrapper class for WorldGuard API calls. */
public class WorldGuardWrapper {

    /** Custom flag for growth permission. */
    private static StateFlag FLAG = new StateFlag("floristics", false);
    
    public static void onLoad() {
        
        WorldGuard.getInstance().getFlagRegistry().register(FLAG);
    }
    
    public static boolean canGrow(Location location) {
        
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        State result = query.getApplicableRegions(BukkitAdapter.adapt(location)).queryValue(null, FLAG);
        return result == State.ALLOW;
    }
}
