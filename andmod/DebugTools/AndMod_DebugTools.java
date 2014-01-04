package andmod.DebugTools;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Item_Food;
import andmod.AndCore.Item_SpecialArmor;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid	= "AndanteMod_DebugTools",
		name	= "Debug Tools",
		version	= "1.6.2.0",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_DebugTools {

	//point: class

	public static int aitemqty = 7;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 24004;

	public static int ablockqty = 0;
	public static int[] ablockID = new int[ablockqty];
	public static Block[] ablock = new Block[ablockqty];
	public static String[][] ablockname = new String[ablockqty][3];
	public int ablockIDdefault = 2366;
	
	public static String ObjectHeader = "DebugTools:";

	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		
		id++;
		aitemname[id][0] = "PhoenixPotion";
		aitemname[id][1] = "Potion of Phoenix";
		aitemname[id][2] = "蓬莱の薬";
		id++;
		aitemname[id][0] = "Halo";
		aitemname[id][1] = "Angelic Circle";
		aitemname[id][2] = "天使の輪";
		id++;
		aitemname[id][0] = "WallBreaker";
		aitemname[id][1] = "Wall Breaker";
		aitemname[id][2] = "どこでもいける";
		id++;
		aitemname[id][0] = "TenguClog";
		aitemname[id][1] = "Tengu's Clog";
		aitemname[id][2] = "天狗の下駄";
		id++;
		aitemname[id][0] = "RenameCard";
		aitemname[id][1] = "Rename Card";
		aitemname[id][2] = "リネームカード";
		id++;
		aitemname[id][0] = "SuperTorch";
		aitemname[id][1] = "Super Torch";
		aitemname[id][2] = "奇跡の松明";
		id++;
		aitemname[id][0] = "StarlightScope";
		aitemname[id][1] = "Starlight Scope";
		aitemname[id][2] = "スターライトスコープ";
		
		//point: add new item name

		/*
		id ++;
		ablockname[id][0] = "Luminescence";
		ablockname[id][1] = "Luminescence";
		ablockname[id][2] = "蛍の光";
		*/
		
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

		
		//Potion of Phoenix
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Food( aitemID[id], 64, 0, 0, false, true )
			//.addPotionEffect( Potion.heal.id, 20 * 60 * 60, 4, 1.0F )
			.addPotionEffect( Potion.field_76434_w.id, 20 * 60 * 60, 255, 1.0F )
			.setContainer( new ItemStack( aitemID[id] + 256, 1, 0 ) ).setAlwaysEdible()
			.setCreativeTab( CreativeTabs.tabMisc )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	
		}
		
		
		//Angelic Circle
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ObjectHeader.toLowerCase() + "textures/armor/halo.png", "", EnumArmorMaterial.DIAMOND, 0 )
			.addEffect( Potion.heal.id, 4, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Potion.field_76443_y.id, 4, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTALL, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
			.setCreativeTab( CreativeTabs.tabMisc )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	
		}
		
		
		//Wall Breaker
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_WallBreaker( aitemID[id] )
			.setCreativeTab( CreativeTabs.tabMisc )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
		}

		
		//Tengu's Clog
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ObjectHeader.toLowerCase() + "textures/armor/halo.png", "", EnumArmorMaterial.DIAMOND, 3 )
			.addEffect( Potion.moveSpeed.id, 30, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Potion.jump.id, 5, Item_SpecialArmor.FLAG_ANYTIME )
			.setCreativeTab( CreativeTabs.tabMisc )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	
		}
		
		
		//Rename Card
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_RenameCard( aitemID[id] )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
		}
		
		
		//Super Torch
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SuperTorch( aitemID[id] ).setCreativeTab( CreativeTabs.tabMisc )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
		}
		
		
		//Starlight Scope
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ObjectHeader.toLowerCase() + "textures/armor/StarlightScope.png", "", EnumArmorMaterial.DIAMOND, 0 )
			.addEffect( Potion.nightVision.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_AUTOENCHANTMENT, Enchantment.respiration.effectId | ( 3 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_ANYTIME )
			.setCreativeTab( CreativeTabs.tabMisc )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );
	
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