package gyz_test;

import java.util.Random;
import java.math.*;

public class powernumber {
	static long recursivepower(int x,int n)

	{
		if(n==1) return x;
		if(n%2==0)
		{
			long i=recursivepower(x,n/2);
			return i*i;
		}
		else
		{
			long i=recursivepower(x,(n-1)/2);
			return i*i*x;
		}
	}
	static void binaryform(long x)
	{
		int i=32;
		if(x>=0)
		{
			System.out.print("0");
			while(i>10)
			{
				System.out.print((x&(1<<(i-1)))>>i-1);
				--i;
			}
				
		}
	}
	public static void main(String[]args){
		Random random=new Random();
		System.out.println(recursivepower(18,10));
		System.out.println(Math.pow(18, 10));
		System.out.println(recursivepower(18,1)%1000);
		int i=0,x=18,last=1;
		while(i++<10)
			last=(last*x%1000);
		System.out.println(last);
//		binaryform((long)Math.pow(2,31));
		int m=255;
		System.out.println(m|(1<<30));
		
	}
}
