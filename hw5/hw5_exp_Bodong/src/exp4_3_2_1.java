
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class exp4_3_2_1{
	public static int NUM_of_feature_label=257;//number of features plus one label
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Machine Learning hw5 3.2.1==================================================");
		int count=0;
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
		
		
		
		
		String[] string_data=new String[count];
		
		count=0;
		double num;
		try {
			   BufferedReader in = new BufferedReader(new FileReader("train.data"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   
				   
				   StringBuilder builder=new StringBuilder("");
				   
				   
				   
				   String[] strs_line=str.split(" ");
				   training_data[shuffle_array[count]][0]=1;
				   //System.out.print(training_label[count]+"  ");
				   for(int i=1;i<=strs_line.length;i++){
					   num=Double.parseDouble(strs_line[i-1]);
					   training_data[shuffle_array[count]][i]=(num==0?-1:num);
					   if(num==0){
						   builder.append('e');
					   }else{
						   builder.append('p');
					   }
					   
					   
					   //training_data[count][i]=num;
					   //System.out.print(training_data[shuffle_array[count]][i]+"  ");
				   }
				   
				   string_data[shuffle_array[count]]=builder.toString();
				   
				   
				   
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
			   BufferedReader in = new BufferedReader(new FileReader("train.labels"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   training_label[shuffle_array[count]]=(double)Integer.parseInt(str);
				   if(training_label[shuffle_array[count]]>0)
					   string_data[shuffle_array[count]]=string_data[shuffle_array[count]]+"p";
				   else string_data[shuffle_array[count]]=string_data[shuffle_array[count]]+"e";
				   
				   
				   
				   
				   count++;
			   }
			   //System.out.println("count= "+count);
			   
			   
			   
			   
			   
			   in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		int N=5;//number of trees
		int k=8;//number of features for each tree,randomly choose 8 features, then do ID3
		
		
		
		int NUM_of_feature_label=257;//number of features plus one label
		
		
		String[] str_fea=new String[NUM_of_feature_label];//first 256 is features, the last is label
		for(int i=0;i<NUM_of_feature_label;i++){
			str_fea[i]="ep";//p means +1, e means -1
		}
		
		
		
		
		ArrayList<TreeNode> forest=new ArrayList<TreeNode>();
		
		for(int tree_ind=0;tree_ind<N;tree_ind++){
			
			
			
			Integer[] random_pick = new Integer[NUM_of_feature_label-1];//256 features
			for(int j=0;j<NUM_of_feature_label-1;j++){
				random_pick[j]=j;
			}
			//int[] array = new int[]{1, 2, 3, 4}; //does not work
			// Shuffle the elements in the array
			Collections.shuffle(Arrays.asList(random_pick));
			
			
			
			
			
			
			
		   int[] avai_feature=new int[NUM_of_feature_label-1];
		   for(int i=0;i<avai_feature.length-1;i++){
			   if(random_pick[i]<k)
				   avai_feature[i]=1;//randomly choose k features in each tree
		   }
		   
		   TreeNode root_node=buildtree(string_data,avai_feature,str_fea);
		   double accuracy=get_accuracy(string_data,root_node,str_fea);
		   System.out.print("The "+(tree_ind)+"th tree   ");
		   System.out.println("Accuracy=  "+accuracy);
		   //System.out.println("");
		
		   forest.add(root_node);
		
		}
		
		
		//use SVM to learn w after training random forest
		//double training_data_tree[][]=new double[count][N+1];//first dimension is bias
		//double training_label_tree[]=new double[count];
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//train!!!!
		double C=1;
		double gamma0=0.01;
		double gamma_t;
		double[] w= new double[N+1];//first dimension is bias
		
		double dot_product;
		for(int i=0;i<count;i++){
			dot_product=w[0]*1;
			for(int j=1;j<N+1;j++){
				dot_product+=w[j]*(get_decision(string_data[i],forest.get(j-1),str_fea)=='p'?1.0:-1.0);
			}
			gamma_t=gamma0/(1+gamma0*i/C);
			//if(training_label[i]*dot_product<=1){
			if((string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)*dot_product<=1){
				for(int j=0;j<N+1;j++){
					//w[j]=(1-gamma_t)*w[j]+gamma_t*C*training_label[i]*training_data[i][j];
					
					
					if(j>0)
					w[j]=(1-gamma_t)*w[j]+gamma_t*C*(string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)*
							(get_decision(string_data[i],forest.get(j-1),str_fea)=='p'?1.0:-1.0);
					else w[j]=(1-gamma_t)*w[j]+gamma_t*C*(string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)*1;
				}
			}
			else{
				for(int j=0;j<N;j++){
					w[j]=(1-gamma_t)*w[j];
				}
			}
		}
		
		System.out.println("Training complete, the w is (first dimension is bias)");
		for(int j=0;j<=N;j++){
			System.out.print(w[j]+"  ");
		}
		System.out.println("");
		
		
		double correct_num_train=0;
		double total_num_train=0;
		
		for(int i=0;i<count;i++){
			dot_product=w[0]*1;
			for(int j=0;j<N;j++){
				dot_product+=w[j+1]*(get_decision(string_data[i],forest.get(j),str_fea)=='p'?1.0:-1.0);
			}
			total_num_train++;
			//if(training_label[i]*dot_product>0||(training_label[i]>0&&dot_product==0))
				
			if((string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)*dot_product>0||
					((string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)>0&&dot_product==0))
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
		
		
		double[] test_label=new double[count];
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		String[] test_string_data=new String[count];
		
		count=0;
		//double num;
		try {
			   BufferedReader in = new BufferedReader(new FileReader("test.data"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   
				   
				   StringBuilder builder=new StringBuilder("");
				   
				   
				   
				   String[] strs_line=str.split(" ");
				   test_data[count][0]=1;
				   //System.out.print(training_label[count]+"  ");
				   for(int i=1;i<=strs_line.length;i++){
					   num=Double.parseDouble(strs_line[i-1]);
					   test_data[count][i]=(num==0?-1:num);
					   if(num==0){
						   builder.append('e');
					   }else{
						   builder.append('p');
					   }
					   
					   
					   //training_data[count][i]=num;
					   //System.out.print(training_data[shuffle_array[count]][i]+"  ");
				   }
				   
				   test_string_data[count]=builder.toString();
				   
				   
				   
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
			   BufferedReader in = new BufferedReader(new FileReader("test.labels"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
				   test_label[count]=(double)Integer.parseInt(str);
				   if(test_label[count]>0)
					   test_string_data[count]=test_string_data[count]+"p";
				   else test_string_data[count]=test_string_data[count]+"e";
				   
				   
				   
				   
				   count++;
			   }
			   //System.out.println("count= "+count);
			   
			   
			   
			   
			   
			   in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		correct_num_train=0;
		total_num_train=0;
		
		for(int i=0;i<count;i++){
			dot_product=w[0]*1;
			for(int j=0;j<N;j++){
				dot_product+=w[j+1]*(get_decision(test_string_data[i],forest.get(j),str_fea)=='p'?1.0:-1.0);
			}
			total_num_train++;
			//if(training_label[i]*dot_product>0||(training_label[i]>0&&dot_product==0))
				
			if((test_string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)*dot_product>0||
					((test_string_data[i].charAt(NUM_of_feature_label-1)=='p'?1.0:-1.0)>0&&dot_product==0))
				correct_num_train++;
		}
		System.out.println("The testing accuracy is "+correct_num_train/total_num_train);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*count=0;
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
			   int[] avai_feature=new int[22];
			   for(int i=0;i<avai_feature.length;i++)
				   avai_feature[i]=1;
			   TreeNode root_node=buildtree(data,avai_feature);
			   double accuracy=get_accuracy(data,root_node);
			   System.out.println("hw1_A1b_Bodong  Accuracy=  "+accuracy);
			   System.out.println("");
			   
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
		System.out.println("The testing accuracy is "+correct_num_test/total_num_test);*/
		
		
		
		System.out.println("");
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static char get_decision(String oneline,TreeNode root_node,String[] str_fea)
	{
		while(root_node.feature_index!=NUM_of_feature_label-1)
		{
			int position=str_fea[root_node.feature_index].indexOf(oneline.charAt(root_node.feature_index));
			root_node=root_node.children[position];
		}
		return root_node.decision;
	}
	
	
	
	public static double get_accuracy(String[] data, TreeNode root_node,String[] str_fea)
	{
		int count_all=0;
		int count_correct=0;
		for(int i=0;i<data.length;i++)
		{
			count_all++;
			if(get_decision(data[i],root_node,str_fea)==data[i].charAt(NUM_of_feature_label-1))
				count_correct++;
		}
		return (double)count_correct/(double)count_all;
	}
	
	
	public static char majority_e_p(String[] data)
	{
		if(data==null)
			return 'e';
		else
		{
			int count=0;
			int count_e=0;
			for(int i=0;i<data.length;i++)
			{
				if(data[i].charAt(NUM_of_feature_label-1)=='e')
					count_e++;
				count++;
			}
			if(2*count_e>=count)
				return 'e';
			else return 'p';
		}
	}
	
	public static double entropy(double num,double den)
	{
		/*if(den==0||num==0)
			return 0;
		else return (Math.log10(num/den)/Math.log10(2));
		*/
		if(den==0||num==0)
			return 0;
		else return (0-(num/den)*Math.log10(num/den)/Math.log10(2));
		
	}
	
	public static double get_info_gain(String[] data,int feature_index,String[] str_fea)
	{
		int total_num=str_fea[feature_index].length();
		int[] count_each=new int[total_num];
		int[] count_each_e=new int[total_num];
		int count_all=0;
		int count_all_e=0;
		//int[] count_=new int[total_num];
		for(int k=0;k<data.length;k++)
		{
			for(int i=0;i<total_num;i++)
			{
				if(data[k].charAt(feature_index)==str_fea[feature_index].charAt(i))
				{
					count_each[i]++;
					count_all++;
					if(data[k].charAt(NUM_of_feature_label-1)=='e')
					{
						count_each_e[i]++;
						count_all_e++;
					}
				}
			}
		}
		
		double info_gain=entropy((double)count_all_e,(double)count_all);
		for(int i=0;i<total_num;i++)
		{
			if(count_each[i]!=0)
				info_gain=info_gain-((double)count_each[i]/(double)count_all)*entropy((double)count_each_e[i],(double)count_each[i]);
		}
		return info_gain;
		
	}
	
	public static int get_num_with_1_info_gain(String[] data,int[] avai_feature,String[] str_fea)
	{
		if(data==null)
			return -1;
		else
		{
			double[] info_gain=new double[NUM_of_feature_label-1];//NUM_of_feature_label features
			int max_num=-1;
			double max_info_gain=-10000;
			for(int i=0;i<info_gain.length;i++)
			{
				if(avai_feature[i]==1)
				{
					info_gain[i]=get_info_gain(data,i,str_fea);
					if(info_gain[i]>max_info_gain)
					{
						max_info_gain=info_gain[i];
						max_num=i;
					}
				}
			}
			if(max_info_gain>0)
				return max_num;
			else return -1;
		}
	}
	
	public static TreeNode buildtree(String[] data,int[] avai_feature,String[] str_fea)
	{
		int count_avai=0;
		for(int i=0;i<avai_feature.length;i++)
		{
			if(avai_feature[i]==1)
				count_avai++;
		}
		
		if(count_avai==0)//leaf node
		{
			if(majority_e_p(data)=='e')
			{
				TreeNode node = new TreeNode(NUM_of_feature_label-1,'e',null);
				return node;
			}
			else {
				TreeNode node = new TreeNode(NUM_of_feature_label-1,'p',null);
				return node;
			}
		}
		else
		{
			int num_with_1_info_gain=get_num_with_1_info_gain(data,avai_feature,str_fea);
			if(num_with_1_info_gain==-1)//if getting deeper would not improve accuracy
			{
				if(majority_e_p(data)=='e')
				{
					TreeNode node = new TreeNode(NUM_of_feature_label-1,'e',null);
					return node;
				}
				else {
					TreeNode node = new TreeNode(NUM_of_feature_label-1,'p',null);
					return node;
				}
			}
			else
			{
				int num_category=str_fea[num_with_1_info_gain].length();
				TreeNode node = new TreeNode(num_with_1_info_gain,'n',null);
				
				int[] fea_count=new int[num_category];
				for(int i=0;i<data.length;i++)
				{
					
					for(int j=0;j<num_category;j++)
					{
						if(data[i].charAt(num_with_1_info_gain)==str_fea[num_with_1_info_gain].charAt(j))
							fea_count[j]++;
					}
				}
				for(int j=0;j<num_category;j++)//build child tree
				{
					if(fea_count[j]==0)//no data in this category
					{
						//TreeNode node_c0 = new TreeNode(NUM_of_feature_label,'e',null);//'e' is majority
						//node.set_child(node_c0,j);
						avai_feature[num_with_1_info_gain]=0;
						TreeNode node_c0 =buildtree(null,avai_feature,str_fea);
						node.set_child(node_c0,j);
						node_c0.set_parent(node);
						
					}
					else 
					{
						String[] sub_data=new String[fea_count[j]];
						int count_fcj=0;
						for(int m=0;m<data.length;m++)
						{
							if(data[m].charAt(num_with_1_info_gain)==str_fea[num_with_1_info_gain].charAt(j))
							{
								sub_data[count_fcj]=data[m];
								count_fcj++;
							}
						}
						avai_feature[num_with_1_info_gain]=0;
						TreeNode node_next =buildtree(sub_data,avai_feature,str_fea);
						node.set_child(node_next,j);
						node_next.set_parent(node);////////////////////
					}
					
				}
				return node;
			
			
			}
			
			
			
			
			
		}
	}
	
	
	
	
	
	
	
	
	

}