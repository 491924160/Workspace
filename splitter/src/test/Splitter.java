/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.util.regex.*;
import java.util.*;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
 class Pair <T,S>
{
	T a;
	S b;
	Pair(T m,S n)
	{
		a=m;
		b=n;
	}
	Pair()
	{
		a=null;
		b=null;
	}
	T getT()
	{
		return a;
	}
	S getS()
	{
		return b;
	}
	public int hashCode()
	{
		return a.hashCode()+b.hashCode();
		
	}
	public boolean equals(Object exp)
	{
		if(!(exp instanceof Pair)) return false;
		else
		{
			Pair pair=(Pair)exp;
		if(pair.a.equals(this.a)&&pair.b.equals(this.b))
		return true;
		else return false;
		}
	}
	public String toString(){
		return a+" "+b;
	}		
}

public class Splitter {

	public static String calcPwd(String pwd) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(pwd.getBytes());
			String str = "";

			for (int i = 0; i < result.length; i++) {
				str += Integer.toHexString((int) ((result[i] >> 4) & 0xf));
				str += Integer.toHexString((int) (result[i] & 0xf));
			}

			return str;
		} catch (Exception e) {
			return "";
		}
	}

	public static void insert() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		ArrayList<String> idxAry = new ArrayList<>();

		try {
			while (true) {
				String s = br.readLine();
				if (s.length() <= 0) {
					break;
				}

				idxAry.add(s);
			}

			for (String str : idxAry) {
				String[] strs = str.split("\t");

				String insertStr = String.format(
						"INSERT INTO ADDRESS (AID, APT_NUM, STREET_ADDR, CITY, STATE, ZIP, PHONE_NO, Loc) \n"
						+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s',  SDO_GEOMETRY(\n"
						+ "      2001,\n"
						+ "      NULL,\n"
						+ "      SDO_POINT_TYPE('%s', '%s', NULL),\n"
						+ "      NULL,\n"
						+ "      NULL));",
						strs[0], strs[1], strs[2], strs[3], strs[4], strs[5], strs[6], strs[7], strs[8]);

				System.out.printf("%s\n\n", insertStr);
			}

		} catch (IOException ioe) {
		}
	}
	
	public static void genuserlink_v1() throws IOException{
		String path="e:\\data\\v1_weight.txt";  //权重文件的地址
		File file=new File(path),output=new File("e:\\data\\v1_sql.sql");
		BufferedReader reader=new BufferedReader(new FileReader(file));
		PrintStream ps=new PrintStream(output);
		String line=null;
		if(!output.exists())
			output.createNewFile();
		while((line=reader.readLine())!=null)
		{
			String[] array=line.split("\\s+");
			
			String sql=String.format("update user_weight set v1=v1+%s where usr_id1=\'%s\' and usr_id2=\'%s\'", array[2],array[0],array[1]);
			ps.println(sql);
			
		}
		ps.close();
		reader.close();
		
//		Connection conn=ConnectDB.openConnection();
//		conn.setAutoCommit(false);
	}
	
	public static void filter_user() throws IOException{
		File user_sql=new File("E:\\TDDOWNLOAD\\data-0.sql");
		BufferedReader reader=new BufferedReader(new FileReader(user_sql));
		File user_valid=new File("e:\\data\\user_valid.txt");
		PrintStream ps=new PrintStream(user_valid);
		String line=null;
		Pattern ptr=Pattern.compile("\\d+");
		while((line=reader.readLine())!=null){
			Matcher matcher=ptr.matcher(line);
			if(matcher.find())
				ps.println(matcher.group());
		}
		reader.close();
		ps.close();
	}
	
	public static void trim_entity_weight() throws IOException{                      //get the insertion sql for user link table
		File user_valid=new File("e:\\data\\user_valid.txt");
		BufferedReader reader=new BufferedReader(new FileReader(user_valid));
		File entity_weight=new File("e:\\data\\entity_weight.txt");
		File user_weight=new File("e:\\data\\user_weight.txt");
		File v1_weight = new File("e:\\data\\v1_weight.txt");
		if(!v1_weight.exists()){
			v1_weight.createNewFile();
		}
		PrintStream ps=new PrintStream(v1_weight);
		
		HashSet<String> set=new HashSet<String>();
		String line=reader.readLine();
		while(line!=null)
		{	
			if(!set.contains(line))
				set.add(line);
			line=reader.readLine();
		}
		reader.close();
		
		reader=new BufferedReader(new FileReader(entity_weight));
		line=reader.readLine();
		Pattern ptr=Pattern.compile("\\d+");
		File v2_weight=new File("e:\\data\\v2_weight.txt");
		if(!v2_weight.exists())
			v2_weight.createNewFile();
//		int count=0;
		while(line!=null){
			Matcher matcher=ptr.matcher(line);
			String id=null;
			String id_1=null;
			if(matcher.find())
				id=matcher.group();
			if(matcher.find())
				id_1=matcher.group();
			if(set.contains(id)&&set.contains(id_1))
			{
				ps.println(line.replace(":", "\t"));
			}
			line=reader.readLine();
		}
		reader.close();
		ps.close();
		
		reader=new BufferedReader(new FileReader(user_weight));
		ps=new PrintStream(v2_weight);
		line=reader.readLine();		
		while(line!=null){
			Matcher matcher=ptr.matcher(line);
			String id=null;
			String id_1=null;
			if(matcher.find())
				id=matcher.group();
			if(matcher.find())
				id_1=matcher.group();
			if(set.contains(id)&&set.contains(id_1))
			{
				ps.println(line.replace(":", "\t"));
			}
			line=reader.readLine();
		}
		reader.close();
		ps.close();
	}
	
	public static void insert_weight() throws IOException{
		File user_weight=new File("e:\\data\\total_user_weight.txt");
		BufferedReader reader=new BufferedReader(new FileReader(user_weight));
		File output_sql=new File("e:\\data\\insertion_weight.sql");
		if(!output_sql.exists())
			output_sql.createNewFile();
		HashSet<Pair> set=new HashSet<Pair>();
		PrintStream ps=new PrintStream(output_sql);
		Pattern ptr=Pattern.compile("\\d+");
		String line=null;
		while((line=reader.readLine())!=null){
			Matcher matcher=ptr.matcher(line);
			String[] user_id=new String[2];
			int count=0;
			while(matcher.find()&&count<2)
			{
				user_id[count++]=matcher.group();
			}
			Pair<String,String> pair=new Pair<String,String>(user_id[0],user_id[1]);
			if(!set.contains(pair))
			{
			String sql=String.format(
									"insert into user_weight"
									+"(usr_id1,usr_id2)"
									+"values("
									+" %s, %s)", 
									user_id[0],user_id[1]);
			set.add(pair);
			ps.println(sql);
			}
			else 
			{
				System.out.println(true);
				continue;
			}
		}
		reader.close();
		ps.close();
	}
	
