
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

public class hw1_B1a_Bodong {
	
	public static String[] str_fea=new String[]{"bcxfks","fgys","nbcgrpuewy","tf","alcyfmnps",
										"adfn","cwd","bn","knbhgropuewy","et",
										"bcuezr?","fyks","fyks","nbcgopewy","nbcgopewy",
										"pu","nowy","not","ceflnpsz","knbhrouwy","acnsvy","glmpuwd","ep"};
	
	public static int total_N=3822;
	
	public static void main(String[] args) {
		
		
		
		String[] data=new String[total_N];
		String new_str;
		int count=0;
		try {
			   BufferedReader in = new BufferedReader(new FileReader("B_training.data"));
			   String str;
			   
			   while ((str = in.readLine()) != null)
			   {
			       new_str=str.replace(",","");
			       data[count]=new_str;
			       count++;
			   }
			   
			   
			   in.close();
			   int[] avai_feature=new int[22];
			   for(int i=0;i<avai_feature.length;i++)
				   avai_feature[i]=1;
			   TreeNode root_node=buildtree(data,avai_feature);
			   double accuracy=get_accuracy(data,root_node);
			   //System.out.println("Accuracy=  "+accuracy);
			   System.out.println("hw1_B1a_Bodong     Accuracy=  "+accuracy);
			   System.out.println("");
			   
			} catch (IOException e) {
				e.printStackTrace();
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
			return 'e';
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
		else return (Math.log10(num/den)/Math.log10(2));
		*/
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
	
	public static TreeNode buildtree(String[] data,int[] avai_feature)
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
						TreeNode node_c0 =buildtree(null,avai_feature);
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
						TreeNode node_next =buildtree(sub_data,avai_feature);
						node.set_child(node_next,j);
						node_next.set_parent(node);////////////////////
					}
					
				}
				return node;
			
			
			}
			
			
			
			
			
		}
	}

}