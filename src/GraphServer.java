//package edu.mtholyoke.cs341bd.bookz;
import java.util. *;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
/**
 * @author jfoley
 */
public class GraphServer extends AbstractHandler {
	Server jettyServer;
	HTMLView view;
	Model model;

	public GraphServer(String baseURL, int port) throws IOException {
		view = new HTMLView(baseURL);
		jettyServer = new Server(port);
		model = new Model();

		// We create a ContextHandler, since it will catch requests for us under
		// a specific path.
		// This is so that we can delegate to Jetty's default ResourceHandler to
		// serve static files, e.g. CSS & images.
		ContextHandler staticCtx = new ContextHandler();
		staticCtx.setContextPath("/static");
		ResourceHandler resources = new ResourceHandler();
		resources.setBaseResource(Resource.newResource("static/"));
		staticCtx.setHandler(resources);
		// This context handler just points to the "handle" method of this
		// class.
		ContextHandler defaultCtx = new ContextHandler();
		defaultCtx.setContextPath("/");
		defaultCtx.setHandler(this);
		// Tell Jetty to use these handlers in the following order:
		ContextHandlerCollection collection = new ContextHandlerCollection();
		collection.addHandler(staticCtx);
		collection.addHandler(defaultCtx);
		jettyServer.setHandler(collection);
	
	}
	/**
	 * Once everything is set up in the constructor, actually start the server
	 * here:
	 * 
	 * @throws Exception
	 *             if something goes wrong.
	 */
	public void run() throws Exception {
		jettyServer.start();
		jettyServer.join(); // wait for it to finish here! We're using threads behind the scenes; so this keeps the main thread around until something can happen!
	}

	/**
	 * The main callback from Jetty.
	 * 
	 * @param resource
	 *            what is the user asking for from the server?
	 * @param jettyReq
	 *            the same object as the next argument, req, just cast to a
	 *            jetty-specific class (we don't need it).
	 * @param req
	 *            http request object -- has information from the user.
	 * @param resp
	 *            http response object -- where we respond to the user.
	 * @throws IOException
	 *             -- If the user hangs up on us while we're writing back or
	 *             gave us a half-request.
	 * @throws ServletException
	 *             -- If we ask for something that's not there, this might
	 *             happen.
	 */
	@Override
	public void handle(String resource, Request jettyReq, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {		
		Map<String,String[]> map= req.getParameterMap();
		ServerRequest request = new ServerRequest(req, resp);
		// if some asks for data.js, say this:
		if("/data.js".equals(request.path)){
			String nodeID = request.getParameter("node", "root");
			System.out.println("nodeID "+nodeID);
			Node node = model.graph.findNode(nodeID);
			System.out.println("node is null "+node==null);
			
			ArrayList<Node> children=model.graph.findChildren(node);
			System.out.println("children is null "+children);
			if(children==null)
			{
				System.out.println("if you know anything");
				//return; //don't do anything
				//we just print the current page
				
				resp.sendRedirect("/data.js?node=root"); 
			}
			
			try (PrintWriter out = resp.getWriter()) {
				view.printHTMLTop(out);
				
				out.println("<script>var links = [");
				// edges!
				for(Node n: children) {
					
					StringBuilder sb= new StringBuilder("");
					sb.append("{source:");
					sb.append("\"");
					sb.append(node.name ); //changed this
					sb.append("\"");
					sb.append(", target:");
					sb.append("\"");
					sb.append(n.name);
					sb.append("\"");
					sb.append(", type:");
					sb.append("\"licensing\"},");
					out.println(sb.toString());
				}
				out.println("];");
				
				out.println("var extraNodeInfo = {};");
				out.println("var nodePhoto={};");
				
				out.println("extraNodeInfo['"+node.name+"']= {description: '"+node.description+"'};");
				for(Node n : children) {
					
					out.println("extraNodeInfo['"+n.name+"']= {description: '"+n.description+"'};");
				}
				out.println("nodePhoto['"+node.name+"']= {picture: '"+node.image+"'};");
				for(Node n:children){
					out.println("nodePhoto['"+n.name+"']= {picture: '"+n.image+"'};");
				}
				
				
				out.println("</script>");
				
				view.printHTMLBottom(out);
			} 
			return;
		}
	
		
		//if they ask for something else, send them to root:
		resp.sendRedirect("/data.js?node=root"); 
	}
	
	
	
	
}
