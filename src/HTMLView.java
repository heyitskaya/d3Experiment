//package edu.mtholyoke.cs341bd.bookz;
//import javax.servlet.http.Cookie. *;
import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class HTMLView {

	private String metaURL;

	public HTMLView(String baseURL) {
		this.metaURL = "<base href=\"" + baseURL + "\">";
	}

	/**
	 * HTML top boilerplate; put in a function so that I can use it for all the
	 * pages I come up with.
	 * 
	 * @param html
	 *            where to write to; get this from the HTTP response.
	 * @param title
	 *            the title of the page, since that goes in the header.
	 */
	
	/**given a file name returns the string representation of the entire HTML**/
	
	public void printHTMLForNode(PrintWriter html,Node n)
	{
		//get n from the path
		//go into the model to get correct children of this node and print to javascipt file
		
		
	}
	public void printHTMLTop(PrintWriter html)
	{
		html.println(readHTMLFile("IndexTop.html"));
	}
	
	public void printHTMLBottom(PrintWriter html)
	{
		html.println(readHTMLFile("IndexBottom.html"));
	}
	
	public String readHTMLFile(String fileName)
	{
		StringBuilder contentBuilder = new StringBuilder();
		try {
			
		    BufferedReader in = new BufferedReader(new FileReader(fileName));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str).append('\n'); //put the spaces back in because it matters for javascript
		    }
		    in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = contentBuilder.toString();
		return content;
		
	}
	public void printPageStart(PrintWriter html, String title){
		String s=readHTMLFile("index.html");
		html.println(s);
	}
	
/**	void printPageStart(PrintWriter html, String title) {
		html.println("<!DOCTYPE html>"); // HTML5
		html.println("<html>");
		html.println("  <head>");
		html.println("    <title>" + title + "</title>");
		html.println("    " + metaURL);
		html.println("    <link type=\"text/css\" rel=\"stylesheet\" href=\"" + getStaticURL("bookz.css") + "\">");
		html.println("  </head>");
		html.println("  <body>");
		html.println("  <a class='none' href='/front'><h1 class=\"logo\">"+title+"</h1></a>");
	} **/

	public String getStaticURL(String resource) {
		return "static/" + resource;
	}

	/**
	 * HTML bottom boilerplate; close all the tags we open in
	 * printPageStart.
	 *
	 * @param html
	 *            where to write to; get this from the HTTP response.
	 */
	void printPageEnd(PrintWriter html) {
		html.println("  </body>");
		html.println("</html>");
	}

	void printSearchForm(PrintWriter html) {
		html.println("<form method='GET' action='/search'>");
		html.println("<input type='text' placeholder='Search...' name='q' />");
		html.println("<input type='submit' value='Go!' />");
		html.println("</form>");
	}
	
	public void logIn(){
		System.out.println("you're logging in ");
	}
	public void printLogin(PrintWriter html){
		html.println("<form action='/login' method='POST'>");
		html.println("<label>Username: ");
		html.println("<input type=\"text\" name=\"user\"> <br>");
		html.println("<input type='submit' value='Login' />");
		html.println("</label>");
		html.println("</form>");
	}
	public static void main(String[] args)
	{
		HTMLView view= new HTMLView("haha");
		String s=view.readHTMLFile("index.html");
		System.out.println(s);
		
	}
	
}
