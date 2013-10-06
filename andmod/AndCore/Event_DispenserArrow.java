package andmod.AndCore;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Event_DispenserArrow extends BehaviorProjectileDispense {

	
	/**
	 * ディスペンサーから矢を撃つためのイベントを定義します。
	 */
	public Event_DispenserArrow() {	
	}
	
	
	@Override
	public ItemStack dispenseStack( IBlockSource bsource, ItemStack items ) {
		
		int arrowID;
		
		
		if ( items.getItem() instanceof IArrow ) {
			arrowID = ( (IArrow) items.getItem() ).getArrowID( items ); 
			
			if ( !( (IArrow) items.getItem() ).canConsumeArrow( items ) )
				return items;
			
		} else arrowID = items.itemID;
			
		
        World world = bsource.getWorld();
        IPosition iposition = BlockDispenser.getIPositionFromBlockSource( bsource );
        EnumFacing enumfacing = BlockDispenser.getFacing( bsource.getBlockMetadata() );
        Entity_Arrow earrow = getProjectileEntity( world, iposition, arrowID );
        
        earrow.setThrowableHeading( enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + 0.1F, enumfacing.getFrontOffsetZ(), getSpeed( arrowID ), getWobbling( arrowID ) );
        world.spawnEntityInWorld( earrow );
        
        if ( items.getItem() instanceof IArrow ) {
        	items = ( (IArrow) items.getItem() ).consumeArrow( items );
        	if ( items == null ) return new ItemStack( arrowID, 0, 0 );		//nullを返すとエラー落ちするので、ダミーアイテムを返しています
        
        } else {
        	items.splitStack( 1 );
        }
        
        return items;
    }


	//呼ばれることを考慮していません。
	@Override
	protected IProjectile getProjectileEntity( World world, IPosition iposition ) {
		System.out.println( "[Andmod][Event_DispenserArrow] Fatal Error: Unexpected function calling!!" );
		Entity_Arrow earrow = new Entity_Arrow( world, iposition.getX(), iposition.getY(), iposition.getZ(), -1 );
		earrow.canBePickedUp = 1;
        return earrow;
	}


	protected Entity_Arrow getProjectileEntity( World world, IPosition iposition, int arrowID ) {
		//if ( arrowID <= 0 || isQuiver ) arrowID = Item.arrow.itemID;
		Entity_Arrow earrow = new Entity_Arrow( world, iposition.getX(), iposition.getY(), iposition.getZ(), arrowID );
		earrow.canBePickedUp = 1;
        return earrow;
	}


	/**
	 * 矢のブレの度合いを取得します。
	 * @param arrowID	矢のアイテムID。
	 * @return			ブレの度合いを返します。既定値は6.0です。
	 */
	protected double getWobbling( int arrowID ) {
		if ( arrowID <= 0 || arrowID == Item.arrow.itemID ) return 6.0;
		else return ( (Item_Arrow)Item.itemsList[arrowID] ).getVelocity() + 5.0;	//fixme
	}
	
	
	/**
	 * 矢の初速度を取得します。
	 * @param arrowID	矢のアイテムID。
	 * @return			速度を返します。既定値は1.1です。	
	 */
	protected double getSpeed( int arrowID ) {
		if ( arrowID <= 0 || arrowID == Item.arrow.itemID ) return 1.1;
		else return ( (Item_Arrow)Item.itemsList[arrowID] ).getVelocity() * 1.1;	//fixme
	}
	
}
