package hw2_Bodong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*import java.util.Collections;
import java.util.List;
import java.util.ArrayList;*/

public class T3_3_4_aggressive_perceptron_no_shuffle_Bodong {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			   BufferedReader in0 = new BufferedReader(new FileReader("a5a.train"));
			   //List<Integer>[] data=new LinkedList<Integer>();
			   String str;
			   
			   //get number of features and number of lines
			   //ex:-1 1:1 15:1 22:1 36:1 42:1 62:1 67:1 73:1 74:1 76:1 78:1 83:1 , we get 83
			   int i,m,max_feature_index=0,lines=0;
			   while ((str = in0.readLine()) != null)
			   {
				   lines++;
			       //System.out.println(str);
				   m=str.length()-1;
			       while(str.charAt(m)!=':')
			    	   m--;
			       i=str.length()-1;
			       while(str.charAt(i)!=' '||i>m)
			    	   i--;
			       if(max_feature_index<get_num_from_str(str, i+1, m-1))
			    	   max_feature_index=get_num_from_str(str, i+1, m-1);
			   }

			   in0.close();
			   
			   int data[][]=new int[lines][max_feature_index+2];//last index saves y
			   for(i=0;i<lines;i++)
				   for(int j=0;j<max_feature_index+2;j++)
					   data[i][j]=0;
			   int line_ind=0;
			   BufferedReader in = new BufferedReader(new FileReader("a5a.train"));
			   int start=0;
			   while ((str = in.readLine()) != null)
			   {
				   if(str.charAt(0)=='+')
					   data[line_ind][max_feature_index+1]=1;
				   else data[line_ind][max_feature_index+1]=-1;
			       i=str.length()-3;
			       
			       for(i=2;i<str.length()-1;i++)
			       {
			    	   if(str.charAt(i)==' ')
			    		   start=i+1;
			    	   if(str.charAt(i)==':')
			    	   {
			    		   if(str.charAt(i+1)=='1')
			    		   {
			    		   
			    			   //System.out.println(line_ind+"  "+get_num_from_str(str, start, i-1)+"  "+start+"  "+(i-1));
			    			   data[line_ind][get_num_from_str(str, start, i-1)]=1;
			    		   }
			    		   else
			    			   data[line_ind][get_num_from_str(str, start, i-1)]=0;
			    	   }
			       }
			       
			       line_ind++;
			   }
			   
			   in.close();
			   
			   //List<Integer[]> data=new LinkedList<Integer[]>();
			   /*for(i=0;i<lines;i++)
			   {
				   for(int j=0;j<max_feature_index+2;j++)
					   System.out.print(data[i][j]+"  ");
				   System.out.println();
			   }*/
			   
