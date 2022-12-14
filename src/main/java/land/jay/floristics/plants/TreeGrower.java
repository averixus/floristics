/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics.plants;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import com.google.common.collect.Sets;
import land.jay.floristics.Floristics;

/** Grower for tree structures. */
public class TreeGrower extends PlantGrower {
    
    /** Materials trees can grow through. */
    protected static final Set<Material> SEARCH_DOWN = Sets.newHashSet(Material.AIR, Material.ALLIUM, Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.COCOA, Material.CORNFLOWER, Material.DANDELION, Material.DEAD_BUSH, Material.FERN, Material.GRASS, Material.LARGE_FERN, Material.LILY_OF_THE_VALLEY, Material.OXEYE_DAISY, Material.PEONY, Material.PINK_TULIP, Material.RED_TULIP, Material.SUNFLOWER, Material.SUGAR_CANE, Material.SWEET_BERRY_BUSH, Material.TALL_GRASS, Material.WHITE_TULIP, Material.ORANGE_TULIP);    
    
    /** Type of tree to grow. */
    protected final TreeType tree;
    /** Empty radius required to grow. */
    protected final int radius;

    public TreeGrower(TreeType tree, Material material, int radius, SurfaceType surface, double chance) {

        super(material, surface, chance);
        this.tree = tree;
        this.radius = radius;
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

        Block placeBlock = world.getHighestBlockAt(x, z).getRelative(BlockFace.UP);

        Block surfaceBlock = placeBlock.getRelative(BlockFace.DOWN);
        if (surfaceBlock.isLiquid()) {
            return;
        }

        Material surfaceMaterial = surfaceBlock.getType();
        
        while (SEARCH_DOWN.contains(surfaceMaterial)) {
            surfaceBlock = surfaceBlock.getRelative(BlockFace.DOWN);
            surfaceMaterial = surfaceBlock.getType();
        }
        
        if (!this.surface.isValid(surfaceBlock.getType()) ||
                !Floristics.hasPermission(placeBlock.getLocation())) {
            return;
        }

        world.generateTree(placeBlock.getLocation(), this.tree);
    }
    
    /** @return Whether the required radius is clear at the location. */
    protected boolean hasSpace(World world, int x, int z) {

        for (int xOffset = - this.radius; xOffset <= this.radius; xOffset++) {
            for (int zOffset = - this.radius; zOffset <= this.radius; zOffset++) {
                if (Math.abs(xOffset) + Math.abs(zOffset) > this.radius) {
                    continue;
                }
                
                int xPos = x + xOffset;
                int zPos = z + zOffset;
                Block spaceBlock = world.getHighestBlockAt(xPos, zPos);
                Material spaceMaterial = spaceBlock.getType();
                Block surfaceBlock = spaceBlock.getRelative(BlockFace.DOWN);
                Material surfaceMaterial = surfaceBlock.getType();
                
                if (surfaceMaterial.data == Leaves.class || surfaceMaterial == Material.BROWN_MUSHROOM_BLOCK || surfaceMaterial == Material.RED_MUSHROOM_BLOCK || spaceMaterial == Material.CHORUS_PLANT || spaceMaterial == Material.CHORUS_FLOWER) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
