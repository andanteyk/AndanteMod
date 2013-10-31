package andmod.Pendulum;

import java.util.logging.Level;

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
		modid	= "AndanteMod_Pendulum",
		name	= "Pendulum",
		version	= "1.6.2.0" /*,
		dependencies = "required-after:AndanteMod_AndCore" */
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_Pendulum {

	//point: class

	public static int aitemqty = 3;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 24110;

	public static String ObjectHeader = "Pendulum:";

	private int fm = OreDictionary.WILDCARD_VALUE;
	
	
	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "TreasurePendulum";
		aitemname[id][1] = "Pendulum of Treasure Hunter";
		aitemname[id][2] = "宝探しのペンデュラム";

		id++;
		aitemname[id][0] = "ArchaeologistPendulum";
		aitemname[id][1] = "Pendulum of Archaeologist";
		aitemname[id][2] = "考古学者のペンデュラム";

		id++;
		aitemname[id][0] = "StrangerPendulum";
		aitemname[id][1] = "Pendulum of Stranger";
		aitemname[id][2] = "旅人のペンデュラム";

		//point: add new item name


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property ItemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				ItemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = ItemProp.getInt();
			}
			
			Property prop = cfg.get( "General", "canTranslateEnglish", Item_Pendulum.canTranslateEnglish );
			prop.comment = "Specify whether when language settings are English, translate message to *broken* English. [true/false]";
			Item_Pendulum.canTranslateEnglish = prop.getBoolean( Item_Pendulum.canTranslateEnglish );

		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;

		//Pendulum of Treasure Hunter
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Pendulum( aitemID[id], Item_Pendulum.TARGET_MINESHAFT | Item_Pendulum.TARGET_SCATTERED )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"s",
					"s",
					"g",
					's', new ItemStack( Item.silk, 1, 0 ),
					'g', new ItemStack( Item.diamond, 1, 0 ) );
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 1 ),
					"ggg",
					"gpg",
					"ggg",
					'p', new ItemStack( aitem[id], 1, 0 ),
					'g', new ItemStack( Item.diamond, 1, 0 ) );
		}

		
		//Pendulum of Archaeologist
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Pendulum( aitemID[id], Item_Pendulum.TARGET_STRONGHOLD | Item_Pendulum.TARGET_NETHERBRIGDE )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"s",
					"s",
					"g",
					's', new ItemStack( Item.silk, 1, 0 ),
					'g', new ItemStack( Item.enderPearl, 1, 0 ) );
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 1 ),
					"ggg",
					"gpg",
					"ggg",
					'p', new ItemStack( aitem[id], 1, 0 ),
					'g', new ItemStack( Item.enderPearl, 1, 0 ) );
		}


		//Pendulum of Stranger
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Pendulum( aitemID[id], Item_Pendulum.TARGET_VILLAGE )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"s",
					"s",
					"g",
					's', new ItemStack( Item.silk, 1, 0 ),
					'g', new ItemStack( Item.emerald, 1, 0 ) );
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 1 ),
					"ggg",
					"gpg",
					"ggg",
					'p', new ItemStack( aitem[id], 1, 0 ),
					'g', new ItemStack( Item.emerald, 1, 0 ) );
		}
		
	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}