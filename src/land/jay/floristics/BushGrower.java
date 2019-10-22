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
    
    /** Materials to search down through for air plants. */
    protected static final Set<Material> SEARCH_DOWN_AIR = Sets.newHashSet(Material.AIR, Material.ACACIA_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES, Material.JUNGLE_LEAVES, Material.OAK_LEAVES, Material.SPRUCE_LEAVES);
    /** Materials to search down through for water plants. */
    protected static final Set<Material> SEARCH_DOWN_WATER = Sets.newHashSet(Material.AIR, Material.WATER, Material.ICE, Material.BLUE_ICE, Material.FROSTED_ICE, Material.PACKED_ICE);
    
    /** Utility to describe the state of a particular location. */
    protected enum SearchResult { PRESENT, VALID, INVALID; }
    
    /** The bush to grow. */
    protected final Material material;
    /** Whether it's a double height bush. */
    protected final boolean isDouble;
    /** Whether it grows in water. */
    protected final boolean isWater;
    /** Default maximum density of this bush. */
    protected final double density;
    /** Radius to check density in. */
    protected final int radius;

    BushGrower(Material material, boolean isWater, double density, SurfaceType surface, double chance) {
        
        super(surface, chance);
        this.material = material;
        this.isDouble = material.data == Bisected.class;
        this.isWater = isWater;
        this.density = density;
        this.radius = (int) ((1.0 - this.density) * 8);
    }
    
    @Override
    protected int getBlockSeed() {
        
        return this.material.ordinal();
    }
    
    @Override
    public void grow(World world,  int x, int z) {

        if (this.isPresent(world, x, z) &&
                (this.checkDensity(world, x, z) < this.getLocalDensity(world, x, z))) {

            this.placeBush(world, x, z);
        }
    }
    
    /** @return A consistent local density from 0.5 to 1.5 of the default density. */
    protected double getLocalDensity(World world, int x, int z) {

        int chunkX = x >> 4; //chunk.getX();
        int chunkZ = z >> 4; //chunk.getZ();
        Random random = new Random(world.getSeed() +
               (this.getBlockSeed() * chunkX * 0x4c1906) + (chunkX * 0x5ac0db) + 
                (this.getBlockSeed() * 0x4307a7L) + (chunkZ * 0x5f24f) ^ 0x3ad8025f); 
        return (0.5 + random.nextDouble()) * this.density;
    }
    
    /** @return Whether to keep searching down past this material. */
    protected boolean searchDown(Material checkMaterial) {
        
        if (this.isWater) {
            
            return SEARCH_DOWN_WATER.contains(checkMaterial);
            
        } else {
            
            return SEARCH_DOWN_AIR.contains(checkMaterial);
        }
    }
    
    /** @return Whether this material is a valid space for the bush. */
    protected boolean isSpace(Material checkMaterial) {
        
        if (this.isWater) {
            
            return checkMaterial == Material.WATER;
            
        } else {
            
            return checkMaterial == Material.AIR;
        }
    }
    
    /** Attempts to place this bush at the location. */
    protected void placeBush(World world, int x, int z) {

        if (this.search(world, world.getBiome(x, z), x, z) != SearchResult.VALID) {
            
            return;
        }
        
        Block surfaceBlock = world.getHighestBlockAt(x, z);
        Material surfaceMaterial = surfaceBlock.getType();
        
        while (this.searchDown(surfaceMaterial)) {
            
            surfaceBlock = surfaceBlock.getRelative(BlockFace.DOWN);
            surfaceMaterial = surfaceBlock.getType();
        }
        
        Block spaceBlock = surfaceBlock.getRelative(BlockFace.UP);
        spaceBlock.setType(this.material, false);
        
        if (this.isDouble) {
            
            Block topBlock = spaceBlock.getRelative(BlockFace.UP);
            topBlock.setType(this.material, false);
            Bisected spaceData = (Bisected) spaceBlock.getBlockData();
            Bisected topData = (Bisected) topBlock.getBlockData();
            spaceData.setHalf(Half.BOTTOM);
            topData.setHalf(Half.TOP);
            spaceBlock.setBlockData(spaceData);
            topBlock.setBlockData(topData);
        }
    }
        
    /** @return The state of the location for this bush. */
    protected SearchResult search(World world, Biome biome, int searchX, int searchZ) {

        // Don't search other biomes
        if (biome != world.getBiome(searchX, searchZ)) {
            
            return SearchResult.INVALID;
        }
        
        Block surfaceBlock = world.getHighestBlockAt(searchX, searchZ);
        Material surfaceMaterial = surfaceBlock.getType();
        
        while (this.searchDown(surfaceMaterial)) {
            
            surfaceBlock = surfaceBlock.getRelative(BlockFace.DOWN);
            surfaceMaterial = surfaceBlock.getType();
        }
        
        if (surfaceMaterial == this.material) {
            
            return SearchResult.PRESENT;
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
                
                return SearchResult.VALID;
            }
        }
        
        return SearchResult.INVALID;
    }
    
    /** @return The density of this plant in the radius. */
    protected double checkDensity(World world, int x, int z) {

        double validCount = 0;
        double foundCount = 0;
        
        for (int xCheck = x - this.radius; xCheck < x + this.radius; xCheck++) {
            
            for (int zCheck = z - this.radius; zCheck < z + this.radius; zCheck++) {
 
                SearchResult result = this.search(world, world.getBiome(x, z), xCheck, zCheck);
                
                switch (result) {
                    
                    case PRESENT:
                        foundCount++;
                        validCount++;
                        break;
                    case VALID:
                        validCount++;
                        break;
                    case INVALID: default:
                        break;
                }
            }
        }
        
        return foundCount / validCount;
    }
}
