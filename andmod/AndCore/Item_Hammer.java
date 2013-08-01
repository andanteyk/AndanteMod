package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Item_Hammer extends ItemPickaxe implements ICraftingHandler, IFuelHandler {

	protected int itemDamageVsEntity;
	protected int itemDamageVsBlock;
	protected int enchantability;

	protected boolean repair;
	protected boolean isBurning = false;
	protected int burnTick = 0;

	private int fm = OreDictionary.WILDCARD_VALUE;


	/**
	 * 新しいハンマーを定義します。
	 * @param itemID		アイテムのID。
	 * @param material		素材。
	 */
	public Item_Hammer( int itemID, EnumToolMaterial material ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 2;
		enchantability = material.getEnchantability();

		postInit();
	}


	/**
	 * 新しいハンマーを定義します。
	 * @param itemID			アイテムのID。
	 * @param material			素材。
	 * @param durability		耐久値。
	 * @param power				攻撃力。
	 * @param efficiency		採掘効率。
	 * @param enchantability	エンチャント適性。
	 */
	public Item_Hammer( int itemID, EnumToolMaterial material, int durability, float power, float efficiency, int enchantability ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 2;

		setMaxDamage( durability );
		damageVsEntity = power;
		this.enchantability = enchantability;
		efficiencyOnProperMaterial = efficiency;

		postInit();
	}


	/**
	 * 新しいハンマーを定義した直後に呼ばれます。
	 */
	public void postInit () {
		GameRegistry.registerCraftingHandler( this );
		MinecraftForge.setToolClass( this, "pickaxe", toolMaterial.getHarvestLevel() );
		MinecraftForge.setToolClass( this, "axe",     toolMaterial.getHarvestLevel() );
		addBasicCraftingRecipe();
	}


	@Override
	public int getItemEnchantability() {
		return enchantability;
	}


	@Override
	public boolean hitEntity( ItemStack items, EntityLivingBase defender, EntityLivingBase attacker ) {
		items.damageItem( itemDamageVsEntity, attacker );
		return true;
	}

	
	/**
	 * 生物に対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Hammer setItemDamageVsEntity( int damage ) {
		itemDamageVsEntity = damage;
		return this;
	}


	/**
	 * ブロックに対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Hammer setItemDamageVsBlock( int damage ) {
		itemDamageVsBlock = damage;
		return this;
	}


	/**
	 * かまどでの燃焼時間を設定します。ハンマー系は普通の燃料ハンドラではなく、こちらで登録してください。
	 * @param tick	燃焼時間。200=1個精錬できます。
	 * @return		設定されたアイテムを返します。
	 */
	public Item_Hammer setBurnTick( int tick ) {
		burnTick = tick;
		GameRegistry.registerFuelHandler( this );
		return this;
	}


	@Override
	public boolean doesContainerItemLeaveCraftingGrid( ItemStack items ) {
		return false;
	}

	
	@Override
	public boolean hasContainerItem() {
		return !repair;
	}


	@Override
	public ItemStack getContainerItemStack( ItemStack items ) {
		if ( isBurning ) {
			isBurning = false;
			return null;

		} else if ( items != null && items.itemID == itemID ) {
			int lv = EnchantmentHelper.getEnchantmentLevel( Enchantment.unbreaking.effectId, items );
			if ( itemRand.nextFloat() * ( lv + 1 ) <= 1.0 ) items.setItemDamage( items.getItemDamage() + 1 );
		}

		return items;
	}
	

	@Override
	public void onCrafting( EntityPlayer eplayer, ItemStack items, IInventory craftMatrix ) {
		repair = itemID == items.itemID;
	}
	

	@Override
	public void onSmelting( EntityPlayer eplayer, ItemStack items ) {
	}


	@Override
	public boolean canHarvestBlock( Block block ) {

		if ( block == Block.obsidian )
			return toolMaterial.getHarvestLevel() >= 3;

		else if (	block == Block.oreDiamond || block == Block.blockDiamond ||
				block == Block.oreEmerald || block == Block.blockEmerald ||
				block == Block.oreGold || block == Block.blockGold ||
				block == Block.oreRedstone || block == Block.oreRedstoneGlowing )
			return toolMaterial.getHarvestLevel() >= 2;

		else if (	block == Block.oreIron || block == Block.blockIron ||
				block == Block.oreLapis || block == Block.blockLapis )
			return toolMaterial.getHarvestLevel() >= 1;

		else if ( 	block.blockMaterial == Material.rock ||
				block.blockMaterial == Material.iron ||
				block.blockMaterial == Material.anvil )
			return true;

		else return false;

	}



	//以下、斧の処理に準じます
	@Override
	public boolean onBlockDestroyed( ItemStack items, World world, int id, int bx, int by, int bz, EntityLivingBase eliv ) {

		if ( Block.blocksList[id].blockMaterial != Material.leaves && Block.blocksList[id].getBlockHardness( world, bx, by, bz ) != 0.0 )
			items.damageItem( itemDamageVsBlock, eliv );

		return true;
	}


	@Override
	public float getStrVsBlock( ItemStack items, Block block ) {
		return block != null && ( block.blockMaterial == Material.wood || block.blockMaterial == Material.plants || block.blockMaterial == Material.leaves || block.blockMaterial == Material.vine || block.blockMaterial == Material.pumpkin ) ? efficiencyOnProperMaterial : super.getStrVsBlock( items, block );
	}


	@Override
	public int getBurnTime( ItemStack fuel ) {
		if ( fuel.itemID == itemID ) {
			isBurning = true;
			return burnTick;
		}
		return 0;
	}





	
	public void addBasicCraftingRecipe () {
		addBasicCraftingRecipe ( toolMaterial.getHarvestLevel(), toolMaterial == EnumToolMaterial.GOLD );
	}

	public void addAdvancedCraftingRecipe () {
		addAdvancedCraftingRecipe ( toolMaterial.getHarvestLevel(), toolMaterial == EnumToolMaterial.GOLD );
	}


	public void addBasicCraftingRecipe ( int toolMaterial, boolean canRepairMetalEquipments ) {

		if ( toolMaterial >= 0 ) {	//>=wood

			//石
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stone, 1, 0) });
			//丸石
			GameRegistry.addShapelessRecipe(new ItemStack(Block.gravel, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.cobblestone, 1, 0) });
			//砂利
			GameRegistry.addShapelessRecipe(new ItemStack(Block.sand, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.gravel, 1, 0) });
			//砂岩（全種）
			GameRegistry.addShapelessRecipe(new ItemStack(Block.sand, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.sandStone, 1, fm) });
			//「滑らかな」石ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 2, 8),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 0),
				new ItemStack(Block.stoneSingleSlab, 1, 0),
				new ItemStack(Block.stoneSingleSlab, 1, 0),
				new ItemStack(Block.stoneSingleSlab, 1, 0) });
			//「滑らかな」砂岩ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 2, 9),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 1),
				new ItemStack(Block.stoneSingleSlab, 1, 1),
				new ItemStack(Block.stoneSingleSlab, 1, 1),
				new ItemStack(Block.stoneSingleSlab, 1, 1) });

			//煉瓦
			GameRegistry.addShapelessRecipe(new ItemStack(Item.brick, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.brick, 1, 0) });
			//苔石
			GameRegistry.addShapelessRecipe(new ItemStack(Block.gravel, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.cobblestoneMossy, 1, 0) });
			//氷
			GameRegistry.addShapelessRecipe(new ItemStack(Block.blockSnow, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.ice, 1, 0) });
			//雪ブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Item.snowball, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.blockSnow, 1, 0) });
			//粘土
			GameRegistry.addShapelessRecipe(new ItemStack(Item.clay, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.blockClay, 1, 0) });
			//ネザーラック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.slowSand, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.netherrack, 1, 0) });
			//グローストーン
			GameRegistry.addShapelessRecipe(new ItemStack(Item.glowstone, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.glowStone, 1, 0) });

			//石煉瓦
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick, 1, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneBrick, 1, 0) });
			//苔むした石煉瓦
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick, 1, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneBrick, 1, 1) });
			//割れた石煉瓦
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneBrick, 1, 2) });
			//模様入り石煉瓦
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick, 1, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneBrick, 1, 3) });
			//ネザー煉瓦
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherrackBrick, 4),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.netherBrick, 1, 0) });
			//ネザークォーツブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherQuartz, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.blockNetherQuartz, 1, fm) });

		}

		if ( toolMaterial >= 1 ) {	//>=stone


		}

		if ( toolMaterial >= 2 ) {	//>=iron

			//＊金床
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 31, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.anvil, 1, 0) });
			//＊ひびの入った金床
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 20, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.anvil, 1, 1) });
			//＊壊れかけの金床
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 10, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.anvil, 1, 2) });


		}

		if ( toolMaterial >= 3 ) {	//>=diamond

			//黒曜石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.flint, 64, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.obsidian, 1, 0) });
			//ネザースター
			GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond, 16, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.netherStar, 1, 0) });

		}

		if ( toolMaterial >= 4 )
			//岩盤
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stone, 64),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.bedrock, 1, 0) });









		if ( canRepairMetalEquipments ) {	//武具修復

			//鉄のシャベル
			GameRegistry.addShapelessRecipe(new ItemStack(Item.shovelIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.shovelIron, 1, fm) });
			//鉄のツルハシ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.pickaxeIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.pickaxeIron, 1, fm) });
			//鉄の斧
			GameRegistry.addShapelessRecipe(new ItemStack(Item.axeIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.axeIron, 1, fm) });
			//火打石と打ち金
			GameRegistry.addShapelessRecipe(new ItemStack(Item.flintAndSteel, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.flintAndSteel, 1, fm) });
			//鉄の剣
			GameRegistry.addShapelessRecipe(new ItemStack(Item.swordIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.swordIron, 1, fm) });
			//金の剣
			GameRegistry.addShapelessRecipe(new ItemStack(Item.swordGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.swordGold, 1, fm) });
			//金のシャベル
			GameRegistry.addShapelessRecipe(new ItemStack(Item.shovelGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.shovelGold, 1, fm) });
			//金のツルハシ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.pickaxeGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.pickaxeGold, 1, fm) });
			//金の斧
			GameRegistry.addShapelessRecipe(new ItemStack(Item.axeGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.axeGold, 1, fm) });
			//鉄のクワ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.hoeIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.hoeIron, 1, fm) });
			//金のクワ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.hoeGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.hoeGold, 1, fm) });
			//チェーンヘルメット
			GameRegistry.addShapelessRecipe(new ItemStack(Item.helmetChain, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.helmetChain, 1, fm) });
			//チェーンチェストプレート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.plateChain, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.plateChain, 1, fm) });
			//チェーンレギンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.legsChain, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.legsChain, 1, fm) });
			//チェーンブーツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.bootsChain, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bootsChain, 1, fm) });
			//鉄のヘルメット
			GameRegistry.addShapelessRecipe(new ItemStack(Item.helmetIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.helmetIron, 1, fm) });
			//鉄のチェストプレート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.plateIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.plateIron, 1, fm) });
			//鉄のレギンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.legsIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.legsIron, 1, fm) });
			//鉄のブーツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.bootsIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bootsIron, 1, fm) });
			//金のヘルメット
			GameRegistry.addShapelessRecipe(new ItemStack(Item.helmetGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.helmetGold, 1, fm) });
			//金のチェストプレート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.plateGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.plateGold, 1, fm) });
			//金のレギンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.legsGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.legsGold, 1, fm) });
			//金のブーツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.bootsGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bootsGold, 1, fm) });
			//ハサミ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.shears, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.shears, 1, fm) });


		} else if ( toolMaterial >= 2 ) {	//>=iron

			//鉄のシャベル
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.shovelIron, 1, 0) });
			//鉄のツルハシ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.pickaxeIron, 1, 0) });
			//鉄の斧
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.axeIron, 1, 0) });
			//火打石と打ち金
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.flintAndSteel, 1, 0) });
			//鉄の剣
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.swordIron, 1, 0) });
			//金の剣
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.swordGold, 1, 0) });
			//金のシャベル
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.shovelGold, 1, 0) });
			//金のツルハシ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.pickaxeGold, 1, 0) });
			//金の斧
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.axeGold, 1, 0) });
			//鉄のクワ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.hoeIron, 1, 0) });
			//金のクワ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.hoeGold, 1, 0) });
			//チェーンヘルメット
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.helmetChain, 1, 0) });
			//チェーンチェストプレート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.plateChain, 1, 0) });
			//チェーンレギンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.legsChain, 1, 0) });
			//チェーンブーツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bootsChain, 1, 0) });
			//鉄のヘルメット
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.helmetIron, 1, 0) });
			//鉄のチェストプレート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.plateIron, 1, 0) });
			//鉄のレギンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 7, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.legsIron, 1, 0) });
			//鉄のブーツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bootsIron, 1, 0) });
			//金のヘルメット
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.helmetGold, 1, 0) });
			//金のチェストプレート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.plateGold, 1, 0) });
			//金のレギンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 7, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.legsGold, 1, 0) });
			//金のブーツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bootsGold, 1, 0) });
			//ハサミ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.shears, 1, 0) });


		}

	}

	
	public void addAdvancedCraftingRecipe ( int toolMaterial, boolean canRepairMetalEquipments ) {

		if ( toolMaterial >= 0 ) {
			//石炭鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.coal, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreCoal, 1, 0) });
			//ディスペンサー
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 7, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.dispenser, 1, 0) });
			//音符ブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.music, 1, 0) });
			//粘着ピストン
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pistonStickyBase, 1, 0) });
			//ピストン
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pistonBase, 1, 0) });
			//枯れ木
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.deadBush, 1, 0) });

			//石ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 0),
				new ItemStack(Block.stoneSingleSlab, 1, 0) });
			//砂岩ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 1),
				new ItemStack(Block.stoneSingleSlab, 1, 1) });
			//旧い木材ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 2),
				new ItemStack(Block.stoneSingleSlab, 1, 2) });
			//丸石ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 3),
				new ItemStack(Block.stoneSingleSlab, 1, 3) });
			//煉瓦ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 4),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 4),
				new ItemStack(Block.stoneSingleSlab, 1, 4) });
			//石煉瓦ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 5),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 5),
				new ItemStack(Block.stoneSingleSlab, 1, 5) });
			//ネザー煉瓦ハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 6),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 6),
				new ItemStack(Block.stoneSingleSlab, 1, 6) });
			//ネザークォーツハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneDoubleSlab, 1, 7),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneSingleSlab, 1, 7),
				new ItemStack(Block.stoneSingleSlab, 1, 7) });
			//石ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 0) });
			//砂岩ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 1) });
			//旧い木材ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 2) });
			//丸石ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 3) });
			//煉瓦ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 4),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 4) });
			//石煉瓦ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 5),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 5) });
			//ネザー煉瓦ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 6),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 6) });
			//ネザークォーツダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 7),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 7) });
			//「滑らかな」石ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 8) });
			//「滑らかな」砂岩ダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneSingleSlab, 2, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneDoubleSlab, 1, 9) });
			//樫の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsWoodOak, 1, 0) });
			//チェスト
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.chest, 1, 0) });
			//作業台
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.workbench, 1, 0) });
			//かまど
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.furnaceIdle, 1, 0) });
			//梯子
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.ladder, 1, 0) });
			//丸石の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsCobblestone, 1, 0) });
			//レバー
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.lever, 1, 0) });
			//石の感圧板
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stone, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pressurePlateStone, 1, 0) });
			//木の感圧板
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pressurePlatePlanks, 1, 0) });
			//石のボタン
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stoneButton, 1, 0) });
			//ジュークボックス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.jukebox, 1, 0) });
			//フェンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.fence, 1, 0) });
			//ジャック・オ・ランタン
			GameRegistry.addShapelessRecipe(new ItemStack(Item.pumpkinSeeds, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pumpkinLantern, 1, 0) });
			//トラップドア
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.trapdoor, 1, 0) });
			//茶色のキノコ岩
			GameRegistry.addShapelessRecipe(new ItemStack(Block.mushroomBrown, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.mushroomCapBrown, 1, fm) });
			//赤色のキノコ岩
			GameRegistry.addShapelessRecipe(new ItemStack(Block.mushroomRed, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.mushroomCapRed, 1, fm) });
			//スイカ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.melon, 9),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.melon, 1, 0) });
			//フェンスゲート
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 10),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.fenceGate, 1, 0) });
			//煉瓦の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Item.brick, 6, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsBrick, 1, 0) });
			//石煉瓦の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsStoneBrick, 1, 0) });
			//ネザー煉瓦フェンス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherrackBrick, 4),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.netherFence, 1, 0) });
			//ネザー煉瓦の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherrackBrick, 6, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsNetherBrick, 1, 0) });
			//レッドストーンランプ
			GameRegistry.addShapelessRecipe(new ItemStack(Block.glowStone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.redstoneLampIdle, 1, 0) });

			//樫のハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodDoubleSlab, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodSingleSlab, 1, 0),
				new ItemStack(Block.woodSingleSlab, 1, 0) });
			//松のハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodDoubleSlab, 1, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodSingleSlab, 1, 1),
				new ItemStack(Block.woodSingleSlab, 1, 1) });
			//白樺ののハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodDoubleSlab, 1, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodSingleSlab, 1, 2),
				new ItemStack(Block.woodSingleSlab, 1, 2) });
			//ジャングルの木のハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodDoubleSlab, 1, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodSingleSlab, 1, 3),
				new ItemStack(Block.woodSingleSlab, 1, 3) });
			//樫のダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodSingleSlab, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodDoubleSlab, 1, 0) });
			//松のダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodSingleSlab, 2, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodDoubleSlab, 1, 1) });
			//白樺のダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodSingleSlab, 2, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodDoubleSlab, 1, 2) });
			//ジャングルの木のダブルハーフブロック
			GameRegistry.addShapelessRecipe(new ItemStack(Block.woodSingleSlab, 2, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodDoubleSlab, 1, 3) });

			//砂岩の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.sandStone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsSandStone, 1, 0) });
			//トリップワイヤーフック
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.tripWireSource, 1, 0),
				new ItemStack(Block.tripWireSource, 1, 0) });

			//松の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 1, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsWoodSpruce, 1, 0) });
			//白樺の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 1, 2),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsWoodBirch, 1, 0) });
			//ジャングルの木の階段
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 1, 3),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsWoodJungle, 1, 0) });

			//丸石の壁
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.cobblestoneWall, 1, 0) });
			//苔石の壁
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.cobblestoneWall, 1, 1) });
			//木のボタン
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.woodenButton, 1, 0) });
			//トラップチェスト
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.chestTrapped, 1, 0) });
			//日照センサー
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherQuartz, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.daylightSensor, 1, 0) });
			//ネザークォーツ鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherQuartz, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreNetherQuartz, 1, 0) });
			//絵画
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.painting, 1, 0) });
			//看板
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.sign, 1, 0) });
			//木のドア
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 6, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.doorWood, 1, 0) });
			//ボート
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.boat, 1, 0) });
			//サトウキビ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.paper, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.reed, 1, 0) });
			//つりざお
			GameRegistry.addShapelessRecipe(new ItemStack(Item.silk, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.fishingRod, 1, 0) });
			//骨粉　１つ増えます
			GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 4, 15),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bone, 1, 0) });
			//ベッド
			GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bed, 1, 0) });
			//レッドストーンリピータ
			GameRegistry.addShapelessRecipe(new ItemStack(Block.torchRedstoneIdle, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.redstoneRepeater, 1, 0) });
			//醸造台
			GameRegistry.addShapelessRecipe(new ItemStack(Item.blazeRod, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.brewingStand, 1, 0) });
			//額縁
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.itemFrame, 1, 0) });
			//花瓶？
			GameRegistry.addShapelessRecipe(new ItemStack(Item.brick, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.flowerPot, 1, 0) });

			//スケルトンの頭蓋骨
			GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 16, 15),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.skull, 1, 0) });
			//ウィザースケルトンの頭蓋骨
			GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 16, 15),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.skull, 1, 1) });
			//ゾンビの首
			GameRegistry.addShapelessRecipe(new ItemStack(Item.rottenFlesh, 16, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.skull, 1, 2) });
			//人間の首
			GameRegistry.addShapelessRecipe(new ItemStack(Item.appleRed, 16, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.skull, 1, 3) });
			//クリーパーの頭
			GameRegistry.addShapelessRecipe(new ItemStack(Item.gunpowder, 16, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.skull, 1, 4) });
			//人参つきつりざお
			GameRegistry.addShapelessRecipe(new ItemStack(Item.silk, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.carrotOnAStick, 1, 0) });
			//花火発射台？
			GameRegistry.addShapelessRecipe(new ItemStack(Item.gunpowder, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.firework, 1, 0) });
			//花火？
			GameRegistry.addShapelessRecipe(new ItemStack(Item.gunpowder, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.fireworkCharge, 1, 0) });
			//レッドストーンコンパレータ
			GameRegistry.addShapelessRecipe(new ItemStack(Block.torchRedstoneIdle, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.comparator, 1, 0) });	//redstone comparetor
			//ネザークォーツの階段
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherQuartz, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.stairsNetherQuartz, 1, 0) });




		}

		if ( toolMaterial >= 1 ) {
			//鉄鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreIron, 1, 0) });
			//ラピスラズリ鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 9, 4),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreLapis, 1, 0) });
			//＊鉄格子
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.fenceIron, 1, 0),
				new ItemStack(Block.fenceIron, 1, 0),
				new ItemStack(Block.fenceIron, 1, 0) });
			//＊エンドポータルフレーム
			GameRegistry.addShapelessRecipe(new ItemStack(Block.whiteStone, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.endPortalFrame, 1, 0) });
			//＊鉄の感圧版
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pressurePlateIron, 1, 0) });
			//＊ホッパー
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.hopperBlock, 1, 0) });
			//＊ドロッパー
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 7, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.dropper, 1, 0) });
			//バケツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bucketEmpty, 1, 0) });
			//水入りバケツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bucketWater, 1, 0) });
			//溶岩バケツ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bucketLava, 1, 0) });
			//トロッコ
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.minecartEmpty, 1, 0) });
			//鉄のドア
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 6, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.doorIron, 1, 0) });
			//ミルク
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 3, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.bucketMilk, 1, 0) });
			//トロッコ（チェスト）
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.minecartCrate, 1, 0) });
			//トロッコ（竈）
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.minecartPowered, 1, 0) });
			//コンパス
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.compass, 1, 0) });
			//大釜
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 7, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.cauldron, 1, 0) });
			//トロッコ（TNT）
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 5, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.minecartTnt, 1, 0) });
			//トロッコ（ホッパー）
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 10, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.minecartHopper, 1, 0) });

		}

		if ( toolMaterial >= 2 ) {
			//金鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreGold, 1, 0) });
			//＊パワードレール
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.railPowered, 1, 0) });
			//＊ディテクターレール
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.railDetector, 1, 0) });
			//＊レール
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.rail, 1, 0),
				new ItemStack(Block.rail, 1, 0),
				new ItemStack(Block.rail, 1, 0) });
			//＊アクティベータレール
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.railActivator, 1, 0) });
			//ダイヤモンド鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreDiamond, 1, 0) });
			//レッドストーン鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.redstone, 6),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreRedstone, 1, 0) });
			//エメラルド鉱石
			GameRegistry.addShapelessRecipe(new ItemStack(Item.emerald, 1),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.oreEmerald, 1, 0) });

		}

		if ( toolMaterial >= 2 ) {
			//＊金の感圧版
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 2, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.pressurePlateGold, 1, 0) });
			//時計
			GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Item.pocketSundial, 1, 0) });


		}
		if ( toolMaterial >= 3 ) {
			//＊エンチャントテーブル
			GameRegistry.addShapelessRecipe(new ItemStack(Block.obsidian, 4, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.enchantmentTable, 1, 0) });
			//＊エンダーチェスト
			GameRegistry.addShapelessRecipe(new ItemStack(Block.obsidian, 8, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.enderChest, 1, 0) });
			//＊ビーコン
			GameRegistry.addShapelessRecipe(new ItemStack(Item.netherStar, 1, 0),
					new Object[]{ new ItemStack(this, 1, fm),
				new ItemStack(Block.beacon, 1, 0) });
		}
	}



}

