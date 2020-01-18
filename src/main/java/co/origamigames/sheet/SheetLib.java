package co.origamigames.sheet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SheetLib implements ModInitializer {
    public static final String MOD_ID = "sheet-lib";

    @Override
    public void onInitialize() {
        System.out.println("** SheetLib Loaded **");
    }

    // undefined block
    public static Block block(String mod_id, String id, Block block, ItemGroup item_group) {
        Registry.register(Registry.BLOCK, new Identifier(mod_id, id), block);
        Registry.register(Registry.ITEM, new Identifier(mod_id, id),
                new BlockItem(block, new Item.Settings().group(item_group)));

        return block;
    }
    // block with the properties of another
    public static Block copiedBlock(String mod_id, String id, Block copied_block, ItemGroup item_group) {
        Block block = new Block(FabricBlockSettings.copy(copied_block).build());
        block(mod_id, id, block, item_group);

        return block;
    }

    // undefined item
    public static Item item(String mod_id, String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(mod_id, id), item);
    }
    // basic item
    public static Item item(String mod_id, String id, ItemGroup item_group, int max_count) {
        return item(mod_id, id, new Item(new Item.Settings().group(item_group).maxCount(max_count)));
    }
    // spawn egg item
    @SuppressWarnings("rawtypes")
    public static Item spawnEggItem(String mod_id, String entity_id, ItemGroup item_group, int max_count, int maxCount, EntityType entity, int primaryColor,
                             int secondaryColor) {
        return Registry.register(Registry.ITEM, new Identifier(mod_id, entity_id + "_spawn_egg"),
                new SpawnEggItem(entity, primaryColor, secondaryColor,
                        new Item.Settings().maxCount(max_count).group(item_group)));
    }
}
