package hw2_Bodong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class T3_3_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			   BufferedReader in0 = new BufferedReader(new FileReader("table2"));
			   //List<Integer>[] data=new LinkedList<Integer>();
			   String str;
			   
			   //get number of features and number of lines
			   //ex:-1 1:1 15:1 22:1 36:1 42:1 62:1 67:1 73:1 74:1 76:1 78:1 83:1 , we get 83
			   int i,max_feature_index=0,lines=0;
			   while ((str = in0.readLine()) != null)
			   {
				   lines++;
			       //System.out.println(str);
			       i=str.length()-3;
			       while(str.charAt(i)!=' ')
			    	   i--;
			       if(max_feature_index<get_num_from_str(str, i+1, str.length()-3))
			    	   max_feature_index=get_num_from_str(str, i+1, str.length()-3);
			   }

			   in0.close();
			   
			   int data[][]=new int[lines][max_feature_index+2];//last index saves y
			   for(i=0;i<lines;i++)
				   for(int j=0;j<max_feature_index+2;j++)
					   data[i][j]=0;
			   int line_ind=0;
			   BufferedReader in = new BufferedReader(new FileReader("table2"));
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
			   
			   int weight[]=weight_simple_perceptron(data,lines,max_feature_index+2);
			   System.out.print("T3.3.1   weight is ");
			   for(i=0;i<max_feature_index+2;i++)
				   System.out.print(weight[i]+"  ");
			   System.out.println("");
			   System.out.println("T3.3.1   "+cal_mistakes(data,weight)+" mistakes are made.");
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	public static int get_num_from_str(String str, int start, int end)
	{
		if(start>str.length()-1||end>str.length()-1||start<0||end<0||start>end)
			System.out.println("Wrong in get_um_from_str!");
		int num=0;
		for(int i=start;i<=end;i++)
			num+=(10*num+str.charAt(i)-'0');
		return num;
	}
	public static int[] weight_simple_perceptron(int data[][], int lines, int columns)
	{
		//one pass, no iteration
		int weight[]=new int[columns];//weight[columns-1] is b, data[][columns-1] is y
		int sig;
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
			}
				
		}
		return weight;
	}
	
	public static int cal_mistakes(int data[][],int weight[])
	{
		int length=weight.length;
		int lines=data.length;
		int mistakes=0, sig;
		for(int i=0;i<lines;i++)
		{
			sig=0;
			for(int j=0;j<length-1;j++)
			{
				sig+=weight[j]*data[i][j];
			}
			sig+=weight[length-1];
			if((sig>=0&&data[i][length-1]<0)||(sig<0&&data[i][length-1]>0))
				mistakes++;
		}
		return mistakes;
	}

}
