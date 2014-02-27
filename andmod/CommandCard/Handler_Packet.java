package andmod.CommandCard;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class Handler_Packet implements IPacketHandler {

	@Override
	public void onPacketData( INetworkManager manager, Packet250CustomPayload packet, Player player ) {
		if ( packet.channel.equals( "AM_CC_CommandGui" ) ) {
			
			DataInputStream istream = new DataInputStream( new ByteArrayInputStream( packet.data ) );
			
			int nameSize, commandSize, color, flag;
			byte[] buf;
			String name, command;
			
			try {
				
				nameSize = istream.readInt();
				commandSize = istream.readInt();
				color = istream.readInt();
				flag = istream.readInt();
				
				buf = new byte[nameSize];
				istream.read( buf, 0, nameSize );
				name = new String( buf, "UTF-8" ).trim();
				
				buf = new byte[commandSize];
				istream.read( buf, 0, commandSize );
				command = new String( buf, "UTF-8" ).trim();
				
				
				//System.out.println( "Packet was received; name=" + name + "(" + nameSize + "), command=" + command + "(" + commandSize + ")" );
				
				if ( player instanceof EntityPlayerMP ) {
					EntityPlayerMP eplayer = (EntityPlayerMP)player;
					ItemStack items = eplayer.getCurrentEquippedItem();
					
					if ( items != null && items.itemID == AndMod_CommandCard.getItemIDforCraft( "CommandCard" ) ) {
						
						//checkme
						if ( items.getTagCompound() == null ) {
							Item_CommandCard.initNBT( items );
						}
						
						Item_CommandCard.setColor( items, color );
						
						if ( !name.equals( "" ) && !name.equals( items.getItemName() ) )
							items.setItemName( name );
						else
							items.setItemName( "" );
						
						items.getTagCompound().setString( "Command", command );
						eplayer.addChatMessage( "Command set: " + command );
						
						eplayer.inventoryContainer.detectAndSendChanges();
						
						//System.out.println( "[ITEM] name=" + items.getDisplayName() + ", command=" + items.getTagCompound().getString( "Command" ) );
					}
				}
				
				
				if ( ( flag & 1 ) != 0 ) {
					AndMod_CommandCard.addCommandData( name, command, color );
				}
				
				
			} catch( IOException e ) {
				e.printStackTrace();
				return;
			}
			
		}
	}

}
