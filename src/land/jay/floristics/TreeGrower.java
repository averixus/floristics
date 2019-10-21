package land.jay.floristics;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;

/** Grower for tree structures. */
public class TreeGrower extends PlantGrower {
    
    /** Type of tree to grow. */
    protected final TreeType tree;
    /** Material to use for density and distribution seeds. */
    protected final Material seedBlock;
    /** Empty radius required to grow. */
    protected final int radius;

    public TreeGrower(TreeType tree, Material seedBlock, int radius, SurfaceType surface, double chance) {

        super(surface, chance);
        this.tree = tree;
        this.seedBlock = seedBlock;
        this.radius = radius;
    }

    @Override
    protected int getBlockSeed() {

        return this.seedBlock.ordinal();
    }

    @Override
    public void grow(World world, int x, int z) {

        if (!this.isPresent(world, x, z) || !this.hasSpace(world, x, z)) {
            
            return;
        }

        this.placeTree(world, x, z);
    }
    
    /** Attempts to place this tree at the location. */
    protected void placeTree(World world, int x, int z) {

        Block placeBlock = world.getHighestBlockAt(x, z);
        Block surfaceBlock = placeBlock.getRelative(BlockFace.DOWN);
        
        if (!this.surface.isValid(surfaceBlock.getType())) {
            
            return;
        }

        world.generateTree(placeBlock.getLocation(), this.tree);
    }
    
    /** @return Whether the required radius is clear at the location. */
    protected boolean hasSpace(World world, int x, int z) {

        for (int xOffset = - this.radius; xOffset <= this.radius; xOffset++) {
            
            for (int zOffset = - this.radius; zOffset <= this.radius; zOffset++) {
                
                // Check in a taxicab circle only
                if (Math.abs(xOffset) + Math.abs(zOffset) > this.radius) {
                    
                    continue;
                }
                
                int xPos = x + xOffset;
                int zPos = z + zOffset;
                Block downBlock = world.getHighestBlockAt(xPos, zPos).getRelative(BlockFace.DOWN);
                
                // Do not generate if there are trees nearby
                if (downBlock.getType().data == Leaves.class) {
                    
                    return false;
                }
            }
        }
        
        return true;
    }
}
