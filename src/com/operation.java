package com;

import model.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class operation extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException,ServletException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		eval obj = new eval();
		String driver = (String)getServletContext().getInitParameter("driver");
		String path = (String)getServletContext().getInitParameter("path");
		int id = Integer.parseInt(request.getParameter("id"));
		int value = Integer.parseInt(request.getParameter("val"));
		String str = request.getParameter("txt");
		int result;
		try{
			result = obj.insert(driver,path,id,str,value);
			out.print(result);
		}catch(Exception ex){
			out.print(ex);
		}
	}
}
