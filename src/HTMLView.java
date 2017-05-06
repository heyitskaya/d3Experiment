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
	
}
