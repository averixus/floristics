/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics;

import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.common.collect.Sets;
import land.jay.floristics.compat.GriefPreventionWrapper;
import land.jay.floristics.compat.RedProtectWrapper;
import land.jay.floristics.compat.WorldGuardWrapper;

public class Floristics extends JavaPlugin {
    
    public static Floristics instance;
    public static final Random RAND = new Random();
    
    /** Config ticks between growth cycles. */
    private static int delay = 1;
    /** Config worlds to grow in. */
    private static Set<String> worlds = Sets.newHashSet();
    /** Config plants to grow. */
    private static Set<Material> plants = Sets.newHashSet();

    /** Whether GriefPrevention is present. */
    private static boolean hasGp = false;
    /** Whether WorldGuard is present. */
    private static boolean hasWg = false;
    /** Whether RedProtect is present. */
    private static boolean hasRp = false;

    @Override
    public void onLoad() {
        
        instance = this;
        
        hasGp = Bukkit.getPluginManager().getPlugin("GriefPrevention") != null;
        hasWg = Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
        hasRp = Bukkit.getPluginManager().getPlugin("RedProtect") != null;
        
        if (hasWg) { WorldGuardWrapper.onLoad(); }
    }
    
    @Override
    public void onEnable() {
        
        this.getCommand("floristics").setExecutor(this);
        
        this.saveDefaultConfig();
        delay = this.getConfig().getInt("delay");
        worlds.addAll(this.getConfig().getStringList("worlds"));
        ConfigurationSection section = this.getConfig().getConfigurationSection("plants");
        
        for (String key : section.getKeys(false)) {
            
            if (section.getBoolean(key)) {
                
                plants.add(Material.getMaterial(key));
            }
        }

        if (hasGp) { GriefPreventionWrapper.onEnable(); }
        if (hasRp) { RedProtectWrapper.onEnable(); }
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
                () -> this.growCycle(), delay, delay);
    }
    
    /** Attempts growth in one chunk per enabled world. */
    private void growCycle() {
        
        for (World world : Bukkit.getWorlds()) {
            
            if (worlds.contains(world.getName())) {
                
                Chunk[] chunks = world.getLoadedChunks();
                Chunk chunk = chunks[RAND.nextInt(chunks.length)];
                int x = (chunk.getX() * 16) + RAND.nextInt(16);
                int z = (chunk.getZ() * 16) + RAND.nextInt(16);
                
                BiomeGrower.handleGrowth(world, x, z);
            }
        }
    }
    
    /** @return Whether growth of this plant is enabled. */
    public static boolean isEnabled(Material material) {
        
        return plants.contains(material);
    }
    
    /** @return Whether growth is allowed at this location. */
    public static boolean hasPermission(Location location) {
        
        boolean result = true;

        result = hasGp ? result && GriefPreventionWrapper.canGrow(location) : result;
        result = hasWg ? result && WorldGuardWrapper.canGrow(location) : result;            
        result = hasRp ? result && RedProtectWrapper.canGrow(location) : result;
        
        return result;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (hasGp) {
            
            if (args[0].equals("gp")) {
                
                GriefPreventionWrapper.handleCommand(sender, args);
                
            } else {
                
                sender.sendMessage("Use /floristics gp [enable|disable] for GriefPrevention permissions.");
            }
            
        } else {
            
            sender.sendMessage("This command is only for use with GriefPrevention.");
        }

        return true;
    }
}
