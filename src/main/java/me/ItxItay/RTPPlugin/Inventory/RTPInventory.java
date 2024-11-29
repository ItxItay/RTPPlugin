package me.ItxItay.RTPPlugin.Inventory;

import me.ItxItay.RTPPlugin.RTPPlugin;
import me.ItxItay.RTPPlugin.Utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RTPInventory {

    public final FileConfiguration config = RTPPlugin.getPlugin().getConfig();
    private ConfigurationSection settings =  RTPPlugin.getSettings();


    public void openRTPInventory(Player player){
        String menuTitle = config.getString("RTP.MenuSettings.title", "def");
        int menuSize = config.getInt("RTP.MenuSettings.size", 27);

        Inventory inventory = Bukkit.createInventory(null, menuSize, menuTitle);
        inventory = RTPlevels(inventory);
        RTPPlugin.setPlayerInventory(player.getUniqueId(), inventory);
        player.openInventory(inventory);
    }

    public Inventory RTPlevels(Inventory inv){

        for (String LEVELSListString : RTPPlugin.getLevelsList()){
            Material material =  Material.valueOf(settings.getString(LEVELSListString + ".menu.material"));
            int slot = settings.getInt(LEVELSListString + ".menu.slot");
            String name = settings.getString(LEVELSListString + ".menu.name");
            List<String> lore = settings.getStringList(LEVELSListString + ".menu.lore");
            int CustomModelData = settings.getInt(LEVELSListString + ".menu.custommodeldata");
            ItemStack LevelItem = ItemStackBuilder.ItemNullabe(material, name, CustomModelData, lore, LEVELSListString, 1);
            inv.setItem(slot, LevelItem);
        }


        return inv;
    }


}

