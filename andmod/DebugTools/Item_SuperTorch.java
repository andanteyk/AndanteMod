package andmod.DebugTools;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import andmod.AndCore.AndMod_AndCore;

public class Item_SuperTorch extends Item {

	public Item_SuperTorch( int itemID ) {
		super( itemID );
	}

	
	@Override
	public void onUpdate( ItemStack items, World world, Entity entity, int index, boolean onhand ) {
		
		int bx = MathHelper.floor_double( entity.posX ), by = MathHelper.floor_double( entity.posY ), bz = MathHelper.floor_double( entity.posZ );
		if ( world.isAirBlock( bx, by, bz ) ) {
			world.setBlock( bx, by, bz, AndMod_AndCore.getBlockID( "Luminescence" ), 0, 2 );
		}
		
	}

	
}
