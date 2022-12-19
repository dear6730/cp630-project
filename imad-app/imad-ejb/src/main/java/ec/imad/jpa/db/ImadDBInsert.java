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

			insertLocation();
			insertCategory();
			insertProduct();
			insertStock();
			insertOrder(TOTAL_RECORDS);
			insertOrderItem(TOTAL_RECORDS);

			insertHistoricalStock();

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

	private void insertLocation() throws SQLException {
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

	private void insertCategory() throws SQLException {
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

	private void insertProduct() throws SQLException {
		String sql1="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Cannondale Topstone 4 Bicycle',1734.95,1,2,'50001')";
		String sql2="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Polygon Xtrada 7 Bike',1449.95,1,2,'50002')";
		String sql3="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Intense Sniper T Expert Bike',5899.95,1,2,'50003')";
		String sql4="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Ridley Liz SL40 Bicycle',3799.95,1,12,'50004')";
		String sql5="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Cannondale Trail 16 Bicycle - Children',469.95,1,2,'50005')";
		String sql6="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Runners Wooden Push Bicycle - Children',104.94,1,2,'50006')";
		String sql7="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Polygon Xtrada 6 1 x 11 Bike',1249.95,1,2,'50007')";
		String sql8="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('MEC Synergy Gore-Tex Jacket',469.95,2,5,'50008')";
		String sql9="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('MEC Fair Trade T-Shirt',29.95,2,5,'50009')";
		String sql10="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Patagonia Cotton Flannel Shirt ',86.94,2,5,'50010')";
		String sql11="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Brooks Long Sleeve Top',48.94,2,5,'50011')";
		String sql12="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Prana Aisley Swimsuit Tankini',49.93,2,5,'50012')";
		String sql13="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Castelli Long Sleeve Jersey',150,2,5,'50013')";
		String sql14="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Pearl Izumi Tank',69.95,2,18,'50014')";
		String sql15="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Darn Tough Sunset Socks',29.95,3,7,'50015')";
		String sql16="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Merrell Trail Running Shoes',74.93,3,7,'50016')";
		String sql17="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Brooks Road Running Shoes',125.95,3,7,'50017')";
		String sql18="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Salomon Gore-Tex Light Trail Shoes',199.95,3,7,'50018')";
		String sql19="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Merrell Waterproof Light Trail Shoes',189.95,3,7,'50019')";
		String sql20="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Fubuki Niseko 2.0 Winter Boots',159.95,3,7,'50020')";
		String sql21="INSERT INTO IMAD_TPRODUCT (name, price, category_id, global_reorder_point, sku) VALUES ('Timberland Rugged Waterproof Boots',189.95,3,6,'50021')";

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

	private void insertStock() throws SQLException {

		String sql1="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (1,1,3)";
		String sql2="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (2,1,0)";
		String sql3="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (3,1,2)";
		String sql4="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (4,1,1)";
		String sql5="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (5,1,2)";
		String sql6="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (6,1,3)";
		String sql7="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (7,1,1)";
		String sql8="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (8,1,16)";
		String sql9="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (9,1,18)";
		String sql10="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (10,1,20)";
		String sql11="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (11,1,22)";
		String sql12="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (12,1,24)";
		String sql13="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (13,1,26)";
		String sql14="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (14,1,3)";
		String sql15="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (15,1,30)";
		String sql16="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (16,1,32)";
		String sql17="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (17,1,34)";
		String sql18="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (18,1,36)";
		String sql19="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (19,1,38)";
		String sql20="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (20,1,40)";
		String sql21="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (21,1,0)";
		String sql22="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (1,2,2)";
		String sql23="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (2,2,0)";
		String sql24="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (3,2,1)";
		String sql25="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (4,2,1)";
		String sql26="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (5,2,1)";
		String sql27="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (6,2,2)";
		String sql28="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (7,2,0)";
		String sql29="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (8,2,24)";
		String sql30="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (9,2,27)";
		String sql31="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (10,2,30)";
		String sql32="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (11,2,33)";
		String sql33="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (12,2,36)";
		String sql34="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (13,2,39)";
		String sql35="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (14,2,3)";
		String sql36="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (15,2,45)";
		String sql37="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (16,2,48)";
		String sql38="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (17,2,51)";
		String sql39="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (18,2,54)";
		String sql40="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (19,2,57)";
		String sql41="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (20,2,60)";
		String sql42="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (21,2,0)";
		String sql43="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (1,3,3)";
		String sql44="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (2,3,2)";
		String sql45="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (3,3,2)";
		String sql46="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (4,3,1)";
		String sql47="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (5,3,2)";
		String sql48="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (6,3,3)";
		String sql49="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (7,3,1)";
		String sql50="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (8,3,32)";
		String sql51="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (9,3,36)";
		String sql52="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (10,3,40)";
		String sql53="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (11,3,44)";
		String sql54="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (12,3,48)";
		String sql55="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (13,3,52)";
		String sql56="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (14,3,3)";
		String sql57="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (15,3,60)";
		String sql58="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (16,3,64)";
		String sql59="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (17,3,68)";
		String sql60="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (18,3,72)";
		String sql61="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (19,3,76)";
		String sql62="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (20,3,80)";
		String sql63="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (21,3,0)";
		String sql64="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (1,4,1)";
		String sql65="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (2,4,2)";
		String sql66="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (3,4,1)";
		String sql67="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (4,4,1)";
		String sql68="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (5,4,1)";
		String sql69="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (6,4,1)";
		String sql70="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (7,4,0)";
		String sql71="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (8,4,40)";
		String sql72="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (9,4,45)";
		String sql73="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (10,4,50)";
		String sql74="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (11,4,55)";
		String sql75="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (12,4,60)";
		String sql76="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (13,4,65)";
		String sql77="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (14,4,3)";
		String sql78="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (15,4,75)";
		String sql79="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (16,4,80)";
		String sql80="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (17,4,85)";
		String sql81="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (18,4,90)";
		String sql82="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (19,4,95)";
		String sql83="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (20,4,100)";
		String sql84="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (21,4,0)";
		String sql85="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (1,5,2)";
		String sql86="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (2,5,1)";
		String sql87="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (3,5,2)";
		String sql88="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (4,5,1)";
		String sql89="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (5,5,2)";
		String sql90="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (6,5,4)";
		String sql91="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (7,5,2)";
		String sql92="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (8,5,48)";
		String sql93="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (9,5,54)";
		String sql94="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (10,5,60)";
		String sql95="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (11,5,66)";
		String sql96="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (12,5,72)";
		String sql97="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (13,5,78)";
		String sql98="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (14,5,3)";
		String sql99="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (15,5,90)";
		String sql100="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (16,5,96)";
		String sql101="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (17,5,102)";
		String sql102="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (18,5,108)";
		String sql103="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (19,5,114)";
		String sql104="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (20,5,120)";
		String sql105="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (21,5,0)";
		String sql106="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (1,6,3)";
		String sql107="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (2,6,2)";
		String sql108="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (3,6,2)";
		String sql109="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (4,6,1)";
		String sql110="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (5,6,2)";
		String sql111="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (6,6,3)";
		String sql112="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (7,6,1)";
		String sql113="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (8,6,56)";
		String sql114="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (9,6,63)";
		String sql115="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (10,6,70)";
		String sql116="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (11,6,77)";
		String sql117="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (12,6,84)";
		String sql118="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (13,6,91)";
		String sql119="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (14,6,3)";
		String sql120="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (15,6,105)";
		String sql121="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (16,6,112)";
		String sql122="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (17,6,119)";
		String sql123="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (18,6,126)";
		String sql124="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (19,6,133)";
		String sql125="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (20,6,140)";
		String sql126="INSERT INTO IMAD_TSTOCK (PRODUCT_ID, LOCATION_ID, QUANTITY) VALUES (21,6,0)";

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


	public void insertHistoricalStock() throws SQLException {

		String sql1="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (1,1,3,2022,4)";
		String sql2="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (2,1,0,2022,5)";
		String sql3="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (3,1,2,2022,6)";
		String sql4="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (4,1,1,2022,7)";
		String sql5="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (5,1,2,2022,8)";
		String sql6="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (6,1,3,2022,9)";
		String sql7="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (7,1,1,2022,4)";
		String sql8="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (8,1,16,2022,5)";
		String sql9="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (9,1,18,2022,6)";
		String sql10="INSERT INTO IMAD_THISTORICAL_STOCK (PRODUCT_ID, LOCATION_ID, QUANTITY, YEAR, MONTH) VALUES (10,1,20,2022,7)";

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

		iModelUpdated = statement.executeBatch();
		logger.info("Inserting new " + iModelUpdated.length + " rows at IMAD_THISTORICAL_STOCK");


	}
}