//	public static void insert_userlink(){
//		Connection conn=ConnectDB.openConnection();
//		System.out.println(conn);
//		try{
//		Statement stmt=conn.createStatement();
//		conn.setAutoCommit(false);
//		File insert_sql=new File("e:\\data\\insertion_link.sql");
//		BufferedReader reader =new BufferedReader(new FileReader(insert_sql));
//		String line=null;
//		int count=0;
//		while((line=reader.readLine())!=null){
//			
//			stmt.executeUpdate(line);
//			System.out.println(count++);
//		}
//			
//		conn.commit();
//		conn.close();
//		reader.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//	}	
	
	public static void genuserlink_v2() throws IOException{
		String path="e:\\data\\v2_weight.txt";  //权重文件的地址
		File file=new File(path),output=new File("e:\\data\\v2_sql_reverse.sql");
		BufferedReader reader=new BufferedReader(new FileReader(file));
		PrintStream ps=new PrintStream(output);
		String line=null;
		if(!output.exists())
			output.createNewFile();
		
		
		
		while((line=reader.readLine())!=null)
		{
			String[] array=line.split("\\s+");		
			String sql = String.format("update user_weight set v2=v2+%s"+
										" where usr_id1=\'%s\' and usr_id2=\'%s\'", 
										array[2],array[1],array[0]);   //reverse the edge
			ps.println(sql);			
		}
		ps.close();
		reader.close();
	}
	
	public static void genupdate_sql ()throws IOException
	{
		genuserlink_v2();
		genuserlink_v1();
		
	}
	
	public static void sql_weight() throws IOException{
		String path="e:\\data\\total_weight.txt";  //权重文件的地址
		File file=new File(path),output=new File("e:\\data\\weight_sql.sql");
		BufferedReader reader=new BufferedReader(new FileReader(file));
		PrintStream ps=new PrintStream(output);
		String line=null;
		if(!output.exists())
			output.createNewFile();
		while((line=reader.readLine())!=null)
		{
			String[] array=line.split("\\s+");			
			String sql=String.format("insert into user_weight "
									+ "values(%s, %s, %s)",
									array[0],array[1],array[2]);
			ps.println(sql);
			
		}
		ps.close();
		reader.close();
	}
	
	public static void update_v1() throws IOException{
		Connection conn=ConnectDB.openConnection();
		File file_v1=new File("e:\\data\\v1_sql.sql");
		BufferedReader reader = new BufferedReader(new FileReader(file_v1));
		try{
		conn.setAutoCommit(false);
		Statement stmt=conn.createStatement();
		String line=null;
		int count=0;
		while((line=reader.readLine())!=null && count++<100){
			stmt.addBatch(line);
		}
		int[] array=stmt.executeBatch();
		for(int i :array){
			if(i==0){
				System.out.println("insertion error");
				conn.rollback();
				conn.close();
			}
		}
		conn.commit();
		conn.close();
		}catch(Exception e1){
			e1.printStackTrace();	
		}		
	}
	
	public static void update_v2() throws FileNotFoundException{
		Connection conn=ConnectDB.openConnection();
		File file_v2=new File("e:\\data\\v2_sql.sql");
		BufferedReader reader = new BufferedReader(new FileReader(file_v2));
		try{
		conn.setAutoCommit(false);
		Statement stmt=conn.createStatement();
		String line=null;
		int count=0;
		while((line=reader.readLine())!=null){
			stmt.addBatch(line);
			System.out.println(count++);
		}
		int[] array=stmt.executeBatch();
		for(int i :array)
		{
			if(i==0)
			{
				System.out.println("insertion error");
				conn.rollback();
				conn.close();
			}
		}
		conn.commit();
		conn.close();
		}catch(Exception e1)
		{
			e1.printStackTrace();
			
		}
		
	}
