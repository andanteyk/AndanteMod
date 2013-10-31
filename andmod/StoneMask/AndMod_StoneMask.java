package andmod.StoneMask;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Item_SpecialArmor;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(
		modid	= "AndanteMod_StoneMask",
		name	= "StoneMask",
		version	= "1.6.2.0",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_StoneMask {

	//point: class

	public static int aitemqty = 1;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23619;

	public static String ObjectHeader = "StoneMask:";
	
	public static String[] ArmorTexture = new String[aitemqty];

	private int fm = OreDictionary.WILDCARD_VALUE;
	

	private boolean isCraftable = false;
	private boolean addChestContents = true;
	

	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "StoneMask";
		aitemname[id][1] = "Stone Mask";
		aitemname[id][2] = "石仮面";
		


		//point: add new item name
		
		String header = ObjectHeader.toLowerCase() + "textures/mask/";

		id = -1;
		id ++;
		ArmorTexture[id] = header + "stonemask.png";		
		
		

		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property ItemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				ItemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = ItemProp.getInt();
			}

			
			Property prop = cfg.get( "General", "isCraftable", isCraftable );
			prop.comment = "Specify whether you can create 'Stone Mask' in crafting table. [true/false]";
			isCraftable = prop.getBoolean( isCraftable );
			
			prop = cfg.get( "General", "addChestContents", addChestContents );
			prop.comment = "Specify whether to include a new item to the chest. [true/false]";
			addChestContents = prop.getBoolean( addChestContents );
			
			
		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}


		for( int i = 0; i < aitemqty; i++ )
			aitemname[i][0] = ObjectHeader + aitemname[i][0];

	}




	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		int id = -1;
		int armorid = -1;
		
		
		EnumArmorMaterial AMStoneMask = EnumHelper.addArmorMaterial( "STONEMASK", 12, new int[] { 1, 1, 1, 1 }, 0 );
		AMStoneMask.customCraftingMaterial = new ItemStack( Block.blockRedstone, 1, 0 ).getItem();

		
		//Stone Mask
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMStoneMask, 0 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.moveSpeed.id, 1, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.digSpeed.id, 1, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.damageBoost.id, 1, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.jump.id, 1, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.regeneration.id, 1, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.resistance.id, 2, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.nightVision.id, 0, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Potion.field_76434_w.id, 9, Item_SpecialArmor.FLAG_DARKNESS )	//health boost
			.addEffect( Item_SpecialArmor.EFFECT_EXTINGUISHMENT, 1000, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.poison.id, 1000, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.wither.id, 1000, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.weakness.id, 1000, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.moveSlowdown.id, 1000, Item_SpecialArmor.FLAG_DARKNESS )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.confusion.id, 1000, Item_SpecialArmor.FLAG_DARKNESS )
	
			.addEffect( Potion.moveSlowdown.id, 1, Item_SpecialArmor.FLAG_SUNLIGHT )
			.addEffect( Potion.digSlowdown.id, 1, Item_SpecialArmor.FLAG_SUNLIGHT )
			.addEffect( Potion.confusion.id, 0, Item_SpecialArmor.FLAG_SUNLIGHT )
			.addEffect( Potion.weakness.id, 1, Item_SpecialArmor.FLAG_SUNLIGHT )
			.addEffect( Item_SpecialArmor.EFFECT_IGNITION, 1000, Item_SpecialArmor.FLAG_SUNLIGHT )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.nightVision.id, 1000, Item_SpecialArmor.FLAG_SUNLIGHT ) 

			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			addChestContent( ChestGenHooks.MINESHAFT_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 2 );
			addChestContent( ChestGenHooks.PYRAMID_DESERT_CHEST, new ItemStack( aitem[id] ), 1, 1, 2 );
			addChestContent( ChestGenHooks.PYRAMID_JUNGLE_CHEST, new ItemStack( aitem[id] ), 1, 1, 2 );
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 2 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 2 );
			addChestContent( ChestGenHooks.DUNGEON_CHEST, new ItemStack( aitem[id] ), 1, 1, 2 );
			
			
			if ( isCraftable ) {
				GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
						"sts",
						"s s",
						's', new ItemStack( Block.stoneBrick, 1, 2 ),
						't', Block.blockRedstone );
			}

			
		}


	}



	private void addChestContent( String category, ItemStack items, int min, int max, int weight ) {
		if ( addChestContents )
			ChestGenHooks.addItem( category, new WeightedRandomChestContent( items, min, max, weight ) );
	}
	

	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}