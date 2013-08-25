package andmod.ExBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Block_Vector extends Block {

	private double speed;
	private boolean hasFloor;
	private int normalColor;
	private int poweredColor;

	@SideOnly(Side.CLIENT)
    private Icon[] icons;


	public Block_Vector( int blockID, Material material, double speed, int normalColor, int poweredColor ) {
		super( blockID, material );

		this.speed = speed;
		hasFloor = true;
		this.normalColor = normalColor;
		this.poweredColor = poweredColor;
	}


	@Override
	public void registerIcons( IconRegister ireg ) {

		super.registerIcons( ireg );

		icons = new Icon[6];
		icons[0] = ireg.registerIcon( func_111023_E() + "_in" );
		icons[1] = ireg.registerIcon( func_111023_E() + "_out" );
		icons[2] = ireg.registerIcon( func_111023_E() + "_up" );
		icons[3] = ireg.registerIcon( func_111023_E() + "_down" );
		icons[4] = ireg.registerIcon( func_111023_E() + "_left" );
		icons[5] = ireg.registerIcon( func_111023_E() + "_right" );
	}


	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon( int side, int meta ) {

		if ( ( meta & 8 ) != 0 ) meta ^= 9;

		switch ( meta ) {
		case 0:
			switch( side ) {
			case 0:	return icons[0];
			case 1: return icons[1];
			case 2:
			case 3:
			case 4:
			case 5:	return icons[2];
			}

		case 1:
			switch( side ) {
			case 0:	return icons[1];
			case 1: return icons[0];
			case 2:
			case 3:
			case 4:
			case 5:	return icons[3];
			}

		case 2:
			switch( side ) {
			case 0:
			case 1: return icons[3];
			case 2: return icons[0];
			case 3:	return icons[1];
			case 4: return icons[5];
			case 5:	return icons[4];
			}

		case 3:
			switch( side ) {
			case 0:
			case 1: return icons[2];
			case 2: return icons[1];
			case 3:	return icons[0];
			case 4: return icons[4];
			case 5:	return icons[5];
			}

		case 4:
			switch( side ) {
			case 0:
			case 1: return icons[5];
			case 2: return icons[4];
			case 3:	return icons[5];
			case 4: return icons[0];
			case 5:	return icons[1];
			}

		case 5:
			switch( side ) {
			case 0:
			case 1: return icons[4];
			case 2: return icons[5];
			case 3:	return icons[4];
			case 4: return icons[1];
			case 5:	return icons[0];
			}

		default:
			return super.getIcon( side, meta );
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }


	@Override
	public boolean isOpaqueCube() {
        return false;
    }


	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered( IBlockAccess iba, int bx, int by, int bz, int side ) {
		int id = iba.getBlockId( bx, by, bz );
        return id == blockID ? false : super.shouldSideBeRendered( iba, bx, by, bz, 1 - side );

    }


	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int bx, int by, int bz ) {
		if ( hasFloor &&
			( ( world.getBlockMetadata( bx, by, bz ) & 6 ) != 0 && !( by > 0 && world.getBlockId( bx, by - 1, bz ) != 0 ) ) )
			return AxisAlignedBB.getBoundingBox( bx, by, bz, bx + 1.0, by + 1.0 / 16.0, bz + 1.0 );
		else
			return null;
	}


	@Override
	public void onEntityCollidedWithBlock( World world, int bx, int by, int bz, Entity entity ) {

		double attraction = 0.02;
		int meta = world.getBlockMetadata( bx, by, bz );

		if ( ( meta & 8 ) != 0 ) meta ^= 9;
		if ( entity instanceof EntityItem ) { 
			attraction = 0.10;
		}
		
		
		switch( meta ) {
		case 0:
			entity.motionY = +speed;
			entity.addVelocity( ( bx + 0.5 - entity.posX ) * attraction, 0.0, ( bz + 0.5 - entity.posZ ) * attraction );
			break;
		case 1:
			entity.motionY = -speed;
			entity.addVelocity( ( bx + 0.5 - entity.posX ) * attraction, 0.0, ( bz + 0.5 - entity.posZ ) * attraction );
			break;
		case 2:
			entity.motionZ = +speed;
			entity.addVelocity( ( bx + 0.5 - entity.posX ) * attraction, ( by + 0.5 - entity.posY ) * attraction, 0.0 );
			break;
		case 3:
			entity.motionZ = -speed;
			entity.addVelocity( ( bx + 0.5 - entity.posX ) * attraction, ( by + 0.5 - entity.posY ) * attraction, 0.0 );
			break;
		case 4:
			entity.motionX = +speed;
			entity.addVelocity( 0.0, ( by + 0.5 - entity.posY ) * attraction, ( bz + 0.5 - entity.posZ ) * attraction );
			break;
		case 5:
			entity.motionX = -speed;
			entity.addVelocity( 0.0, ( by + 0.5 - entity.posY ) * attraction, ( bz + 0.5 - entity.posZ ) * attraction );
			break;
		}

		entity.fallDistance = 0.0F;
	}


	@Override
	public int onBlockPlaced( World world, int bx, int by, int bz, int side, float px, float py, float pz, int meta ) {

		return side ^ 1;
	}


	@Override
	public void onBlockPlacedBy( World world, int bx, int by, int bz, EntityLivingBase eliv, ItemStack items ) {
        int l = BlockPistonBase.determineOrientation( world, bx, by, bz, eliv );
        if ( eliv.isSneaking() )
        	l ^= 1;
        world.setBlockMetadataWithNotify( bx, by, bz, l ^ 1, 2 );
    }


	@Override
	public void onNeighborBlockChange( World world, int bx, int by,	int bz, int nid ) {
		boolean ispowered = world.isBlockIndirectlyGettingPowered( bx, by, bz ) || world.isBlockIndirectlyGettingPowered( bx, by + 1, bz );
		int meta = world.getBlockMetadata( bx, by, bz );

		if ( ( ispowered && ( meta & 8 ) == 0 ) || ( !ispowered && ( meta & 8 ) != 0 ) )
			world.setBlockMetadataWithNotify( bx, by, bz, meta ^ 8, 3 );
	}


	@Override
	public int colorMultiplier( IBlockAccess iba, int bx, int by, int bz ) {
		int meta = iba.getBlockMetadata( bx, by, bz );

		if ( ( meta & 8 ) != 0 ) return poweredColor;
		else return normalColor;
	}


	@Override
	public int getRenderColor( int meta ) {

		if ( ( meta & 8 ) != 0 ) return poweredColor;
		else return normalColor;
	}



}
