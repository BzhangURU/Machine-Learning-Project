
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;




public class hw6_code_Bodong {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//double sigma=10.0;
		//double sigma_array[]={0.1, 0.2, 0.5, 0.7, 1.0, 2, 5, 10, 50, 100};
		//double sigma_epoch[][]=new double[10][100];
		
		//double accuracy_arr_test[][]=new double[6][3];
		int cal_mistake;
		double max_cross_accuracy=0;
		int best_sigma_ind=0;
		
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
			   
			   int data[][]=new int[lines][max_feature_index+1];//first index saves y
			   for(i=0;i<lines;i++)
				   for(int j=0;j<max_feature_index+1;j++)
					   data[i][j]=0;
			   int line_ind=0;
			   BufferedReader in = new BufferedReader(new FileReader("a5a.train"));
			   int start=0;
			   while ((str = in.readLine()) != null)
			   {
				   if(str.charAt(0)=='+')
					   data[line_ind][0]=1;
				   else data[line_ind][0]=-1;
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
			    			   data[line_ind][get_num_from_str(str, start, i-1)]=0;//correct
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
			   /*System.out.println("hw6");
			   double weight[]=weight_multiple_passes_perceptron(data,lines,max_feature_index+1,10,sigma);
			   //int weight[]=weight_simple_perceptron(data,lines,max_feature_index+2);
			   System.out.print("hw6   weight is ");
			   for(i=0;i<max_feature_index+1;i++)
				   System.out.print(weight[i]+"  ");
			   System.out.println("");
			   int cal_mistake=cal_mistakes(data,weight);
			   //System.out.println("T3.3.2   "+cal_mistake+" mistakes are made.");
			   double accuracy_train_simple=1-(double)cal_mistake/(double)lines;
			   System.out.println("hw6   Accuracy in training set is "+accuracy_train_simple);*/
			   
			   
			   
			   
			 //Cross-validation/////////////////////////////////////////////////////////////////////////////
			   int max_epoch=50;
			   double accuracy_epoch[]=new double[max_epoch];
			   double object_value_epoch[]=new double[max_epoch];
			   double negative_log_accuracy_epoch[]=new double[max_epoch];
			   double accuracycross_sigma[]=new double[10];
			   int Fold=5;
			   double sigma_array[]={0.1, 0.2, 0.5, 0.7, 1.0, 2, 5, 10, 50, 100};
				
				
			for(int i_sigma=0;i_sigma<10;i_sigma++){	
				System.out.println("");
			
				System.out.print("sigma= "+sigma_array[i_sigma]);
				
			
					
					
					
				//accuracy_cross_validation_perceptron(int data[][], int lines, int columns, int passes,double sigma,int Fold)
				
					
				accuracycross_sigma[i_sigma]=accuracy_cross_validation_perceptron(data,lines,max_feature_index+1,20,sigma_array[i_sigma],Fold);
				   //int weight[]=weight_simple_perceptron(data,lines,max_feature_index+2);
				   
				   //cal_mistake=cal_mistakes(data,weight);
				   //System.out.println("T3.3.2   "+cal_mistake+" mistakes are made.");
				   //double accuracy_train_simple=1-(double)cal_mistake/(double)lines;
				
				
				if(max_cross_accuracy<accuracycross_sigma[i_sigma]){
					max_cross_accuracy=accuracycross_sigma[i_sigma];
					best_sigma_ind=i_sigma;
				}
				   System.out.println("    Accuracy in cross validation is "+accuracycross_sigma[i_sigma]);
					
			}	
					
			   
			   
			   
			   
			   
			System.out.println("The best choice of sigma is sigma= "+sigma_array[best_sigma_ind]+" with accuracy= "+max_cross_accuracy);  
			
			   
			   
			   
			   
			   
			 
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   //save test set data////////////////////////////////////////////////////////////////////////////////////////////////////////////
			   try {
				   in0 = new BufferedReader(new FileReader("a5a.test"));
				   //List<Integer>[] data=new LinkedList<Integer>();
				   //String str;
				   
				   //get number of features and number of lines
				   //ex:-1 1:1 15:1 22:1 36:1 42:1 62:1 67:1 73:1 74:1 76:1 78:1 83:1 , we get 83
				   //int i,max_feature_index=0,lines=0;
				   int max_feature_index_test=0;
				   int lines_test=0;
				   while ((str = in0.readLine()) != null)
				   {
					   lines_test++;
					   
					   
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
				   
				   int data_test[][]=new int[lines_test][max_feature_index_test+1];//first index saves y
				   for(i=0;i<lines_test;i++)
					   for(int j=0;j<max_feature_index_test+1;j++)
						   data_test[i][j]=0;
				   line_ind=0;
				   in = new BufferedReader(new FileReader("a5a.test"));
				   start=0;
				   while ((str = in.readLine()) != null)
				   {
					   if(str.charAt(0)=='+')
						   data_test[line_ind][0]=1;
					   else data_test[line_ind][0]=-1;
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
				    			   data_test[line_ind][get_num_from_str(str, start, i-1)]=1;
				    		   }
				    		   else
				    			   data_test[line_ind][get_num_from_str(str, start, i-1)]=0;//correct
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
				   
				   
				   
				   System.out.println("Now use the whole data to train with different epoch and test accuracy...");    
				   
				   
				   double weight[]=weight_multiple_passes_perceptron(data,lines,max_feature_index+1,max_epoch,sigma_array[best_sigma_ind],data_test,accuracy_epoch,max_feature_index_test,object_value_epoch);  
				   
				   
				   
				   
				   
				   for(int r=0;r<max_epoch;r++){
					   negative_log_accuracy_epoch[r]=-Math.log(accuracy_epoch[r]);
				   }
				   
				   
				   
				   
				   
				   
				   
				   
				   
				   
				   
				   //cal_mistake=cal_mistakes_on_test(data_test,weight,max_feature_index_test);
				   //System.out.println("T3.3.2   "+cal_mistake+" mistakes are made.");
				   //double accuracy_test_simple=1-(double)cal_mistake/(double)lines;
				   //System.out.println("hw6  Accuracy in testing set is "+accuracy_test_simple);
				   
				   
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
		
		num=Integer.parseInt(str.substring(start, end+1));
		/*for(int i=start;i<=end;i++)
			num=(10*num+str.charAt(i)-'0');*/
		return num;
	}
	
	
	public static double[] weight_multiple_passes_perceptron(int data[][], int lines, int columns, int passes,double sigma,int data_test[][],double accuracy_epoch[],int max_feature_index_test,double object_value_epoch[])
	{
		
		double weight[]=new double[columns];//weight[columns-1] is b, data[][columns-1] is y
		/*for(int w_ind=0;w_ind<columns-1;w_ind++)
		{
			weight[w_ind]=Math.random();
		}*/
		double sig;
		int num_updates=0;
		double rate=0.01;
		int best_epoch=1;
		double best_accuracy=0;
		double best_object=0;
		double yx;
		/*int array[]=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
		   Collections.shuffle(array);*/
		List<Integer> shuffle=new ArrayList<Integer>();
		for(int s=0;s<lines;s++)
			shuffle.add(s);
		
		
		
		
		
		
		try{
		    PrintWriter writer = new PrintWriter("plot_data.txt", "UTF-8");
		    
		    
		    		
		    	
		    
		
		
		
		
		
		
		
		
		
		
		
		
		
		for(int iter=0;iter<passes;iter++)
		{
		
			Collections.shuffle(shuffle);
			//System.out.println(shuffle);
			
			for(int i=0;i<lines;i++)
			{
				sig=0;
				for(int j=1;j<columns;j++)
					sig+=weight[j]*(double)data[shuffle.get(i)][j];
				sig+=weight[0];//add b
				if((sig>=0&&(double)data[shuffle.get(i)][0]<0)||(sig<0&&(double)data[shuffle.get(i)][0]>0))//data!=0
				{
					for(int k=1;k<columns;k++){
						//weight[k]+=rate*(double)data[shuffle.get(i)][k]*(double)data[shuffle.get(i)][0];
						yx=(double)data[shuffle.get(i)][k]*(double)data[shuffle.get(i)][0];
						
						weight[k]=weight[k]*(1-rate*2/(sigma*sigma))+rate*yx*(1/(1+Math.exp(sig*(double)data[shuffle.get(i)][0])));
					}
						
					weight[0]+=rate*(double)data[shuffle.get(i)][0];
					num_updates++;
				}
					
			}
			
			
			if(iter<5||(iter%5==0)){
				accuracy_epoch[iter]=1.0-(double)cal_mistakes_on_test(data_test,weight,max_feature_index_test)/(double)data_test.length;
				
				System.out.print("After "+(iter+1)+" th iteration, the accuracy is "+accuracy_epoch[iter]);
				
				object_value_epoch[iter]=cal_object_on_test(data_test,weight,max_feature_index_test,sigma);
				System.out.println("   The object value is "+object_value_epoch[iter]);
				
				
				
				//@@@@@@@@@@@@@@@@@@@@@@@@@@2
				/*System.out.print("     Weight= ");
				
				for(int t=0;t<weight.length;t++){
					System.out.print(weight[t]+"   ");
				}
				System.out.println("");*/
				
				//sum{log(1+exp(-yi wT xi))}+ wT w/(sigma^2)
				if(best_accuracy<accuracy_epoch[iter]){
					best_epoch=iter+1;
					best_accuracy=accuracy_epoch[iter];
					best_object=object_value_epoch[iter];
				}
				
				writer.println(String.valueOf(iter));
				writer.println(String.valueOf(accuracy_epoch[iter]));
				writer.println(String.valueOf(object_value_epoch[iter]));
			}
			
			
			
		}
		
		System.out.println("So to get best accuracy, epoch= "+best_epoch+"  accuracy= "+best_accuracy);
		
		writer.close();
		} catch (Exception e) {
		  
		}
		
		
		
		
		
		//System.out.println("hw6   Totally "+num_updates+" updates in multiple passes Perceptron Algorithm");
		return weight;
	}
	
	
	public static double accuracy_cross_validation_perceptron(int data[][], int lines, int columns, int passes,double sigma,int Fold)
	{
		
		//double weight[]=new double[columns];//weight[columns-1] is b, data[][columns-1] is y
		/*for(int w_ind=0;w_ind<columns-1;w_ind++)
		{
			weight[w_ind]=Math.random();
		}*/
		double sig;
		int num_updates=0;
		double rate=0.01;
		double yx;
		/*int array[]=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
		   Collections.shuffle(array);*/
		List<Integer> shuffle=new ArrayList<Integer>();
		for(int s=0;s<lines;s++)
			shuffle.add(s);
		double wrong_num=0;
		double total_num=0;
		
		
		
		for(int cross_ind=0;cross_ind<Fold;cross_ind++){
			double weight[]=new double[columns];
			for(int iter=0;iter<passes;iter++)
			{
		
				Collections.shuffle(shuffle);
				//System.out.println(shuffle);
			
				for(int i=0;i<lines;i++)
				{
				
				
					if(i<cross_ind*lines/Fold||i>(cross_ind+1)*lines/Fold-1){
					
						sig=0;
						for(int j=1;j<columns;j++)
							sig+=weight[j]*(double)data[shuffle.get(i)][j];
						sig+=weight[0];//add b
						if((sig>=0&&(double)data[shuffle.get(i)][0]<0)||(sig<0&&(double)data[shuffle.get(i)][0]>0))//data!=0
						{
							for(int k=1;k<columns;k++){
								//weight[k]+=rate*(double)data[shuffle.get(i)][k]*(double)data[shuffle.get(i)][0];
								yx=(double)data[shuffle.get(i)][k]*(double)data[shuffle.get(i)][0];
								
								weight[k]=weight[k]*(1-rate*2/(sigma*sigma))+rate*yx*(1/(1+Math.exp(sig*(double)data[shuffle.get(i)][0])));
							}
								
							
							weight[0]+=rate*(double)data[shuffle.get(i)][0];
							num_updates++;
						}
					
					}
				}
				//System.out.println("hw6   Totally "+num_updates+" updates in multiple passes Perceptron Algorithm");
				
			}
			
			
			
			
			
			
			
			for(int i=0;i<lines;i++)
			{
				
				if(i>=cross_ind*lines/Fold&&i<=(cross_ind+1)*lines/Fold-1){
					total_num++;
				
					sig=0;
					for(int j=1;j<columns;j++)
					{
						sig+=weight[j]*(double)data[i][j];
					}
					sig+=weight[0];
					
					
					if((sig>=0&&(double)data[i][0]<0)||(sig<0&&(double)data[i][0]>0))
						wrong_num++;
				}
			}
			
			
			
			
			
			
			
			
			
		}
		return (1-wrong_num/total_num);
		
	}
	
	
	
	
	public static double[] weight_simple_perceptron(int data[][], int lines, int columns,double sigma)
	{
		//one pass, no iteration
		double weight[]=new double[columns];//weight[columns-1] is b, data[][columns-1] is y
		double sig;
		int num_updates=0;
		for(int i=0;i<lines;i++)
		{
			sig=0;
			for(int j=1;j<columns;j++)
				sig+=weight[j]*(double)data[i][j];
			sig+=weight[0];//add b
			if((sig>=0&&(double)data[i][0]<0)||(sig<0&&(double)data[i][0]>0))//data!=0
			{
				for(int k=1;k<columns;k++)
					weight[k]+=(double)data[i][k]*(double)data[i][0];
				weight[0]+=(double)data[i][0];
				num_updates++;
			}
				
		}
		System.out.println("hw6    Totally "+num_updates+" updates in simple Perceptron Algorithm");
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
			for(int j=1;j<length;j++)
			{
				sig+=weight[j]*(double)data[i][j];
			}
			sig+=weight[0];
			if((sig>=0&&(double)data[i][0]<0)||(sig<0&&(double)data[i][0]>0))
				mistakes++;
		}
		return mistakes;
	}
	
	
	//maybe max_feature_index on training <max_feature_index on test, data is on test
	public static int cal_mistakes_on_test(int data[][],double weight[],int max_feature_index_test)
	{
		int length_train=weight.length;
		int length_test=max_feature_index_test+1;
		int min_length=Math.min(length_train, length_test);
		
		
		int lines=data.length;
		int mistakes=0;
		double sig;
		
		for(int i=0;i<lines;i++)
		{
			sig=0;
			for(int j=1;j<min_length;j++)
			{
				sig+=weight[j]*(double)data[i][j];
			}
			sig+=weight[0];
			if((sig>=0&&(double)data[i][0]<0)||(sig<0&&(double)data[i][0]>0))
				mistakes++;
		}
		return mistakes;
	}
	
	
	
	public static double cal_object_on_test(int data[][],double weight[],int max_feature_index_test,double sigma)
	{
		//sum{log(1+exp(-yi wT xi))}+ wT w/(sigma^2)
		int length_train=weight.length;
		int length_test=max_feature_index_test+1;
		int min_length=Math.min(length_train, length_test);
		
		
		int lines=data.length;
		double sum=0;
		double inner_product=0;
		for(int i=0;i<lines;i++)
		{
			inner_product=0;
			for(int j=1;j<min_length;j++)
			{
				inner_product+=weight[j]*(double)data[i][j];
			}
			inner_product+=weight[0];
			sum+=Math.log(1+Math.exp(-(double)data[i][0]*inner_product));
			/*if(Math.log(1+Math.exp(-(double)data[i][0])*inner_product)<0){
				System.out.println("wrong!");
				System.out.println("data[0]=  "+(double)data[i][0]+"  exp= "+(Math.exp(-(double)data[i][0])*inner_product));
				System.out.println("wrong!");
			}*/
		}
		double sum2=0;
		for(int j=1;j<min_length;j++)
		{
			sum2+=weight[j]*weight[j];
		}
		sum2=sum2/(sigma*sigma);
		
		return sum+sum2;
	}
	
	

}
