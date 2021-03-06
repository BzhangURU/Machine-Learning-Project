//Author: Bodong Zhang
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/*class TreeNode {
	int depth;
	int feature_index;
	String feature_letters;
	private TreeNode parent;
	private TreeNode[] children=new TreeNode[12];


    public TreeNode (int depth, int feature_index, String feature_letters, TreeNode parent) {
        this.depth = depth;
        this.feature_index = feature_index;
        this.feature_letters=feature_letters;
        this.parent = parent;
        this.children=null;
    }

    public int get_depth() {
        return depth;
    }

    public void set_depth(int depth) {
        this.depth = depth;
    }
    
    public int get_feature_index() {
        return feature_index;
    }

    public void set_feature_index(int feature_index) {
        this.feature_index = feature_index;
    }
    
    public String get_feature_letters() {
        return feature_letters;
    }

    public void set_feature_letters(String feature_letters) {
        this.feature_letters = feature_letters;
    }
    
    public TreeNode get_parent() {
        return parent;
    }

    public void set_parent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode get_child(int num) {
        return children[num];
    }

    public void set_child(TreeNode onechild, int num) {
        this.children[num] = onechild;
    }

    
}*/

public class hw1_C_method2_Bodong {
	
	public static String[] str_fea=new String[]{"bcxfks","fgys","nbcgrpuewy","tf","alcyfmnps",
										"adfn","cwd","bn","knbhgropuewy","et",
										"bcuezr","fyks","fyks","nbcgopewy","nbcgopewy",
										"pu","nowy","not","ceflnpsz","knbhrouwy","acnsvy","glmpuwd","ep"};
	
	public static int[] training6length=new int[]{905,905,905,906,906,907};
	public static int total_Num=905+905+905+906+906+907;
	//public static int total_N=637;
	//public static int total_test_N=1822;
	
	public static int depth_limit=25;//limit max depth
	public static char majority;
	
