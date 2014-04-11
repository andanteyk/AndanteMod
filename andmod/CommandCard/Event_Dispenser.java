package andmod.CommandCard;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public class Event_Dispenser extends BehaviorDefaultDispenseItem implements ICommandSender {

	//private Minecraft mc = Minecraft.getMinecraft();


	@Override
	protected ItemStack dispenseStack( IBlockSource ibs, ItemStack items ) {

		if ( !ibs.getWorld().isRemote ) {
 			MinecraftServer minecraftserver = MinecraftServer.getServer();

            if ( minecraftserver != null && minecraftserver.isCommandBlockEnabled() ) {
                ICommandManager icommandmanager = minecraftserver.getCommandManager();
                String com = Item_CommandCard.getCommand( items );
                if ( com != null && com.length() > 0 )
                	icommandmanager.executeCommand( this, com );
            }
		}

		return items;
	}

	@Override
	protected void playDispenseSound( IBlockSource par1iBlockSource ) {
	}

	@Override
	protected void spawnDispenseParticles( IBlockSource par1iBlockSource, EnumFacing par2EnumFacing ) {
	}



	@Override
	public String getCommandSenderName() {
		return "@";
	}

	@Override
	public void sendChatToPlayer( ChatMessageComponent chatmessagecomponent ) {
	}

	@Override
	public boolean canCommandSenderUseCommand( int i, String s ) {
		return i <= 2;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		/*/
		return new ChunkCoordinates( MathHelper.floor_double( mc.thePlayer.posX ), MathHelper.floor_double( mc.thePlayer.posY ), MathHelper.floor_double( mc.thePlayer.posZ ) );		//checkme
		/*/
		return new ChunkCoordinates( 0, 0, 0 );
		//*/
	}

	@Override
	public World func_130014_f_() {
		return MinecraftServer.getServer().func_130014_f_();
	}



}
