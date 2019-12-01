/** Copyright (C) 2019 Jay Avery */
package land.jay.floristics.compat;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.KeyAlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.CustomDataField;
import land.jay.floristics.Floristics;

public class TownyWrapper {

    /** Custom field for growth permission. */
    private static final BooleanDataField FIELD = new BooleanDataField("floristics", false);
    
    public static void onLoad() {
        
        Floristics.info("Towny is present, adding field to registry.");
        try {
            TownyAPI.getInstance().registerCustomDataField(FIELD);
        } catch (KeyAlreadyRegisteredException ex) {
            Floristics.error("Someone has already registered a floristics field for Towny, this should never happen!", ex);
        }
    }
    
    public static boolean canGrow(Location location) {
        
        Town town;
        try {
            TownBlock block = TownyAPI.getInstance().getTownBlock(location);
            town = block == null ? null : block.getTown();        
        } catch (NotRegisteredException ex) {
            Floristics.error("Something went wrong in the Towny API, this should never happen!", ex);
            return false;
        }
        
        if (town == null) {
            return true;
        }
        
        if (!town.hasMeta() || !town.getMetadata().contains(FIELD)) {
            town.addMetaData(FIELD);
        }
        BooleanDataField field = null;
        for (CustomDataField metadata : town.getMetadata()) {
            if (metadata.equals(FIELD)) {
                field = (BooleanDataField) metadata;
            }
        }
        
        return field.getValue();
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
                    "/floristics towny - display whether growth is enabled in this Town\n" +
                    "/floristics towny <enable|disable> - enable or disable growth in this Town\n" +
                    "Or replace /floristics with /flo for convenience.");
            return;
        }
        
        Resident resident;
        Town town;
        try {
            resident = TownyAPI.getInstance().getDataSource().getResident(player.getName());
            TownBlock block = TownyAPI.getInstance().getTownBlock(player.getLocation());
            town = block == null ? null : block.getTown();        
        } catch (NotRegisteredException ex) {
            Floristics.error("Something went wrong in the Towny API, this should never happen!", ex);
            return;
        }
        
        if (town == null || !town.isMayor(resident)) {
            player.sendMessage("You are not in a Town you own.");
            return;
        }
        
        if (!town.getMetadata().contains(FIELD)) {
            town.addMetaData(FIELD);
        }
        BooleanDataField field = null;
        for (CustomDataField metadata : town.getMetadata()) {
            if (metadata.equals(FIELD)) {
                field = (BooleanDataField) metadata;
            }
        }
        
        if (validChange) {
            if (args[1].equals("enable")) {
                field.setValue(true);
                player.sendMessage("Growth enabled in this Town.");
            } else if (args[1].equals("disable")) {
                field.setValue(false);
                player.sendMessage("Growth disabled in this Town.");
            }
            
        } else {
            player.sendMessage("Growth in this Town is currently " + (field.getValue() ? "enabled." : "disabled."));
        }
    }
}
