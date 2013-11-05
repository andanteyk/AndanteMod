package andmod.Gummi;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Block_Leaves extends BlockLeaves {

	protected ItemStack[] raredrops;
	private boolean hasOptifine;
	
	
	public Block_Leaves( int blockID ) {
		super( blockID );
		raredrops = new ItemStack[8];
		
		if ( FMLCommonHandler.instance().getSide() == Side.CLIENT ) {
			Minecraft mc = Minecraft.getMinecraft();
			
			try {
				System.out.println( "[Replaced Leaves] Optifine found; Rendering mode of Leaves : " + mc.gameSettings.getClass().getDeclaredField( "ofTrees" ).get( mc.gameSettings ).toString() );
				hasOptifine = true;
				
			} catch ( Exception e ) {
				System.out.println( "[Replaced Leaves] Optifine not found." );
				hasOptifine = false;
			}
			
			
			setGraphicsLevel();
			
		}
	
	}

	
	/**
	 * レアドロップするアイテムを指定します。
	 * @param id		0=樫、1=白樺、2=松、3=ジャングルからドロップします。スーパーレアドロップを指定する場合、+4してください。
	 * @param items		アイテム情報。
	 * @return			設定されたブロックを返します。
	 */
	public Block_Leaves setRareDrop ( int id, ItemStack items ) {
		raredrops[id] = items;
		return this;
	}
	
	
	//基本的にコピーですが、ちょこちょこ改変してあります。
	@Override
	public void dropBlockAsItemWithChance( World world, int bx, int by, int bz, int meta, float par6, int par7 ) {

		if ( !world.isRemote ) {
			int prob = 20;

			if ( ( meta & 3 ) == 3 )		//ジャングルの苗木を落としづらく
				prob = 40;

			if ( par7 > 0 ) {
				prob -= 2 << par7;

				if ( prob < 10 ) prob = 10;
			}


			if ( world.rand.nextInt( prob ) == 0 )
				dropBlockAsItem_do( world, bx, by, bz, new ItemStack( idDropped( meta, world.rand, par7 ), 1, damageDropped( meta ) ) );


			//以下、リンゴ処理＋α
			prob = 200;			//　確率を 1/n, つまり 1/200 にします

			if ( par7 > 0 ) {
				prob -= 10 << par7;

				if ( prob < 40 ) prob = 40;
			}



			for ( int i = 0; i < 4; i ++ )
				if ( raredrops[i] != null && ( meta & 3 ) == i && world.rand.nextInt( prob ) == 0 )
					dropBlockAsItem_do( world, bx, by, bz, raredrops[i].copy() );		//.copy()がないと不具合を起こします。


			prob = 3200;		//案外出るものですよ？
			if ( par7 > 0 ) {
				prob -= 160 << par7;

				if ( prob < 640 ) prob = 640;
			}


			for ( int i = 0; i < 4; i ++ )
				if ( raredrops[i + 4] != null && ( meta & 3 ) == i && world.rand.nextInt( prob ) == 0 )
					dropBlockAsItem_do( world, bx, by, bz, raredrops[i + 4].copy() );

		}
	}

	
	@SideOnly( Side.CLIENT )
	public void setGraphicsLevel() {
		
		Minecraft mc = Minecraft.getMinecraft();
		
		boolean settings = mc.gameSettings.fancyGraphics;
		if ( hasOptifine )
			//Optifine導入時のみ動作します
			try {
				int optiset = Integer.valueOf( mc.gameSettings.getClass().getDeclaredField( "ofTrees" ).get( mc.gameSettings ).toString() );

				if ( optiset != 0 )
					settings = optiset == 2 ;

			} catch ( Exception e ) {
			}

		setGraphicsLevel( settings );
	
	}


	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int par1, int par2 ) {

		setGraphicsLevel();
		
		return super.getIcon( par1, par2 );
	}


}
