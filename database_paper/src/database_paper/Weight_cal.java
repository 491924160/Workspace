package database_paper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weight_cal {

	public static void weight_cal() throws IOException {
		File weight_file = new File("E:\\data\\user_weight.txt");
		File user_tweet = new File("E:\\data\\trimmed_tweet");
		File entity_weight = new File("e:\\data\\entity_weight.txt");
		if (!entity_weight.exists()) {
			entity_weight.createNewFile();
		}
		File single_tweet = null;
//		PrintStream ps = new PrintStream(weight_file);
		BufferedReader reader = null;
		String line = null, user_id, entity;
		Pattern ptr_2;// ptr_0,ptr_1,
		Matcher matcher_0;// ,matcher_1,matcher_2;
		MatchResult result = null;
//		HashMap<Pair<String, String>, Integer> user_map = new HashMap<Pair<String, String>, Integer>(
//				2000000);
		 HashMap<Pair<String,String>,Integer> user_map_1=new
		 HashMap<Pair<String,String>,Integer>(2000000);
		ArrayList<String> ids = new ArrayList<String>();

		if (!weight_file.exists())
			weight_file.createNewFile();

		int count = 0;
		File[] list = user_tweet.listFiles();
		int file_size = list.length;

		ptr_2 = Pattern.compile("\\d+");
		;
		while (count < file_size) {
			reader = new BufferedReader(new FileReader(list[count++]));
			while ((line = reader.readLine()) != null) {
				matcher_0 = ptr_2.matcher(line);
				int i = 0;
				while (matcher_0.find()) {
					entity = matcher_0.group();
					if (i != 1)
						ids.add(entity);
					i++;
				}
				if (ids.size() >= 2) {
					int size = ids.size();
//					for (int k = 1; k < size; k++) {
//						Pair pair = new Pair(ids.get(0), ids.get(k));
//						Integer value = null;
//						if (user_map.containsKey(pair)) {
//							value = user_map.get(pair);
//							user_map.put(pair, new Integer(value + 1));
//						} else
//							user_map.put(pair, 1);
//					}
					 if(size>=3)
					 {
					 for(int h=1;h<size;h++)
					 for(int l=h+1;l<size;l++)
					 {
					 Pair pair=new Pair(ids.get(h),ids.get(l));
					 Pair pair_1=new Pair(ids.get(l),ids.get(h));
					 if(user_map_1.containsKey(pair))
					 {
					 Integer value=null;
					 value=user_map_1.get(pair);
					 user_map_1.put(pair, new Integer(value+1));
					 }
					 else user_map_1.put(pair, 1);
					 if(user_map_1.containsKey(pair_1))
					 {
					 Integer value=null;
					 value=user_map_1.get(pair_1);
					 user_map_1.put(pair_1, new Integer(value+1));
					 }
					 else user_map_1.put(pair_1, 1);
					 }
					 }
				}
				ids.clear();
			}
			System.out.println(count);
			reader.close();
		}

//		Set<Entry<Pair<String, String>, Integer>> set_1 = user_map.entrySet();
//		Iterator<Entry<Pair<String, String>, Integer>> iter_1 = set_1
//				.iterator();
//		Entry<Pair<String, String>, Integer> i;
//		while (iter_1.hasNext()) {
//			i = iter_1.next();
//			ps.println(i.getKey() + ":" + i.getValue());
//		}
//		ps.close();
//
//		//
		 PrintStream ps=new PrintStream(entity_weight);
		 Set<Entry<Pair<String, String>, Integer>>
		 set_2=user_map_1.entrySet();
		 Iterator<Entry<Pair<String, String>, Integer>>
		 iter_2=set_2.iterator();
		 Entry<Pair<String, String>, Integer> k;
		 while(iter_2.hasNext())
		 {
		 k=iter_2.next();
		 ps.println(k.getKey()+":"+k.getValue());
		 }
		 ps.close();

	}

	public static void main(String[] args) throws IOException {
		weight_cal();
	}
}
