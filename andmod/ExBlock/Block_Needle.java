package andmod.ExBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Block_Needle extends Block {

	private float damage;
	private int renderType;
	
	public Block_Needle( int blockID, Material material, float damage, int renderType ) {
		super( blockID, material );
		
		//setBlockBounds( 0.0F, 0.0F, 0.0F, 1.0F, 1.0F / 16.0F, 1.0F );
		this.damage = damage;
		this.renderType = renderType;
	}

	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int bx, int by, int bz ) {
		return null;
	}
	
	
	@Override
	public void onEntityCollidedWithBlock( World world, int bx, int by,	int bz, Entity entity ) {
		
		if ( entity instanceof EntityLivingBase ) {
			( (EntityLivingBase)entity ).attackEntityFrom( DamageSource.cactus, damage );
		}
	}


	@Override
	public int getRenderType(){
		return renderType;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
}
