package pl.xayanix.renewblocks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

/*
    created by: Xayanix at 2021-01-08 19:30
*/

@Getter
@RequiredArgsConstructor
public class BlockToRemove {

    private final Block block;
    private final long time;
    private final long timestamp = System.currentTimeMillis();

    public void remove(){
        if(timestamp + time > System.currentTimeMillis())
            return;

        block.setType(Material.AIR);
        RenewBlocks.PLUGIN.getBlockToRemoveList().remove(this);
    }

}
