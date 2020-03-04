package co.origamigames.sheet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.apache.logging.log4j.LogManager;

@SuppressWarnings("unused")
public class SheetLib implements ModInitializer {
    public static final String MOD_ID = "sheet-lib";

    @Override
    public void onInitialize() {
        LogManager.getLogger(MOD_ID).info("[SheetLib] Loaded");
    }

    // blocks
    public static Block block(String mod_id, String id, Block block, ItemGroup item_group) {
        Registry.register(Registry.BLOCK, new Identifier(mod_id, id), block);
        Registry.register(Registry.ITEM, new Identifier(mod_id, id),
                new BlockItem(block, new Item.Settings().group(item_group)));

        return block;
    }
    public static Block copiedBlock(String mod_id, String id, Block copied_block, ItemGroup item_group) {
        Block block = new Block(FabricBlockSettings.copy(copied_block).build());
        block(mod_id, id, block, item_group);

        return block;
    }
    // add stripping functionality for a pair of blocks (ONLY WORKS FOR BLOCKS WITH AN AXIS BLOCKSTATE)
    public static void addStrippingFunctionality(Block blockToBeStripped, Block blockAfterStrip, int itemDamageAmount, Tag<Item> usableItemsTag) {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (player.getStackInHand(hand).getItem().isIn(usableItemsTag) && world.getBlockState(hit.getBlockPos()).getBlock() == blockToBeStripped) {
                BlockPos blockPos = hit.getBlockPos();
                BlockState blockState = world.getBlockState(blockPos);

                world.playSound(player, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isClient) {
                    world.setBlockState(blockPos, blockAfterStrip.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
                    if (!player.isCreative()) {
                        ItemStack stack = player.getStackInHand(hand);
                        stack.damage(itemDamageAmount, player, ((p) -> p.sendToolBreakStatus(hand)));
                    }
                }

                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
    public static void addStrippingFunctionality(Block blockToBeStripped, Block blockAfterStrip, int itemDamageCount) {
        addStrippingFunctionality(blockToBeStripped, blockAfterStrip, itemDamageCount, FabricToolTags.AXES);
    }
    public static void addStrippingFunctionality(Block blockToBeStripped, Block blockAfterStrip) {
        addStrippingFunctionality(blockToBeStripped, blockAfterStrip, 1);
    }
    // add block transparency
    public static void setTransparencyRenderLayer(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }
    // add blocks to fuel registry
    public static void addToFuelRegistry(Block block, int burnTime) {
        FuelRegistry.INSTANCE.add(block, burnTime);
    }
    public static void addToFlammableBlockRegistry(Block block, int burnChance, int spreadChance) {
        FlammableBlockRegistry.getDefaultInstance().add(block, burnChance, spreadChance);
    }
    public static void addToFlammableBlockRegistry(Tag<Block> blockTag, int burnChance, int spreadChance) {
        FlammableBlockRegistry.getDefaultInstance().add(blockTag, burnChance, spreadChance);
    }

    // items
    public static Item item(String mod_id, String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(mod_id, id), item);
    }
    public static Item item(String mod_id, String id, ItemGroup item_group, int max_count) {
        return item(mod_id, id, new Item(new Item.Settings().group(item_group).maxCount(max_count)));
    }
    @SuppressWarnings("rawtypes")
    public static Item spawnEggItem(String mod_id, String entity_id, ItemGroup item_group, int max_count, EntityType entity, int primaryColor, int secondaryColor) {
        return Registry.register(Registry.ITEM, new Identifier(mod_id, entity_id + "_spawn_egg"),
                new SpawnEggItem(entity, primaryColor, secondaryColor,
                        new Item.Settings().maxCount(max_count).group(item_group)));
    }

    public static void lootTableAddition(String mod_id, String loot_table) {
        Identifier VANILLA_TABLE = new Identifier("minecraft", loot_table);
        Identifier ADDITION_TABLE = new Identifier(mod_id, "additions/" + loot_table);

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (VANILLA_TABLE.equals(id)) {
                supplier.copyFrom(lootManager.getTable(ADDITION_TABLE));
            }
        });
    }

    // world gen
        // default ore addition
    public static void addOverworldOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk, OreFeatureConfig.Target target) {
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE
                                .configure(new OreFeatureConfig(target, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.COUNT_RANGE
                                        .configure(new RangeDecoratorConfig(count, bottomOffset, topOffset, maxPerChunk))));
            }
        }
    }
    public static void addOverworldOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk) {
        addOverworldOre(block, size, count, bottomOffset, topOffset, maxPerChunk, OreFeatureConfig.Target.NATURAL_STONE);
    }
    public static void addNetherOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk, OreFeatureConfig.Target target) {
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE
                                .configure(new OreFeatureConfig(target, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.COUNT_RANGE
                                        .configure(new RangeDecoratorConfig(count, bottomOffset, topOffset, maxPerChunk))));
            }
        }
    }
    public static void addNetherOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk) {
        addNetherOre(block, size, count, bottomOffset, topOffset, maxPerChunk, OreFeatureConfig.Target.NETHERRACK);
    }
        // magma-like spawning conditions
    public static void addToMagmaDecorator(Block block, int size, int count, OreFeatureConfig.Target target) {
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE.configure(new OreFeatureConfig(target, block.getDefaultState(), size))
                                .createDecoratedFeature(Decorator.MAGMA.configure(new CountDecoratorConfig(count))));
            }
        }
    }
        // add overworld biome
    public static Biome overworldBiome(String mod_id, String id, Biome biome, OverworldClimate climate, double weight) {
        OverworldBiomes.addContinentalBiome(biome, climate, weight / 2);
        FabricBiomes.addSpawnBiome(biome);
        return Registry.register(Registry.BIOME, new Identifier(mod_id, id), biome);
    }
}
