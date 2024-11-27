package me.ItxItay.RTPPlugin.Listeners;

import me.ItxItay.RTPPlugin.RTPPlugin;
import me.ItxItay.RTPPlugin.Utils.Constants.NameSpacedKey;
import me.ItxItay.RTPPlugin.Utils.CoordinatesMath;
import me.ItxItay.RTPPlugin.Utils.RTPMath;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;


public class ClickEventItem implements Listener {

    public final FileConfiguration config = RTPPlugin.getPlugin().getConfig();

    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null) return;

        ConfigurationSection settings =  config.getConfigurationSection("RTP.RTPSettings");
        Set<String> LEVELSList = settings.getKeys(false);

        String PersistentDataContainer = event.getItem().getItemMeta().getPersistentDataContainer().get(NameSpacedKey.levelKey, PersistentDataType.STRING);

        if (!LEVELSList.contains(PersistentDataContainer))return;
        if (player.getWorld() != Bukkit.getWorld(config.getString("RTP.worlds.spawn"))) {
            String invalidworld = config.getString("RTP.messages.not_in_spawn_world");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidworld));
            return;
        }
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));



        String range = config.getString("RTP.RTPSettings." + PersistentDataContainer + ".range");
        CoordinatesMath.tp2Coordinates(range, player);

    }
}
