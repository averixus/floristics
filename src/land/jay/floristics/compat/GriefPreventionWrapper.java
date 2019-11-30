package land.jay.floristics.compat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import land.jay.floristics.Floristics;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

/** Wrapper class for GriefPrevention API calls. */
public class GriefPreventionWrapper {
    
    /** File to store claims. */
    private static File claimsFile;
    /** Config to interact with claims. */
    private static YamlConfiguration claimsConfig;
    /** Set of claims with growth enabled. */
    private static Set<Long> enabledClaims = Sets.newHashSet();
    
    public static void onEnable() {

        Floristics.info("GriefPrevention is present, preparing gp.yml.");
        
        claimsFile = new File(Floristics.instance.getDataFolder(), "gp.yml");
        if (!claimsFile.exists()) {
            Floristics.instance.saveResource("gp.yml", false);
        }
        
        claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
        final InputStream stream = Floristics.instance.getResource("gp.yml");
        if (stream == null) {
            Floristics.error("Something went wrong reading gp.yml, this should never happen!", null);
            return;
        }

        claimsConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));
        List<Long> list = claimsConfig.getLongList("gp");
        if (!list.isEmpty()) {
            enabledClaims.addAll(list);
        }
    }

    public static boolean canGrow(Location location) {
        
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);        
        return claim == null || enabledClaims.contains(claim.getID());
    }
    
    public static void handleCommand(CommandSender sender, String[] args) {
        
        boolean validInfo = args.length == 1;
        boolean validChange = args.length == 2 && (args[1].equals("enable") || args[1].equals("disable"));
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return;
        }
        
        Player player = (Player) sender;
        
        if (!validInfo && !validChange) {
            player.sendMessage("Unknown command or invalid arguments. Valid uses:\n" +
                    "/floristics gp - display whether growth is enabled in this GriefPrevention claim\n" +
                    "/floristics gp <enable|disable> - enable or disable growth in this GriefPrevention claim\n" +
                    "Or replace /floristics with /flo for convenience.");
            return;
        }
        
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), true, null);

        if (claim == null || claim.allowEdit(player) != null) {
            player.sendMessage("You are not in one of your claims.");
            return;
        }
        
        Long id = claim.getID();

        if (validChange) {
            if (args[1].equals("enable")) {
                enabledClaims.add(id);
                player.sendMessage("Growth enabled in this claim.");  
            } else if (args[1].equals("disable")) {
                enabledClaims.remove(id);
                player.sendMessage("Growth disabled in this claim.");
            }
            claimsConfig.set("gp", Lists.newArrayList(enabledClaims));
            
            try {
                claimsConfig.save(claimsFile);
            } catch (IOException ex) {
                Floristics.error("Error saving gp.yml!", ex);
            }
            
        } else {
            player.sendMessage("Growth in this claim is currently " + (enabledClaims.contains(id) ? "enabled." : "disabled."));
        }
    }
}
