package andmod.AndCore;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Item_Food extends ItemFood {

	protected EnumAction act = EnumAction.eat;
	protected ItemStack container = null;

	protected ArrayList<Integer> IDList = new ArrayList<Integer>();
	protected ArrayList<Integer> DurationList = new ArrayList<Integer>();
	protected ArrayList<Integer> AmplifierList = new ArrayList<Integer>();
	protected ArrayList<Float> ProbabilityList = new ArrayList<Float>();

	/** ステータス付加：点火します。 */
	public static final int EFFECT_IGNITION		= 256 + 0;
	/** ステータス付加：体力を(amp)回復します。 */
	public static final int EFFECT_HEAL			= 256 + 1;
	/** ステータス付加：悪性ステータス異常を回復します。 */
	public static final int EFFECT_DISPEL		= 256 + 2;
	/** ステータス付加：ステータス効果を除去します。牛乳と同じです。 */
	public static final int EFFECT_REMOVEEFFECT = 256 + 3;


	/**
	 * 食料のデータを設定します。
	 * @param itemId		アイテムのID。
	 * @param maxstack		スタック可能最大個数。
	 * @param heal			満腹度回復量。
	 * @param saturation	隠し満腹度（腹持ち）回復量。内部的に(満腹度*隠し満腹度*2.0)加算されます
	 * @param canfeedDog	trueなら犬に与えることができます。
	 * @param isDrink		trueならモーションが飲むときのものになります。falseなら食べます。
	 */
	public Item_Food( int itemId, int maxstack, int heal, float saturation, boolean canfeedDog, boolean isDrink ) {
		super( itemId, heal, saturation, canfeedDog );

		maxStackSize = maxstack;
		act = ( isDrink ? EnumAction.drink : EnumAction.eat );
	}

	/**
	 * 食料のデータを設定します。こちらは腹持ちを直接指定できます。
	 * @param itemId		アイテムのID。
	 * @param maxstack		スタック可能最大個数。
	 * @param heal			満腹度回復量。0を指定すると腹持ちも0になります。
	 * @param saturation	隠し満腹度（腹持ち）回復量。
	 * @param canfeedDog	trueなら犬に与えることができます。
	 * @param isDrink		trueならモーションが飲むときのものになります。falseなら食べます。
	 */
	public Item_Food( int itemId, int maxstack, int heal, double saturationValue, boolean canfeedDog, boolean isDrink ) {
		super( itemId, heal, (float)saturationValue / heal / 2.0F, canfeedDog );

		maxStackSize = maxstack;
		act = ( isDrink ? EnumAction.drink : EnumAction.eat );
	}




	@Override
	public EnumAction getItemUseAction( ItemStack items ) {
		return act;
	}

	@Override
	public ItemStack onEaten( ItemStack items, World world, EntityPlayer eplayer ) {
		
		/*//重複食事テスト
		try {
			int x = 1 / 0;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		*/
			
		items.stackSize --;
		eplayer.getFoodStats().addStats( this );
	
		world.playSoundAtEntity( eplayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F );

		tryToApplyFoodEffect( items, world, eplayer );


		if ( container != null ) {
			if ( items.stackSize <= 0 )
				return container;

			//.copy() を付け忘れると container の個数が減少してバグを発生させるようです。
			else if ( !eplayer.inventory.addItemStackToInventory( container.copy() ) )
				eplayer.dropPlayerItem( container ) ;
		}

		return items;
	}

	
	/**
	 * 食料の特殊効果の適用を試みます。
	 * @param items		食べたアイテム。
	 * @param world		世界。
	 * @param eplayer	食べたプレイヤー。
	 */
	protected void tryToApplyFoodEffect( ItemStack items, World world, EntityPlayer eplayer ) {
		if ( !world.isRemote )
			for ( int i = 0; i < IDList.size(); i++ )
				if ( itemRand.nextFloat() < ProbabilityList.get( i ) )
					applyFoodEffect( world, eplayer, i );
	}


	/**
	 * 食料の特殊効果を適用します。
	 * @param world		世界。
	 * @param eplayer	食べたプレイヤー。
	 * @param id		効果ID。
	 */
	protected void applyFoodEffect ( World world, EntityPlayer eplayer, int id ) {

		if ( IDList.get( id ) < 256 )
			eplayer.addPotionEffect(new PotionEffect(IDList.get( id ), DurationList.get( id ), AmplifierList.get( id ) ) );

		else
			switch( IDList.get( id ) - 256 ) {
			case 0:		//点火
				eplayer.setFire( DurationList.get( id ) );
				break;
			case 1:		//体力回復(固定値、ゾンビかどうかの影響も受けない)
				eplayer.heal( AmplifierList.get( id ) );
				break;
			case 2:		//悪性ステータス回復
				for ( int i = 0; i < Potion.potionTypes.length; i ++ )
					if ( Potion.potionTypes[i] != null && Potion.potionTypes[i].isBadEffect() )
						eplayer.removePotionEffect( i );
				break;
			case 3:		//ステータス異常回復
				eplayer.clearActivePotions();
				break;
			}
	}






	/**
	 * アイテムのコンテナ（キノコシチューにおけるボウルのような）を設定します。
	 * @param container	コンテナ。
	 * @return			設定されたアイテムを返します。
	 */
	public Item_Food setContainer( ItemStack container ) {
		this.container = container;
		return this;
	}

	/**
	 * 食料を食べたときに与えられる、ポーション効果を設定します。複数追加できます。
	 * @param ID			ポーションID。
	 * @param duration		持続時間。
	 * @param amplifier		効果レベル。
	 * @param probability	確率。0.0～1.0の間で指定します。
	 * @return	設定されたデータを返します。
	 */
	public Item_Food addPotionEffect( int ID, int duration, int amplifier, float probability ) {
		IDList.add( ID );
		DurationList.add( duration );
		AmplifierList.add( amplifier );
		ProbabilityList.add( probability );
		return this;
	}


}
