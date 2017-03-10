import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
class check2{
	/*public class movie_info{
		String name;
		String link;
		
	}*/
	public int num;
	public check2(int a){
		this.num = a;
	}
	
	
	public class point{
		int x;
		int y;
		public point(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
	
	public void check_hashset(){
		/*HashSet<point> set=new HashSet<point>();
		
		point pt=new point(1,2);*/
		
		HashSet<Integer> set=new HashSet<Integer>();
		
		point pt=new point(1,2);
		
		set.add(3);
		
		//System.out.println("set.contains: "+set.contains(new point(1,2)));
		System.out.println("set.contains: "+set.contains(3));
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double tt=0;
		long tStart1 = System.currentTimeMillis();
		double a_time=0;
		for(int r=0;r<10000000;r++){
			a_time=r*1.84325346234-r;
		}
		long tStart2 = System.currentTimeMillis();
		for(int r=0;r<10000000;r++){
			
			double b_time=r*1.84325346234-r;
			tt=b_time;
		}
		
		
		long tStart3 = System.currentTimeMillis();
		
		
		
		System.out.println(tStart2-tStart1);
		System.out.println(tStart3-tStart2);
		System.out.println(a_time);
		System.out.println(tt);
		
		
		System.out.println("Integer compare 1 8: "+Integer.compare(1, 8));
		String sub="q";
		System.out.println("substring: "+sub.substring(1));
		System.out.println("substring length: "+sub.substring(1).length());
		
		check2 obj=new check2(0);
		
		obj.check_hashset();
		
		
		
		
		
		
		
		
		
		
		int a=9;
		int b=a/10;
		System.out.println(b);
		int[] cc=new int[3];
		System.out.println(cc[0]);
		String str="This is my first try.";
		String[] split=str.split(" ");
		System.out.println(split[3]);
		
		movie_info one_movie=new movie_info("The Shawshank Redemption","tt0111161","1994");
		System.out.println(one_movie.name);
		
		//movie_info movies[]=new movie_info
		List<movie_info> movies=new ArrayList<movie_info>();
		for(int i=0;i<4;i++){
			movie_info movie1=new movie_info("The Shawshank Redemption","tt0111161","1994");
			movies.add(movie1);
		}
		System.out.println(movies.get(3).link);
		String str2="5,345,432";
		//System.out.println(str2.replace(',', ' ')).trim();
		System.out.println(Integer.parseInt(str2.replace(",", "")));
		String str3="4.8";
		System.out.println(Double.parseDouble(str3));
		System.out.println((char)(1+'A'));
		
		for(int bd=0;bd<100;bd++){
			System.out.println((int)(10*Math.random()));
		}
		
		check2 c1 = new check2(1);
		check2 c2 = new check2(2);
		System.out.println(c1.num + " " + c2.num);
		c1 = c2;
		c2.num = 123;
		System.out.println(c1.num + " " + c2.num);
	}

}
