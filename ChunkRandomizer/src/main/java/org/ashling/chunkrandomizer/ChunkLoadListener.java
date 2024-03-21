package org.ashling.chunkrandomizer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class ChunkLoadListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!event.isNewChunk()) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            double spawnChance = ChunkRandomizer.getInstance().getConfigManager().getSpawnChance();
            if (random.nextDouble() < spawnChance) {
                Bukkit.getServer().getScheduler().callSyncMethod(ChunkRandomizer.getInstance(), () -> {
                    spawnDiamond(event.getChunk());
                    return null;
                });
            }
        });
    }

    private void spawnDiamond(Chunk chunk) {
        int x = random.nextInt(16) + chunk.getX() * 16;
        int z = random.nextInt(16) + chunk.getZ() * 16;
        int y = chunk.getWorld().getHighestBlockYAt(x, z);

        Location dropLocation = new Location(chunk.getWorld(), x, y, z);
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta meta = diamond.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mystic Diamond");
        meta.setLore(Arrays.asList(ChatColor.GOLD + "A rare and luminous gem", ChatColor.GREEN + "Its glow is otherworldly"));
        meta.addEnchant(org.bukkit.enchantments.Enchantment.LUCK, 1, false);
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        diamond.setItemMeta(meta);

        Item item = chunk.getWorld().dropItem(dropLocation, diamond);
        item.setGlowing(true);

        Bukkit.getScheduler().runTaskLater(ChunkRandomizer.getInstance(), item::remove, 1200); // Despawns after 60 seconds
        Bukkit.getLogger().info("Spawned Mystic Diamond at " + dropLocation + " in world " + chunk.getWorld().getName());
    }
}