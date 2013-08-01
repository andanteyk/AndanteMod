package andmod.ExTools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block_Spring extends Block {

	/** ジャンプ力を指定します。 */
	protected float bound;
	/** 透過テクスチャを使うかを指定します。 */
	public boolean isTransparent = false;


	/**
	 * 新しいバネブロックを定義します。
	 * @param blockId	ブロックのID。
	 * @param material	素材。
	 * @param bound		ブロックの上に乗ったときのジャンプ力。
	 */
	public Block_Spring( int blockID, Material material, float bound ) {
		super( blockID, material );
		this.bound = bound;
	}



	/**
	 * 半透明なテクスチャを利用するかを指定します。
	 * @param isTransparent	透明か透明でないかを指定します。
	 * @return				設定されたブロックを返します。
	 */
	public Block_Spring setTransparent ( boolean isTransparent ) {
		this.isTransparent = isTransparent;
		return this;
	}

	@Override
	public int getRenderBlockPass() {
		return isTransparent ? 1 : 0;
	}

	@Override
	public boolean shouldSideBeRendered( IBlockAccess ba, int bx, int by, int bz, int side ) {
		if ( isTransparent ) {
			int i1 = ba.getBlockId( bx, by, bz );
			return i1 == blockID ? false : super.shouldSideBeRendered( ba, bx, by, bz, 1 - side );

		} else
			return super.shouldSideBeRendered( ba, bx, by, bz, side );
	}

	@Override
	public boolean isOpaqueCube() {
		return !isTransparent;
	}


	@Override
	public void onFallenUpon( World world, int bx, int by, int bz, Entity entity, float par6 ) {
		//落下ダメージを無効化します。これを抜くと「落下ダメージは受けないが、足首の折れる音はする」という謎状態になります
		entity.fallDistance = 0;
	}



	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World par1World, int bx, int by, int bz ) {
		double f = 1.0 / 16.0;
		return AxisAlignedBB.getAABBPool().getAABB( bx, by, bz, bx + 1, by + 1 - f, bz + 1 );
	}

	@Override
	public void onEntityCollidedWithBlock( World world, int bx, int by, int bz, Entity entity ) {
		if ( entity.onGround && !entity.isSneaking() ) {

			//System.out.println( "fall: " + entity.fallDistance );

			entity.motionY += bound;
			entity.fallDistance = -bound / 2.0F;		//ある程度負の値にしておかないと、(ブロックのへこみ分も加算されて)落下ダメージを受けます
			entity.onGround = false;
		}
	}

}
