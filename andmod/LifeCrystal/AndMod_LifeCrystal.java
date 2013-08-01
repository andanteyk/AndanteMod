package andmod.LifeCrystal;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
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
		modid	= "AndanteMod_LifeCrystal",
		name	= "Life Crystal",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_LifeCrystal {

	//point: class

	public static int aitemqty = 3;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23610;

	public static String ObjectHeader = "LifeCrystal:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "LifeCrystal";
		aitemname[id][1] = "Life Crystal";
		aitemname[id][2] = "生命のクリスタル";

		id++;
		aitemname[id][0] = "HealingCrystal";
		aitemname[id][1] = "Healing Crystal";
		aitemname[id][2] = "癒しのクリスタル";

		id++;
		aitemname[id][0] = "DivineCrystal";
		aitemname[id][1] = "Divine Crystal";
		aitemname[id][2] = "地神のクリスタル";

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

		int id = -1;
		Event_Resurrection e = new Event_Resurrection();
		

		//Life Crystal
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_LifeCrystal( aitemID[id], 10, 5.0F, 2.0F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			e.addItem( aitemID[id] + 256 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"rcr",
					"cbc",
					"rcr",
					'c', new ItemStack( Item.goldNugget, 1, 0 ),
					'r', new ItemStack( Item.redstone, 1, 0 ),
					'b', new ItemStack( Item.appleRed, 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( aitem[id], 1, fm ),
					new ItemStack( Item.goldNugget, 1, 0 ) );
		}

		
		//Healing Crystal
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_LifeCrystal( aitemID[id], 20, 9.0F, 2.0F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			e.addItem( aitemID[id] + 256 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Block.glowStone, 1, 0 ),
					'b', new ItemStack( Item.appleGold, 1, fm ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( aitem[id], 1, fm ),
					new ItemStack( Item.ingotGold, 1, fm ) );
		}
	
		
		//Divine Crystal
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_LifeCrystal( aitemID[id], 40, 15.0F, 1.0F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			e.addItem( aitemID[id] + 256 );

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					" c ",
					"cbc",
					" c ",
					'c', new ItemStack( Item.diamond, 1, 0 ),
					'b', new ItemStack( Item.appleGold, 1, 1 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( aitem[id], 1, fm ),
					new ItemStack( Block.blockGold, 1, 1 ) );
		}
			

		MinecraftForge.EVENT_BUS.register( e );

	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}