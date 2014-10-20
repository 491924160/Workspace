package database_paper;

	public class Pair <T,S>
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
