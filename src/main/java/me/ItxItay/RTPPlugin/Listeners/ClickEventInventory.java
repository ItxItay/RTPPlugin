package me.ItxItay.RTPPlugin.Listeners;

import me.ItxItay.RTPPlugin.Commands.RTPCommand;
import me.ItxItay.RTPPlugin.RTPPlugin;
import me.ItxItay.RTPPlugin.Utils.Constants.NameSpacedKey;
import me.ItxItay.RTPPlugin.Utils.CoordinatesMath;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;



public class ClickEventInventory implements Listener {

    public final FileConfiguration config = RTPPlugin.getPlugin().getConfig();

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (RTPPlugin.getPlayerInventory(player.getUniqueId()) != event.getClickedInventory()) return;
        event.setCancelled(true);

        ItemStack clickeditem = event.getCurrentItem();
        if (clickeditem == null) return;

        if (!clickeditem.getItemMeta().getPersistentDataContainer().has(NameSpacedKey.levelKey , PersistentDataType.STRING)) return;
        player.closeInventory();


        String PersistentDataContainer = clickeditem.getItemMeta().getPersistentDataContainer().get(NameSpacedKey.levelKey, PersistentDataType.STRING);

        String permission = "rtp.permission." + PersistentDataContainer;
        if (!player.hasPermission(permission)){
            String permissionMessages = config.getString("RTP.messages.you_dont_have_permission");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', permissionMessages));
            return;
        }


        String range = config.getString("RTP.RTPSettings." + PersistentDataContainer + ".range");
        CoordinatesMath.tp2Coordinates(range, player);

        RTPCommand.addCoolDown(player);

    }
}