	public static void main(String[] args) {
		
		String[] files=new String[]{"C_training_00.data","C_training_01.data","C_training_02.data","C_training_03.data","C_training_04.data","C_training_05.data"};
		double[] accuracies=new double[6];
		
		
		
		//method 2 set '?' as majority value of that label
		String[] data0=new String[total_Num];
		   
		   try {
			   BufferedReader in0;
				in0 = new BufferedReader(new FileReader("C_training.data"));
				   String str0;
				   String new_str0;
				   //String[] data0=new String[total_Num];
				   int count0=0;
				   while ((str0 = in0.readLine()) != null)
				   {
				       new_str0=str0.replace(",","");
				       data0[count0]=new_str0;
				       count0++;
				   }
				   in0.close();
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		   char replace_char_if_e=majority_char_at_if_e(data0,10);
		   char replace_char_if_p=majority_char_at_if_p(data0,10);
	
		   
		   System.out.println("hw1_C_method2_Bodong     If e, then the majority character is "+replace_char_if_e);
		   System.out.println("hw1_C_method2_Bodong     If p, then the majority character is "+replace_char_if_p);
		   
		   
		   
		   
		   
		   
		   
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		for(int sample_num=0;sample_num<6;sample_num++)
		{
		
		String[] data=new String[total_Num-training6length[sample_num]];
		String[] test_data=new String[training6length[sample_num]];
		String new_str;
		int count=0;
		try {
			   
			   String str;
			   BufferedReader in;
			   
			   
			   for(int choose=0;choose<6;choose++)
			   {
				   if(choose!=sample_num)
				   {
					   in = new BufferedReader(new FileReader(files[choose]));
					   while ((str = in.readLine()) != null)
					   {
					       new_str=str.replace(",","");
					       if(new_str.charAt(new_str.length()-1)=='e')
					    	   new_str=new_str.replace('?',replace_char_if_e);
					       else new_str=new_str.replace('?',replace_char_if_p);
					       data[count]=new_str;
					       count++;
					   }
					   in.close();
				   }
			   }
			   
			   
			   
			   
			   majority=majority_e_p(data);
			   int[] avai_feature=new int[22];
			   for(int i=0;i<avai_feature.length;i++)
				   avai_feature[i]=1;
			   int depth=0;
			   TreeNode root_node=buildtree(data,avai_feature,depth);
			   
			   int tree_depth=get_depth_of_tree(root_node);
			   
			   System.out.println("hw1_C_method2_Bodong     The depth of tree is  "+tree_depth);
			   
			   
			   count=0;
			   BufferedReader in_test = new BufferedReader(new FileReader(files[sample_num]));
			   while ((str = in_test.readLine()) != null)
			   {
			       new_str=str.replace(",","");
			       //new_str=new_str.replace('?', replace_char);
			       
			       if(new_str.charAt(new_str.length()-1)=='e')
			    	   new_str=new_str.replace('?',replace_char_if_e);
			       else new_str=new_str.replace('?',replace_char_if_p);
			       
			       test_data[count]=new_str;
			       count++;
			   }
			   
			   
			   in_test.close();
			   
			   
			   //System.out.println("feature index= "+root_node.get_feature_index());
			   
			   
			   
			   
			   
			   accuracies[sample_num]=get_accuracy(test_data,root_node);
			   System.out.println("hw1_C_method2_Bodong     Depth Limit = "+depth_limit+"   No. "+sample_num+" Accuracy=  "+accuracies[sample_num]);
			   
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		double average_accuracy=0;
		for(int i=0;i<6;i++)
			average_accuracy=average_accuracy+accuracies[i];
		average_accuracy=average_accuracy/6;
		System.out.println("hw1_C_method2_Bodong     Average accuracy= "+average_accuracy);
		double std=0;
		for(int i=0;i<6;i++)
		{
			std=std+(accuracies[i]-average_accuracy)*(accuracies[i]-average_accuracy);
		}
		std=std/6;
		std=Math.sqrt(std);
		System.out.println("hw1_C_method2_Bodong     Standard deviation= "+std);
		System.out.println("");
		
		
	}//main end
	
	//Setting C   method 2
	public static char majority_char_at_if_e(String[] data0,int index)
	{
		int num_values=str_fea[index].length();
		int[] count0_num=new int[num_values];
		for(int i=0;i<data0.length;i++)
		{
			for(int k=0;k<num_values;k++)
			{
				if(data0[i].charAt(index)==str_fea[index].charAt(k)&&data0[i].charAt(data0[i].length()-1)=='e')
					count0_num[k]++;
			}
		}
		
		char max_char=' ';
		int max_count_num=-1;
		for(int k=0;k<num_values;k++)
		{
			if(count0_num[k]>max_count_num)
			{
				max_char=str_fea[index].charAt(k);
				max_count_num=count0_num[k];
			}
		}
		return max_char;
		
		
	}
	
	
	
	
	
	
	public static char majority_char_at_if_p(String[] data0,int index)
	{
		int num_values=str_fea[index].length();
		int[] count0_num=new int[num_values];
		for(int i=0;i<data0.length;i++)
		{
			for(int k=0;k<num_values;k++)
			{
				if(data0[i].charAt(index)==str_fea[index].charAt(k)&&data0[i].charAt(data0[i].length()-1)=='p')
					count0_num[k]++;
			}
		}
		
		char max_char=' ';
		int max_count_num=-1;
		for(int k=0;k<num_values;k++)
		{
			if(count0_num[k]>max_count_num)
			{
				max_char=str_fea[index].charAt(k);
				max_count_num=count0_num[k];
			}
		}
		return max_char;
		
		
	}
	
	
	
	
	
	
	
	
	
	public static int get_depth_of_tree(TreeNode root_node)
	{
		if(root_node.children[0]==null)
			return 0;
		else
		{
			int number=str_fea[root_node.feature_index].length();
			int[] depth_array=new int[number];
			for(int i=0;i<number;i++)
			{
				depth_array[i]=get_depth_of_tree(root_node.children[i]);
			}
			int max_depth=0;
			for(int i=0;i<number;i++)
			{
				if(depth_array[i]>=max_depth)
					max_depth=depth_array[i];
			}
			return max_depth+1;
		}
	}
	
	public static char get_decision(String oneline,TreeNode root_node)
	{
		while(root_node.feature_index!=22)
		{
			int position=str_fea[root_node.feature_index].indexOf(oneline.charAt(root_node.feature_index));
			root_node=root_node.children[position];
		}
		return root_node.decision;
	}
	
	
	
	public static double get_accuracy(String[] data, TreeNode root_node)
	{
		int count_all=0;
		int count_correct=0;
		for(int i=0;i<data.length;i++)
		{
			count_all++;
			if(get_decision(data[i],root_node)==data[i].charAt(22))
				count_correct++;
		}
		return (double)count_correct/(double)count_all;
	}
	
	
	public static char majority_e_p(String[] data)
	{
		if(data==null)
			return majority;
		else
		{
			int count=0;
			int count_e=0;
			for(int i=0;i<data.length;i++)
			{
				if(data[i].charAt(22)=='e')
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
		else return (Math.log10(num/den)/Math.log10(2));*/
		if(den==0||num==0)
			return 0;
		else return (0-(num/den)*Math.log10(num/den)/Math.log10(2));
	}
	
	public static double get_info_gain(String[] data,int feature_index)
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
					if(data[k].charAt(22)=='e')
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
	
	public static int get_num_with_1_info_gain(String[] data,int[] avai_feature)
	{
		if(data==null)
			return -1;
		else
		{
			double[] info_gain=new double[22];//22 features
			int max_num=-1;
			double max_info_gain=-10000;
			for(int i=0;i<info_gain.length;i++)
			{
				if(avai_feature[i]==1)
				{
					info_gain[i]=get_info_gain(data,i);
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
	
	public static TreeNode buildtree(String[] data,int[] avai_feature,int depth)
	{
		int count_avai=0;
		for(int i=0;i<avai_feature.length;i++)
		{
			if(avai_feature[i]==1)
				count_avai++;
		}
		
		if(count_avai==0||depth>=depth_limit)//leaf node
		{
			
			
			
			if(majority_e_p(data)=='e')
			{
				TreeNode node = new TreeNode(22,'e',null);
				return node;
			}
			else {
				TreeNode node = new TreeNode(22,'p',null);
				return node;
			}
			
			
		}
		else
		{
			int num_with_1_info_gain=get_num_with_1_info_gain(data,avai_feature);
			if(num_with_1_info_gain==-1)//if getting deeper would not improve accuracy
			{
				/*if(data!=null)
				{
					int test_total=0;
					for(test_total=0;test_total<data.length;test_total++)
					{
						System.out.println(data[test_total]);
					}
					System.out.println("");
				}*/
				
				
				if(majority_e_p(data)=='e')
				{
					TreeNode node = new TreeNode(22,'e',null);
					return node;
				}
				else {
					TreeNode node = new TreeNode(22,'p',null);
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
						//TreeNode node_c0 = new TreeNode(22,'e',null);//'e' is majority
						//node.set_child(node_c0,j);
						avai_feature[num_with_1_info_gain]=0;
						TreeNode node_c0 =buildtree(null,avai_feature,depth+1);
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
						TreeNode node_next =buildtree(sub_data,avai_feature,depth+1);
						node.set_child(node_next,j);
						node_next.set_parent(node);////////////////////
					}
					
				}
				return node;
			
			
			}
			
			
			
			
			
		}
	}

}
