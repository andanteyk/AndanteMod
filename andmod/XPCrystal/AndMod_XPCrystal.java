package andmod.XPCrystal;

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
		modid	= "AndanteMod_XPCrystal",
		name	= "XP Crystal",
		version	= "1.6.2.0"
		/*
		, dependencies = "after:AndanteMod_GlassTool"
		 */
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)


public class AndMod_XPCrystal {

	//point: class

	public static int aitemqty = 5;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23604;

	private int fm = OreDictionary.WILDCARD_VALUE;


	public static String ObjectHeader = "XPCrystal:";


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "XPCrystal20";
		aitemname[id][1] = "XP Crystal";
		aitemname[id][2] = "経験クリスタル";

		id++;
		aitemname[id][0] = "XPCrystal100";
		aitemname[id][1] = "Higher XP Crystal";
		aitemname[id][2] = "上位の経験クリスタル";

		id++;
		aitemname[id][0] = "XPCrystal500";
		aitemname[id][1] = "Greater XP Crystal";
		aitemname[id][2] = "高位の経験クリスタル";

		id++;
		aitemname[id][0] = "XPCrystal2000";
		aitemname[id][1] = "Special XP Crystal";
		aitemname[id][2] = "素敵な経験クリスタル";

		id++;
		aitemname[id][0] = "XPCrystal10000";
		aitemname[id][1] = "Master XP Crystal";
		aitemname[id][2] = "奇跡の経験クリスタル";

		//point: add new item name




		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property ItemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				ItemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = ItemProp.getInt();
			}

		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}


	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		//Block CrystalGlass = Utility_Searcher.SearchBlock("GlassTool:CrystalGlass");





		int id = -1;

		//XP Crystal (20)
		id++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_XPCrystal( aitemID[id], 64, 20 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] ).setCreativeTab( CreativeTabs.tabMisc );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, aitem[id].getMaxDamage() ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.glass ),
					'b', new ItemStack( Item.book ) );
		}


		//XP Crystal (100)
		id++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_XPCrystal( aitemID[id], 64, 100 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] ).setCreativeTab( CreativeTabs.tabMisc );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, aitem[id].getMaxDamage() ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.glass ),
					'b', new ItemStack( Item.appleGold, 1, fm ) );
		}


		//XP Crystal (500)
		id++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_XPCrystal( aitemID[id], 64, 500 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] ).setCreativeTab( CreativeTabs.tabMisc );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, aitem[id].getMaxDamage() ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.blockNetherQuartz, 1, fm ),
					'b', new ItemStack( Item.enchantedBook, 1, fm ) );
		}






		//XP Crystal (2000)
		id++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_XPCrystal( aitemID[id], 64, 2000 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] ).setCreativeTab( CreativeTabs.tabMisc );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, aitem[id].getMaxDamage() ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.blockNetherQuartz, 1, fm ),
					'b', new ItemStack( Item.expBottle ) );
		}


		//XP Crystal (10000)
		id++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_XPCrystal( aitemID[id], 64, 10000 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] ).setCreativeTab( CreativeTabs.tabMisc );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			//note: このクリスタルのみ、作成時には最大チャージされます
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.blockNetherQuartz, 1, fm ),
					'b', new ItemStack( Item.netherStar ) );
		}



		//point: add new item/recipe

	}




}
