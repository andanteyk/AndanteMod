package andmod.ExBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Block_Pitfall extends Block {

	public Block_Pitfall( int blockID, Material material ) {
		super( blockID, material );	
	}

	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int bx, int by, int bz ) {
		return null;
	}
	
	
	@SideOnly(Side.CLIENT)
    public Icon getBlockTexture( IBlockAccess iba, int bx, int by, int bz, int side ) {
		
		/*/
		int[] id = new int[6];
		int[] cnt = new int[6];
		int ret = -1;
		id[0] = iba.getBlockId( bx, by - 1, bz );
		id[1] = iba.getBlockId( bx, by + 1, bz );
		id[2] = iba.getBlockId( bx, by, bz - 1 );
		id[3] = iba.getBlockId( bx, by, bz + 1 );
		id[4] = iba.getBlockId( bx - 1, by, bz );
		id[5] = iba.getBlockId( bx + 1, by, bz );
		
		for ( int i = 0; i < 6; i ++ ) {
			cnt[i] = 0;
			if ( id[i] == 0 || id[i] == this.blockID ) continue;
			
			for ( int k = i + 1; k < 6; k ++ ) {
				if ( id[i] == id[k] ) cnt[i] ++;
			}
			
			if ( cnt[i] >= 3 ) {
				ret = i;
				break;
			}
		}
		
		if ( ret == -1 ) {
			for ( int i = 0; i < 6; i ++ )
				if ( cnt[i] >= 2 ) {
					ret = i; break;
				}
		}
		
		if ( ret == -1 ) {
			for ( int i = 0; i < 6; i ++ )
				if ( id[i] != 0 && id[i] != this.blockID ) {
					ret = i; break;
				}
		}
		
		
		if ( ret != -1 ) {
			
			switch ( ret ) {
			case 0:
				by--; break;
			case 1:
				by++; break;
			case 2:
				bz--; break;
			case 3:
				bz++; break;
			case 4:
				bx--; break;
			case 5:
				bx++; break;
			}
			return Block.blocksList[id[ret]].getBlockTexture( iba, bx, by, bz, side );
		}
		
		if ( id[0] != 0 )
			return Block.blocksList[id[0]].getBlockTexture( iba, bx, by - 1, bz, side );
		if ( id[2] != 0 )
			return Block.blocksList[id[2]].getBlockTexture( iba, bx, by, bz - 1, side );
		if ( id[4] != 0 )
			return Block.blocksList[id[4]].getBlockTexture( iba, bx - 1, by, bz, side );
		
		return this.getIcon( side, iba.getBlockMetadata( bx, by, bz ) );
		
		/*/
		
		int[] id = new int[6];
		int[] cnt = new int[6];
		int ret = -1;
		id[0] = iba.getBlockId( bx, by - 1, bz );
		id[1] = iba.getBlockId( bx, by + 1, bz );
		id[2] = iba.getBlockId( bx, by, bz - 1 );
		id[3] = iba.getBlockId( bx, by, bz + 1 );
		id[4] = iba.getBlockId( bx - 1, by, bz );
		id[5] = iba.getBlockId( bx + 1, by, bz );
		
		for ( int i = 0; i < 6; i ++ ) {
			cnt[i] = 0;
			if ( id[i] == 0 || id[i] == this.blockID ) continue;
			
			for ( int k = i + 1; k < 6; k ++ ) {
				if ( id[i] == id[k] ) cnt[i] ++;
			}
			
			if ( cnt[i] >= 3 ) {
				ret = i;
				break;
			}
		}
		
		
		if ( ret == -1 ) {
			if ( id[2] != 0 && id[2] == id[3] )
				return getAnotherBlockTexture( iba, bx, by, bz, side, id[2], 2 );
			if ( id[4] != 0 && id[4] == id[5] )
				return getAnotherBlockTexture( iba, bx, by, bz, side, id[4], 4 );
			if ( id[0] != 0 && id[0] == id[1] )
				return getAnotherBlockTexture( iba, bx, by, bz, side, id[0], 0 );

		}
		
		//
		if ( ret == -1 ) {
			for ( int i = 0; i < 6; i ++ )
				if ( cnt[i] >= 2 ) {
					ret = i; break;
				}
		}
		
		if ( ret == -1 ) {
			for ( int i = 0; i < 6; i ++ )
				if ( id[i] != 0 && id[i] != this.blockID ) {
					ret = i; break;
				}
		}
		
		
		if ( ret != -1 ) {
			
			switch ( ret ) {
			case 0:
				by--; break;
			case 1:
				by++; break;
			case 2:
				bz--; break;
			case 3:
				bz++; break;
			case 4:
				bx--; break;
			case 5:
				bx++; break;
			}
			return Block.blocksList[id[ret]].getBlockTexture( iba, bx, by, bz, side );
		}
		
		if ( id[0] != 0 )
			return Block.blocksList[id[0]].getBlockTexture( iba, bx, by - 1, bz, side );
		if ( id[2] != 0 )
			return Block.blocksList[id[2]].getBlockTexture( iba, bx, by, bz - 1, side );
		if ( id[4] != 0 )
			return Block.blocksList[id[4]].getBlockTexture( iba, bx - 1, by, bz, side );
		
		return this.getIcon( side, iba.getBlockMetadata( bx, by, bz ) );
		
		
		//*/
    }
	
	
	private Icon getAnotherBlockTexture( IBlockAccess iba, int bx, int by, int bz, int side, int id, int shift ) {
		switch ( shift ) {
		case 0:
			by--; break;
		case 1:
			by++; break;
		case 2:
			bz--; break;
		case 3:
			bz++; break;
		case 4:
			bx--; break;
		case 5:
			bx++; break;
		}
		return Block.blocksList[id].getBlockTexture( iba, bx, by, bz, side );
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
	public boolean renderAsNormalBlock() {
		return false;
	}


	@Override
	public int colorMultiplier( IBlockAccess par1iBlockAccess, int par2, int par3, int par4 ) {
		return 0x8888FF;
	}

}
