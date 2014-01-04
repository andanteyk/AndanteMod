package andmod.ExBlock;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
		modid	= "AndanteMod_ExBlock",
		name	= "ExBlock",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_ExBlock {

	//point: class

	public static int aitemqty = 0;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 0;

	public static int ablockqty = 7;
	public static int[] ablockID = new int[ablockqty];
	public static Block[] ablock = new Block[ablockqty];
	public static String[][] ablockname = new String[ablockqty][3];
	public int ablockIDdefault = 2370;
	
	public static String ObjectHeader = "ExBlock:";

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
		ablockname[id][0] = "Needle";
		ablockname[id][1] = "Needle";
		ablockname[id][2] = "トゲトゲ床";
		id ++;
		ablockname[id][0] = "BloodyNeedle";
		ablockname[id][1] = "Bloody Needle";
		ablockname[id][2] = "血塗られたトゲトゲ床";
		id ++;
		ablockname[id][0] = "HealingSquare";
		ablockname[id][1] = "Healing Square";
		ablockname[id][2] = "癒しの魔法陣";
		id ++;
		ablockname[id][0] = "SlipperyBlock";
		ablockname[id][1] = "Slippery Block";
		ablockname[id][2] = "つるつるブロック";
		id ++;
		ablockname[id][0] = "Pitfall";
		ablockname[id][1] = "Pitfall";
		ablockname[id][2] = "落とし穴";
		id ++;
		ablockname[id][0] = "VectorBlock";
		ablockname[id][1] = "Vector Block";
		ablockname[id][2] = "ベクトルブロック";
		id ++;
		ablockname[id][0] = "InvaderBlock";
		ablockname[id][1] = "Invader Block";
		ablockname[id][2] = "侵蝕ブロック";
		
		
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

		//Needle
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_Needle( ablockID[id], Material.rock, 2.0F, 1 )
			.setLightOpacity( 0 ).setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabDecorations )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 4 ),
					" c ",
					" c ",
					"ccc",
					'c', new ItemStack( Item.ingotIron, 1, 0 ) );
		}
		
		
		//Bloody Needle
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_Needle( ablockID[id], Material.rock, 4.0F, 6 )
			.setLightOpacity( 0 ).setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabDecorations )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 4 ),
					" c ",
					" c ",
					"ccc",
					'c', new ItemStack( Item.swordIron, 1, 0 ) );
		}
				
		
		//Healing Square
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_HealingSquare( ablockID[id], Material.clay, 1.0F, 10 )
			.setLightOpacity( 0 ).setLightValue( 8.0F / 15.0F ).setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabDecorations )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"tet",
					"eoe",
					"tet",
					'e', new ItemStack( Item.emerald, 1, 0 ),
					't', new ItemStack( Item.ghastTear, 1, 0 ),
					'o', new ItemStack( Item.eyeOfEnder, 1, 0 ) );
		}
		
		
		//Slippery Block
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_Slipper( ablockID[id], Material.rock, 1.0F / 0.91F ) //1.0F / 0.91F
			.setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 4 ),
					"ii",
					"ss",
					'i', new ItemStack( Block.ice, 1, 0 ),
					's', new ItemStack( Block.stone, 1, 0 ) );
		}
		
		
		//Pitfall
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_Pitfall( ablockID[id], Material.rock )
			.setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"i",
					"s",
					'i', new ItemStack( Block.trapdoor, 1, 0 ),
					's', new ItemStack( Block.glass, 1, 0 ) );
		}
		
		
		//Vector Block
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_Vector( ablockID[id], Material.glass, 0.2, 0x44FFFF, 0xFF4444 )
			.setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundGlassFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
				
		}
		
		
		//Invader Block
		id ++;
		if ( ablockID[id] != 0 ) {
			ablock[id] = new Block_Invader( ablockID[id] )
			.setHardness( 1.5F ).setResistance( 10.0F ).setStepSound( Block.soundGlassFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );
	
			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
				
		}
		
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