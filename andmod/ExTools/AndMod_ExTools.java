package andmod.ExTools;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import andmod.AndCore.Block_Base;
import andmod.AndCore.Entity_Arrow;
import andmod.AndCore.Event_Arrow;
import andmod.AndCore.Event_DispenserArrow;
import andmod.AndCore.Event_MobEquipment;
import andmod.AndCore.Handler_CreativeTab;
import andmod.AndCore.Handler_Fuel;
import andmod.AndCore.Item_Armor;
import andmod.AndCore.Item_Arrow;
import andmod.AndCore.Item_Axe;
import andmod.AndCore.Item_Base;
import andmod.AndCore.Item_Bow;
import andmod.AndCore.Item_Hammer;
import andmod.AndCore.Item_Hoe;
import andmod.AndCore.Item_Pickaxe;
import andmod.AndCore.Item_Shears;
import andmod.AndCore.Item_Spade;
import andmod.AndCore.Item_SpecialArmor;
import andmod.AndCore.Item_SpecialSword;
import andmod.AndCore.Item_Sword;
import andmod.AndCore.Proxy_Common;
import andmod.AndCore.Struct_Arrow;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(
		modid	= "AndanteMod_ExTools",
		name	= "ExTools",
		version	= "1.6.2.5",
		dependencies = "required-after:AndanteMod_AndCore"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)



public class AndMod_ExTools {

	//point: class

	public static int aitemqty = 25;
	public static int[] aitemID = new int[aitemqty * 16];
	public static Item[] aitem = new Item[aitemqty * 16];
	public static String[][] aitemname = new String[aitemqty * 16][3];
	public int aitemIDdefault[] = new int[aitemqty];

	public static int ablockqty = 7;
	public static int[] ablockID = new int[ablockqty];
	public static Block[] ablock = new Block[ablockqty];
	public static String[][] ablockname = new String[ablockqty][3];
	public int ablockIDdefault = 2357;

	public static String[] atypename = new String[aitemqty];


	public static String ObjectHeader = "ExTools:";

	public static String[][] ArmorTexture = new String[aitemqty][2];
	private int fm = OreDictionary.WILDCARD_VALUE;


	@Mod.Instance( "AndanteMod_ExTools" )
	public static AndMod_ExTools instance;

	@SidedProxy( clientSide = "andmod.AndCore.Proxy_Client", serverSide = "andmod.AndCore.Proxy_Common" )
	public static Proxy_Common proxy;


