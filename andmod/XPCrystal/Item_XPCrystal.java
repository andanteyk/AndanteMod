package andmod.XPCrystal;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Item_XPCrystal extends Item {

	/**
	 * 新しい経験値クリスタルを定義します
	 * @param itemID	アイテムのID。
	 * @param maxStack	最大スタック個数。
	 * @param capacity	経験値貯蓄量。
	 */
	public Item_XPCrystal( int itemID, int maxStack, int capacity ) {
		super( itemID );
		
		setMaxStackSize( maxStack );
		setMaxDamage( capacity );
		setHasSubtypes( true );		//こうしないとインベントリでShift移動したときにアイテムが崩壊します。
	
	}

	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		if( world.isRemote ) return items;
		
		if( items.getItemDamage() == 0 || eplayer.experienceTotal <= 0 ) {
			//経験値を開放します。
			
			int xp = items.getMaxDamage() - items.getItemDamage();
			ItemStack emptyItem = new ItemStack( this, 1, items.getMaxDamage() );
			calcXP( eplayer, xp );
			
			if( --items.stackSize <= 0 )
				return emptyItem;
			if( !eplayer.inventory.addItemStackToInventory( emptyItem ) )
				eplayer.dropPlayerItem( emptyItem );
			
			//fixme:　音が鳴りません
			world.playSoundAtEntity( eplayer, "random.orb", 0.1F, 0.5F * ( ( itemRand.nextFloat() - itemRand.nextFloat() ) * 0.7F + 1.8F ) );
			//eplayer.playSound( "random.orb", 0.1F, 0.5F * ( ( itemRand.nextFloat() - itemRand.nextFloat() ) * 0.7F + 1.8F ) );
			     
			
		} else if( eplayer.experienceTotal > 0 ) {
			//経験値を溜めます。
			
			int xp = calcXP( eplayer, -items.getItemDamage() );
			ItemStack chargedItem = new ItemStack( this, 1, xp );
			
			if( --items.stackSize <= 0 )
				return chargedItem;
			if( !eplayer.inventory.addItemStackToInventory( chargedItem ) )
				eplayer.dropPlayerItem( chargedItem );
			
			world.playSoundAtEntity( eplayer, "random.orb", 0.1F, 0.5F * ( ( itemRand.nextFloat() - itemRand.nextFloat() ) * 0.7F + 1.8F ) );
			
		}
		
		return items;
	}

	
	/**
	 * 経験値を増減させます。
	 * @param eplayer	対象となるプレイヤー。
	 * @param xp		変動値。
	 * @return			減算しきれなかった経験値を返します。
	 */
	private int calcXP( EntityPlayer eplayer, int xp ) {
		
		if( xp > 0 ) {
			//経験値を加算します
			
			int lx = xp;
			int maxlv = xptolv( xp );
			int lv = 0;
			
			for( int incr = xpBarCap( lv ); lv <= maxlv && incr <= lx; incr = xpBarCap( lv ) ) {
				lx -= incr;
				eplayer.addExperience( incr );
				lv ++;
			}
			
			eplayer.addExperience( lx );
			
			return 0;
			
			
		} else if( xp < 0 ) {
			//経験値を減算します
			
			xp = -xp;
			
			int curxp = lvtoxp( eplayer.experienceLevel, eplayer.experience );
			
			eplayer.setScore( eplayer.getScore() - curxp );
			eplayer.experience = 0.0F;
			eplayer.experienceLevel = 0;
			eplayer.experienceTotal = 0;
			
			
			if( curxp < xp ) 
				return xp - curxp;
			
			else {
				calcXP( eplayer, curxp - xp );
				return 0;
			}
		
			
		} else return 0;
		
	}
	
	
	
	
	/**
	 * レベルから経験値に変換します。
	 * @param lv	現在のレベル。
	 * @param xp	経験値バーの伸び。
	 * @return		経験値。
	 */
	private int lvtoxp( int lv, float xp ) {
		int ret = 0;
		for( int i = 0; i < lv; i++ )
			ret += xpBarCap( i );
		
		//変換後の経験値に、多少の誤差が含まれることがあります。
		//仕様なのでどうしようもありません。
		return Math.round( xpBarCap( lv ) * xp ) + ret;
	}
	
	
	
	/**
	 * 経験値からレベルに変換します。
	 * @param xp	経験値。
	 * @return		レベル。
	 */
	private int xptolv( int xp ) {
		int lv = 0;
		
		for( int decr = xpBarCap( lv ); decr <= xp; decr = xpBarCap( lv ) ) {
			xp -= decr;
			lv++;
		}
		
		return lv;
	}
	
	
	
	/**
	 * 次のレベルへの所要経験値を求めます。
	 * この関数は{@link EntityPlayer#xpBarCap() EntityPlayer.xpBarCap()}の改変版です。
	 * @param lv	レベル。
	 * @return		所要経験値。
	 */
	private int xpBarCap( int lv ) {
        return ( lv >= 30 ) ? ( 62 + ( lv - 30 ) * 7 ) : ( ( lv >= 15 ) ? ( 17 + ( lv - 15 ) * 3 ) : 17 );
    }
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {

		list.add( ( items.getMaxDamage() - items.getItemDamage() ) + " / " + items.getMaxDamage() + " XP" );
		
		if( items.stackSize > 1 )
			list.add( "Total " + ( ( items.getMaxDamage() - items.getItemDamage() ) * items.stackSize ) + " / " + ( items.getMaxDamage() * items.stackSize ) + " XP" ); 
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity( ItemStack items ) {
		return items.getItemDamage() == 0 ? EnumRarity.epic : EnumRarity.common;
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect( ItemStack items, int pass ) {
		return items.getItemDamage() == 0;	
	}
	

	
}
