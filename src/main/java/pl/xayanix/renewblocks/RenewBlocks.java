package pl.xayanix.renewblocks;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xayanix.renewblocks.listeners.BlockBreakListener;
import pl.xayanix.renewblocks.listeners.BlockPlaceListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Getter
public final class RenewBlocks extends JavaPlugin {

    public static RenewBlocks PLUGIN;

    private List<BlockToRemove> blockToRemoveList = new CopyOnWriteArrayList<>();

    @Override
    public void onEnable() {
        PLUGIN = this;
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getScheduler().runTaskTimer(this, () -> this.blockToRemoveList.forEach(BlockToRemove::remove), 1, 1);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
