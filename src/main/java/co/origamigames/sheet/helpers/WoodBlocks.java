package co.origamigames.sheet.helpers;

import co.origamigames.sheet.block.*;
import com.terraformersmc.terraform.block.*;
import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class WoodBlocks {
    public Block log;
    public Block wood;
    public Block leaves;
    public Block planks;
    public SlabBlock slab;
    public TerraformStairsBlock stairs;
    public FenceBlock fence;
    public FenceGateBlock fenceGate;
    public TerraformDoorBlock door;
    public TerraformButtonBlock button;
    public TerraformPressurePlateBlock pressurePlate;
    public TerraformSignBlock sign;
    public TerraformWallSignBlock wallSign;
    public TerraformTrapdoorBlock trapdoor;
    public Block strippedLog;
    public Block strippedWood;
    private String name;
    private WoodInfo woodInfo;

    FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();

    public WoodBlocks() {}

    public static WoodBlocks register(String name, String mod_id, ItemGroup item_group, WoodInfo woodInfo, Supplier<EntityType<TerraformBoatEntity>> boatType, boolean isFlammable, boolean useExtendedLeaves) {
        WoodBlocks blocks = registerCrafted(name, mod_id, item_group, woodInfo, isFlammable);

        blocks.log = register(name + "_log", mod_id, new StrippableLogBlock(() -> blocks.strippedLog, woodInfo.plankColor, FabricBlockSettings.copy(Blocks.OAK_LOG).materialColor(woodInfo.barkColor).build()));
        blocks.wood = register(name + "_wood", mod_id, new StrippableLogBlock(() -> blocks.strippedWood, woodInfo.barkColor, FabricBlockSettings.copy(Blocks.OAK_LOG).materialColor(woodInfo.barkColor).build()));
        if (woodInfo.hasLeaves) {
            if (useExtendedLeaves) {
                blocks.leaves = register(name + "_leaves", mod_id, new ExtendedLeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).materialColor(woodInfo.leafColor).build()));
            } else {
                blocks.leaves = register(name + "_leaves", mod_id, new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).materialColor(woodInfo.leafColor).build()));
            }
        } else {
//            useExtendedLeaves = false;
            blocks.leaves = null;
        }
        blocks.strippedLog = register("stripped_" + name + "_log", mod_id, new LogBlock(woodInfo.plankColor, FabricBlockSettings.copy(Blocks.OAK_LOG).materialColor(woodInfo.plankColor).build()));
        blocks.strippedWood = register("stripped_" + name + "_wood", mod_id, new LogBlock(woodInfo.plankColor, FabricBlockSettings.copy(Blocks.OAK_LOG).materialColor(woodInfo.plankColor).build()));

        if (isFlammable) blocks.addTreeFireInfo();

        WoodItems.register(woodInfo, blocks, boatType, mod_id, item_group);

        return blocks;
    }
    public static WoodBlocks register(String name, String mod_id, ItemGroup item_group, WoodInfo woodInfo, Supplier<EntityType<TerraformBoatEntity>> boatType, boolean isFlammable) {
        return register(name, mod_id, item_group, woodInfo, boatType, isFlammable, true);
    }

    public static WoodBlocks registerCrafted(String name, String mod_id, ItemGroup item_group, WoodInfo woodInfo, boolean isFlammable) {
        WoodBlocks blocks = new WoodBlocks();
        blocks.name = name;
        blocks.woodInfo = woodInfo;

        blocks.planks = register(name + "_planks", mod_id, new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).materialColor(woodInfo.plankColor).build()));
        blocks.slab = register(name + "_slab", mod_id, new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB).materialColor(woodInfo.plankColor).build()));
        blocks.stairs = register(name + "_stairs", mod_id, new TerraformStairsBlock(blocks.planks, FabricBlockSettings.copy(Blocks.OAK_STAIRS).materialColor(woodInfo.plankColor).build()));
        blocks.fence = register(name + "_fence", mod_id, new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE).materialColor(woodInfo.plankColor).build()));
        blocks.fenceGate = register(name + "_fence_gate", mod_id, new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE).materialColor(woodInfo.plankColor).build()));
        blocks.door = register(name + "_door", mod_id, new TerraformDoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR).materialColor(woodInfo.plankColor).build()));
        blocks.button = register(name + "_button", mod_id, new TerraformButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).materialColor(woodInfo.plankColor).build()));
        blocks.pressurePlate = register(name + "_pressure_plate", mod_id, new TerraformPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE).materialColor(woodInfo.plankColor).build()));
        blocks.trapdoor = register(name + "_trapdoor", mod_id, new TerraformTrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR).materialColor(woodInfo.plankColor).build()));

        Identifier signTexture = new Identifier(mod_id, "entity/signs/" + name);
        blocks.sign = register(name + "_sign", mod_id, new TerraformSignBlock(signTexture, FabricBlockSettings.copy(Blocks.OAK_SIGN).materialColor(woodInfo.plankColor).build()));
        blocks.wallSign = register(name + "_wall_sign", mod_id, new TerraformWallSignBlock(signTexture, FabricBlockSettings.copy(Blocks.OAK_WALL_SIGN).materialColor(woodInfo.plankColor).build()));

        if (isFlammable) {
            blocks.addCraftedFireInfo();

            FuelRegistry.INSTANCE.add(blocks.fence, 300);
            FuelRegistry.INSTANCE.add(blocks.fenceGate, 300);
        }

        BlockRenderLayerMap.INSTANCE.putBlock(blocks.door, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(blocks.trapdoor, RenderLayer.getCutout());

        return blocks;
    }

    public void addTreeFireInfo() {
        flammableBlockRegistry.add(log, 5, 5);
        flammableBlockRegistry.add(strippedLog, 5, 5);

        if (wood != log) {
            flammableBlockRegistry.add(wood, 5, 5);
        }

        if (strippedWood != strippedLog) {
            flammableBlockRegistry.add(strippedWood, 5, 5);
        }

        flammableBlockRegistry.add(leaves, 30, 60);
    }

    public void addCraftedFireInfo() {
        flammableBlockRegistry.add(planks, 5, 20);
        flammableBlockRegistry.add(slab, 5, 20);
        flammableBlockRegistry.add(stairs, 5, 20);
        flammableBlockRegistry.add(fence, 5, 20);
        flammableBlockRegistry.add(fenceGate, 5, 20);
    }

    public enum LogSize {
        LARGE("large"),
        SMALL("small");

        private final String name;

        LogSize(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    
    private static <T extends Block> T register(String name, String mod_id, T block) {
        return Registry.register(name, mod_id, block);
    }
}
