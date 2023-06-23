package Methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pojo.DetailsPojo;

public class AllMethods {
	
	static PreparedStatement quantityPS;
	static ResultSet quantityRS;
	static PreparedStatement assestsTable ;
	static ResultSet assestsTableDetails;
	static PreparedStatement updateAssestsTable ;
	static PreparedStatement deleteDetails ;
	static PreparedStatement moneyDetails;
	static ResultSet moneyRS;

	//static Logger log = Logger.getLogger("AllMethods");  
	
	public String profitOrLoss(DetailsPojo detailsPojo,Connection con) {
		
		String userSymbol=detailsPojo.getSymbol();
		int quantity=detailsPojo.getQuantity();
		int bidPrice=detailsPojo.getBuyPrice();
		
		int moneyMade = 0;
		
		int count=countReturn(userSymbol, con);//getting the total quantity of stock from db
		
		if(count>=quantity && quantity>0 && bidPrice>0) {
			
			try {
				
				assestsTable=con.prepareStatement(Constants.GET_DETAILS);
				
				assestsTable.setString(1, userSymbol);
				
				try {
					
					assestsTableDetails=assestsTable.executeQuery();/***storing stocks in result set in ascending 
																		order based on buy price***/

					while(assestsTableDetails.next() ) {
						
						String symbol=assestsTableDetails.getString("symbol");//Getting stock symbol from db
						 																				 																				 																
						int askPrice=assestsTableDetails.getInt("buyPrice");//Getting stock buy price from db
						
						int tableQuantity=assestsTableDetails.getInt("quantity");//Getting stock quantity from db
						
						int sellingQuantity=Math.min(quantity, tableQuantity);
						
						int value=(bidPrice-askPrice)*sellingQuantity; //calculating profit of selling stocks
						
						moneyMade+=value;            //updating money
						
						quantity-=sellingQuantity;   //decreasing quantity

						int remainingQuantity=tableQuantity-sellingQuantity;
												
						if(sellingQuantity<tableQuantity) {
							
							
							updateRow(remainingQuantity,symbol, askPrice, con);
						}
						
						else{
							
							deleteRow(askPrice, symbol, con);
							
						}
						
					}
					
					String money = Integer.toString(moneyMade);

					return "The amount you made is "+money;
					
				} catch (Exception e) {
					
					//log.warn(e.getMessage()+" Exception in while loading Result Set");
					e.printStackTrace();
				}
				
			} catch (SQLException e) {
				//log.warn(e.getMessage()+" Exception in While Getting Details");
				e.printStackTrace();
			}
			finally {
				try {
					if(updateAssestsTable!=null) {
						updateAssestsTable.close();

					}
					
					if(assestsTableDetails!=null) {
						assestsTableDetails.close();

					}
					if(assestsTable!=null) {
						assestsTable.close();

					}
					if(quantityRS!=null) {
						quantityRS.close();

					}
					if(quantityPS!=null) {
						quantityPS.close();

					}
					if(deleteDetails!=null) {
						deleteDetails.close();

					}
					
				} catch (SQLException e) {
				//	log.warn(e.getMessage()+" Exception in closing ststements");
					e.printStackTrace();
				}
			}
		}
		else if(count<quantity && quantity>0){
			return "The quantity given by the user is greater than the existing quantity or the stock "+userSymbol+" given does not exists" ;
		}
		
		else {
			return "Enter a positive quantity value of bidprice or quantity";
		}
		return null;		
	}
	
	
	public void updateRow(int remainingQuantity,String symbol,int askPrice,Connection con) throws SQLException {
		//updating table after selling stocks
		updateAssestsTable=con.prepareStatement(Constants.UPDATE_TABLE);
		updateAssestsTable.setInt(1, remainingQuantity);
		updateAssestsTable.setString(2, symbol);
		updateAssestsTable.setInt(3, askPrice);
		updateAssestsTable.execute();
	}
	
	public void deleteRow(int askPrice,String symbol, Connection con) throws SQLException {
		
		/* deleting a row if the quantity of a stock becomes zero */
		deleteDetails=con.prepareStatement(Constants.DELETE_DEATILS);
		deleteDetails.setString(1, symbol);
		deleteDetails.setInt(2, askPrice);
		deleteDetails.execute();
		
	}
	
	
	public int countReturn(String userSymbol,Connection con ) {
		
		int count=0;
		try {
			quantityPS=con.prepareStatement(Constants.GET_QUANTITY);
			quantityPS.setString(1, userSymbol);
			try {
				quantityRS=quantityPS.executeQuery();
				while(quantityRS.next()) {
					count+=quantityRS.getInt("quantity");
				}
			}catch (Exception e) {		
				//(e.getMessage()+" Exception in checking Quantity PreparedStatement");
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			//(e1.getMessage()+" Exception in checking Quantity Result set");
			e1.printStackTrace();
		}
		return count;
	}
	

}
