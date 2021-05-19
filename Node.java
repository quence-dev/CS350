public class Node {
	private int hint;
	private int solution;
	private boolean leaf = false;
	private Node [] children = new Node[4];

	public Node(int hint){
		this.hint = hint;
	}

	public void setChildren(Node n1, Node n2, Node n3, Node n4) {
		this.children[0] = n1;
		this.children[1] = n2;
		this.children[2] = n3;
		this.children[3] = n4;
	}

	public Node getChild(int i){
		return this.children[i];
	}

	public void setLeaf(){
		this.leaf = true;
	}

	public void setSolution(int solution){
		this.solution = solution;
	}

	public int getHint(){
		return this.hint;
	}

	public int getSolution(){
		return this.solution;
	}

	public boolean getLeaf(){
		return this.leaf;
	}

}