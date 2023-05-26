package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pojo.UserPojo;

import Methods.DataBase;
import Methods.UserMethods;

public class LogoutFromAll extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc=request.getServletContext();
		DataBase baseUtil=(DataBase) sc.getAttribute("sqlEmployee");
		Connection connection=baseUtil.getConnector();
		Gson gson=new Gson(); 
		PrintWriter out=response.getWriter();
		UserMethods uM=new UserMethods();
		UserPojo up=gson.fromJson(request.getReader(), UserPojo.class);
		String v=uM.logoutFromAll(up.getUsername(), up.getPassword(), connection);
		if(v!=null)
		{
			
			out.println("Logged out From all Devices."+"\n"+"You new Token is");
			out.print(v);

		}
		else {
			out.print("Invalid");
		}
	}

}
