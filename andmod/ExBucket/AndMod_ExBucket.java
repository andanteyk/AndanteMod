package andmod.ExBucket;

import java.util.logging.Level;

import net.minecraft.block.Block;
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
		modid	= "AndanteMod_ExBucket",
		name	= "ExBucket",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_ExBucket {

	//point: class

	public static int aitemqty = 7;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23587;

	public static String ObjectHeader = "ExBucket:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "WoodenBucketEmpty";
		aitemname[id][1] = "Wooden Bucket";
		aitemname[id][2] = "木のバケツ";
		
		id++;
		aitemname[id][0] = "WoodenBucketWater";
		aitemname[id][1] = "Wooden Water Bucket";
		aitemname[id][2] = "水入り木のバケツ";
		
		id++;
		aitemname[id][0] = "WoodenBucketMilk";
		aitemname[id][1] = "Wooden Milk Bucket";
		aitemname[id][2] = "ミルク入り木のバケツ";
		
		id++;
		aitemname[id][0] = "GoldenBucketEmpty";
		aitemname[id][1] = "Golden Bucket";
		aitemname[id][2] = "金のバケツ";
		
		id++;
		aitemname[id][0] = "GoldenBucketWater";
		aitemname[id][1] = "Golden Water Bucket";
		aitemname[id][2] = "水入り金のバケツ";
		
		id++;
		aitemname[id][0] = "GoldenBucketLava";
		aitemname[id][1] = "Golden Lava Bucket";
		aitemname[id][2] = "溶岩入り金のバケツ";
		
		id++;
		aitemname[id][0] = "GoldenBucketMilk";
		aitemname[id][1] = "Golden Milk Bucket";
		aitemname[id][2] = "ミルク入り金のバケツ";

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

		//Wooden Bucket - Empty
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 64, 1, null, null, 300 )
			.addContentsID( new ItemStack( getItemIDforCraft( "WoodenBucketWater" ), 1, 0 ), new ItemStack( Block.waterStill, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "WoodenBucketWater" ), 1, 0 ), new ItemStack( Block.waterMoving, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "WoodenBucketMilk" ), 1, 0 ), new ItemStack( Item.bucketMilk, 1, 0 ), 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"m m",
					" m ",
					'm', new ItemStack( Item.bowlEmpty, 1, 0 ) );
		}

		
		//Wooden Bucket - Water
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 1, 1, new ItemStack( getItemIDforCraft( "WoodenBucketEmpty" ), 1, 0 ), new ItemStack( Block.waterMoving, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "WoodenBucketWater" ), 1, 0 ), new ItemStack( Block.waterStill, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "WoodenBucketWater" ), 1, 0 ), new ItemStack( Block.waterMoving, 1, 0 ), 0 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			GameRegistry.addRecipe( new ItemStack( Block.ice, 4, 0 ),
					" s ",
					"sbs",
					" s ",
					's', new ItemStack( Block.blockSnow, 1, 0 ), 
					'b', new ItemStack( aitem[id], 1, fm ) ) ;

		}

		
		//Wooden Bucket - Milk
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 1, 1, new ItemStack( getItemIDforCraft( "WoodenBucketEmpty" ), 1, 0 ), new ItemStack( Item.bucketMilk, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "WoodenBucketMilk" ), 1, 0 ), new ItemStack( Item.bucketMilk, 1, 0 ), 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( Item.cake, 1 ),
					"mmm",
					"ses",
					"www",
					'm', new ItemStack( aitem[id], 1, fm ),
					's', new ItemStack( Item.sugar, 1, 0 ),
					'e', new ItemStack( Item.egg, 1, 0 ),
					'w', new ItemStack( Item.wheat, 1, 0 ) );
		}

		
		//Golden Bucket - Empty
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 64, 4, null, null, 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ), new ItemStack( Block.waterStill, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ), new ItemStack( Block.waterMoving, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ), new ItemStack( Block.lavaStill, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ), new ItemStack( Block.lavaMoving, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketMilk" ), 1, 0 ), new ItemStack( Item.bucketMilk, 1, 0 ), 1 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"m m",
					" m ",
					'm', new ItemStack( Item.ingotGold, 1, 0 ) );
		}

		
		//Golden Bucket - Water
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 1, 4, new ItemStack( getItemIDforCraft( "GoldenBucketEmpty" ), 1, 0 ), new ItemStack( Block.waterMoving, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ), new ItemStack( Block.waterStill, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ), new ItemStack( Block.waterMoving, 1, 0 ), 0 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
			
			GameRegistry.addRecipe( new ItemStack( Block.ice, 4, 0 ),
					" s ",
					"sbs",
					" s ",
					's', new ItemStack( Block.blockSnow, 1, 0 ), 
					'b', new ItemStack( aitem[id], 1, fm ) ) ;
		}

		
		//Golden Bucket - Lava
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 1, 4, new ItemStack( getItemIDforCraft( "GoldenBucketEmpty" ), 1, 0 ), new ItemStack( Block.lavaMoving, 1, 0 ), 200 * 100 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ), new ItemStack( Block.lavaStill, 1, 0 ), 0 )
			.addContentsID( new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ), new ItemStack( Block.lavaMoving, 1, 0 ), 0 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			if ( aitemID[id - 1] != 0 ) {
				GameRegistry.addRecipe( new ItemStack( Block.stone, 64, 0 ),
						"l",
						"w",
						'l', new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ),
						'w', new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ) );
				GameRegistry.addRecipe( new ItemStack( Block.obsidian, 1, 0 ),
						"w",
						"l",
						'l', new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ),
						'w', new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ) );
				GameRegistry.addRecipe(new ItemStack(Block.cobblestone, 64, 0),
						"wl",
						'l', new ItemStack( getItemIDforCraft( "GoldenBucketLava" ), 1, 0 ),
						'w', new ItemStack( getItemIDforCraft( "GoldenBucketWater" ), 1, 0 ) );
			}
		}
		
		
		//Golden Bucket - Milk
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Bucket( aitemID[id], 1, 4, new ItemStack( getItemIDforCraft( "GoldenBucketEmpty" ), 1, 0 ), new ItemStack( Item.bucketMilk, 1, 0 ), 0 )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( Item.cake, 1 ),
					"mmm",
					"ses",
					"www",
					'm', new ItemStack( aitem[id], 1, fm ),
					's', new ItemStack( Item.sugar, 1, 0 ),
					'e', new ItemStack( Item.egg, 1, 0 ),
					'w', new ItemStack( Item.wheat, 1, 0 ) );
		}

		
		GameRegistry.addRecipe( new ItemStack( Block.ice, 4, 0 ),
				" s ",
				"sbs",
				" s ",
				's', new ItemStack( Block.blockSnow, 1, 0 ), 
				'b', new ItemStack( Item.bucketWater, 1, 0 ) ) ;
		
		GameRegistry.addRecipe( new ItemStack( Block.stone, 64, 0 ),
				"l",
				"w",
				'l', new ItemStack( Item.bucketLava, 1, 0 ),
				'w', new ItemStack( Item.bucketWater, 1, 0 ) );
		GameRegistry.addRecipe( new ItemStack( Block.obsidian, 1, 0 ),
				"w",
				"l",
				'l', new ItemStack( Item.bucketLava, 1, 0 ),
				'w', new ItemStack( Item.bucketWater, 1, 0 ) );
		GameRegistry.addRecipe(new ItemStack(Block.cobblestone, 64, 0),
				"wl",
				'l', new ItemStack( Item.bucketLava, 1, 0 ),
				'w', new ItemStack( Item.bucketWater, 1, 0 ) );
		
	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}