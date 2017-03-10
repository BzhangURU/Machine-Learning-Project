import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
public class exp1_svm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count=0;
		System.out.println("Machine Learning hw5 3.1.1==================================================");
		try {
			   BufferedReader in = new BufferedReader(new FileReader("train.labels"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   //System.out.println(str);
			       count++;
			   }
			   //System.out.println("count= "+count);
			   
			   in.close();
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		double[][] training_data=new double[count][257];//The first dimension is bias
		
		Integer[] shuffle_array = new Integer[count];//{1, 2, 3, 4};
		for(int j=0;j<count;j++){
			shuffle_array[j]=j;
		}
		//int[] array = new int[]{1, 2, 3, 4}; //does not work
		// Shuffle the elements in the array
		Collections.shuffle(Arrays.asList(shuffle_array));
		
		double[] training_label=new double[count];//The first dimension is bias
		
		count=0;
		try {
			   BufferedReader in = new BufferedReader(new FileReader("train.labels"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   training_label[shuffle_array[count]]=(double)Integer.parseInt(str);
				   count++;
			   }
			   //System.out.println("count= "+count);
			   
			   in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		count=0;
		double num;
		try {
			   BufferedReader in = new BufferedReader(new FileReader("train.data"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   String[] strs_line=str.split(" ");
				   training_data[shuffle_array[count]][0]=1;
				   //System.out.print(training_label[count]+"  ");
				   for(int i=1;i<=strs_line.length;i++){
					   num=Double.parseDouble(strs_line[i-1]);
					   training_data[shuffle_array[count]][i]=(num==0?-1:num);
					   //training_data[count][i]=num;
					   //System.out.print(training_data[shuffle_array[count]][i]+"  ");
				   }
				   //System.out.println("");
				   count++;
			   }
			   //System.out.println("count= "+count);
			   
			   in.close();
			  /* int[] avai_feature=new int[22];
			   for(int i=0;i<avai_feature.length;i++)
				   avai_feature[i]=1;
			   TreeNode root_node=buildtree(data,avai_feature);
			   double accuracy=get_accuracy(data,root_node);
			   System.out.println("hw1_A1b_Bodong  Accuracy=  "+accuracy);
			   System.out.println("");*/
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		//train!!!!
		double C=1;
		double gamma0=0.01;
		double gamma_t;
		double[] w= new double[257];
		double dot_product;
		for(int i=0;i<count;i++){
			dot_product=0;
			for(int j=0;j<257;j++){
				dot_product+=w[j]*training_data[i][j];
			}
			gamma_t=gamma0/(1+gamma0*i/C);
			if(training_label[i]*dot_product<=1){
				for(int j=0;j<257;j++){
					w[j]=(1-gamma_t)*w[j]+gamma_t*C*training_label[i]*training_data[i][j];
				}
			}
			else{
				for(int j=0;j<257;j++){
					w[j]=(1-gamma_t)*w[j];
				}
			}
		}
		
		System.out.println("Training complete, the w is (first dimension is bias)");
		for(int j=0;j<257;j++){
			System.out.print(w[j]+"  ");
		}
		System.out.println("");
		
		
		double correct_num_train=0;
		double total_num_train=0;
		
		for(int i=0;i<count;i++){
			dot_product=0;
			for(int j=0;j<257;j++){
				dot_product+=w[j]*training_data[i][j];
			}
			total_num_train++;
			if(training_label[i]*dot_product>0||(training_label[i]>0&&dot_product==0))
				correct_num_train++;
		}
		System.out.println("The training accuracy is "+correct_num_train/total_num_train);
		
		
		
		
		
		
		
		
		//test!!!!!!
		count=0;
		
		try {
			   BufferedReader in = new BufferedReader(new FileReader("test.labels"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   //System.out.println(str);
			       count++;
			   }
			   //System.out.println("count= "+count);
			   
			   in.close();
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		double[][] test_data=new double[count][257];//The first dimension is bias
		
		
		double[] test_label=new double[count];//The first dimension is bias
		
		count=0;
		try {
			   BufferedReader in = new BufferedReader(new FileReader("test.labels"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   test_label[count]=(double)Integer.parseInt(str);
				   count++;
			   }
			   //System.out.println("count= "+count);
			   
			   in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		count=0;
		
		try {
			   BufferedReader in = new BufferedReader(new FileReader("test.data"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   String[] strs_line=str.split(" ");
				   test_data[count][0]=1;
				   //System.out.print(training_label[count]+"  ");
				   for(int i=1;i<=strs_line.length;i++){
					   num=Double.parseDouble(strs_line[i-1]);
					   test_data[count][i]=(num==0?-1:num);
					   //training_data[count][i]=num;
					   //System.out.print(training_data[shuffle_array[count]][i]+"  ");
				   }
				   //System.out.println("");
				   count++;
			   }
			   //System.out.println("count= "+count);
			   
			   in.close();
			  /* int[] avai_feature=new int[22];
			   for(int i=0;i<avai_feature.length;i++)
				   avai_feature[i]=1;
			   TreeNode root_node=buildtree(data,avai_feature);
			   double accuracy=get_accuracy(data,root_node);
			   System.out.println("hw1_A1b_Bodong  Accuracy=  "+accuracy);
			   System.out.println("");*/
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		double correct_num_test=0;
		double total_num_test=0;
		
		for(int i=0;i<count;i++){
			dot_product=0;
			for(int j=0;j<257;j++){
				dot_product+=w[j]*test_data[i][j];
			}
			total_num_test++;
			if(test_label[i]*dot_product>0||(test_label[i]>0&&dot_product==0))
				correct_num_test++;
		}
		System.out.println("The testing accuracy is "+correct_num_test/total_num_test);
		
		
		
		System.out.println("");
		
		
		
	}

}
