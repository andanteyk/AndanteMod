package andmod.CommandCard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class Gui_Container extends Container {

	public Gui_Container( EntityPlayer eplayer, World world, int x, int y, int z ) {
		
	}

	@Override
	public boolean canInteractWith( EntityPlayer eplayer ) {
		return true;
	}

}
