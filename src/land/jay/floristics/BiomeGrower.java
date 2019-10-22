/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import com.google.common.collect.Lists;
import land.jay.floristics.PlantGrower.SurfaceType;

/** Handler for growth depending on biome. */
public enum BiomeGrower {
    
    PLAINS {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.3, SurfaceType.DIRT, 1));
        this.plants.add(new BushGrower(Material.TALL_GRASS, false, 0.1, SurfaceType.DIRT, 1));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.AZURE_BLUET, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.ORANGE_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.PINK_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.RED_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.WHITE_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.OXEYE_DAISY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.CORNFLOWER, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.02));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    SUNFLOWER_PLAINS {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.3, SurfaceType.DIRT, 1));
        this.plants.add(new BushGrower(Material.TALL_GRASS, false, 0.1, SurfaceType.DIRT, 1));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.AZURE_BLUET, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.ORANGE_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.PINK_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.RED_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.WHITE_TULIP, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.OXEYE_DAISY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.CORNFLOWER, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.02));
        this.plants.add(new SugarcaneGrower(0.4, 0.05)); 
        this.plants.add(new BushGrower(Material.SUNFLOWER, false, 0.1, SurfaceType.DIRT, 0.2));
    }},
    COOL_OCEAN {{
        this.plants.add(new BushGrower(Material.SEAGRASS, true, 0.1, SurfaceType.SEAWEED, 0.1));
        this.plants.add(new BushGrower(Material.TALL_SEAGRASS, true, 0.05, SurfaceType.SEAWEED, 0.1));
        this.plants.add(new BushGrower(Material.KELP, true, 0.15, SurfaceType.SEAWEED, 0.1));
    }},
    WARM_OCEAN {{
        this.plants.add(new BushGrower(Material.SEAGRASS, true, 0.1, SurfaceType.SEAWEED, 0.1));
        this.plants.add(new BushGrower(Material.TALL_SEAGRASS, true, 0.05, SurfaceType.SEAWEED, 0.1));  
    }},
    DESERT {{
        this.plants.add(new BushGrower(Material.DEAD_BUSH, false, 0.02, SurfaceType.DEAD, 0.1));
        this.plants.add(new CactusGrower(0.02, 0.1));
        this.plants.add(new SugarcaneGrower(0.4, 0.25));
    }},
    MOUNTAINS {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.13));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new TreeGrower(TreeType.REDWOOD, Material.SPRUCE_LOG, 7, SurfaceType.DIRT, 0.05));
    }},
    WOODED_MOUNTAINS {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.13));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 5, SurfaceType.DIRT, 0.2));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 5, SurfaceType.DIRT, 0.2));
        this.plants.add(new TreeGrower(TreeType.REDWOOD, Material.SPRUCE_LOG, 5, SurfaceType.DIRT, 0.2));    
    }},
    FOREST {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.LILAC, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.ROSE_BUSH, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.PEONY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.LILY_OF_THE_VALLEY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG,  5, SurfaceType.DIRT, 0.95));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 5, SurfaceType.DIRT, 0.1));
        this.plants.add(new TreeGrower(TreeType.BIRCH, Material.BIRCH_LOG, 5, SurfaceType.DIRT, 0.95));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    TAIGA {{
        this.plants.add(new TreeGrower(TreeType.REDWOOD, Material.SPRUCE_LOG, 5, SurfaceType.DIRT, 0.95));
        this.plants.add(new BushGrower(Material.FERN, false, 0.15, SurfaceType.DIRT, 0.15));
        this.plants.add(new BushGrower(Material.LARGE_FERN, false, 0.05, SurfaceType.DIRT, 0.15));
        this.plants.add(new BushGrower(Material.GRASS, false, 0.05, SurfaceType.DIRT, 0.1));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.SWEET_BERRY_BUSH, false, 0.02, SurfaceType.DIRT, 0.08));
        
    }},
    SNOWY_TAIGA {{
        this.plants.add(new TreeGrower(TreeType.REDWOOD, Material.SPRUCE_LOG, 5, SurfaceType.DIRT, 0.95));
        this.plants.add(new BushGrower(Material.FERN, false, 0.1, SurfaceType.DIRT, 0.05));
        this.plants.add(new BushGrower(Material.LARGE_FERN, false, 0.02, SurfaceType.DIRT, 0.05));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.005));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.005));
        this.plants.add(new BushGrower(Material.SWEET_BERRY_BUSH, false, 0.02, SurfaceType.DIRT, 0.03));
    }},
    MEGA_TAIGA {{
        this.plants.add(new TreeGrower(TreeType.MEGA_REDWOOD, Material.SPRUCE_LOG, 7, SurfaceType.DIRT, 0.95));
        this.plants.add(new TreeGrower(TreeType.REDWOOD, Material.SPRUCE_LOG, 5, SurfaceType.DIRT, 0.4));
        this.plants.add(new BushGrower(Material.FERN, false, 0.15, SurfaceType.DIRT, 0.15));
        this.plants.add(new BushGrower(Material.LARGE_FERN, false, 0.05, SurfaceType.DIRT, 0.15));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DEAD_BUSH, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.BROWN_MUSHROOM, false, 0.02, SurfaceType.FUNGI, 0.01));
        this.plants.add(new BushGrower(Material.RED_MUSHROOM, false, 0.02, SurfaceType.FUNGI, 0.01));
    }},
    SWAMP {{
        this.plants.add(new TreeGrower(TreeType.SWAMP, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.5));
        this.plants.add(new BushGrower(Material.GRASS, false, 0.2, SurfaceType.DIRT, 0.5));
        this.plants.add(new TreeGrower(TreeType.BROWN_MUSHROOM, Material.BROWN_MUSHROOM_BLOCK, 7, SurfaceType.FUNGI, 0.001));
        this.plants.add(new TreeGrower(TreeType.RED_MUSHROOM, Material.RED_MUSHROOM_BLOCK, 7, SurfaceType.FUNGI, 0.001));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
        this.plants.add(new BushGrower(Material.SEAGRASS, true, 0.1, SurfaceType.SEAWEED, 0.1));
        this.plants.add(new BushGrower(Material.TALL_SEAGRASS, true, 0.05, SurfaceType.SEAWEED, 0.1));  
        this.plants.add(new BushGrower(Material.BLUE_ORCHID, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.LILY_PAD, false, 0.02, SurfaceType.WATER, 0.01));
        this.plants.add(new BushGrower(Material.BROWN_MUSHROOM, false, 0.02, SurfaceType.FUNGI, 0.01));
        this.plants.add(new BushGrower(Material.RED_MUSHROOM, false, 0.02, SurfaceType.FUNGI, 0.01));
    }},
    RIVER {{
        this.plants.add(new SugarcaneGrower(0.4, 0.005));
        this.plants.add(new BushGrower(Material.SEAGRASS, true, 0.1, SurfaceType.SEAWEED, 0.1));
        this.plants.add(new BushGrower(Material.TALL_SEAGRASS, true, 0.05, SurfaceType.SEAWEED, 0.1));  
    }},
    END {{
        this.plants.add(new TreeGrower(TreeType.CHORUS_PLANT, Material.CHORUS_PLANT, 7, SurfaceType.END, 0.5)); 
    }},
    TUNDRA {{
        this.plants.add(new TreeGrower(TreeType.REDWOOD, Material.SPRUCE_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new BushGrower(Material.GRASS, false, 0.05, SurfaceType.DIRT, 0.05));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
    }},
    MUSHROOM {{
        this.plants.add(new TreeGrower(TreeType.BROWN_MUSHROOM, Material.BROWN_MUSHROOM_BLOCK, 7, SurfaceType.FUNGI, 0.15));
        this.plants.add(new TreeGrower(TreeType.RED_MUSHROOM, Material.RED_MUSHROOM_BLOCK, 7, SurfaceType.FUNGI, 0.15));
        this.plants.add(new BushGrower(Material.BROWN_MUSHROOM, false, 0.02, SurfaceType.FUNGI, 0.1));
        this.plants.add(new BushGrower(Material.RED_MUSHROOM, false, 0.02, SurfaceType.FUNGI, 0.1));
    }},
    JUNGLE {{
        this.plants.add(new TreeGrower(TreeType.JUNGLE, Material.JUNGLE_LOG, 7, SurfaceType.DIRT, 1));
        this.plants.add(new TreeGrower(TreeType.SMALL_JUNGLE, Material.JUNGLE_LOG, 7, SurfaceType.DIRT, 1));
        this.plants.add(new TreeGrower(TreeType.JUNGLE_BUSH, Material.JUNGLE_LOG, 5, SurfaceType.DIRT, 1));
        this.plants.add(new TreeGrower(TreeType.COCOA_TREE, Material.COCOA, 5, SurfaceType.DIRT, 0.005));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.1));
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.BAMBOO, false, 0.001, SurfaceType.DIRT, 0.001));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    JUNGLE_EDGE {{
        this.plants.add(new TreeGrower(TreeType.SMALL_JUNGLE, Material.JUNGLE_LOG, 7, SurfaceType.DIRT, 0.2));
        this.plants.add(new TreeGrower(TreeType.COCOA_TREE, Material.COCOA, 5, SurfaceType.DIRT, 0.005));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.02));
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    BAMBOO_JUNGLE {{
        this.plants.add(new TreeGrower(TreeType.JUNGLE, Material.JUNGLE_LOG, 7, SurfaceType.DIRT, 0.5));
        this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.BAMBOO, false, 0.2, SurfaceType.DIRT, 0.6));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    SAVANNA {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.3, SurfaceType.DIRT, 1));
        this.plants.add(new BushGrower(Material.TALL_GRASS, false, 0.1, SurfaceType.DIRT, 1));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.05, SurfaceType.DIRT, 0.05));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.05, SurfaceType.DIRT, 0.05));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.05));
        this.plants.add(new TreeGrower(TreeType.ACACIA, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.15));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    BADLANDS {{
        this.plants.add(new BushGrower(Material.DEAD_BUSH, false, 0.02, SurfaceType.DEAD, 0.1));
        this.plants.add(new CactusGrower(0.02, 0.1));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    WOODED_BADLANDS {{
        this.plants.add(new BushGrower(Material.DEAD_BUSH, false, 0.02, SurfaceType.DEAD, 0.1));
        this.plants.add(new CactusGrower(0.02, 0.1));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 5, SurfaceType.DIRT, 0.9));
    }},
    DARK_FOREST {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.LILAC, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.ROSE_BUSH, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.PEONY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.LILY_OF_THE_VALLEY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.DARK_OAK, Material.DARK_OAK_LOG, 5, SurfaceType.DIRT, 1));
        this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 5, SurfaceType.DIRT, 0.5));
        this.plants.add(new TreeGrower(TreeType.RED_MUSHROOM, Material.RED_MUSHROOM_BLOCK, 5, SurfaceType.FUNGI, 0.15));
        this.plants.add(new TreeGrower(TreeType.BROWN_MUSHROOM, Material.BROWN_MUSHROOM_BLOCK, 5, SurfaceType.FUNGI, 0.5));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    FLOWER_FOREST {{
       this.plants.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.95));
       this.plants.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.3));
       this.plants.add(new TreeGrower(TreeType.BIRCH, Material.BIRCH_LOG, 7, SurfaceType.DIRT, 0.95));
       this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
       this.plants.add(new BushGrower(Material.POPPY, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.DANDELION, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.ALLIUM, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.AZURE_BLUET, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.ORANGE_TULIP, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.PINK_TULIP, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.RED_TULIP, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.WHITE_TULIP, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.OXEYE_DAISY, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.CORNFLOWER, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.LILY_OF_THE_VALLEY, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.LILAC, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.ROSE_BUSH, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new BushGrower(Material.PEONY, false, 0.15, SurfaceType.DIRT, 0.1));
       this.plants.add(new SugarcaneGrower(0.4, 0.05)); 
    }},
    BIRCH_FOREST {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.LILAC, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.ROSE_BUSH, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.PEONY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.LILY_OF_THE_VALLEY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.BIRCH, Material.BIRCH_LOG, 5, SurfaceType.DIRT, 0.95));
        this.plants.add(new SugarcaneGrower(0.4, 0.05));
    }},
    TALL_BIRCH {{
        this.plants.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.2));
        this.plants.add(new BushGrower(Material.LILAC, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.POPPY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.DANDELION, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.ROSE_BUSH, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.PEONY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new BushGrower(Material.LILY_OF_THE_VALLEY, false, 0.02, SurfaceType.DIRT, 0.01));
        this.plants.add(new TreeGrower(TreeType.TALL_BIRCH, Material.BIRCH_LOG, 5, SurfaceType.DIRT, 0.95));
        this.plants.add(new SugarcaneGrower(0.4, 0.05)); 
    }},
    BEACH {{
       this.plants.add(new SugarcaneGrower(0.4, 0.05)); 
    }},
    BARREN;
    
    /** List of possible plants to grow in this biome. */
    protected List<PlantGrower> plants = Lists.newArrayList();
    
    /** Attempts to grow a random plant for this biome. */
    public void growSomething(World world, int x, int z) {
        
        if (!this.plants.isEmpty()) {

            this.plants.get(Floristics.RAND.nextInt(this.plants.size())).grow(world, x, z);
        }
    }
    
    /** Handles growth at the given location. */
    public static void handleGrowth(World world, int x, int z) {
        
        get(world.getBiome(x, z)).growSomething(world, x, z);
    }
    
    /** @return The appropriate BiomeGrower for the given Biome. */
    private static BiomeGrower get(Biome biome) {
        
        switch (biome) {
            
            case PLAINS:
                return PLAINS;
            case SUNFLOWER_PLAINS:
                return SUNFLOWER_PLAINS;
            case OCEAN: case DEEP_OCEAN: case COLD_OCEAN: case DEEP_COLD_OCEAN: case LUKEWARM_OCEAN: case DEEP_LUKEWARM_OCEAN:
                return COOL_OCEAN;
            case WARM_OCEAN: case DEEP_WARM_OCEAN:
                return WARM_OCEAN;
            case DESERT: case DESERT_HILLS: case DESERT_LAKES:
                return DESERT;
            case MOUNTAINS: case MOUNTAIN_EDGE: case GRAVELLY_MOUNTAINS: case MODIFIED_GRAVELLY_MOUNTAINS:
                return MOUNTAINS;
            case WOODED_MOUNTAINS:
                return WOODED_MOUNTAINS;
            case FOREST: case WOODED_HILLS:
                return FOREST;
            case TAIGA: case TAIGA_HILLS: case TAIGA_MOUNTAINS:
                return TAIGA;
            case SNOWY_TAIGA: case SNOWY_TAIGA_HILLS: case SNOWY_TAIGA_MOUNTAINS:
                return SNOWY_TAIGA;
            case GIANT_TREE_TAIGA: case GIANT_TREE_TAIGA_HILLS: case GIANT_SPRUCE_TAIGA: case GIANT_SPRUCE_TAIGA_HILLS:
                return MEGA_TAIGA;
            case SWAMP: case SWAMP_HILLS:
                return SWAMP;
            case SNOWY_TUNDRA: case SNOWY_MOUNTAINS:
                return TUNDRA;
            case MUSHROOM_FIELDS: case MUSHROOM_FIELD_SHORE:
                return MUSHROOM;
            case JUNGLE: case JUNGLE_HILLS: case MODIFIED_JUNGLE:
                return JUNGLE;
            case JUNGLE_EDGE: case MODIFIED_JUNGLE_EDGE:
                return JUNGLE_EDGE;
            case BAMBOO_JUNGLE: case BAMBOO_JUNGLE_HILLS:
                return BAMBOO_JUNGLE;
            case SAVANNA: case SAVANNA_PLATEAU: case SHATTERED_SAVANNA: case SHATTERED_SAVANNA_PLATEAU:
                return SAVANNA;
            case BADLANDS: case BADLANDS_PLATEAU: case MODIFIED_BADLANDS_PLATEAU: case ERODED_BADLANDS:
                return BADLANDS;
            case WOODED_BADLANDS_PLATEAU: case MODIFIED_WOODED_BADLANDS_PLATEAU:
                return WOODED_BADLANDS;
            case DARK_FOREST: case DARK_FOREST_HILLS:
                return DARK_FOREST;
            case FLOWER_FOREST:
                return FLOWER_FOREST;
            case BIRCH_FOREST: case BIRCH_FOREST_HILLS:
                return BIRCH_FOREST;
            case TALL_BIRCH_FOREST: case TALL_BIRCH_HILLS:
                return TALL_BIRCH;
            case RIVER:
                return RIVER;
            case END_HIGHLANDS:
                return END;
            case BEACH:
                return BEACH;
            case FROZEN_OCEAN: case DEEP_FROZEN_OCEAN: case NETHER: case THE_END: case FROZEN_RIVER: case STONE_SHORE: case SNOWY_BEACH: case SMALL_END_ISLANDS: case END_MIDLANDS: case END_BARRENS: case THE_VOID: case ICE_SPIKES:
            default:
                return BARREN;
        }
    }
}