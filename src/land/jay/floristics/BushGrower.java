/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics;

import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import com.google.common.collect.Sets;

/** Grower for simple plants. */
public class BushGrower extends PlantGrower {
    
    /** Materials to search down through for air plant locations. */
    protected static final Set<Material> SEARCH_DOWN_AIR = Sets.newHashSet(Material.AIR, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES, Material.JUNGLE_LEAVES, Material.OAK_LEAVES, Material.SPRUCE_LEAVES);
    /** Materials to search down through for water plant locations. */
    protected static final Set<Material> SEARCH_DOWN_WATER = Sets.newHashSet(Material.AIR, Material.WATER, Material.ICE, Material.BLUE_ICE, Material.FROSTED_ICE, Material.PACKED_ICE);
    
    /** Constant to define searched y coords where the plant is already present. */
    protected static final int PRESENT = -1;
    /** Constant to define searched y coords where the plant is not valid. */
    protected static final int INVALID = -2;
    
    /** Whether it's a double height bush. */
    protected final boolean isDouble;
    /** Whether it grows in water. */
    protected final boolean isWater;
    /** Default maximum density of this bush. */
    protected final double density;
    /** Radius to check density in. */
    protected final int radius;

    BushGrower(Material material, boolean isWater, double density, SurfaceType surface, double chance) {
        
        super(material, surface, chance);
        this.isDouble = material.data == Bisected.class;
        this.isWater = isWater;
        this.density = density;
        this.radius = (int) ((1.0 - this.density) * 8);
    }
    
    @Override
    public void grow(World world,  int x, int z) {
        if (this.isPresent(world, x, z) && (this.calcDensity(world, x, z) < this.getLocalDensity(world, x, z))) {
            this.placeBush(world, x, z);
        }
    }
    
    /** @return A consistent local density from 0.5 to 1.5 of the default density. */
    protected double getLocalDensity(World world, int x, int z) {

        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        Random random = new Random(world.getSeed() +
               (this.material.ordinal() * chunkX * 0x4c1906) + (chunkX * 0x5ac0db) + 
                (this.material.ordinal() * 0x4307a7L) + (chunkZ * 0x5f24f) ^ 0x3ad8025f); 
        return (0.5 + random.nextDouble()) * this.density;
    }
    
    /** @return Whether to keep searching down past this material. */
    protected boolean searchDown(Material checkMaterial) {
        return this.isWater ? SEARCH_DOWN_WATER.contains(checkMaterial) :
            SEARCH_DOWN_AIR.contains(checkMaterial);
    }
    
    /** @return Whether this material is a valid space for the bush. */
    protected boolean isSpace(Material checkMaterial) {
        return this.isWater ? checkMaterial == Material.WATER : checkMaterial == Material.AIR;
    }
    
    /** Attempts to place this bush at the location. */
    protected void placeBush(World world, int x, int z) {
        
        int targetY = this.search(world, world.getBiome(x, z), x, z);
        
        if (targetY < 0) {
            return;
        }
        
        Block spaceBlock = world.getBlockAt(x, targetY, z);
        
        if (this.isDouble) {
            Block topBlock = spaceBlock.getRelative(BlockFace.UP);
            if (Floristics.hasPermission(spaceBlock.getLocation()) && Floristics.hasPermission(topBlock.getLocation())) {
                spaceBlock.setType(this.material, false);
                topBlock.setType(this.material, false);
                Bisected spaceData = (Bisected) spaceBlock.getBlockData();
                Bisected topData = (Bisected) topBlock.getBlockData();
                spaceData.setHalf(Half.BOTTOM);
                topData.setHalf(Half.TOP);
                spaceBlock.setBlockData(spaceData);
                topBlock.setBlockData(topData);
            }
        } else {
            if (Floristics.hasPermission(spaceBlock.getLocation())) {
                spaceBlock.setType(this.material, false);
            }
        }
    }
        
    /** @return The target y coordinate for this bush, or -1 if it's present and -2 if invalid. */
    protected int search(World world, Biome biome, int searchX, int searchZ) {

        if (biome != world.getBiome(searchX, searchZ)) {
            return INVALID;
        }
        
        Block surfaceBlock = world.getHighestBlockAt(searchX, searchZ);
        Material surfaceMaterial = surfaceBlock.getType();
        
        while (this.searchDown(surfaceMaterial)) {
            surfaceBlock = surfaceBlock.getRelative(BlockFace.DOWN);
            surfaceMaterial = surfaceBlock.getType();
        }
        
        if (surfaceMaterial == this.material) {
            return PRESENT;
        }
        
        if (this.surface.isValid(surfaceMaterial)) {
            Block spaceBlock = surfaceBlock.getRelative(BlockFace.UP);
            Material spaceMaterial = spaceBlock.getType();
            boolean hasSpace = this.isSpace(spaceMaterial);
            
            if (this.isDouble) {
                Block topBlock = spaceBlock.getRelative(BlockFace.UP);
                Material topMaterial = topBlock.getType();
                hasSpace = hasSpace && (this.isSpace(topMaterial));
            }
            
            if (hasSpace) {
                return spaceBlock.getZ();
            }
        }
        
        return INVALID;
    }
    
    /** @return The density of this plant in the radius. */
    protected double calcDensity(World world, int x, int z) {

        double validCount = 0;
        double foundCount = 0;
        
        for (int xCheck = x - this.radius; xCheck < x + this.radius; xCheck++) {
            for (int zCheck = z - this.radius; zCheck < z + this.radius; zCheck++) {
 
                int result = this.search(world, world.getBiome(x, z), xCheck, zCheck);
                
                if (result == PRESENT) {
                    foundCount++;
                    validCount++;
                } else if (result > 0) {
                    validCount++;
                }
            }
        }
        
        return foundCount / validCount;
    }
}
