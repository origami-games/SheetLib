package co.origamigames.sheet.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

@SuppressWarnings({"unused","deprecated"})
public class PublicStairsBlock extends StairsBlock {
    public PublicStairsBlock(Block base, Properties settings) {
        super(base.getDefaultState(), settings);
    }
}