			   //int weight[]=weight_simple_perceptron(data,lines,max_feature_index+2);
			   System.out.println("");
			   System.out.println("T3.3.4     five passes");
			   //int weight[]=weight_multiple_passes_perceptron(data,lines,max_feature_index+2,5);
			   double weight[]=weight_margin_aggressive_multiple_passes_perceptron(data,lines,max_feature_index+2,5,2);
			   //int weight[]=weight_simple_perceptron(data,lines,max_feature_index+2);
			   System.out.print("T3.3.4   weight is ");
			   for(i=0;i<max_feature_index+2;i++)
				   System.out.print(weight[i]+"  ");
			   System.out.println("");
			   int cal_mistake=cal_mistakes(data,weight);
			   //System.out.println("T3.3.2   "+cal_mistake+" mistakes are made.");
			   double accuracy_train_simple=1-(double)cal_mistake/(double)lines;
			   System.out.println("T3.3.4   Accuracy in training set is "+accuracy_train_simple);
			   
			   
			   
			   
			   
			   
			   //on test set
			   try {
				   in0 = new BufferedReader(new FileReader("a5a.test"));
				   //List<Integer>[] data=new LinkedList<Integer>();
				   //String str;
				   
				   //get number of features and number of lines
				   //ex:-1 1:1 15:1 22:1 36:1 42:1 62:1 67:1 73:1 74:1 76:1 78:1 83:1 , we get 83
				   //int i,max_feature_index=0,lines=0;
				   int max_feature_index_test=0;
				   lines=0;
				   while ((str = in0.readLine()) != null)
				   {
					   lines++;
					   
					   
					   m=str.length()-1;
				       while(str.charAt(m)!=':')
				    	   m--;
				       i=str.length()-1;
				       while(str.charAt(i)!=' '||i>m)
				    	   i--;
					   
				       
				       if(max_feature_index_test<get_num_from_str(str, i+1, m-1))
				    	   max_feature_index_test=get_num_from_str(str, i+1, m-1);
				   }

				   in0.close();
				   
				   data=new int[lines][max_feature_index_test+2];//last index saves y
				   for(i=0;i<lines;i++)
					   for(int j=0;j<max_feature_index_test+2;j++)
						   data[i][j]=0;
				   line_ind=0;
				   in = new BufferedReader(new FileReader("a5a.test"));
				   start=0;
				   while ((str = in.readLine()) != null)
				   {
					   if(str.charAt(0)=='+')
						   data[line_ind][max_feature_index_test+1]=1;
					   else data[line_ind][max_feature_index_test+1]=-1;
				       i=str.length()-3;
				       
				       for(i=2;i<str.length()-1;i++)
				       {
				    	   if(str.charAt(i)==' ')
				    		   start=i+1;
				    	   if(str.charAt(i)==':')
				    	   {
				    		   if(str.charAt(i+1)=='1')
				    		   {
				    		   
				    			   //System.out.println(line_ind+"  "+get_num_from_str(str, start, i-1)+"  "+start+"  "+(i-1));
				    			   data[line_ind][get_num_from_str(str, start, i-1)]=1;
				    		   }
				    		   else
				    			   data[line_ind][get_num_from_str(str, start, i-1)]=0;
				    	   }
				       }
				       
				       line_ind++;
				   }
				   
				   in.close();
				   
				   //List<Integer[]> data=new LinkedList<Integer[]>();
				   /*for(i=0;i<lines;i++)
				   {
					   for(int j=0;j<max_feature_index_test+2;j++)
						   System.out.print(data[i][j]+"  ");
					   System.out.println();
				   }*/
				   
				   //int weight[]=weight_simple_perceptron(data,lines,max_feature_index_test+2);
				   /*System.out.print("T3.3.1   weight is ");
				   for(i=0;i<max_feature_index_test+2;i++)
					   System.out.print(weight[i]+"  ");
				   System.out.println("");
				   System.out.println("T3.3.1   "+cal_mistakes(data,weight)+" mistakes are made.");*/
				   
				   cal_mistake=cal_mistakes_on_test(data,weight,max_feature_index,max_feature_index_test);
				   //System.out.println("T3.3.2   "+cal_mistake+" mistakes are made.");
				   double accuracy_test_simple=1-(double)cal_mistake/(double)lines;
				   System.out.println("T3.3.4   Accuracy in testing set is "+accuracy_test_simple);
				   
				   
				   /*int array[]=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
				   Collections.shuffle(array);*/
				   
				} catch (IOException e) {
					e.printStackTrace();
				}
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	public static int get_num_from_str(String str, int start, int end)
	{
		//int hello=0;
		if(start>str.length()-1||end>str.length()-1||start<0||end<0||start>end)
		{
			System.out.println("Wrong in get_um_from_str!");
			System.out.println("start= "+start+"  end= "+end);
			
			//hello=0;
		}
		//hello+=1;
		int num=0;
		//num=Integer.parseInt(str.substring(start, end));
		for(int i=start;i<=end;i++)
			num=(10*num+str.charAt(i)-'0');
		return num;
	}
	
	
	public static int[] weight_multiple_passes_perceptron(int data[][], int lines, int columns, int passes)
	{
		//one pass, no iteration
		int weight[]=new int[columns];//weight[columns-1] is b, data[][columns-1] is y
		
		
		
		
		
		
		
		int sig;
		int num_updates=0;
		
		
		/*int array[]=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
		   Collections.shuffle(array);*/
		
		//no shuffle
		/*List<Integer> shuffle=new ArrayList<Integer>();
		for(int s=0;s<lines;s++)
			shuffle=s;;*/
		
		
		for(int iter=0;iter<passes;iter++)
		{
		
			//Collections.shuffle(shuffle);
			//System.out.println(shuffle);
			
			for(int i=0;i<lines;i++)
			{
				sig=0;
				for(int j=0;j<columns-1;j++)
					sig+=weight[j]*data[i][j];
				sig+=weight[columns-1];//add b
				if((sig>=0&&data[i][columns-1]<0)||(sig<0&&data[i][columns-1]>0))//data!=0
				{
					for(int k=0;k<columns-1;k++)
						weight[k]+=data[i][k]*data[i][columns-1];
					weight[columns-1]+=data[i][columns-1];
					num_updates++;
				}
					
			}
		}
		System.out.println("T3.3.4    Totally "+num_updates+" updates in multiple passes Perceptron Algorithm");
		return weight;
	}
	
