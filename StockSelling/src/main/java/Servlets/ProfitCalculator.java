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

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import Methods.AllMethods;
import Methods.DataBase;
import Pojo.DetailsPojo;


public class ProfitCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger("ProfitCalculator");     

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		JSONObject jsonObject = new JSONObject();

		ServletContext sc=request.getServletContext();
		
		DataBase baseUtil=(DataBase) sc.getAttribute("DB_Connection");
		
		Connection connection=baseUtil.getConnector();//getting data base connection  
		Gson gson=new Gson(); 
		PrintWriter out=response.getWriter();
		AllMethods allMethods=new AllMethods();
		
		//getting input from user in json format
		DetailsPojo  detailsPojo=gson.fromJson(request.getReader(), DetailsPojo.class);
		
		String  maxProfitOrMinLoss=allMethods.profitOrLoss(detailsPojo, connection);
		
		response.setContentType("application/JSON");
		
		jsonObject.put("Message", maxProfitOrMinLoss);
		
		out.print(jsonObject.toString());
		
		try {
			if(connection!=null) {
				
				connection.close();				
			}
		} catch (SQLException e) {
			log.error(e.getMessage()+" Error while closing db connection");
		}
	}

}
