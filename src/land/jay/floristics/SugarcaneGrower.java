package land.jay.floristics;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/** Grower for sugarcane's extra requirements. */
public class SugarcaneGrower extends BushGrower {

    public SugarcaneGrower(double density, double chance) {

        super(Material.SUGAR_CANE, false, density, SurfaceType.DIRT, chance);
    }

    @Override
    protected SearchResult search(World world, Biome biome, int searchX, int searchZ) {
     
        SearchResult result = super.search(world, biome, searchX, searchZ);

        if (result == SearchResult.VALID) {
            
            Block base = world.getHighestBlockAt(searchX, searchZ);
            Material baseMaterial = base.getType();
            
            while (this.searchDown(baseMaterial)) {
                
                base = base.getRelative(BlockFace.DOWN);
                baseMaterial = base.getType();
            }
            
            if (base.getRelative(BlockFace.NORTH).getType() != Material.WATER &&
                    base.getRelative(BlockFace.EAST).getType() != Material.WATER &&
                    base.getRelative(BlockFace.SOUTH).getType() != Material.WATER &&
                    base.getRelative(BlockFace.WEST).getType() != Material.WATER) {
                
                return SearchResult.INVALID;
            }
        }
        
        return result;
    }
}
