import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
public class exp2_3_1_2_svm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Machine Learning hw5 3.1.2==================================================");
		double[] C_array=new double[6];
		for(int i=0;i<6;i++)
			C_array[i]=Math.pow(2.0, 1.0-(double)i);
		double[] gamma_array=new double[]{0.1,0.01,0.001};
		
		double accuracy_arr_train[][]=new double[6][3];
		double accuracy_arr_test[][]=new double[6][3];
		
		
		
		
				
				int count=0;
				int dimension_without_bias=0;
				try {
					   BufferedReader in = new BufferedReader(new FileReader("madelon_train.data"));
					   String str;
					   
					   
					   if ((str = in.readLine()) != null)
					   {
						   String[] strs_get_dimension=str.split(" ");
						   dimension_without_bias=strs_get_dimension.length;
					       count++;
					   }
					   
					   
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
				
				int dimension_1=dimension_without_bias+1;
				double[][] training_data=new double[count][dimension_1];//The first dimension is bias
				
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
					   BufferedReader in = new BufferedReader(new FileReader("madelon_train.labels"));
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
					   BufferedReader in = new BufferedReader(new FileReader("madelon_train.data"));
					   String str;
					   
					   while ((str = in.readLine()) != null)
					   {
						   String[] strs_line=str.split(" ");
						   training_data[shuffle_array[count]][0]=1;
						   //System.out.print(training_label[count]+"  ");
						   for(int i=1;i<=strs_line.length;i++){
							   num=Double.parseDouble(strs_line[i-1]);
							   training_data[shuffle_array[count]][i]=num;
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
				
				
				
				
				count=0;
				
				try {
					   BufferedReader in = new BufferedReader(new FileReader("madelon_test.labels"));
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
				double[][] test_data=new double[count][dimension_1];//The first dimension is bias
				
				
				double[] test_label=new double[count];//The first dimension is bias
				
				count=0;
				try {
					   BufferedReader in = new BufferedReader(new FileReader("madelon_test.labels"));
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
					   BufferedReader in = new BufferedReader(new FileReader("madelon_test.data"));
					   String str;
					   
					   while ((str = in.readLine()) != null)
					   {
						   String[] strs_line=str.split(" ");
						   test_data[count][0]=1;
						   //System.out.print(training_label[count]+"  ");
						   for(int i=1;i<=strs_line.length;i++){
							   num=Double.parseDouble(strs_line[i-1]);
							   test_data[count][i]=num;
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
				
				
				
				
		int Fold=5;		
				
		double each_accuracy[][]=new double[6][3];
		//double global_w[]=new double[dimension_1];
		double best_accuracy=0;
		double dot_product;		
		for(int dif_C=0;dif_C<6;dif_C++){
			for(int dif_gamma=0;dif_gamma<3;dif_gamma++){
		
				double C=C_array[dif_C];
				double gamma0=gamma_array[dif_gamma];	
				double accuracy_accumulate_train=0;
				double accuracy_accumulate=0;
				
				
				
				//train!!!!
				
				
				
				//Cross-validation
				
				
				
				
				
				for(int cross_ind=0;cross_ind<Fold;cross_ind++){
				
					double gamma_t;
					double[] w= new double[dimension_1];
					
					
					
					for(int i=0;i<count;i++){
						if(i<cross_ind*count/5||i>(cross_ind+1)*count/5-1){
							dot_product=0;
							for(int j=0;j<dimension_1;j++){
								dot_product+=w[j]*training_data[i][j];
							}
							gamma_t=gamma0/(1+gamma0*i/C);
							if(training_label[i]*dot_product<=1){
								for(int j=0;j<dimension_1;j++){
									w[j]=(1-gamma_t)*w[j]+gamma_t*C*training_label[i]*training_data[i][j];
								}
							}
							else{
								for(int j=0;j<dimension_1;j++){
									w[j]=(1-gamma_t)*w[j];
								}
							}
						}
					}
					
					
					
					
					
					
					/*System.out.println("Training complete, the first 10 dimension of w is (first dimension is bias)");
					for(int j=0;j<dimension_1;j++){
						System.out.print(w[j]+"  ");
					}
					for(int j=0;j<10;j++){
						System.out.print(w[j]+"  ");
					}
					System.out.println("");*/
					
					
					double correct_num_train=0;
					double total_num_train=0;
					
					for(int i=0;i<count;i++){
						if(i<cross_ind*count/5||i>(cross_ind+1)*count/5-1){
							dot_product=0;
							for(int j=0;j<dimension_1;j++){
								dot_product+=w[j]*training_data[i][j];
							}
							total_num_train++;
							if(training_label[i]*dot_product>0||(training_label[i]>0&&dot_product==0))
								correct_num_train++;
						}
					}
					
					
					
					
					accuracy_accumulate_train+=correct_num_train/total_num_train;
					
					
					//cross validation
					correct_num_train=0;
					total_num_train=0;
					
					for(int i=0;i<count;i++){
						if(i>=cross_ind*count/5&&i<=(cross_ind+1)*count/5-1){
							dot_product=0;
							for(int j=0;j<dimension_1;j++){
								dot_product+=w[j]*training_data[i][j];
							}
							total_num_train++;
							if(training_label[i]*dot_product>0||(training_label[i]>0&&dot_product==0))
								correct_num_train++;
						}
					}
					
					accuracy_accumulate+=correct_num_train/total_num_train;
				
				}
				
				System.out.print("C=   "+C);
				System.out.println("    gamma0=   "+gamma0);
				System.out.println("The average training accuracy is "+accuracy_accumulate_train/Fold);
				System.out.println("The average cross validation accuracy is "+accuracy_accumulate/Fold);
				each_accuracy[dif_C][dif_gamma]=accuracy_accumulate/Fold;
				
				
				
				
				
				
				
			}
		}
		
		
		double bestC=0;
		double bestgamma=0;
		
		for(int dif_C=0;dif_C<6;dif_C++){
			for(int dif_gamma=0;dif_gamma<3;dif_gamma++){
				if(each_accuracy[dif_C][dif_gamma]>best_accuracy){
					best_accuracy=each_accuracy[dif_C][dif_gamma];
					bestC=C_array[dif_C];
					bestgamma=gamma_array[dif_gamma];
				}
			}
		}
		
		System.out.println("The best C that has highest accuracy is "+bestC);
		System.out.println("The best gamma that has highest accuracy is "+bestgamma);
		System.out.println("Use the best C and gamma to train whole training set again:");
		
		
		
		
		
		
		
		
		
		
		
		
		double gamma0=bestgamma;
		double C=bestC;
		
		
		
		double gamma_t;
		double[] w= new double[dimension_1];
		
		
		
		for(int i=0;i<count;i++){
			
				dot_product=0;
				for(int j=0;j<dimension_1;j++){
					dot_product+=w[j]*training_data[i][j];
				}
				gamma_t=gamma0/(1+gamma0*i/C);
				if(training_label[i]*dot_product<=1){
					for(int j=0;j<dimension_1;j++){
						w[j]=(1-gamma_t)*w[j]+gamma_t*C*training_label[i]*training_data[i][j];
					}
				}
				else{
					for(int j=0;j<dimension_1;j++){
						w[j]=(1-gamma_t)*w[j];
					}
				}
			
		}
		
		
		
		
		
		
		System.out.println("Training complete, the first 10 dimension of w is (first dimension is bias)");
		/*for(int j=0;j<dimension_1;j++){
			System.out.print(w[j]+"  ");
		}*/
		for(int j=0;j<10;j++){
			System.out.print(w[j]+"  ");
		}
		System.out.println("");
		
		
		double correct_num_train=0;
		double total_num_train=0;
		
		for(int i=0;i<count;i++){
			
				dot_product=0;
				for(int j=0;j<dimension_1;j++){
					dot_product+=w[j]*training_data[i][j];
				}
				total_num_train++;
				if(training_label[i]*dot_product>0||(training_label[i]>0&&dot_product==0))
					correct_num_train++;
			
		}
		
		
		
		
		System.out.println("Training accuracy="+correct_num_train/total_num_train);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//test!!!!!!
		
		
		
		double correct_num_test=0;
		double total_num_test=0;
		
		for(int i=0;i<count;i++){
			dot_product=0;
			for(int j=0;j<dimension_1;j++){
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