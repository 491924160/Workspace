package gyz_test;

import java.util.Random;

class Location
{
	double x,y;
	Location(double x,double y)
	{
		this.x=x;
		this.y=y;
	}
	void move(int[]k)
	{
		x=x+(double)k[0];
		y=y+(double)k[1];
	}
	void getLoc()
	{
		System.out.print(x+"\t"+y);
	}
}
class Direction
{
	int pt;
	Direction(int i)
	{
		pt=i;
	}
	void move(Location loc)
	{
		switch(pt)
		{
		case 0:++loc.x;break;
		case 1:--loc.x;break;
		case 2:++loc.y;break;
		case 3:--loc.y;break;
		}
	}
	
}
class field
{
	private Location loc;
	private Drunk drunk;
	field(Location loc,Drunk drunk){
		this.loc=loc;
		this.drunk=drunk;
	}
	void move(Direction dir){
		dir.move(this.loc);	
		
	}
	Location getLoc()
	{
		return this.loc;
	}
	Drunk gerDrunk()
	{
		return drunk;
	}
}
class Drunk
{
	private String name;
	Random random;
	Drunk(String id){
	name=id;	
	}
	void move(field area,int time){
		while(time>0){
			Direction dir=new Direction(random.nextInt(4));
			area.move(dir );
			--time;
		}
	}
	
}
public class randomwalk {
	static void performtrial(Drunk drunk,int times,field area)
	{
	drunk.move(area, times);
	area.getLoc().getLoc();
	}
	public static void main(){
		Drunk drunk=new Drunk("gyz");
		field area=new field(new Location(0,0),drunk);
		performtrial(drunk,100,area);
	}

}
