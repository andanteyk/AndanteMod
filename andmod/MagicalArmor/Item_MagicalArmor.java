package andmod.MagicalArmor;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import andmod.AndCore.Item_Armor;

public class Item_MagicalArmor extends Item_Armor implements ISpecialArmor {
	
	private boolean canConsumeXP = true;

	public Item_MagicalArmor( int itemID, String[] texture,	EnumArmorMaterial material, int armorType ) {
		super( itemID, texture, material, armorType );
	}

	public Item_MagicalArmor( int itemID, String topTexture, String legsTexture, EnumArmorMaterial material, int armorType ) {
		super( itemID, topTexture, legsTexture, material, armorType );
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	public Item_MagicalArmor setCanConsumeXP( boolean flag ) {
		canConsumeXP = flag;
		return this;
	}
	
	@Override
	public ArmorProperties getProperties( EntityLivingBase eplayer, ItemStack armor, DamageSource source, double damage, int slot ) {
		
		if ( eplayer instanceof EntityPlayer && ((EntityPlayer)eplayer).experienceTotal > 0 )
			return new ArmorProperties( 1, 1.0, Integer.MAX_VALUE );
		else
			return new ArmorProperties( 0, getArmorMaterial().getDamageReductionAmount( armorType ) / 25.0, armor.getMaxDamage() - armor.getItemDamage() + 1 );
	}

	@Override
	public int getArmorDisplay( EntityPlayer eplayer, ItemStack armor, int slot ) {
		return getArmorMaterial().getDamageReductionAmount( armorType );
	}

	@Override
	public void damageArmor( EntityLivingBase eplayer, ItemStack items, DamageSource source, int damage, int slot ) {

		if ( eplayer instanceof EntityPlayer && ((EntityPlayer)eplayer).experienceTotal > 0 )
			decreaseXP( (EntityPlayer)eplayer, damage );
		else items.damageItem( damage * 4, eplayer );
			
	}

	
	@Override
	public void onArmorTickUpdate( World world, EntityPlayer eplayer, ItemStack items ) {
		
		if ( world.getWorldTime() % 20 == 0 ) {
			if (  !decreaseXP( eplayer, canConsumeXP ? 1 : 0 ) )
				eplayer.addPotionEffect( new PotionEffect( Potion.moveSlowdown.id, 39, 2 ) );
		}
	}

	
	
	private boolean decreaseXP( EntityPlayer eplayer, int amount ) {
		int curxp = lvtoxp( eplayer.experienceLevel, eplayer.experience );
		if ( amount <= 0 ) return curxp > 0;
		
		eplayer.setScore( eplayer.getScore() - curxp );
		eplayer.experience = 0.0F;
		eplayer.experienceLevel = 0;
		eplayer.experienceTotal = 0;
		
		if ( curxp <= amount ) return false;
		
		int lx = curxp - amount;
		int maxlv = xptolv( curxp - amount );
		int lv = 0;
		
		for( int incr = xpBarCap( lv ); lv <= maxlv && incr <= lx; incr = xpBarCap( lv ) ) {
			lx -= incr;
			eplayer.addExperience( incr );
			lv ++;
		}
		
		eplayer.addExperience( lx );
		
		return true;
	}
	
	
	
	
	//以降、Item_XPCrystalより借用
	private int xptolv( int xp ) {
		int lv = 0;
		
		for( int decr = xpBarCap( lv ); decr <= xp; decr = xpBarCap( lv ) ) {
			xp -= decr;
			lv++;
		}
		
		return lv;
	}
	
	private int lvtoxp( int lv, float xp ) {
		int ret = 0;
		for( int i = 0; i < lv; i++ )
			ret += xpBarCap( i );
		
		//変換後の経験値に、多少の誤差が含まれることがあります。
		//仕様なのでどうしようもありません。
		return Math.round( xpBarCap( lv ) * xp ) + ret;
	}
	
	private int xpBarCap( int lv ) {
        return ( lv >= 30 ) ? ( 62 + ( lv - 30 ) * 7 ) : ( ( lv >= 15 ) ? ( 17 + ( lv - 15 ) * 3 ) : 17 );
    }
	
}
