package andmod.Amulet;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Item_Base;
import andmod.AndCore.Item_SpecialArmor;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(
		modid	= "AndanteMod_Amulet",
		name	= "Amulet",
		version	= "1.6.2.2",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_Amulet {

	//point: class

	public static int aitemqty = 9;
	public static int[] aitemID = new int[aitemqty];
	public static Item[] aitem = new Item[aitemqty];
	public static String[][] aitemname = new String[aitemqty][3];
	public int aitemIDdefault = 23876;

	public static String ObjectHeader = "Amulet:";
	
	public static String[] ArmorTexture = new String[aitemqty - 1];

	private int fm = OreDictionary.WILDCARD_VALUE;
	
	
	private int explosionPower = 30;			//実際の値は/10されます。
	private int explosionProbability = 5;
	private boolean explosionBreakable = true;
	private boolean explosionUnsafe = false;


	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id++;
		aitemname[id][0] = "IronChain";
		aitemname[id][1] = "Iron Chain";
		aitemname[id][2] = "鉄の鎖";
		id++;
		aitemname[id][0] = "SunlightAmulet";
		aitemname[id][1] = "Amulet of Sunlight";
		aitemname[id][2] = "陽光のアミュレット";
		id++;
		aitemname[id][0] = "MoonlightAmulet";
		aitemname[id][1] = "Amulet of Moonlight";
		aitemname[id][2] = "月光のアミュレット";
		id++;
		aitemname[id][0] = "SalamanderAmulet";
		aitemname[id][1] = "Amulet of Salamander";
		aitemname[id][2] = "火精のアミュレット";
		id++;
		aitemname[id][0] = "UndineAmulet";
		aitemname[id][1] = "Amulet of Undine";
		aitemname[id][2] = "水精のアミュレット";
		id++;
		aitemname[id][0] = "SylphideAmulet";
		aitemname[id][1] = "Amulet of Sylphide";
		aitemname[id][2] = "風精のアミュレット";
		id++;
		aitemname[id][0] = "LeprechaunAmulet";
		aitemname[id][1] = "Amulet of Leprechaun";
		aitemname[id][2] = "金精のアミュレット";
		id++;
		aitemname[id][0] = "GnomeAmulet";
		aitemname[id][1] = "Amulet of Gnome";
		aitemname[id][2] = "地精のアミュレット";
		id++;
		aitemname[id][0] = "CreeperAmulet";
		aitemname[id][1] = "Amulet of Creeper";
		aitemname[id][2] = "クリーパーのアミュレット";


		//point: add new item name
		
		String header = ObjectHeader.toLowerCase() + "textures/amulet/";

		id = -1;
		id ++;
		ArmorTexture[id] = header + "sunlight.png";
		id ++;
		ArmorTexture[id] = header + "moonlight.png";
		id ++;
		ArmorTexture[id] = header + "salamander.png";
		id ++;
		ArmorTexture[id] = header + "undine.png";
		id ++;
		ArmorTexture[id] = header + "shylpeed.png";
		id ++;
		ArmorTexture[id] = header + "leprechaun.png";
		id ++;
		ArmorTexture[id] = header + "gnome.png";
		id ++;
		ArmorTexture[id] = header + "creeper.png";
		
		

		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		try	{
			cfg.load();

			for( int i = 0; i < aitemID.length; i++ ) {
				Property ItemProp = cfg.getItem( "ItemID_" + aitemname[i][0], aitemIDdefault + i );
				ItemProp.comment = "ItemID - " + aitemname[i][1];
				aitemID[i] = ItemProp.getInt();
			}

			
			Property prop = cfg.get( "General", "CreeperAmulet_ExplosionPower", explosionPower );
			prop.comment = "Specifies the explosive power of 'Amulet of Creeper'. [0-1023] sssss..." ;
			explosionPower = prop.getInt();
			if ( explosionPower < 0 ) explosionPower = 0;
			if ( explosionPower > 1023 ) explosionPower = 1023;
			
			prop = cfg.get( "General", "CreeperAmulet_ExplosionProbability", explosionProbability );
			prop.comment = "Specifies the explosion probability of 'Amulet of Creeper'. [0-1000] sssss..." ;
			explosionProbability = prop.getInt();
			if ( explosionProbability < 0 ) explosionProbability = 0;
			if ( explosionProbability > 1000 ) explosionProbability = 1000;
			
			prop = cfg.get( "General", "CreeperAmulet_ExplosionBreakable", explosionBreakable );
			prop.comment = "Specify explosion of 'Amulet of Creeper' Do you want to destroy the terrain. [true/false] sssss..." ;
			explosionBreakable = prop.getBoolean( explosionBreakable );

			prop = cfg.get( "General", "CreeperAmulet_ExplosionUnsafe", explosionUnsafe );
			prop.comment = "Specify that can damage yourself by the explosion of 'Amulet of Creeper'. [true/false] sssss..." ;
			explosionUnsafe = prop.getBoolean( explosionUnsafe );

			
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
		
		
		//Iron Chain
		id ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
					" i ",
					"i i",
					" i ",
					'i', new ItemStack( Item.ingotIron, 1, 0 ) );
			
			GameRegistry.addRecipe( new ItemStack( Item.helmetChain, 1 ),
					"ccc",
					"c c",
					'c', new ItemStack( aitem[id], 1 ) );
			GameRegistry.addRecipe( new ItemStack( Item.plateChain, 1 ),
					"c c",
					"ccc",
					"ccc",
					'c', new ItemStack( aitem[id], 1 ) );
			GameRegistry.addRecipe( new ItemStack( Item.legsChain, 1 ),
					"ccc",
					"c c",
					"c c",
					'c', new ItemStack( aitem[id], 1 ) );
			GameRegistry.addRecipe( new ItemStack( Item.bootsChain, 1 ),
					"c c",
					"c c",
					'c', new ItemStack( aitem[id], 1 ) );
			
		}
		
		
		EnumArmorMaterial AMAmulet = EnumHelper.addArmorMaterial( "AMULET", 1, new int[] { 1, 1, 1, 1 }, 0 );
		AMAmulet.customCraftingMaterial = new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ).getItem();

		
		//Amulet of Sunlight
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.regeneration.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_HAPPINESS, 2 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_SUNLIGHT )
			.addEffect( Potion.hunger.id, 0, Item_SpecialArmor.FLAG_NIGHT )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.speckledMelon, 1, 0 ) );
			
		}


		//Amulet of Moonlight
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.invisibility.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Potion.nightVision.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_HAPPINESS, 3 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_MOONLIGHT )
			.addEffect( Potion.weakness.id, 0, Item_SpecialArmor.FLAG_SUNLIGHT )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.dyePowder, 1, 4 ) );
			
		}
		
		
		//Amulet of Salamander
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.fireResistance.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Potion.damageBoost.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.fireworkCharge, 1, 0 ) );
			
		}

		
		//Amulet of Undine
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.waterBreathing.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Potion.resistance.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.fishRaw, 1, 0 ) );
			
		}
		
		
		//Amulet of Shylpeed
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.moveSpeed.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Potion.jump.id, 1, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.feather, 1, 0 ) );
			
		}
		
		
		//Amulet of Leprechaun
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.poison.id, 1000, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.wither.id, 1000, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_AUTOREPAIRING, 5, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.ingotGold, 1, 0 ) );
			
		}
		
		
		//Amulet of Gnome
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Potion.digSpeed.id, 0, Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_SECRETEATING, 2 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( 10 << Item_SpecialArmor.AMP_DURATIONSHIFT ), Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Item.brick, 1, 0 ) );
			
		}
		
		
		//Amulet of Creeper
		id ++;
		armorid ++;
		if ( aitemID[id] != 0 ) {
			aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], "", AMAmulet, 1 )
			.setCanReduceAllDamage( true )
			.addEffect( Item_SpecialArmor.EFFECT_EXPLOSION, explosionProbability | ( explosionPower << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( explosionUnsafe ? Item_SpecialArmor.AMP_EXPLOSION_UNSAFE : 0 ) | ( explosionBreakable ? Item_SpecialArmor.AMP_EXPLOSION_DESTRUCTABLE : 0 ), Item_SpecialArmor.FLAG_ANYTIME )
			.addEffect( Item_SpecialArmor.EFFECT_COUNTEREXPLOSION, 1000 | ( explosionPower << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( explosionUnsafe ? Item_SpecialArmor.AMP_EXPLOSION_UNSAFE : 0 ) | ( explosionBreakable ? Item_SpecialArmor.AMP_EXPLOSION_DESTRUCTABLE : 0 ) | Item_SpecialArmor.AMP_COUNTER_PROJECTILE, Item_SpecialArmor.FLAG_ANYTIME )
			.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

			GameRegistry.registerItem( aitem[id], aitemname[id][0] );
			LanguageRegistry.addName( aitem[id], aitemname[id][1] );
			LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

			
			GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
					"i i",
					" n ",
					" g ",
					'i', new ItemStack( getItemIDforCraft( "IronChain" ), 1, 0 ),
					'n', Item.goldNugget,
					'g', new ItemStack( Block.tnt, 1, 0 ) );
			
		}


	}





	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for (int i = 0; i < aitemname.length; i ++)
			if ( aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		return 0;
	}

}