package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import Methods.AllMethods;
import Methods.DataBase;
import Pojo.DetailsPojo;


public class ProfitCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson=new Gson(); 

		JSONObject jsonObject = new JSONObject();
		AllMethods obj1 = new AllMethods();
		ServletContext context=request.getServletContext();
		DataBase ob = (DataBase) context.getAttribute("dbcon");
		try{
			Connection con = ob.getConnection();
			DetailsPojo json = gson.fromJson(request.getReader(),DetailsPojo.class);
			PrintWriter out = response.getWriter();
			String str = obj1.profitOrLoss(json, con);
			response.setContentType("application/JSON");
			jsonObject.put("message",str);
			out.println(jsonObject);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