	//point: 特殊オプション変数
	private boolean replaceVanillaTools = false;
	private int mobEquipmentProbability = 100;
	private boolean addChestContents = true;
	
	
	private Handler_Fuel fuelh;
	private Event_MobEquipment eequip;
	public static Handler_CreativeTab tabExTools = new Handler_CreativeTab( "ExTools", -1, "ExTools" );
	
	
	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {

		int id = -1;

		id ++;
		atypename[id] = "Glass";
		aitemIDdefault[id] = 23571;
		id ++;
		atypename[id] = "Ancient";
		aitemIDdefault[id] = 23620;
		id ++;
		atypename[id] = "Coal";
		aitemIDdefault[id] = 23652;
		id ++;
		atypename[id] = "Obsidian";
		aitemIDdefault[id] = 23668;
		id ++;
		atypename[id] = "ExVanilla";
		aitemIDdefault[id] = 23684;
		id ++;
		atypename[id] = "LapisLazuli";
		aitemIDdefault[id] = 23716;
		id ++;
		atypename[id] = "Redstone";
		aitemIDdefault[id] = 23732;
		id ++;
		atypename[id] = "Rainbow";
		aitemIDdefault[id] = 23748;
		id ++;
		atypename[id] = "Quartz";
		aitemIDdefault[id] = 23764;
		id ++;
		atypename[id] = "Emerald";
		aitemIDdefault[id] = 23780;
		id ++;
		atypename[id] = "Flint";
		aitemIDdefault[id] = 23796;
		id ++;
		atypename[id] = "Slime";
		aitemIDdefault[id] = 23812;
		id ++;
		atypename[id] = "Star";
		aitemIDdefault[id] = 23828;
		id ++;
		atypename[id] = "Bone";
		aitemIDdefault[id] = 23860;
		id ++;
		atypename[id] = "Ender";
		aitemIDdefault[id] = 23892;
		id ++;
		atypename[id] = "ExArcher";
		aitemIDdefault[id] = 23908;
		id ++;
		atypename[id] = "ExArcher2";
		aitemIDdefault[id] = 23924;
		id ++;
		atypename[id] = "Blaze";
		aitemIDdefault[id] = 23940;
		id ++;
		atypename[id] = "Pumpkin";
		aitemIDdefault[id] = 23956;
		id ++;
		atypename[id] = "Lantern";
		aitemIDdefault[id] = 23972;
		id ++;
		atypename[id] = "Cactus";
		aitemIDdefault[id] = 24014;
		id ++;
		atypename[id] = "Glowstone";
		aitemIDdefault[id] = 24030;
		id ++;
		atypename[id] = "Ghast";
		aitemIDdefault[id] = 24046;
		id ++;
		atypename[id] = "Poison";
		aitemIDdefault[id] = 24078;
		id ++;
		atypename[id] = "Ice";
		aitemIDdefault[id] = 24094;
		

		
		id = -1;
		id++;
		aitemname[id][0] = "GlassStick";
		aitemname[id][1] = "Glass Stick";
		aitemname[id][2] = "ガラスの棒";
		id++;
		aitemname[id][0] = "GlassEdge";
		aitemname[id][1] = "Glass Edge";
		aitemname[id][2] = "ガラスの刃";
		id++;
		aitemname[id][0] = "GlassSword";
		aitemname[id][1] = "Glass Sword";
		aitemname[id][2] = "ガラスのつるぎ";
		id++;
		aitemname[id][0] = "GlassShovel";
		aitemname[id][1] = "Glass Shovel";
		aitemname[id][2] = "ガラスのショベル";
		id++;
		aitemname[id][0] = "GlassPickaxe";
		aitemname[id][1] = "Glass Pickaxe";
		aitemname[id][2] = "ガラスのツルハシ";
		id++;
		aitemname[id][0] = "GlassAxe";
		aitemname[id][1] = "Glass Axe";
		aitemname[id][2] = "ガラスの斧";
		id++;
		aitemname[id][0] = "GlassHoe";
		aitemname[id][1] = "Glass Hoe";
		aitemname[id][2] = "ガラスのクワ";
		id++;
		aitemname[id][0] = "GlassHammer";
		aitemname[id][1] = "Glass Hammer";
		aitemname[id][2] = "ガラスのハンマー";
		id++;
		aitemname[id][0] = "GlassHelmet";
		aitemname[id][1] = "Glass Helmet";
		aitemname[id][2] = "ガラスのヘルメット";
		id++;
		aitemname[id][0] = "GlassChestplate";
		aitemname[id][1] = "Glass Chestplate";
		aitemname[id][2] = "ガラスのチェストプレート";
		id++;
		aitemname[id][0] = "GlassLeggings";
		aitemname[id][1] = "Glass Leggings";
		aitemname[id][2] = "ガラスのレギンス";
		id++;
		aitemname[id][0] = "GlassBoots";
		aitemname[id][1] = "Glass Boots";
		aitemname[id][2] = "ガラスのブーツ";
		id++;
		aitemname[id][0] = "GlassBow";
		aitemname[id][1] = "Glass Bow";
		aitemname[id][2] = "ガラスの弓";
		id++;
		aitemname[id][0] = "GlassArrow";
		aitemname[id][1] = "Glass Arrow";
		aitemname[id][2] = "ガラスの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "StoneStick";
		aitemname[id][1] = "Stone Stick";
		aitemname[id][2] = "石の棒";
		id++;
		aitemname[id][0] = "AncientSword";
		aitemname[id][1] = "Ancient Sword";
		aitemname[id][2] = "古代の剣";
		id++;
		aitemname[id][0] = "AncientShovel";
		aitemname[id][1] = "Ancient Shovel";
		aitemname[id][2] = "古代のショベル";
		id++;
		aitemname[id][0] = "AncientPickaxe";
		aitemname[id][1] = "Ancient Pickaxe";
		aitemname[id][2] = "古代のツルハシ";
		id++;
		aitemname[id][0] = "AncientAxe";
		aitemname[id][1] = "Ancient Axe";
		aitemname[id][2] = "古代の斧";
		id++;
		aitemname[id][0] = "AncientHoe";
		aitemname[id][1] = "Ancient Hoe";
		aitemname[id][2] = "古代のクワ";
		id++;
		aitemname[id][0] = "AncientHammer";
		aitemname[id][1] = "Ancient Hammer";
		aitemname[id][2] = "古代のハンマー";
		id++;
		aitemname[id][0] = "AncientHelmet";
		aitemname[id][1] = "Ancient Helmet";
		aitemname[id][2] = "古代のヘルメット";
		id++;
		aitemname[id][0] = "AncientChestplate";
		aitemname[id][1] = "Ancient Chestplate";
		aitemname[id][2] = "古代のチェストプレート";
		id++;
		aitemname[id][0] = "AncientLeggings";
		aitemname[id][1] = "Ancient Leggings";
		aitemname[id][2] = "古代のレギンス";
		id++;
		aitemname[id][0] = "AncientBoots";
		aitemname[id][1] = "Ancient Boots";
		aitemname[id][2] = "古代のブーツ";
		id++;
		aitemname[id][0] = "AncientBow";
		aitemname[id][1] = "Ancient Bow";
		aitemname[id][2] = "古代の弓";
		id++;
		aitemname[id][0] = "AncientArrow";
		aitemname[id][1] = "Ancient Arrow";
		aitemname[id][2] = "古代の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "CoalSword";
		aitemname[id][1] = "Coal Sword";
		aitemname[id][2] = "石炭の剣";
		id++;
		aitemname[id][0] = "CoalShovel";
		aitemname[id][1] = "Coal Shovel";
		aitemname[id][2] = "石炭のショベル";
		id++;
		aitemname[id][0] = "CoalPickaxe";
		aitemname[id][1] = "Coal Pickaxe";
		aitemname[id][2] = "石炭のツルハシ";
		id++;
		aitemname[id][0] = "CoalAxe";
		aitemname[id][1] = "Coal Axe";
		aitemname[id][2] = "石炭の斧";
		id++;
		aitemname[id][0] = "CoalHoe";
		aitemname[id][1] = "Coal Hoe";
		aitemname[id][2] = "石炭のクワ";
		id++;
		aitemname[id][0] = "CoalHammer";
		aitemname[id][1] = "Coal Hammer";
		aitemname[id][2] = "石炭のハンマー";
		id++;
		aitemname[id][0] = "CoalHelmet";
		aitemname[id][1] = "Coal Helmet";
		aitemname[id][2] = "石炭のヘルメット";
		id++;
		aitemname[id][0] = "CoalChestplate";
		aitemname[id][1] = "Coal Chestplate";
		aitemname[id][2] = "石炭のチェストプレート";
		id++;
		aitemname[id][0] = "CoalLeggings";
		aitemname[id][1] = "Coal Leggings";
		aitemname[id][2] = "石炭のレギンス";
		id++;
		aitemname[id][0] = "CoalBoots";
		aitemname[id][1] = "Coal Boots";
		aitemname[id][2] = "石炭のブーツ";
		id++;
		aitemname[id][0] = "CoalBow";
		aitemname[id][1] = "Coal Bow";
		aitemname[id][2] = "石炭の弓";
		id++;
		aitemname[id][0] = "CoalArrow";
		aitemname[id][1] = "Coal Arrow";
		aitemname[id][2] = "石炭の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "ObsidianSword";
		aitemname[id][1] = "Obsidian Sword";
		aitemname[id][2] = "黒曜石の剣";
		id++;
		aitemname[id][0] = "ObsidianShovel";
		aitemname[id][1] = "Obsidian Shovel";
		aitemname[id][2] = "黒曜石のショベル";
		id++;
		aitemname[id][0] = "ObsidianPickaxe";
		aitemname[id][1] = "Obsidian Pickaxe";
		aitemname[id][2] = "黒曜石のツルハシ";
		id++;
		aitemname[id][0] = "ObsidianAxe";
		aitemname[id][1] = "Obsidian Axe";
		aitemname[id][2] = "黒曜石の斧";
		id++;
		aitemname[id][0] = "ObsidianHoe";
		aitemname[id][1] = "Obsidian Hoe";
		aitemname[id][2] = "黒曜石のクワ";
		id++;
		aitemname[id][0] = "ObsidianHammer";
		aitemname[id][1] = "Obsidian Hammer";
		aitemname[id][2] = "黒曜石のハンマー";
		id++;
		aitemname[id][0] = "ObsidianHelmet";
		aitemname[id][1] = "Obsidian Helmet";
		aitemname[id][2] = "黒曜石のヘルメット";
		id++;
		aitemname[id][0] = "ObsidianChestplate";
		aitemname[id][1] = "Obsidian Chestplate";
		aitemname[id][2] = "黒曜石のチェストプレート";
		id++;
		aitemname[id][0] = "ObsidianLeggings";
		aitemname[id][1] = "Obsidian Leggings";
		aitemname[id][2] = "黒曜石のレギンス";
		id++;
		aitemname[id][0] = "ObsidianBoots";
		aitemname[id][1] = "Obsidian Boots";
		aitemname[id][2] = "黒曜石のブーツ";
		id++;
		aitemname[id][0] = "ObsidianShears";
		aitemname[id][1] = "Obsidian Shears";
		aitemname[id][2] = "黒曜石のハサミ";
		id++;
		aitemname[id][0] = "ObsidianBow";
		aitemname[id][1] = "Obsidian Bow";
		aitemname[id][2] = "黒曜石の弓";
		id++;
		aitemname[id][0] = "ObsidianArrow";
		aitemname[id][1] = "Obsidian Arrow";
		aitemname[id][2] = "黒曜石の矢";

		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "WoodenHammer";
		aitemname[id][1] = "Wooden Hammer";
		aitemname[id][2] = "木のハンマー";
		id++;
		aitemname[id][0] = "StoneHammer";
		aitemname[id][1] = "Stone Hammer";
		aitemname[id][2] = "石のハンマー";
		id++;
		aitemname[id][0] = "IronHammer";
		aitemname[id][1] = "Iron Hammer";
		aitemname[id][2] = "鉄のハンマー";
		id++;
		aitemname[id][0] = "GoldenHammer";
		aitemname[id][1] = "Golden Hammer";
		aitemname[id][2] = "金のハンマー";
		id++;
		aitemname[id][0] = "DiamondHammer";
		aitemname[id][1] = "Diamond Hammer";
		aitemname[id][2] = "ダイヤのハンマー";
		id++;
		aitemname[id][0] = "WoodenHelmet";
		aitemname[id][1] = "Wooden Helmet";
		aitemname[id][2] = "木のヘルメット";
		id++;
		aitemname[id][0] = "WoodenChestplate";
		aitemname[id][1] = "Wooden Chestplate";
		aitemname[id][2] = "木のチェストプレート";
		id++;
		aitemname[id][0] = "WoodenLeggings";
		aitemname[id][1] = "Wooden Leggings";
		aitemname[id][2] = "木のレギンス";
		id++;
		aitemname[id][0] = "WoodenBoots";
		aitemname[id][1] = "Wooden Boots";
		aitemname[id][2] = "木のブーツ";
		id++;
		aitemname[id][0] = "StoneHelmet";
		aitemname[id][1] = "Stone Helmet";
		aitemname[id][2] = "石のヘルメット";
		id++;
		aitemname[id][0] = "StoneChestplate";
		aitemname[id][1] = "Stone Chestplate";
		aitemname[id][2] = "石のチェストプレート";
		id++;
		aitemname[id][0] = "StoneLeggings";
		aitemname[id][1] = "Stone Leggings";
		aitemname[id][2] = "石のレギンス";
		id++;
		aitemname[id][0] = "StoneBoots";
		aitemname[id][1] = "Stone Boots";
		aitemname[id][2] = "石のブーツ";
		id++;
		aitemname[id][0] = "GoldenShears";
		aitemname[id][1] = "Golden Shears";
		aitemname[id][2] = "金のハサミ";
		id++;
		aitemname[id][0] = "DiamondShears";
		aitemname[id][1] = "Diamond Shears";
		aitemname[id][2] = "ダイヤのハサミ";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "LapisLazuliSword";
		aitemname[id][1] = "Lapis Lazuli Sword";
		aitemname[id][2] = "ラピスラズリの剣";
		id++;
		aitemname[id][0] = "LapisLazuliShovel";
		aitemname[id][1] = "Lapis Lazuli Shovel";
		aitemname[id][2] = "ラピスラズリのショベル";
		id++;
		aitemname[id][0] = "LapisLazuliPickaxe";
		aitemname[id][1] = "Lapis Lazuli Pickaxe";
		aitemname[id][2] = "ラピスラズリのツルハシ";
		id++;
		aitemname[id][0] = "LapisLazuliAxe";
		aitemname[id][1] = "Lapis Lazuli Axe";
		aitemname[id][2] = "ラピスラズリの斧";
		id++;
		aitemname[id][0] = "LapisLazuliHoe";
		aitemname[id][1] = "Lapis Lazuli Hoe";
		aitemname[id][2] = "ラピスラズリのクワ";
		id++;
		aitemname[id][0] = "LapisLazuliHammer";
		aitemname[id][1] = "Lapis Lazuli Hammer";
		aitemname[id][2] = "ラピスラズリのハンマー";
		id++;
		aitemname[id][0] = "LapisLazuliHelmet";
		aitemname[id][1] = "Lapis Lazuli Helmet";
		aitemname[id][2] = "ラピスラズリのヘルメット";
		id++;
		aitemname[id][0] = "LapisLazuliChestplate";
		aitemname[id][1] = "Lapis Lazuli Chestplate";
		aitemname[id][2] = "ラピスラズリのチェストプレート";
		id++;
		aitemname[id][0] = "LapisLazuliLeggings";
		aitemname[id][1] = "Lapis Lazuli Leggings";
		aitemname[id][2] = "ラピスラズリのレギンス";
		id++;
		aitemname[id][0] = "LapisLazuliBoots";
		aitemname[id][1] = "Lapis Lazuli Boots";
		aitemname[id][2] = "ラピスラズリのブーツ";
		id++;
		aitemname[id][0] = "LapisLazuliShears";
		aitemname[id][1] = "Lapis Lazuli Shears";
		aitemname[id][2] = "ラピスラズリのハサミ";
		id++;
		aitemname[id][0] = "LapisLazuliBow";
		aitemname[id][1] = "Lapis Lazuli Bow";
		aitemname[id][2] = "ラピスラズリの弓";
		id++;
		aitemname[id][0] = "LapisLazuliArrow";
		aitemname[id][1] = "Lapis Lazuli Arrow";
		aitemname[id][2] = "ラピスラズリの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "RedstoneSword";
		aitemname[id][1] = "Redstone Sword";
		aitemname[id][2] = "レッドストーンの剣";
		id++;
		aitemname[id][0] = "RedstoneShovel";
		aitemname[id][1] = "Redstone Shovel";
		aitemname[id][2] = "レッドストーンのショベル";
		id++;
		aitemname[id][0] = "RedstonePickaxe";
		aitemname[id][1] = "Redstone Pickaxe";
		aitemname[id][2] = "レッドストーンのツルハシ";
		id++;
		aitemname[id][0] = "RedstoneAxe";
		aitemname[id][1] = "Redstone Axe";
		aitemname[id][2] = "レッドストーンの斧";
		id++;
		aitemname[id][0] = "RedstoneHoe";
		aitemname[id][1] = "Redstone Hoe";
		aitemname[id][2] = "レッドストーンのクワ";
		id++;
		aitemname[id][0] = "RedstoneHammer";
		aitemname[id][1] = "Redstone Hammer";
		aitemname[id][2] = "レッドストーンのハンマー";
		id++;
		aitemname[id][0] = "RedstoneHelmet";
		aitemname[id][1] = "Redstone Helmet";
		aitemname[id][2] = "レッドストーンのヘルメット";
		id++;
		aitemname[id][0] = "RedstoneChestplate";
		aitemname[id][1] = "Redstone Chestplate";
		aitemname[id][2] = "レッドストーンのチェストプレート";
		id++;
		aitemname[id][0] = "RedstoneLeggings";
		aitemname[id][1] = "Redstone Leggings";
		aitemname[id][2] = "レッドストーンのレギンス";
		id++;
		aitemname[id][0] = "RedstoneBoots";
		aitemname[id][1] = "Redstone Boots";
		aitemname[id][2] = "レッドストーンのブーツ";
		id++;
		aitemname[id][0] = "RedstoneShears";
		aitemname[id][1] = "Redstone Shears";
		aitemname[id][2] = "レッドストーンのハサミ";
		id++;
		aitemname[id][0] = "RedstoneBow";
		aitemname[id][1] = "Redstone Bow";
		aitemname[id][2] = "レッドストーンの弓";
		id++;
		aitemname[id][0] = "RedstoneArrow";
		aitemname[id][1] = "Redstone Arrow";
		aitemname[id][2] = "レッドストーンの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "RainbowShard";
		aitemname[id][1] = "Rainbow Shard";
		aitemname[id][2] = "虹のかけら";
		id++;
		aitemname[id][0] = "RainbowSword";
		aitemname[id][1] = "Rainbow Sword";
		aitemname[id][2] = "虹の剣";
		id++;
		aitemname[id][0] = "RainbowShovel";
		aitemname[id][1] = "Rainbow Shovel";
		aitemname[id][2] = "虹のショベル";
		id++;
		aitemname[id][0] = "RainbowPickaxe";
		aitemname[id][1] = "Rainbow Pickaxe";
		aitemname[id][2] = "虹のツルハシ";
		id++;
		aitemname[id][0] = "RainbowAxe";
		aitemname[id][1] = "Rainbow Axe";
		aitemname[id][2] = "虹の斧";
		id++;
		aitemname[id][0] = "RainbowHoe";
		aitemname[id][1] = "Rainbow Hoe";
		aitemname[id][2] = "虹のクワ";
		id++;
		aitemname[id][0] = "RainbowHammer";
		aitemname[id][1] = "Rainbow Hammer";
		aitemname[id][2] = "虹のハンマー";
		id++;
		aitemname[id][0] = "RainbowHelmet";
		aitemname[id][1] = "Rainbow Helmet";
		aitemname[id][2] = "虹のヘルメット";
		id++;
		aitemname[id][0] = "RainbowChestplate";
		aitemname[id][1] = "Rainbow Chestplate";
		aitemname[id][2] = "虹のチェストプレート";
		id++;
		aitemname[id][0] = "RainbowLeggings";
		aitemname[id][1] = "Rainbow Leggings";
		aitemname[id][2] = "虹のレギンス";
		id++;
		aitemname[id][0] = "RainbowBoots";
		aitemname[id][1] = "Rainbow Boots";
		aitemname[id][2] = "虹のブーツ";
		id++;
		aitemname[id][0] = "Rainbow";
		aitemname[id][1] = "Rainbow";
		aitemname[id][2] = "虹";
		id++;
		aitemname[id][0] = "RainbowArrow";
		aitemname[id][1] = "Rainbow Arrow";
		aitemname[id][2] = "虹の矢";
		id++;
		aitemname[id][0] = "SkyArrow";
		aitemname[id][1] = "Sky Arrow";
		aitemname[id][2] = "青空の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "QuartzSword";
		aitemname[id][1] = "Quartz Sword";
		aitemname[id][2] = "水晶の剣";
		id++;
		aitemname[id][0] = "QuartzShovel";
		aitemname[id][1] = "Quartz Shovel";
		aitemname[id][2] = "水晶のショベル";
		id++;
		aitemname[id][0] = "QuartzPickaxe";
		aitemname[id][1] = "Quartz Pickaxe";
		aitemname[id][2] = "水晶のツルハシ";
		id++;
		aitemname[id][0] = "QuartzAxe";
		aitemname[id][1] = "Quartz Axe";
		aitemname[id][2] = "水晶の斧";
		id++;
		aitemname[id][0] = "QuartzHoe";
		aitemname[id][1] = "Quartz Hoe";
		aitemname[id][2] = "水晶のクワ";
		id++;
		aitemname[id][0] = "QuartzHammer";
		aitemname[id][1] = "Quartz Hammer";
		aitemname[id][2] = "水晶のハンマー";
		id++;
		aitemname[id][0] = "QuartzHelmet";
		aitemname[id][1] = "Quartz Helmet";
		aitemname[id][2] = "水晶のヘルメット";
		id++;
		aitemname[id][0] = "QuartzChestplate";
		aitemname[id][1] = "Quartz Chestplate";
		aitemname[id][2] = "水晶のチェストプレート";
		id++;
		aitemname[id][0] = "QuartzLeggings";
		aitemname[id][1] = "Quartz Leggings";
		aitemname[id][2] = "水晶のレギンス";
		id++;
		aitemname[id][0] = "QuartzBoots";
		aitemname[id][1] = "Quartz Boots";
		aitemname[id][2] = "水晶のブーツ";
		id++;
		aitemname[id][0] = "QuartzShears";
		aitemname[id][1] = "Quartz Shears";
		aitemname[id][2] = "水晶のハサミ";
		id++;
		aitemname[id][0] = "QuartzBow";
		aitemname[id][1] = "Quartz Bow";
		aitemname[id][2] = "水晶の弓";
		id++;
		aitemname[id][0] = "QuartzArrow";
		aitemname[id][1] = "Quartz Arrow";
		aitemname[id][2] = "水晶の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "EmeraldSword";
		aitemname[id][1] = "Emerald Sword";
		aitemname[id][2] = "エメラルドの剣";
		id++;
		aitemname[id][0] = "EmeraldShovel";
		aitemname[id][1] = "Emerald Shovel";
		aitemname[id][2] = "エメラルドのショベル";
		id++;
		aitemname[id][0] = "EmeraldPickaxe";
		aitemname[id][1] = "Emerald Pickaxe";
		aitemname[id][2] = "エメラルドのツルハシ";
		id++;
		aitemname[id][0] = "EmeraldAxe";
		aitemname[id][1] = "Emerald Axe";
		aitemname[id][2] = "エメラルドの斧";
		id++;
		aitemname[id][0] = "EmeraldHoe";
		aitemname[id][1] = "Emerald Hoe";
		aitemname[id][2] = "エメラルドのクワ";
		id++;
		aitemname[id][0] = "EmeraldHammer";
		aitemname[id][1] = "Emerald Hammer";
		aitemname[id][2] = "エメラルドのハンマー";
		id++;
		aitemname[id][0] = "EmeraldHelmet";
		aitemname[id][1] = "Emerald Helmet";
		aitemname[id][2] = "エメラルドのヘルメット";
		id++;
		aitemname[id][0] = "EmeraldChestplate";
		aitemname[id][1] = "Emerald Chestplate";
		aitemname[id][2] = "エメラルドのチェストプレート";
		id++;
		aitemname[id][0] = "EmeraldLeggings";
		aitemname[id][1] = "Emerald Leggings";
		aitemname[id][2] = "エメラルドのレギンス";
		id++;
		aitemname[id][0] = "EmeraldBoots";
		aitemname[id][1] = "Emerald Boots";
		aitemname[id][2] = "エメラルドのブーツ";
		id++;
		aitemname[id][0] = "EmeraldShears";
		aitemname[id][1] = "Emerald Shears";
		aitemname[id][2] = "エメラルドのハサミ";
		id++;
		aitemname[id][0] = "EmeraldBow";
		aitemname[id][1] = "Emerald Bow";
		aitemname[id][2] = "エメラルドの弓";
		id++;
		aitemname[id][0] = "EmeraldArrow";
		aitemname[id][1] = "Emerald Arrow";
		aitemname[id][2] = "エメラルドの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "FlintSword";
		aitemname[id][1] = "Flint Sword";
		aitemname[id][2] = "火打石の剣";
		id++;
		aitemname[id][0] = "FlintShovel";
		aitemname[id][1] = "Flint Shovel";
		aitemname[id][2] = "火打石のショベル";
		id++;
		aitemname[id][0] = "FlintPickaxe";
		aitemname[id][1] = "Flint Pickaxe";
		aitemname[id][2] = "火打石のツルハシ";
		id++;
		aitemname[id][0] = "FlintAxe";
		aitemname[id][1] = "Flint Axe";
		aitemname[id][2] = "火打石の斧";
		id++;
		aitemname[id][0] = "FlintHoe";
		aitemname[id][1] = "Flint Hoe";
		aitemname[id][2] = "火打石のクワ";
		id++;
		aitemname[id][0] = "FlintHammer";
		aitemname[id][1] = "Flint Hammer";
		aitemname[id][2] = "火打石のハンマー";
		id++;
		aitemname[id][0] = "FlintHelmet";
		aitemname[id][1] = "Flint Helmet";
		aitemname[id][2] = "火打石のヘルメット";
		id++;
		aitemname[id][0] = "FlintChestplate";
		aitemname[id][1] = "Flint Chestplate";
		aitemname[id][2] = "火打石のチェストプレート";
		id++;
		aitemname[id][0] = "FlintLeggings";
		aitemname[id][1] = "Flint Leggings";
		aitemname[id][2] = "火打石のレギンス";
		id++;
		aitemname[id][0] = "FlintBoots";
		aitemname[id][1] = "Flint Boots";
		aitemname[id][2] = "火打石のブーツ";
		id++;
		aitemname[id][0] = "FlintShears";
		aitemname[id][1] = "Flint Shears";
		aitemname[id][2] = "火打石のハサミ";
		id++;
		aitemname[id][0] = "FlintBow";
		aitemname[id][1] = "Flint Bow";
		aitemname[id][2] = "火打石の弓";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "SlimeSword";
		aitemname[id][1] = "Slime Sword";
		aitemname[id][2] = "スライムの剣";
		id++;
		aitemname[id][0] = "SlimeShovel";
		aitemname[id][1] = "Slime Shovel";
		aitemname[id][2] = "スライムのショベル";
		id++;
		aitemname[id][0] = "SlimePickaxe";
		aitemname[id][1] = "Slime Pickaxe";
		aitemname[id][2] = "スライムのツルハシ";
		id++;
		aitemname[id][0] = "SlimeAxe";
		aitemname[id][1] = "Slime Axe";
		aitemname[id][2] = "スライムの斧";
		id++;
		aitemname[id][0] = "SlimeHoe";
		aitemname[id][1] = "Slime Hoe";
		aitemname[id][2] = "スライムのクワ";
		id++;
		aitemname[id][0] = "SlimeHammer";
		aitemname[id][1] = "Slime Hammer";
		aitemname[id][2] = "スライムのハンマー";
		id++;
		aitemname[id][0] = "SlimeHelmet";
		aitemname[id][1] = "Slime Helmet";
		aitemname[id][2] = "スライムのヘルメット";
		id++;
		aitemname[id][0] = "SlimeChestplate";
		aitemname[id][1] = "Slime Chestplate";
		aitemname[id][2] = "スライムのチェストプレート";
		id++;
		aitemname[id][0] = "SlimeLeggings";
		aitemname[id][1] = "Slime Leggings";
		aitemname[id][2] = "スライムのレギンス";
		id++;
		aitemname[id][0] = "SlimeBoots";
		aitemname[id][1] = "Slime Boots";
		aitemname[id][2] = "スライムのブーツ";
		id++;
		aitemname[id][0] = "SlimeBow";
		aitemname[id][1] = "Slime Bow";
		aitemname[id][2] = "スライムの弓";
		id++;
		aitemname[id][0] = "SlimeArrow";
		aitemname[id][1] = "Slime Arrow";
		aitemname[id][2] = "スライムの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "StarSword";
		aitemname[id][1] = "Star Sword";
		aitemname[id][2] = "スターソード";
		id++;
		aitemname[id][0] = "StarShovel";
		aitemname[id][1] = "Star Shovel";
		aitemname[id][2] = "スターショベル";
		id++;
		aitemname[id][0] = "StarPickaxe";
		aitemname[id][1] = "Star Pickaxe";
		aitemname[id][2] = "スターピックアックス";
		id++;
		aitemname[id][0] = "StarAxe";
		aitemname[id][1] = "Star Axe";
		aitemname[id][2] = "スターアックス";
		id++;
		aitemname[id][0] = "StarHoe";
		aitemname[id][1] = "Star Hoe";
		aitemname[id][2] = "スターホゥ";
		id++;
		aitemname[id][0] = "StarHammer";
		aitemname[id][1] = "Star Hammer";
		aitemname[id][2] = "スターハンマー";
		id++;
		aitemname[id][0] = "StarHelmet";
		aitemname[id][1] = "Star Helmet";
		aitemname[id][2] = "スターヘルメット";
		id++;
		aitemname[id][0] = "StarChestplate";
		aitemname[id][1] = "Star Chestplate";
		aitemname[id][2] = "スターチェストプレート";
		id++;
		aitemname[id][0] = "StarLeggings";
		aitemname[id][1] = "Star Leggings";
		aitemname[id][2] = "スターレギンス";
		id++;
		aitemname[id][0] = "StarBoots";
		aitemname[id][1] = "Star Boots";
		aitemname[id][2] = "スターブーツ";
		id++;
		aitemname[id][0] = "StarShears";
		aitemname[id][1] = "Star Shears";
		aitemname[id][2] = "スターシェアーズ";
		id++;
		aitemname[id][0] = "StarBow";
		aitemname[id][1] = "Star Bow";
		aitemname[id][2] = "スターボウ";
		id++;
		aitemname[id][0] = "StarArrow";
		aitemname[id][1] = "Shooting Star";
		aitemname[id][2] = "シューティングスター";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "BoneSword";
		aitemname[id][1] = "Bone Sword";
		aitemname[id][2] = "骨の剣";
		id++;
		aitemname[id][0] = "BoneShovel";
		aitemname[id][1] = "Bone Shovel";
		aitemname[id][2] = "骨のショベル";
		id++;
		aitemname[id][0] = "BonePickaxe";
		aitemname[id][1] = "Bone Pickaxe";
		aitemname[id][2] = "骨のツルハシ";
		id++;
		aitemname[id][0] = "BoneAxe";
		aitemname[id][1] = "Bone Axe";
		aitemname[id][2] = "ja_JP";
		aitemname[id][2] = "骨の斧";
		id++;
		aitemname[id][0] = "BoneHoe";
		aitemname[id][1] = "Bone Hoe";
		aitemname[id][2] = "骨のクワ";
		id++;
		aitemname[id][0] = "BoneHammer";
		aitemname[id][1] = "Bone Hammer";
		aitemname[id][2] = "骨のハンマー";
		id++;
		aitemname[id][0] = "BoneHelmet";
		aitemname[id][1] = "Bone Helmet";
		aitemname[id][2] = "骨のヘルメット";
		id++;
		aitemname[id][0] = "BoneChestplate";
		aitemname[id][1] = "Bone Chestplate";
		aitemname[id][2] = "骨のチェストプレート";
		id++;
		aitemname[id][0] = "BoneLeggings";
		aitemname[id][1] = "Bone Leggings";
		aitemname[id][2] = "骨のレギンス";
		id++;
		aitemname[id][0] = "BoneBoots";
		aitemname[id][1] = "Bone Boots";
		aitemname[id][2] = "骨のブーツ";
		id++;
		aitemname[id][0] = "BoneBow";
		aitemname[id][1] = "Bone Bow";
		aitemname[id][2] = "骨の弓";
		id++;
		aitemname[id][0] = "BoneArrow";
		aitemname[id][1] = "Bone Arrow";
		aitemname[id][2] = "骨の矢";
		id++;
		aitemname[id][0] = "WitherArrow";
		aitemname[id][1] = "Wither Arrow";
		aitemname[id][2] = "ウィザーの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "EnderSword";
		aitemname[id][1] = "Ender Sword";
		aitemname[id][2] = "エンドの剣";
		id++;
		aitemname[id][0] = "EnderShovel";
		aitemname[id][1] = "Ender Shovel";
		aitemname[id][2] = "エンドのショベル";
		id++;
		aitemname[id][0] = "EnderPickaxe";
		aitemname[id][1] = "Ender Pickaxe";
		aitemname[id][2] = "エンドのツルハシ";
		id++;
		aitemname[id][0] = "EnderAxe";
		aitemname[id][1] = "Ender Axe";
		aitemname[id][2] = "エンドの斧";
		id++;
		aitemname[id][0] = "EnderHoe";
		aitemname[id][1] = "Ender Hoe";
		aitemname[id][2] = "エンドのクワ";
		id++;
		aitemname[id][0] = "EnderHammer";
		aitemname[id][1] = "Ender Hammer";
		aitemname[id][2] = "エンドのハンマー";
		id++;
		aitemname[id][0] = "EnderHelmet";
		aitemname[id][1] = "Ender Helmet";
		aitemname[id][2] = "エンドのヘルメット";
		id++;
		aitemname[id][0] = "EnderChestplate";
		aitemname[id][1] = "Ender Chestplate";
		aitemname[id][2] = "エンドのチェストプレート";
		id++;
		aitemname[id][0] = "EnderLeggings";
		aitemname[id][1] = "Ender Leggings";
		aitemname[id][2] = "エンドのレギンス";
		id++;
		aitemname[id][0] = "EnderBoots";
		aitemname[id][1] = "Ender Boots";
		aitemname[id][2] = "エンドのブーツ";
		id++;
		aitemname[id][0] = "EnderShears";
		aitemname[id][1] = "Ender Shears";
		aitemname[id][2] = "エンドのハサミ";
		id++;
		aitemname[id][0] = "EnderBow";
		aitemname[id][1] = "Ender Bow";
		aitemname[id][2] = "エンドの弓";
		id++;
		aitemname[id][0] = "EnderArrow";
		aitemname[id][1] = "Ender Arrow";
		aitemname[id][2] = "エンドの矢";

		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "StoneBow";
		aitemname[id][1] = "Stone Bow";
		aitemname[id][2] = "石の弓";
		id++;
		aitemname[id][0] = "IronBow";
		aitemname[id][1] = "Iron Bow";
		aitemname[id][2] = "鉄の弓";
		id++;
		aitemname[id][0] = "DiamondBow";
		aitemname[id][1] = "Diamond Bow";
		aitemname[id][2] = "ダイヤの弓";
		id++;
		aitemname[id][0] = "GoldenBow";
		aitemname[id][1] = "Golden Bow";
		aitemname[id][2] = "ja_JP";
		aitemname[id][2] = "金の弓";
		id++;
		aitemname[id][0] = "WoodenArrow";
		aitemname[id][1] = "Wooden Arrow";
		aitemname[id][2] = "木の矢";
		id++;
		aitemname[id][0] = "StoneArrow";
		aitemname[id][1] = "Stone Arrow";
		aitemname[id][2] = "ja_JP";
		aitemname[id][2] = "石の矢";
		id++;
		aitemname[id][0] = "IronArrow";
		aitemname[id][1] = "Iron Arrow";
		aitemname[id][2] = "鉄の矢";
		id++;
		aitemname[id][0] = "DiamondArrow";
		aitemname[id][1] = "Diamond Arrow";
		aitemname[id][2] = "ja_JP";
		aitemname[id][2] = "ダイヤの矢";
		id++;
		aitemname[id][0] = "GoldenArrow";
		aitemname[id][1] = "Golden Arrow";
		aitemname[id][2] = "金の矢";
		id++;
		aitemname[id][0] = "VenomArrow";
		aitemname[id][1] = "Venom Arrow";
		aitemname[id][2] = "毒矢";
		id++;
		aitemname[id][0] = "ParalysisArrow";
		aitemname[id][1] = "Paralysis Arrow";
		aitemname[id][2] = "麻痺の矢";
		id++;
		aitemname[id][0] = "HealingArrow";
		aitemname[id][1] = "Healing Arrow";
		aitemname[id][2] = "癒しの矢";
		id++;
		aitemname[id][0] = "CursedArrow";
		aitemname[id][1] = "Cursed Arrow";
		aitemname[id][2] = "呪われた矢";
		id++;
		aitemname[id][0] = "BlowArrow";
		aitemname[id][1] = "Gale Arrow";
		aitemname[id][2] = "吹き飛ばしの矢";
		id++;
		aitemname[id][0] = "AttractionArrow";
		aitemname[id][1] = "Attraction Arrow";
		aitemname[id][2] = "引き寄せの矢";
		id++;
		aitemname[id][0] = "LightningArrow";
		aitemname[id][1] = "Lightning Arrow";
		aitemname[id][2] = "稲妻の矢";
		id++;
		aitemname[id][0] = "BombArrow";
		aitemname[id][1] = "Bomb Arrow";
		aitemname[id][2] = "爆弾の矢";
		id++;
		aitemname[id][0] = "CreeperArrow";
		aitemname[id][1] = "Creeper Arrow";
		aitemname[id][2] = "クリーパーの矢";
		id++;
		aitemname[id][0] = "IncendiaryArrow";
		aitemname[id][1] = "Incendiary Arrow";
		aitemname[id][2] = "焼夷矢";
		id++;
		aitemname[id][0] = "MagicalArrow";
		aitemname[id][1] = "Magical Arrow";
		aitemname[id][2] = "魔法の矢";
		id++;
		aitemname[id][0] = "TorchArrow";
		aitemname[id][1] = "Torch Arrow";
		aitemname[id][2] = "松明の矢";
		id++;
		aitemname[id][0] = "AssassinationArrow";
		aitemname[id][1] = "Arrow of Assassination";
		aitemname[id][2] = "暗殺の矢";

		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "BlazeQuartz";
		aitemname[id][1] = "Blaze Quartz";
		aitemname[id][2] = "ブレイズクォーツ";
		id++;
		aitemname[id][0] = "BlazeSword";
		aitemname[id][1] = "Blaze Sword";
		aitemname[id][2] = "ブレイズの剣";
		id++;
		aitemname[id][0] = "BlazeShovel";
		aitemname[id][1] = "Blaze Shovel";
		aitemname[id][2] = "ブレイズのショベル";
		id++;
		aitemname[id][0] = "BlazePickaxe";
		aitemname[id][1] = "Blaze Pickaxe";
		aitemname[id][2] = "ブレイズのツルハシ";
		id++;
		aitemname[id][0] = "BlazeAxe";
		aitemname[id][1] = "Blaze Axe";
		aitemname[id][2] = "ブレイズの斧";
		id++;
		aitemname[id][0] = "BlazeHoe";
		aitemname[id][1] = "Blaze Hoe";
		aitemname[id][2] = "ブレイズのクワ";
		id++;
		aitemname[id][0] = "BlazeHammer";
		aitemname[id][1] = "Blaze Hammer";
		aitemname[id][2] = "ブレイズのハンマー";
		id++;
		aitemname[id][0] = "BlazeHelmet";
		aitemname[id][1] = "Blaze Helmet";
		aitemname[id][2] = "ブレイズのヘルメット";
		id++;
		aitemname[id][0] = "BlazeChestplate";
		aitemname[id][1] = "Blaze Chestplate";
		aitemname[id][2] = "ブレイズのチェストプレート";
		id++;
		aitemname[id][0] = "BlazeLeggings";
		aitemname[id][1] = "Blaze Leggings";
		aitemname[id][2] = "ブレイズのレギンス";
		id++;
		aitemname[id][0] = "BlazeBoots";
		aitemname[id][1] = "Blaze Boots";
		aitemname[id][2] = "ブレイズのブーツ";
		id++;
		aitemname[id][0] = "BlazeShears";
		aitemname[id][1] = "Blaze Shears";
		aitemname[id][2] = "ブレイズのハサミ";
		id++;
		aitemname[id][0] = "BlazeBow";
		aitemname[id][1] = "Blaze Bow";
		aitemname[id][2] = "ブレイズの弓";
		id++;
		aitemname[id][0] = "BlazeArrow";
		aitemname[id][1] = "Blaze Arrow";
		aitemname[id][2] = "ブレイズの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "PumpkinSword";
		aitemname[id][1] = "Pumpkin Sword";
		aitemname[id][2] = "カボチャの剣";
		id++;
		aitemname[id][0] = "PumpkinShovel";
		aitemname[id][1] = "Pumpkin Shovel";
		aitemname[id][2] = "カボチャのショベル";
		id++;
		aitemname[id][0] = "PumpkinPickaxe";
		aitemname[id][1] = "Pumpkin Pickaxe";
		aitemname[id][2] = "カボチャのツルハシ";
		id++;
		aitemname[id][0] = "PumpkinAxe";
		aitemname[id][1] = "Pumpkin Axe";
		aitemname[id][2] = "カボチャの斧";
		id++;
		aitemname[id][0] = "PumpkinHoe";
		aitemname[id][1] = "Pumpkin Hoe";
		aitemname[id][2] = "カボチャのクワ";
		id++;
		aitemname[id][0] = "PumpkinHammer";
		aitemname[id][1] = "Pumpkin Hammer";
		aitemname[id][2] = "カボチャのハンマー";
		id++;
		aitemname[id][0] = "PumpkinHelmet";
		aitemname[id][1] = "Pumpkin Helmet";
		aitemname[id][2] = "カボチャのヘルメット";
		id++;
		aitemname[id][0] = "PumpkinChestplate";
		aitemname[id][1] = "Pumpkin Chestplate";
		aitemname[id][2] = "カボチャのチェストプレート";
		id++;
		aitemname[id][0] = "PumpkinLeggings";
		aitemname[id][1] = "Pumpkin Leggings";
		aitemname[id][2] = "カボチャのレギンス";
		id++;
		aitemname[id][0] = "PumpkinBoots";
		aitemname[id][1] = "Pumpkin Boots";
		aitemname[id][2] = "カボチャのブーツ";
		id++;
		aitemname[id][0] = "PumpkinBow";
		aitemname[id][1] = "Pumpkin Bow";
		aitemname[id][2] = "カボチャの弓";
		id++;
		aitemname[id][0] = "PumpkinArrow";
		aitemname[id][1] = "Pumpkin Arrow";
		aitemname[id][2] = "カボチャの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "LanternSword";
		aitemname[id][1] = "Jack 'o' Sword";
		aitemname[id][2] = "ジャックの剣";			//rakugaki:「瓜霊の剣」のほうが厨二臭がしていい気が
		id++;
		aitemname[id][0] = "LanternShovel";
		aitemname[id][1] = "Jack 'o' Shovel";
		aitemname[id][2] = "ジャックのショベル";
		id++;
		aitemname[id][0] = "LanternPickaxe";
		aitemname[id][1] = "Jack 'o' Pickaxe";
		aitemname[id][2] = "ジャックのツルハシ";
		id++;
		aitemname[id][0] = "LanternAxe";
		aitemname[id][1] = "Jack 'o' Axe";
		aitemname[id][2] = "ジャックの斧";
		id++;
		aitemname[id][0] = "LanternHoe";
		aitemname[id][1] = "Jack 'o' Hoe";
		aitemname[id][2] = "ジャックのクワ";
		id++;
		aitemname[id][0] = "LanternHammer";
		aitemname[id][1] = "Jack 'o' Hammer";
		aitemname[id][2] = "ジャックのハンマー";
		id++;
		aitemname[id][0] = "LanternHelmet";
		aitemname[id][1] = "Jack 'o' Helmet";
		aitemname[id][2] = "ジャックのヘルメット";
		id++;
		aitemname[id][0] = "LanternChestplate";
		aitemname[id][1] = "Jack 'o' Chestplate";
		aitemname[id][2] = "ジャックのチェストプレート";
		id++;
		aitemname[id][0] = "LanternLeggings";
		aitemname[id][1] = "Jack 'o' Leggings";
		aitemname[id][2] = "ジャックのレギンス";
		id++;
		aitemname[id][0] = "LanternBoots";
		aitemname[id][1] = "Jack 'o' Boots";
		aitemname[id][2] = "ジャックのブーツ";
		id++;
		aitemname[id][0] = "LanternBow";
		aitemname[id][1] = "Jack 'o' Bow";
		aitemname[id][2] = "ジャックの弓";
		id++;
		aitemname[id][0] = "LanternArrow";
		aitemname[id][1] = "Jack 'o' Arrow";
		aitemname[id][2] = "ジャックの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "CactusSword";
		aitemname[id][1] = "Cactus Sword";
		aitemname[id][2] = "サボテンの剣";
		id++;
		aitemname[id][0] = "CactusShovel";
		aitemname[id][1] = "Cactus Shovel";
		aitemname[id][2] = "サボテンのショベル";
		id++;
		aitemname[id][0] = "CactusPickaxe";
		aitemname[id][1] = "Cactus Pickaxe";
		aitemname[id][2] = "サボテンのツルハシ";
		id++;
		aitemname[id][0] = "CactusAxe";
		aitemname[id][1] = "Cactus Axe";
		aitemname[id][2] = "サボテンの斧";
		id++;
		aitemname[id][0] = "CactusHoe";
		aitemname[id][1] = "Cactus Hoe";
		aitemname[id][2] = "サボテンのクワ";
		id++;
		aitemname[id][0] = "CactusHammer";
		aitemname[id][1] = "Cactus Hammer";
		aitemname[id][2] = "サボテンのハンマー";
		id++;
		aitemname[id][0] = "CactusHelmet";
		aitemname[id][1] = "Cactus Helmet";
		aitemname[id][2] = "サボテンのヘルメット";
		id++;
		aitemname[id][0] = "CactusChestplate";
		aitemname[id][1] = "Cactus Chestplate";
		aitemname[id][2] = "サボテンのチェストプレート";
		id++;
		aitemname[id][0] = "CactusLeggings";
		aitemname[id][1] = "Cactus Leggings";
		aitemname[id][2] = "サボテンのレギンス";
		id++;
		aitemname[id][0] = "CactusBoots";
		aitemname[id][1] = "Cactus Boots";
		aitemname[id][2] = "サボテンのブーツ";
		id++;
		aitemname[id][0] = "CactusBow";
		aitemname[id][1] = "Cactus Bow";
		aitemname[id][2] = "サボテンの弓";
		id++;
		aitemname[id][0] = "CactusArrow";
		aitemname[id][1] = "Cactus Arrow";
		aitemname[id][2] = "サボテンの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "GlowstoneSword";
		aitemname[id][1] = "Glowstone Sword";
		aitemname[id][2] = "グロウストーンの剣";
		id++;
		aitemname[id][0] = "GlowstoneShovel";
		aitemname[id][1] = "Glowstone Shovel";
		aitemname[id][2] = "グロウストーンのショベル";
		id++;
		aitemname[id][0] = "GlowstonePickaxe";
		aitemname[id][1] = "Glowstone Pickaxe";
		aitemname[id][2] = "グロウストーンのツルハシ";
		id++;
		aitemname[id][0] = "GlowstoneAxe";
		aitemname[id][1] = "Glowstone Axe";
		aitemname[id][2] = "グロウストーンの斧";
		id++;
		aitemname[id][0] = "GlowstoneHoe";
		aitemname[id][1] = "Glowstone Hoe";
		aitemname[id][2] = "グロウストーンのクワ";
		id++;
		aitemname[id][0] = "GlowstoneHammer";
		aitemname[id][1] = "Glowstone Hammer";
		aitemname[id][2] = "グロウストーンのハンマー";
		id++;
		aitemname[id][0] = "GlowstoneHelmet";
		aitemname[id][1] = "Glowstone Helmet";
		aitemname[id][2] = "グロウストーンのヘルメット";
		id++;
		aitemname[id][0] = "GlowstoneChestplate";
		aitemname[id][1] = "Glowstone Chestplate";
		aitemname[id][2] = "グロウストーンのチェストプレート";
		id++;
		aitemname[id][0] = "GlowstoneLeggings";
		aitemname[id][1] = "Glowstone Leggings";
		aitemname[id][2] = "グロウストーンのレギンス";
		id++;
		aitemname[id][0] = "GlowstoneBoots";
		aitemname[id][1] = "Glowstone Boots";
		aitemname[id][2] = "グロウストーンのブーツ";
		id++;
		aitemname[id][0] = "GlowstoneShears";
		aitemname[id][1] = "Glowstone Shears";
		aitemname[id][2] = "グロウストーンのハサミ";
		id++;
		aitemname[id][0] = "GlowstoneBow";
		aitemname[id][1] = "Glowstone Bow";
		aitemname[id][2] = "グロウストーンの弓";
		id++;
		aitemname[id][0] = "GlowstoneArrow";
		aitemname[id][1] = "Glowstone Arrow";
		aitemname[id][2] = "グロウストーンの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "GhastSword";
		aitemname[id][1] = "Ghast Sword";
		aitemname[id][2] = "ガストの剣";
		id++;
		aitemname[id][0] = "GhastShovel";
		aitemname[id][1] = "Ghast Shovel";
		aitemname[id][2] = "ガストのショベル";
		id++;
		aitemname[id][0] = "GhastPickaxe";
		aitemname[id][1] = "Ghast Pickaxe";
		aitemname[id][2] = "ガストのツルハシ";
		id++;
		aitemname[id][0] = "GhastAxe";
		aitemname[id][1] = "Ghast Axe";
		aitemname[id][2] = "ガストの斧";
		id++;
		aitemname[id][0] = "GhastHoe";
		aitemname[id][1] = "Ghast Hoe";
		aitemname[id][2] = "ガストのクワ";
		id++;
		aitemname[id][0] = "GhastHammer";
		aitemname[id][1] = "Ghast Hammer";
		aitemname[id][2] = "ガストのハンマー";
		id++;
		aitemname[id][0] = "GhastHelmet";
		aitemname[id][1] = "Ghast Helmet";
		aitemname[id][2] = "ガストのヘルメット";
		id++;
		aitemname[id][0] = "GhastChestplate";
		aitemname[id][1] = "Ghast Chestplate";
		aitemname[id][2] = "ガストのチェストプレート";
		id++;
		aitemname[id][0] = "GhastLeggings";
		aitemname[id][1] = "Ghast Leggings";
		aitemname[id][2] = "ガストのレギンス";
		id++;
		aitemname[id][0] = "GhastBoots";
		aitemname[id][1] = "Ghast Boots";
		aitemname[id][2] = "ガストのブーツ";
		id++;
		aitemname[id][0] = "GhastShears";
		aitemname[id][1] = "Ghast Shears";
		aitemname[id][2] = "ガストのハサミ";
		id++;
		aitemname[id][0] = "GhastBow";
		aitemname[id][1] = "Ghast Bow";
		aitemname[id][2] = "ガストの弓";
		id++;
		aitemname[id][0] = "GhastArrow";
		aitemname[id][1] = "Ghast Arrow";
		aitemname[id][2] = "ガストの矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "PoisonSteel";
		aitemname[id][1] = "Poison Steel";
		aitemname[id][2] = "毒鋼";
		id++;
		aitemname[id][0] = "PoisonSword";
		aitemname[id][1] = "Poison Sword";
		aitemname[id][2] = "毒鋼の剣";
		id++;
		aitemname[id][0] = "PoisonShovel";
		aitemname[id][1] = "Poison Shovel";
		aitemname[id][2] = "毒鋼のショベル";
		id++;
		aitemname[id][0] = "PoisonPickaxe";
		aitemname[id][1] = "Poison Pickaxe";
		aitemname[id][2] = "毒鋼のツルハシ";
		id++;
		aitemname[id][0] = "PoisonAxe";
		aitemname[id][1] = "Poison Axe";
		aitemname[id][2] = "毒鋼の斧";
		id++;
		aitemname[id][0] = "PoisonHoe";
		aitemname[id][1] = "Poison Hoe";
		aitemname[id][2] = "毒鋼のクワ";
		id++;
		aitemname[id][0] = "PoisonHammer";
		aitemname[id][1] = "Poison Hammer";
		aitemname[id][2] = "毒鋼のハンマー";
		id++;
		aitemname[id][0] = "PoisonHelmet";
		aitemname[id][1] = "Poison Helmet";
		aitemname[id][2] = "毒鋼のヘルメット";
		id++;
		aitemname[id][0] = "PoisonChestplate";
		aitemname[id][1] = "Poison Chestplate";
		aitemname[id][2] = "毒鋼のチェストプレート";
		id++;
		aitemname[id][0] = "PoisonLeggings";
		aitemname[id][1] = "Poison Leggings";
		aitemname[id][2] = "毒鋼のレギンス";
		id++;
		aitemname[id][0] = "PoisonBoots";
		aitemname[id][1] = "Poison Boots";
		aitemname[id][2] = "毒鋼のブーツ";
		id++;
		aitemname[id][0] = "PoisonBow";
		aitemname[id][1] = "Poison Bow";
		aitemname[id][2] = "毒鋼の弓";
		id++;
		aitemname[id][0] = "PoisonArrow";
		aitemname[id][1] = "Poison Arrow";
		aitemname[id][2] = "毒鋼の矢";
		
		id = id / 16 * 16 + 15;
		id++;
		aitemname[id][0] = "IceStick";
		aitemname[id][1] = "Icicle";
		aitemname[id][2] = "つらら";
		id++;
		aitemname[id][0] = "IceSword";
		aitemname[id][1] = "Ice Sword";
		aitemname[id][2] = "氷の剣";
		id++;
		aitemname[id][0] = "IceShovel";
		aitemname[id][1] = "Ice Shovel";
		aitemname[id][2] = "氷のショベル";
		id++;
		aitemname[id][0] = "IcePickaxe";
		aitemname[id][1] = "Ice Pickaxe";
		aitemname[id][2] = "氷のツルハシ";
		id++;
		aitemname[id][0] = "IceAxe";
		aitemname[id][1] = "Ice Axe";
		aitemname[id][2] = "氷の斧";
		id++;
		aitemname[id][0] = "IceHoe";
		aitemname[id][1] = "Ice Hoe";
		aitemname[id][2] = "氷のクワ";
		id++;
		aitemname[id][0] = "IceHammer";
		aitemname[id][1] = "Ice Hammer";
		aitemname[id][2] = "氷のハンマー";
		id++;
		aitemname[id][0] = "IceHelmet";
		aitemname[id][1] = "Ice Helmet";
		aitemname[id][2] = "氷のヘルメット";
		id++;
		aitemname[id][0] = "IceChestplate";
		aitemname[id][1] = "Ice Chestplate";
		aitemname[id][2] = "氷のチェストプレート";
		id++;
		aitemname[id][0] = "IceLeggings";
		aitemname[id][1] = "Ice Leggings";
		aitemname[id][2] = "氷のレギンス";
		id++;
		aitemname[id][0] = "IceBoots";
		aitemname[id][1] = "Ice Boots";
		aitemname[id][2] = "氷のブーツ";
		id++;
		aitemname[id][0] = "IceShears";
		aitemname[id][1] = "Ice Shears";
		aitemname[id][2] = "氷のハサミ";
		id++;
		aitemname[id][0] = "IceBow";
		aitemname[id][1] = "Ice Bow";
		aitemname[id][2] = "氷の弓";
		id++;
		aitemname[id][0] = "IceArrow";
		aitemname[id][1] = "Ice Arrow";
		aitemname[id][2] = "氷の矢";
		id++;
		aitemname[id][0] = "IceSwordCursed";
		aitemname[id][1] = "Ice Sword";
		aitemname[id][2] = "アイスソード";
		//point: add new item name

		
		
		id = -1;

		id++;
		ablockname[id][0] = "CrystalGlass";
		ablockname[id][1] = "Crystal Glass";
		ablockname[id][2] = "クリスタルガラス";
		id++;
		ablockname[id][0] = "CrystalGlassPane";
		ablockname[id][1] = "Crystal Glass Pane";
		ablockname[id][2] = "クリスタル板ガラス";
		id++;
		ablockname[id][0] = "ObsidianGlass";
		ablockname[id][1] = "Obsidian Glass";
		ablockname[id][2] = "クリアオブシディアン";
		id++;
		ablockname[id][0] = "ObsidianGlassPane";
		ablockname[id][1] = "Obsidian Glass Pane";
		ablockname[id][2] = "クリアオブシディアンペイン";
		id++;
		ablockname[id][0] = "CoalBlock";
		ablockname[id][1] = "Block of Coal";
		ablockname[id][2] = "石炭ブロック";
		id++;
		ablockname[id][0] = "SlimeBlock";
		ablockname[id][1] = "Block of Slime";
		ablockname[id][2] = "スライムブロック";
		id++;
		ablockname[id][0] = "WhiteObsidian";
		ablockname[id][1] = "White Obsidian";
		ablockname[id][2] = "白曜石";
		

		//point: add new block name

		String header = ObjectHeader.toLowerCase() + "textures/armor/";

		id = -1;
		id ++;
		ArmorTexture[id][0] = header + "glass_1.png";
		ArmorTexture[id][1] = header + "glass_2.png";
		id ++;
		ArmorTexture[id][0] = header + "ancient_1.png";
		ArmorTexture[id][1] = header + "ancient_2.png";
		id ++;
		ArmorTexture[id][0] = header + "coal_1.png";
		ArmorTexture[id][1] = header + "coal_2.png";
		id ++;
		ArmorTexture[id][0] = header + "obsidian_1.png";
		ArmorTexture[id][1] = header + "obsidian_2.png";
		id ++;
		ArmorTexture[id][0] = header + "wood_1.png";
		ArmorTexture[id][1] = header + "wood_2.png";
		id ++;
		ArmorTexture[id][0] = header + "stone_1.png";
		ArmorTexture[id][1] = header + "stone_2.png";
		id ++;
		ArmorTexture[id][0] = header + "lapislazuli_1.png";
		ArmorTexture[id][1] = header + "lapislazuli_2.png";
		id ++;
		ArmorTexture[id][0] = header + "redstone_1.png";
		ArmorTexture[id][1] = header + "redstone_2.png";
		id ++;
		ArmorTexture[id][0] = header + "rainbow_1.png";
		ArmorTexture[id][1] = header + "rainbow_2.png";
		id ++;
		ArmorTexture[id][0] = header + "quartz_1.png";
		ArmorTexture[id][1] = header + "quartz_2.png";
		id ++;
		ArmorTexture[id][0] = header + "emerald_1.png";
		ArmorTexture[id][1] = header + "emerald_2.png";
		id ++;
		ArmorTexture[id][0] = header + "flint_1.png";
		ArmorTexture[id][1] = header + "flint_2.png";
		id ++;
		ArmorTexture[id][0] = header + "slime_1.png";
		ArmorTexture[id][1] = header + "slime_2.png";
		id ++;
		ArmorTexture[id][0] = header + "star_1.png";
		ArmorTexture[id][1] = header + "star_2.png";
		id ++;
		ArmorTexture[id][0] = header + "bone_1.png";
		ArmorTexture[id][1] = header + "bone_2.png";
		id ++;
		ArmorTexture[id][0] = header + "ender_1.png";
		ArmorTexture[id][1] = header + "ender_2.png";
		id ++;
		ArmorTexture[id][0] = header + "blaze_1.png";
		ArmorTexture[id][1] = header + "blaze_2.png";
		id ++;
		ArmorTexture[id][0] = header + "pumpkin_1.png";
		ArmorTexture[id][1] = header + "pumpkin_2.png";
		id ++;
		ArmorTexture[id][0] = header + "lantern_1.png";
		ArmorTexture[id][1] = header + "lantern_2.png";
		id ++;
		ArmorTexture[id][0] = header + "cactus_1.png";
		ArmorTexture[id][1] = header + "cactus_2.png";
		id ++;
		ArmorTexture[id][0] = header + "glowstone_1.png";
		ArmorTexture[id][1] = header + "glowstone_2.png";
		id ++;
		ArmorTexture[id][0] = header + "ghast_1.png";
		ArmorTexture[id][1] = header + "ghast_2.png";
		id ++;
		ArmorTexture[id][0] = header + "poison_1.png";
		ArmorTexture[id][1] = header + "poison_2.png";
		id ++;
		ArmorTexture[id][0] = header + "ice_1.png";
		ArmorTexture[id][1] = header + "ice_2.png";



		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );

