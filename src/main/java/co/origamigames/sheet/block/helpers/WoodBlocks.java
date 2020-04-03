package co.origamigames.sheet.block.helpers;

import co.origamigames.sheet.SheetLib;
import com.terraformersmc.terraform.block.*;
import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.item.TerraformBoatItem;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class WoodBlocks {
    public String mod_id;

    public String wood_id;
    public ItemGroup item_group;

    public boolean hasLeaves;
    public boolean isFlammable;
    public int fuelTime;

    public Supplier<EntityType<TerraformBoatEntity>> boatEntity;
    public TerraformBoatItem boatItem;

    public LogBlock log;
    public LogBlock stripped_log;
    public PillarBlock wood;
    public PillarBlock stripped_wood;
    public LeavesBlock leaves;
    public Block planks;
    public SlabBlock slab;
    public TerraformStairsBlock stairs;
    public FenceBlock fence;
    public FenceGateBlock fence_gate;
    public TerraformDoorBlock door;
    public TerraformButtonBlock button;
    public TerraformPressurePlateBlock pressure_plate;
    public TerraformSignBlock sign;
    public TerraformWallSignBlock wall_sign;
    public TerraformTrapdoorBlock trapdoor;

    public WoodBlocks(WoodBlocks.Info info) {
        this.mod_id = info.mod_id;

        this.wood_id = info.wood_id;
        this.item_group = info.item_group;

        if (this.wood_id.equals("")) {
            SheetLib.LOGGER.severe(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
            return;
        }

        this.hasLeaves = info.hasLeaves;
        this.isFlammable = info.isFlammable;
        this.fuelTime = info.fuelTime;

        this.boatEntity = info.boatEntity;
        if (this.boatEntity != null) info.boatItem = Registry.register(Registry.ITEM, new Identifier(this.mod_id, this.wood_id), new TerraformBoatItem (
                (world, x, y, z) -> {
                    TerraformBoatEntity entity = Objects.requireNonNull(boatEntity.get().create(world), "null boat from EntityType::create");

                    entity.updatePosition(x, y, z);
                    entity.setVelocity(Vec3d.ZERO);
                    entity.prevX = x;
                    entity.prevY = y;
                    entity.prevZ = z;

                    return entity;
                },
                new Item.Settings().group(this.item_group).maxCount(1)
        ));

        this.log = (LogBlock)register(this.wood_id + "_log", info.log);
        this.stripped_log = (LogBlock)register("stripped_" + this.wood_id + "_log", info.stripped_log);
        this.wood = (PillarBlock)register(this.wood_id + "_wood", info.wood);
        this.stripped_wood = (PillarBlock)register("stripped_" + this.wood_id + "_wood", info.stripped_wood);
        if (hasLeaves) this.leaves = (LeavesBlock)register(this.wood_id + "_leaves", info.leaves);
            else this.leaves = null;
        this.planks = register(this.wood_id + "_planks", info.planks);
        this.slab = (SlabBlock)register(this.wood_id + "_slab", info.slab);
        this.stairs = (TerraformStairsBlock)register(this.wood_id + "_stairs", info.stairs);
        this.fence = (FenceBlock)register(this.wood_id + "_fence", info.fence);
        this.fence_gate = (FenceGateBlock)register(this.wood_id + "_fence_gate", info.fence_gate);
        this.door = (TerraformDoorBlock)register(this.wood_id + "_door", info.door);
        this.button = (TerraformButtonBlock)register(this.wood_id + "_button", info.button);
        this.pressure_plate = (TerraformPressurePlateBlock)register(this.wood_id + "_pressure_plate", info.pressure_plate);
        this.sign = (TerraformSignBlock)register(this.wood_id + "_sign", info.sign);
        this.wall_sign = (TerraformWallSignBlock)register(this.wood_id + "_wall_sign", info.wall_sign);
        this.trapdoor = (TerraformTrapdoorBlock)register(this.wood_id + "_trapdoor", info.trapdoor);

        Logger LOGGER = SheetLib.LOGGER;
        if (this.wood_id.equals("")) {
            LOGGER.severe(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
            return;
        }

        if (this.isFlammable) {
            FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
            flammableBlockRegistry.add(this.planks, 5, 20);
            flammableBlockRegistry.add(this.slab, 5, 20);
            flammableBlockRegistry.add(this.fence_gate, 5, 20);
            flammableBlockRegistry.add(this.fence, 5, 20);
            flammableBlockRegistry.add(this.stairs, 5, 20);
            flammableBlockRegistry.add(this.log, 5, 5);
            flammableBlockRegistry.add(this.stripped_log, 5, 5);
            flammableBlockRegistry.add(this.wood, 5, 5);
            flammableBlockRegistry.add(this.stripped_wood, 5, 5);
            flammableBlockRegistry.add(this.leaves, 30, 60);

            FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
            fuelRegistry.add(this.log, this.fuelTime);
            fuelRegistry.add(this.planks, this.fuelTime);
            fuelRegistry.add(this.stairs, this.fuelTime);
            fuelRegistry.add(this.slab, this.fuelTime / 2);
            fuelRegistry.add(this.trapdoor, this.fuelTime);
            fuelRegistry.add(this.pressure_plate, this.fuelTime);
            fuelRegistry.add(this.fence, this.fuelTime);
            fuelRegistry.add(this.fence_gate, this.fuelTime);
            fuelRegistry.add(this.sign, (int)((double)this.fuelTime / 1.5));
            fuelRegistry.add(this.door, (int)((double)this.fuelTime / 1.5));
            fuelRegistry.add(this.boatItem, this.fuelTime * 4);
            fuelRegistry.add(this.button, this.fuelTime / 3);
        }

        BlockRenderLayerMap blockRenderLayerMap = BlockRenderLayerMap.INSTANCE;
        blockRenderLayerMap.putBlock(this.door, RenderLayer.getCutout());
        blockRenderLayerMap.putBlock(this.trapdoor, RenderLayer.getCutout());
    }

    private Block register(String id, Block block) {
        if (block instanceof TerraformWallSignBlock) {
            Registry.register(Registry.BLOCK, new Identifier(this.mod_id, id), block);
            return block;
        } else return SheetLib.block(this.mod_id, id, this.item_group, block);
    }

    public static class Info {
        public Info() {
            if (isFlammable) fuelTime = 300;
            else fuelTime = 0;
        }

        public String mod_id = "";
        public String wood_id = "";
        public ItemGroup item_group = null;

        public boolean hasLeaves = true;
        public boolean isFlammable = true;
        public int fuelTime;

        public Supplier<EntityType<TerraformBoatEntity>> boatEntity = null;
        public TerraformBoatItem boatItem = null;

        public MaterialColor endMaterialColor = MaterialColor.BROWN;
        public SignType signType = SignType.OAK;

        public LogBlock log = new LogBlock(endMaterialColor, FabricBlockSettings.copy(Blocks.OAK_LOG).build());
        public LogBlock stripped_log = new LogBlock(endMaterialColor, FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG).build());
        public PillarBlock wood = new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD).build());
        public PillarBlock stripped_wood = new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD).build());
        public LeavesBlock leaves = new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).build());
        public Block planks = new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).build());
        public SlabBlock slab = new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB).build());
        public TerraformStairsBlock stairs = new TerraformStairsBlock(planks, FabricBlockSettings.copy(Blocks.OAK_STAIRS).build());
        public FenceBlock fence = new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE).build());
        public FenceGateBlock fence_gate = new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE).build());
        public TerraformDoorBlock door = new TerraformDoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR).build());
        public TerraformButtonBlock button = new TerraformButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON).build());
        public TerraformPressurePlateBlock pressure_plate = new TerraformPressurePlateBlock(FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE).build());
        public TerraformSignBlock sign = new TerraformSignBlock(SheetLib.texture(mod_id, "entity/signs/" + wood_id), FabricBlockSettings.copy(Blocks.OAK_SIGN).build());
        public TerraformWallSignBlock wall_sign = new TerraformWallSignBlock(SheetLib.texture(mod_id, "entity/signs/" + wood_id), FabricBlockSettings.copy(Blocks.OAK_WALL_SIGN).build());
        public TerraformTrapdoorBlock trapdoor = new TerraformTrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR).build());
    }
}
