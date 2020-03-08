package co.origamigames.sheet.block.helpers;

import com.terraformersmc.terraform.entity.TerraformBoatEntity;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

public class WoodInfo {
    public String name;
    public boolean hasLeaves = true;
    public boolean isFlammable = true;
    public Supplier<EntityType<TerraformBoatEntity>> boatEntity = null;
    public WoodBlocks blocks;
    public MaterialColor barkColor;
    public MaterialColor plankColor;
    public MaterialColor leafColor = MaterialColor.FOLIAGE;

    public boolean getUsesExtendedLeaves() {
        return hasLeaves;
    }
}
