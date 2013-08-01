package andmod.ExFoods;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Item_Food;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_ExFoods",
		name	= "ExFoods",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_ExFoods {

	//point: class

	public static int aitemqty = 8;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23700;

	public static String ObjectHeader = "ExFoods:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "Chocolate";
		aitemname[id][1] = "Chocolate";
		aitemname[id][2] = "チョコレート";

		id++;
		aitemname[id][0] = "ApplePie";
		aitemname[id][1] = "Apple Pie";
		aitemname[id][2] = "アップルパイ";

		id++;
		aitemname[id][0] = "MeatPie";
		aitemname[id][1] = "Meat Pie";
		aitemname[id][2] = "ミートパイ";

		id++;
		aitemname[id][0] = "ChocolatePie";
		aitemname[id][1] = "Chocolate Pie";
		aitemname[id][2] = "チョコレートパイ";

		id++;
		aitemname[id][0] = "BakedApple";
		aitemname[id][1] = "Baked Apple";
		aitemname[id][2] = "ベイクドアップル";

		id++;
		aitemname[id][0] = "IceCandy";
		aitemname[id][1] = "Ice Candy";
		aitemname[id][2] = "アイスキャンディ";

		id++;
		aitemname[id][0] = "CarrotPie";
		aitemname[id][1] = "Carrot Pie";
		aitemname[id][2] = "キャロットパイ";

		id++;
		aitemname[id][0] = "PumpkinStew";
		aitemname[id][1] = "Pumpkin Stew";
		aitemname[id][2] = "カボチャシチュー";

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

		//Chocolate
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 3.2D, false, false )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addShapelessRecipe(new ItemStack( aitem[id], 1, 0 ),
					new ItemStack(Item.sugar, 1, 0),
					new ItemStack(Item.sugar, 1, 0),
					new ItemStack(Item.dyePowder, 1, 3) );
		}

		
		//Apple pie
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 6, 8.0D, false, false ).addPotionEffect( Potion.resistance.id, 120 * 20, 0, 0.5F )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ),
					new ItemStack( Item.egg, 1, 0 ),
					new ItemStack( Item.appleRed, 1, 0 ) );
		}


		//Meat pie		//note:(現実世界での)レシピに正直自信がないです^^;卵要りましたっけ？
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 10, 12.8D, false, false ).addPotionEffect( Potion.damageBoost.id, 120 * 20, 0, 0.5F )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( Item.beefCooked, 1, 0 ),
					new ItemStack( Item.egg, 1, 0 ),
					new ItemStack( Item.wheat, 1, 0 ) );
		}
		

		//Chocolate pie
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 8, 7.0D, false, false ).addPotionEffect( Potion.digSpeed.id, 180 * 20, 0, 0.5F )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( getItemIDforCraft( "Chocolate" ), 1, 0 ),
				new ItemStack( Item.egg, 1, 0 ),
				new ItemStack( Item.sugar, 1, 0 ) );
		}

		
		//Baked apple
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.3F, false, false ).addPotionEffect( Item_Food.EFFECT_HEAL, 1, 4, 1.0F )
					.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			FurnaceRecipes.smelting().addSmelting( Item.appleRed.itemID, new ItemStack( aitem[id], 1 ), 0.2F );
		}

		
		//Ice Candy
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 3, 0.3F, false, true ).addPotionEffect( Potion.fireResistance.id, 90 * 20, 0, 0.5F ).setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 4, 0 ),
				new ItemStack( Block.ice, 1, 0 ),
				new ItemStack( Item.sugar, 1, 0 ) );
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ), 
				new ItemStack( Block.blockSnow, 1, 0 ),
				new ItemStack( Item.sugar, 1, 0 ) );
		}

		
		//Carrot pie
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 8, 8.0D, false, false ).addPotionEffect( Potion.nightVision.id, 90 * 20, 0, 0.5F ).setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ), 
				new ItemStack( Item.carrot, 1, 0 ),
				new ItemStack( Item.egg, 1, 0 ),
				new ItemStack( Item.sugar, 1, 0 ) );
		}


		//Pumpkin Stew
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 6, 6.4D, false, true ).setContainer( new ItemStack( Item.bowlEmpty ) ).setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
				new ItemStack( Item.bowlEmpty, 1, 0 ),
				new ItemStack( Block.pumpkin, 1, 0 ) );
		}

	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}