package andmod.ExTools;

import java.util.Random;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class Block_Glass extends BlockBreakable {

	public Block_Glass( int blockID, String texture, Material material, boolean shouldSideRender ) {
		super( blockID, texture, material, shouldSideRender );
		setCreativeTab( CreativeTabs.tabBlock );
	}


	@Override
	public int quantityDropped (Random par1Random ) {
		return 0;
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
	public int getRenderBlockPass() {
		return 0;
	}


	@Override
	public boolean canSilkHarvest() {
		return true;
	}

}
