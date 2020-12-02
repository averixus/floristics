package land.jay.floristics.growers;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

import com.google.common.collect.Lists;

import land.jay.floristics.Floristics;
import land.jay.floristics.compat.VivaldiWrapper;
import land.jay.floristics.plants.BeeGrower;
import land.jay.floristics.plants.BushGrower;
import land.jay.floristics.plants.PlantGrower;
import land.jay.floristics.plants.SugarcaneGrower;
import land.jay.floristics.plants.TreeGrower;
import me.nologic.vivaldi.core.season.Season;
import land.jay.floristics.plants.PlantGrower.SurfaceType;

public enum VivaldiSeasonGrower {

	SPRING{{
		this.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.4));
		this.add(new BushGrower(Material.TALL_GRASS, false, 0.15, SurfaceType.DIRT, 0.15));
	   	this.add(new BushGrower(Material.PINK_TULIP, false, 0.15, SurfaceType.DIRT, 0.1));
	   	this.add(new BushGrower(Material.RED_TULIP, false, 0.25, SurfaceType.DIRT, 0.1));
	   	this.add(new BushGrower(Material.WHITE_TULIP, false, 0.25, SurfaceType.DIRT, 0.1));
	   	this.add(new BushGrower(Material.OXEYE_DAISY, false, 0.25, SurfaceType.DIRT, 0.1));
	   	this.add(new BushGrower(Material.CORNFLOWER, false, 0.15, SurfaceType.DIRT, 0.1));
	   	this.add(new BushGrower(Material.LILY_OF_THE_VALLEY, false, 0.15, SurfaceType.DIRT, 0.05));
	}},
	SUMMER{{
		this.add(new TreeGrower(TreeType.TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.));
	    this.add(new TreeGrower(TreeType.BIG_TREE, Material.OAK_LOG, 7, SurfaceType.DIRT, 0.1));
		this.add(new TreeGrower(TreeType.BIRCH, Material.BIRCH_LOG, 7, SurfaceType.DIRT, 0.1));
		this.add(new BeeGrower(0.15));
		this.add(new BushGrower(Material.TALL_GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.TALL_GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.TALL_GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.TALL_GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.15, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.02, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.05, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.02, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.MELON, false, 0.02, SurfaceType.DIRT, 0.01));
		this.add(new BushGrower(Material.POPPY, false, 0.15, SurfaceType.DIRT, 0.1));
		this.add(new BushGrower(Material.DANDELION, false, 0.15, SurfaceType.DIRT, 0.1));
		this.add(new BushGrower(Material.ALLIUM, false, 0.15, SurfaceType.DIRT, 0.1));
		this.add(new BushGrower(Material.AZURE_BLUET, false, 0.05, SurfaceType.DIRT, 0.1));
		this.add(new BushGrower(Material.ORANGE_TULIP, false, 0.15, SurfaceType.DIRT, 0.1));
		this.add(new BushGrower(Material.LILAC, false, 0.15, SurfaceType.DIRT, 0.05));
	   	this.add(new BushGrower(Material.ROSE_BUSH, false, 0.15, SurfaceType.DIRT, 0.05));
	   	this.add(new BushGrower(Material.PEONY, false, 0.15, SurfaceType.DIRT, 0.05));
	   	this.add(new BushGrower(Material.SUNFLOWER, false, 0.15, SurfaceType.DIRT, 0.05));
	   	this.add(new SugarcaneGrower(0.4, 0.05)); 
	}},
	AUTUMN{{
		this.add(new BushGrower(Material.TALL_GRASS, false, 0.05, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.GRASS, false, 0.1, SurfaceType.DIRT, 0.5));
		this.add(new BushGrower(Material.PUMPKIN, false, 0.01, SurfaceType.DIRT, 0.01));
		this.add(new BushGrower(Material.BROWN_MUSHROOM, false, 0.01, SurfaceType.DIRT, 0.3));
		this.add(new BushGrower(Material.RED_MUSHROOM, false, 0.01, SurfaceType.DIRT, 0.2));
	   	this.add(new TreeGrower(TreeType.RED_MUSHROOM, Material.RED_MUSHROOM_BLOCK, 5, SurfaceType.DIRT, 0.15));
        this.add(new TreeGrower(TreeType.BROWN_MUSHROOM, Material.BROWN_MUSHROOM_BLOCK, 5, SurfaceType.DIRT, 0.2));
	}},
	WINTER{};
	
	/** List of possible plants to grow in this season. */
    protected List<PlantGrower> plants = Lists.newArrayList();
    
    /** Attempts to add this plant grower if it is enabled by config. */
    protected void add(PlantGrower grower) {
        if (Floristics.isEnabled(grower.material)) {
            this.plants.add(grower);
        }
    }
    
    /** Attempts to grow a random plant for current season. */
    public void growSomething(World world, int x, int z) {
        if (!this.plants.isEmpty()) {
        	
        	if (Floristics.hasVivaldi) {
    			if (VivaldiWrapper.getAPI().isWinter()) return;
    		}
        	
            this.plants.get(Floristics.RAND.nextInt(this.plants.size())).grow(world, x, z);
        }
    }
    
    /** Handles growth at the given location. */
    public static void handleGrowth(World world, int x, int z) {
        get(VivaldiWrapper.getAPI().getSeason()).growSomething(world, x, z);
    }
    
    /** @return Custom VivaldiBiomeGrower for the current Season. */
    private static VivaldiSeasonGrower get(Season season) {
        
        switch (season) {
        
			case AUTUMN:
				return AUTUMN;
			case SPRING:
				return SPRING;
			case SUMMER:
				return SUMMER;
			case WINTER:
				return WINTER;
			default:
				return SPRING;
				
        }
    }
	
}
