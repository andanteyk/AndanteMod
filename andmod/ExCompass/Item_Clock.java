package andmod.ExCompass;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Item_Clock extends Item {

	@SideOnly( Side.CLIENT )
	private Icon[] iconList;

	@SideOnly( Side.CLIENT )
	private Icon[] moonList;

	protected int dispmode;


	public Item_Clock( int itemID, int dispmode ) {
		super( itemID );

		setMaxStackSize( 1 );
		iconList = new Icon[64];
		moonList = new Icon[8];

		setMaxDamage( 0 );
		setHasSubtypes( true );
		this.dispmode = dispmode;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister ireg ) {
		//super.registerIcons( ireg );

		for ( int i = 0; i < iconList.length; i ++ )
			iconList[i] = ireg.registerIcon( this.getUnlocalizedName().replace( "item.", "" ) + "_" + i );
		for ( int i = 0; i < moonList.length; i ++ )
			moonList[i] = ireg.registerIcon( this.getUnlocalizedName().replace( "item.", "" ) + "_moon_" + i );
	}


	protected int getIconIndex ( ItemStack items, World world ) {
		int max = iconList.length;

		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			nbt = new NBTTagCompound();
			items.setTagCompound( nbt );
			initNBT( nbt, world );
		}

		double dir, vec, angle = nbt.getDouble("angle"), delta = nbt.getDouble("delta");

		if ( nbt.getLong( "time" ) != world.getWorldTime() ) {


			if ( !canReceiveTime( world ) )
				dir = itemRand.nextDouble();
			else
				dir = world.getCelestialAngle(1.0F);

			vec = dir - angle;
			while ( vec < -0.5D ) vec ++;
			while ( vec >= 0.5D ) vec --;

			delta = ( delta + vec / 10.0D ) * 0.8D;
			angle += delta;

			while ( angle < 0.0D ) angle ++;
			while ( angle >= 1.0D ) angle --;

			setNBT ( nbt, angle, delta );

		}


		return MathHelper.floor_double( angle * max ) % max;
	}


	protected int getMoonIndex( ItemStack items, World world ) {

		byte mp;

		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			nbt = new NBTTagCompound();
			items.setTagCompound( nbt );
			initNBT( nbt, world );
		}


		if ( nbt.getLong( "time" ) != world.getWorldTime() ) {

			if ( !canReceiveTime( world ) )
				nbt.setByte( "moonphase", (byte)( ( nbt.getByte( "moonphase" ) + 1 ) % 8 ) );
			else
				nbt.setByte( "moonphase", (byte)world.getMoonPhase() );

		}


		return nbt.getByte( "moonphase" );
	}


	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		if ( !world.isRemote ) {


			items.setItemDamage( items.getItemDamage() ^ 0x1 );
			if ( ( items.getItemDamage() & 0x1 ) != 0 ) {
				if ( isJapanese() )
					eplayer.addChatMessage("時報を[オフ]にしました");
				else
					eplayer.addChatMessage("Turned [Off] the time signal.");
			} else if ( isJapanese() )
				eplayer.addChatMessage("時報を[オン]にしました");
			else
				eplayer.addChatMessage("Turned [On] the time signal.");

		}

		return items;
	}


	protected void initNBT ( NBTTagCompound nbt, World world ) {
		setNBT( nbt, 0.0D, 0.0D );
		nbt.setByte( "moonphase", (byte)0 );
		nbt.setLong( "time", world.getWorldTime() );
	}

	protected void setNBT ( NBTTagCompound nbt, double angle, double delta ) {
		nbt.setDouble( "angle", angle );
		nbt.setDouble( "delta", delta );
	}


	/**
	 * 時刻を取得可能かどうかを取得します。
	 * ネザー等では使用不能になるようにする処理です。
	 * @param world		世界の情報。
	 * @return			可能ならtrueを返します。
	 */
	protected boolean canReceiveTime ( World world ) {
		return world.provider.isSurfaceWorld();
	}


	/**
	 * 時刻を適切な文字列に変換します。
	 * @param time	時刻データ。
	 * @param mode	変換モード。0=24h, 1=12h(AM/PM), 2=直接
	 * @return		変換された文字列。
	 */
	protected String getTime ( long time, int mode ) {

		time = time % 24000;

		switch ( mode ) {
		case 0:
			time = ( time >= 18000 ? time - 18000 : time + 6000 );
			return String.format("%02d:%02d", time / 1000, ( time % 1000 ) * 60 / 1000 );
		case 1:
			time = ( time >= 18000 ? time - 18000 : time + 6000 );
			return String.format("%02d:%02d %s", time / 1000 % 12, ( time % 1000 ) * 60 / 1000, ( time < 12000 ? "AM" : "PM" ) );
		case 2:
			return "" + time;
		default:
			return "" + time;
		}

	}


	/**
	 * 月齢として表示する文字列を取得します。
	 * @param items		対象となるアイテム。
	 * @param world		時刻取得用の世界データ。
	 * @return			変換された文字列。
	 */
	protected String getMoonPhase( ItemStack items, World world ) {

		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) {
			nbt = new NBTTagCompound();
			items.setTagCompound( nbt );
			initNBT( nbt, world );
		}

		if ( !isJapanese() ) {
			switch ( nbt.getByte( "moonphase" ) ) {
			case 0:
				return "Full moon";
			case 1:
				return "Waning gibbous moon";
			case 2:
				return "Third quarter moon";
			case 3:
				return "Waning crescent moon";
			case 4:
				return "New moon";
			case 5:
				return "Waxing crescent moon";
			case 6:
				return "First quarter moon";
			case 7:
				return "Waxing gibbous moon";
			default:
				return "Scarlet moon";
			}

		} else {
			switch ( nbt.getByte( "moonphase" ) ) {
			case 0:
				return "満月";
			case 1:
				return "十六夜";
			case 2:
				return "下弦";
			case 3:
				return "晦日";
			case 4:
				return "新月";
			case 5:
				return "三日月";
			case 6:
				return "上弦";
			case 7:
				return "待宵月";
			default:
				return "紅い月";
			}
		}
	}


	@Override
	public void onUpdate( ItemStack items, World world, Entity entity, int slot, boolean onhand ) {

		if ( world.isRemote ) {

			if ( entity instanceof EntityPlayer && ( items.getItemDamage() & 0x1 ) == 0  && canReceiveTime( world ) ) {

				long time = world.getWorldTime() % 24000;
				EntityPlayer eplayer = (EntityPlayer) entity;

				if ( isJapanese() ) {

					if ( time == 0 )
						eplayer.addChatMessage("[時報] 朝が来ました"); 
					if ( time == 6000 )
						eplayer.addChatMessage("[時報] 正午です"); 
					if ( time == 12000 )
						eplayer.addChatMessage("[時報] 夜が来ます");
					if ( time == 18000 )
						eplayer.addChatMessage("[時報] 真夜中です");

				} else {

					if ( time == 0 )
						eplayer.addChatMessage("[Time signal] Morning has come"); 
					if ( time == 6000 )
						eplayer.addChatMessage("[Time signal] It's noon"); 
					if ( time == 12000 )
						eplayer.addChatMessage("[Time signal] Night is coming"); 
					if ( time == 18000 )
						eplayer.addChatMessage("[Time signal] It's midnight");
				}
			}
		}


	}




	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}


	//checkme
	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {
		NBTTagCompound nbt = items.getTagCompound();

		if ( nbt == null ) list.add( "No Data" );
		else if ( !canReceiveTime( eplayer.worldObj ) )
			list.add( EnumChatFormatting.RED + "Unknown" );
		else
			list.add( getTime( eplayer.worldObj.getWorldTime(), dispmode ) + ( ( items.getItemDamage() & 0x1 ) == 0 ? " / " : " * " ) + getMoonPhase( items, eplayer.worldObj ) );
	}


	public boolean isJapanese() {
		return Minecraft.getMinecraft().gameSettings.language.equals( "ja_JP" );
	}


	@Override
	public Icon getIcon( ItemStack items, int pass ) {

		if ( pass == 0 )
			return iconList[ getIconIndex( items, Minecraft.getMinecraft().thePlayer.worldObj ) ];

		else
			return moonList[ getMoonIndex( items, Minecraft.getMinecraft().thePlayer.worldObj ) ];

	}

}
