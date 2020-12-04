/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics.compat;

import org.bukkit.Location;
import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import land.jay.floristics.Floristics;

/** Wrapper class for RedProtect API calls. */
public class RedProtectWrapper {
    
    /** @return Whether compatibility was successfully set up. */
    public static boolean onEnable() {
        
        try {
            Floristics.info("RedProtect is present, adding flag to API.");
            RedProtect.get().getAPI().addFlag("floristics", false, false);
            return true;
        } catch (Exception ex) {
            Floristics.error("Something went wrong integrating with RedProtect, this should never happen!\n" +
                    "RedProtect compatibility will be DISABLED.", ex);
            return false;
        }
    }
    
    public static boolean canGrow(Location location) {
        
        Region region = RedProtect.get().getAPI().getRegion(location);        
        return region == null || region.getFlagBool("floristics");
    }
}
