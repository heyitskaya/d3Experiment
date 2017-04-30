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
		woody.description="A woody plant is a plant that produces wood as its structural tissue. Woody plants are usually either trees, shrubs, or lianas. These are usually perennial plants whose stems and larger roots are reinforced with wood produced from secondary xylem. The main stem, larger branches, and roots of these plants are usually covered by a layer of bark. Wood is a structural cellular adaptation that allows woody plants to grow from above ground stems year after year, thus making some woody plants the largest and tallest terrestrial plants.";
		
		//Node herb= new Node("In general use, herbs are any plants used for food, flavoring, medicine, or fragrances for their savory or aromatic properties. Culinary use typically distinguishes herbs from spices. Herbs refers to the leafy green or flowering parts of a plant (either fresh or dried), while spices are produced from other parts of the plant (usually dried), including seeds, berries, bark, roots and fruits.");
		Node herb= new Node("herb");
		herb.description="In general use, herbs are any plants used for food, flavoring, medicine, or fragrances for their savory or aromatic properties. Culinary use typically distinguishes herbs from spices. Herbs refers to the leafy green or flowering parts of a plant (either fresh or dried), while spices are produced from other parts of the plant (usually dried), including seeds, berries, bark, roots and fruits.";
		Node kaya= new Node("someone");
		kaya.description="kaya";
		ArrayList<Node> l= new ArrayList<Node>();
		l.add(woody);
		l.add(herb);
		l.add(kaya);
		Node vascular= new Node("Vascular",l);
		vascular.description="Vascular plants (from Latin vasculum: duct), also known as tracheophytes (from the equivalent Greek term trachea) and also higher plants, form a large group of plants (c. 308,312 accepted known species) that are defined as those land plants that have lignified tissues (the xylem) for conducting water and minerals ...";
		root=vascular;
		vascular.id="root";
		
		woody.id="Woody";
		herb.id="Herb";
		kaya.id="Kaya";
		Node tree= new Node("Tree");
		tree.description="In botany, a tree is a perennial plant with an elongated stem, or trunk, supporting branches and leaves in most species.";
		tree.id="Tree";
		Node shrub= new Node("Shrub");
		shrub.description="A shrub or bush is a small to medium-sized woody plant. It is distinguished from a tree by its multiple stems and shorter height, usually under 6 m (20 ft) tall. Plants of many species may grow either into shrubs or trees, depending on their growing conditions.";
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
