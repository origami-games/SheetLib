package co.origamigames.sheet.block;

import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

public class ClassicLogBlock extends PillarBlock {
    public ClassicLogBlock(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
        super(Settings.of(Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    }
}
