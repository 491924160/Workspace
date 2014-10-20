package gyz_test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.math.*;


public class gyz_test2 {
//byte[] x={01,02,03};
//ByteArrayInputStream steam=new ByteArrayInputStream(x);
	static void hyp()
	{
		try
		{double height,base;
		Scanner scanner=new Scanner(System.in);
		System.out.print("input the height of triangular:");
		height=scanner.nextDouble();
		System.out.print("input the base of triangular:");
		base=scanner.nextDouble();
		double hyp=Math.sqrt(height*height+base*base);
		System.out.print(hyp);
		scanner.close();
		}
		catch(Exception e)
		{
			System.out.println("error");
		}finally
		
		{
			hyp();
		}
		
		
	}
	static void exception() throws Exception
	{
		throw new Exception();
	}
	static void bisearch(int[]x,int a)
	{
		int low=0,high=x.length-1,mid=(low+high)/2;
		while(high-low>=2){
		if(a==x[mid]) 
			{
			System.out.print("found"+mid);
			return;
			}
		 if(a<x[mid])
		{
			high=mid-1;
			mid=(low+high)/2;
		}
		else
		{
			low=mid+1;
			mid=(low+high)/2;
		}
		}
		if(x[low]==a) System.out.print("found"+low);
		else if(x[high]==a) System.out.print("found"+high);
		else System.out.print("not found");		
	}
	static void merge(int[]x,int low,int high,int mid)
	{
		int[] result=new int[high-low+1];
		int i=0, k=mid+1,m=low;
		while(mid-m>=0&&high-k>=0)
		{
			if(x[m]>x[k])
			{
				result[i++]=x[k];
						k++;
			}
			else
			{
				result[i++]=x[m];
				m++;
			}
		}
		while(m<=mid)
				result[i++]=x[m++];	
		while(k<=high)
				result[i++]=x[k++];
		for(int count=0;count<result.length;count++)
			x[low++]=result[count];		
	}
	static void mergesort(int[]x,int low,int high)
	{
		int mid=(low+high)/2;
		if(high-low<2)
		{
			if(x[high]<x[low])
			{
			int a=x[high];
			x[high]=x[low];
			x[low]=a;
			}
		}
		else{
			mergesort(x,low,mid);
			mergesort(x,mid+1,high);
			merge(x,low,high,mid);
		}
	}
	public static void main(String[] args)
	{
		//Systm.out.print(list[0]);
		List<String> count=new ArrayList<String>();
		List<String> count1=new ArrayList<String>();
		count1=count;
		count.add("adasdf");
		count.add("asdfasdf");
		Iterator<String> iter=count.iterator();
	    count.set(1, "element");
	    int i=0;
		while(iter.hasNext())
		{
	       
			System.out.println(iter.next());
			System.out.println(count1.get(i++));
		}
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("c", 23);
		boolean x=map.containsKey("c");
		if(x)
			System.out.println("yes");
		//hyp();
//	    try
//	    {
//	    	exception();
//	    }catch(Exception e)
//	    {
//	    	System.out.print("error");
//	    }
		int[] array={1,5,6,8,9,11,23,45,67,89,123,456};
		//bisearch(array,123);
		int[] array1={213,42,16,7,343,86,123,23,236,896,1,2,3,4,5};
		mergesort(array1,0,array1.length-1);
		System.out.print(Arrays.toString(array1));
	}
}
