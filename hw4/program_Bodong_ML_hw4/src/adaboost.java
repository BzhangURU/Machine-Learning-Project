import java.util.ArrayList;
import java.util.Arrays;



public class adaboost {
	
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
	
	public static double h_abcd(adaboost_point_x x,int function_ind){
		//h_a
		if(function_ind==1){
			if(x.x1>=0)
				return 1;
			else return -1;
		}//h_b
		else if(function_ind==2){
			if(x.x1-2>=0)
				return 1;
			else return -1;
		}//h_c
		else if(function_ind==3){
			if(x.x1>=0)
				return -1;
			else return 1;
		}//h_d
		else {
			if(x.x2>=0)
				return -1;
			else return 1;
		}
	}
	
	
	public static double h_a(adaboost_point_x x){
		if(x.x1>=0)
			return 1;
		else return -1;
	}
	
	public static double h_b(adaboost_point_x x){
		if(x.x1-2>=0)
			return 1;
		else return -1;
	}
	
	public static double h_c(adaboost_point_x x){
		if(x.x1>=0)
			return -1;
		else return 1;
	}
	
	public static double h_d(adaboost_point_x x){
		if(x.x2>=0)
			return -1;
		else return 1;
	}
	
	
	public static double mysgn(double num){
		if(num>=0)
			return 1;
		else return -1;
	}
	
	
	public static void cal_error_choose_func(double[][] e_error,double[] e_error_invalid,
			double[][] D,adaboost_point_x[] x_points,double[] y,int m,int iter,int FUNC_NUM){
		for(int j=0;j<FUNC_NUM;j++){
			if(e_error_invalid[j]!=1){
				e_error[iter][j]=0.5;
				for(int i=0;i<m;i++){
					e_error[iter][j]-=0.5*D[iter][i]*y[i]*h_abcd(x_points[i],j);
				}
			}
			//e_error_invalid[j]=1;
		}
		
		System.out.println("The No. "+iter+"iteration, the error for each function is:");
		for(int k=0;k<FUNC_NUM;k++){
			if(e_error_invalid[k]!=1)
				System.out.println(e_error[iter][k]+"   ");
			else System.out.println("Not applicable   ");
		}
		
	}
	
	
	public static void adaboost_alg(adaboost_point_x[] x_points,double[] y){
		int ITER_STEP=4;
		int FUNC_NUM=4;
		//initialize D1(i)
		int m=y.length;
		double[] y_estimate=new double[m];
		double[] alpha=new double[ITER_STEP];
		double[][] D=new double[ITER_STEP][m];
		
		for(int i=0;i<m;i++){
			D[0][i]=1/m;
		}
		double[][] e_error=new double[ITER_STEP][FUNC_NUM];
		
		//if ==1, no need to check error, that hypothesis has been used
		double[] e_error_invalid=new double[FUNC_NUM];
		
		int[] min_ind=new int[ITER_STEP];
		Arrays.fill(min_ind, -1);
		
		//For loop
		for(int iter=0;iter<ITER_STEP;iter++){
			//first get the func_ind with min error
			cal_error_choose_func(e_error,e_error_invalid,D,x_points,y,m,iter,FUNC_NUM);
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
			alpha[iter]=0.5*Math.exp((1-min_error)/min_error);
			if(iter+1!=ITER_STEP){
				for(int i=0;i<m;i++){
					D[iter+1][i]=D[iter+1][i]*Math.exp(-alpha[iter]*y[i]*h_abcd(x_points[i],min_ind[iter]));
				}
			}
				
			
			
		}
		
		for(int final_i=0;final_i<m;final_i++){
			for(int final_iter=0;final_iter<ITER_STEP;final_iter++)
				y_estimate[final_i]+=alpha[final_iter]*h_abcd(x_points[final_i],min_ind[final_iter]);
			y_estimate[final_i]=mysgn(y_estimate[final_i]);
		}
		System.out.println("The estimated y is: ");
		for(int print_i=0;print_i<m;print_i++)
			System.out.print(y_estimate[print_i]+"  ");
		System.out.println("");
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		adaboost_point_x[] x_points = new adaboost_point_x[4];
		System.out.println(x_points);
//		adaboost_point_x pointx=new adaboost_point_x();
		x_points[0].setpoint((double)1,(double)1);
		System.out.println(x_points[0]);
		
		x_points[1].x1=1;
		x_points[1].x2=-1;
		
		x_points[2].x1=-1;
		x_points[2].x2=-1;
		
		x_points[3].x1=-1;
		x_points[3].x2=1;
		
		double[] y=new double[4];
		y[0]=-1;y[1]=1;y[2]=-1;y[3]=-1;
		
		adaboost_alg(x_points,y);
		
	}

}
