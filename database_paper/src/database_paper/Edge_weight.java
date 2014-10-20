package database_paper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Edge_weight {
public static void user_tweet()
{
	File file=new File("E:\\data\\tweets");
	if(file.isDirectory())
	{
		File[] list=file.listFiles();
		File user_tweet=new File("E:\\data\\user_tweet.txt");
		try {
			FileOutputStream out=new FileOutputStream(user_tweet);
			PrintStream steam=new PrintStream(out);
			int i=0;
			while(i<list.length)
			{
				steam.println(list[i].getName());				
				i++;
			}
			steam.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public static void intersect_user() throws IOException
{
	File file=new File("E:\\data\\user_tweet.txt");
	File intersection=new File("E:\\data\\intersection_tweet_user.txt");
	PrintStream print=new PrintStream(new FileOutputStream(intersection));
	BufferedReader reader=new BufferedReader(new FileReader(file));
	HashMap<String,Integer> map=new HashMap<String,Integer>(20000);
	String name=null;
	
	while((name=reader.readLine())!=null)
	{
		map.put(name, 1);
	}
	
	File user=new File("E:\\data\\valid-user.txt");
	
	if(user.exists())
	{
		BufferedReader reader_1=new BufferedReader(new FileReader(user));
		String valid_user=null;
		Pattern pattern=Pattern.compile("^\\d+");
		
		Matcher matcher=null;
		String id=null;
		
		while((valid_user=reader_1.readLine())!=null)
		{
			matcher=pattern.matcher(valid_user);
			if(matcher.find())
			{
				id=matcher.group();
				//System.out.println(true);
				if(map.containsKey(id))
				{
					print.println(id);
				}
			}
		}
		
	}
	else
	{
		System.out.println("valid-user doesnt exist!");
	}
}
public static void tweet() throws IOException
{
	File total_tweet=new File("E:\\data\\trimmed_tweet");
	
	
	if(total_tweet.isDirectory())
	{
	File tweet=new File("E:\\data\\tweets");
	File[] tweets=tweet.listFiles();
	File file_1=null;
	File new_tweet=null;
	BufferedReader reader=null;
	PrintStream ps;
	int count=0;
	
	File user_valid=new File("e:\\data\\user_valid.txt");
	reader=new BufferedReader(new FileReader(user_valid));
	String line_1=null;
	HashSet<String> set=new HashSet<String>();
	while((line_1=reader.readLine())!=null){
		if(!set.contains(line_1))
			set.add(line_1);
	}
	reader.close();
	
	while(count<tweets.length)
	{
	file_1=tweets[count];
	if(set.contains(file_1.getName()))
	{
	reader=new BufferedReader(new FileReader(file_1));
	new_tweet=new File("e:\\data\\trimmed_tweet\\"+file_1.getName());
	ps=new PrintStream(new FileOutputStream(new_tweet,true));	
	Pattern regex=Pattern.compile("MentionedEntities:.+"),regex_1=Pattern.compile("ID:\\s*\\d+");
	Matcher matcher=null,matcher_1=null;	
	String line=null,text,file_name=file_1.getName();
	StringBuilder builder=new StringBuilder();
	
	while((line=reader.readLine())!=null)
	{
		builder.append(line+"\n");
	}
	
	text=builder.toString();
	matcher=regex.matcher(text);
	matcher_1=regex_1.matcher(text);
	while(matcher_1.find()&&matcher.find())
	{
		ps.println("USER:"+file_name+" "+matcher_1.group()+" "+matcher.group());
	}

	System.out.println(count);
	ps.close();
	reader.close();
	}
	count++;
	}
	}
	
}

public static void main(String[]args) throws IOException
{
	//System.out.println(Integer.MAX_VALUE);
	//intersect_user(); 
	tweet();
	//single_cal();
}
}
