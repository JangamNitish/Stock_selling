package Methods;

public class Constants {
	
	public static final String GET_DETAILS="SELECT * FROM assets_table WHERE SYMBOL=? ORDER BY buyPrice ASC";
	public static final String UPDATE_TABLE="UPDATE assets_table SET quantity = ? WHERE symbol = ? AND buyPrice = ?";
	public static final String DELETE_DEATILS = "DELETE FROM assets_table WHERE symbol = ? AND buyPrice = ?";
	public static final String GET_QUANTITY="SELECT quantity FROM assets_table WHERE symbol=?";

}
