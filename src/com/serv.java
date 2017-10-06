package com;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import model.*;
import helper.*;
import java.io.*;
import java.sql.*;

public class serv extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		//out.println("start");
		eval ob = new eval();
		String d = (String)getServletContext().getInitParameter("driver");
		String p = (String)getServletContext().getInitParameter("path");
		try{
			ArrayList<topic> list = ob.inital(d,p,1001);
			/*for(int i=0;i<list.size();i++){
				topic t = list.get(i);
				out.print(t.getData()+"<br/>");
				ArrayList<topic> sub1 = t.getSub();
				for(int j=0;j<sub1.size();j++){
					out.print(sub1.get(j).getData()+"<br/>");
					ArrayList<topic> sub2 = sub1.get(j).getSub();
					for(int k=0;k<sub2.size();k++){
						out.print(sub2.get(k).getData()+"<br/>");
					}
				}
			}
			*/
			request.setAttribute("topics",list);
		}catch(Exception ex){
			//out.println(ex);
			//out.print(ex.getMessage());
			/*StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString(); // stack trace as a string
			System.out.println(sStackTrace);
			*/
			request.setAttribute("error",ex);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("main.jsp");
		view.forward(request,response);
	}
}