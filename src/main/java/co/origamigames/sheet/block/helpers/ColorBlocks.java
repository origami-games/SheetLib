package co.origamigames.sheet.block.helpers;

import co.origamigames.sheet.SheetLib;
import co.origamigames.sheet.block.PublicCarpetBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class ColorBlocks {
    public String mod_id;

    public String color;
    public ItemGroup item_group;

    public boolean hasDye;
    public boolean isFlammable;
    public int fuelTime;

    public DyeItem dye;

    public Block wool;
    public Block terracotta;
    public GlazedTerracottaBlock glazed_terracotta;
    public Block concrete;
    public ConcretePowderBlock concrete_powder;
    public StainedGlassBlock stained_glass;
    public StainedGlassPaneBlock stained_glass_pane;
    public CarpetBlock carpet;
    public ShulkerBoxBlock shulker_box;
    public BannerItem bannerItem;
    public BannerBlock bannerBlock;
    public WallBannerBlock wall_bannerBlock;
    public BedItem bedItem;
    public BedBlock bedBlock;

    public ColorBlocks(ColorBlocks.Info info) {
        this.mod_id = info.mod_id;

        this.color = info.color;
        this.item_group = info.item_group;

        if (this.color.equals("")) {
            SheetLib.LOGGER.severe(SheetLib.log("No id is defined for " + info + " in mod " + this.mod_id + "!"));
            return;
        }

        this.hasDye = info.hasDye;
        this.isFlammable = info.isFlammable;
        this.fuelTime = info.fuelTime;

        this.dye = info.dye;

        this.wool = register(this.color + "_wool", info.wool);
        this.terracotta = register(this.color + "_terracotta", info.terracotta);
        this.glazed_terracotta = (GlazedTerracottaBlock)register(this.color + "_glazed_terracotta", info.glazed_terracotta);
        this.concrete = register(this.color + "_concrete", info.concrete);
        this.concrete_powder = (ConcretePowderBlock)register(this.color + "_concrete_powder", info.concrete_powder);
        this.stained_glass = (StainedGlassBlock)register(this.color + "_stained_glass", info.stained_glass);
        this.stained_glass_pane = (StainedGlassPaneBlock)register(this.color + "_stained_glass_pane", info.stained_glass_pane);
        this.carpet = (CarpetBlock)register(this.color + "_carpet", info.carpet);
        this.shulker_box = (ShulkerBoxBlock)register(this.color + "_shulker_box", info.shulker_box);
        this.bannerItem = (BannerItem)SheetLib.item(this.mod_id, this.color + "_banner", info.bannerItem);
        this.bannerBlock = (BannerBlock)register(this.color + "_banner", info.bannerBlock);
        this.wall_bannerBlock = (WallBannerBlock)register(this.color + "_wall_banner", info.wall_bannerBlock);
        this.bedItem = (BedItem)SheetLib.item(this.mod_id, this.color + "_bed", info.bedItem);
        this.bedBlock = (BedBlock)register(this.color + "_bed", info.bedBlock);

        if (this.isFlammable) {
            FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();
//            flammableBlockRegistry.add(this.base, 5, 20);

            FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
//            fuelRegistry.add(this.base, this.fuelTime);
        }
    }

    private Block register(String id, Block block) {
        if (block instanceof BannerBlock || block instanceof WallBannerBlock || block instanceof BedBlock) {
            return Registry.register(Registry.BLOCK, id, block);
        } else return SheetLib.block(this.mod_id, id, this.item_group, block);
    }

    public static class Info {
        public Info() {
            if (isFlammable) fuelTime = 300;
            else fuelTime = 0;
        }

        public String mod_id = "";
        public String color = "";
        public DyeColor dyeColor = DyeColor.WHITE;
        public ItemGroup item_group = ItemGroup.DECORATIONS;

        public boolean hasDye = true;
        public boolean isFlammable = true;
        public int fuelTime;

        public DyeItem dye = new DyeItem(dyeColor, new Item.Settings().group(item_group));

        public Block wool = new Block(FabricBlockSettings.copy(Blocks.WHITE_WOOL).build());
        public Block terracotta = new Block(FabricBlockSettings.copy(Blocks.WHITE_TERRACOTTA).build());
        public GlazedTerracottaBlock glazed_terracotta = new GlazedTerracottaBlock(FabricBlockSettings.copy(Blocks.WHITE_GLAZED_TERRACOTTA).build());
        public Block concrete = new Block(FabricBlockSettings.copy(Blocks.WHITE_CONCRETE).build());
        public ConcretePowderBlock concrete_powder = new ConcretePowderBlock(concrete, FabricBlockSettings.copy(Blocks.WHITE_CONCRETE_POWDER).build());
        public StainedGlassBlock stained_glass = new StainedGlassBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_STAINED_GLASS).build());
        public StainedGlassPaneBlock stained_glass_pane = new StainedGlassPaneBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_STAINED_GLASS_PANE).build());
        public CarpetBlock carpet = new PublicCarpetBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_CARPET).build());
        public ShulkerBoxBlock shulker_box = new ShulkerBoxBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_SHULKER_BOX).build());
        public BannerBlock bannerBlock = new BannerBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_BANNER).build());
        public WallBannerBlock wall_bannerBlock = new WallBannerBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_WALL_BANNER).build());
        public BannerItem bannerItem = new BannerItem(bannerBlock, wall_bannerBlock, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16));
        public BedBlock bedBlock = new BedBlock(dyeColor, FabricBlockSettings.copy(Blocks.WHITE_BED).build());
        public BedItem bedItem = new BedItem(bedBlock, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(1));
    }
}
