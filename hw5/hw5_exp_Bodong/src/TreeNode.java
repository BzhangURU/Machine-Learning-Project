public class TreeNode {
	//int depth;
	int feature_index;
	char decision;//e f n//not leaf node for n
	//String feature_letters;
	public TreeNode parent;
	public TreeNode[] children;


    public TreeNode (int feature_index,char decision, TreeNode parent) {
        
        this.feature_index = feature_index;
        this.decision = decision;
        //this.feature_letters=feature_letters;
        this.parent = parent;
        this.children=new TreeNode[12];//automatically set to null!!!!!!!!!!!never forget to memset here!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    /*public int get_depth() {
        return depth;
    }

    public void set_depth(int depth) {
        this.depth = depth;
    }*/
    
    public int get_feature_index() {
        return feature_index;
    }

    public void set_feature_index(int feature_index) {
        this.feature_index = feature_index;
    }
    
    public int get_decision() {
        return decision;
    }

    public void set_decision(char decision) {
        this.decision = decision;
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
    	//num<=11
        this.children[num] = onechild;
        onechild.parent=this;
    }
}