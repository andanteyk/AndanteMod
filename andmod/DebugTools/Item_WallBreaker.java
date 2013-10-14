package andmod.DebugTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Item_WallBreaker extends Item {

	public Item_WallBreaker( int itemID ) {
		super( itemID );
	}

	
	@Override
	public void onUpdate( ItemStack items, World world, Entity entity, int index, boolean onhand ) {
		if ( !world.isRemote && onhand ) {
			int px = MathHelper.floor_double( entity.posX );
			int py = MathHelper.floor_double( entity.posY );
			int pz = MathHelper.floor_double( entity.posZ );
			int bound = 3;
			
			for ( int x = -bound; x <= bound; x ++ ) {
				for ( int z = -bound; z <= bound; z ++ ) {
					
					//if ( Math.abs( x ) + Math.abs( z ) > bound ) continue;
					
					for ( int y = 0; y <= 2; y ++ ) {
						//world.destroyBlock( px + x, py + y, pz + z, false );
						world.setBlockToAir( px + x, py + y, pz + z );
					}
				}
			}
			
		}
	}


	@Override
	public boolean onItemUse( ItemStack items, EntityPlayer eplayer, World world, int bx, int by, int bz, int side, float fx, float fy, float fz ) {
		for ( int y = 0; y < 4; y ++ ) {
			world.setBlockToAir( bx, by - y, bz );
		}
		return true;
	}

}
