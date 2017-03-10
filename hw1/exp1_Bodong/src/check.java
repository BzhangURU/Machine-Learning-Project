import java.util.Arrays;

public class check {

	public static void change(int a[])
	{
		a[1]=999;
	}
	//row and column
	/*public class Point{
		int r;
		int c;
		//int ind;
		Point(){r=0;c=0;ind=0;}
		Point(int row_ind,int col_ind){r=row_ind;c=col_ind;}
		//Point(int row_ind,int col_ind){r=row_ind;c=col_ind;ind=col_ind+}
	}*/
	//get the index of neighbors
	public static int[] neighbors_ind(int index,int Row,int Col)
	{
		int r=index/Col;
		int c=index-Col*(index/Col);
		int ind=0;
		//point is at the corner of matrix
		if((r==0||r==Row-1)&&(c==0||c==Col-1))
		{
			int[] neighbors=new int[3];
			while(ind<3)
			{
				for(int i=r-1;i<=r+1;i++)
					for(int j=c-1;j<=c+1;j++)
						if(i>=0&&i<Row&&j>=0&&j<Col&&(i!=r||j!=c))
						{
							neighbors[ind]=i*Row+j;
							ind++;
						}
			}
			return neighbors;
		}
		else if(r==0||r==Row-1||c==0||c==Col-1)
		{
			int[] neighbors=new int[5];
			while(ind<5)
			{
				for(int i=r-1;i<=r+1;i++)
					for(int j=c-1;j<=c+1;j++)
						if(i>=0&&i<Row&&j>=0&&j<Col&&(i!=r||j!=c))
						{
							neighbors[ind]=i*Row+j;
							ind++;
						}
			}
			return neighbors;
		}
		else{
			int[] neighbors=new int[8];
			while(ind<8)
			{
				for(int i=r-1;i<=r+1;i++)
					for(int j=c-1;j<=c+1;j++)
						if(i>=0&&i<Row&&j>=0&&j<Col&&(i!=r||j!=c))
						{
							neighbors[ind]=i*Row+j;
							ind++;
						}
			}
			return neighbors;
		}
		
		
	}
	
	
	
	//transform index to row and column
	/*public Point index2r_c(int index,int row,int col)
	{
		Point rc=new Point(index/col,index-col*(index/col));
		return rc;
	}*/
	
	public static boolean exist_edge(int[][] grid,int a_index, int b_index)
	{
		//int row=grid.length;
		int col=grid[0].length;
		int a_r=a_index/col;
		int a_c=a_index-col*(a_index/col);
		int b_r=b_index/col;
		int b_c=b_index-col*(b_index/col);
		if(Math.abs(a_r-b_r)<=1&&Math.abs(a_c-b_c)<=1&&Math.abs(grid[a_r][a_c]-grid[b_r][b_c])>3)
			return true;
		else return false;
	}
	
	public static boolean exist_path(int[][] grid,int current_index, int new_index,int[] path,int total_num)
	{
		//int row=grid.length;
		int col=grid[0].length;
		int a_r=current_index/col;
		int a_c=current_index-col*(current_index/col);
		int b_r=new_index/col;
		int b_c=new_index-col*(new_index/col);
		
		int i=0;
		while(path[i]>=0&&i<total_num)
		{
			if(path[i]==new_index)
				return false;
			i++;
		}
		
		if(Math.abs(a_r-b_r)<=1&&Math.abs(a_c-b_c)<=1&&Math.abs(grid[a_r][a_c]-grid[b_r][b_c])>3)
			return true;
		else return false;
	}
	
	
	
	//get the max length if part of the path(length: available_length) has been set
	public static int max_path(int[][] grid, int[][] edges,int[] path,int available_length,int total_num,int Row, int Col)
	{
		/*System.out.println("available length: "+available_length);
		System.out.print("path: ");
		for(int check_i=0;check_i<available_length;check_i++)
			System.out.print(grid[path[check_i]/Col][path[check_i]-Col*(path[check_i]/Col)]+"  ");
		System.out.println("");*/
		if(available_length==total_num)
		{
			//System.out.println("available_length==total_num");
			return total_num;
		}
		
		//check whether there is no path any more
		int[] neighbors=neighbors_ind(path[available_length-1],Row,Col);
		int no_path=1;
		for(int i=0;i<neighbors.length;i++)
		{
			if(exist_path(grid,path[available_length-1], neighbors[i],path,total_num))
			 no_path=0;
		}
		if(no_path==1)
		{
			//System.out.println("No path any more");
			return available_length;
		}
		
		int max=0;
		for(int i=0;i<neighbors.length;i++)
		{
			if(exist_path(grid,path[available_length-1], neighbors[i],path,total_num))
			{
				path[available_length]=neighbors[i];
				
				max=Math.max(max, max_path(grid, edges,path,available_length+1,total_num,Row,Col));
				path[available_length]=-1;
			}
		}
		return max;
		
		
	}
	
	public static int longestSequence(int[][] grid)
	{
		int Row=grid.length;
		if(Row==0) return 0;
		int Col=grid[0].length;
		if(Col==0) return 0;
		
		int total_num=Row*Col;
		//create the edges,edges[i][j]==1 means ith vertex is adjacent with jth vertex
		int edges[][]=new int[total_num][total_num];
		for(int i=0;i<total_num;i++)
		{
			int[] neighbors=neighbors_ind(i,Row,Col);
			for(int j=0;j<neighbors.length;j++)
			{
				if(exist_edge(grid,i, neighbors[j]))
				{
					edges[i][neighbors[j]]=1;
					edges[neighbors[j]][i]=1;
				}
				
			}
		}
		/*for(int i1=0;i1<edges.length;i1++)
		{
			for(int i2=0;i2<edges[0].length;i2++)
				System.out.print(edges[i1][i2]+"  ");
			System.out.println("");
		}*/
		
		int path[]=new int[total_num];
		int max_length=0;
		
		//check the maximum length start from each point
		for(int start_i=0;start_i<total_num;start_i++)
		{
			/*System.out.println(" ");
			System.out.println(" ");
			System.out.println("Now look at "+start_i);*/
			Arrays.fill(path, -1);
			path[0]=start_i;
			max_length=Math.max(max_length, max_path(grid, edges,path,1,total_num,Row,Col));
			if(max_length==total_num)
				return total_num;
			
		}
		return max_length;
		
		
		
		
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a=new int[]{1,1,1};
		change(a);
		System.out.println(a[1]);
		int[][] matrix={{4,2,4},{0,3,1},{3,7,9}};
		//int[][] matrix={{1,6,2},{8,3,7},{4,9,5}};
		System.out.println(matrix[0].length);
		System.out.println("The longest Sequence's length= "+longestSequence(matrix));
		
	}

}
