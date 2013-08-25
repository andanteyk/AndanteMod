package andmod.ExBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

//スリッパではないです^^;
public class Block_Slipper extends Block {
	
	
	public Block_Slipper( int blockID, Material material, float slipperiness ) {
		super( blockID, material );	
		
		this.slipperiness = slipperiness;	//普通0.6, 氷0.98
	}

	
}
