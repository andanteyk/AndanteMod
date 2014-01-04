package andmod.Personality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
		modid	= "AndanteMod_Personality",
		name	= "Personality",
		version	= "1.6.2.0"
		)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false
		)


public class AndMod_Personality {

	public static final String ObjectHeader = "Personality:";
	
	private Event_Naming ename;
	private boolean enabled = true;
	
	
	@Mod.EventHandler
	public void preInit( FMLPreInitializationEvent event ) {
		
		ename = new Event_Naming();
		String pin = "";
		 
		Configuration cfg = new Configuration( event.getSuggestedConfigurationFile() );
		Property prop;
		try	{
			cfg.load();
			prop = cfg.get( "General", "alwaysRenderNameTag", false );
			prop.comment = "nothing";
			ename.alwaysRenderNameTag = prop.getBoolean( false );
			
			prop = cfg.get( "General", "enabled", enabled );
			prop.comment = "enabled";
			enabled = prop.getBoolean( enabled );
			
			prop = cfg.get( "Twitter", "PIN", pin );
			prop.comment = "PIN";
			pin = prop.getString();
			
			
			
		} catch ( Exception e ) {
			FMLLog.log( Level.SEVERE, e, "AndMod_" + ObjectHeader + "Error has occured." );
		} finally {	cfg.save();	}

		
		//*/
		try {
			String parser = ",";
			File file = new File( event.getModConfigurationDirectory().getAbsolutePath() + "\\AndanteMod_Personality_Nametable.txt" );
			if ( !file.exists() ) {
				file.createNewFile();
				
				PrintWriter writer = new PrintWriter( new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file ), "UTF-8" ) ) );
				writer.println( "#1行に1つずつ名前を入力してください。" );
				writer.close();
			}
			
			else {
				BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( file ), "UTF-8" ) );
				String name;
				while ( ( name = reader.readLine() ) != null ) {
					/*/
					name = name.trim();
					if ( name.startsWith( "#" ) ) continue;
					ename.addName( name );
					/*/
					if ( name.startsWith( "#" ) ) continue;
					for ( String ret : name.split( parser ) ) {
						ret.trim();
						if ( ret.length() > 0 )
							ename.addName( ret );
					}
						
					//*/
				}
				
				reader.close();
			}
		} catch ( FileNotFoundException e ) {
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		/*/
			
		TwitterManager tm = new TwitterManager(); //"825721321-0tJqhdPpagB5qS7TFfiXuhvwOVbXHURjT7LgLWMx", "9Xax06vcu8dpEaNesE7O5CAd2lnPyLHfJbOKyjqKUyDqB" );
		
		if ( pin.length() == 0 ) {
			tm.openAuthWindow();
		} else {
			tm.setAccessToken( pin );
		}
		
		if ( tm.isEnabled() )
			tm.getFollowers( ename.nameList );
		
		//*/
	}
	
	@Mod.EventHandler
	public void init( FMLInitializationEvent event ) {
		
		if ( enabled && ename.nameList.size() > 0 )
			MinecraftForge.EVENT_BUS.register( ename );
	
	}
	
}
