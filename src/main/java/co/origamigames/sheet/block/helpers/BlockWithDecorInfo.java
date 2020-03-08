package co.origamigames.sheet.block.helpers;

import com.terraformersmc.terraform.block.TerraformStairsBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;

public class BlockWithDecorInfo {
    public String name;
    public boolean isPlural;
    public boolean isFlammable = true;

    public Block baseBlock = new Block(FabricBlockSettings.copy(Blocks.AIR).build());
    public SlabBlock slabBlock = new SlabBlock(FabricBlockSettings.copy(baseBlock).build());
    public TerraformStairsBlock stairsBlock = new TerraformStairsBlock(baseBlock, FabricBlockSettings.copy(baseBlock).build());
    public WallBlock wallBlock = new WallBlock(FabricBlockSettings.copy(baseBlock).build());

    public int getFuelTime() {
        if (isFlammable) return 300;
           else return 0;
    }
}
