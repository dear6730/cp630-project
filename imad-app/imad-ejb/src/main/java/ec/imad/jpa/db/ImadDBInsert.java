package ec.imad.jpa.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

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
			insertMockTotalStockCategory(4);

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
		String sql = "INSERT INTO IMAD_TPRODUCT (name, price, category_id) VALUES (?, ?, ?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setString(1, "Product " + i);
			ps.setBigDecimal(2, new BigDecimal(i*3.14));
			ps.setInt(3, randomGenerator.nextInt(3) + 1);
			ps.addBatch();
		}
		iModelUpdated = ps.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TPRODUCT");
	}

	private void insertStock(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (?, ?, ?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setInt(1, randomGenerator.nextInt(10) + 1);
			ps.setInt(2, randomGenerator.nextInt(10) + 1);
			ps.setInt(3, randomGenerator.nextInt(100) + 1);
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
}