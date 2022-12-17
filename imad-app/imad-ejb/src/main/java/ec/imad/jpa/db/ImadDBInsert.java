package ec.imad.jpa.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

public class ImadDBInsert {
    private static final Logger logger = Logger.getLogger(ImadDBInsert.class.getName());  

	@PersistenceContext(unitName="primary")
    private EntityManager entityManager;

	private Connection connection = null;
	private PreparedStatement ps = null;
	private int[] iModelUpdated;

	public void insertInitialLoad() {

		try {
			logger.info("Initializing------------------------------------------");
			logger.info("IMAD_T TABLES");

			connection = ConnectionFactory.getInstance().getConnection();
			final int TOTAL_RECORDS = 10;

			insertLocation(TOTAL_RECORDS);
			insertCategory(3);
			insertProduct(TOTAL_RECORDS);
			insertStock(TOTAL_RECORDS);
			insertOrder(TOTAL_RECORDS);
			insertOrderItem(TOTAL_RECORDS);

			// Mock data for A tables:
			insertMockTotalStockValue();
			// insertMockTotalStockCategory(4);
			insertMockTop5Products();
			//insertMockOverviewStockingIssues();
			insertMockCurrentStateOfStock(TOTAL_RECORDS);

			//for: productsOutOfStockOrNearlyOutOfStock
			insertMockCombinedOutOfStockPercentage();
			insertMockCombinedOutOfStockHeader();


			ps.close();
			logger.info("Ending------------------------------------------------");
		} catch (SQLException e) { // Handle errors for JDBC
			e.printStackTrace();
		} catch (Exception e) { // Handle errors for Class.forName
			e.printStackTrace();
		} finally { 
			ConnectionFactory.getInstance().closeConnection(connection);
		}
	}

