package land.jay.floristics;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/** Grower for mushrooms' extra requirements. */
public class MushroomGrower extends BushGrower {
    
    /** Whether this grower should check for light levels. */
    private final boolean dark;

    MushroomGrower(Material material, double density, double chance, boolean dark) {

        super(material, false, density, dark ? SurfaceType.DARK_FUNGI : SurfaceType.FUNGI, chance);
        this.dark = dark;
    }

    @Override
    protected int search(World world, Biome biome, int searchX, int searchZ) {
        
        int targetY = super.search(world, biome, searchX, searchZ);
        
        if (targetY > 0 && this.dark &&
                world.getBlockAt(searchX, targetY, searchZ).getLightLevel() > 12) {
                return INVALID;
        }
        
        return targetY;
    }
}
