package andmod.CommandCard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class Gui_Handler implements IGuiHandler {

	public Gui_Handler() {
	}

	@Override
	public Object getServerGuiElement( int ID, EntityPlayer eplayer, World world, int x, int y, int z ) {
		if ( ID == AndMod_CommandCard.guiID )
			return new Gui_Container( eplayer, world, x, y, z );
		return null;
	}

	@Override
	public Object getClientGuiElement( int ID, EntityPlayer eplayer, World world, int x, int y, int z ) {
		//*/
		if ( ID == AndMod_CommandCard.guiID )
			return new Gui_CommandCard( eplayer, world );
		
		//*/
		return null;	//checkme
	}

}
