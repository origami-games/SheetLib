package co.origamigames.sheet.block.helpers;

import co.origamigames.sheet.SheetLibRegistryHelper;
import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.item.TerraformBoatItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;

import java.util.function.Supplier;

public class WoodItems {
    public BlockItem log;
    public BlockItem wood;
    public BlockItem leaves;
    public BlockItem planks;
    public BlockItem slab;
    public BlockItem stairs;
    public BlockItem fence;
    public BlockItem fenceGate;
    public BlockItem door;
    public BlockItem button;
    public BlockItem pressurePlate;
    public SignItem sign;
    public BlockItem trapdoor;
    public BlockItem strippedLog;
    public BlockItem strippedWood;
    public TerraformBoatItem boat;

    public WoodItems() {}

    public static void register(String mod_id, ItemGroup item_group, WoodInfo woodInfo) {
        WoodItems items = new WoodItems();
        WoodBlocks blocks = woodInfo.blocks;
        String name = woodInfo.name;

        items.log = registerBlockItem(mod_id, name + "_log", item_group, blocks.log);
        if (woodInfo.hasLeaves) items.leaves = registerBlockItem(mod_id, name + "_leaves", item_group, blocks.leaves);
        items.planks = registerBlockItem(mod_id, name + "_planks", item_group, blocks.planks);
        items.slab = registerBlockItem(mod_id, name + "_slab", item_group, blocks.slab);
        items.stairs = registerBlockItem(mod_id, name + "_stairs", item_group, blocks.stairs);
        items.fence = registerBlockItem(mod_id, name + "_fence", item_group, blocks.fence);
        items.fenceGate = registerBlockItem(mod_id, name + "_fence_gate", item_group, blocks.fenceGate);
        items.door = registerBlockItem(mod_id, name + "_door", item_group, blocks.door);
        items.button = registerBlockItem(mod_id, name + "_button", item_group, blocks.button);
        items.pressurePlate = registerBlockItem(mod_id, name + "_pressure_plate", item_group, blocks.pressurePlate);
        items.trapdoor = registerBlockItem(mod_id, name + "_trapdoor", item_group, blocks.trapdoor);
        items.sign = registerSignItem(mod_id, name + "_sign", item_group, blocks.sign, blocks.wallSign);
        items.strippedLog = registerBlockItem(mod_id, "stripped_" + name + "_log", item_group, blocks.strippedLog);
        if (woodInfo.boatEntity != null) items.boat = registerBoatItem(mod_id, name + "_boat", item_group, woodInfo.boatEntity);
            else items.boat = null;

        if (blocks.log != blocks.wood) {
            items.wood = registerBlockItem(mod_id, name + "_wood", item_group, blocks.wood);
        } else {
            items.wood = items.log;
        }

        if (blocks.strippedLog != blocks.strippedWood) {
            items.strippedWood = registerBlockItem(mod_id, "stripped_" + name + "_wood", item_group, blocks.strippedWood);
        } else {
            items.strippedWood = items.strippedLog;
        }
    }

    private static BlockItem registerBlockItem(String mod_id, String name, ItemGroup item_group, Block block) {
        return SheetLibRegistryHelper.registerBlockItem(mod_id, name, item_group, block);
    }
    private static SignItem registerSignItem(String mod_id, String name, ItemGroup item_group, Block standing, Block wall) {
        return SheetLibRegistryHelper.registerSignItem(mod_id, name, item_group, standing, wall);
    }
    private static TerraformBoatItem registerBoatItem(String mod_id, String name, ItemGroup item_group, Supplier<EntityType<TerraformBoatEntity>> boatType) {
        return SheetLibRegistryHelper.registerBoatItem(mod_id, name, item_group, boatType);
    }
}