		try	{
			cfg.load();


			cfg.addCustomCategoryComment( "ToolType", "ID of each tools. If you don't use these tools, set 0." );
			for ( int i = 0; i < atypename.length; i ++ ) {
				if ( atypename[i] == null ) continue;

				Property prop = cfg.get( "ToolType", atypename[i], aitemIDdefault[i] );
				aitemIDdefault[i] = prop.getInt();
			}

			for ( int i = 0; i < aitemID.length; i ++ ) {
				if ( aitemname[i][0] == null || aitemIDdefault[i / 16] <= 0 ) continue;
				aitemID[i] = aitemIDdefault[i / 16] + i % 16;
			}

			for ( int i = 0; i < ablockID.length; i ++ )	{
				Property BlockProp = cfg.getBlock( "BlockID_" + ablockname[i][0], ablockIDdefault + i );
				BlockProp.comment = "BlockID - " + ablockname[i][1];
				ablockID[i] = BlockProp.getInt();
			}
			
			
			Property prop = cfg.get( "General", "mobEquipmentProbability", mobEquipmentProbability );
			prop.comment = "Specify mobs are to be equipped with new equipment. [0-10000;default=100]";
			mobEquipmentProbability = prop.getInt();
			
			prop = cfg.get( "General", "replaceVanillaTools", replaceVanillaTools );
			prop.comment = "Specify that you want to replace the tools of vanilla. [true/false]";
			replaceVanillaTools = prop.getBoolean( replaceVanillaTools );
			
			prop = cfg.get( "General", "addChestContents", addChestContents );
			prop.comment = "Specify whether to include a new item to the chest. [true/false]";
			addChestContents = prop.getBoolean( addChestContents );
			
			
			prop = cfg.get( "Axe", "showBreakingEffect", Item_Axe.showBreakingEffect );
			prop.comment = "Specify that you want to display the destruction effects during harvesting. [true/false]";
			Item_Axe.showBreakingEffect = prop.getBoolean( Item_Axe.showBreakingEffect );
			
			prop = cfg.get( "Axe", "canGatherItem", Item_Axe.canGatherItem );
			prop.comment = "Specify that you want to harvest together the items in during harvesting. [true/false]";
			Item_Axe.canGatherItem = prop.getBoolean( Item_Axe.canGatherItem );
			
			prop = cfg.get( "Axe", "canCutAll", Item_Axe.canCutAll );
			prop.comment = "Specify that harvesting function is available. [0-2] 0=Disabled, 1=Disabled(Enabled to sneak in), 2=Enabled(Disabled to sneak in)";
			Item_Axe.canCutAll = prop.getInt();
			
			prop = cfg.get( "Axe", "canCutLeaves", Item_Axe.canCutLeaves );
			prop.comment = "Specify that you can also harvest leaves. [true/false]";
			Item_Axe.canCutLeaves = prop.getBoolean( Item_Axe.canCutLeaves );
			
			prop = cfg.get( "Axe", "durabilityMode", Item_Axe.durabilityMode );
			prop.comment = "Specify how the decrease of the value of durability during harvesting. [0-2] 0=decrease each time the harvesting, 1=Same as 0(Only within the range of durability), 2=Only decrease 1";
			Item_Axe.durabilityMode = prop.getInt();
			
			prop = cfg.get( "Axe", "loopLimit", Item_Axe.loopLimit );
			prop.comment = "Specify the limits of the harvesting range. [1-] Note: The number is too large to cause delay or freeze.";
			Item_Axe.loopLimit = prop.getInt();
			
			prop = cfg.get( "Axe", "breakLimit", Item_Axe.breakLimit );
			prop.comment = "Specify the limits of the number of harvesting. [1-] Note: The number is too large to cause delay or freeze.";
			Item_Axe.breakLimit = prop.getInt();
			
			
			prop = cfg.get( "Hoe", "canUseExtendedMode", Item_Hoe.canUseExtendedMode );
			prop.comment = "Specify that extended function of hoes is available. [true/false]";
			Item_Hoe.canUseExtendedMode = prop.getBoolean( Item_Hoe.canUseExtendedMode );
			
			

		} catch ( Exception e ) {
			FMLLog.log(Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured");
		} finally {	cfg.save();	}


		

		for ( int i = 0; i < aitemID.length; i ++ )
			if ( aitemname[i][0] != null )
				aitemname[i][0] = ObjectHeader + aitemname[i][0];
		for ( int i = 0; i < ablockqty; i ++ )
			ablockname[i][0] = ObjectHeader + ablockname[i][0];

	}







	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {

		fuelh = new Handler_Fuel();
		eequip = new Event_MobEquipment();
		eequip.setMasterProbability( mobEquipmentProbability );
		
		int id = -1;
		int armorid = -1;
		



		if ( isEnabled( "Glass" ) ) {
			//Crystal Glass
			id ++;
			ablock[id] = new Block_Glass( ablockID[id], ablockname[id][0], Material.glass, false )
			.setLightOpacity( 0 ).setHardness( 0.3F ).setResistance( 1.5F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					" s ",
					"sqs",
					" s ",
					's', new ItemStack( Block.glass ),
					'q', new ItemStack( Item.netherQuartz ) );
			
			
			//Crystal Glass Pane
			id ++;
			ablock[id] = new Block_Pane( ablockID[id], ablockname[id - 1][0], ablockname[id][0], Material.glass, false )
			.setLightOpacity( 0 ).setHardness( 0.3F ).setResistance( 1.5F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"sss",
					"sss",
					's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );

			
			//Obsidian Glass
			id ++;
			ablock[id] = new Block_Glass( ablockID[id], ablockname[id][0], Material.rock, false )
			.setLightOpacity( 0 ).setHardness( 50.0F ).setResistance( 2000.0F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			MinecraftForge.setBlockHarvestLevel( ablock[id], "pickaxe", 3 );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					" s ",
					"sos",
					" s ",
					's', new ItemStack( Block.glass ),
					'o', new ItemStack( Block.obsidian ) );
			
			
			//Obsidian Glass Pane
			id ++;
			ablock[id] = new Block_Pane( ablockID[id], ablockname[id - 1][0], ablockname[id][0], Material.rock, false )
			.setLightOpacity( 0 ).setHardness( 50.0F ).setResistance( 2000.0F ).setStepSound( Block.soundGlassFootstep )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );
			
			MinecraftForge.setBlockHarvestLevel( ablock[id], "pickaxe", 3 );
			
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"sss",
					"sss",
					's', new ItemStack( getBlockID( "ObsidianGlass" ), 1, 0 ) );

		} else id += 4;
		
		
		if ( isEnabled( "Coal" ) ) {
			//Coal Block
			id ++;
			ablock[id] = new Block_Base( ablockID[id], Material.rock ).setHardness( 3.0F ).setResistance( 5.0F ).setStepSound( Block.soundStoneFootstep )//.setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );

			
			Block.setBurnProperties( ablockID[id], 30, 2 );
			fuelh.addFuel( ablockID[id], -1, 200 * 80 );

			/*
			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"ccc",
					"ccc",
					"ccc",
					'c', new ItemStack( Item.coal, 1, fm ) );
			GameRegistry.addRecipe( new ItemStack( Item.diamond, 1 ),
					"ccc",
					"ccc",
					"ccc",
					'c', new ItemStack( ablock[id], 1, 0 ) );
			*/
			GameRegistry.addRecipe( new ItemStack( Item.coal, 9 ),
					"c",
					'c', new ItemStack( ablock[id], 1, 0 ) );
			
			
			
		} else id += 1;
		
		
		if ( isEnabled( "Slime" ) ) {
			//Slime Block
			id++;
			ablock[id] = new Block_Spring( ablockID[id], Material.clay, 0.80F )
			.setTransparent( true ).setLightOpacity( 3 ).setHardness( 0.5F ).setResistance( 500.0F ).setStepSound( Block.soundClothFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );

			
			Block.setBurnProperties( ablockID[id], 5, 10 );
			fuelh.addFuel( ablockID[id], -1, 200 * 8 );

			GameRegistry.addRecipe( new ItemStack( ablock[id], 1 ),
					"ccc",
					"ccc",
					"ccc",
					'c', new ItemStack( Item.slimeBall, 1, 0 ) );
			GameRegistry.addRecipe( new ItemStack( Item.slimeBall, 9, 0 ),
					"c",
					'c', new ItemStack( ablock[id], 1, 0 ) );

		} else id += 1;

		
		if ( isEnabled( "Ghast" ) ) {
			//White Obsidian
			id ++;
			ablock[id] = new Block_Base( ablockID[id], Material.rock ).setHardness( 50.0F ).setResistance( 2000.0F ).setStepSound( Block.soundStoneFootstep ).setCreativeTab( CreativeTabs.tabBlock )
			.setUnlocalizedName( ablockname[id][0] ).func_111022_d( ablockname[id][0] );

			GameRegistry.registerBlock( ablock[id], ablockname[id][0] );
			LanguageRegistry.addName( ablock[id], ablockname[id][1] );
			LanguageRegistry.instance().addNameForObject( ablock[id], "ja_JP", ablockname[id][2] );

			
			MinecraftForge.setBlockHarvestLevel( ablock[id], "pickaxe", 3 );
			
			GameRegistry.addRecipe( new ItemStack( ablock[id], 2 ),
					"ccc",
					"ctc",
					"ccc",
					'c', new ItemStack( Block.obsidian, 1, 0 ),
					't', new ItemStack( Item.ghastTear, 1, 0 ) );
			
		} else id += 1;
		
