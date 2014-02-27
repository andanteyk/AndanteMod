package andmod.CommandCard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;

import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_CommandCard",
		name	= "CommandCard",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		channels = { "AM_CC_CommandGui" },
		packetHandler = Handler_Packet.class
		)



public class AndMod_CommandCard {

	//point: class

	public static int aitemqty = 1;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 24142;

	
	public static String ObjectHeader = "CommandCard:";

	private int fm = OreDictionary.WILDCARD_VALUE;

	@Mod.Instance( "AndanteMod_CommandCard" )
	public static AndMod_CommandCard instance;
	public static final int guiID = 1;
	
	
	public static File configFile;
	
	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		
		id++;
		aitemname[id][0] = "CommandCard";
		aitemname[id][1] = "Command Card";
		aitemname[id][2] = "コマンドカード";
		//point: add new item name

		

		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property itemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				itemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = itemProp.getInt();
			}
			
			
			//Get Command List;
			configFile = new File( event.getModConfigurationDirectory().getAbsolutePath() + "\\AndanteMod_CommandCard_CommandList.txt" );
			getCommandData( configFile );

			
		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];
	
	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;

		Handler_CreativeTab tabCommandCard;
		
		
		//Command Card
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_CommandCard( aitemID[id] )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	
			

			tabCommandCard = new Handler_CreativeTab( "CommandCard", aitem[id].itemID, "Command Card" );
			
			aitem[id].setCreativeTab( tabCommandCard );
			
			NetworkRegistry.instance().registerGuiHandler( this, new Gui_Handler() );
			
			LanguageRegistry.instance().addStringLocalization( "CommandCard.gui.register", "en_US", "Register" );
			LanguageRegistry.instance().addStringLocalization( "CommandCard.gui.register", "ja_JP", "登録" );
			LanguageRegistry.instance().addStringLocalization( "CommandCard.gui.name", "en_US", "Name" );
			LanguageRegistry.instance().addStringLocalization( "CommandCard.gui.name", "ja_JP", "アイテム名" );
			
			
			BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_Dispenser() );
			GameRegistry.addRecipe( (IRecipe) aitem[id] );
		}
	
	}


	
	
	protected void getCommandData( File file ) {
		
		try {
			if ( !file.exists() ) {
				
				file.createNewFile();
				PrintWriter writer = new PrintWriter( new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file ), "UTF-8" ) ) );
				
				writer.println( "; AndanteMod_CommandCard - Command List" );
				writer.println( "" ); 
				writer.println( "Sunrise #FFFFCC /time set 1000" ); 
				writer.println( "Sunset #000066 /time set 13000" ); 
				writer.println( "Survival Mode #0000FF /gamemode 0 @p" ); 
				writer.println( "Creative Mode #0000FF /gamemode 1 @p" ); 
				writer.println( "Warp to the Sky #44FFFF /tp @p ~ ~128 ~" ); 
				writer.println( "Suicide #C00000 /tp @p ~ -10000 ~" ); 
				writer.println( "Immortal #FF80FF /effect @p 10 36000 255" ); 
				writer.println( "Full Health #FFEEFF /effect @p 21 36000 255" ); 
				writer.println( "Weather Switch /toggledownfall" ); 
				//writer.println( "Goggle /give @p 314 1 0 {display:{Name:\"Hello\"}}" ); 
				
				writer.close();
				
				
			} 
			
			{
				BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( file ), "UTF-8" ) );
				String buf;
				while ( ( buf = reader.readLine() ) != null ) {
					
					int indexa, indexb;
					int color = Item_CommandCard.defaultColor;
					
					buf = buf.trim();
					if ( buf.startsWith( ";" ) ) continue;
					
					indexa = buf.indexOf( "/" );
					indexb = buf.indexOf( "#" );
					
					if ( indexb != -1 && indexb < indexa ) {
						try {
							color = Integer.parseInt( buf.substring( indexb + 1, indexb + 7 ), 16 );
							
						} catch ( NumberFormatException e ) {
							color = Item_CommandCard.defaultColor;
						}
					} else indexb = -1;
					
					if ( indexa != -1 ) {
						String name = buf.substring( 0, indexa );
						if ( indexb != -1 )
							name = name.substring( 0, indexb ) + name.substring( indexb + 7 );
						name = name.trim();
						
						Item_CommandCard.addCommand( name, buf.substring( indexa ).trim(), color );
					}
				
				}
				
				reader.close();
			}
		
		} catch ( IOException e ) {
			e.printStackTrace();
		}	
	
		
	}

	
	
	public static void addCommandData( String name, String command ) {
		addCommandData( name, command, Item_CommandCard.defaultColor );
	}
	
	
	public static void addCommandData( String name, String command, int color ) {
		
		if ( name == null || command == null || name.length() <= 0 || command.length() <= 0 ) 
			return;
		
		try {
			if ( !configFile.exists() ) {	
				configFile.createNewFile();	
			}
			
			PrintWriter writer = new PrintWriter( new BufferedWriter( new OutputStreamWriter( new FileOutputStream( configFile, true ), "UTF-8" ) ) );
			
			writer.println();
			writer.println( name + " " + ( color == Item_CommandCard.defaultColor ? "" : String.format( "#%1$06X ", color ) ) + command );
			writer.close();
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
	}
	

	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}
	
		
}