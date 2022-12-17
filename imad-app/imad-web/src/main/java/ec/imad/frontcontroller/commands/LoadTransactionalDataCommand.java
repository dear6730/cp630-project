package ec.imad.frontcontroller.commands;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import ec.imad.jpa.db.ImadDBInsert;

public class LoadTransactionalDataCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("Initial load has been started.");
		new ImadDBInsert().insertInitialLoad();
	}
}