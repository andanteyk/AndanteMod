package andmod.AndCore;

import net.minecraft.item.ItemStack;

public interface IArrow {
	
	int getArrowID( ItemStack items );
	boolean canConsumeArrow( ItemStack items );
	ItemStack consumeArrow( ItemStack items );
	
}
