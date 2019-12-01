/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/** Grower for cactus' extra requirements. */
public class CactusGrower extends BushGrower {

    CactusGrower(double density, double chance) {

        super(Material.CACTUS, false, density, SurfaceType.SAND, chance);
    }

    @Override
    protected int search(World world, Biome biome, int searchX, int searchZ) {
        
        int targetY = super.search(world, biome, searchX, searchZ);

        if (targetY > 0) {
            
            Block space = world.getBlockAt(searchX, targetY, searchZ);
            Block base = space.getRelative(BlockFace.DOWN);
            
            boolean isDry = base.getRelative(BlockFace.NORTH).getType() != Material.WATER &&
                    base.getRelative(BlockFace.EAST).getType() != Material.WATER &&
                    base.getRelative(BlockFace.SOUTH).getType() != Material.WATER &&
                    base.getRelative(BlockFace.WEST).getType() != Material.WATER;
            boolean hasSpace = this.isSpace(space.getRelative(BlockFace.NORTH).getType()) &&
                    this.isSpace(space.getRelative(BlockFace.EAST).getType()) &&
                    this.isSpace(space.getRelative(BlockFace.SOUTH).getType()) &&
                    this.isSpace(space.getRelative(BlockFace.WEST).getType());
            
            if (!isDry || !hasSpace) {
                return INVALID;
            }
        }
        
        return targetY;
    }
}
