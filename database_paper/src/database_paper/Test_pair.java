package database_paper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class Test_pair {
public static void main(String[]args)
{
	Pair t=new Pair("sdf","sdf");
	Pair t1=new Pair("sdf","sdf");
	System.out.println(new String("asdf").equals(new String("asdf")));
	Pair t2=new Pair("3","4");
	HashMap<Pair<String,String>,Integer> user_map=new HashMap<Pair<String,String>,Integer>(2000000);
	user_map.put(t, 1);
	user_map.put(t2, 1);
	//System.out.println(t.hashCode()==t1.hashCode());
	if(user_map.containsKey(t1))
	{
		Integer i=user_map.get(t1);
		user_map.put(t1, new Integer(i+1));
	}
	
	Set<Entry<Pair<String, String>, Integer>> set_1=user_map.entrySet();
	Iterator<Entry<Pair<String, String>, Integer>> iter_1=set_1.iterator();
	while(iter_1.hasNext())
	{
		 Entry<Pair<String, String>, Integer> i=iter_1.next();
		 if(i.getValue()>=1)
		 System.out.println(i.getKey()+":"+i.getValue());
	}
}
}