	private void insertLocation(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TLOCATION (city) VALUES (?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);

		for (int i = 1; i <= totalRecords; i++) {	
			ps.setString(1, "City " + i);
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TLOCATION");
	}

	private void insertCategory(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TCATEGORY (name) VALUES (?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);

		for (int i = 1; i <= totalRecords; i++) {	
			ps.setString(1, "Category " + i);
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TCATEGORY");
	}

	private void insertProduct(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TPRODUCT (name, price, global_reorder_point, category_id) VALUES (?, ?, ?, ?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setString(1, "Product " + i);
			ps.setBigDecimal(2, new BigDecimal(i*3.14));
			ps.setInt(3, randomGenerator.nextInt(12) + 1);
			ps.setInt(4, randomGenerator.nextInt(3) + 1);
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TPRODUCT");
	}

	private void insertStock(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (?, ?, ?, ?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setInt(1, randomGenerator.nextInt(10) + 1);
			ps.setInt(2, randomGenerator.nextInt(10) + 1);
			Integer q = randomGenerator.nextInt(100);
			if(q%3 == 0)
				q = 0;
			ps.setInt(3, q);
			ps.setInt(4, randomGenerator.nextInt(10) + 2);
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TSTOCK");
	}

	private void insertOrder(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TORDER (LOCATION_ID, DATE, STATUS, REQUIRED_DATE, SHIPPED_DATE) VALUES (?,?,?,?,?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setInt(1, randomGenerator.nextInt(10) + 1);
			ps.setDate(2, new Date(new java.util.Date().getTime()));
			ps.setString(3, "Status");
			ps.setDate(4, new Date(new java.util.Date().getTime()));
			ps.setDate(5, new Date(new java.util.Date().getTime()));
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TORDER");
	}

	private void insertOrderItem(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TORDER_ITEM (ORDER_ID, PRODUCT_ID, QUANTITY, PRICE) VALUES (?,?,?,?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setInt(1, randomGenerator.nextInt(10) + 1);
			ps.setInt(2, randomGenerator.nextInt(10) + 1);
			ps.setInt(3, randomGenerator.nextInt(10) + 1);
			ps.setBigDecimal(4, new BigDecimal(i*3.14));
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TORDER_ITEM");
	}


	private void insertMockTotalStockCategory(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_ATOTAL_STOCK_CATEGORY (NAME,VALUE) VALUES (?,?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();

		for (int i = 1; i <= totalRecords; i++) {	
			ps.setString(1, "Category " + i);
			ps.setInt(2, randomGenerator.nextInt(1000) + 1);
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ATOTAL_STOCK_CATEGORY");
	}

	private void insertMockTotalStockValue() throws SQLException {
		String sql1= "INSERT INTO IMAD_ATOTAL_STOCK_VALUE (CITY,category_name,total_category) VALUES ('Location 1','Category1',"+new BigDecimal(999)+")";
		String sql2= "INSERT INTO IMAD_ATOTAL_STOCK_VALUE (CITY,category_name,total_category) VALUES ('Location 1','Category2',"+new BigDecimal(550)+")";
		String sql3= "INSERT INTO IMAD_ATOTAL_STOCK_VALUE (CITY,category_name,total_category) VALUES ('Location 1','Category3',"+new BigDecimal(300)+")";

		String sql4= "INSERT INTO IMAD_ATOTAL_STOCK_VALUE (CITY,category_name,total_category) VALUES ('Location 2','Category1',"+new BigDecimal(1000)+")";
		String sql5= "INSERT INTO IMAD_ATOTAL_STOCK_VALUE (CITY,category_name,total_category) VALUES ('Location 2','Category2',"+new BigDecimal(550)+")";
		String sql6= "INSERT INTO IMAD_ATOTAL_STOCK_VALUE (CITY,category_name,total_category) VALUES ('Location 2','Category3',"+new BigDecimal(400)+")";
		
		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);
		statement.addBatch(sql6);
		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ATOTAL_STOCK_VALUE");
	}

	private void insertMockTop5Products() throws SQLException {

		// mock the SORTED results going into the A table
		String sql1= "INSERT INTO IMAD_ATOP_5_PRODUCTS (NAME,VALUE) VALUES ('Product 7', '3234.56')";
		String sql2= "INSERT INTO IMAD_ATOP_5_PRODUCTS (NAME,VALUE) VALUES ('Product 2', '2334.56')";
		String sql3= "INSERT INTO IMAD_ATOP_5_PRODUCTS (NAME,VALUE) VALUES ('Product 1', '1234.56')";
		String sql4= "INSERT INTO IMAD_ATOP_5_PRODUCTS (NAME,VALUE) VALUES ('Product 5', '934.56')";
		String sql5= "INSERT INTO IMAD_ATOP_5_PRODUCTS (NAME,VALUE) VALUES ('Product 17', '834.56')";

		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);

		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ATOP_5_PRODUCTS");
	}

	private void insertMockOverviewStockingIssues() throws SQLException {
		String sql = "INSERT INTO IMAD_ASTOCK_ISSUES (percentage_out_of_stock, percentage_nearly_out_of_stock) VALUES (?,?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();

		Integer num = 100;
		Integer oos = randomGenerator.nextInt(num);
		num -= oos;
		Integer noos = randomGenerator.nextInt(num);

		ps.setBigDecimal(1, new BigDecimal(oos));
		ps.setBigDecimal(2, new BigDecimal(noos));
		ps.addBatch();

		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ASTOCK_ISSUES");
	}

	private void insertMockCurrentStateOfStock(int totalRecords) throws SQLException {

		String sql = "INSERT INTO IMAD_ACURRENT_STATE_OF_STOCK (SKU,NAME,QUANTITY,STATUS,STATUS_STATE) VALUES (?,?,?,?,?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();

		for (int i = 1; i <= totalRecords; i++) {	

			int val = randomGenerator.nextInt(15);
			if(val % 5 == 0) { // increase the chance of 0
				val = 0;
			}

			if( val < 8) { // ignoring things with decent stock

				ps.setInt(1, i+50050);
				ps.setString(2, "Product " + i);
				ps.setInt(3, val);
				if(val == 0) {
					ps.setString(4, "Out of Stock");
					ps.setString(5, "Error");
				} else {
					ps.setString(4, "Nearly Out");
					ps.setString(5, "Warning");
				}

				ps.addBatch();
			}
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ACURRENT_STATE_OF_STOCK");
	}

	//productsOutOfStockOrNearlyOutOfStock
	public void insertMockCombinedOutOfStockPercentage() throws SQLException {

		String sql1= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE (MONTH,STOCK) VALUES ('Jan', '26.11')";
		String sql2= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE (MONTH,STOCK) VALUES ('Feb', '23.23')";
		String sql3= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE (MONTH,STOCK) VALUES ('Mar', '11.11')";
		String sql4= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE (MONTH,STOCK) VALUES ('Apr', '15.5')";
		String sql5= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE (MONTH,STOCK) VALUES ('May', '11.26')";
		String sql6= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE (MONTH,STOCK) VALUES ('Jun', '15.9')";

		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);
		statement.addBatch(sql6);

		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE");
	}

	public void insertMockCombinedOutOfStockHeader() throws SQLException {

		String sql1= "INSERT INTO IMAD_ACOMBINED_OUT_OF_STOCK_HEADER (NUMBER,TREND,STATE,DETAILS,TARGET) VALUES ('26','Up','Error','Nov 2022','11')";

		Statement statement = connection.createStatement();
		statement.addBatch(sql1);

		iModelUpdated = statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_ACOMBINED_OUT_OF_STOCK_HEADER");
	}	
}