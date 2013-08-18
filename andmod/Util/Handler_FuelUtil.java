package andmod.Util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class Handler_FuelUtil implements IFuelHandler {

	public Handler_FuelUtil() {
	}

	@Override
	public int getBurnTime( ItemStack fuel ) {
		
		if ( fuel.itemID == Item.seeds.itemID )
			return 200 / 4;
		else if ( fuel.itemID == Block.leaves.blockID )
			return 200 / 2;
		else if ( fuel.itemID == Block.deadBush.blockID )
			return 200;
		else if ( fuel.itemID == Block.tallGrass.blockID )
			return 200 / 2;
		else if ( fuel.itemID == Block.cloth.blockID )
			return 200;
		else if ( fuel.itemID == Item.reed.itemID )
			return 200 / 2;
		else if ( fuel.itemID == Item.wheat.itemID )
			return 200 * 3 / 4;
		else if ( fuel.itemID == Item.bowlEmpty.itemID )
			return 200;
		else if ( fuel.itemID == Item.blazePowder.itemID )
			return 200 * 6;
		else if ( fuel.itemID == Item.magmaCream.itemID )
			return 200 * 12;
		else if ( fuel.itemID == Item.doorWood.itemID )
			return 200 * 4;
		else if ( fuel.itemID == Item.boat.itemID )
			return 200 * 4;
		else if ( fuel.itemID == Block.field_111038_cB.blockID )
			return (int)( 200 * 7.5 );
		
		
		return 0;
	}

}
