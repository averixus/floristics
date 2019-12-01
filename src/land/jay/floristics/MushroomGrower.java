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
    protected SearchResult search(World world, Biome biome, int searchX, int searchZ) {
        
        SearchResult result = super.search(world, biome, searchX, searchZ);
        
        if (result == SearchResult.VALID && this.dark) {
            
            Block base = world.getHighestBlockAt(searchX, searchZ);
            Material baseMaterial = base.getType();
            
            while (this.searchDown(baseMaterial)) {
                base = base.getRelative(BlockFace.DOWN);
                baseMaterial = base.getType();
            }
            
            if (base.getRelative(BlockFace.UP).getLightLevel() > 12) {
                return SearchResult.INVALID;
            }
        }
        
        return result;
    }
}
