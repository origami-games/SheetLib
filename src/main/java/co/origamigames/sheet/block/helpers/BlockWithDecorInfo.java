package co.origamigames.sheet.block.helpers;

import com.terraformersmc.terraform.block.TerraformStairsBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;

public class BlockWithDecorInfo {
    public String name;
    public boolean isPlural;
    public boolean isFlammable = true;

    public Block baseBlock = null;
    public SlabBlock slabBlock = null;
    public TerraformStairsBlock stairsBlock = null;
    public WallBlock wallBlock = null;

    public int getFuelTime() {
        if (isFlammable) return 300;
           else return 0;
    }
}
