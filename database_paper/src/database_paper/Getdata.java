package database_paper;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.AccountSettings;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
public class Getdata {
	
	public static void get_id() throws IOException
	{
		File user_tweet=new File("E:\\data\\trimmed_tweet");
		File[] file_list=user_tweet.listFiles();
		File current=null;
		BufferedReader reader=null;
		PrintStream ps=new PrintStream(new File("e:\\data\\id.txt"));
		Pattern ptr=Pattern.compile("ID:\\s*\\d+");
		Matcher matcher=null;
		int file_size=file_list.length;
		String line=null;
		String text=null;
		StringBuilder builder=new StringBuilder();
		
		
		for(int i=0;i<100;i++)
		{
			current=file_list[i];
			reader=new BufferedReader(new FileReader(current));
			while((line=reader.readLine())!=null){
				builder.append(line+"\n");
			}
			text=builder.toString();
			matcher=ptr.matcher(text);
			while(matcher.find())
			{
				ps.println(matcher.group().replace("ID: ", ""));
			}
		}
		
	}
public static void main(String[]args) throws IOException
{
	Twitter conn=Tweet_connect.connect();
	File id=new File("e:\\data\\id.txt");
	BufferedReader reader=new BufferedReader(new FileReader(id));
	File out=new File("e://data//geo.txt");
	PrintStream ps=new PrintStream(out);
	String line=null;
	Status sta=null;
	Long l=null;
	Place pl=null;
	User user=null;
	String location=null;
	String place="";
	GeoLocation[][] geo=null;
	try {
		while((line=reader.readLine())!=null)
		{
			l=Long.valueOf(line);
			sta=conn.showStatus(l);
			pl=sta.getPlace();
			pl=sta.getPlace();
			if(pl!=null)
			{
				System.out.println(pl.getGeometryCoordinates());
			}
			user=sta.getUser();
			location=user.getLocation();
			ps.println(l+"loc:"+location);
		}
		
	} catch (TwitterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
