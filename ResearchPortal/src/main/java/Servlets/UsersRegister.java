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


public class UsersRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc=request.getServletContext();
		DataBase baseUtil=(DataBase) sc.getAttribute("sqlEmployee");
		Connection connection=baseUtil.getConnector();
		Gson gson=new Gson(); 
		PrintWriter out=response.getWriter();
		UserMethods uM=new UserMethods();
		UserPojo up=gson.fromJson(request.getReader(), UserPojo.class);
		if(uM.validUserName(up.getUsername(), connection) && uM.validPassword(up.getPassword(), connection) && uM.emailPhoneVal(up.getEmail(), up.getPhonenumber(), connection)) {
			uM.insertDetails(up, connection);
			out.print("Account Created Sucessfully");
		}
		else {
			out.print("Invalid details");
		}
	
	}

	
}
