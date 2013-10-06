package andmod.AndCore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Item_Arrow extends Item implements IArrow {

	public Struct_Arrow property;

	/**
	 * 新しい矢を定義します。直後に setParameters で初期化することをお勧めします。
	 * @param itemID	アイテムのID。
	 * @param maxStack	スタック個数。
	 */
	public Item_Arrow( int itemID, int maxStack ) {
		super( itemID );

		setMaxStackSize( maxStack );
		setCreativeTab( CreativeTabs.tabCombat );
	}

	/**
	 * 矢のデータを設定します。
	 * @param velocity	矢の速度。既定値1.0。
	 * @param power		矢の威力。既定値2.0。
	 * @param knockback	ノックバック力。既定値0.0。
	 * @param gravity	重力値。既定値0.05F。
	 * @param texture	テクスチャのパス。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Arrow setParameters( double velocity, double power, double knockback, double gravity, String texture ) {
		property = new Struct_Arrow( itemID + 256, velocity, power, knockback, gravity, texture );
		return this;
	}


	/**
	 * 矢に特殊効果を与えます。
	 * @param effectID		効果ID。
	 * @param amplifier		強化レベル。
	 * @param duration		効果時間。
	 * @param probability	発生確率。0.0～1.0の間で指定します。
	 * @return				設定されたアイテムを返します。
	 */
	public Item_Arrow addEffect( int effectID, int amplifier, int duration, float probability ) {

		property.EffectIDList.add( effectID );
		property.AmplifierList.add( amplifier );
		property.DurationList.add( duration );
		property.ProbabilityList.add( probability );
		return this;
	}



	public double getVelocity() {
		return property.velocity;
	}


	public double getPower() {
		return property.power;
	}


	public double getKnockback() {
		return property.knockback;
	}


	public double getGravity() {
		return property.gravity;
	}


	public ResourceLocation getTexture() {
		return property.texture;
	}


	@Override
	public int getMaxItemUseDuration( ItemStack par1ItemStack ) {
		return 72000;
	}

	
	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		//debug
		/*
		if( !world.isRemote ) {
			long itime = world.getChunkFromBlockCoords( (int)Math.round( eplayer.posX ), (int)Math.round( eplayer.posZ ) ).field_111204_q;
			eplayer.addChatMessage( "Inhabited Time: " + itime );
			double probbase = world.func_110746_b( eplayer.posX, eplayer.posY, eplayer.posZ );
			eplayer.addChatMessage( "Probability Base : " + ( probbase * 100.0 ) );
		}
		*/
		
		//投げられないほうがいいと思いますです
		//eplayer.setItemInUse( items, getMaxItemUseDuration( items ) );
		
		return items;
	}

	
	
	@Override
	public int getArrowID( ItemStack items ) {
		if ( items == null ) return 0;
		return itemID;
	}

	@Override
	public boolean canConsumeArrow( ItemStack items ) {
		if ( items == null ) return false;
		return items.stackSize > 0;
	}

	@Override
	public ItemStack consumeArrow( ItemStack items ) {
		if ( items == null ) return items;
		
		items.stackSize --;
		if ( items.stackSize <= 0 )
			items = null;
		
		return items;
	}

	
	/*
	@Override
	public void onPlayerStoppedUsing( ItemStack items, World world, EntityPlayer eplayer, int remain ) {
		
		//undone: 矢を投げるコード
		double strength = ( getMaxItemUseDuration( items ) - remain ) / 20.0;
		strength = ( strength * strength + strength * 2.0 ) / 3.0;
		if ( strength > 1.0 ) strength = 1.0;
		
		Entity_Arrow earrow = new Entity_Arrow( world, eplayer, strength * 2.0 * property.velocity, Item.bow.itemID, this.itemID );
		earrow.setDamage( property.power );
		earrow.setIsCritical( strength == 1.0 );
		
		earrow.setKnockbackStrength( property.knockback );
		
		if ( !eplayer.capabilities.isCreativeMode ) items.stackSize --;
		else earrow.canBePickedUp = 2;
		
		world.playSoundAtEntity( eplayer, "random.bow", 1.0F, 1.0F / ( itemRand.nextFloat() * 0.4F + 1.2F ) + (float)strength * 0.5F );

		if ( !world.isRemote ) world.spawnEntityInWorld( earrow );
		
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		
		return EnumAction.bow;
	}
	*/
	

}
