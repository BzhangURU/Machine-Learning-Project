import java.util.ArrayList;
import java.util.Arrays;



public class adaboost_double {
	
//	public class adaboost_point_x{
//		double x1;
//		double x2;
//		adaboost_point_x(double a,double b){
//			this.x1=a;
//			this.x2=b;
//		}
//		adaboost_point_x(){
//			;
//			/*this.x1=-1;
//			this.x2=b;*/
//		}
//		public void setpoint(double d, double e) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
	
	public static double h_abcd(double x1,double x2,int function_ind){
		//h_a
		if(function_ind==0){
			if(x1>=0)
				return 1;
			else return -1;
		}//h_b
		else if(function_ind==1){
			if(x1-2>=0)
				return 1;
			else return -1;
		}//h_c
		else if(function_ind==2){
			if(x1>=0)
				return -1;
			else return 1;
		}//h_d
		else {
			if(x2>=0)
				return -1;
			else return 1;
		}
	}
	
	
	public static double h_a(double x1,double x2){
		if(x1>=0)
			return 1;
		else return -1;
	}
	
	public static double h_b(double x1,double x2){
		if(x1-2>=0)
			return 1;
		else return -1;
	}
	
	public static double h_c(double x1,double x2){
		if(x1>=0)
			return -1;
		else return 1;
	}
	
