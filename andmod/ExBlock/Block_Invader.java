package andmod.ExBlock;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class Block_Invader extends Block {

	public Block_Invader( int blockID ) {
		super( blockID, Material.rock );

		this.setTickRandomly( true );
	}

	
	@Override
	public void updateTick( World world, int bx, int by, int bz, Random rand ) {
		
		final int bound = 1;
		int sidenum = 0;
		
		//*/
		for ( int y = - bound; y <= bound; y ++ ) {
			for ( int z = - bound; z <= bound; z ++ ) {
				for ( int x = - bound; x <= bound; x ++ ) {
				
					if ( Math.abs( x ) + Math.abs( y ) + Math.abs( z ) > 1 ) 
						continue;
				
					int blockID = world.getBlockId( bx + x, by + y, bz + z );
					
					if ( blockID != 0 && blockID != this.blockID && ( blockID == Block.waterStill.blockID || blockID == Block.waterMoving.blockID ) ) {
						world.setBlock( bx + x, by + y, bz + z, this.blockID, 0, 3 );
						//world.playAuxSFX( 2001, bx + x, by + y, bz + z, Block.grass.blockID + ( 0 << 12 ) );
					}
					
					if ( ( blockID == Block.waterStill.blockID || blockID == Block.waterMoving.blockID ) ) {
						sidenum ++;
					}
				}
			}
		}

		if ( sidenum <= 0 )
			world.setBlockToAir( bx, by, bz );
		
		/*/
		
		int x = rand.nextInt( bound * 2 + 1 ) - bound;
		int y = rand.nextInt( bound * 2 + 1 ) - bound;
		int z = rand.nextInt( bound * 2 + 1 ) - bound;
		if ( world.getBlockId( bx + x, by + y, bz + z ) != 0 )
			world.setBlock( bx + x, by + y, bz + z, this.blockID, 0, 2 );
		//*/
		
	}

	
	
}
