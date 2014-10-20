package gyz_test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.*;

public class test3 {
	static int loop=0;
	static int knackpack(int[] weight, int[] value, int maxweight,int index)
	{
		loop++;
		int total_value_with=0,total_value_without=0;
		if(maxweight==0||index>=weight.length) return 0;
		if(weight[index]>maxweight)	
			return knackpack(weight,value,maxweight,++index);
		else{
			total_value_with=value[index]+knackpack(weight,value,maxweight-weight[index],index+1);
			System.out.println(total_value_with);
			total_value_without=knackpack(weight,value,maxweight,index+1);
		}
		return ((total_value_with>total_value_without)?total_value_with:total_value_without);		
	}
	public static void main(String[] args)
{
	int[]x=new int[101];
	Random random=new Random();
	int m=random.nextInt(123);
	int i=0;
	while(i<101)
	{
		x[i++]=random.nextInt(100000);
	}
//	gyz_test2.mergesort(x, 0, x.length-1);
//	System.out.print(Arrays.toString(x));
	int[] weight={5,2,8,1,2,5,2};
	int[] value={6,8,12,5,10,10,3};
	int maxweight=15;
	int amount=knackpack(weight,value,maxweight,0);
	System.out.print(amount);
	
	
}
}
