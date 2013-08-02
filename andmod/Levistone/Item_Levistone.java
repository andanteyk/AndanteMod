package andmod.Levistone;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class Item_Levistone extends ItemArmor {
	
	private String armortex;

	public Item_Levistone( int itemID, String texture, EnumArmorMaterial material, int armorType ) {
		super( itemID, material, 1, armorType );
		armortex = texture;
	}

	@Override
	public void onArmorTickUpdate( World world, EntityPlayer eplayer, ItemStack items ) {
		
		if ( !eplayer.onGround && eplayer.motionY <= -0.05 ) {
			if ( !eplayer.isSneaking() ) eplayer.motionY = -0.1;
			eplayer.fallDistance = 0.0F;
		}
	}

	
	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {
		
		if ( !world.isRemote ) {
            ChunkPosition cpos = world.findClosestStructure( "Stronghold", (int)eplayer.posX, (int)eplayer.posY, (int)eplayer.posZ );

            if ( cpos != null ) {
            	
            	double px = cpos.x - eplayer.posX;
            	double pz = cpos.z - eplayer.posZ;
            	
            	double angle = Math.atan2( pz, px ) / Math.PI * 180.0 - eplayer.rotationYaw - 90.0;
            	double distance = Math.sqrt( px * px + pz * pz );
            	
            	while ( angle >= 180.0 ) angle -= 360.0;
            	while ( angle < -180.0 ) angle += 360.0;
            	
            	eplayer.addChatMessage( "Stronghold : " + String.format( "%1$.0f", distance ) + "m, " + String.format( "%1$.0f", angle ) + " degrees" );
            }
        }
		
		return items;
	}
	
	
	@Override
	public String getArmorTexture( ItemStack items, Entity entity, int slot, int layer ) {
		return armortex;
	}

	

}
