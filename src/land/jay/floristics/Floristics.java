package land.jay.floristics;

import java.io.File;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
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
import land.jay.floristics.compat.TownyWrapper;
import land.jay.floristics.compat.WorldGuardWrapper;
import land.jay.floristics.growers.DefaultBiomeGrower;
import land.jay.floristics.growers.VivaldiSeasonGrower;

public class Floristics extends JavaPlugin {
    
    public static Floristics instance;
    public static final Random RAND = new Random();
    
    /** Track the current config file version. */
    private static final int CONFIG_VERSION = 5;
    
    /** Config ticks between growth cycles. */
    private static int delay = 1;
    /** Config growth attempts per cycle. */
    private static int growths = 1;
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
    /** Whether Towny is present. */
    private static boolean hasTy = false;
    /** Whether Vivaldi is present. */
    public static boolean hasVivaldi = false;

    @Override
    public void onLoad() {
        
        instance = this;
        
        int version = !this.getConfig().contains("config-version", true) ? 0 : this.getConfig().getInt("config-version");
        if (version != CONFIG_VERSION) {
            warn("Your config file is out of date! A new one will be created, and the old one backed up as config-backup-" + version + ".yml.");
            File configFile = new File(getDataFolder(), "config.yml");
            configFile.renameTo(new File(this.getDataFolder(), "config-backup-" + version + ".yml"));
        }
        
        this.saveDefaultConfig();
        
        delay = this.getConfig().getInt("delay");
        growths = this.getConfig().getInt("growths");
        worlds.addAll(this.getConfig().getStringList("worlds"));
        ConfigurationSection section = this.getConfig().getConfigurationSection("plants");
        for (String key : section.getKeys(false)) {
            if (section.getBoolean(key)) {
                plants.add(Material.getMaterial(key));
            }
        }
        
        hasGp = Bukkit.getPluginManager().getPlugin("GriefPrevention") != null;
        hasWg = Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
        hasRp = Bukkit.getPluginManager().getPlugin("RedProtect") != null;
        hasTy = Bukkit.getPluginManager().getPlugin("Towny") != null;
		hasVivaldi = Bukkit.getPluginManager().getPlugin("Vivaldi") != null;
		
		if (hasVivaldi) {
			info("Detected using of Vivaldi. Changing from DefaultBiomeGrower to VivaldiSeasonGrower...");
		}
        
        if (hasWg) { hasWg = WorldGuardWrapper.onLoad(); }
        if (hasTy) { hasTy = TownyWrapper.onLoad(); }
    }
    
    @Override
    public void onEnable() {
        
        this.getCommand("floristics").setExecutor(this);

        if (hasGp) { hasGp = GriefPreventionWrapper.onEnable(); }
        if (hasRp) { hasRp = RedProtectWrapper.onEnable(); }
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
                () -> this.growCycle(), delay, delay);
    }
    
    /** Attempts growth in each enabled world. */
    private void growCycle() {
        
        for (World world : Bukkit.getWorlds()) {
            if (worlds.contains(world.getName())) {
                Chunk[] chunks = world.getLoadedChunks();
                if (chunks.length > 0) {
                    for (int i = 0; i < growths; i++) {
                        Chunk chunk = chunks[RAND.nextInt(chunks.length)];
                        int x = (chunk.getX() * 16) + RAND.nextInt(16);
                        int z = (chunk.getZ() * 16) + RAND.nextInt(16);
                        if (hasVivaldi) {
                        	VivaldiSeasonGrower.handleGrowth(world, x, z);
                        } else DefaultBiomeGrower.handleGrowth(world, x, z);
                    }
                }
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
        result = hasTy ? result && TownyWrapper.canGrow(location) : result;
        return result;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!hasGp && !hasTy) {
            sender.sendMessage("This command is only for use with GriefPrevention or Towny.");
            return true;
        }
        
        if (args.length > 0 && args[0].equals("gp")) {
            if (hasGp) {
                GriefPreventionWrapper.handleCommand(sender, args);
            } else {
                sender.sendMessage("This command is only for use with GriefPrevention.");
            }
        } else if (args.length > 0 && args[0].equals("towny")) {
            if (hasTy) {
                TownyWrapper.handleCommand(sender, args);
            } else {
                sender.sendMessage("This command is only for use with Towny.");
            }
        } else {
            if (hasGp && hasTy) {
                sender.sendMessage("Use /floristics gp [enable|disable] for GriefPrevention permissions.\n" +
                        "or /floristics towny [enable|disable] for Towny permissions.");
            } else if (hasGp) {
                sender.sendMessage("Use /floristics gp [enable|disable] for GriefPrevention permissions.");
            } else if (hasTy) {
                sender.sendMessage("Use /floristics towny [enable|disable] for Towny permissions.");
            }
        }
        
        return true;
    }
    
    public static void info(String message) {
        instance.getLogger().log(Level.INFO, message);
    }
    
    public static void warn(String message) {
        instance.getLogger().log(Level.WARNING, message);
    }
    
    public static void error(String message, Exception ex) {
        instance.getLogger().log(Level.SEVERE, message, ex);
    }
}
