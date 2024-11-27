package me.ItxItay.RTPPlugin.Commands;

import me.ItxItay.RTPPlugin.RTPPlugin;
import me.ItxItay.RTPPlugin.Utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class giveRTPCommand implements CommandExecutor {

    public final FileConfiguration config = RTPPlugin.getPlugin().getConfig();


    private static final String PERMISSION_GIVERTP = "RTP.command.givertp";
    private ItemStack item;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        String targetName = args[0];
        Player targetPlayer = sender.getServer().getPlayer(targetName);
        String level = args[1];
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be run by a player.");
            return true;
        }

        if (!sender.hasPermission(PERMISSION_GIVERTP)) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return false;
        }

        if (targetPlayer == null) {
            sender.sendMessage("§cThe player not found!");
            return false;
        }

        ConfigurationSection settings =  config.getConfigurationSection("RTP.RTPSettings");
        Set<String> LEVELSList = settings.getKeys(false);

        if (!LEVELSList.contains(level)){
            sender.sendMessage("§cRange not found!");
            return false;
        }

        player.getInventory().addItem(is(item, level));


        return false;
    }

    public ItemStack is(ItemStack item, String level){
        String path = "RTP.RTPSettings." + level + ".oneTimeCard.";

        Material material =  Material.valueOf(config.getString(path + "material"));
        String name = config.getString(path + "name");
        List<String> lore = config.getStringList(path + "lore");
        int CustomModelData = config.getInt(path + "custommodeldata");
        item = ItemStackBuilder.ItemNullabe(material, name, CustomModelData, lore, level, 1);

        return item;
    }
}