		//point: add new blocks




		
		id = -1;
		
		
		if ( replaceVanillaTools )
			registerReplacedVanillaTools();
		
		
		if ( isEnabled( "Glass" ) ) 
			registerGlassTools( id, armorid );
		id += 16; armorid ++;
		
		
		if ( isEnabled( "Ancient" ) ) 
			registerAncientTools( id, armorid );
		id += 16; armorid ++;
	
		
		if ( isEnabled( "Coal" ) ) 
			registerCoalTools( id, armorid );
		id += 16; armorid ++; 

		
		if ( isEnabled( "Obsidian" ) ) 
			registerObsidianTools( id, armorid );
		id += 16; armorid ++; 

		
		if ( isEnabled ( "ExVanilla" ) )
			registerExVanillaTools( id, armorid );
		id += 16; armorid += 2;
		
		
		if ( isEnabled( "LapisLazuli" ) ) 
			registerLapislazuliTools( id, armorid );
		id += 16; armorid ++; 
		
		
		if ( isEnabled( "Redstone" ) ) 
			registerRedstoneTools( id, armorid );
		id += 16; armorid ++; 
		
		
		if ( isEnabled( "Rainbow" ) )
			registerRainbowTools( id, armorid );
		id += 16; armorid ++; 
		
		
		if ( isEnabled( "Quartz" ) )
			registerQuartzTools( id, armorid );
		id += 16; armorid ++; 
		
		
		if ( isEnabled( "Emerald" ) ) 
			registerEmeraldTools( id, armorid );
		id += 16; armorid ++; 
		
		
		if ( isEnabled( "Flint" ) ) 
			registerFlintTools( id, armorid );
		id += 16; armorid ++; 
			
		
		if ( isEnabled( "Slime" ) )
			registerSlimeTools( id, armorid );
		id += 16; armorid ++; 

		
		if ( isEnabled( "Star" ) ) 
			registerStarTools( id, armorid );
		 id += 16; armorid ++; 


		if ( isEnabled( "Bone" ) ) 
			registerBoneTools( id, armorid );
		id += 16; armorid ++;
		
		
		if ( isEnabled( "Ender" ) )
			registerEnderTools( id, armorid );
		id += 16; armorid ++;

		
		if ( isEnabled( "ExArcher" ) && isEnabled( "ExArcher2" ) )
			registerExArcher( id, armorid );
		id += 32;

		
		if ( isEnabled( "Blaze" ) )
			registerBlazeTools( id, armorid );
		id += 16; armorid ++; 
		

		if ( isEnabled( "Pumpkin" ) )
			registerPumpkinTools( id, armorid );
		id += 16; armorid ++;
		
		
		if ( isEnabled( "Lantern" ) ) 
			registerLanternTools( id, armorid );
		id += 16; armorid ++; 
	
		
		if ( isEnabled( "Cactus" ) ) 
			registerCactusTools( id, armorid );
		id += 16; armorid ++; 
		
		
		if ( isEnabled( "Glowstone" ) )
			registerGlowstoneTools( id, armorid );
		id += 16; armorid ++;
		
				
		if ( isEnabled( "Ghast" ) )
			registerGhastTools( id, armorid );
		id += 16; armorid ++;
		
		
		if ( isEnabled( "Poison" ) )
			registerPoisonTools( id, armorid );
		id += 16; armorid ++;
		
		
		if ( isEnabled( "Ice" ) )
			registerIceTools( id, armorid );
		id += 16; armorid ++;
		
		//point: add new item



		
		//point: register
		registerArrows();
		registerHammerRecipe();
		registerCreativeTabs();

