package andmod.Personality;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterManager {

	public Twitter twitter;
	private RequestToken req = null;
	
	public TwitterManager() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled( true )
		.setOAuthConsumerKey( "SicvqGqyZ5nMElAB6MZyWA" )
		.setOAuthConsumerSecret( "arTgLw0bnde0XoX6o4wM9PstZxEhsaAQwsGb8UgysNw" );
		
		twitter = new TwitterFactory( cb.build() ).getInstance(); 
	}

	
	public void getFollowers( ArrayList<String> name ) {
		
		try {
			
			long myID = twitter.getId();
			long followersID[] = twitter.getFollowersIDs( -1 ).getIDs();
			long fid[] = new long[100];
			
			for ( int i = 0; i < followersID.length; i += 100 ) {
				
				for ( int j = 0; j < 100; j ++ ) {
					if ( i * 100 + j >= followersID.length )
						fid[j] = 0;
					else
						fid[j] = followersID[i * 100 + j];
				}
				
				ResponseList<User> followerData = twitter.lookupUsers( fid );
				for ( User user : followerData ) {
					
					Relationship relation = twitter.showFriendship( myID, user.getId() );
					if ( relation.isSourceFollowingTarget() )
						name.add( user.getName() );
				}
			}
			
		} catch ( TwitterException e ) {
			e.printStackTrace();
		}
		
	}
	
	
	public void openAuthWindow() {
		
		try {
			
			req = twitter.getOAuthRequestToken();
			Desktop desktop = Desktop.getDesktop();
			desktop.browse( URI.create( req.getAuthenticationURL() ) );
		
		} catch ( TwitterException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( IllegalStateException e ) {
		
		}
		
		
	}
	
	
	public void setAccessToken( String pin ) {
		
		try {
			if ( req == null )
				req = twitter.getOAuthRequestToken();
			
			AccessToken accessToken = twitter.getOAuthAccessToken( req, pin );
			twitter.setOAuthAccessToken( accessToken );
		
		} catch ( TwitterException e ) {

			e.printStackTrace();
		}
		
	}
	
	
	public boolean isEnabled() {
		return twitter.getAuthorization() != null && twitter.getAuthorization().isEnabled();
	}
}
