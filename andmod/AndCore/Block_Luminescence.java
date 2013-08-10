package andmod.AndCore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block_Luminescence extends Block {
	
	//メタデータフラグ
	//1, 2	:明度。
	//3		:状態フラグ。0なら次のアップデート時に、1なら規定時間後に消滅する
	//4		:消滅フラグ。0なら即消滅、1ならこのフラグを折る(次の判定で消滅)
	
	protected final int tickRate = 5;

	public Block_Luminescence( int blockID ) {
		super( blockID, Material.air );

		setTickRandomly( true );
	}


	@Override
	public int idDropped( int par1, Random par2Random, int par3 ) {
		return 0;
	}


	@Override
	public boolean canDropFromExplosion( Explosion explosion ) {
		return false;
	}


	@Override
	public int getLightValue( IBlockAccess world, int bx, int by, int bz ) {
		switch( world.getBlockMetadata( bx, by, bz ) & 3 ) {
		case 0:
			return 15;
		case 1:
			return 14;
		case 2:
			return 11;
		case 3:
			return 7;
		}

		return 0;
	}


	@Override
	public boolean isBlockReplaceable( World world, int x, int y, int z ) {
		return true;
	}


	@Override
	public boolean canBeReplacedByLeaves( World world, int x, int y, int z ) {
		return true;
	}


	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int bx, int by, int bz ) {
		return null;
	}


	@Override
	public void updateTick( World world, int bx, int by, int bz, Random rnd ) {
		if ( ( world.getBlockMetadata( bx, by, bz ) & 8 ) == 0 ) {
			world.setBlock( bx, by, bz, 0, 0, 2 );
		} else {
			int meta = world.getBlockMetadata( bx, by, bz );
			world.setBlock( bx, by, bz, blockID, meta ^ 8, 2 );
			if ( ( meta & 4 ) != 0 ) world.scheduleBlockUpdate( bx, by, bz, blockID, tickRate );
		}
	}


	@Override
	public boolean isAirBlock( World world, int x, int y, int z ) {
		return true;
	}


	@Override
	public void onBlockAdded( World world, int bx, int by, int bz ) {
		if ( ( world.getBlockMetadata( bx, by, bz ) & 4 ) != 0 )
			world.scheduleBlockUpdate( bx, by, bz, blockID, tickRate );
	}

	
	//いる？
	@Override
	public boolean isBlockSolid( IBlockAccess baccess, int bx, int by, int bz, int side ) {
		return false;
	}

	
	//α値設定用。デバッグに使っています
	@Override
	public int getRenderBlockPass() {
		return 1;
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}


	@Override
	public boolean isCollidable() {
		return false;
	}


	@Override
	public int quantityDropped( Random random ) {
		return 0;
	}


	@Override
	public boolean shouldSideBeRendered( IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return false;
	}

}
