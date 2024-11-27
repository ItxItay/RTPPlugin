package me.ItxItay.RTPPlugin.Utils;

import me.ItxItay.RTPPlugin.Utils.Constants.NameSpacedKey;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemStackBuilder {

    public static ItemStack ItemNullabe(
            Material material,
            String displayName,
            Integer customModelData,
            List<String> lore,
            String level,
            Integer amount
    ) {
        ItemStack is = new ItemStack(material, (amount == null) ? 1 : amount);
        ItemMeta isMeta = is.getItemMeta();
        if (customModelData != null) isMeta.setCustomModelData(customModelData);
        if (displayName != null) isMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        if (lore != null) isMeta.setLore(lore);
        if (level != null) isMeta.getPersistentDataContainer().set(NameSpacedKey.levelKey, PersistentDataType.STRING, level);
        is.setItemMeta(isMeta);
        return is;
    }

}
