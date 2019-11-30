package land.jay.floristics.compat;

import org.bukkit.Location;
import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import land.jay.floristics.Floristics;

/** Wrapper class for RedProtect API calls. */
public class RedProtectWrapper {
    
    public static void onEnable() {
        
        Floristics.info("RedProtect is present, adding flag to API.");
        RedProtect.get().getAPI().addFlag("floristics", false, false);
    }
    
    public static boolean canGrow(Location location) {
        
        Region region = RedProtect.get().getAPI().getRegion(location);        
        return region == null || region.getFlagBool("floristics");
    }
}