		GameRegistry.registerFuelHandler( fuelh );
		MinecraftForge.EVENT_BUS.register( eequip );
		
	}
	
	
	
	
	
	//point: Replaced Vanilla Tools
	private void registerReplacedVanillaTools() {
			
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordWood ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.4 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordStone ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.3 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordStone ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.2 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordDiamond ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.025 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordDiamond ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.01 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordGold ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.swordGold ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( Item.bow ), Event_MobEquipment.FLAG_WITHERSKELETON, 0.01 );
		
		
		//Iron Shovel
		Item.itemsList[Item.shovelIron.itemID] = new Item_Spade( Item.shovelIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "shovelIron" ).func_111206_d( "iron_shovel" );

		//Iron Pickaxe
		Item.itemsList[Item.pickaxeIron.itemID] = new Item_Pickaxe( Item.pickaxeIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "pickaxeIron" ).func_111206_d( "iron_pickaxe" );

		//Iron Axe
		Item.itemsList[Item.axeIron.itemID] = new Item_Axe( Item.axeIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "hatchetIron" ).func_111206_d( "iron_axe" );

		//Iron Sword
		Item.itemsList[Item.swordIron.itemID] = new Item_Sword( Item.swordIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "swordIron" ).func_111206_d( "iron_sword" );

		//Wooden Sword
		Item.itemsList[Item.swordWood.itemID] = new Item_Sword( Item.swordWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "swordWood" ).func_111206_d( "wood_sword" );

		//Wooden Shovel
		Item.itemsList[Item.shovelWood.itemID] = new Item_Spade( Item.shovelWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "shovelWood" ).func_111206_d( "wood_shovel" );

		//Wooden Pickaxe
		Item.itemsList[Item.pickaxeWood.itemID] = new Item_Pickaxe( Item.pickaxeWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "pickaxeWood" ).func_111206_d( "wood_pickaxe" );

		//Wooden Axe
		Item.itemsList[Item.axeWood.itemID] = new Item_Axe( Item.axeWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "hatchetWood" ).func_111206_d( "wood_axe" );

		//Stone Sword
		Item.itemsList[Item.swordStone.itemID] = new Item_Sword( Item.swordStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "swordStone" ).func_111206_d( "stone_sword" );

		//Stone Shovel
		Item.itemsList[Item.shovelStone.itemID] = new Item_Spade( Item.shovelStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "shovelStone" ).func_111206_d( "stone_shovel" );

		//Stone Pickaxe
		Item.itemsList[Item.pickaxeStone.itemID] = new Item_Pickaxe( Item.pickaxeStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "pickaxeStone" ).func_111206_d( "stone_pickaxe" );

		//Stone Axe
		Item.itemsList[Item.axeStone.itemID] = new Item_Axe( Item.axeStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "hatchetStone" ).func_111206_d( "stone_axe" );

		//Diamond Sword
		Item.itemsList[Item.swordDiamond.itemID] = new Item_Sword( Item.swordDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "swordDiamond" ).func_111206_d( "diamond_sword" );

		//Diamond Shovel
		Item.itemsList[Item.shovelDiamond.itemID] = new Item_Spade( Item.shovelDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "shovelDiamond" ).func_111206_d( "diamond_shovel" );

		//Diamond Pickaxe
		Item.itemsList[Item.pickaxeDiamond.itemID] = new Item_Pickaxe( Item.pickaxeDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "pickaxeDiamond" ).func_111206_d( "diamond_pickaxe" );

		//Diamond Axe
		Item.itemsList[Item.axeDiamond.itemID] = new Item_Axe( Item.axeDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "hatchetDiamond" ).func_111206_d( "diamond_axe" );

		//Gold Sword
		Item.itemsList[Item.swordGold.itemID] = new Item_Sword( Item.swordGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "swordGold" ).func_111206_d( "gold_sword" );

		//Gold Shovel
		Item.itemsList[Item.shovelGold.itemID] = new Item_Spade( Item.shovelGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "shovelGold" ).func_111206_d( "gold_shovel" );

		//Gold Pickaxe
		Item.itemsList[Item.pickaxeGold.itemID] = new Item_Pickaxe( Item.pickaxeGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "pickaxeGold" ).func_111206_d( "gold_pickaxe" );

		//Gold Axe
		Item.itemsList[Item.axeGold.itemID] = new Item_Axe( Item.axeGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "hatchetGold" ).func_111206_d( "gold_axe" );

		//Wooden Hoe
		Item.itemsList[Item.hoeWood.itemID] = new Item_Hoe( Item.hoeWood.itemID - 256, EnumToolMaterial.WOOD ).setUnlocalizedName( "hoeWood" ).func_111206_d( "wood_hoe" );

		//Stone Hoe
		Item.itemsList[Item.hoeStone.itemID] = new Item_Hoe( Item.hoeStone.itemID - 256, EnumToolMaterial.STONE ).setUnlocalizedName( "hoeStone" ).func_111206_d( "stone_hoe" );

		//Iron Hoe
		Item.itemsList[Item.hoeIron.itemID] = new Item_Hoe( Item.hoeIron.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "hoeIron" ).func_111206_d( "iron_hoe" );

		//Diamond Hoe
		Item.itemsList[Item.hoeDiamond.itemID] = new Item_Hoe( Item.hoeDiamond.itemID - 256, EnumToolMaterial.EMERALD ).setUnlocalizedName( "hoeDiamond" ).func_111206_d( "diamond_hoe" );

		//Gold Hoe
		Item.itemsList[Item.hoeGold.itemID] = new Item_Hoe( Item.hoeGold.itemID - 256, EnumToolMaterial.GOLD ).setUnlocalizedName( "hoeGold" ).func_111206_d( "gold_hoe" );

		//Leather Cap
		//Item.itemsList[Item.helmetLeather.itemID] = new Item_Armor( Item.helmetLeather.itemID - 256, new String[]{ "textures/models/armor/leather_layer_1.png", "textures/models/armor/leather_layer_2.png" }, EnumArmorMaterial.CLOTH, 0, 0 ).setUnlocalizedName( "helmetCloth" ).func_111206_d( "leather_helmet" );
		
		//Shears
		Item.itemsList[Item.shears.itemID] = new Item_Shears( Item.shears.itemID - 256, EnumToolMaterial.IRON ).setUnlocalizedName( "shears" ).func_111206_d( "shears" );

	}
	
	
	//point: Glass Tools
	private void registerGlassTools( int id, int armorid ) {
		
		
		//Glass Stick
		id ++;

		aitem[id] = new Item_Base( aitemID[id], 64 ).setFull3D().setCreativeTab( CreativeTabs.tabMaterials )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"g",
				"g",
				'g', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );

		

		//Glass Edge
		id ++;

		aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" f ",
				"qgq",
				" o ",
				'f', new ItemStack( Item.flint ),
				'q', new ItemStack( Item.netherQuartz ),
				'o', new ItemStack( Block.obsidian ),
				'g', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
		
		
		
		EnumToolMaterial TMGlass = EnumHelper.addToolMaterial( "GLASS", 4, 1, 20.0F, 995.0F, 99 );
		TMGlass.customCraftingMaterial = Item.itemsList[ getItemIDforCraft( "GlassEdge" ) ];
		
		
		//Glass Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1, 0 );
			items.addEnchantment( Enchantment.looting, 3 );
			items.addEnchantment( Enchantment.unbreaking, 1 );
			
			eequip.addMobWeaponEquipment( items, Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.001 );
			eequip.addMobWeaponEquipment( items, Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.001 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
					"e",
					"e",
					"s",
					'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
		}
		

		//Glass Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1, 0 );
			items.addEnchantment( Enchantment.silkTouch, 1 );
			items.addEnchantment( Enchantment.unbreaking, 1 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
				"e",
				"s",
				"s",
				'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
		}
		

		//Glass Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1, 0 );
			items.addEnchantment( Enchantment.fortune, 3 );
			items.addEnchantment( Enchantment.unbreaking, 1 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
					"eee",
					" s ",
					" s ",
					'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
		}
		

		//Glass Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMGlass ).setItemDamageVsEntity( 2 ).setItemDamageVsBlock( 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1, 0 );
			items.addEnchantment( Enchantment.fortune, 3 );
			items.addEnchantment( Enchantment.unbreaking, 1 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
					"ee",
					"es",
					" s",
					'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
		}

		
		//Glass Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMGlass )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1, 0 );
			items.addEnchantment( Enchantment.unbreaking, 10 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
					"ee",
					"s ",
					"s ",
					'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
		}

		
		//Glass Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMGlass ).setRepairAmount( -1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1 );
			items.addEnchantment( Enchantment.unbreaking, 3 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
					" s ",
					"eee",
					" s ",
					'e', new ItemStack( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
					's', new ItemStack( getItemIDforCraft( "GlassStick" ), 1, 0 ) );
		}





		armorid ++;
		EnumArmorMaterial AMGlass = EnumHelper.addArmorMaterial( "GLASS", 1, new int[] { 6, 16, 12, 6 }, 99 );
		AMGlass.customCraftingMaterial = Item.itemsList[ getBlockID( "CrystalGlass" ) ];
		
		ItemStack helmet = new ItemStack( aitemID[id + 1] + 256, 1, 0 );
		ItemStack chestplate = new ItemStack( aitemID[id + 2] + 256, 1, 0 );
		ItemStack leggings = new ItemStack( aitemID[id + 3] + 256, 1, 0 );
		ItemStack boots = new ItemStack( aitemID[id + 4] + 256, 1, 0 );
		
		helmet.addEnchantment( Enchantment.unbreaking, 3 );
		chestplate.addEnchantment( Enchantment.unbreaking, 3 );
		leggings.addEnchantment( Enchantment.unbreaking, 3 );
		boots.addEnchantment( Enchantment.unbreaking, 3 );
		
		
		
		eequip.addMobArmorEquipment( helmet, chestplate, leggings, boots, Event_MobEquipment.FLAG_OVERWORLD, 0.001 );
		eequip.addMobArmorEquipment( helmet, chestplate, leggings, boots, Event_MobEquipment.FLAG_NETHER,    0.001 );
		
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, helmet, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, helmet, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, chestplate, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, chestplate, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, leggings, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, leggings, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, boots, 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, boots, 1, 1, 1 );
		
		
		//Glass Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME ).setMaxDamage( 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( helmet,
			"sss",
			"s s",
			's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
	
		

		//Glass Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME ).setMaxDamage( 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( chestplate,
				"s s",
				"sss",
				"sss",
				's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
	
			
		
		//Glass Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME ).setMaxDamage( 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( leggings,
			"sss",
			"s s",
			"s s",
			's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
	
		

		//Glass Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlass, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_INVINCIBLE, 0, Item_SpecialArmor.FLAG_ANYTIME ).setMaxDamage( 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( boots,
				"s s",
				"s s",
				's', new ItemStack( getBlockID( "CrystalGlass" ), 1, 0 ) );
	
		

		//Glass Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMGlass ).setParameters( 18.0F, 6.0F, 8.0F, 2.5F ).setItemDamageOnUse( 2 ).setCraftingMaterial( getItemIDforCraft( "GlassStick" ) )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		{
			ItemStack items = new ItemStack( aitem[id], 1, 0 );
			items.addEnchantment( Enchantment.unbreaking, 1 );
			items.addEnchantment( Enchantment.infinity, 1 );
			
			addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, items, 1, 1, 1 );
			addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, items, 1, 1, 1 );
			
			GameRegistry.addRecipe( items,
					" st",
					"s t",
					" st",
					's', new ItemStack ( getItemIDforCraft( "GlassStick" ), 1, 0 ),
					't', Item.silk );
		}


		//Glass Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 6.0, 8.0, 2.5, 0.00, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 16, 64, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 16, 64, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( getItemIDforCraft( "GlassEdge" ), 1, 0 ),
				's', new ItemStack ( getItemIDforCraft( "GlassStick" ), 1, 0 ),
				'f', Item.feather );
		
	}
	
	
	//point: Ancient Tools
	private void registerAncientTools( int id, int armorid ) {
		
		
		//Ancient Stick
		id ++;

		aitem[id] = new Item_Base( aitemID[id], 64 ).setFull3D().setCreativeTab( CreativeTabs.tabMaterials )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"g",
				"g",
				'g', new ItemStack( Block.stoneBrick, 1, fm ) );

		
		

		EnumToolMaterial TMAncient = EnumHelper.addToolMaterial( "ANCIENT", 1, 198, 4.2F, 1.2F, 15 );
		TMAncient.customCraftingMaterial = new ItemStack( Block.stoneBrick, 1, fm ).getItem();
		
		
		//Ancient Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMAncient ).addEffect( Potion.moveSlowdown.id, 0, 20 * 20, 0.05F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.25 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.stoneBrick, 1, fm ),
				's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );

		

		//Ancient Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMAncient )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.stoneBrick, 1, fm ),
				's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );

		

		//Ancient Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMAncient )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.stoneBrick, 1, fm ),
				's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );

		

		//Ancient Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMAncient )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.stoneBrick, 1, fm ),
				's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );


		
		//Ancient Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMAncient )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.stoneBrick, 1, fm ),
				's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );


		
		//Ancient Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMAncient ).setMaxDamage( (int)( 64 * 3 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.stoneBrick, 1, fm ),
				's', new ItemStack( getItemIDforCraft( "StoneStick" ), 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMAncient = EnumHelper.addArmorMaterial( "ANCIENT", 5, new int[] { 2, 5, 3, 1 }, 15 );
		AMAncient.customCraftingMaterial = new ItemStack( Block.stoneBrick, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.25 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.15 );
		
		
		//Ancient Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.stoneBrick, 1, fm ) );



		//Ancient Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.stoneBrick, 1, fm ) );


		
		//Ancient Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.stoneBrick, 1, fm ) );


		//Ancient Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMAncient, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.stoneBrick, 1, fm ) );



		//Ancient Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMAncient ).setParameters( 0.6F, 1.0F, 0.4F, 0.0F ).setCraftingMaterial( getItemIDforCraft( "StoneStick" ) )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( getItemIDforCraft( "StoneStick" ), 1, 0 ),
				't', Item.silk );



		//Ancient Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.7, 2.0, 0.0, 0.08, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.moveSlowdown.id, 2, 6 * 20, 0.2F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.stoneBrick, 1, fm ),
				's', new ItemStack ( getItemIDforCraft( "StoneStick" ), 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Coal Tools
	private void registerCoalTools( int id, int armorid ) {

		
		EnumToolMaterial TMCoal = EnumHelper.addToolMaterial( "COAL", 1, 472, 5.0F, 2.0F, 8 );
		TMCoal.customCraftingMaterial = new ItemStack( Block.field_111034_cE, 1, 0 ).getItem();
		
		
		//Coal Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMCoal ).addEffect( Item_SpecialSword.EFFECT_IGNITION, 0, 4, 0.1F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.025 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.field_111034_cE, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Coal Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMCoal )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.field_111034_cE, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Coal Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMCoal )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.field_111034_cE, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Coal Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMCoal )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.field_111034_cE, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Coal Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMCoal )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.field_111034_cE, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Coal Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMCoal ).setBurnTick( 200 * 80 ).setMaxDamage( (int)( 64 * 7.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.field_111034_cE, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMCoal = EnumHelper.addArmorMaterial( "COAL", 20, new int[] { 2, 6, 4, 2 }, 8 );
		AMCoal.customCraftingMaterial = new ItemStack( Block.field_111034_cE, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.05 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.025 );
		
		
		//Coal Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.field_111034_cE, 1, 0 ) );



		//Coal Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.field_111034_cE, 1, 0 ) );


		
		//Coal Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.field_111034_cE, 1, 0 ) );

		

		//Coal Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMCoal, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.field_111034_cE, 1, 0 ) );



		//Coal Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMCoal ).setParameters( 0.9F, 1.0F, 0.2F, 0.2F ).setCraftingMaterial( Block.field_111034_cE.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 80 );
			
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.field_111034_cE, 1, 0 ),
				't', Item.silk );



		//Coal Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.9, 0.4, 0.2, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 2 );
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.coal, 1, fm ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
		
		GameRegistry.addRecipe( new ItemStack( Block.field_111034_cE ), 
				"ccc", 
				"ccc",
				"ccc",
				'c', new ItemStack( Item.coal, 1, fm ) );
		
	}
	
	
	//point: Obsidian Tools
	private void registerObsidianTools( int id, int armorid ) {

		
		EnumToolMaterial TMObsidian = EnumHelper.addToolMaterial( "OBSIDIAN", 1, 2341, 5.5F, 1.8F, 1 );
		TMObsidian.customCraftingMaterial = new ItemStack( Block.obsidian, 1, fm ).getItem();
		
		
		//Obsidian Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMObsidian )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.10 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.obsidian, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Obsidian Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMObsidian )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.obsidian, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Obsidian Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMObsidian )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.obsidian, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Obsidian Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMObsidian )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.obsidian, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Obsidian Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMObsidian )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.obsidian, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Obsidian Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMObsidian ).setMaxDamage( (int)( 64 * 36 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.obsidian, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMObsidian = EnumHelper.addArmorMaterial( "OBSIDIAN", 50, new int[] { 2, 6, 5, 2 }, 1 );
		AMObsidian.customCraftingMaterial = new ItemStack( Block.obsidian, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.05 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.10 );
		
		
		//Obsidian Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_HEAVINESS, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.obsidian, 1, 0 ) );



		//Obsidian Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_HEAVINESS, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.obsidian, 1, 0 ) );


		
		//Obsidian Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_HEAVINESS, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.obsidian, 1, 0 ) );

		

		//Obsidian Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMObsidian, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTEXPLOSION, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_HEAVINESS, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.obsidian, 1, 0 ) );


		
		//Obsidian Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMObsidian )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Block.obsidian, 1, 0 ) );

		

		//Obsidian Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMObsidian ).setParameters( 0.5F, 1.1F, 0.5F, 0.0F ).setCraftingMaterial( Block.obsidian.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.obsidian, 1, 0 ),
				't', Item.silk );



		//Obsidian Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.7, 3.2, 0.0, 0.08, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.obsidian, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Ex Vanilla Tools
	private void registerExVanillaTools( int id, int armorid ) {
		
		
		//note: EnumToolMaterial.WOOD を使うと問答無用で竈の燃料になってしまう(すると無限燃焼の不具合を起こします)ので、これで対応します
		EnumToolMaterial TMWood = EnumHelper.addToolMaterial( "NONFLAMMABLE_WOOD", EnumToolMaterial.WOOD.getHarvestLevel(), EnumToolMaterial.WOOD.getMaxUses(),
				EnumToolMaterial.WOOD.getEfficiencyOnProperMaterial(), EnumToolMaterial.WOOD.getDamageVsEntity(), EnumToolMaterial.WOOD.getEnchantability() );

		//Wooden Hammer
		id ++;
		
		aitem[id] = new Item_Hammer( aitemID[id], TMWood ).setBurnTick( 200 ).setMaxDamage( (int)( 64 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.planks, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		
		//Stone Hammer
		id ++;
		
		aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.STONE ).setMaxDamage( (int)( 64 * 2 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.cobblestone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		
		//Iron Hammer
		id ++;
		
		aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.IRON ).setMaxDamage( (int)( 64 * 4 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.ingotIron, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		
		//Gold Hammer
		id ++;
		
		aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.GOLD ).setCraftingLevel( 2 ).setRepairAmount( 64 ).setMaxDamage( (int)( 64 * 0.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.ingotGold, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		
		//Diamond Hammer
		id ++;
		
		aitem[id] = new Item_Hammer( aitemID[id], EnumToolMaterial.EMERALD ).setMaxDamage( (int)( 64 * 24 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.diamond, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		
		
		armorid ++;
		EnumArmorMaterial AMWood = EnumHelper.addArmorMaterial( "WOOD", 3, new int[] { 1, 2, 2, 1 }, 15 );
		AMWood.customCraftingMaterial = new ItemStack( Block.planks, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.4 );
		
		
		//Wooden Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 0 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.planks, 1, fm ) );



		//Wooden Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 1 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.planks, 1, fm ) );


		
		//Wooden Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 2 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 );

		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.planks, 1, fm ) );

		

		//Wooden Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMWood, 3 ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, Item_SpecialArmor.AMP_RESIST_WEAKNESS | 500, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.planks, 1, fm ) );

		
		
		armorid ++;
		EnumArmorMaterial AMStone = EnumHelper.addArmorMaterial( "STONE", 4, new int[] { 2, 4, 3, 1 }, 5 );
		AMStone.customCraftingMaterial = new ItemStack( Block.cobblestone, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.3 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.2 );
		
		
		//Stone Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 0 ).setIsDisposable( true )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.cobblestone, 1, fm ) );



		//Stone Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 1 ).setIsDisposable( true )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.cobblestone, 1, fm ) );


		
		//Stone Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 2 ).setIsDisposable( true )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.cobblestone, 1, fm ) );

		

		//Stone Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMStone, 3 ).setIsDisposable( true )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.cobblestone, 1, fm ) );

		
		
		//Golden Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], EnumToolMaterial.GOLD )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Item.ingotGold, 1, 0 ) );
		
		
		//Diamond Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], EnumToolMaterial.EMERALD )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Item.diamond, 1, 0 ) );
		
	}
	
	
	//point: Lapis lazuli Tools
	private void registerLapislazuliTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMLapisLazuli = EnumHelper.addToolMaterial( "LAPISLAZURI", 0, 767, 7.5F, 2.5F, 25 );
		TMLapisLazuli.customCraftingMaterial = new ItemStack( Block.blockLapis, 1, fm ).getItem();
		
		
		//LapisLazuli Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMLapisLazuli )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.025 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.blockLapis, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//LapisLazuli Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMLapisLazuli )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.blockLapis, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//LapisLazuli Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMLapisLazuli )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.blockLapis, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//LapisLazuli Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMLapisLazuli )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.blockLapis, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//LapisLazuli Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMLapisLazuli )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.blockLapis, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//LapisLazuli Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMLapisLazuli ).setMaxDamage( (int)( 64 * 12 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.blockLapis, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMLapisLazuli = EnumHelper.addArmorMaterial( "LAPISLAZULI", 28, new int[] { 2, 7, 5, 2 }, 25 );
		AMLapisLazuli.customCraftingMaterial = new ItemStack( Block.blockLapis, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.05 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.025 );
		
		
		//LapisLazuli Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMLapisLazuli, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.blockLapis, 1, 0 ) );



		//LapisLazuli Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMLapisLazuli, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.blockLapis, 1, 0 ) );


		
		//LapisLazuli Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMLapisLazuli, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.blockLapis, 1, 0 ) );

		

		//LapisLazuli Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMLapisLazuli, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.blockLapis, 1, 0 ) );


		
		//LapisLazuli Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMLapisLazuli )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Block.blockLapis, 1, 0 ) );

		

		//LapisLazuli Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMLapisLazuli ).setParameters( 1.2F, 1.0F, 0.5F, 0.0F ).setCraftingMaterial( Block.blockLapis.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.blockLapis, 1, 0 ),
				't', Item.silk );



		//LapisLazuli Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 2.0, 0.0, 0.04, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.dyePowder, 1, 4 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
	
	}
	
	
	//point: Redstone Tools
	private void registerRedstoneTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMRedstone = EnumHelper.addToolMaterial( "REDSTONE", 3, 511, 7.0F, 3.0F, 12 );
		TMRedstone.customCraftingMaterial = new ItemStack( Block.blockRedstone, 1, fm ).getItem();
		
		
		//Redstone Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMRedstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.025 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.blockRedstone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Redstone Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMRedstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.blockRedstone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Redstone Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMRedstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.blockRedstone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Redstone Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMRedstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.blockRedstone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Redstone Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMRedstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.blockRedstone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Redstone Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMRedstone ).setMaxDamage( (int)( 64 * 8 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.blockRedstone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMRedstone = EnumHelper.addArmorMaterial( "REDSTONE", 25, new int[] { 3, 7, 5, 3 }, 12 );
		AMRedstone.customCraftingMaterial = new ItemStack( Block.blockRedstone, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.05 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.025 );
		
		
		//Redstone Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMRedstone, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.blockRedstone, 1, 0 ) );



		//Redstone Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMRedstone, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.blockRedstone, 1, 0 ) );


		
		//Redstone Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMRedstone, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.blockRedstone, 1, 0 ) );

		

		//Redstone Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMRedstone, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.blockRedstone, 1, 0 ) );


		
		//Redstone Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMRedstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Block.blockRedstone, 1, 0 ) );

		

		//Redstone Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMRedstone ).setParameters( 1.0F, 1.1F, 0.5F, 0.4F ).setCraftingMaterial( Block.blockRedstone.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.blockRedstone, 1, 0 ),
				't', Item.silk );



		//Redstone Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 2.0, 0.4, 0.07, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.redstone, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Rainbow Tools
	private void registerRainbowTools( int id, int armorid ) {
		
		
		//Rainbow Shard
		id ++;

		aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		GameRegistry.addShapelessRecipe( new ItemStack( aitem[id], 1 ),
			new ItemStack( Item.dyePowder, 1, 1 ),
			new ItemStack( Item.dyePowder, 1, 14 ),
			new ItemStack( Item.dyePowder, 1, 11 ),
			new ItemStack( Item.dyePowder, 1, 2 ),
			new ItemStack( Item.dyePowder, 1, 4 ),
			new ItemStack( Item.dyePowder, 1, 5 ),
			new ItemStack( Item.dyePowder, 1, 9 ) ) ;
		
		
		EnumToolMaterial TMRainbow = EnumHelper.addToolMaterial( "RAINBOW", 1, 343, 7.7F, 1.23F, 7 );
		TMRainbow.customCraftingMaterial = new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, fm ).getItem();
		
		
		//Rainbow Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMRainbow )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.07 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Rainbow Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMRainbow )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Rainbow Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMRainbow )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Rainbow Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMRainbow )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Rainbow Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMRainbow )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Rainbow Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMRainbow ).setMaxDamage( (int)( 64 * 5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMRainbow = EnumHelper.addArmorMaterial( "RAINBOW", 7, new int[] { 1, 3, 2, 1 }, 7 );
		AMRainbow.customCraftingMaterial = new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.07 );
		
		
		//Rainbow Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMRainbow, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_AUTOREPAIRING, 25 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_WET | Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ) );



		//Rainbow Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMRainbow, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_AUTOREPAIRING, 25 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_WET | Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ) );


		
		//Rainbow Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMRainbow, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_AUTOREPAIRING, 25 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_WET | Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ) );

		

		//Rainbow Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMRainbow, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_AUTOREPAIRING, 25 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ), Item_SpecialArmor.FLAG_WET | Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( getItemIDforCraft( "RainbowShard" ), 1, 0 ) );


		
		//Rainbow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMRainbow ).setParameters( 1.23F, 1.23F, 0.7F, 0.0F ).setCraftingMaterial( getItemIDforCraft( "RainbowShard" ) )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				't', Item.silk );



		//Rainbow Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.23, 1.23, 0.0, 0.07, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		//Sky Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 2.4, 0.0, -0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"f",
				"f",
				'h', new ItemStack ( getItemIDforCraft( "RainbowShard" ), 1, 0 ),
				'f', Item.feather );

	}
	
	
	//point: Quartz Tools
	private void registerQuartzTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMQuartz = EnumHelper.addToolMaterial( "QUARTZ", 2, 214, 6.4F, 1.4F, 18 );
		TMQuartz.customCraftingMaterial = new ItemStack( Block.blockNetherQuartz, 1, fm ).getItem();
		
		
		//Quartz Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMQuartz )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.10 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.blockNetherQuartz, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Quartz Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMQuartz )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.blockNetherQuartz, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Quartz Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMQuartz )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.blockNetherQuartz, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Quartz Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMQuartz )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.blockNetherQuartz, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Quartz Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMQuartz )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.blockNetherQuartz, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Quartz Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMQuartz ).setMaxDamage( (int)( 64 * 3.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.blockNetherQuartz, 1, fm ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMQuartz = EnumHelper.addArmorMaterial( "QUARTZ", 14, new int[] { 2, 6, 5, 2 }, 18 );
		AMQuartz.customCraftingMaterial = new ItemStack( Block.blockNetherQuartz, 1, fm ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.1 );
		
		
		//Quartz Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMQuartz, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.blockNetherQuartz, 1, fm ) );



		//Quartz Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMQuartz, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.blockNetherQuartz, 1, fm ) );


		
		//Quartz Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMQuartz, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.blockNetherQuartz, 1, fm ) );

		

		//Quartz Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMQuartz, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.blockNetherQuartz, 1, fm ) );


		
		//Quartz Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMQuartz )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Block.blockNetherQuartz, 1, fm ) );

		

		//Quartz Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMQuartz ).setParameters( 1.5F, 1.0F, -0.1F, 0.0F ).setCraftingMaterial( Block.blockNetherQuartz.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.blockNetherQuartz, 1, fm ),
				't', Item.silk );



		//Quartz Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.05, 1.8, 0.0, 0.01, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.netherQuartz, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Emerald Tools
	private void registerEmeraldTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMEmerald = EnumHelper.addToolMaterial( "REAL_EMERALD", 3, 1248, 10.0F, 3.4F, 12 );
		TMEmerald.customCraftingMaterial = new ItemStack( Item.emerald, 1, fm ).getItem();
		
		
		//Emerald Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMEmerald )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.025 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.010 );
		
		addChestContent( ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack( aitem[id], 1, 0 ), 1, 1, 2 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Item.emerald, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Emerald Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMEmerald )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Item.emerald, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Emerald Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMEmerald )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Item.emerald, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Emerald Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMEmerald )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Item.emerald, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Emerald Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMEmerald )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Item.emerald, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Emerald Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMEmerald ).setMaxDamage( (int)( 64 * 20 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.emerald, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMEmerald = EnumHelper.addArmorMaterial( "REAL_EMERALD", 30, new int[] { 3, 7, 6, 3 }, 12 );
		AMEmerald.customCraftingMaterial = new ItemStack( Item.emerald, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.025 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.01 );
		
		
		//Emerald Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMEmerald, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		addChestContent( ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack( aitem[id], 1, 0 ), 1, 1, 2 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Item.emerald, 1, 0 ) );

		

		//Emerald Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMEmerald, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack( aitem[id], 1, 0 ), 1, 1, 2 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Item.emerald, 1, 0 ) );

		
		
		//Emerald Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMEmerald, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack( aitem[id], 1, 0 ), 1, 1, 2 );
		
		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Item.emerald, 1, 0 ) );

		
		
		//Emerald Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMEmerald, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.VILLAGE_BLACKSMITH, new ItemStack( aitem[id], 1, 0 ), 1, 1, 2 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Item.emerald, 1, 0 ) );

		
		
		//Emerald Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMEmerald )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Item.emerald, 1, 0 ) );

		

		//Emerald Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMEmerald ).setParameters( 2.0F, 1.25F, 0.2F, 0.0F ).setCraftingMaterial( Item.emerald.itemID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.emerald, 1, 0 ),
				't', Item.silk );



		//Emerald Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.5, 2.0, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.emerald, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
	}
	
	
	//point:Flint Tools
	private void registerFlintTools( int id, int armorid ) {
		

		EnumToolMaterial TMFlint = EnumHelper.addToolMaterial( "FLINT", 2, 48, 10.8F, 2.7F, 24 );
		TMFlint.customCraftingMaterial = new ItemStack( Item.flint, 1, 0 ).getItem();
		
		
		//Flint Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMFlint ).setItemDamageVsBlock( 8 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.075 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.15 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Item.flint, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Flint Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMFlint ).setItemDamageVsEntity( 8 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Item.flint, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Flint Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMFlint ).setItemDamageVsEntity( 8 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Item.flint, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Flint Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMFlint ).setItemDamageVsEntity( 8 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Item.flint, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Flint Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMFlint )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Item.flint, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Flint Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMFlint ).setMaxDamage( (int)( 64 * 0.75 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.flint, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMFlint = EnumHelper.addArmorMaterial( "FLINT", 6, new int[] { 2, 5, 4, 1 }, 24 );
		AMFlint.customCraftingMaterial = new ItemStack( Item.flint, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.075 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.15 );
		
		
		//Flint Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMFlint, 0 )
		.addEffect( Potion.damageBoost.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Item.flint, 1, 0 ) );



		//Flint Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMFlint, 1 )
		.addEffect( Potion.damageBoost.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Item.flint, 1, 0 ) );


		
		//Flint Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMFlint, 2 )
		.addEffect( Potion.damageBoost.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Item.flint, 1, 0 ) );

		

		//Flint Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMFlint, 3 )
		.addEffect( Potion.damageBoost.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Item.flint, 1, 0 ) );


		//Flint Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMFlint )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Item.flint, 1, 0 ) );
		

		//Flint Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMFlint ).setParameters( 1.0F, 1.0F, 1.0F, 0.0F ).setCraftingMaterial( Item.flint.itemID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.flint, 1, 0 ),
				't', Item.silk );

	}

	
	//point: Slime Tools
	private void registerSlimeTools( int id, int armorid ) {
		
		EnumToolMaterial TMSlime = EnumHelper.addToolMaterial( "SLIME", 0, 96, 3.0F, 0.3F, 49 );
		TMSlime.customCraftingMaterial = new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ).getItem();
		
		
		//Slime Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMSlime )
		.addEffect( Potion.weakness.id, 0, 20 * 20, 0.1F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.075 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Slime Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMSlime )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Slime Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMSlime )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Slime Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMSlime )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Slime Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMSlime )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Slime Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMSlime ).setBurnTick( 200 * 8 ).setMaxDamage( (int)( 64 * 1.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMSlime = EnumHelper.addArmorMaterial( "SLIME", 5, new int[] { 2, 5, 3, 2 }, 49 );
		AMSlime.customCraftingMaterial = new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.075 );
		
		
		//Slime Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 0 )
		.setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );



		//Slime Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 1 )
		.setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );


		
		//Slime Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 2 )
		.setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );

		

		//Slime Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMSlime, 3 )
		.setIsDisposable( true ).addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.weakness.id, ( ( 10 * 20 ) << Item_SpecialArmor.AMP_DURATIONSHIFT ) + ( 0 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );

		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( getBlockID( "SlimeBlock" ), 1, 0 ) );



		//Slime Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMSlime ).setParameters( 2.0F, 0.8F, -0.2F, 0.0F ).setCraftingMaterial( getBlockID( "SlimeBlock" ) )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 8 );
			
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( getBlockID( "SlimeBlock" ), 1, 0 ),
				't', Item.silk );



		//Slime Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.9, 1.6, 0.0, 0.04, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.weakness.id, 0, 12 * 20, 0.1F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 2 );
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.slimeBall, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}
	

	//point: Star Tools
	private void registerStarTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMStar = EnumHelper.addToolMaterial( "STAR", 3, 6561, 16.0F, 6.0F, 24 );
		TMStar.customCraftingMaterial = new ItemStack( Item.netherStar, 1, fm ).getItem();
		
		
		//Star Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMStar )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.001 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.004 );
		
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Item.netherStar, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Star Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMStar )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Item.netherStar, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Star Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMStar )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Item.netherStar, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Star Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMStar )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Item.netherStar, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Star Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMStar )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Item.netherStar, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Star Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMStar ).setMaxDamage( (int)( 64 * 102.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.netherStar, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMStar = EnumHelper.addArmorMaterial( "STAR", 144, new int[] { 5, 11, 9, 5 }, 24 );
		AMStar.customCraftingMaterial = new ItemStack( Item.netherStar, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.001 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.004 );
		
		
		//Star Helmet
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMStar, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Item.netherStar, 1, 0 ) );



		//Star Chestplate
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMStar, 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Item.netherStar, 1, 0 ) );


		
		//Star Leggings
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMStar, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Item.netherStar, 1, 0 ) );

		

		//Star Boots
		id ++;

		aitem[id] = new Item_Armor( aitemID[id], ArmorTexture[armorid], AMStar, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Item.netherStar, 1, 0 ) );


		
		//Star Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMStar )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Item.netherStar, 1, 0 ) );

		

		//Star Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMStar ).setParameters( 6.0F, 1.8F, 1.0F, 0.5F ).setCraftingMaterial( Item.netherStar.itemID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 1, 1, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 1, 1, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.netherStar, 1, 0 ),
				't', Item.silk );



		//Shooting Star
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 2.0, 3.2, 0.5, 0.02, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		addChestContent( ChestGenHooks.STRONGHOLD_CORRIDOR, new ItemStack( aitem[id] ), 16, 64, 1 );
		addChestContent( ChestGenHooks.STRONGHOLD_CROSSING, new ItemStack( aitem[id] ), 16, 64, 1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 64 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.netherStar, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Bone Tools
	private void registerBoneTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMBone = EnumHelper.addToolMaterial( "BONE", 1, 144, 6.0F, 1.0F, 17 );
		TMBone.customCraftingMaterial = new ItemStack( Item.skull, 1, 0 ).getItem();
		
		
		//Bone Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMBone )
		.addEffect( Item_SpecialSword.EFFECT_USURPATION, 0, 0, 0.1F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_BONE,   0.15 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_ROTTEN, 0.05 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Item.skull, 1, 0 ),
				's', new ItemStack( Item.bone, 1, 0 ) );

		

		//Bone Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMBone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Item.skull, 1, 0 ),
				's', new ItemStack( Item.bone, 1, 0 ) );

		

		//Bone Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMBone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Item.skull, 1, 0 ),
				's', new ItemStack( Item.bone, 1, 0 ) );

		

		//Bone Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMBone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Item.skull, 1, 0 ),
				's', new ItemStack( Item.bone, 1, 0 ) );


		
		//Bone Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMBone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Item.skull, 1, 0 ),
				's', new ItemStack( Item.bone, 1, 0 ) );


		
		//Bone Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMBone ).setMaxDamage( (int)( 64 * 2.25 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.skull, 1, 0 ),
				's', new ItemStack( Item.bone, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMBone = EnumHelper.addArmorMaterial( "BONE", 8, new int[] { 2, 5, 4, 2 }, 17 );
		AMBone.customCraftingMaterial = new ItemStack( Item.skull, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_BONE,   0.15 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_ROTTEN, 0.05 );
		
		
		//Bone Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBone, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTPROJECTILE, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Item.skull, 1, 0 ) );



		//Bone Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBone, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTPROJECTILE, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Item.skull, 1, 0 ) );


		
		//Bone Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBone, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTPROJECTILE, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Item.skull, 1, 0 ) );

		

		//Bone Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBone, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTPROJECTILE, 100, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Item.skull, 1, 0 ) );



		//Bone Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMBone ).setParameters( 2.0F, 1.05F, -0.2F, 0.0F ).setCraftingMaterial( Item.bone.itemID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.bone, 1, 0 ),
				't', Item.silk );



		//Bone Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.75, 0.0, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.skull, 1, 0 ),
				's', new ItemStack ( Item.bone, 1, 0 ),
				'f', Item.feather );

		
		//@Deprecated
		//Wither Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 2.0, 0.2, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.wither.id, 2, 20 * 6, 0.8F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.skull, 1, 1 ),
				's', new ItemStack ( Item.bone, 1, 0 ),
				'f', Item.feather );
		
		
		GameRegistry.addRecipe( new ItemStack( Item.skull, 1, 0 ),
				"sss",
				"scs",
				"sss",
				's', new ItemStack( Item.dyePowder, 1, 15 ),
				'c', Item.bone );
		GameRegistry.addRecipe( new ItemStack( Item.dyePowder, 11, 15 ),
				"s",
				's', new ItemStack( Item.skull.itemID, 1, 0 ) );

		GameRegistry.addRecipe( new ItemStack( Item.skull, 1, 1 ),
				"sss",
				"sos",
				"sss",
				's', new ItemStack ( Item.coal, 1, 0 ),
				'o', new ItemStack ( Item.skull, 1, 0 ) );
		GameRegistry.addRecipe(new ItemStack( Item.coal, 8, 0 ),
				"s",
				's', new ItemStack( Item.skull.itemID, 1, 1 ) );

		GameRegistry.addRecipe( new ItemStack( Item.skull, 1, 2 ),
				"sss",
				"sss",
				"sss",
				's', Item.rottenFlesh );
		GameRegistry.addRecipe( new ItemStack( Item.rottenFlesh, 9, 0 ),
				"s",
				's', new ItemStack( Item.skull.itemID, 1, 2 ) );

		/* //廃止されました
		GameRegistry.addRecipe(new ItemStack(Item.skull, 1, 3),
				"sss",
				"sss",
				"sss",
				's', Item.appleRed );
		GameRegistry.addRecipe(new ItemStack(Item.appleRed, 9, 0),
				"s",
				's', new ItemStack(Item.skull.itemID, 1, 3) );
		 */

		GameRegistry.addRecipe( new ItemStack( Item.skull, 1, 4 ),
				"sss",
				"sss",
				"sss",
				's', Item.gunpowder );
		GameRegistry.addRecipe( new ItemStack( Item.gunpowder, 9, 0 ),
				"s",
				's', new ItemStack( Item.skull.itemID, 1, 4 ) );
	}
	
	
	
	//point: Ender Tools
	private void registerEnderTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMEnder = EnumHelper.addToolMaterial( "ENDER", 3, 1331, 11.0F, 4.0F, 32 );
		TMEnder.customCraftingMaterial = new ItemStack( Item.eyeOfEnder, 1, fm ).getItem();
		
		
		//Ender Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMEnder )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.015 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Ender Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMEnder )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Ender Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMEnder )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Ender Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMEnder )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Ender Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMEnder )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Ender Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMEnder ).setMaxDamage( (int)( 64 * 20 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMEnder = EnumHelper.addArmorMaterial( "ENDER", 30, new int[] { 3, 7, 6, 3 }, 12 );
		AMEnder.customCraftingMaterial = new ItemStack( Item.eyeOfEnder, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.015 );
		
		
		//Ender Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMEnder, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Item.eyeOfEnder, 1, 0 ) );



		//Ender Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMEnder, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Item.eyeOfEnder, 1, 0 ) );


		
		//Ender Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMEnder, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Item.eyeOfEnder, 1, 0 ) );

		

		//Ender Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMEnder, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFALLING, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Item.eyeOfEnder, 1, 0 ) );


		
		//Ender Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMEnder )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Item.eyeOfEnder, 1, 0 ) );

		

		//Ender Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMEnder ).setParameters( 3.0F, 1.2F, 1.0F, -0.2F ).setCraftingMaterial( Item.eyeOfEnder.itemID )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F ) 
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.eyeOfEnder, 1, 0 ),
				't', Item.silk );



		//Ender Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.25, 2.25, -0.2, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 16 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.eyeOfEnder, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

	}
	
	
	//point: ExArcher
	private void registerExArcher( int id, int armorid ) {
		
		//Stone Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], EnumToolMaterial.STONE ).setParameters( 0.5F, 0.9F, 0.2F, 0.0F ).setCraftingMaterial( Block.cobblestone.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.cobblestone, 1, 0 ),
				't', Item.silk );


		
		//Iron Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], EnumToolMaterial.IRON ).setParameters( 0.8F, 1.1F, 0.4F, 0.0F ).setCraftingMaterial( Item.ingotIron.itemID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.ingotIron, 1, 0 ),
				't', Item.silk );

		
		
		//Diamond Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], EnumToolMaterial.EMERALD ).setParameters( 1.5F, 1.2F, 0.6F, 0.0F ).setCraftingMaterial( Item.diamond.itemID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.diamond, 1, 0 ),
				't', Item.silk );

		
		
		//Golden Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], EnumToolMaterial.GOLD ).setParameters( 3.0F, 0.9F, 0.0F, 0.5F ).setCraftingMaterial( Item.ingotGold.itemID )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.ingotGold, 1, 0 ),
				't', Item.silk );

		
		
		//Wooden Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.9, 0.4, 0.0, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		fuelh.addFuel( aitemID[id] + 256, -1, 200 );
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.planks, 1, fm ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 2 ),
				"h",
				"s",
				"s",
				'h', new ItemStack ( Block.wood, 1, fm ),
				's', new ItemStack ( Item.stick, 1, 0 ) );

		
		
		//Stone Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.6, 2.4, 0.0, 0.10, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.cobblestone, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 2 ),
				"h",
				"s",
				"s",
				'h', new ItemStack ( Block.stone, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ) );

		
		
		//Iron Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 2.0, 0.0, 0.06, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.ingotIron, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Diamond Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.5, 2.8, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 16 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.diamond, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Golden Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.9, 1.0, 0.5, 0.04, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.ingotGold, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		//todo: Deprecated
		//Venom Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.8, 0.4, 0.0, 0.01, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.poison.id, 0, 5 * 20, 0.8F )
		.addEffect( Potion.weakness.id, 0, 30 * 20, 0.5F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		//from Splash Potion of Poison I
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16388 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		//from Splash Potion of Poison II
		GameRegistry.addRecipe( new ItemStack( aitem[id], 16 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16420 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		//from Splash Potion of Poison I extended
		GameRegistry.addRecipe( new ItemStack( aitem[id], 16 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16452 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.spiderEye ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		GameRegistry.addRecipe(new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.poisonousPotato ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Paralysis Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 0.8, 0.4, -0.1, 0.01, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.weakness.id, 0, 10 * 20, 0.8F )
		.addEffect( Potion.moveSlowdown.id, 6, 10 * 20, 0.8F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.fermentedSpiderEye, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		//todo: Deprecated
		//Healing Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.6, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.heal.id, 1, 1, 1.0F )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		//from Splash Potion of Healing I
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16389 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		//from Splash Potion of Healing II
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16421 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		//from Splash Potion of Healing I extended...?
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16453 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.speckledMelon ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Cursed Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.0, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.harm.id, 1, 1, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		//Splash Potion of Harming I
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16396 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		//Splash Potion of Harming II
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16428 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		//Splash Potion of Harming I extended...?
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.potion, 1, 16460 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.rottenFlesh ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Gale Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.2, 3.0, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"f",
				"s",
				"f",
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Attraction Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.2, -2.0, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.web, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Lightning Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.0, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_LIGHTNING, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.fenceIron, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Bomb Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 0.0, 0.0, 0.07, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_EXPLOSIONWITHFIRE, 3, 0, 0.9F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.fireballCharge, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Creeper Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 0.0, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_EXPLOSION, 4, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.gunpowder, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 16 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.skull, 1, 4 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Incendiary Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 0.0, 0.0, 0.07, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_EXPLOSIONFIREONLY, 2, 0, 1.0F )
		.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.blazePowder, 1, fm ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Magical Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 2.0, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_INCONSUMABLE, 0, 0, 1.0F )
		.addEffect( Struct_Arrow.EFFECT_ENDERSTRIKE, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.expBottle, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		
		//Torch Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 0.0, 0.0, 0.01, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_PLACEBLOCK, Block.torchWood.blockID, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 2 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.torchWood, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
		//Arrow of Assassination
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 2.0, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_ASSASSINATION, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.torchRedstoneActive, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
	}
	

	//point: Blaze Tools
	private void registerBlazeTools( int id, int armorid ) {
		
		
		//Blaze Quartz
		id ++;

		aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 24 );
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" p ",
				"pqp",
				" p ",
				'p', new ItemStack( Item.blazePowder, 1, 0 ),
				'q', new ItemStack( Item.netherQuartz, 1, 0 ) );

		
		
		EnumToolMaterial TMBlaze = EnumHelper.addToolMaterial( "BLAZE", 3, 428, 9.0F, 3.7F, 20 );
		TMBlaze.customCraftingMaterial = new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, fm ).getItem();
		
		
		//Blaze Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMBlaze )
		.addEffect( Item_SpecialSword.EFFECT_IGNITION, 0, 7, 0.5F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		
		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );

		

		//Blaze Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMBlaze )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );
		
		

		//Blaze Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMBlaze )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );

		

		//Blaze Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMBlaze )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );


		
		//Blaze Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMBlaze )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );


		
		//Blaze Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMBlaze ).setBurnTick( 200 * 64 ).setMaxDamage( (int)( 64 * 6.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMBlaze = EnumHelper.addArmorMaterial( "BLAZE", 18, new int[] { 3, 6, 5, 2 }, 20 );
		AMBlaze.customCraftingMaterial = new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.05 );
		
		
		//Blaze Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBlaze, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ) );

		

		//Blaze Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBlaze, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ) );


		
		//Blaze Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBlaze, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ) );

		

		//Blaze Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMBlaze, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 250, Item_SpecialArmor.FLAG_ANYTIME ).addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 64 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ) );


		
		//Blaze Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMBlaze )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 48 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ) );

		

		//Blaze Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMBlaze ).setParameters( 1.5F, 1.2F, 0.6F, 0.0F ).setCraftingMaterial( Item.blazeRod.itemID )
		.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F ) 
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, 200 * 36 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Item.blazeRod, 1, 0 ),
				't', Item.silk );



		//Blaze Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 2.4, 0.0, 0.03, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		fuelh.addFuel( aitemID[id] + 256, -1, (int)( 200 * 4.5 ) );//checkme
		
		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 8 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( getItemIDforCraft( "BlazeQuartz" ), 1, 0 ),
				's', new ItemStack ( Item.blazeRod, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Pumpkin Tools
	private void registerPumpkinTools( int id, int armorid ) {
		
		
		EnumToolMaterial TMPumpkin = EnumHelper.addToolMaterial( "PUMPKIN", 2, 99, 1.8F, 0.0F, 2 );
		TMPumpkin.customCraftingMaterial = new ItemStack( Block.pumpkin, 1, 0 ).getItem();
		
		
		//Pumpkin Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMPumpkin )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.075 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.pumpkin, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Pumpkin Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMPumpkin )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.pumpkin, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Pumpkin Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMPumpkin )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.pumpkin, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Pumpkin Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMPumpkin )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.pumpkin, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Pumpkin Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMPumpkin )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.pumpkin, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Pumpkin Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMPumpkin ).setMaxDamage( (int)( 64 * 1.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.pumpkin, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMPumpkin = EnumHelper.addArmorMaterial( "PUMPKIN", 3, new int[] { 1, 3, 3, 1 }, 2 );
		AMPumpkin.customCraftingMaterial = new ItemStack( Block.pumpkin, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.075 );
		
		
		//Pumpkin Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPumpkin, 0 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.pumpkin, 1, 0 ) );



		//Pumpkin Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPumpkin, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_SECRETEATING, 1 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( 0 << Item_SpecialArmor.AMP_DURATIONSHIFT ), Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.pumpkin, 1, 0 ) );


		
		//Pumpkin Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPumpkin, 2 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.pumpkin, 1, 0 ) );

		

		//Pumpkin Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPumpkin, 3 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.pumpkin, 1, 0 ) );



		//Pumpkin Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMPumpkin ).setParameters( 1.0F, 1.0F, -0.2F, 0.0F ).setCraftingMaterial( Block.pumpkin.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.pumpkin, 1, 0 ),
				't', Item.silk );



		//Pumpkin Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 1.8, 0.0, 0.04, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.field_76443_y.id, 1, 1, 0.8F ) //Saturation
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.pumpkin, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}

	
	//point: Lantern Tools
	private void registerLanternTools( int id, int armorid ) {
		
		EnumToolMaterial TMLantern = EnumHelper.addToolMaterial( "LANTERN", 3, 74, 2.7F, 0.4F, 4 );
		TMLantern.customCraftingMaterial = new ItemStack( Block.pumpkinLantern, 1, 0 ).getItem();
		
		
		//Lantern Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMLantern )
		.addEffect( Item_SpecialSword.EFFECT_LIFEDRAIN, 50, 0, 0.2F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Lantern Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMLantern )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Lantern Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMLantern )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Lantern Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMLantern )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Lantern Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMLantern )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Lantern Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMLantern ).setMaxDamage( (int)( 64 * 1 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMLantern = EnumHelper.addArmorMaterial( "LANTERN", 2, new int[] { 2, 3, 3, 1 }, 4 );
		AMLantern.customCraftingMaterial = new ItemStack( Block.pumpkinLantern, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.05 );
		
		
		//Lantern Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMLantern, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 1, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.pumpkinLantern, 1, 0 ) );



		//Lantern Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMLantern, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 1, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.pumpkinLantern, 1, 0 ) );


		
		//Lantern Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMLantern, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 1, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.pumpkinLantern, 1, 0 ) );

		

		//Lantern Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMLantern, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 1, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.pumpkinLantern, 1, 0 ) );



		//Lantern Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMLantern ).setParameters( 1.2F, 1.0F, -0.2F, 0.0F ).setCraftingMaterial( Block.pumpkinLantern.blockID )
		.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.pumpkinLantern, 1, 0 ),
				't', Item.silk );



		//Lantern Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 1.8, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Struct_Arrow.EFFECT_FLAME, 0, 0, 1.0F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.pumpkinLantern, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	
	//point: Cactus Tools
	private void registerCactusTools( int id, int armorid ) {
		
		EnumToolMaterial TMCactus = EnumHelper.addToolMaterial( "CACTUS", 2, 86, 8.0F, 1.8F, 3 );
		TMCactus.customCraftingMaterial = new ItemStack( Block.cactus, 1, fm ).getItem();
		
		
		//Cactus Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMCactus )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.075 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.cactus, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Cactus Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMCactus )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.cactus, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Cactus Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMCactus )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.cactus, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Cactus Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMCactus )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.cactus, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Cactus Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMCactus )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.cactus, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Cactus Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMCactus ).setMaxDamage( (int)( 64 * 1.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.cactus, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMCactus = EnumHelper.addArmorMaterial( "CACTUS", 5, new int[] { 2, 4, 3, 2 }, 3 );
		AMCactus.customCraftingMaterial = new ItemStack( Block.cactus, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.075 );
		
		
		//Cactus Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMCactus, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERREFLECT, 250, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTCACTUS, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.cactus, 1, 0 ) );



		//Cactus Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMCactus, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERREFLECT, 250, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTCACTUS, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.cactus, 1, 0 ) );


		
		//Cactus Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMCactus, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERREFLECT, 250, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTCACTUS, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.cactus, 1, 0 ) );

		

		//Cactus Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMCactus, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERREFLECT, 250, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTCACTUS, 0 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.cactus, 1, 0 ) );
		
		

		//Cactus Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMCactus ).setParameters( 1.0F, 1.0F, 1.0F, 0.0F ).setCraftingMaterial( Block.cactus.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.cactus, 1, 0 ),
				't', Item.silk );



		//Cactus Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.0, 2.5, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.cactus, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
		
	}	
	
	
	//point: Glowstone Tools
	private void registerGlowstoneTools( int id, int armorid ) {
		
		EnumToolMaterial TMGlowstone = EnumHelper.addToolMaterial( "GLOWSTONE", 3, 181, 7.2F, 2.4F, 16 );
		TMGlowstone.customCraftingMaterial = new ItemStack( Block.glowStone, 1, fm ).getItem();
		
		
		//Glowstone Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMGlowstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.1 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.glowStone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Glowstone Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMGlowstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.glowStone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Glowstone Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMGlowstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.glowStone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Glowstone Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMGlowstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.glowStone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Glowstone Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMGlowstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.glowStone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Glowstone Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMGlowstone ).setMaxDamage( (int)( 64 * 3 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.glowStone, 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );




		armorid ++;
		EnumArmorMaterial AMGlowstone = EnumHelper.addArmorMaterial( "GLOWSTONE", 10, new int[] { 3, 7, 5, 2 }, 16 );
		AMGlowstone.customCraftingMaterial = new ItemStack( Block.glowStone, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.1 );
		
		
		//Glowstone Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlowstone, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.glowStone, 1, 0 ) );



		//Glowstone Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlowstone, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.glowStone, 1, 0 ) );


		
		//Glowstone Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlowstone, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.glowStone, 1, 0 ) );

		

		//Glowstone Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGlowstone, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_LUMINESCENCE, 0, Item_SpecialArmor.FLAG_ANYTIME )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.glowStone, 1, 0 ) );


		
		//Glowstone Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMGlowstone )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Block.glowStone, 1, 0 ) );

		

		//Glowstone Bow
		id ++;
		
		aitem[id] = new Item_Bow( aitemID[id], TMGlowstone ).setParameters( 6.0F, 1.2F, 0.0F, 0.0F ).setCraftingMaterial( Block.glowStone.blockID )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( Block.glowStone, 1, 0 ),
				't', Item.silk );



		//Glowstone Arrow
		id ++;
		
		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.2, 2.4, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.glowstone, 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );
		
	}


	//point: Ghast Tools
	private void registerGhastTools( int id, int armorid ) {
		
		EnumToolMaterial TMGhast = EnumHelper.addToolMaterial( "Ghast", 3, 2401, 14.0F, 4.0F, 25 );
		TMGhast.customCraftingMaterial = new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ).getItem();
		
		
		//Ghast Sword
		id ++;

		aitem[id] = new Item_Sword( aitemID[id], TMGhast )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.015 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );

		

		//Ghast Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMGhast )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );
		
		

		//Ghast Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMGhast )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );

		

		//Ghast Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMGhast )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );


		
		//Ghast Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMGhast )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );


		
		//Ghast Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMGhast ).setMaxDamage( (int)( 64 * 37.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ),
				's', new ItemStack( Item.blazeRod, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMGhast = EnumHelper.addArmorMaterial( "Ghast", 62, new int[] { 4, 9, 7, 4 }, 25 );
		AMGhast.customCraftingMaterial = new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.015 );
		
		
		//Ghast Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGhast, 0 )
		.addEffect( Potion.regeneration.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ) );



		//Ghast Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGhast, 1 )
		.addEffect( Potion.regeneration.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ) );


		
		//Ghast Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGhast, 2 )
		.addEffect( Potion.regeneration.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ) );

		

		//Ghast Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMGhast, 3 )
		.addEffect( Potion.regeneration.id, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ) );

		
		
		//Ghast Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMGhast )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( getBlockID( "WhiteObsidian" ), 1, 0 ) );

				

		//Ghast Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMGhast ).setParameters( 3.0F, 1.25F, 1.0F, 0.0F ).setCraftingMaterial( getBlockID( "WhiteObsidian" ) )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( getBlockID( "WhiteObsidian" ), 1, 0 ),
				't', Item.silk );



		//Ghast Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 2.0, 3.2, 0.0, 0.05, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 16 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Item.ghastTear, 1, 0 ),
				's', new ItemStack ( Item.blazeRod, 1, 0 ),
				'f', Item.feather );

		
	}
	
	
	//point: Poison Tools
	private void registerPoisonTools( int id, int armorid ) {
		
		
		//Poison steel
		id ++;

		aitem[id] = new Item_Base( aitemID[id], 64 ).setCreativeTab( CreativeTabs.tabMaterials )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		GameRegistry.addShapelessRecipe( new ItemStack( aitem[id] ), 
				new ItemStack( Item.ingotIron ), 
				new ItemStack( Item.spiderEye ),
				new ItemStack( Item.spiderEye ),
				new ItemStack( Item.spiderEye ) );
		
				
				
		EnumToolMaterial TMPoison = EnumHelper.addToolMaterial( "POISON", 2, 225, 7.0F, 2.0F, 12 );
		TMPoison.customCraftingMaterial = new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ).getItem();
		
		
		//Poison Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMPoison )
		.addEffect( Potion.poison.id, 1, 60, 0.2F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_NETHER    & Event_MobEquipment.FLAG_SWORDMAN, 0.05 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Poison Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMPoison )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );
		
		

		//Poison Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMPoison )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );

		

		//Poison Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMPoison )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Poison Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMPoison )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );


		
		//Poison Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMPoison ).setMaxDamage( (int)( 64 * 3.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack( Item.stick, 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMPoison = EnumHelper.addArmorMaterial( "POISON", 14, new int[] { 2, 6, 5, 2 }, 12 );
		AMPoison.customCraftingMaterial = new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD, 0.05 );
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_NETHER,    0.05 );

		
		//Poison Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPoison, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.poison.id, 100 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( 60 << Item_SpecialArmor.AMP_DURATIONSHIFT ), Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.poison.id, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ) );



		//Poison Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPoison, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.poison.id, 100 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( 60 << Item_SpecialArmor.AMP_DURATIONSHIFT ), Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.poison.id, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ) );


		
		//Poison Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPoison, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.poison.id, 100 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( 60 << Item_SpecialArmor.AMP_DURATIONSHIFT ), Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.poison.id, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ) );

		

		//Poison Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMPoison, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_COUNTERSTATUS + Potion.poison.id, 100 | ( 1 << Item_SpecialArmor.AMP_AMPLIFIERSHIFT ) | ( 60 << Item_SpecialArmor.AMP_DURATIONSHIFT ), Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTSTATUS + Potion.poison.id, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ) );
				

		//Poison Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMPoison ).setParameters( 0.8F, 1.1F, 0.2F, 0.0F ).setCraftingMaterial( getItemIDforCraft( "PoisonSteel" ) )
		.addEffect( Potion.poison.id, 1, 120, 0.2F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack ( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				't', Item.silk );



		//Poison Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 2.0, 0.0, 0.06, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.poison.id, 1, 120, 0.8F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( getItemIDforCraft( "PoisonSteel" ), 1, 0 ),
				's', new ItemStack ( Item.stick, 1, 0 ),
				'f', Item.feather );

		
	}

	
	//point: Ice Tools
	private void registerIceTools( int id, int armorid ) {
		
		
		//Ice Stick
		id ++;

		aitem[id] = new Item_Base( aitemID[id] ).setFull3D()
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"e",
				"e",
				'e', new ItemStack( Block.ice, 1, 0 ) );

				
		EnumToolMaterial TMIce = EnumHelper.addToolMaterial( "ICE", 2, 127, 6.4F, 2.4F, 8 );
		TMIce.customCraftingMaterial = new ItemStack( Block.ice, 1, 0 ).getItem();
		
		
		//Ice Sword
		id ++;

		aitem[id] = new Item_SpecialSword( aitemID[id], TMIce )
		.addEffect( Potion.moveSlowdown.id, 6, 20 * 5, 0.1F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD    & Event_MobEquipment.FLAG_SWORDMAN, 0.025 );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"e",
				"s",
				'e', new ItemStack( Block.ice, 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ) );

		

		//Ice Shovel
		id ++;

		aitem[id] = new Item_Spade( aitemID[id], TMIce )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"e",
				"s",
				"s",
				'e', new ItemStack( Block.ice, 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ) );
		
		

		//Ice Pickaxe
		id ++;

		aitem[id] = new Item_Pickaxe( aitemID[id], TMIce )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"eee",
				" s ",
				" s ",
				'e', new ItemStack( Block.ice, 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ) );

		

		//Ice Axe
		id ++;

		aitem[id] = new Item_Axe( aitemID[id], TMIce )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"es",
				" s",
				'e', new ItemStack( Block.ice, 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ) );


		
		//Ice Hoe
		id ++;

		aitem[id] = new Item_Hoe( aitemID[id], TMIce )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"ee",
				"s ",
				"s ",
				'e', new ItemStack( Block.ice, 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ) );


		
		//Ice Hammer
		id ++;

		aitem[id] = new Item_Hammer( aitemID[id], TMIce ).setMaxDamage( (int)( 64 * 37.5 ) - 1 )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s ",
				"eee",
				" s ",
				'e', new ItemStack( Block.ice, 1, 0 ),
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ) );






		armorid ++;
		EnumArmorMaterial AMIce = EnumHelper.addArmorMaterial( "ICE", 8, new int[] { 2, 5, 4, 2 }, 8 );
		AMIce.customCraftingMaterial = new ItemStack( Block.ice, 1, 0 ).getItem();
		eequip.addMobArmorEquipment( new ItemStack( aitemID[id + 1] + 256, 1, 0 ), new ItemStack( aitemID[id + 2] + 256, 1, 0 ), new ItemStack( aitemID[id + 3] + 256, 1, 0 ), new ItemStack( aitemID[id + 4] + 256, 1, 0 ), 
				Event_MobEquipment.FLAG_OVERWORLD,    0.025 );
		
		
		//Ice Helmet
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMIce, 0 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 100 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_EXTINGUISHMENT, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.addEffect( Item_SpecialArmor.EFFECT_FREEZE, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				's', new ItemStack( Block.ice, 1, 0 ) );



		//Ice Chestplate
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMIce, 1 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 100 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_EXTINGUISHMENT, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.addEffect( Item_SpecialArmor.EFFECT_FREEZE, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"sss",
				"sss",
				's', new ItemStack( Block.ice, 1, 0 ) );


		
		//Ice Leggings
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMIce, 2 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 100 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_EXTINGUISHMENT, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.addEffect( Item_SpecialArmor.EFFECT_FREEZE, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe(new ItemStack( aitem[id], 1 ),
				"sss",
				"s s",
				"s s",
				's', new ItemStack( Block.ice, 1, 0 ) );

		

		//Ice Boots
		id ++;

		aitem[id] = new Item_SpecialArmor( aitemID[id], ArmorTexture[armorid], AMIce, 3 )
		.addEffect( Item_SpecialArmor.EFFECT_RESISTFIRE, 100 | Item_SpecialArmor.AMP_RESIST_UNBREAKABLE, Item_SpecialArmor.FLAG_ANYTIME )
		.addEffect( Item_SpecialArmor.EFFECT_EXTINGUISHMENT, 1000, Item_SpecialArmor.FLAG_FULLEQ )
		.addEffect( Item_SpecialArmor.EFFECT_FREEZE, 0, Item_SpecialArmor.FLAG_FULLEQ )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				"s s",
				"s s",
				's', new ItemStack( Block.ice, 1, 0 ) );

		
		
		//Ice Shears
		id ++;

		aitem[id] = new Item_Shears( aitemID[id], TMIce )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" s",
				"s ",
				's', new ItemStack( Block.ice, 1, 0 ) );

				

		//Ice Bow
		id ++;

		aitem[id] = new Item_Bow( aitemID[id], TMIce ).setParameters( 1.5F, 1.1F, 0.0F, 0.0F ).setCraftingMaterial( Block.ice.blockID )
		.addEffect( Potion.moveSlowdown.id, 6, 20 * 5, 0.2F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		GameRegistry.addRecipe( new ItemStack( aitem[id], 1 ),
				" st",
				"s t",
				" st",
				's', new ItemStack( getItemIDforCraft( "IceStick" ), 1, 0 ),
				't', Item.silk );



		//Ice Arrow
		id++;

		aitem[id] = new Item_Arrow( aitemID[id], 64 ).setParameters( 1.1, 2.2, 0.0, 0.04, ObjectHeader.toLowerCase() + "textures/entity/" + aitemname[id][0].substring( ObjectHeader.length() ) + ".png" )
		.addEffect( Potion.moveSlowdown.id, 6, 20 * 5, 0.2F )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );


		BlockDispenser.dispenseBehaviorRegistry.putObject( aitem[id], new Event_DispenserArrow() );
		
		GameRegistry.addRecipe( new ItemStack( aitem[id], 4 ),
				"h",
				"s",
				"f",
				'h', new ItemStack ( Block.ice, 1, 0 ),
				's', new ItemStack ( getItemIDforCraft( "IceStick" ), 1, 0 ),
				'f', Item.feather );

		
		//Ice Sword(Cursed)
		id ++;

		aitem[id] = new Item_IceSword( aitemID[id], TMIce )
		.setUnlocalizedName( aitemname[id][0] ).func_111206_d( aitemname[id][0] );

		GameRegistry.registerItem( aitem[id], aitemname[id][0] );
		LanguageRegistry.addName( aitem[id], aitemname[id][1] );
		LanguageRegistry.instance().addNameForObject( aitem[id], "ja_JP", aitemname[id][2] );

		
		eequip.addMobWeaponEquipment( new ItemStack( aitem[id] ), Event_MobEquipment.FLAG_OVERWORLD    & Event_MobEquipment.FLAG_SWORDMAN, 0.01 );
	
	}
	

	
	private void registerArrows() {
		Event_Arrow earrow = new Event_Arrow();

		earrow.addArrow( Item.arrow.itemID );
		if ( isEnabled( "Glass" ) ) earrow.addArrow( getItemIDforCraft( "GlassArrow" ) );
		if ( isEnabled( "Ancient" ) ) earrow.addArrow( getItemIDforCraft( "AncientArrow" ) );
		if ( isEnabled( "Coal" ) ) earrow.addArrow( getItemIDforCraft( "CoalArrow" ) );
		if ( isEnabled( "Obsidian" ) ) earrow.addArrow( getItemIDforCraft( "ObsidianArrow" ) );
		if ( isEnabled( "LapisLazuli" ) ) earrow.addArrow( getItemIDforCraft( "LapisLazuliArrow" ) );
		if ( isEnabled( "Redstone" ) ) earrow.addArrow( getItemIDforCraft( "RedstoneArrow" ) );
		if ( isEnabled( "Rainbow" ) ) {
			earrow.addArrow( getItemIDforCraft( "RainbowArrow" ) );
			earrow.addArrow( getItemIDforCraft( "SkyArrow" ) );
		}
		if ( isEnabled( "Quartz" ) ) earrow.addArrow( getItemIDforCraft( "QuartzArrow" ) );
		if ( isEnabled( "Emerald" ) ) earrow.addArrow( getItemIDforCraft( "EmeraldArrow" ) );
		if ( isEnabled( "Slime" ) ) earrow.addArrow( getItemIDforCraft( "SlimeArrow" ) );
		if ( isEnabled( "Star" ) ) earrow.addArrow( getItemIDforCraft( "StarArrow" ) );
		if ( isEnabled( "Bone" ) ) {
			earrow.addArrow( getItemIDforCraft( "BoneArrow" ) );
			earrow.addArrow( getItemIDforCraft( "WitherArrow" ) );
		}
		
		if ( isEnabled( "Ender" ) ) earrow.addArrow( getItemIDforCraft( "EnderArrow" ) );
		if ( isEnabled( "ExArcher" ) ) {
			earrow.addArrow( getItemIDforCraft( "WoodenArrow" ) );
			earrow.addArrow( getItemIDforCraft( "StoneArrow" ) );
			earrow.addArrow( getItemIDforCraft( "IronArrow" ) );
			earrow.addArrow( getItemIDforCraft( "DiamondArrow" ) );
			earrow.addArrow( getItemIDforCraft( "GoldenArrow" ) );
			earrow.addArrow( getItemIDforCraft( "VenomArrow" ) );
			earrow.addArrow( getItemIDforCraft( "ParalysisArrow" ) );
			earrow.addArrow( getItemIDforCraft( "HealingArrow" ) );
			earrow.addArrow( getItemIDforCraft( "CursedArrow" ) );
			earrow.addArrow( getItemIDforCraft( "BlowArrow" ) );
			earrow.addArrow( getItemIDforCraft( "AttractionArrow" ) );
			earrow.addArrow( getItemIDforCraft( "LightningArrow" ) );
			earrow.addArrow( getItemIDforCraft( "BombArrow" ) );
			earrow.addArrow( getItemIDforCraft( "CreeperArrow" ) );
			earrow.addArrow( getItemIDforCraft( "IncendiaryArrow" ) );
			earrow.addArrow( getItemIDforCraft( "MagicalArrow" ) );
			earrow.addArrow( getItemIDforCraft( "TorchArrow" ) );
			earrow.addArrow( getItemIDforCraft( "AssassinationArrow" ) );
		}
		if ( isEnabled( "Blaze" ) ) earrow.addArrow( getItemIDforCraft( "BlazeArrow" ) );
		if ( isEnabled( "Pumpkin" ) ) earrow.addArrow( getItemIDforCraft( "PumpkinArrow" ) );
		if ( isEnabled( "Lantern" ) ) earrow.addArrow( getItemIDforCraft( "LanternArrow" ) );
		if ( isEnabled( "Cactus" ) ) earrow.addArrow( getItemIDforCraft( "CactusArrow" ) );
		if ( isEnabled( "Glowstone" ) ) earrow.addArrow( getItemIDforCraft( "GlowstoneArrow" ) );
		if ( isEnabled( "Ghast" ) ) earrow.addArrow( getItemIDforCraft( "GhastArrow" ) );
		if ( isEnabled( "Poison" ) ) earrow.addArrow( getItemIDforCraft( "PoisonArrow" ) );
		if ( isEnabled( "Ice" ) ) earrow.addArrow( getItemIDforCraft( "IceArrow" ) );
		
		MinecraftForge.EVENT_BUS.register( earrow );

		EntityRegistry.registerModEntity( Entity_Arrow.class, ObjectHeader + "ExArrow", 0, instance, 250, 1, true );
		proxy.registerArrowRenderer( Entity_Arrow.class );
		
	}

	
	
	private void registerHammerRecipe() {
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.shovelIron, 	1, fm ), new ItemStack( Item.ingotIron, 1, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.pickaxeIron, 	1, fm ), new ItemStack( Item.ingotIron, 3, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.axeIron, 		1, fm ), new ItemStack( Item.ingotIron, 3, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.flintAndSteel, 	1, fm ), new ItemStack( Item.ingotIron, 1, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.swordIron, 		1, fm ), new ItemStack( Item.ingotIron, 2, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.swordGold, 		1, fm ), new ItemStack( Item.ingotGold, 2, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.shovelGold, 	1, fm ), new ItemStack( Item.ingotGold, 1, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.pickaxeGold, 	1, fm ), new ItemStack( Item.ingotGold, 3, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.axeGold, 		1, fm ), new ItemStack( Item.ingotGold, 3, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.hoeIron, 		1, fm ), new ItemStack( Item.ingotIron, 2, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.hoeGold, 		1, fm ), new ItemStack( Item.ingotGold, 2, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.helmetChain, 	1, fm ), new ItemStack( Item.ingotIron, 2, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.plateChain, 	1, fm ), new ItemStack( Item.ingotIron, 4, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.legsChain, 		1, fm ), new ItemStack( Item.ingotIron, 3, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.bootsChain, 	1, fm ), new ItemStack( Item.ingotIron, 2, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.helmetIron, 	1, fm ), new ItemStack( Item.ingotIron, 5, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.plateIron, 		1, fm ), new ItemStack( Item.ingotIron, 8, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.legsIron, 		1, fm ), new ItemStack( Item.ingotIron, 7, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.bootsIron, 		1, fm ), new ItemStack( Item.ingotIron, 4, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.helmetGold, 	1, fm ), new ItemStack( Item.ingotGold, 5, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.plateGold, 		1, fm ), new ItemStack( Item.ingotGold, 8, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.legsGold, 		1, fm ), new ItemStack( Item.ingotGold, 7, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.bootsGold, 		1, fm ), new ItemStack( Item.ingotGold, 4, 0 ), 2 );
		Item_Hammer.addRepairingRecipe( new ItemStack( Item.shears, 		1, fm ), new ItemStack( Item.ingotIron, 2, 0 ), 2 );
		
		if ( isEnabled( "Coal" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalSword" ), 		1, fm ), new ItemStack( Item.coal, 18, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalShovel" ), 		1, fm ), new ItemStack( Item.coal,  9, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalPickaxe" ), 		1, fm ), new ItemStack( Item.coal, 27, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalAxe" ),	 		1, fm ), new ItemStack( Item.coal, 27, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalHoe" ), 			1, fm ), new ItemStack( Item.coal, 18, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalHelmet" ), 		1, fm ), new ItemStack( Item.coal, 45, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalChestplate" ), 	1, fm ), new ItemStack( Item.coal, 72, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalLeggings" ), 	1, fm ), new ItemStack( Item.coal, 63, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "CoalBoots" ), 		1, fm ), new ItemStack( Item.coal, 36, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "ExVanilla" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GoldenShears" ),  1, fm ), new ItemStack( Item.ingotGold, 2, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "DiamondShears" ), 1, fm ), new ItemStack( Item.diamond, 2, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "LapisLazuli" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliSword" ), 		1, fm ), new ItemStack( Item.dyePowder, 18, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliShovel" ), 		1, fm ), new ItemStack( Item.dyePowder,  9, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliPickaxe" ), 		1, fm ), new ItemStack( Item.dyePowder, 27, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliAxe" ),	 		1, fm ), new ItemStack( Item.dyePowder, 27, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliHoe" ), 			1, fm ), new ItemStack( Item.dyePowder, 18, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliHelmet" ), 		1, fm ), new ItemStack( Item.dyePowder, 45, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliChestplate" ), 	1, fm ), new ItemStack( Item.dyePowder, 72, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliLeggings" ),	 	1, fm ), new ItemStack( Item.dyePowder, 63, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "LapisLazuliBoots" ), 		1, fm ), new ItemStack( Item.dyePowder, 36, 4 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "Redstone" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneSword" ), 		1, fm ), new ItemStack( Item.redstone, 18, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneShovel" ), 		1, fm ), new ItemStack( Item.redstone,  9, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstonePickaxe" ), 		1, fm ), new ItemStack( Item.redstone, 27, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneAxe" ),	 		1, fm ), new ItemStack( Item.redstone, 27, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneHoe" ), 			1, fm ), new ItemStack( Item.redstone, 18, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneHelmet" ), 		1, fm ), new ItemStack( Item.redstone, 45, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneChestplate" ), 	1, fm ), new ItemStack( Item.redstone, 72, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneLeggings" ), 	1, fm ), new ItemStack( Item.redstone, 63, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "RedstoneBoots" ), 		1, fm ), new ItemStack( Item.redstone, 36, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "Quartz" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzSword" ), 		1, fm ), new ItemStack( Item.netherQuartz,  8, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzShovel" ), 	1, fm ), new ItemStack( Item.netherQuartz,  4, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzPickaxe" ), 	1, fm ), new ItemStack( Item.netherQuartz, 12, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzAxe" ),	 	1, fm ), new ItemStack( Item.netherQuartz, 12, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzHoe" ), 		1, fm ), new ItemStack( Item.netherQuartz,  8, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzHelmet" ), 	1, fm ), new ItemStack( Item.netherQuartz, 20, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzChestplate" ), 1, fm ), new ItemStack( Item.netherQuartz, 32, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzLeggings" ), 	1, fm ), new ItemStack( Item.netherQuartz, 28, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "QuartzBoots" ), 		1, fm ), new ItemStack( Item.netherQuartz, 16, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "Emerald" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldSword" ), 		1, fm ), new ItemStack( Item.emerald, 2, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldShovel" ), 		1, fm ), new ItemStack( Item.emerald, 1, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldPickaxe" ), 		1, fm ), new ItemStack( Item.emerald, 3, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldAxe" ),	 		1, fm ), new ItemStack( Item.emerald, 3, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldHoe" ), 			1, fm ), new ItemStack( Item.emerald, 2, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldHelmet" ), 		1, fm ), new ItemStack( Item.emerald, 5, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldChestplate" ),	1, fm ), new ItemStack( Item.emerald, 8, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldLeggings" ), 		1, fm ), new ItemStack( Item.emerald, 7, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "EmeraldBoots" ), 		1, fm ), new ItemStack( Item.emerald, 4, 0 ), 3 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "Glowstone" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneSword" ), 		1, fm ), new ItemStack( Item.glowstone,  8, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneShovel" ), 		1, fm ), new ItemStack( Item.glowstone,  4, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstonePickaxe" ), 	1, fm ), new ItemStack( Item.glowstone, 12, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneAxe" ),	 		1, fm ), new ItemStack( Item.glowstone, 12, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneHoe" ), 		1, fm ), new ItemStack( Item.glowstone,  8, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneHelmet" ), 		1, fm ), new ItemStack( Item.glowstone, 20, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneChestplate" ),	1, fm ), new ItemStack( Item.glowstone, 32, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneLeggings" ), 	1, fm ), new ItemStack( Item.glowstone, 28, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "GlowstoneBoots" ), 		1, fm ), new ItemStack( Item.glowstone, 16, 0 ), 2 | Item_Hammer.FLAG_UNREPAIRABLE );
		}
		
		if ( isEnabled( "Poison" ) ) {
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonSword" ), 		1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 2, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonShovel" ), 	1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 1, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonPickaxe" ), 	1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 3, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonAxe" ),		1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 3, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonHoe" ), 		1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 2, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonHelmet" ), 	1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 5, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonChestplate" ),	1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 8, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonLeggings" ), 	1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 7, 0 ), 2 );
			Item_Hammer.addRepairingRecipe( new ItemStack( getItemIDforCraft( "PoisonBoots" ), 		1, fm ), new ItemStack( getItemIDforCraft( "PoisonSteel" ), 4, 0 ), 2 );
		}
	}
	

	
	
	private void addChestContent( String category, ItemStack items, int min, int max, int weight ) {
		if ( addChestContents )
			ChestGenHooks.addItem( category, new WeightedRandomChestContent( items, min, max, weight ) );
	}
	
	
	private void registerCreativeTabs() {
		for ( Item i : aitem ) {
			if ( i != null ) {
				if ( tabExTools.iconID == -1 && i instanceof Item_Sword )
					tabExTools.iconID = i.itemID;
				
				i.setCreativeTab( tabExTools );
			}
		}
			
	}
	


	public static int getItemID( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < aitemname.length; i ++ )
			if ( aitemname[i][0] != null && aitemname[i][0].equals( name ) ) return aitemID[i];
		return 0;
	}

	public static int getItemIDforCraft( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < aitemname.length; i ++ )
			if ( aitemname[i][0] != null && aitemname[i][0].equals( name ) ) return aitemID[i] + 256;
		int x = 1 / 0;	//debug: for illegal access
		return 0;
	}

	public static int getItemArrayID ( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < aitemname.length; i ++ )
			if ( aitemname[i][0] != null && aitemname[i][0].equals( name ) ) return i;
		return 0;
	}

	public static int getBlockID ( String name ) {
		name = ObjectHeader + name;

		for ( int i = 0; i < ablockname.length; i ++ )
			if ( ablockname[i][0] != null && ablockname[i][0].equals( name ) ) return ablockID[i];
		int x = 1 / 0;	//debug: for illegal access
		return 0;
	}


	private boolean isEnabled( String typeName ) {

		for ( int i = 0; i < atypename.length; i ++ )
			if ( atypename[i].equals( typeName ) && aitemIDdefault[i] != 0 ) return true;

		return false;
	}
	
}
