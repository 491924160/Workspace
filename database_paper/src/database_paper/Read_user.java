package database_paper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read_user {
	static void modify_weight() throws IOException{
		File file=new File("e:\\data\\user_weight.txt");
		File file_1=new File("e:\\data\\modified_user_weight.txt");
		if(!file_1.exists())
			file_1.createNewFile();
		PrintStream ps=new PrintStream(file_1);
		BufferedReader reader=new BufferedReader(new FileReader(file));
		String line=reader.readLine();
		int count=0;
		while(line!=null)
		{
			
		ps.println(line.replace(":", " "));
		line=reader.readLine();
		}
	}
	static void check_existing_user() throws IOException
	{
		File file=new File("e:\\data\\user_weight.txt");
		File file_1=new File("e:\\data\\tweets");
		File file_2=new File("e:\\data\\existing_user_weight.txt");
		PrintStream ps=new PrintStream(file_2);
		if(!file_2.exists())
			file_2.createNewFile();
		File[] list=file_1.listFiles();
		HashSet<String> set=new HashSet<String>();
		for(int i=0;i<list.length;i++)
		{
			if(!set.contains(list[i].getName()))
				set.add(list[i].getName());
			//System.out.println(i);
		}
		Pattern pattern=Pattern.compile("\\d+");
		BufferedReader reader=new BufferedReader(new FileReader(file));
		String line=reader.readLine();
		int count_1=0;
		while(line!=null)
		{
			Matcher matcher=pattern.matcher(line);
			String[] user=new String[2];
			int count=0;
			while(matcher.find()&&count<2)
			{
				user[count]=matcher.group();
				count++;
			}
			if(set.contains(user[0])&&set.contains(user[1]))
				ps.println(line);	
			
			System.out.println(count_1++);
			line=reader.readLine();
		}
		
	}
	
public static void main(String[]args) throws IOException
{
	check_existing_user();
}
}
