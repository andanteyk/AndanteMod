package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_Axe extends ItemAxe {

	protected int itemDamageVsEntity;
	protected int itemDamageVsBlock;
	protected int enchantability;

	private int cutCount = 0;
	private int dmgCount = 0;
	private int dmgLimit = 0;

	public static boolean showBreakingEffect = true;
	public static boolean canGatherItem = true;
	public static int canCutAll = 2;		//機能を有効にするか。0=常に無効, 1=無効(スニーク中のみ有効), 2=有効(スニーク中のみ無効)
	public static boolean canCutLeaves = true;
	public static int durabilityMode = 0;	//耐久モード。0=破壊した分だけ減少（耐久限界突破）、1=0に同じ（耐久限界あり）、2=-1のみ
	public static int loopLimit = 16;		//再帰コール限界値（≒範囲）
	public static int breakLimit = 256;		//破壊可能ブロック個数限界



	public Item_Axe( int itemID, EnumToolMaterial material ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 1;

		enchantability = material.getEnchantability();
	}

	public Item_Axe( int itemID, EnumToolMaterial material, int durability, float power, float efficiency, int enchantability ) {
		super( itemID, material );

		itemDamageVsEntity = 2;
		itemDamageVsBlock = 1;

		setMaxDamage( durability );
		damageVsEntity = power;
		efficiencyOnProperMaterial = efficiency;
		this.enchantability = enchantability;
	}


	/**
	 * 生物に対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Axe setItemDamageVsEntity( int damage ) {
		itemDamageVsEntity = damage;
		return this;
	}


	/**
	 * ブロックに対して使った時の、耐久値の減少量を設定します。
	 * @param damage	耐久値の減少量。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Axe setItemDamageVsBlock( int damage ) {
		itemDamageVsBlock = damage;
		return this;
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


	@Override
	public boolean onBlockDestroyed( ItemStack items, World world, int id, int bx, int by, int bz, EntityLivingBase eliv ) {

		//一括伐採用
		if ( eliv instanceof EntityPlayer )
			if ( processCutAll( items, world, id, bx, by, bz, (EntityPlayer)eliv ) )
				return true;


		if ( Block.blocksList[id].blockMaterial != Material.leaves && Block.blocksList[id].getBlockHardness( world, bx, by, bz ) != 0.0 )
			items.damageItem( itemDamageVsBlock, eliv );

		return true;
	}


	//note: 葉っぱとスイカも効率破壊の対象になります
	@Override
	public float getStrVsBlock( ItemStack items, Block block ) {
		return block != null && ( block.blockMaterial == Material.wood || block.blockMaterial == Material.plants || block.blockMaterial == Material.leaves || block.blockMaterial == Material.vine || block.blockMaterial == Material.pumpkin ) ? efficiencyOnProperMaterial : super.getStrVsBlock( items, block );
	}




	//以下、一括破壊関連コード

	/**
	 * ブロックが破壊される前に呼ばれます。  trueを返すとブロック破壊処理を中断します。
	 * 注意: SMPでは、 クライアントとサーバー両方で呼ばれます！
	 * ...これを使っていると、SSPでは処理されなくなってしまいます。この関数はClientからしか呼ばれないので当然ですが。
	 *
	 * @param items 装備しているアイテム
	 * @param bx X座標
	 * @param by Y座標
	 * @param bz Z座標
	 * @param eplayer 破壊しようとしているプレイヤー
	 * @return trueなら処理を中断、falseなら処理を続行します。
	 */
	@Override
	public boolean onBlockStartBreak( ItemStack items, int bx, int by, int bz, EntityPlayer eplayer ) {

		if ( eplayer.capabilities.isCreativeMode )
			return processCutAll(items, eplayer.worldObj, eplayer.worldObj.getBlockId(bx, by, bz), bx, by, bz, eplayer);
		else return super.onBlockStartBreak(items, bx, by, bz, eplayer);

	}


	protected boolean processCutAll( ItemStack items, World world, int bid, int bx, int by, int bz, EntityPlayer eplayer ) {

		Block b = Block.blocksList[bid];

		debugPrint( "processCatAll was called; id=" + bid + ", " + (b == null) + ", " + ( b == null ? true : !eplayer.canHarvestBlock(b)) + ", " + (toolMaterial.getHarvestLevel() < 2));


		if ( b == null || !eplayer.canHarvestBlock(b) || toolMaterial.getHarvestLevel() < 2 || !getCanCutAll(eplayer) ) return false;
		else if ( b.blockMaterial == Material.wood || ( canCutLeaves && b.blockMaterial == Material.leaves ) ) {

			cutCount = 0;
			dmgCount = 0;
			dmgLimit = items.getMaxDamage() - items.getItemDamage() + 1;

			if ( Block.blocksList[world.getBlockId(bx, by, bz)] == null )
				searchBlock ( eplayer.worldObj, eplayer, bx, by, bz, 0 );
			else breakBlock ( eplayer.worldObj, eplayer, bx, by, bz, 0 );

			debugPrint( cutCount + " Blocks are broken" );

			items.damageItem( ( durabilityMode == 2 ? 1 : dmgCount ) * itemDamageVsBlock, eplayer);
			if ( items.stackSize <= 0 ) eplayer.destroyCurrentEquippedItem();

			return true;
		} else return false;

	}



	protected void breakBlock( World world, EntityPlayer eplayer, int bx, int by, int bz, int depth ) {

		Block b = Block.blocksList[world.getBlockId(bx, by, bz)];


		if ( b == null || world.isRemote || ( durabilityMode == 1 && dmgCount >= dmgLimit ) || !( b.blockMaterial == Material.wood || ( canCutLeaves && b.blockMaterial == Material.leaves ) ) ||
				depth >= toolMaterial.getHarvestLevel() * loopLimit || cutCount >= toolMaterial.getHarvestLevel() * breakLimit ) return;

		int meta = world.getBlockMetadata(bx, by, bz);
		debugPrint("Breaking at (" + bx + ", " + by + ", " + bz + "), id=" + b.blockID + ", meta=" + meta);


		if ( showBreakingEffect )
			world.destroyBlock(bx, by, bz, false);
		else
			world.setBlockToAir(bx, by, bz);


		if ( !eplayer.capabilities.isCreativeMode )
			if ( canGatherItem )
				b.harvestBlock(world, eplayer, (int)Math.round(eplayer.posX), (int)Math.round(eplayer.posY), (int)Math.round(eplayer.posZ), meta);
			else
				b.harvestBlock(world, eplayer, bx, by, bz, meta);

		cutCount ++;
		if ( !canCutLeaves || b.blockMaterial != Material.leaves )
			dmgCount ++;




		searchBlock ( world, eplayer, bx, by, bz, depth );

	}



	protected void searchBlock ( World world, EntityPlayer eplayer, int bx, int by, int bz, int depth ) {

		for ( int y = 1; y >= -1; y -- )
			for ( int z = -1; z <= 1; z ++ )
				for ( int x = -1; x <= 1; x ++ ) {

					if ( x == 0 && y == 0 && z == 0 ) continue;

					breakBlock ( world, eplayer, bx + x, by + y, bz + z, depth + 1 );
				}

	}



	protected boolean getCanCutAll( EntityPlayer eplayer ) {
		return ( ( canCutAll == 1 &&  eplayer.isSneaking() ) ||
				( canCutAll == 2 && !eplayer.isSneaking() ) );
	}


	public void debugPrint( String str ) {
		//if ( DEBUG ) System.out.println(str);
	}





}