//
//	private static class LocationPair {
//
//		public String K;
//		public String V;
//
//		public LocationPair(String k, String v) {
//			K = k;
//			V = v;
//		}
//
//		public LocationPair() {
//
//		}
//	}

//	private static LocationPair HashLocation(String loc) {
//		try {
//			String[] AB = loc.split(",");
//			LocationPair lp = new LocationPair();
//
//			lp.V = String.format("%s|%s", AB[0].trim(), AB[1].trim());
//
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] result = md.digest(lp.V.getBytes());
//			lp.K = "";
//
//			for (int i = 0; i < result.length; i++) {
//				lp.K += Integer.toHexString((int) ((result[i] >> 4) & 0xf));
//				lp.K += Integer.toHexString((int) (result[i] & 0xf));
//			}
//
//			return lp;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	public static void getLocation() {
//		HashMap<String, String> locations = new HashMap<>();
//		int CountOfCoord = 0;
//		int CountOfAddr = 0;
//		int CountOfLine = 0;
//		try {
//			InputStreamReader isr = new FileReader("c:\\tmp\\valid-user.txt");
//			BufferedReader br = new BufferedReader(isr);
//			String TmpStr;
//			while ((TmpStr = br.readLine()) != null) {
//				CountOfLine++;
//
//				String[] Fields = TmpStr.split("\t");
//				if (Fields.length != 8) {
//					System.out.printf("field number incompleted when parsing " + Fields[7] + "\n");
//					continue;
//				}
//
//				if (Fields[7].matches(".*\\d+.*")) {
//					CountOfCoord++;
//					continue;
//				}
//
//				LocationPair lp = HashLocation(Fields[7]);
//				if (lp == null) {
//					System.out.printf("A error found when parsing " + Fields[7] + "\n");
//					continue;
//				}
//
//				if (!locations.containsKey(lp.K)) {
//					locations.put(lp.K, lp.V);
//				}
//
//				CountOfAddr++;
//			}
//
//			isr.close();
//		} catch (FileNotFoundException fnfe) {
//			System.err.println(fnfe.getMessage());
//		} catch (IOException ioe) {
//			System.err.println(ioe.getMessage());
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//
//		System.out.printf("total %d lines\n", CountOfLine);
//		System.out.printf("total %d users provide coordiates\n", CountOfCoord);
//		System.out.printf("total %d users provide addresses\n", CountOfAddr);
//		System.out.printf("total %d different entries\n", locations.size());
//
//		try {
//			OutputStreamWriter osw = new FileWriter("c:\\tmp\\string-location.txt");
//			BufferedWriter bw = new BufferedWriter(osw);
//
//			for (Map.Entry<String, String> kvp : locations.entrySet()) {
//				bw.write(String.format("%s\t%s\n", kvp.getKey().toString(), kvp.getValue().toString()));
//			}
//
//			bw.close();
//			osw.close();
//		} catch (IOException ioe) {
//			System.err.println(ioe.getMessage());
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//
//	}

	public static void Count(){
		File file = new File("e:\\data\\trimmed_tweet");
		File[] filelist = file.listFiles();
		int count_tweet  = 0, count_mention = 0;
		Pattern pattern = Pattern.compile("MentionedEntities:\\s*\\d+");
		for(File tweet : filelist){		
			try {
				BufferedReader reader = new BufferedReader(new FileReader(tweet));
				String line = null;
				while((line=reader.readLine())!=null){
					count_tweet++;
					Matcher matcher = pattern.matcher(line);
					if(matcher.find())
						count_mention++;
				}
				reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IOException e1){
				e1.printStackTrace();
			}
		}
		System.out.printf("tweet:%d mention:%d",count_tweet,count_mention);
	}

	public static void genSQLInsertion() {
		// read valid users
		try {
			InputStreamReader isr = new FileReader("c:\\tmp\\invalid-cleaned-user.txt");
			OutputStreamWriter osw = new FileWriter("c:\\tmp\\data.sql");
			BufferedReader br = new BufferedReader(isr);
			BufferedWriter bw = new BufferedWriter(osw);
			String TmpStr = null;
			while ((TmpStr = br.readLine()) != null) {
				String[] fields = TmpStr.split("\t");
				String sqlString = "";
				try {
					sqlString = String.format(
							"INSERT INTO USER_PROFILE "
							+ "(usr_id, usr_name, friend_count, follower_count, status_count, favorite_count, account_age, loc) "
							+ "values (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', "
							+ "SDO_GEOMETRY(2001, NULL, "
							+ "SDO_POINT_TYPE(\'%s\', \'%s\', NULL), "
							+ "NULL, NULL));\n",
							fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]);
				} catch (Exception e) {
					System.err.println(TmpStr);
				}

				bw.write(sqlString);
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addressInvalidUser() {
		HashMap<String, String> users = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("c:\\tmp\\valid-user.txt"));
			String TmpStr;

			while ((TmpStr = br.readLine()) != null) {
				String[] Fields = TmpStr.split("\t");
				users.put(Fields[0].trim(), Fields[7]);
			}

			br.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader("c:\\tmp\\invalid-user.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\tmp\\invalid-cleaned-user.txt"));
			String TmpStr;

			while ((TmpStr = br.readLine()) != null) {
				String[] Fields = TmpStr.split("\t");
				if (Fields.length != 8) {
					System.out.printf("field number incompleted when parsing " + Fields[7] + "\n");
					continue;
				}

				String LatLng = LocationCrawler.getLocationCoord(users.get(Fields[0].trim()));
				String NewLine = "";
				for (int i = 0; i < 7; i++) {
					NewLine += Fields[i] + "\t";
				}

				NewLine += LatLng.replace('|', '\t');
				bw.write(NewLine + "\n");

				System.out.println(NewLine);
				Thread.sleep(500);
			}

			br.close();
			bw.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	
	static class ConnectDB {
		public static String username = "";
		public static String pwd = "";
		
		public static Connection openConnection() {
			try {
				String driverName = "oracle.jdbc.driver.OracleDriver";
				Class.forName(driverName);
				String url = "jdbc:oracle:thin:@localhost:1521/pdborcl";	

				return DriverManager.getConnection(url, username, pwd);
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found");
				e.printStackTrace();
				return null;
			} catch (SQLException sqle) {
				System.out.println("Connection Failed");
				sqle.printStackTrace();
				return null;
			}

		}

		public static void closeConnection(Connection conn) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("connection closing error ");
			}
		}
	}

	public static void InsertNetwork() {
		Connection conn = ConnectDB.openConnection();
		if (conn == null) {
			System.err.println("cannot connect to database");
			System.exit(0);
		}
		try {
			conn.setAutoCommit(false);
			try {
				InputStreamReader isr = new FileReader("c:\\tmp\\valid-network.txt");
				BufferedReader br = new BufferedReader(isr);
				String TmpStr = null;
				int cnt = 0;
				int nousercnt = 0;
				Statement s = conn.createStatement();
				while ((TmpStr = br.readLine()) != null) {
					String[] edge = TmpStr.split("\t");
					String sqlString = String.format("insert into user_link (usr_id1, usr_id2) "
							+ "values (\'%s\', \'%s\')", edge[0], edge[1]);
					try {
						s.executeUpdate(sqlString);
						cnt++;
					} catch (SQLException e) {
						if (e.getErrorCode() == 2291)
							nousercnt++;
						else {
							System.err.printf("data %s cannot be insert: %s\n", TmpStr, e.getMessage());
							System.in.read();
						}
					}

					if (cnt % 1000 == 0) {
						conn.commit();
						System.out.printf("%d links has been inserted, %d links invalid\n", cnt, nousercnt);
					}
				}
				br.close();
				conn.close();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void check_valid() throws IOException{
		//File file = new File("e:\\data\\total_weight.txt");
		File valid=new File("e:\\data\\user_valid.txt");
		BufferedReader reader=new BufferedReader(new FileReader(valid));
		File weight_total = new File("e:\\data\\total_weight.txt");
		
		HashSet<String> set=new HashSet<String>();
		String line=reader.readLine();
		while(line!=null)
		{	
			if(!set.contains(line))
				set.add(line);
			line=reader.readLine();
		}
		reader.close();
		
		reader=new BufferedReader(new FileReader(weight_total));
		Pattern ptr=Pattern.compile("\\d++");
		while((line=reader.readLine())!=null){
			Matcher matcher=ptr.matcher(line);
			String id=null;
			String id_1=null;
			if(matcher.find())
				id=matcher.group();
			if(matcher.find())
				id_1=matcher.group();
			if(!(set.contains(id)&&set.contains(id_1)))
				System.out.println(true);
			
		}
		
	}

	public static void main(String[] args) throws IOException {
	
			// getLocation();
			// queryLocation();
			// cleanUser();
			// genSQLInsertion();
			//addressInvalidUser();
			ConnectDB.username = args[0];
			ConnectDB.pwd = args[1];
			//update_v2();
//			genupdate_sql();
			//insert_userlink();
			//sql_userlink();
			//genuserlink_v2();
			Count();
//			test();
			//update_v1();
			//check_valid();
			//insert_weight();
//			trim_entity_weight();
			//sql_weight();
		//sql_user_link();
			//InsertNetwork();
	//		genuserling_v2();
	//		genuserlink_v1();
			//insert_v1();
	//		filter_user();
	//		insert();
	//
	//		InputStreamReader isr = new InputStreamReader(Sys tem.in);
	//		BufferedReader br = new BufferedReader(isr);
	//
	//		ArrayList<String> idxAry = new ArrayList<String>();
	//
	//		try {
	//			while (true) {
	//				String s = br.readLine();
	//				if (s.contains("a")) {
	//					break;
	//				}
	//
	//				idxAry.add(s);
	//			}
	//
	//			for (String str : idxAry) {
	//				System.out.println(calcPwd(str.trim()));
	//			}
	//
	//		} catch (IOException ioe) {
	//
	//		}
			// TODO code application logic here
		/*	InputStreamReader isr = new InputStreamReader(System.in);
			 BufferedReader br = new BufferedReader(isr);
			
			 ArrayList<String> idxAry = new ArrayList<String>();
			 ArrayList<String> cAry = new ArrayList<String>();
			 try {
			 while (true) {
			 String s = br.readLine();
			 Matcher m = Pattern.compile("O\\d+").matcher(s);
						
			 if (m.lookingAt()) {
			 String idx = s.substring(0, m.end());
			 String content = s.substring(m.end() + 1);
						
			 idxAry.add(idx);
			 cAry.add(content);
			 } else break;
			 }
			
			 } catch (IOException ioe) {
				
			 }
			
			 for (int i = 0; i < idxAry.size(); i++) {
			 Pattern p = Pattern.compile("(\\(Pro\\d+,\\s*\\d+\\))");
			 Matcher m = p.matcher(cAry.get(i));
			 while (m.find()) {
			 for (int j = 0; j < m.groupCount(); j++) {
			 String tstr = m.group(j).substring(1);
			 tstr = tstr.substring(0, tstr.length() - 1);
			 String[] result = tstr.split(",");
			 System.out.printf("%s\t%s\t%s\n", idxAry.get(i), result[0], result[1]);
			 }
			 }
			 }	*/
		}

}
