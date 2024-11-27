package me.ItxItay.RTPPlugin.Utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

// Made by NotAMojangDev <https://namd.dev>
// Mail: mailto:contact@namd.dev
public class CoolDownUtil {

    private final Cache<UUID, Long> cooldowns;
    private final long extraTime;

    public CoolDownUtil(Long time, TimeUnit unit) {
        cooldowns = CacheBuilder
                .newBuilder()
                .expireAfterWrite(time, unit)
                .build();
        extraTime = unit.toMillis(time);
    }

    public void addCooldown(Player player) {
        addCooldown(player.getUniqueId());
    }

    public void addCooldown(UUID uuid) {
        cooldowns.put(uuid, System.currentTimeMillis() + extraTime);
    }

    public long getRemainingTime(UUID uuid) {
        if (!cooldowns.asMap().containsKey(uuid)) return -1;

        long durationLeft = cooldowns.asMap().get(uuid);
        return durationLeft - System.currentTimeMillis();
    }

}
