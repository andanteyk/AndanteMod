package andmod.Lighter;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Handler_Fuel;
import andmod.AndCore.Item_Base;
import andmod.AndCore.Item_Food;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_Lighter",
		name	= "Lighter",
		version	= "1.6.2.0",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_Lighter {

	//point: class

	public static int aitemqty = 5;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23636;

	public static String ObjectHeader = "Lighter:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		
		id++;
		aitemname[id][0] = "SlimeOil";
		aitemname[id][1] = "Slime Oil";
		aitemname[id][2] = "スライムオイル";
		id++;
		aitemname[id][0] = "LighterEmpty";
		aitemname[id][1] = "Lighter";
		aitemname[id][2] = "ライター";
		id++;
		aitemname[id][0] = "LighterSlimeOil";
		aitemname[id][1] = "Lighter";
		aitemname[id][2] = "ライター";
		id++;
		aitemname[id][0] = "LighterLava";
		aitemname[id][1] = "Lighter";
		aitemname[id][2] = "ライター";
		id++;
		aitemname[id][0] = "LighterMagmaCream";
		aitemname[id][1] = "Lighter";
		aitemname[id][2] = "ライター";
		
		//point: add new item name


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property itemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				itemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = itemProp.getInt();
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
		Handler_Fuel fuelh = new Handler_Fuel();

		
		//Slime Oil
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 2, 6.4D, false, true )
			.addPotionEffect( Potion.confusion.id, 20 * 30, 0, 0.8F )
			.setAlwaysEdible()
			.setCreativeTab( CreativeTabs.tabMaterials )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	
			FurnaceRecipes.smelting().addSmelting( Item.slimeBall.itemID, new ItemStack( aitem[id], 1 ), 0.2F );
			fuelh.addFuel( aitem[id].itemID, -1, 200 * 8 );

			GameRegistry.addShapelessRecipe( new ItemStack( Item.porkRaw, 1 ),
				new ItemStack( Item.rottenFlesh, 1, 0 ),
				new ItemStack( aitem[id], 1, 0 ) );

			for ( int i = 0; i < 16; i ++ )
				GameRegistry.addShapelessRecipe( new ItemStack( Item.dyePowder, 1, i ),
					new ItemStack( Block.cloth, 1, 15 - i ),
					new ItemStack( aitem[id], 1, 0 ) );

			GameRegistry.addShapelessRecipe( new ItemStack( Block.blockClay, 1 ),
				new ItemStack( Block.dirt, 1, 0 ),
				new ItemStack( aitem[id], 1, 0 ) );
		}
	
		
		//Lighter (empty)
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Base( aitemID[id], 64 )
			.setInformation( "Empty", "空" ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"f",
					"b",
					'f', new ItemStack( Item.flintAndSteel, 1, 0 ),
					'b', new ItemStack( Item.glassBottle, 1, 0 ) );
			
		}
		
		
		//Lighter (slime oil)
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Lighter( aitemID[id], 32, 50, true, new ItemStack( getItemIDforCraft( "LighterEmpty" ) , 1, 0 ) )
			.addInformation( "Slime Oil", "スライムオイル" ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"f",
					"b",
					"s",
					'f', new ItemStack( Item.flintAndSteel, 1, 0 ),
					'b', new ItemStack( Item.glassBottle, 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "SlimeOil" ), 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( getItemIDforCraft( "LighterEmpty" ), 1, 0 ),
				new ItemStack( getItemIDforCraft( "SlimeOil" ), 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( aitem[id], 1, fm ),
				new ItemStack( getItemIDforCraft( "SlimeOil" ), 1, 0 ) );

		}
		
		
		//Lighter (lava)
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Lighter( aitemID[id], 400, 50, true, new ItemStack( getItemIDforCraft( "LighterEmpty" ) , 1, 0 ) )
			.addInformation( "Lava", "溶岩" ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"f",
					"b",
					"s",
					'f', new ItemStack( Item.flintAndSteel, 1, 0 ),
					'b', new ItemStack( Item.glassBottle, 1, 0 ),
					's', new ItemStack( Item.bucketLava, 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( getItemIDforCraft( "LighterEmpty" ), 1, 0 ),
				new ItemStack( Item.bucketLava, 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( aitem[id], 1, fm ),
				new ItemStack( Item.bucketLava, 1, 0 ) );

		}
		
		
		//Lighter (magma cream)
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Lighter( aitemID[id], 48, 50, true, new ItemStack( getItemIDforCraft( "LighterEmpty" ) , 1, 0 ) )
			.addInformation( "Magma Cream", "マグマクリーム" ).setCreativeTab( CreativeTabs.tabTools )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	

			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"f",
					"b",
					"s",
					'f', new ItemStack( Item.flintAndSteel, 1, 0 ),
					'b', new ItemStack( Item.glassBottle, 1, 0 ),
					's', new ItemStack( Item.magmaCream, 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( getItemIDforCraft( "LighterEmpty" ), 1, 0 ),
				new ItemStack( Item.magmaCream, 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( aitem[id], 1, fm ),
				new ItemStack( Item.magmaCream, 1, 0 ) );

		}
	
		GameRegistry.registerFuelHandler( fuelh );
	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}
	
}