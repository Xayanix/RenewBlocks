package pl.xayanix.renewblocks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.xayanix.renewblocks.RenewBlocks;


public class BlockBreakListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("renewblocks.admin")) {
			switch (event.getBlock().getType()) {
				case MOSSY_COBBLESTONE:
                    if (RenewBlocks.PLUGIN.getBlockToRemoveList().stream().noneMatch(blockToRemove -> blockToRemove.getBlock().getX() == event.getBlock().getX() && blockToRemove.getBlock().getZ() == event.getBlock().getZ() && blockToRemove.getBlock().getY() == event.getBlock().getY())) {
                        event.setCancelled(true);
                    }
					return;
				default:
					event.setCancelled(true);
			}
		}


	}
}
