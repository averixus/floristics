/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics.plants;

import java.util.Random;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.World;
import com.google.common.collect.Sets;

/** Handles growth of a particular plant. */
public abstract class PlantGrower {
    
    /** Material of this plant. */
    public final Material material;
    /** Surfaces this plant can grow on. */
    protected final SurfaceType surface;
    /** Chance of this plant being present in each chunk. */
    protected final double chance;
        
    public PlantGrower(Material material, SurfaceType surface, double chance) {

        this.material = material;
        this.surface = surface;
        this.chance = chance;
    }
    
    /** @return Whether this plant should grow in this chunk. */
    protected boolean isPresent(World world, int x, int z) {

        if (this.chance == 1) {
            return true;
        }
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        Random random = new Random(world.getSeed() +
                (this.material.ordinal() * chunkX * 0x4c1906) + (chunkX * 0x5ac0db) + 
                (this.material.ordinal() * 0x4307a7L) + (chunkZ * 0x5f24f) ^ 0x3ad8025f); 
        boolean result = random.nextFloat() < this.chance;
        return result;
    }

    /** Attempts to grow this plant. */
    public abstract void grow(World world, int x, int z);
    
    /** Types of growing surface. */
    public enum SurfaceType {
        
        DIRT(Material.DIRT, Material.GRASS_BLOCK),
        FUNGI(Material.DIRT, Material.GRASS_BLOCK, Material.PODZOL, Material.MYCELIUM, Material.NETHERRACK),
        DARK_FUNGI(Material.DIRT, Material.GRASS_BLOCK, Material.PODZOL, Material.MYCELIUM, Material.NETHERRACK, Material.STONE, Material.DIORITE, Material.ANDESITE, Material.GRANITE, Material.GRAVEL),
        SEAWEED(Material.SAND, Material.DIRT, Material.GRAVEL, Material.CLAY),
        JUNGLE(Material.DIRT, Material.COARSE_DIRT, Material.GRASS_BLOCK, Material.SAND, Material.PODZOL, Material.GRAVEL),
        DEAD(Material.SAND, Material.RED_SAND, Material.PODZOL, Material.DIRT, Material.COARSE_DIRT, Material.TERRACOTTA, Material.BLACK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.WHITE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.LIME_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.RED_TERRACOTTA, Material.PINK_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BROWN_TERRACOTTA),
        SAND(Material.SAND, Material.RED_SAND),
        WATER(Material.WATER),
        END(Material.END_STONE),
        BEE(Material.OAK_LOG, Material.BIRCH_LOG);
        
        /** Valid materials for this type of surface. */
        private final Set<Material> surfaces;
        
        private SurfaceType(Material... surfaces) {
            this.surfaces = Sets.newHashSet(surfaces);
        }
        
        /** @return Whether the given material is valid for this surface. */
        public boolean isValid(Material material) {
            return this.surfaces.contains(material);
        }
    }
}
