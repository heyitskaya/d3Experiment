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
				//resp.sendRedirect("/data.js?node=root"); //fuck
				resp.sendRedirect("/data.js?node=root"); //fuck
			}
			//System.out.println("it has "+children.size()+"children");
			
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
					// print info.put(name, values) statements for each node
					
					out.println("extraNodeInfo['"+n.name+"']= {description: '"+n.description+"'};");
				}
				out.println("nodePhoto['"+node.name+"']= {picture: '"+node.image+"'};");
				for(Node n:children){
					out.println("nodePhoto['"+n.name+"']= {picture: '"+n.image+"'};");
				}
				
				
				out.println("</script>");
				
				//out.println("console.log('hello from data.js for node="+node+"');");
				view.printHTMLBottom(out);
			} 
			return;
		}
	
		
		//if they ask for something else, send them to root:
		resp.sendRedirect("/data.js?node=root"); //fuck
		
	/**	//create a settings page
		if("/settings".equals(request.path)){
			if("POST".equals(request.method)){
				//handle update of preferences
				boolean newShowRandom=request.hasParameter("showRandom");
				Cookie showRandom=request.getCookie("showRandom");
				if(showRandom== null){
					showRandom=new Cookie("showRandom", "new");
				}
				showRandom.setValue(newShowRandom ? "true" : "false");
				request.sendCookie(showRandom);
			}
			else if("GET".equals(request.method)){
				//view.showSettingsForm(request);
			}
		}
		if("POST".equals(request.method) && "/report".equals(request.path)) {
			model.reportBook(request.getParameter("book", "ERROR"));
			// tell the browser to redirect the user to list of reported books:
			request.resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
			request.resp.setHeader("Location", "/reported");
			try (PrintWriter out = request.resp.getWriter()) {
				out.println("/reported");
			}
			// done.
			return;
		}
		String path = request.path;
		if("/login".equals(path)){
			String userName=Util.join(map.get("user"));
			request.sendCookie(new Cookie("user", userName));
			view.printLoginConfirmation(request.resp.getWriter());
		}
		if("/logout".equals(path)){ //if the user is attempting to log out
			
			view.showFrontPage(model, resp,request);
			
		}
		if("/like".equals(path) ){ //and method is post
			String bookID=Util.join(map.get("book"));
			GutenbergBook book=model.library.get(bookID);
			if(request.hasCookie("user")){
				
				Cookie userCookie=request.getCookie("user"); 
				if(book!=null){
					String userName=userCookie.getValue();
					book.usersLiked.add(userName); //add users name
					
					
					book.numLikes=book.usersLiked.size();
					model.likeBook(bookID); 
					
					
					
				}
				view.displayLikesPage(resp, model.booksLiked);
				
				
			}
			else{ //when it doesnt have that cookie we send them to the login page
				//restart
				view.showFrontPage(model, resp,request);
			}
		}
		
		if (!"GET".equals(request.method)) {
			return;
		}
		if("/robots.txt".equals(path)) {
			// We're returning a fake file? Here's why: http://www.robotstxt.org/
			resp.setContentType("text/plain");
			try (PrintWriter txt = resp.getWriter()) {
				txt.println("User-Agent: *");
				txt.println("Disallow: /");
			}
			return;
		}
		if("/reported".equals(path)) {
			view.showBookCollection(model.getReportedBooks(), request, "Books that have been reported", Collections.emptyMap());
			return;
		}
		if("/search".equals(path)) {
			String query = request.getParameter("q", "");
			Map<String, String> params = Collections.singletonMap("q", query);
			view.showBookCollection(model.searchBooks(query), request, "Books matching '"+query+"'", params);
			return;
		}
		// Title pages
		String titleCmd = Util.getAfterIfStartsWith("/title/", path);
		if(titleCmd != null) {
			char firstChar = titleCmd.charAt(0);
			view.showBookCollection(this.model.getBooksStartingWith(firstChar), request, "Books starting with '"+firstChar+"'", Collections.emptyMap());
			return;
		}
		String authorCmd=Util.getAfterIfStartsWith("/author/",path);
		if(authorCmd!=null && authorCmd.length()!=0){ //might not be null but might also be empty
			//get the first letter
			char firstChar=authorCmd.charAt(0);
			view.showBookCollectionByAuthor(this.model.getAuthorStartingWith(firstChar),request,"Authors starting with",Collections.emptyMap());
		}
		// Per-book pages
		String bookId = Util.getAfterIfStartsWith("/book/", path);
		if(bookId != null) {
			view.showBookPage(this.model.getBook(bookId), resp);
			return;
		}
		//only gonna check when there's an author
		if(path.startsWith("/author")) {
			String firstName=Util.join(map.get("first"));
			String lastName=Util.join(map.get("last"));
			
			HashMap<ArrayList<String>,Author> authorLibrary=model.authorLibrary;
			ArrayList<String> fullName= new ArrayList<String>();
			fullName.add(lastName);
			fullName.add(firstName);
			
			
			Author currAuthor=authorLibrary.get(fullName);
			if(currAuthor!=null){
				PrintWriter html = resp.getWriter();
				view.printBooksBySingleAuthor(html,currAuthor);
				html.close();
			}
		}
		// Front page!
		if ("/front".equals(path) || "/".equals(path)) {
			view.showFrontPage(this.model, resp,request);
			return;
		} **/
	}
	
	//book <--->user defined as string
	//book has a set of users
	//users have a set of books
	//login form only has username, turn username into a cookie give it to brower and browser sends it to us anytime
	
	//recommend only keeping track of one set
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
