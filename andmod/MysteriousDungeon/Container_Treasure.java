package andmod.MysteriousDungeon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class Container_Treasure extends Container {
	
	private TileEntity_Treasure ttreasure;

	public Container_Treasure( EntityPlayer eplayer, TileEntity_Treasure tileentity ) {
		ttreasure = tileentity;
		
		//addSlotToContainer( ttreasure. )
	}

	@Override
	public boolean canInteractWith( EntityPlayer entityplayer ) {
		return false;
	}

}