	public static double h_d(double x1,double x2){
		if(x2>=0)
			return -1;
		else return 1;
	}
	
	
	public static double mysgn(double num){
		if(num>=0)
			return 1;
		else return -1;
	}
	
	
	public static void cal_error_choose_func(double[][] e_error,double[] e_error_invalid,
			double[][] D,double[] x1_points,double[] x2_points,double[] y,int m,int iter,int FUNC_NUM){
		for(int j=0;j<FUNC_NUM;j++){
			if(e_error_invalid[j]!=1){
				e_error[iter][j]=0.5;
				for(int i=0;i<m;i++){
					//System.out.println("D="+D[iter][i]+"   y="+y[i]+"   h_abcd="+h_abcd(x1_points[i],x2_points[i],j));
					e_error[iter][j]-=0.5*D[iter][i]*y[i]*h_abcd(x1_points[i],x2_points[i],j);
					//System.out.println("error= "+e_error[iter][j]);
				}
			}
			//e_error_invalid[j]=1;
		}
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("The No. "+iter+" iteration, the error for each function is:");
		for(int k=0;k<FUNC_NUM;k++){
			if(e_error_invalid[k]!=1)
				System.out.println(e_error[iter][k]+"   ");
			else System.out.println("function already used, Not applicable   ");
		}
		
	}
	
	
	public static void adaboost_alg(double[] x1_points,double[] x2_points,double[] y){
		int ITER_STEP=4;
		int FUNC_NUM=4;
		//initialize D1(i)
		int m=y.length;
		double[] y_estimate=new double[m];
		double[] alpha=new double[ITER_STEP];
		double[][] D=new double[ITER_STEP][m];
		
		for(int i=0;i<m;i++){
			D[0][i]=(double)1/(double)m;
		}
		//System.out.println("D======"+D[0][1]);
		double[][] e_error=new double[ITER_STEP][FUNC_NUM];
		
		//if ==1, no need to check error, that hypothesis has been used
		double[] e_error_invalid=new double[FUNC_NUM];
		
		int[] min_ind=new int[ITER_STEP];
		Arrays.fill(min_ind, -1);
		
		//For loop
		for(int iter=0;iter<ITER_STEP;iter++){
			//first get the func_ind with min error
			//System.out.println("D======"+D[0][1]);
			cal_error_choose_func(e_error,e_error_invalid,D,x1_points,x2_points,y,m,iter,FUNC_NUM);
			double min_error=Double.MAX_VALUE;
			//int min_ind=-1;
			for(int find_min_i=0;find_min_i<FUNC_NUM;find_min_i++){
				if(e_error_invalid[find_min_i]==0&&e_error[iter][find_min_i]<min_error){
					min_error=e_error[iter][find_min_i];
					min_ind[iter]=find_min_i;
				}
			}
			if(min_ind[iter]<FUNC_NUM&&min_ind[iter]>=0)
				e_error_invalid[min_ind[iter]]=1;
			else System.out.println("Wrong!");
			alpha[iter]=0.5*Math.log((1-min_error)/min_error);
			
			
			double Z_sumD=0;
			
			if(iter+1!=ITER_STEP){
				for(int i=0;i<m;i++){
					D[iter+1][i]=D[iter][i]*Math.exp(-alpha[iter]*y[i]*h_abcd(x1_points[i],x2_points[i],min_ind[iter]));
				}
				//System.out.println("new D=========="+D[iter+1][2]);
				Z_sumD=0;
				for(int i=0;i<m;i++){
					Z_sumD+=D[iter+1][i];
				}
				//System.out.println("Z=========="+Z_sumD);
				for(int i=0;i<m;i++){
					D[iter+1][i]=D[iter+1][i]/Z_sumD;
				}
				
				
			}
			System.out.println("choose "+min_ind[iter]+"th function. error= "+min_error);
			System.out.println("alpha= "+alpha[iter]+"  Z= "+Z_sumD);	
			System.out.println("Table:");
			if(iter==0)
				System.out.println("x=[x1,x2]        yi       ha(x)      D            D(i)*y*h(x)         new D");	
			else
			System.out.println("x=[x1,x2]        yi       ha(x)               D                     D(i)*y*h(x)                 new D");
			for(int test_i=0;test_i<m;test_i++){
				System.out.println("["+x1_points[test_i]+"   "+x2_points[test_i]+"]     "+y[test_i]+"      "+h_abcd(x1_points[test_i],x2_points[test_i],min_ind[iter])+
						"        "+D[iter][test_i]+"         "+D[iter][test_i]*y[test_i]*h_abcd(x1_points[test_i],x2_points[test_i],min_ind[iter])+"         "+D[Math.min(iter+1,ITER_STEP-1)][test_i]);	
			}
			
		
		
			for(int final_i=0;final_i<m;final_i++){
				for(int final_iter=0;final_iter<=iter;final_iter++){
					y_estimate[final_i]+=alpha[final_iter]*h_abcd(x1_points[final_i],x2_points[final_i],min_ind[final_iter]);
					//System.out.println("!!!!!!!!!!!!x1="+x1_points[final_i]+"     x2="+x2_points[final_i]+"     ind="+min_ind[final_iter]+"   h======="+h_abcd(x1_points[final_i],x2_points[final_i],min_ind[final_iter]));
					//System.out.println("y_estimate="+y_estimate[final_i]);
					//System.out.println("");
				}
				
				y_estimate[final_i]=mysgn(y_estimate[final_i]);
			}
			System.out.println("The estimated y is: ");
			for(int print_i=0;print_i<m;print_i++)
				System.out.print(y_estimate[print_i]+"  ");
			System.out.println("");
		
		
		
		
		}
		
		/*for(int final_i=0;final_i<m;final_i++){
			for(int final_iter=0;final_iter<ITER_STEP;final_iter++)
				y_estimate[final_i]+=alpha[final_iter]*h_abcd(x1_points[final_i],x2_points[final_i],min_ind[final_iter]);
			y_estimate[final_i]=mysgn(y_estimate[final_i]);
		}
		System.out.println("The estimated y is: ");
		for(int print_i=0;print_i<m;print_i++)
			System.out.print(y_estimate[print_i]+"  ");
		System.out.println("");*/
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*adaboost_point_x[] x_points = new adaboost_point_x[4];
		System.out.println(x_points);
//		adaboost_point_x pointx=new adaboost_point_x();
		x_points[0].setpoint((double)1,(double)1);
		System.out.println(x_points[0]);
		
		x_points[1].x1=1;
		x_points[1].x2=-1;
		
		x_points[2].x1=-1;
		x_points[2].x2=-1;
		
		x_points[3].x1=-1;
		x_points[3].x2=1;*/
		
		
		double[] x1_points=new double[4];
		x1_points[0]=1;
		x1_points[1]=1;
		x1_points[2]=-1;
		x1_points[3]=-1;
		
		double[] x2_points=new double[4];
		x2_points[0]=1;
		x2_points[1]=-1;
		x2_points[2]=-1;
		x2_points[3]=1;
		
		double[] y=new double[4];
		y[0]=-1;y[1]=1;y[2]=-1;y[3]=-1;
		
		adaboost_alg(x1_points,x2_points,y);
		
	}

}