	public static double[] weight_margin_aggressive_multiple_passes_perceptron(int data[][], int lines, int columns, double u, int passes)
	{
		//one pass, no iteration
		double weight[]=new double[columns];//weight[columns-1] is b, data[][columns-1] is y
		
		//randomly initialize weight
		for(int w_ind=0;w_ind<columns-1;w_ind++)
		{
			weight[w_ind]=Math.random();
		}
		
		
		double sig;
		int num_updates=0;
		
		
		
		
		/*List<Integer> shuffle=new ArrayList<Integer>();
		for(int s=0;s<lines;s++)
			shuffle.add(s);*/
		
		
		for(int iter=0;iter<passes;iter++)
		{
		
			//Collections.shuffle(shuffle);
		
		
		
			for(int i=0;i<lines;i++)
			{
				sig=0;
				for(int j=0;j<columns-1;j++)
					sig+=weight[j]*((double)data[i][j]);
				sig+=weight[columns-1];//add b
				//if((sig>=0&&data[i][columns-1]<0)||(sig<0&&data[i][columns-1]>0))//data!=0
				if(sig*data[i][columns-1]<=u)//data!=0
				{
					//getwx
					double wx=0;
					for(int k=0;k<columns-1;k++)
					{
						wx+=weight[k]*(double)data[i][k];
					}
					
					//get xx
					double xx=0;
					for(int k=0;k<columns-1;k++)
					{
						xx+=data[i][k]*(double)data[i][k];
					}
					
					
					
					double eta=(u-(double)data[i][columns-1]*(wx+weight[columns-1]))/(xx+1);
					for(int k=0;k<columns-1;k++)
						weight[k]+=eta*(double)data[i][k]*(double)data[i][columns-1];
					weight[columns-1]+=eta*(double)data[i][columns-1];
					num_updates++;
				}
					
			}
		}
		System.out.println("T3.3.4 margin    Totally "+num_updates+" updates in simple Perceptron Algorithm");
		return weight;
	}
	
	public static int[] weight_simple_perceptron(int data[][], int lines, int columns)
	{
		//one pass, no iteration
		int weight[]=new int[columns];//weight[columns-1] is b, data[][columns-1] is y
		int sig;
		int num_updates=0;
		for(int i=0;i<lines;i++)
		{
			sig=0;
			for(int j=0;j<columns-1;j++)
				sig+=weight[j]*data[i][j];
			sig+=weight[columns-1];//add b
			if((sig>=0&&data[i][columns-1]<0)||(sig<0&&data[i][columns-1]>0))//data!=0
			{
				for(int k=0;k<columns-1;k++)
					weight[k]+=data[i][k]*data[i][columns-1];
				weight[columns-1]+=data[i][columns-1];
				num_updates++;
			}
				
		}
		System.out.println("T3.3.4    Totally "+num_updates+" updates in simple Perceptron Algorithm");
		return weight;
	}
	
	public static int cal_mistakes(int data[][],double weight[])
	{
		int length=weight.length;
		int lines=data.length;
		int mistakes=0;
		double sig;
		for(int i=0;i<lines;i++)
		{
			sig=0;
			for(int j=0;j<length-1;j++)
			{
				sig+=weight[j]*(double)data[i][j];
			}
			sig+=weight[length-1];
			if((sig>=0&&data[i][length-1]<0)||(sig<0&&data[i][length-1]>0))
				mistakes++;
		}
		return mistakes;
	}
	
	
	//max_feature_index on training <max_feature_index on test, data is on test
	public static int cal_mistakes_on_test(int data[][],double weight[],int max_feature_index,int max_feature_index_test)
	{
		int length_train=weight.length;
		int length_test=max_feature_index_test+2;
		int lines=data.length;
		int mistakes=0;
		double sig;
		for(int i=0;i<lines;i++)
		{
			sig=0;
			for(int j=0;j<length_train-1;j++)
			{
				sig+=weight[j]*(double)data[i][j];
			}
			sig+=weight[length_train-1];
			if((sig>=0&&(double)data[i][length_test-1]<0)||(sig<0&&(double)data[i][length_test-1]>0))
				mistakes++;
		}
		return mistakes;
	}
	
	

}