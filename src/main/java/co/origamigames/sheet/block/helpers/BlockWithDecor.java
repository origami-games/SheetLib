package co.origamigames.sheet.block.helpers;

import co.origamigames.sheet.SheetLib;
import co.origamigames.sheet.block.*;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class BlockWithDecor {

    public String mod_id;

    public String block_id;
    public ItemGroup item_group;

    public boolean isPlural;
    public boolean isFlammable;
    public int fuelTime;

    public Block base;
    public SlabBlock slab;
    public PublicStairsBlock stairs;
    public WallBlock wall;

    public BlockWithDecor(BlockWithDecor.Info info) {
        this.mod_id = info.mod_id;

        this.block_id = info.block_id;
        this.item_group = info.item_group;

        if (this.block_id.equals("")) {
            SheetLib.LOGGER.error(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
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
            SheetLib.LOGGER.error(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
            return;
        }

        // base
        if (base != null) {
            if (this.isPlural) this.base = register(this.block_id + "s", base);
            else this.base = register(this.block_id, base);
        } else SheetLib.LOGGER.warning(SheetLib.log("BlockWithDecor '" + this.mod_id + ":" + this.block_id + "' has no base!"));
        // slab
        if (slab != null) this.slab = (SlabBlock)register(this.block_id + "_slab", slab);
        else this.slab = null;
        // stairs
        if (stairs != null) this.stairs = (PublicStairsBlock)register(this.block_id + "_stairs", stairs);
        else this.stairs = null;
        // wall
        if (wall != null) this.wall = (WallBlock)register(this.block_id + "_wall", wall);
        else this.wall = null;

        if (this.isFlammable) {
            FireBlock flammableBlockRegistry = (FireBlock)Blocks.FIRE;
            flammableBlockRegistry.setFireInfo(this.base, 5, 20);
            flammableBlockRegistry.setFireInfo(this.slab, 5, 20);
            flammableBlockRegistry.setFireInfo(this.stairs, 5, 20);

//            FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
//            fuelRegistry.add(this.base, this.fuelTime);
//            fuelRegistry.add(this.slab, this.fuelTime / 2);
//            fuelRegistry.add(this.stairs, this.fuelTime);
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
        public PublicStairsBlock stairs = null;
        public WallBlock wall = null;
    }
}
