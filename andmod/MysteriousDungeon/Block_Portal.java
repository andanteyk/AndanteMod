package andmod.MysteriousDungeon;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Block_Portal extends BlockBreakable {

	public static final int frameID = Block.blockRedstone.blockID;

	public Block_Portal( int blockID, String name ) {
		super( blockID, name, Material.portal, false );

		setTickRandomly( true );
		setHardness( -1.0F );
		setStepSound( soundGlassFootstep );
		setLightValue( 0.75F );
		setCreativeTab( CreativeTabs.tabDecorations );
	}

	/*
	@Override
	public void updateTick( World world, int bx, int by, int bz, Random random ) {
		super.updateTick( world, bx, by, bz, random );
	}
	*/


	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int bx, int by, int bz ) {
        return null;
    }


	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess iba, int bx, int by, int bz ) {
	    float f;
	    float f1;

	    if ( iba.getBlockId( bx - 1, by, bz) != blockID && iba.getBlockId( bx + 1, by, bz ) != blockID ) {
	        f = 0.125F;
	        f1 = 0.5F;
	        setBlockBounds( 0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1 );

	    } else {
	        f = 0.5F;
	        f1 = 0.125F;
	        setBlockBounds( 0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1 );
	    }
	}


	@Override
	public boolean renderAsNormalBlock() {
        return false;
    }


	public boolean tryToCreatePortal( World world, int bx, int by, int bz ) {
        byte b0 = 0;
        byte b1 = 0;

        if ( world.getBlockId( bx - 1, by, bz ) == frameID || world.getBlockId( bx + 1, by, bz ) == frameID ) {
            b0 = 1;
        }

        if ( world.getBlockId( bx, by, bz - 1 ) == frameID || world.getBlockId(bx, by, bz + 1) == frameID ) {
            b1 = 1;
        }

        if ( b0 == b1 ) {
            return false;


        } else {
            if ( world.isAirBlock( bx - b0, by, bz - b1 ) )
            {
                bx -= b0;
                bz -= b1;
            }

            int l;
            int i1;

            for (l = -1; l <= 2; ++l)
            {
                for (i1 = -1; i1 <= 3; ++i1)
                {
                    boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;

                    if (l != -1 && l != 2 || i1 != -1 && i1 != 3)
                    {
                        int j1 = world.getBlockId(bx + b0 * l, by + i1, bz + b1 * l);
                        boolean isAirBlock = world.isAirBlock(bx + b0 * l, by + i1, bz + b1 * l);

                        if (flag)
                        {
                            if (j1 != frameID )
                            {
                                return false;
                            }
                        }
                        else if (!isAirBlock && j1 != Block.gravel.blockID)
                        {
                            return false;
                        }
                    }
                }
            }

            for (l = 0; l < 2; ++l)
            {
                for (i1 = 0; i1 < 3; ++i1)
                {
                    world.setBlock( bx + b0 * l, by + i1, bz + b1 * l, blockID, 0, 2 );
                }
            }

            return true;
        }
    }


	@Override
	public void onNeighborBlockChange( World world, int bx, int by, int bz, int neighborID ) {
	    byte b0 = 0;
	    byte b1 = 1;

	    if (world.getBlockId(bx - 1, by, bz) == blockID || world.getBlockId(bx + 1, by, bz) == blockID)
	    {
	        b0 = 1;
	        b1 = 0;
	    }

	    int i1;

	    for (i1 = by; world.getBlockId(bx, i1 - 1, bz) == blockID; --i1)
	    {
	        ;
	    }

	    if (world.getBlockId(bx, i1 - 1, bz) != frameID)
	    {
	        world.setBlockToAir(bx, by, bz);
	    }
	    else
	    {
	        int j1;

	        for (j1 = 1; j1 < 4 && world.getBlockId(bx, i1 + j1, bz) == blockID; ++j1)
	        {
	            ;
	        }

	        if (j1 == 3 && world.getBlockId(bx, i1 + j1, bz) == frameID)
	        {
	            boolean flag = world.getBlockId(bx - 1, by, bz) == blockID || world.getBlockId(bx + 1, by, bz) == blockID;
	            boolean flag1 = world.getBlockId(bx, by, bz - 1) == blockID || world.getBlockId(bx, by, bz + 1) == blockID;

	            if (flag && flag1)
	            {
	                world.setBlockToAir(bx, by, bz);
	            }
	            else
	            {
	                if ((world.getBlockId(bx + b0, by, bz + b1) != frameID || world.getBlockId(bx - b0, by, bz - b1) != blockID) && (world.getBlockId(bx - b0, by, bz - b1) != frameID || world.getBlockId(bx + b0, by, bz + b1) != blockID))
	                {
	                    world.setBlockToAir(bx, by, bz);
	                }
	            }
	        }
	        else
	        {
	            world.setBlockToAir(bx, by, bz);
	        }
	    }
	}


	@Override
	@SideOnly(Side.CLIENT)

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5){
        if (par1IBlockAccess.getBlockId(par2, par3, par4) == blockID)
        {
            return false;
        }
        else
        {
            boolean flag = par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == blockID && par1IBlockAccess.getBlockId(par2 - 2, par3, par4) != blockID;
            boolean flag1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == blockID && par1IBlockAccess.getBlockId(par2 + 2, par3, par4) != blockID;
            boolean flag2 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == blockID && par1IBlockAccess.getBlockId(par2, par3, par4 - 2) != blockID;
            boolean flag3 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == blockID && par1IBlockAccess.getBlockId(par2, par3, par4 + 2) != blockID;
            boolean flag4 = flag || flag1;
            boolean flag5 = flag2 || flag3;
            return flag4 && par5 == 4 ? true : (flag4 && par5 == 5 ? true : (flag5 && par5 == 2 ? true : flag5 && par5 == 3));
        }
    }

    
	@Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    
	@Override
	public void onEntityCollidedWithBlock( World world, int bx, int by, int bz, Entity entity ) {
        if ( entity.ridingEntity == null && entity.riddenByEntity == null && entity instanceof EntityPlayerMP ) {
        	EntityPlayerMP eplayer = (EntityPlayerMP) entity;
        	if ( eplayer.timeUntilPortal > 0 )
        		eplayer.timeUntilPortal = 10;
        	else if ( eplayer.dimension != AndMod_MysteriousDungeon.dimensionID ) {
        		eplayer.timeUntilPortal = 10;
        		eplayer.mcServer.getConfigurationManager().transferPlayerToDimension( eplayer, AndMod_MysteriousDungeon.dimensionID, new World_Teleporter( eplayer.mcServer.worldServerForDimension( AndMod_MysteriousDungeon.dimensionID ) ) );
        	} else {
        		eplayer.timeUntilPortal = 10;
        		eplayer.mcServer.getConfigurationManager().transferPlayerToDimension( eplayer, 0, new World_Teleporter( eplayer.mcServer.worldServerForDimension( 0 ) ) );
        	}
            entity.setInPortal();
        }
    }

    @Override
	@SideOnly(Side.CLIENT)

   
    public int getRenderBlockPass() {
        return 1;
    }


}
