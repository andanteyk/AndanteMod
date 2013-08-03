package andmod.AndCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class Item_Shears extends ItemShears {

	public float efficiency;
	
	
	public Item_Shears( int itemID, EnumToolMaterial material ) {
		super( itemID );
		setMaxDamage( material.getMaxUses() );
		this.efficiency = material.getEfficiencyOnProperMaterial() * 2.5F;
	}
	
	
	public Item_Shears( int itemID, int durability, float efficiency ) {
		super( itemID );
		setMaxDamage( durability );
		this.efficiency = efficiency;
	}


	@Override
	public boolean onBlockDestroyed( ItemStack items, World world, int bid, int bx, int by, int bz,	EntityLivingBase eliv ) {
		Material m = world.getBlockMaterial( bx, by, bz );
		
		if ( m != Material.leaves && m != Material.web && m != Material.vine && m != Material.circuits && 
			!( Block.blocksList[bid] instanceof IShearable ) )
			return super.onBlockDestroyed( items, world, bid, bx, by, bz, eliv );
		else
			return true;
	}


	@Override
	public boolean canHarvestBlock( Block block ) {
		return block.blockMaterial == Material.web || block.blockMaterial == Material.circuits;
	}


	@Override
	public float getStrVsBlock( ItemStack items, Block block ) {
		
		if ( block.blockMaterial == Material.web || block.blockMaterial == Material.leaves || block.blockMaterial == Material.vine )
			return efficiency;
		else if ( block.blockMaterial == Material.cloth )
			return efficiency / 3.0F;
		else
			return super.getStrVsBlock( items, block );
	}

	
	
	
}
