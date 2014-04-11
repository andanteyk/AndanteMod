package andmod.CommandCard;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Item_CommandCard extends Item implements ICommandSender, IRecipe {

	public static final int defaultColor = 0xDDD7D2;

	public static ArrayList<String> CommandList = new ArrayList<String>();
	public static ArrayList<String> NameList = new ArrayList<String>();
	public static ArrayList<Integer> ColorList = new ArrayList<Integer>();

	@SideOnly( Side.CLIENT )
    protected Icon iconFore;
	@SideOnly( Side.CLIENT )
	protected Icon iconBack;


	public Item_CommandCard( int itemID ) {
		super( itemID );

		setMaxStackSize( 1 );
		/*/
		setMaxDamage( 0 );
		setHasSubtypes( true );
		//*/
	}


	/*/ //うまく動かないので封印します。
	@Override
	public boolean onItemUseFirst( ItemStack items, EntityPlayer eplayer, World world, int bx, int by, int bz, int side, float px, float py, float pz ) {

		System.out.println( "Side: " + world.isRemote );


		if ( world.getBlockId( bx, by, bz ) == Block.commandBlock.blockID ) {

			//if ( world.isRemote ) return true;

			TileEntityCommandBlock tecom = (TileEntityCommandBlock)world.getBlockTileEntity( bx, by, bz );
			String icom = getCommand( items );


			if ( icom != null && icom.length() > 0 ) {		//copy to block

				tecom.setCommand( icom );


				eplayer.addChatMessage( "Copied to Command Block : " + icom );
				return true;


			} else {		//copy from block

				String command = tecom.getCommand();
				if ( command != null && command.length() > 0 ) {

					setCommand( items, command );

					eplayer.addChatMessage( "Copied from Command Block : " + command );
					return true;
				}
			}

		}

		return false;
	}
	//*/


	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		NBTTagCompound nbt = items.getTagCompound();

		if ( nbt == null )
			initNBT( items );


		if ( !world.isRemote ) {

			if ( eplayer.isSneaking() ) {

				eplayer.openGui( AndMod_CommandCard.instance, AndMod_CommandCard.guiID, world, MathHelper.floor_double( eplayer.posX ), MathHelper.floor_double( eplayer.posY ), MathHelper.floor_double( eplayer.posZ ) );
				return items;
			}


			MinecraftServer minecraftserver = MinecraftServer.getServer();

	        if ( minecraftserver != null && minecraftserver.isCommandBlockEnabled() ) {
	            ICommandManager icommandmanager = minecraftserver.getCommandManager();
	            String com = getCommand( items );
	            if ( com != null && com.length() > 0 )
	            	icommandmanager.executeCommand( this, com );
	        }


		}


		return items;
	}



	@Override
	@SideOnly( Side.CLIENT )
	public void getSubItems( int itemID, CreativeTabs tab, List list ) {

		list.add( new ItemStack( itemID, 1, 0 ) );

		for ( int i = 0; i < CommandList.size(); i ++ ) {
			ItemStack items = new ItemStack( itemID, 1, 0 );
			initNBT( items );

			setCommand( items, CommandList.get( i ) );


			String name = NameList.get( i );
			if ( name.length() > 0 && !name.equals( items.getItem().getLocalizedName( items ) ) )
				items.setItemName( name );

			setColor( items, ColorList.get( i ) );

			list.add( items );
		}

	}


	@Override
	@SideOnly( Side.CLIENT )
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {

		if ( items.getTagCompound() == null ) {
			initNBT( items );
		}


		String com = getCommand( items );
		if ( com.length() <= 0 )
			list.add( EnumChatFormatting.ITALIC + "Empty" + EnumChatFormatting.RESET );
		else
			list.add( com );

	}



	public static NBTTagCompound initNBT( ItemStack items ) {

		if ( items.getTagCompound() == null ) {
			items.setTagCompound( new NBTTagCompound() );
		}

		items.getTagCompound().setString( "Command", "" );
		items.getTagCompound().setInteger( "Color", 0xDDD7D2 );

		return items.getTagCompound();
	}


	public static void setCommand( ItemStack items, String command ) {
		if ( items.getTagCompound() == null )
			initNBT( items );

		items.getTagCompound().setString( "Command", command );
	}


	public static String getCommand( ItemStack items ) {
		if ( items.getTagCompound() == null ) return null;

		return items.getTagCompound().getString( "Command" );
	}


	public static void setColor( ItemStack items, int color ) {
		if ( items.getTagCompound() == null )
			initNBT( items );

		items.getTagCompound().setInteger( "Color", color );
	}


	public static int getColor( ItemStack items ) {
		if ( items.getTagCompound() == null )
			initNBT( items );

		return items.getTagCompound().getInteger( "Color" );
	}


	/*
	public static void addCommand( String name, String command ) {
		addCommand( name, command, defaultColor );
	}
	*/


	public static void addCommand( String name, String command, int color ) {
		if ( name != null && command != null ) {
			NameList.add( name );
			CommandList.add( command );
			ColorList.add( color );
		}
	}

	public static boolean removeCommand( String name, String command ) {

		for ( int i = 0; i < CommandList.size(); i ++ ) {
			if ( CommandList.get( i ).equals( command ) && NameList.get( i ).equals( name ) ) {
				NameList.remove( i );
				CommandList.remove( i );

				return true;
			}
		}

		return false;
	}




	//コマンド使用関連
	@Override
	public String getCommandSenderName() {
		/*/
		return Minecraft.getMinecraft().thePlayer.username;
		/*/
		return "@";
		//*/
	}

	@Override
	public void sendChatToPlayer( ChatMessageComponent chat ) {
	}

	@Override
	public boolean canCommandSenderUseCommand( int i, String s ) {
		return i <= 2;	//FIXME
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		/*/
		Minecraft mc = Minecraft.getMinecraft();
		return new ChunkCoordinates( mc.thePlayer.chunkCoordX, mc.thePlayer.chunkCoordY, mc.thePlayer.chunkCoordZ );
		/*/
		return new ChunkCoordinates( 0, 0, 0 );
		//*/
	}

	@Override
	public World func_130014_f_() {
		return MinecraftServer.getServer().func_130014_f_();
	}


	//テクスチャ関連
	@SideOnly( Side.CLIENT )
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}


	@SideOnly( Side.CLIENT )
	@Override
	public Icon getIconFromDamageForRenderPass( int meta, int pass ) {
		return pass > 0 ? iconFore : iconBack;
	}


	@SideOnly( Side.CLIENT )
	@Override
	public void registerIcons( IconRegister ireg ) {

		iconBack = ireg.registerIcon( this.getUnlocalizedName().substring( 5 ) + "_Back" );
		iconFore = ireg.registerIcon( this.getUnlocalizedName().substring( 5 ) + "_Fore" );
	}


	@SideOnly( Side.CLIENT )
	@Override
	public int getColorFromItemStack( ItemStack items, int pass ) {
		return pass > 0 ? 0xFFFFFF : getColor( items );
	}




	//染色レシピ関連
	@Override
	public boolean matches( InventoryCrafting ic, World world ) {

		boolean isCommandCard = false;
		int dyecount = 0;

		for ( int i = 0; i < ic.getSizeInventory(); i ++ ) {
			ItemStack items = ic.getStackInSlot( i );

			if ( items == null ) continue;

			if ( items.itemID == AndMod_CommandCard.getItemIDforCraft( "CommandCard" ) ) {
				if ( isCommandCard ) return false;
				isCommandCard = true;

			} else if ( items.itemID == Item.dyePowder.itemID ) {
				dyecount ++;

			} else return false;
		}

		return isCommandCard && dyecount > 0;
	}


	@Override
	public ItemStack getCraftingResult( InventoryCrafting ic ) {

		ItemStack commandCard = null;
		int r = 0, g = 0, b = 0;
		int dyecount = 0;

		for ( int i = 0; i < ic.getSizeInventory(); i ++ ) {
			ItemStack items = ic.getStackInSlot( i );

			if ( items == null ) continue;

			if ( items.itemID == AndMod_CommandCard.getItemIDforCraft( "CommandCard" ) ) {
				commandCard = items.copy();

			} else if ( items.itemID == Item.dyePowder.itemID ) {
				dyecount ++;

				switch( items.getItemDamage() ) {
				case 0x0:	r += 0x00; g += 0x00; b += 0x00; break; 	//Black
				case 0x1:	r += 0xFF; g += 0x00; b += 0x00; break; 	//Red
				case 0x2:	r += 0x00; g += 0x80; b += 0x00; break; 	//Green
				case 0x3:	r += 0x40; g += 0x00; b += 0x00; break; 	//Brown
				case 0x4:	r += 0x00; g += 0x00; b += 0xFF; break; 	//Blue
				case 0x5:	r += 0x80; g += 0x00; b += 0x80; break; 	//Purple
				case 0x6:	r += 0x00; g += 0x80; b += 0x80; break; 	//Cyan
				case 0x7:	r += 0xC0; g += 0xC0; b += 0xC0; break; 	//Light Gray
				case 0x8:	r += 0x80; g += 0x80; b += 0x80; break; 	//Gray
				case 0x9:	r += 0xFF; g += 0x80; b += 0xFF; break; 	//Pink
				case 0xA:	r += 0x00; g += 0xFF; b += 0x00; break; 	//Light Green
				case 0xB:	r += 0xFF; g += 0xFF; b += 0x00; break; 	//Yellow
				case 0xC:	r += 0x00; g += 0xFF; b += 0xFF; break; 	//Sky Blue
				case 0xD:	r += 0xFF; g += 0x00; b += 0xFF; break; 	//Magenta
				case 0xE:	r += 0xFF; g += 0x80; b += 0x00; break; 	//Orange
				case 0xF:	r += 0xFF; g += 0xFF; b += 0xFF; break; 	//White
				}
			}
		}

		if ( commandCard == null || dyecount == 0 )
			return null;


		r /= dyecount;
		g /= dyecount;
		b /= dyecount;

		setColor( commandCard, ( r << 16 ) | ( g << 8 ) | b );

		return commandCard;
	}


	@Override
	public int getRecipeSize() {
		return 9;
	}


	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}


}
