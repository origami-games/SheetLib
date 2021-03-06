package co.origamigames.sheet.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;

@SuppressWarnings({"unused","deprecated"})
public class PistonImmovableBlock extends Block {
    public PistonImmovableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState blockState) {
        return PistonBehavior.BLOCK;
    }
}
