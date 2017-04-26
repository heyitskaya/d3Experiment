
/** 
Node 
list of children
String id
string name description
string image file
Node parent=null;

printNodePage(Node){


}**/
import java.util.*;
public class Node {
	ArrayList<Node> children;
	String id;
	String name;
	String description ;
	Node parent; //we only let it have one parent
	String image;
	
	public Node(){
		children= new ArrayList<Node>();
	}
	public Node(String name,ArrayList<Node> children){
		this.name=name;
		this.children=children;
	}
	
	public Node(String name){
		this.name=name;
		
	}
	
	public void printNodePage(Node n){
		//print <script src="/jsgraph/7.js">
	}
	
	

}
