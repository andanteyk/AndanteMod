package andmod.ExCompass;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class Item_Compass extends Item {


	@SideOnly( Side.CLIENT )
	protected Icon[] iconList;


	public Item_Compass( int itemID ) {
		super( itemID );

		setMaxStackSize( 1 );
	}


	@Override
	@SideOnly( Side.CLIENT )
	public void registerIcons( IconRegister ireg ) {
		//super.registerIcons( ireg );

		iconList = new Icon[32];

		for ( int i = 0; i < iconList.length; i ++ )
			iconList[i] = ireg.registerIcon( getUnlocalizedName().replace( "item.", "" ) + "_" + i );

	}


	@Override
	public ItemStack onItemRightClick( ItemStack items, World world, EntityPlayer eplayer ) {

		if ( !world.isRemote && eplayer.isSneaking() ) {

			NBTTagCompound nbt = items.getTagCompound();

			if ( nbt == null ) {
				nbt = new NBTTagCompound();
				items.setTagCompound( nbt );
				initNBT( nbt );
			}

			setNBT( nbt, (int)Math.floor( eplayer.posX ), (int)Math.floor( eplayer.posY ), (int)Math.floor( eplayer.posZ ), eplayer.worldObj.provider.dimensionId );
		}


		return items;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation( ItemStack items, EntityPlayer eplayer, List list, boolean par4 ) {
		NBTTagCompound nbt = items.getTagCompound();

		if ( nbt == null ) list.add( "No Data" );
		else {

			int px = nbt.getInteger("px"), py = nbt.getInteger("py"), pz = nbt.getInteger("pz");
			double bx = ( px + 0.5 - eplayer.posX ), by = ( py + 0.5 - eplayer.posY ), bz = ( pz + 0.5 - eplayer.posZ );
			int dim = nbt.getInteger("dim");


			list.add( ( isSameWorld( items, eplayer.worldObj ) ? "" : EnumChatFormatting.RED ) +
					"(" + px + ", " + py + ", " + pz + ")" +
					( isSameWorld( items, eplayer.worldObj ) ? "; " + String.format( "%.2f", (float)Math.sqrt( ( bx * bx ) + ( by * by ) + ( bz * bz ) ) )  + " m" : "" ) );
			list.add( ( isSameWorld( items, eplayer.worldObj ) ? "" : EnumChatFormatting.RED ) +
					"Dimension: " + dim );
			
			//list.add( "angle=" + nbt.getDouble("angle") + ", delta=" + nbt.getDouble("delta") );
		}
	}


	protected void initNBT( NBTTagCompound nbt ) {
		setNBT( nbt, 0, 64, 0, 0, 0, 0 );
	}

	protected void initNBT( NBTTagCompound nbt, World world ) {
		initNBT( nbt );
		nbt.setLong( "time", world.getWorldTime() );
	}

	protected void setNBT( NBTTagCompound nbt, int px, int py, int pz, int dim ) {
		nbt.setInteger( "px",  px );
		nbt.setInteger( "py",  py );
		nbt.setInteger( "pz",  pz );
		nbt.setInteger( "dim", dim );
	}

	protected void setNBT( NBTTagCompound nbt, int px, int py, int pz, int dim, int angle, int delta ) {
		this.setNBT( nbt, px, py, pz, dim );
		nbt.setDouble( "angle", angle );
		nbt.setDouble( "delta", delta );
	}


	protected boolean isSameWorld( ItemStack items, World world ) {

		NBTTagCompound nbt = items.getTagCompound();

		return nbt != null && world.provider.dimensionId == nbt.getInteger( "dim" );
	}


	@SideOnly( Side.CLIENT )
	protected int getIconIndex( ItemStack items, World world, double px, double pz, double yaw ) {
		int max = iconList.length;

		NBTTagCompound nbt = items.getTagCompound();
		if ( nbt == null ) return 0;

		double dir, vec, angle = nbt.getDouble("angle"), delta = nbt.getDouble("delta");


		if ( nbt.getLong( "time" ) != world.getWorldTime() ) {

			nbt.setLong( "time", world.getWorldTime() );

			if ( world.provider.dimensionId != nbt.getInteger("dim") )
				dir = Math.random() * 360.0;
			else
				dir = Math.atan2( nbt.getInteger("pz") + 0.5 - pz, nbt.getInteger("px") + 0.5 - px ) * 180.0 / Math.PI - yaw + 90.0;


			vec = dir - angle;
			while ( vec < -180.0 ) vec += 360.0;
			while ( vec >= 180.0 ) vec -= 360.0;

			if ( vec >  6.0 ) vec =  6.0;
			if ( vec < -6.0 ) vec = -6.0;


			delta = ( delta + vec ) * 0.8;
			angle += delta;

			while ( angle < 0.0 ) angle += 360.0;
			while ( angle >= 360.0 ) angle -= 360.0;

			nbt.setDouble( "angle", angle );
			nbt.setDouble( "delta", delta );

		}


		//eplayer.addChatMessage( "icon:" + ( MathHelper.floor_double( angle * max / 360.0 ) % max ) );
		return MathHelper.floor_double( angle * max / 360.0 ) % max;
	}


	@SideOnly( Side.CLIENT )
	@Override
	public Icon getIcon( ItemStack items, int pass ) {

		Minecraft mc = Minecraft.getMinecraft();

		if ( pass == 0 ) {
			if ( items.isOnItemFrame() )
				return iconList[ getIconIndex( items, mc.theWorld, items.getItemFrame().posX, items.getItemFrame().posZ, -items.getItemFrame().rotationYaw ) ];
			else {

				/*
				ChunkCoordinates cc = mc.thePlayer.getBedLocation( mc.theWorld.provider.dimensionId );
				if ( cc != null ) {

					ChunkCoordinates ccs = mc.thePlayer.verifyRespawnCoordinates( mc.theWorld, cc, true );
					if ( ccs != null )
						return iconList[ MathHelper.clamp_int( MathHelper.floor_double( Math.atan2( ccs.posZ + 0.5 - mc.thePlayer.posZ, ccs.posX + 0.5 - mc.thePlayer.posX ) * 180.0 / Math.PI * 32 / 360.0 % 32 ), 0, 31 ) ];
					else
						return iconList[ MathHelper.clamp_int( MathHelper.floor_double( Math.atan2( cc.posZ + 0.5 - mc.thePlayer.posZ, cc.posX + 0.5 - mc.thePlayer.posX ) * 180.0 / Math.PI * 32 / 360.0 % 32 ), 0, 31 ) ];

				} else */
					return iconList[ getIconIndex( items, mc.theWorld, mc.thePlayer.posX, mc.thePlayer.posZ, mc.thePlayer.rotationYaw ) ];
			}
		} else {
			return super.getIcon( items, pass );	//呼ばれない…　はずです

		}

	}


	//これがないと正しく描画されません！
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}


	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}
}
