package co.origamigames.sheet;

import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.item.TerraformBoatItem;
import com.terraformersmc.terraform.util.RecipeUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class SheetLibRegistryHelper {
    public static BlockItem registerBlockItem(String mod_id, String name, ItemGroup item_group, Block block) {
        BlockItem item = new BlockItem(block, new Item.Settings().group(item_group));
        item.appendBlocks(Item.BLOCK_ITEMS, item);

        RecipeUtil.registerCompostableBlock(block);

        return Registry.register(Registry.ITEM, new Identifier(mod_id, name), item);
    }

    public static SignItem registerSignItem(String mod_id, String name, ItemGroup item_group, Block standing, Block wall, int maxCount) {
        return Registry.register(Registry.ITEM, new Identifier(mod_id, name), new SignItem(new Item.Settings().group(item_group).maxCount(maxCount), standing, wall));
    }
    public static SignItem registerSignItem(String mod_id, String name, ItemGroup item_group, Block standing, Block wall) {
        return registerSignItem(mod_id, name, item_group, standing, wall, 16);
    }

    public static TerraformBoatItem registerBoatItem(String mod_id, String name, ItemGroup item_group, Supplier<EntityType<TerraformBoatEntity>> boatEntity) {
        return Registry.register(Registry.ITEM, new Identifier(mod_id, name), new TerraformBoatItem(boatEntity, new Item.Settings().group(item_group).maxCount(1)));
    }

    public static <T extends Block> T register(String name, String mod_id, T block) {
        return Registry.register(Registry.BLOCK, new Identifier(mod_id, name), block);
    }
}
