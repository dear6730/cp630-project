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
			// insertMockTotalStockValue();
			// insertMockTotalStockCategory(4);
			insertMockTop5Products();
			//insertMockOverviewStockingIssues();
			//insertMockCurrentStateOfStock(TOTAL_RECORDS);

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
		String sql1="INSERT INTO IMAD_TLOCATION (city) VALUES ('Barrie')";
		String sql2="INSERT INTO IMAD_TLOCATION (city) VALUES ('Burlington')";
		String sql3="INSERT INTO IMAD_TLOCATION (city) VALUES ('Toronto')";
		String sql4="INSERT INTO IMAD_TLOCATION (city) VALUES ('Ottawa')";
		String sql5="INSERT INTO IMAD_TLOCATION (city) VALUES ('North Vancouver')";
		String sql6="INSERT INTO IMAD_TLOCATION (city) VALUES ('Vancouver')";

		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);
		statement.addBatch(sql6);
		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TLOCATION");
	}

	private void insertCategory(int totalRecords) throws SQLException {
		String sql1="INSERT INTO IMAD_TCATEGORY (name) VALUES ('Cycling')";
		String sql2="INSERT INTO IMAD_TCATEGORY (name) VALUES ('Clothing')";
		String sql3="INSERT INTO IMAD_TCATEGORY (name) VALUES ('Footwear')";

		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TCATEGORY");
	}

	private void insertProduct(int totalRecords) throws SQLException {
		String sql1="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Cannondale Topstone 4 Bicycle','50010',1734.95,1,4)";
		String sql2="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Polygon Xtrada 7 Bike','50013',1449.95,1,7)";
		String sql3="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Intense Sniper T Expert Bike','50015',5899.95,1,10)";
		String sql4="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Ridley Liz SL40 Bicycle','50020',3799.95,1,12)";
		String sql5="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Cannondale Trail 16 Bicycle - Children','50021',469.95,1,1)";
		String sql6="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Runners Wooden Push Bicycle - Children','50022',104.94,1,2)";
		String sql7="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Polygon Xtrada 6 1 x 11 Bike','50350',1249.95,1,6)";
		String sql8="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('MEC Synergy Gore-Tex Jacket','50450',469.95,2,4)";
		String sql9="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('MEC Fair Trade T-Shirt','50511',29.95,2,7)";
		String sql10="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Patagonia Cotton Flannel Shirt','50555',86.94,2,10)";
		String sql11="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Brooks Long Sleeve Top','50601',48.94,2,4)";
		String sql12="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Prana Aisley Swimsuit Tankini','50602',49.93,2,7)";
		String sql13="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Castelli Long Sleeve Jersey','50639',150,2,10)";
		String sql14="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Pearl Izumi Tank','50711',69.95,2,12)";
		String sql15="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Darn Tough Sunset Socks','50715',29.95,3,4)";
		String sql16="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Merrell Trail Running Shoes','50180',74.93,3,7)";
		String sql17="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Brooks Road Running Shoes','50811',125.95,3,10)";
		String sql18="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Salomon Gore-Tex Light Trail Shoes','50830',199.95,3,12)";
		String sql19="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Merrell Waterproof Light Trail Shoes','50850',189.95,3,1)";
		String sql20="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Fubuki Niseko 2.0 Winter Boots','50911',159.95,3,2)";
		String sql21="INSERT INTO IMAD_TPRODUCT (name, sku, price, category_id, global_reorder_point) VALUES ('Timberland Rugged Waterproof Boots','50920',189.95,3,6)";

		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);
		statement.addBatch(sql6);
		statement.addBatch(sql7);
		statement.addBatch(sql8);
		statement.addBatch(sql9);
		statement.addBatch(sql10);
		statement.addBatch(sql11);
		statement.addBatch(sql12);
		statement.addBatch(sql13);
		statement.addBatch(sql14);
		statement.addBatch(sql15);
		statement.addBatch(sql16);
		statement.addBatch(sql17);
		statement.addBatch(sql18);
		statement.addBatch(sql19);
		statement.addBatch(sql20);
		statement.addBatch(sql21);
		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TPRODUCT");
	}

	private void insertStock(int totalRecords) throws SQLException {

		String sql1="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (1,1,37,5)";
		String sql2="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (2,1,0,3)";
		String sql3="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (3,1,83,8)";
		String sql4="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (4,1,60,11)";
		String sql5="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (5,1,40,11)";
		String sql6="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (6,1,26,1)";
		// String sql7="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,1,90,9)";
		String sql7="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,1,1,9)";
		String sql8="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (8,1,16,2)";
		String sql9="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (9,1,35,2)";
		String sql10="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (10,1,35,5)";
		String sql11="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (11,1,84,4)";
		String sql12="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (12,1,48,5)";
		String sql13="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (13,1,56,11)";
		String sql14="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (14,1,1,4)";
		String sql15="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (15,1,88,7)";
		String sql16="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (16,1,96,3)";
		String sql17="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (17,1,97,12)";
		String sql18="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (18,1,66,2)";
		String sql19="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (19,1,58,6)";
		String sql20="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (20,1,24,9)";
		// String sql21="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,1,84,8)";
		String sql21="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,1,0,8)";
		String sql22="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (1,2,93,8)";
		String sql23="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (2,2,23,5)";
		String sql24="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (3,2,69,1)";
		String sql25="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (4,2,67,7)";
		String sql26="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (5,2,43,3)";
		String sql27="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (6,2,58,7)";
		// String sql28="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,2,40,7)";
		String sql28="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,2,1,7)";
		String sql29="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (8,2,18,9)";
		String sql30="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (9,2,42,4)";
		String sql31="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (10,2,70,7)";
		String sql32="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (11,2,24,10)";
		String sql33="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (12,2,95,5)";
		String sql34="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (13,2,2,1)";
		String sql35="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (14,2,76,3)";
		String sql36="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (15,2,44,10)";
		String sql37="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (16,2,36,11)";
		String sql38="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (17,2,22,7)";
		String sql39="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (18,2,58,3)";
		String sql40="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (19,2,74,6)";
		String sql41="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (20,2,59,9)";
		// String sql42="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,2,99,8)";
		String sql42="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,2,0,8)";
		String sql43="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (1,3,14,9)";
		String sql44="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (2,3,49,7)";
		String sql45="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (3,3,26,8)";
		String sql46="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (4,3,12,12)";
		String sql47="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (5,3,0,12)";
		String sql48="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (6,3,10,8)";
		// String sql49="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,3,68,4)";
		String sql49="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,3,1,4)";
		String sql50="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (8,3,33,11)";
		String sql51="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (9,3,78,12)";
		String sql52="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (10,3,21,12)";
		String sql53="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (11,3,48,3)";
		String sql54="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (12,3,41,5)";
		String sql55="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (13,3,39,4)";
		String sql56="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (14,3,55,11)";
		String sql57="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (15,3,95,6)";
		String sql58="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (16,3,37,12)";
		String sql59="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (17,3,59,1)";
		String sql60="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (18,3,13,10)";
		String sql61="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (19,3,2,11)";
		String sql62="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (20,3,91,10)";
		// String sql63="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,3,27,1)";
		String sql63="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,3,0,1)";
		String sql64="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (1,4,22,3)";
		String sql65="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (2,4,72,11)";
		String sql66="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (3,4,23,3)";
		String sql67="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (4,4,45,11)";
		String sql68="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (5,4,5,6)";
		String sql69="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (6,4,49,8)";
		// String sql70="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,4,68,7)";
		String sql70="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,4,1,7)";
		String sql71="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (8,4,64,4)";
		String sql72="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (9,4,70,9)";
		String sql73="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (10,4,63,1)";
		String sql74="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (11,4,13,12)";
		String sql75="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (12,4,68,7)";
		String sql76="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (13,4,17,3)";
		String sql77="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (14,4,1,6)";
		String sql78="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (15,4,83,2)";
		String sql79="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (16,4,30,5)";
		String sql80="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (17,4,34,2)";
		String sql81="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (18,4,76,12)";
		String sql82="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (19,4,0,8)";
		String sql83="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (20,4,72,10)";
		// String sql84="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,4,10,7)";
		String sql84="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,4,0,7)";
		String sql85="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (1,5,82,10)";
		String sql86="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (2,5,86,1)";
		String sql87="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (3,5,10,3)";
		String sql88="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (4,5,50,7)";
		String sql89="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (5,5,0,10)";
		String sql90="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (6,5,10,8)";
		// String sql91="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,5,86,10)";
		String sql91="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,5,1,10)";
		String sql92="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (8,5,95,10)";
		String sql93="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (9,5,13,4)";
		String sql94="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (10,5,52,11)";
		String sql95="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (11,5,94,2)";
		String sql96="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (12,5,17,8)";
		String sql97="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (13,5,72,2)";
		String sql98="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (14,5,65,7)";
		String sql99="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (15,5,6,5)";
		String sql100="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (16,5,12,5)";
		String sql101="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (17,5,89,6)";
		String sql102="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (18,5,28,2)";
		String sql103="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (19,5,72,2)";
		String sql104="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (20,5,57,7)";
		// String sql105="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,5,94,9)";
		String sql105="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,5,0,9)";
		String sql106="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (1,6,68,6)";
		String sql107="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (2,6,38,2)";
		String sql108="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (3,6,51,4)";
		String sql109="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (4,6,37,12)";
		String sql110="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (5,6,68,2)";
		String sql111="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (6,6,7,1)";
		// String sql112="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,6,68,11)";
		String sql112="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (7,6,1,11)";
		String sql113="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (8,6,79,1)";
		String sql114="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (9,6,53,6)";
		String sql115="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (10,6,43,8)";
		String sql116="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (11,6,15,12)";
		String sql117="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (12,6,84,3)";
		String sql118="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (13,6,17,2)";
		String sql119="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (14,6,25,10)";
		String sql120="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (15,6,93,4)";
		String sql121="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (16,6,86,8)";
		String sql122="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (17,6,85,1)";
		String sql123="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (18,6,2,12)";
		String sql124="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (19,6,68,12)";
		String sql125="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (20,6,68,4)";
		// String sql126="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,6,46,10)";
		String sql126="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, REORDER_POINT) VALUES (21,6,0,10)";


		Statement statement = connection.createStatement();
		statement.addBatch(sql1);
		statement.addBatch(sql2);
		statement.addBatch(sql3);
		statement.addBatch(sql4);
		statement.addBatch(sql5);
		statement.addBatch(sql6);
		statement.addBatch(sql7);
		statement.addBatch(sql8);
		statement.addBatch(sql9);
		statement.addBatch(sql10);
		statement.addBatch(sql11);
		statement.addBatch(sql12);
		statement.addBatch(sql13);
		statement.addBatch(sql14);
		statement.addBatch(sql15);
		statement.addBatch(sql16);
		statement.addBatch(sql17);
		statement.addBatch(sql18);
		statement.addBatch(sql19);
		statement.addBatch(sql20);
		statement.addBatch(sql21);
		statement.addBatch(sql22);
		statement.addBatch(sql23);
		statement.addBatch(sql24);
		statement.addBatch(sql25);
		statement.addBatch(sql26);
		statement.addBatch(sql27);
		statement.addBatch(sql28);
		statement.addBatch(sql29);
		statement.addBatch(sql30);
		statement.addBatch(sql31);
		statement.addBatch(sql32);
		statement.addBatch(sql33);
		statement.addBatch(sql34);
		statement.addBatch(sql35);
		statement.addBatch(sql36);
		statement.addBatch(sql37);
		statement.addBatch(sql38);
		statement.addBatch(sql39);
		statement.addBatch(sql40);
		statement.addBatch(sql41);
		statement.addBatch(sql42);
		statement.addBatch(sql43);
		statement.addBatch(sql44);
		statement.addBatch(sql45);
		statement.addBatch(sql46);
		statement.addBatch(sql47);
		statement.addBatch(sql48);
		statement.addBatch(sql49);
		statement.addBatch(sql50);
		statement.addBatch(sql51);
		statement.addBatch(sql52);
		statement.addBatch(sql53);
		statement.addBatch(sql54);
		statement.addBatch(sql55);
		statement.addBatch(sql56);
		statement.addBatch(sql57);
		statement.addBatch(sql58);
		statement.addBatch(sql59);
		statement.addBatch(sql60);
		statement.addBatch(sql61);
		statement.addBatch(sql62);
		statement.addBatch(sql63);
		statement.addBatch(sql64);
		statement.addBatch(sql65);
		statement.addBatch(sql66);
		statement.addBatch(sql67);
		statement.addBatch(sql68);
		statement.addBatch(sql69);
		statement.addBatch(sql70);
		statement.addBatch(sql71);
		statement.addBatch(sql72);
		statement.addBatch(sql73);
		statement.addBatch(sql74);
		statement.addBatch(sql75);
		statement.addBatch(sql76);
		statement.addBatch(sql77);
		statement.addBatch(sql78);
		statement.addBatch(sql79);
		statement.addBatch(sql80);
		statement.addBatch(sql81);
		statement.addBatch(sql82);
		statement.addBatch(sql83);
		statement.addBatch(sql84);
		statement.addBatch(sql85);
		statement.addBatch(sql86);
		statement.addBatch(sql87);
		statement.addBatch(sql88);
		statement.addBatch(sql89);
		statement.addBatch(sql90);
		statement.addBatch(sql91);
		statement.addBatch(sql92);
		statement.addBatch(sql93);
		statement.addBatch(sql94);
		statement.addBatch(sql95);
		statement.addBatch(sql96);
		statement.addBatch(sql97);
		statement.addBatch(sql98);
		statement.addBatch(sql99);
		statement.addBatch(sql100);
		statement.addBatch(sql101);
		statement.addBatch(sql102);
		statement.addBatch(sql103);
		statement.addBatch(sql104);
		statement.addBatch(sql105);
		statement.addBatch(sql106);
		statement.addBatch(sql107);
		statement.addBatch(sql108);
		statement.addBatch(sql109);
		statement.addBatch(sql110);
		statement.addBatch(sql111);
		statement.addBatch(sql112);
		statement.addBatch(sql113);
		statement.addBatch(sql114);
		statement.addBatch(sql115);
		statement.addBatch(sql116);
		statement.addBatch(sql117);
		statement.addBatch(sql118);
		statement.addBatch(sql119);
		statement.addBatch(sql120);
		statement.addBatch(sql121);
		statement.addBatch(sql122);
		statement.addBatch(sql123);
		statement.addBatch(sql124);
		statement.addBatch(sql125);
		statement.addBatch(sql126);
		iModelUpdated =  statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_TSTOCK");
	}

	private void insertOrder(int totalRecords) throws SQLException {
		String sql = "INSERT INTO IMAD_TORDER (LOCATION_ID, DATE, STATUS, REQUIRED_DATE, SHIPPED_DATE) VALUES (?,?,?,?,?)";
		ps = (PreparedStatement) connection.prepareStatement(sql);
		Random randomGenerator = new Random();
		
		for (int i = 1; i <= totalRecords; i++) {	
			ps.setInt(1, randomGenerator.nextInt(6) + 1);
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
			ps.setInt(2, randomGenerator.nextInt(21) + 1);
			ps.setInt(3, randomGenerator.nextInt(100) + 1);
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