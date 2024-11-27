package me.ItxItay.RTPPlugin.Utils;

import me.ItxItay.RTPPlugin.RTPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class CoordinatesMath {

    private static FileConfiguration config = RTPPlugin.getPlugin().getConfig();

    public static void tp2Coordinates(String range, Player player){
        String[] minMax = range.split(",");
        int min = Integer.parseInt(minMax[0]);
        int max = Integer.parseInt(minMax[1]);
        int x = RTPMath.MathLoader(max, min); //x
        int z = RTPMath.MathLoader(max, min); //z
        World world = Bukkit.getWorld(config.getString("RTP.worlds.rtp"));
        int y = world.getHighestBlockAt(x, z).getLocation().getBlockY()+1;

        getServer().getScheduler().runTaskLater(RTPPlugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                player.teleport(new Location(world, x + .5, y, z + .5));
            }
        }, 20L);
    }
}
