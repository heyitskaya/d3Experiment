import java.util.*;
public class Graph {
	

//webpages for every piece of the graph when click on it 
	//just like p1 when we did the post
	//posts with comments of it as a graphs
	//same idea 
	//print different pages for every node
	//node children parent
	// data.js is returned by the server
	//when we ask for node 1 print javascript for node 1
	//html page
	//index.html
	
	
	
	
	//parent Vascular               
	
	/**		vascular   
			/      \       \
		woody		herb   kaya
		/
		  \
	tree   shrub
		
		
		*
		*
		**/
			
	
	
	//child woody
	//constructing the graph
	Node root;
	public Graph(){
		Map<String,Node> map;
		//get the string such as node "Vascular" from the 
		//URL and map it to a node
		//descriptions
		//show siblings and children
	
		Node woody= new Node("Woody");
		
		Node herb= new Node("Herb");
		Node kaya= new Node("Kaya");
		ArrayList<Node> l= new ArrayList<Node>();
		l.add(woody);
		l.add(herb);
		l.add(kaya);
		Node vascular= new Node("Vascular",l);
		root=vascular;
		vascular.id="root";
		
		woody.id="Woody";
		herb.id="Herb";
		kaya.id="Kaya";
		Node tree= new Node("Tree");
		tree.id="Tree";
		Node shrub= new Node("Shrub");
		shrub.id="Shrub";
		ArrayList<Node> temp= new ArrayList<Node>();
		temp.add(tree);
		temp.add(shrub);
		woody.children=temp;
	}
	/** given the name of the node return the node, tree traversal 
	 * **/
	public Node findNode(String s)
	{
		if(this.root==null){ //if the root is null 
			return null;
		}
		if(s.equals(root.id))
		{
			return root;
		}
		 Queue<Node> q = new LinkedList<Node>();
	     q.add(root);
	     while(!q.isEmpty()) //while the queue is not empty
	     {
	    	Node currNode=q.remove(); //remove currNode from the queue
	    	if(currNode.children!=null) //if it has children
	    	{
	    		//add all the children of currNode
	    		for(Node child:currNode.children) //iterate through the children
	    		{
	    			if(child.id.equals(s)) //once we've found it return
	    			{
	    				return child;
	    			}
	    			else //continue with the search
	    			{
	    				q.add(child); //add the children 
	    			}
	    		}
	    	}
	    	
	     }
	     return null;
	}
	
	public ArrayList<Node> findChildren(Node n)
	{
		return n.children;
	}
	
	
	//handing to javascript a list of edges, source target, label
/**	public static void main(String[] args) {
		Graph g = new Graph();
		System.out.println("\"");
	
		//for every node in our tree
		//for every child that the starting node has
		for(Node n:g.root.children){
			//{source: "Microsoft", target: "Amazon", type: "licensing"},
			StringBuilder sb= new StringBuilder("");
			sb.append("{source:");
			sb.append("\"");
			sb.append(g.root.name );
			sb.append("\"");
			sb.append(", target:");
			sb.append("\"");
			sb.append(n.name);
			sb.append("\"");
			sb.append(", type:");
			sb.append("\"licensing\"}");
			System.out.println(sb.toString());
		}
		
		Node rootNode=g.findNode("2");
		System.out.println("---------");
		System.out.println(rootNode.id);
		ArrayList<Node> list=rootNode.children;
		System.out.println("----------");

		//System.out.println("----------");
		
		
	} **/
	
	
	
	
	
	
	
	
}
