package co.origamigames.sheet.helpers;

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

    public static WoodItems register(WoodInfo woodInfo, WoodBlocks blocks, Supplier<EntityType<TerraformBoatEntity>> boatType, String mod_id, ItemGroup item_group) {
        WoodItems items = new WoodItems();
        String name = woodInfo.name;

        items.log = registerBlockItem(name + "_log", blocks.log, item_group, mod_id);
        if (woodInfo.hasLeaves) items.leaves = registerBlockItem(name + "_leaves", blocks.leaves, item_group, mod_id);
        items.planks = registerBlockItem(name + "_planks", blocks.planks, item_group, mod_id);
        items.slab = registerBlockItem(name + "_slab", blocks.slab, item_group, mod_id);
        items.stairs = registerBlockItem(name + "_stairs", blocks.stairs, item_group, mod_id);
        items.fence = registerBlockItem(name + "_fence", blocks.fence, item_group, mod_id);
        items.fenceGate = registerBlockItem(name + "_fence_gate", blocks.fenceGate, item_group, mod_id);
        items.door = registerBlockItem(name + "_door", blocks.door, item_group, mod_id);
        items.button = registerBlockItem(name + "_button", blocks.button, item_group, mod_id);
        items.pressurePlate = registerBlockItem(name + "_pressure_plate", blocks.pressurePlate, item_group, mod_id);
        items.trapdoor = registerBlockItem(name + "_trapdoor", blocks.trapdoor, item_group, mod_id);
        items.sign = registerSignItem(name + "_sign", blocks.sign, blocks.wallSign, item_group, mod_id);
        items.strippedLog = registerBlockItem("stripped_" + name + "_log", blocks.strippedLog, item_group, mod_id);
        if (boatType != null) items.boat = registerBoatItem(name + "_boat", boatType, item_group, mod_id);
            else items.boat = null;

        if (blocks.log != blocks.wood) {
            items.wood = registerBlockItem(name + "_wood", blocks.wood, item_group, mod_id);
        } else {
            items.wood = items.log;
        }

        if (blocks.strippedLog != blocks.strippedWood) {
            items.strippedWood = registerBlockItem("stripped_" + name + "_wood", blocks.strippedWood, item_group, mod_id);
        } else {
            items.strippedWood = items.strippedLog;
        }

        return items;
    }

    private static BlockItem registerBlockItem(String name, Block block, ItemGroup item_group, String mod_id) {
        return Registry.registerBlockItem(name, block, item_group, mod_id);
    }
    private static SignItem registerSignItem(String name, Block standing, Block wall, ItemGroup item_group, String mod_id) {
        return Registry.registerSignItem(name, standing, wall, item_group, mod_id);
    }
    private static TerraformBoatItem registerBoatItem(String name, Supplier<EntityType<TerraformBoatEntity>> boatType, ItemGroup item_group, String mod_id) {
        return Registry.registerBoatItem(name, boatType, item_group, mod_id);
    }
}
