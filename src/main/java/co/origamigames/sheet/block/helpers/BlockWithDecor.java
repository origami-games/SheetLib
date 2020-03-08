package co.origamigames.sheet.block.helpers;

import co.origamigames.sheet.SheetLib;
import com.terraformersmc.terraform.block.*;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class BlockWithDecor {
    public Block base;
    public SlabBlock slab;
    public TerraformStairsBlock stairs;
    public WallBlock wall;
    public BlockWithDecorInfo info;
    FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();

    public BlockWithDecor() {}

    public static BlockWithDecor register(String mod_id, ItemGroup item_group, BlockWithDecorInfo blockWithDecorInfo) {
        BlockWithDecor blocks = new BlockWithDecor();

        blocks.info = blockWithDecorInfo;
        Block baseBlock = blockWithDecorInfo.baseBlock;
        SlabBlock slabBlock = blockWithDecorInfo.slabBlock;
        TerraformStairsBlock stairsBlock = blockWithDecorInfo.stairsBlock;
        WallBlock wallBlock = blockWithDecorInfo.wallBlock;

        String name = blockWithDecorInfo.name;
        if (baseBlock != null) {
            if (blockWithDecorInfo.isPlural) blocks.base = register(mod_id, name + "s", item_group, baseBlock);
                else blocks.base = register(mod_id, name, item_group, baseBlock);
        } else Logger.getLogger(SheetLib.MOD_ID).warning("[" + SheetLib.MOD_ID + "] BlockWithDecor '" + mod_id + ":" + name + "' has no base!");
        if (slabBlock != null) blocks.slab = (SlabBlock)register(mod_id, name + "_slab", item_group, slabBlock);
           else blocks.slab = null;
        if (stairsBlock != null) blocks.stairs = (TerraformStairsBlock)register(mod_id, name + "_stairs", item_group, stairsBlock);
            else blocks.stairs = null;
        if (wallBlock != null) blocks.wall = (WallBlock) register(mod_id, name + "_wall", item_group, wallBlock);
            else blocks.wall = null;

        if (blockWithDecorInfo.isFlammable) {
            blocks.addToFireRegistries(blockWithDecorInfo.getFuelTime());
        }

        return blocks;
    }

    private void addToFireRegistries(int fuelTime) {
        this.flammableBlockRegistry.add(this.base, 5, 20);
        this.flammableBlockRegistry.add(this.slab, 5, 20);
        this.flammableBlockRegistry.add(this.stairs, 5, 20);

        FuelRegistry.INSTANCE.add(this.base, fuelTime);
        FuelRegistry.INSTANCE.add(this.slab, fuelTime / 2);
        FuelRegistry.INSTANCE.add(this.stairs, fuelTime);
    }

    private static Block register(String mod_id, String name, ItemGroup item_group, Block block) {
        return SheetLib.block(mod_id, name, block, item_group);
    }
}
