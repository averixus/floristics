package land.jay.floristics;

import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import com.google.common.collect.Sets;

/** Handles growth of a particular plant. */
public abstract class PlantGrower {
    
    /** Surfaces this plant can grow on. */
    protected final SurfaceType surface;
    /** Chance of this plant being present in each chunk. */
    protected final double chance;
    
    public PlantGrower(SurfaceType surface, double chance) {
        
        this.surface = surface;
        this.chance = chance;
    }
    
    /** @return Whether this plant should grow in this chunk. */
    protected boolean isPresent(World world, int x, int z) {

        int chunkX = x >> 4; //chunk.getX();
        int chunkZ = z >> 4; //chunk.getZ();
        Random random = new Random(world.getSeed() +
                (this.getBlockSeed() * chunkX * 0x4c1906) + (chunkX * 0x5ac0db) + 
                (this.getBlockSeed() * 0x4307a7L) + (chunkZ * 0x5f24f) ^ 0x3ad8025f); 
        return  random.nextFloat() < this.chance;
    }
    
    /** @return Constant block-based number for random seeds only. */
    protected abstract int getBlockSeed();

    /** Attempts to grow this plant. */
    public abstract void grow(World world, int x, int z);
    
    /** Types of growing surface. */
    protected enum SurfaceType {
        
        DIRT(Material.DIRT, Material.GRASS_BLOCK),
        FUNGI(Material.DIRT, Material.GRASS_BLOCK, Material.PODZOL, Material.MYCELIUM),
        SEAWEED(Material.SAND, Material.DIRT, Material.GRAVEL, Material.CLAY),
        JUNGLE(Material.DIRT, Material.COARSE_DIRT, Material.GRASS_BLOCK, Material.SAND, Material.PODZOL, Material.GRAVEL),
        DEAD(Material.SAND, Material.RED_SAND, Material.PODZOL, Material.DIRT, Material.COARSE_DIRT, Material.TERRACOTTA, Material.BLACK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.WHITE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.LIME_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.RED_TERRACOTTA, Material.PINK_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BROWN_TERRACOTTA),
        SAND(Material.SAND, Material.RED_SAND),
        WATER(Material.WATER),
        END(Material.END_STONE);
        
        private final Set<Material> surfaces;
        
        private SurfaceType(Material... surfaces) {
            
            this.surfaces = Sets.newHashSet(surfaces);
        }
        
        public boolean isValid(Material material) {
            
            return this.surfaces.contains(material);
        }
    }
}
