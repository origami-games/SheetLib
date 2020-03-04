package co.origamigames.sheet.helpers;

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

import java.util.function.Supplier;

public class Registry {
    public static BlockItem registerBlockItem(String name, Block block, ItemGroup item_group, String mod_id) {
        BlockItem item = new BlockItem(block, new Item.Settings().group(item_group));
        item.appendBlocks(Item.BLOCK_ITEMS, item);

        RecipeUtil.registerCompostableBlock(block);

        return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(mod_id, name), item);
    }

    public static SignItem registerSignItem(String name, Block standing, Block wall, ItemGroup item_group, String mod_id) {
        return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(mod_id, name), new SignItem(new Item.Settings().group(item_group), standing, wall));
    }

    public static TerraformBoatItem registerBoatItem(String name, Supplier<EntityType<TerraformBoatEntity>> boatType, ItemGroup item_group, String mod_id) {
        return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(mod_id, name), new TerraformBoatItem(boatType, new Item.Settings().group(item_group).maxCount(1)));
    }

    public static <T extends Block> T register(String name, String mod_id, T block) {
        return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK, new Identifier(mod_id, name), block);
    }
}
