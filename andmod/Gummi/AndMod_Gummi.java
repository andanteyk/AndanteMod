package andmod.Gummi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		modid	= "AndanteMod_Gummi",
		name	= "Gummi",
		version	= "1.6.2.1",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_Gummi {

	//point: class

	public static int aitemqty = 12;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23988;

	public static String ObjectHeader = "Gummi:";

	private int fm = OreDictionary.WILDCARD_VALUE;

	
	private boolean replaceVanillaLeaves = true;
	private boolean isDevEnv = false;
	

	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		try {
			Field field = Block.class.getDeclaredField( "leaves" );
			isDevEnv = true;
		} catch ( Exception e ) {
			isDevEnv = false;
		}
		
		int id = -1;

		id++;
		aitemname[id][0] = "Peach";
		aitemname[id][1] = "Peach";
		aitemname[id][2] = "モモ";

		id++;
		aitemname[id][0] = "Grape";
		aitemname[id][1] = "Grape";
		aitemname[id][2] = "ブドウ";

		id++;
		aitemname[id][0] = "Pineapple";
		aitemname[id][1] = "Pineapple";
		aitemname[id][2] = "パイナップル";
		
		id++;
		aitemname[id][0] = "GoldenPeach";
		aitemname[id][1] = "Golden Peach";
		aitemname[id][2] = "金のモモ";

		id++;
		aitemname[id][0] = "GoldenGrape";
		aitemname[id][1] = "Golden Grape";
		aitemname[id][2] = "金のブドウ";

		id++;
		aitemname[id][0] = "GoldenPineapple";
		aitemname[id][1] = "Golden Pineapple";
		aitemname[id][2] = "金のパイナップル";

		id++;
		aitemname[id][0] = "AppleGummi";
		aitemname[id][1] = "Apple Gummi";
		aitemname[id][2] = "アップルグミ";

		id++;
		aitemname[id][0] = "PeachGummi";
		aitemname[id][1] = "Peach Gummi";
		aitemname[id][2] = "ピーチグミ";

		id++;
		aitemname[id][0] = "GrapeGummi";
		aitemname[id][1] = "Grape Gummi";
		aitemname[id][2] = "グレープグミ";

		id++;
		aitemname[id][0] = "PineappleGummi";
		aitemname[id][1] = "Pineapple Gummi";
		aitemname[id][2] = "パイングミ";

		id++;
		aitemname[id][0] = "MixGummi";
		aitemname[id][1] = "Mix Gummi";
		aitemname[id][2] = "ミックスグミ";

		id++;
		aitemname[id][0] = "MiracleGummi";
		aitemname[id][1] = "Miracle Gummi";
		aitemname[id][2] = "ミラクルグミ";

		//point: add new item name


		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property ItemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				ItemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = ItemProp.getInt();
			}

			Property repProp = cfg.get( "General", "replaceVanillaLeaves", replaceVanillaLeaves );
			repProp.comment = "Specify that you want to replace the leaves of vanilla. [true/false]";
			replaceVanillaLeaves = repProp.getBoolean( replaceVanillaLeaves );

		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;

		//Peach
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.3F, false, false )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		}
		
		
		//Grape
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.3F, false, false )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		}

		
		//Pineapple
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.3F, false, false )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		}
		
		
		//Golden Peach
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_GoldenPeach( aitemID[id], 64, 4, 1.2F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Item.goldNugget, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Peach" ), 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 1 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Item.ingotGold, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Peach" ), 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 2 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Block.blockGold, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Peach" ), 1, 0 ) );

		}
		

		//Golden Grape
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_GoldenGrape( aitemID[id], 64, 4, 1.2F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Item.goldNugget, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Grape" ), 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 1 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Item.ingotGold, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Grape" ), 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 2 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Block.blockGold, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Grape" ), 1, 0 ) );

		}
		
		
		//Golden Pineapple
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_GoldenPineapple( aitemID[id], 64, 4, 1.2F )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 0 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Item.goldNugget, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Pineapple" ), 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 1 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Item.ingotGold, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Pineapple" ), 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1, 2 ),
					"ccc",
					"cbc",
					"ccc",
					'c', new ItemStack( Block.blockGold, 1, 0 ),
					'b', new ItemStack( getItemIDforCraft( "Pineapple" ), 1, 0 ) );

		}
		

		//Apple Gummi
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.5F, false, false )
			.addPotionEffect( Item_Food.EFFECT_HEAL, 0, 6, 1.0F )
			.setAlwaysEdible()
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( Item.appleRed, 1, 0 ),
					new ItemStack( Item.slimeBall, 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ) );
		}
		
		
		//Peach Gummi
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.8F, false, false )
			.addPotionEffect( Item_Food.EFFECT_HEAL, 0, 4, 1.0F )
			.addPotionEffect( Potion.waterBreathing.id, 1, 0, 1.0F )
			.setAlwaysEdible()
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( getItemIDforCraft( "Peach" ), 1, 0 ),
					new ItemStack( Item.slimeBall, 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ) );
		}
		
		
		//Grape Gummi
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.5F, false, false )
			.addPotionEffect( Item_Food.EFFECT_HEAL, 0, 4, 1.0F )
			.addPotionEffect( Item_Food.EFFECT_DISPEL, 0, 0, 1.0F )
			.setAlwaysEdible()
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( getItemIDforCraft( "Grape" ), 1, 0 ),
					new ItemStack( Item.slimeBall, 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ) );
		}
		
		
		//Pineapple Gummi
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.5F, false, false )
			.addPotionEffect( Potion.regeneration.id, 25 * 8, 1, 1.0F )
			.setAlwaysEdible()
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( getItemIDforCraft( "Pineapple" ), 1, 0 ),
					new ItemStack( Item.slimeBall, 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ) );
		}
		
		
		//Mix Gummi
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.5F, false, false )
			.addPotionEffect( Item_Food.EFFECT_HEAL, 0, 10, 1.0F )
			.addPotionEffect( Item_Food.EFFECT_DISPEL, 0, 0, 1.0F )
			.setAlwaysEdible()
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( Item.appleRed, 1, 0 ),
					new ItemStack( getItemIDforCraft( "Peach" ), 1, 0 ),
					new ItemStack( getItemIDforCraft( "Grape" ), 1, 0 ),
					new ItemStack( getItemIDforCraft( "Pineapple" ), 1, 0 ),
					new ItemStack( Item.slimeBall, 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ) );
		}
		
		
		//Miracle Gummi
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 4, 0.5F, false, false )
			.addPotionEffect( Item_Food.EFFECT_HEAL, 0, 20, 1.0F )
			.addPotionEffect( Item_Food.EFFECT_DISPEL, 0, 0, 1.0F )
			.addPotionEffect( Potion.regeneration.id, 1 * 60 * 20, 0, 1.0F )
			.addPotionEffect( Potion.resistance.id, 3 * 60 * 20, 0, 1.0F )
			.addPotionEffect( Potion.damageBoost.id, 3 * 60 * 20, 0, 1.0F )
			.addPotionEffect( Potion.digSpeed.id, 3 * 60 * 20, 0, 1.0F )
			.addPotionEffect( Potion.waterBreathing.id, 3 * 60 * 20, 0, 1.0F )
			.setAlwaysEdible()
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1, 0 ),
					new ItemStack( Item.appleGold, 1, fm ),
					new ItemStack( getItemIDforCraft( "GoldenPeach" ), 1, fm ),
					new ItemStack( getItemIDforCraft( "GoldenGrape" ), 1, fm ),
					new ItemStack( getItemIDforCraft( "GoldenPineapple" ), 1, fm ),
					new ItemStack( Item.slimeBall, 1, 0 ),
					new ItemStack( Item.sugar, 1, 0 ) );
		}
		
		
		if ( replaceVanillaLeaves ) {
			Block.blocksList[ Block.leaves.blockID ] = null;
			Block.blocksList[ Block.leaves.blockID ] = new Block_Leaves( Block.leaves.blockID )
				.setRareDrop( 0, new ItemStack( Item.appleRed, 1, 0 ) ) 
				.setRareDrop( 1, new ItemStack( getItemIDforCraft( "Grape" ), 1, 0 ) ) 
				.setRareDrop( 2, new ItemStack( getItemIDforCraft( "Peach" ), 1, 0 ) ) 
				.setRareDrop( 3, new ItemStack( getItemIDforCraft( "Pineapple" ), 1, 0 ) ) 

				.setRareDrop( 4, new ItemStack( Item.appleGold, 1, 0 ) )
				.setRareDrop( 5, new ItemStack( getItemIDforCraft( "GoldenGrape" ), 1, 0 ) )
				.setRareDrop( 6,  new ItemStack( getItemIDforCraft( "GoldenPeach" ), 1, 0 ) )
				.setRareDrop( 7,  new ItemStack( getItemIDforCraft( "GoldenPineapple" ), 1, 0 ) )
				.setHardness( 0.2F ).setLightOpacity( 1 ).setStepSound( Block.soundGrassFootstep ).setUnlocalizedName( "leaves" ).func_111022_d( "leaves" );

			try {
				Field f = Block.class.getDeclaredField( isDevEnv ? "leaves" : "field_71952_K" );
				f.setAccessible( true );
				
				Field m = Field.class.getDeclaredField( "modifiers" );
				m.setAccessible( true );
				m.setInt( f, f.getModifiers() & ~Modifier.FINAL );
				
				f.set( null, Block.blocksList[Block.leaves.blockID] );
			
				System.out.println( "[Replaced Leaves] Block.leaves was overridden." );
				
				
			} catch ( Exception e ) {
				System.out.println( "[Replaced Leaves] Block.leaves can't be overridden." );
			}
		}
	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}