package andmod.AndCore;


/**
 * XORShift 乱数ジェネレータ
 * @author Andante
 *
 */
public class RandomXor {

	public int[] seeds = new int[4]; 
	
	
	/**
	 * 乱数ジェネレータを初期化します。
	 * 現在時刻を種として初期化されます。
	 */
	public RandomXor() {
		this( System.nanoTime() );
	}
	
	/**
	 * 乱数ジェネレータを初期化します。
	 * @param seed	乱数の種。
	 */
	public RandomXor( int seed ) {
		
		for ( int i = 0; i < 4; i ++ )
			seeds[i] = seed = 1812433253 * ( seed ^ ( seed >> 30 ) ) + i;
	}
	
	/**
	 * 乱数ジェネレータを初期化します。
	 * @param seed	乱数の種。
	 */
	public RandomXor( long seed ) {
		this( (int)seed );
		
		for ( int i = 0; i < 4; i ++ )
			seeds[i] ^= seed >>> 32;
		
		if ( isZeroSeed() )
			initSeed();
	}
	
	
	protected void initSeed() {
		seeds[0] = 123456789;
		seeds[1] = 362436069;
		seeds[2] = 521288629;
		seeds[3] =  88675123;
	}
	
	
	protected boolean isZeroSeed() {
		return seeds[0] == 0 && seeds[1] == 0 && seeds[2] == 0 && seeds[3] == 0;
	}
	
	
	/**
	 * 次の乱数を求めます。範囲は -2^31 <= x < 2^31 です。
	 * @return	乱数値。
	 */
	public int next() {
		int t = seeds[0] ^ ( seeds[0] << 11 );
		seeds[0] = seeds[1]; seeds[1] = seeds[2]; seeds[2] = seeds[3];
		return seeds[3] = ( seeds[3] ^ ( seeds[3] >>> 19 ) ) ^ ( t ^ ( t >>> 8 ) );
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
	 * 次の乱数を求めます。範囲は 0.0 <= x < 1.0 です。
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
	 * 次の乱数を求めます。
	 * @param probability	trueになる確率。 0.0 - 1.0 の範囲で指定してください。
	 * @return	乱数値。
	 */
	public boolean nextBoolean( float probability ) {
		return nextFloat() < probability;
	}
	
	
	/**
	 * 次の乱数を求めます。
	 * @param probability	trueになる確率。 0.0 - 1.0 の範囲で指定してください。
	 * @return	乱数値。
	 */
	public boolean nextBoolean( double probability ) {
		return nextDouble() < probability;
	}
	
	
	/**
	 * 乱数を消費します。
	 */
	public void consume() {
		consume( nextInt( 64, 256 ) );
	}
	
	
	/**
	 * 乱数を指定した個数消費します。
	 * @param count		消費する個数。
	 */
	public void consume( int count ) {
		for ( int i = 0; i < count; i ++ )
			next();
	}

	
	/**
	 * 次の乱数を求めます。
	 * @param bytes		乱数を格納するbyte配列。
	 */
	public void nextBytes( byte[] bytes ) {
		
		int t = 0;
		
		for ( int i = 0; i < bytes.length; i ++ ) {
			if ( ( i & 3 ) == 0 )
				t = next();
			bytes[i] = (byte)( t & 0xFF );
			t >>>= 8;
		}

	}
	
	
	/**
	 * 次の乱数を求めます。nextIntよりも理論上確率的に正確です。
	 * 実装はRandom.nextIntを参考にしました。
	 * @param max	最大値。常に正である必要があります。
	 * @return		乱数値。
	 */
	public int nextIntStrict( int max ) {
		if ( max <= 0 )
			throw new IllegalArgumentException( "'max' must be positive." );
		
		int val, ret;
		do {
			val = next();
			ret = val % max;
		} while ( val - ret + ( max + 1 ) < 0 );	//maxで割り切れない末端をやり直します。
		
		return ret;
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
