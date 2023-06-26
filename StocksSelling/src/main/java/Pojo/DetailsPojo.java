package Pojo;

public class DetailsPojo {
	String symbol;
	int buyPrice, quantity;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public DetailsPojo(String symbol, int buyPrice, int quantity) {
		super();
		this.symbol = symbol;
		this.buyPrice = buyPrice;
		this.quantity = quantity;
	}
	

}
