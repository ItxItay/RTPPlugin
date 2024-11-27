package me.ItxItay.RTPPlugin.Commands;

import me.ItxItay.RTPPlugin.Inventory.RTPInventory;
import me.ItxItay.RTPPlugin.RTPPlugin;
import me.ItxItay.RTPPlugin.Utils.CoolDownUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;


public class RTPCommand implements CommandExecutor {

    public final FileConfiguration config = RTPPlugin.getPlugin().getConfig();
    public static CoolDownUtil coolDownUtil;


    public RTPCommand(){
        coolDownUtil = new CoolDownUtil(config.getLong("RTP.cooldown", 60), TimeUnit.MINUTES);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;


        if (coolDownUtil.getRemainingTime(player.getUniqueId()) > 0){
            long cooldown = TimeUnit.MILLISECONDS.toSeconds(coolDownUtil.getRemainingTime(player.getUniqueId()));
            long seconds = cooldown % 60;
            long minutes = (cooldown - seconds) / 60;
            String message = ChatColor.translateAlternateColorCodes('&', config.getString("RTP.messages.cooldown_left").replace("{TIME}",
                    ((minutes < 1) ? "" : minutes + ((minutes == 1) ? " minute " : " minutes ")) +
                            ((seconds < 1) ? "" : seconds + ((seconds == 1) ? " second " : " seconds "))));

            player.sendMessage(message);
            return false;
        }



        if (player.getWorld() != Bukkit.getWorld(config.getString("RTP.worlds.spawn"))) {
            String invalidworld = config.getString("RTP.messages.not_in_spawn_world");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidworld));
            return false;
        }


        RTPInventory rtpInventory = new RTPInventory();
        rtpInventory.openRTPInventory(player);

        return false;
    }

    public static void addCoolDown(Player player){
        coolDownUtil.addCooldown(player.getUniqueId());
    }
}