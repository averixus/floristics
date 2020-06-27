/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics.plants;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bamboo.Leaves;
import org.bukkit.entity.EntityType;
import com.google.common.collect.Sets;
import land.jay.floristics.Floristics;

/** Grower for tree with bee nest. */
public class BeeGrower extends PlantGrower {

    private static final BlockFace[] FACES = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    private static final Set<Material> SEARCH_DOWN = Sets.newHashSet(Material.AIR, Material.BIRCH_LEAVES, Material.OAK_LEAVES);
    
    public BeeGrower(double chance) {

        super(Material.BEE_NEST, SurfaceType.BEE, chance);
    }

    @Override
    public void grow(World world, int x, int z) {

        if (!this.isPresent(world, x, z) || !this.hasSpace(world, x, z)) {
            return;
        }
        
        this.placeHive(world, x, z);
    } 
    
    private void placeHive(World world, int x, int z) {

        BlockFace direction = FACES[Floristics.RAND.nextInt(FACES.length)];
        Block placeBlock = world.getHighestBlockAt(x, z);
        Material placeMaterial = placeBlock.getType();
        Block supportBlock = placeBlock.getRelative(direction.getOppositeFace());
        Material supportMaterial = supportBlock.getType();

        while (SEARCH_DOWN.contains(supportMaterial) || (this.surface.isValid(supportMaterial) && placeMaterial != Material.AIR)) {
            supportBlock = supportBlock.getRelative(BlockFace.DOWN);
            supportMaterial = supportBlock.getType();
            placeBlock = supportBlock.getRelative(direction);
            placeMaterial = placeBlock.getType();
        }
        
        if (this.surface.isValid(supportMaterial) && placeMaterial == Material.AIR && Floristics.hasPermission(placeBlock.getLocation())) {
            placeBlock.setType(Material.BEE_NEST);
            Beehive nestData = ((Beehive) placeBlock.getBlockData());
            nestData.setFacing(direction);
            placeBlock.setBlockData(nestData);
            int bees = 1 + Floristics.RAND.nextInt(3);
            for (int i = 0; i < bees; i++) {
                world.spawnEntity(placeBlock.getLocation(), EntityType.BEE);
            }
        }
    }
    
    private boolean hasSpace(World world, int x, int z) {

        for (int xOffset = -8; xOffset <= 8; xOffset++) {
            for (int zOffset = -8; zOffset <= 8; zOffset++) {
                int xPos = x + xOffset;
                int zPos = z + zOffset;
                Block spaceBlock = world.getHighestBlockAt(xPos, zPos);
                Material spaceMaterial = spaceBlock.getType();
                
                while (spaceMaterial == Material.AIR || spaceMaterial.data == Leaves.class) {
                    spaceBlock = spaceBlock.getRelative(BlockFace.DOWN);
                    spaceMaterial = spaceBlock.getType();
                }
                
                if (spaceMaterial.data == Beehive.class) {
                    return false;
                }
            }
        }
        return true;
    }
}
