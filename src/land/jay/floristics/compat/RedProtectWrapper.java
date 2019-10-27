package land.jay.floristics.compat;

import org.bukkit.Location;
import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;

/** Wrapper class for RedProtect API calls. */
public class RedProtectWrapper {
    
    public static void onEnable() {
        
        RedProtect.get().getAPI().addFlag("floristics", false, false);
        System.out.println("added flag");
    }
    
    public static boolean canGrow(Location location) {
        
        Region region = RedProtect.get().getAPI().getRegion(location);
        return region != null && region.getFlagBool("floristics");
    }
}
