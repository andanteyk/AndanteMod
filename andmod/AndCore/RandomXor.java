package andmod.AndCore;

import java.util.Date;

/**
 * 乱数ジェネレータ
 * @author Andante
 *
 */
public class RandomXor {

	public int x, y, z, w; 
	
	
	/**
	 * 乱数ジェネレータを初期化します。
	 * 現在時刻を種として初期化されます。
	 */
	public RandomXor() {
		Date date = new Date();
		initSeed();
		
		x ^= (int)( date.getTime() >>> 32L );
		w ^= (int)( date.getTime() & 0xFFFFFFFFL );
	}
	
	/**
	 * 乱数ジェネレータを初期化します。
	 * @param seed	乱数の種。
	 */
	public RandomXor( int seed ) {
		initSeed();
		
		w ^= seed;
		
		/*	//memo
		for ( int i = 0; i < 4; i ++ )
			seeds[i] = seed = 1812433253 * ( seed ^ ( seed >> 30 ) ) + i;
		*/
	}
	
	/**
	 * 乱数ジェネレータを初期化します。
	 * @param seed	乱数の種。
	 */
	public RandomXor( long seed ) {
		initSeed();
		
		x ^= (int)( seed >>> 32 );
		w ^= (int)( seed & 0xFFFFFFFFL );
	}
	
	
	protected void initSeed() {
		x = 123456789;
		y = 362436069;
		z = 521288629;
		w =  88675123;
	}
	
	
	/**
	 * 次の乱数を求めます。範囲は -2^31 <= x < 2^31 です。
	 * @return	乱数値。
	 */
	public int next() {
		int t = x ^ ( x << 11 );
		x = y; y = z; z = w;
		return w = ( w ^ ( w >>> 19 ) ) ^ ( t ^ ( t >>> 8 ) );
	}
	
	
	/**
	 * 次の乱数を求めます。範囲は 0 <= x < max です。
	 * @param max	最大値。乱数には含まれません。
	 * @return		乱数値。
	 */
	public int nextInt( int max ) {
		return (int)( nextDouble() * max );
	}
	
	
	/**
	 * 次の乱数を求めます。範囲は min <= x <= max です。
	 * @param min	最小値。
	 * @param max	最大値。
	 * @return		乱数値。
	 */
	public int nextInt( int min, int max ) {
		return min + (int)( nextDouble() * ( max - min + 1 ) );
	}
	
	
	/**
	 * 次の乱数を求めます。範囲は 0.0 <= x < 1.0 です。多分。
	 * @return	乱数値。
	 */
	public double nextDouble() {
		return next() / 4294967296.0 + 0.5;
	}
	
	
	/**
	 * 次の乱数を求めます。範囲は 0.0 <= x < 1.0 です。
	 * @return	乱数値。
	 */
	public float nextFloat() {
		return (float)nextDouble();
	}
	
	
	/**
	 * 次の乱数を求めます。
	 * @return	乱数値。
	 */
	public boolean nextBoolean() {
		return next() >= 0;
	}
	
	
	/**
	 * 乱数を指定した個数消費します。
	 * @param count		消費する個数。
	 */
	public void consume( int count ) {
		for ( int i = 0; i < count; i ++ )
			next();
	}

	
	/*	//for test!
	public void print() {
		File file = new File( "test.txt" );
		try {
			PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter( file ) ) );
			
			for ( int i = 0; i < 1024; i ++ )
				pw.println( nextInt( 100, 1000 ) );
			
			pw.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	
	}
	*/
}
