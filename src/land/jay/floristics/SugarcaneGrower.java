/** Copyright (C) 2019 Jay Avery */
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
    protected int search(World world, Biome biome, int searchX, int searchZ) {
     
        int targetY = super.search(world, biome, searchX, searchZ);

        if (targetY > 0) {
            
            Block base = world.getBlockAt(searchX, targetY - 1, searchZ);
            
            if (base.getRelative(BlockFace.NORTH).getType() != Material.WATER &&
                    base.getRelative(BlockFace.EAST).getType() != Material.WATER &&
                    base.getRelative(BlockFace.SOUTH).getType() != Material.WATER &&
                    base.getRelative(BlockFace.WEST).getType() != Material.WATER) {
                return INVALID;
            }
        }
        
        return targetY;
    }
}
