package land.jay.floristics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class Floristics extends JavaPlugin {
    
    /** Utility. */
    public static final Random RAND = new Random();
    
    /** Config ticks between growth cycles. */
    private static int delay = 1;
    /** Config worlds to grow in. */
    private static Set<String> worlds = Sets.newHashSet();
    
    /** Whether GriefPrevention is present. */
    private static boolean hasGp = false;
    /** File to store GP claims. */
    private static File claimsFile;
    /** Config to interact with GP claims. */
    private static YamlConfiguration claimsConfig;
    /** Set of GP claims with growth enabled. */
    private static Set<Long> enabledClaims = Sets.newHashSet();
    
    @Override
    public void onEnable() {
        
        hasGp = Bukkit.getPluginManager().isPluginEnabled("GriefPrevention");
        this.getCommand("floristics").setExecutor(this);
        this.readData();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
                () -> this.growCycle(), delay, delay);
    }
    
    /** Attempts growth in one chunk per enabled world. */
    private void growCycle() {
        
        for (World world : Bukkit.getWorlds()) {
            
            if (worlds.contains(world.getName())) {
                
                Chunk[] chunks = world.getLoadedChunks();
                
                for (int i = 0; i < 100; i++) {//TEST
            
                Chunk chunk = chunks[RAND.nextInt(chunks.length)];
                int x = (chunk.getX() * 16) + RAND.nextInt(16);
                int z = (chunk.getZ() * 16) + RAND.nextInt(16);
                
                if (this.canGrow(world, x, z)) {
                
                    BiomeGrower.handleGrowth(world, x, z);
                }
                }//TEST
            }
        }
    }
    
    /** @return Whether growth is allowed at this block. */
    private boolean canGrow(World world, int x, int z) {
        
        if (hasGp) {
            
            Claim claim = GriefPrevention.instance.dataStore
                    .getClaimAt(new Location(world, x, 0, z), true, null);
            
            if (claim != null) {
                
                return enabledClaims.contains(Long.valueOf(claim.getID()));
            }
        }
        
        return true;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean validInfo = args.length == 1 && args[0].equals("growth");
        boolean validChange = args.length == 2 && args[0].equals("growth") && (args[1].equals("enable") || args[1].equals("disable"));
        
        if (!(sender instanceof Player)) {

            this.getLogger().info("You must be a player to use this command.");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!hasGp) {
            
            player.sendMessage("This command is only for use with GriefPrevention.");
            return true;
        }
        
        if (!validInfo && !validChange) {

            player.sendMessage("Unknown command or invalid arguments. Valid uses:\n" +
                    "/floristics growth - display whether growth is enabled in this GriefPrevention claim\n" +
                    "/floristics growth <enable|disable> - enable or disable growth in this GriefPrevention claim\n" +
                    "Or replace /floristics with /flo for convenience.");
            return true;
        }
        
        Claim claim = GriefPrevention.instance.dataStore
                .getClaimAt(player.getLocation(), true, null);

        if (claim == null || claim.allowEdit(player) != null) {

            player.sendMessage("You are not in one of your claims.");
            return true;
        }
        
        Long id = claim.getID();

        if (validChange) {

            if (args[1].equals("enable")) {

                Floristics.enabledClaims.add(id);
                player.sendMessage("Growth enabled in this claim.");  
                
            } else if (args[1].equals("disable")) {

                Floristics.enabledClaims.remove(id);
                player.sendMessage("Growth disabled in this claim.");
            }
             
        } else {

            player.sendMessage("Growth in this claim is currently " +
                    (Floristics.enabledClaims.contains(id) ? "enabled." : "disabled."));
        }

        this.saveData();
        return true;
    }
    
    /** Reads config and claims data. */
    private void readData() {
        
        this.saveDefaultConfig();
        
        delay = this.getConfig().getInt("delay");
        worlds.addAll(this.getConfig().getStringList("worlds"));
        
        if (hasGp) {
            
            claimsFile = new File(this.getDataFolder(), "claims.yml");
            
            if (!claimsFile.exists()) {
                
                saveResource("claims.yml", false);
            }
            
            if (claimsConfig == null) {
                
                claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
    
                final InputStream stream = getResource("claims.yml");
                
                if (stream == null) {
                    
                    return;
                }
    
                claimsConfig.setDefaults(YamlConfiguration.loadConfiguration(
                        new InputStreamReader(stream, Charsets.UTF_8)));
            }
            
            
            List<Long> list = claimsConfig.getLongList("claims");
            
            if (!list.isEmpty()) {
                
                enabledClaims.addAll(list);
            }
        }
    }
    
    /** Saves config and claims data. */
    private void saveData() {
        
        this.saveConfig();
        
        if (hasGp) {
            
            claimsConfig.set("claims", Lists.newArrayList(enabledClaims));
            
            try {
                
                claimsConfig.save(claimsFile);
                
            } catch (IOException ex) {
                
                this.getLogger().log(Level.SEVERE, "Error saving claims.yml!", ex);
            }
        }
    }
}
