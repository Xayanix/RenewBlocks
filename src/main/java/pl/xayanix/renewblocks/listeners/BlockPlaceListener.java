package pl.xayanix.renewblocks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import pl.xayanix.renewblocks.BlockToRemove;
import pl.xayanix.renewblocks.RenewBlocks;


public class BlockPlaceListener implements Listener {


	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (event.getBlockPlaced().getType().toString().contains("SLAB") || event.getBlockPlaced().getType().toString().contains("STEP") || event.getBlockPlaced().getType() == Material.ANVIL) {
				event.setCancelled(true);
				return;
			}

			switch (event.getBlock().getType()) {
				case MOSSY_COBBLESTONE:
				case SLIME_BLOCK:
				case OBSIDIAN:
					if (event.getBlock().getLocation().add(0, 1, 0).getBlock().getType() == Material.TRIPWIRE) {
						event.setCancelled(true);
					}

					RenewBlocks.PLUGIN.getBlockToRemoveList().add(new BlockToRemove(event.getBlockPlaced(), Math.min(30000, 300000 / event.getBlock().getY())));
					return;
				default:
					if (!player.hasPermission("renewblocks.admin")) {
						event.setCancelled(true);
					}
			}

		}

	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onWater(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockClicked().getRelative(event.getBlockFace());

		if (block.getType() != Material.AIR) {
			event.setCancelled(true);
			return;
		}

		if (player.getGameMode() == GameMode.SURVIVAL) {
			if (event.getBucket() == Material.WATER_BUCKET) {
				RenewBlocks.PLUGIN.getBlockToRemoveList().add(new BlockToRemove(block, 3000));

				ItemStack itemStack = event.getItemStack();
				int slot = player.getInventory().getHeldItemSlot();
				Bukkit.getScheduler().runTaskLater(RenewBlocks.PLUGIN, () -> {
					ItemStack item = player.getInventory().getItem(slot);
					if (item != null && item.isSimilar(itemStack)) {
						player.getInventory().setItem(slot, new ItemStack(Material.WATER_BUCKET));
					}
				}, 60);

			}
		}
	}

}
