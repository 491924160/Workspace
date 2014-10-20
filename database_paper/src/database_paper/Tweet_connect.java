package database_paper;
import twitter4j.conf.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

class Tweet_connect {
	private static String ConsumerKey="KoDk9ijvoP6qiDNSOTz5MyAsx";
	private static String ConsumerSecret="cMEnwbTduDccj4sNJKGyLrFY3EB4QgEgBCQzt6Tq18QaHGRfUD";
	private static String AccessToken="2434696190-XuKMfKSNJTo2eYb288j4bARWXga3ehwnvmfKXNk";
	private static String AccessTokenSecret="lH4Br5EbmOFfcx7NVft32WUAiPOjrnXqRsei1ZmNPdBOs";
	public static Twitter connect()
	{
		ConfigurationBuilder builder=new ConfigurationBuilder();
		builder.setDebugEnabled(true)
		.setOAuthConsumerKey(ConsumerKey)
		.setOAuthConsumerSecret(ConsumerSecret)
		.setOAuthAccessToken(AccessToken)
		.setOAuthAccessTokenSecret(AccessTokenSecret);
		
		TwitterFactory factory=new TwitterFactory(builder.build());
		Twitter conn=factory.getInstance();
		return conn;
	}
}
