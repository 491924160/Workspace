package gyz_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class quicksort {
	static void quicksort(int[] array,int low,int high)
	{
		if(high<=low ) return ;
		int pivot=array[low],i=low;
		for(int index=low+1;index<high;++index)
		{
		if(array[index]<pivot)
		{
			i++;
			int temp=array[i];
			array[i]=array[index];
			array[index]=temp;
		}
		}
		int temp=array[i];
		array[low]=array[i];
		array[i]=pivot;
		quicksort(array,low,i);
		quicksort(array,i+1,high);
		
	}
	public static void main(String[]args)
	{
	Random random=new Random(40);
	int[] array=new int[100];
	for(int i=0;i<100;i++)
		array[i]=random.nextInt(200);
	int[]x={10,5,7,12,88,1};
	int[] gyz={123,22};
	quicksort(array,0,array.length);
	System.out.print(Arrays.toString(array));
	HashMap<int[],String> map=new HashMap<int[],String>();
	
	}
}
