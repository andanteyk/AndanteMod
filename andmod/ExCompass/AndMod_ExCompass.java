package andmod.ExCompass;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		modid	= "AndanteMod_ExCompass",
		name	= "ExCompass",
		version	= "1.6.2.1"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_ExCompass {

	//point: class

	public static int aitemqty = 3;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23648;

	public static String ObjectHeader = "ExCompass:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	private int clockDisplayMode = 0;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;


		id++;
		aitemname[id][0] = "MemorableCompass";
		aitemname[id][1] = "Memorable Compass";
		aitemname[id][2] = "記憶のコンパス";
		id++;
		aitemname[id][0] = "EnderCompass";
		aitemname[id][1] = "Ender Compass";
		aitemname[id][2] = "エンダーコンパス";
		id++;
		aitemname[id][0] = "LunarClock";
		aitemname[id][1] = "Lunar Clock";
		aitemname[id][2] = "月時計";

		//point: add new item name


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property itemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				itemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = itemProp.getInt();
			}

			Property prop = cfg.get( "Clock", "clockDisplayMode", clockDisplayMode );
			prop.comment = "Specify the time format. [0-2] 0=24h, 1=12h(AM/PM), 2=raw data." ;
			clockDisplayMode = prop.getInt();
			if ( clockDisplayMode < 0 ) clockDisplayMode = 0;
			if ( clockDisplayMode > 2 ) clockDisplayMode = 2;

		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;

		//Memorable Compass
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Compass( aitemID[id] ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.blockLapis, 1, 0 ),
					'b', new ItemStack( Item.compass, 1, 0 ) );
		}


		//Ender Compass
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_CompassEnder( aitemID[id], 4 - 1 ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Item.enderPearl, 1, 0 ),
					'b', new ItemStack( Item.compass, 1, 0 ) );

		}


		//Lunar Clock
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Clock( aitemID[id], clockDisplayMode ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.blockNetherQuartz, 1, fm ),
					'b', new ItemStack( Item.pocketSundial, 1, 0 ) );

		}


	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}