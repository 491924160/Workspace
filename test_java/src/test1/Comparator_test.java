package test1;

import java.util.Comparator;
import java.util.PriorityQueue;

class Point1{
	int x;
	Point1(int x){
		this.x = x;
	}
}
class PointComparator implements Comparator<Point1>{

	@Override
	public int compare(Point1 o1, Point1 o2) {
		// TODO Auto-generated method stub
		if(o1.x > o2.x)
			return 1;
		else if( o1.x < o2.x)
			return -1;
		else return 0;
	}
}
public class Comparator_test {
	public static void main(String[]args){
		PriorityQueue<Point1> pq = new PriorityQueue<Point1>(10, new PointComparator());
		pq.add(new Point1(10));
		pq.add(new Point1(5));
		pq.add(new Point1(-1));
		while(!pq.isEmpty())
			System.out.println(pq.poll().x);
	}
}
