package co.origamigames.sheet;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings({"unused","deprecated"})
@Mod(SheetLib.MOD_ID)
public class SheetLib {
    public static SheetLib instance;
    public static final String MOD_ID = "paint-the-world";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public SheetLib() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(instance::setup);

        MinecraftForge.EVENT_BUS.register(instance);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info(SheetLib.log("Loaded"));
    }

    public static String log(String text) {
        return "[SheetLib] " + text;
    }
    public static ResourceLocation texture(String mod_id, String path) {
        return new ResourceLocation(mod_id, "textures/" + path + ".png");
    }

    // blocks
    public static Block block(String mod_id, String id, ItemGroup item_group, Block block) {
        DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, mod_id);
        DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, mod_id);

        BLOCKS.register(id, () -> block);
        ITEMS.register(id, () -> new BlockItem(block, new Item.Properties().group(item_group)));

        return block;
    }
    public static Block copiedBlock(String mod_id, String id, ItemGroup item_group, Block copied_block) {
        Block block = new Block(Block.Properties.from(copied_block));
        block(mod_id, id, item_group, block);

        return block;
    }

    // add block transparency
//    public static void setTransparencyRenderLayer(Block block) {
//        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
//    }
    // add blocks to fuel registry
//    public static void addToFuelRegistry(Block block, int burnTime) {
//        FuelRegistry.INSTANCE.add(block, burnTime);
//    }
    public static void addToFlammableBlockRegistry(Block block, int burnChance, int spreadChance) {
        ((FireBlock) Blocks.FIRE).setFireInfo(block, burnChance, spreadChance);
    }

    // items
    public static Item item(String mod_id, String id, Item item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(mod_id, id), item);
    }
    public static Item item(String mod_id, String id, ItemGroup item_group, int max_count) {
        return item(mod_id, id, new Item(new Item.Properties().group(item_group).maxStackSize(max_count)));
    }
    @SuppressWarnings("rawtypes")
    public static Item spawnEggItem(String mod_id, String entity_id, EntityType entity, ItemGroup item_group, int max_count, int primaryColor, int secondaryColor) {
        return Registry.register(Registry.ITEM, new ResourceLocation(mod_id, entity_id + "_spawn_egg"),
                new SpawnEggItem(entity, primaryColor, secondaryColor,
                        new Item.Properties().maxStackSize(max_count).group(item_group)));
    }

//    public static void lootTableAddition(String mod_id, String loot_table) {
//        ResourceLocation VANILLA_TABLE = new ResourceLocation("minecraft", loot_table);
//        ResourceLocation ADDITION_TABLE = new ResourceLocation(mod_id, "additions/" + loot_table);
//
//        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
//            if (VANILLA_TABLE.equals(id)) {
//                supplier.copyFrom(lootManager.getSupplier(ADDITION_TABLE));
//            }
//        });
//    }

    // world gen
    // default ore addition
//    public static void addOverworldOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk, OreFeatureConfig.FillerBlockType target) {
//        for (Biome biome : Registry.BIOME) {
//            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
//                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
//                        Feature.ORE
//                                .configure(new OreFeatureConfig(target, block.getDefaultState(), size))
//                                .createDecoratedFeature(Decorator.COUNT_RANGE
//                                        .configure(new RangeDecoratorConfig(count, bottomOffset, topOffset, maxPerChunk))));
//            }
//        }
//    }
//    public static void addOverworldOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk) {
//        addOverworldOre(block, size, count, bottomOffset, topOffset, maxPerChunk, OreFeatureConfig.Target.NATURAL_STONE);
//    }
//    public static void addNetherOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk, OreFeatureConfig.Target target) {
//        for (Biome biome : Registry.BIOME) {
//            if (biome.getCategory() == Biome.Category.NETHER) {
//                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
//                        Feature.ORE
//                                .configure(new OreFeatureConfig(target, block.getDefaultState(), size))
//                                .createDecoratedFeature(Decorator.COUNT_RANGE
//                                        .configure(new RangeDecoratorConfig(count, bottomOffset, topOffset, maxPerChunk))));
//            }
//        }
//    }
//    public static void addNetherOre(Block block, int size, int count, int bottomOffset, int topOffset, int maxPerChunk) {
//        addNetherOre(block, size, count, bottomOffset, topOffset, maxPerChunk, OreFeatureConfig.Target.NETHERRACK);
//    }
//    // magma-like spawning conditions
//    public static void addToMagmaDecorator(Block block, int size, int count, OreFeatureConfig.Target target) {
//        for (Biome biome : Registry.BIOME) {
//            if (biome.getCategory() == Biome.Category.NETHER) {
//                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
//                        Feature.ORE.configure(new OreFeatureConfig(target, block.getDefaultState(), size))
//                                .createDecoratedFeature(Decorator.MAGMA.configure(new CountDecoratorConfig(count))));
//            }
//        }
//    }
//    // add overworld biome
//    public static Biome overworldBiome(String mod_id, String id, Biome biome, OverworldClimate climate, double weight) {
//        OverworldBiomes.addContinentalBiome(biome, climate, weight / 2);
//        FabricBiomes.addSpawnBiome(biome);
//        return Registry.register(Registry.BIOME, new ResourceLocation(mod_id, id), biome);
//    }
}
