/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.common.collect.Sets;
import land.jay.floristics.compat.TownyWrapper;

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
    private static final Set<String> worlds = new HashSet<>();
    /** Config plants to grow. */
    private static final Set<Material> plants = new HashSet<>();

    /** Whether Towny is present. */
    private static boolean hasTy = false;

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
        
        hasTy = Bukkit.getPluginManager().getPlugin("Towny") != null;
        if (hasTy) { hasTy = TownyWrapper.onLoad(); }
    }
    
    @Override
    public void onEnable() {
        
        this.getCommand("floristics").setExecutor(this);
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
                () -> this.growCycle(), delay, delay);
    }

    /** Attempts growth in each enabled world. */
    private void growCycle() {

        for (Player player: Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            if (!worlds.contains(world.getName())) {
                continue;
            }

            int x = RAND.nextInt(player.getViewDistance() * 16);
            int z = RAND.nextInt(player.getViewDistance() * 16);

            Block block = world.getBlockAt(x, 64, z);
            if (!block.getLocation().isChunkLoaded()) {
                world.getChunkAtAsync(block.getLocation()).thenAccept(chunk -> BiomeGrower.handleGrowth(world, chunk.getX(), chunk.getZ()));
            }
            else {
                Chunk chunk = block.getChunk();
                BiomeGrower.handleGrowth(world, chunk.getX(), chunk.getZ());
            }
        }

        /*
        for (World world : Bukkit.getWorlds()) {
            if (worlds.contains(world.getName())) {
                Chunk[] chunks = world.getLoadedChunks();
                if (chunks.length > 0) {
                    for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().size(); i++) {
                        Chunk chunk = chunks[RAND.nextInt(chunks.length)];
                        int x = (chunk.getX() * 16) + RAND.nextInt(16);
                        int z = (chunk.getZ() * 16) + RAND.nextInt(16);
                        BiomeGrower.handleGrowth(world, x, z);
                    }
                }
            }
        }
         */
    }
    
    /** @return Whether growth of this plant is enabled. */
    public static boolean isEnabled(Material material) {
        return plants.contains(material);
    }
    
    /** @return Whether growth is allowed at this location. */
    public static boolean hasPermission(Location location) {
        return TownyWrapper.canGrow(location);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!hasTy) {
            sender.sendMessage("This command is only for use with GriefPrevention or Towny.");
            return true;
        }

       if (args.length > 0 && args[0].equals("towny")) {
            if (hasTy) {
                TownyWrapper.handleCommand(sender, args);
            } else {
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
