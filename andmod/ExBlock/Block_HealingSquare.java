package andmod.ExBlock;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Block_HealingSquare extends Block {

	private float damage;
	private int tick;
	
	public Block_HealingSquare( int blockID, Material material, float healingAmount, int tick ) {
		super( blockID, material );
		
		setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, 1.0F / 16.0F, 1.0F );
		damage = healingAmount;
		this.tick = tick;
	}

	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int bx, int by, int bz ) {
		return null;
	}
	
	
	@Override
	public void onEntityCollidedWithBlock( World world, int bx, int by,	int bz, Entity entity ) {
		
		if ( entity instanceof EntityLivingBase ) {
			if ( world.getBlockMetadata( bx, by, bz ) == 0 ) {
				( (EntityLivingBase)entity ).heal( damage );
				
				world.setBlockMetadataWithNotify( bx, by, bz, 1, 2 );
				world.scheduleBlockUpdate( bx, by, bz, blockID, tickRate( world ) );
	
			}
			
			for ( int i = 0; i < 1; i ++ ) {
				world.spawnParticle( "happyVillager", bx + world.rand.nextDouble(), (double)by + 2.0 / 16.0 + world.rand.nextDouble() * 14.0 / 16.0, bz + world.rand.nextDouble(), 
						world.rand.nextGaussian() * 0.02, world.rand.nextGaussian() * 0.02, world.rand.nextGaussian() * 0.02 );
	        }
		}
		
	}


	@Override
	public int getRenderType(){
		return 23;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	@Override
	public int tickRate( World world ){
		return tick;
	}
	
	
	@Override
	public void updateTick( World world, int bx, int by, int bz, Random rnd ) {
		int meta = world.getBlockMetadata( bx, by, bz );
		if ( meta > 0 ) {
			world.setBlockMetadataWithNotify( bx, by, bz, meta - 1, 2 );
			world.scheduleBlockUpdate( bx, by, bz, blockID, tickRate( world ) );
		}
	}


	@Override
	public void onBlockPlacedBy( World world, int bx, int by, int bz, EntityLivingBase eliv, ItemStack items ) {
		world.scheduleBlockUpdate( bx, by, bz, blockID, tickRate( world ) );
	}
	
}
