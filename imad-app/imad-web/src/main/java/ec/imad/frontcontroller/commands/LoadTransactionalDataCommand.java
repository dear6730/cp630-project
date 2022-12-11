package ec.imad.frontcontroller.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Random;

import javax.servlet.ServletException;

import ec.imad.jpa.db.ImadDBInsert;
import ec.imad.jpa.model.Category;
import ec.imad.jpa.model.Location;
import ec.imad.jpa.model.Product;

public class LoadTransactionalDataCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		int categoryRecords = 10;
		ImadDBInsert dbInsert = new ImadDBInsert();
		dbInsert.insertInitialLoad();
		out.println("Initial load has been completed.");
		
		// if(this.insertCategories(categoryRecords)){
		// 	out.write("It has been added "+categoryRecords+" categories.\n");
		// 	if(this.insertProducts(categoryRecords)){
		// 		out.write("It has been added "+categoryRecords+" products.\n");
		// 	}
		// }

		// if(this.insertLocation(categoryRecords)){
		// 	out.write("It has been added "+categoryRecords+" locations.\n");
		// }
	}

	private boolean insertCategories(int categoryRecords) {
		Category category = null;
		for (int i = 1; i <= categoryRecords; i++) {
			category = new Category("Category " + i);
			categoryDao.saveModel(category);	
		}
		return true;
	}

	private boolean insertProducts(int productsRecords) {
		Product product = null;
		Random randomGenerator = new Random();
		for (int i = 1; i <= productsRecords; i++) {
			product = new Product("Product " + i, new BigDecimal(i*3.14),new Category(randomGenerator.nextInt(10) + 1));
			productDao.saveModel(product);	
		}
		return true;
	}

	private boolean insertLocation(int locationRecords) {
		Location location = null;
		for (int i = 1; i <= locationRecords; i++) {
			location = new Location("City " + i);
			locationDao.saveModel(location);	
		}
		return true;
	}
}