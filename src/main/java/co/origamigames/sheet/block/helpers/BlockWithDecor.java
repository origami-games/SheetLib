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
    private Logger LOGGER = SheetLib.LOGGER;

    public String mod_id;

    public String block_id;
    public ItemGroup item_group;

    public boolean isPlural;
    public boolean isFlammable;
    public int fuelTime;

    public Block base;
    public SlabBlock slab;
    public TerraformStairsBlock stairs;
    public WallBlock wall;

    public BlockWithDecor(BlockWithDecor.Info info) {
        this.mod_id = info.mod_id;

        this.block_id = info.block_id;
        this.item_group = info.item_group;

        if (this.block_id.equals("")) {
            SheetLib.LOGGER.severe(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
            return;
        }

        this.isPlural = info.isPlural;
        this.isFlammable = info.isFlammable;
        this.fuelTime = info.fuelTime;
        this.base = info.base;
        this.slab = info.slab;
        this.stairs = info.stairs;
        this.wall = info.wall;

        if (this.block_id.equals("")) {
            LOGGER.severe(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
            return;
        }

        // base
        if (base != null) {
            if (this.isPlural) this.base = register(this.block_id + "s", base);
            else this.base = register(this.block_id, base);
        } else LOGGER.warning(SheetLib.log("BlockWithDecor '" + this.mod_id + ":" + this.block_id + "' has no base!"));
        // slab
        if (slab != null) this.slab = (SlabBlock)register(this.block_id + "_slab", slab);
        else this.slab = null;
        // stairs
        if (stairs != null) this.stairs = (TerraformStairsBlock)register(this.block_id + "_stairs", stairs);
        else this.stairs = null;
        // wall
        if (wall != null) this.wall = (WallBlock)register(this.block_id + "_wall", wall);
        else this.wall = null;

        if (this.isFlammable) {
            FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
            flammableBlockRegistry.add(this.base, 5, 20);
            flammableBlockRegistry.add(this.slab, 5, 20);
            flammableBlockRegistry.add(this.stairs, 5, 20);

            FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
            fuelRegistry.add(this.base, this.fuelTime);
            fuelRegistry.add(this.slab, this.fuelTime / 2);
            fuelRegistry.add(this.stairs, this.fuelTime);
        }
    }

    private Block register(String id, Block block) {
        return SheetLib.block(this.mod_id, id, this.item_group, block);
    }

    public static class Info {
        public Info() {
            if (isFlammable) fuelTime = 300;
                else fuelTime = 0;
        }

        public String mod_id = "";
        public String block_id = "";
        public ItemGroup item_group = null;

        public boolean isPlural = false;
        public boolean isFlammable = true;
        public int fuelTime;

        public Block base = null;
        public SlabBlock slab = null;
        public TerraformStairsBlock stairs = null;
        public WallBlock wall = null;
    }
}