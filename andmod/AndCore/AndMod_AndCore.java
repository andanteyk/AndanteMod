package andmod.AndCore;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_AndCore",
		name	= "AndCore",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_AndCore {

	//point: class

	public static int aitemqty = 0;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 0;

	public static int ablockqty = 1;
	public static int[] ablockID = new int[ablockqty];
	public static Block[] ablock = new Block[ablockqty];
	public static String[][] ablockname = new String[ablockqty][3];
	public int ablockIDdefault = 2366;
	
	public static String ObjectHeader = "AndCore:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		/*
		id++;
		aitemname[id][0] = "Chocolate";
		aitemname[id][1] = "Chocolate";
		aitemname[id][2] = "チョコレート";
		 */
		//point: add new item name

		id ++;
		ablockname[id][0] = "Luminescence";
		ablockname[id][1] = "Luminescence";
		ablockname[id][2] = "蛍の光";
		
		//point: add new block name
		

		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property itemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				itemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = itemProp.getInt();
			}
			
			for( int i = 0; i < ablockID.length; i++ ) {
				Property blockProp = cfg.getBlock( "BlockID_" + ablockname[i][0], ablockIDdefault + i );
				blockProp.comment = "BlockID - " + ablockname[i][1];
				ablockID[i] = blockProp.getInt();
			}


		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];
		for( int i = 0; i < ablockqty; i++ )
			ablockname[i][0] = ObjectHeader + ablockname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;

		id++;
		ablock[id] = new Block_Lighting( ablockID[id] ).setLightOpacity( 0 ).setHardness( 0.0F ).setResistance( 0.0F )
		.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

		GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
		LanguageRegistry.addName( ablock[id], ablockname[id][1] );
		LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
	
		
	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}
	
	public static int getBlockID( String name ) {
		name = ObjectHeader + name;
		
		for ( int i = 0; i < ablockname.length; i ++ ) 
			if ( ablockname[i][0].equals( name ) ) return ablockID[i];
		return 0;
	}
		
}