package gyz_test;
class gyz11
{
	int i;
}
class gyz_p
{
	public int i=5;
	void set(int i)
	{
		this.i=i;
		System.out.print(this.i);
	}
}
class gyz_y extends gyz_p
{
	private int i=50;
	private void abc(){
		System.out.print("haha");
	}
	void set(int i)
	{
		System.out.print(super.i);
		System.out.print(this.i);
		abc();
	}
}
public class test4 {
	static void haha(gyz_p abc){
		abc.set(10);
	}
	public static void main(String[]args)
	{
//		gyz11 abc=new gyz11();
//		gyz11 bcd=abc;
//		bcd.i=10;
//		System.out.println(bcd.i);
//		abc.i=20;
//		System.out.print(abc.i+"\t"+bcd.i);
//		gyz_p p=new gyz_p();
//	//	p.set(19);
		gyz_p Gyz=new gyz_y();
		Gyz.set(10);
	}

}
