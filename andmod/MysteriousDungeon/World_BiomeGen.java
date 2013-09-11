package andmod.MysteriousDungeon;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class World_BiomeGen extends BiomeGenBase {

	public World_BiomeGen( int biomeID ) {
		super( biomeID );

		setBiomeName( "Mysterious Dungeon" );
		setColor( 0x00FFFF );	//checkme
		setMinMaxHeight( -2.0F, 1.0F );	//checkme
		setTemperatureRainfall( 0.2F, 0.0F );	//checkme
		setDisableRain();
		topBlock = (byte)Block.stone.blockID;
		fillerBlock = (byte)Block.stone.blockID;
	}

	
	@Override
	public BiomeDecorator createBiomeDecorator() {
		return getModdedBiomeDecorator( new World_BiomeDecorator( this ) );
	}

	
	@Override
	public int getSkyColorByTemp(float par1) {
		return 0;//checkme
	}

	
	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4) {
		// TODO 自動生成されたメソッド・スタブ
		super.decorate(par1World, par2Random, par3, par4);
	}
	
	
	

}
