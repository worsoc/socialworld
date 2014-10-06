package org.socialworld.datasource;

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;*/
import java.sql.*;

import org.socialworld.objects.God;
import org.socialworld.objects.WriteAccessToSimulationObject;

public class LoadGod extends LoadSimulationObjects {

	private static LoadGod instance;

	/**
	 * The method gets back the only instance of the LoadItem.
	 * 
	 * @return singleton object of LoadHuman
	 */
	public static LoadGod getInstance() {
		if (instance == null) {
			instance = new LoadGod();
			
		}
		return instance;
	}

	@Override
	public God getObject(int objectID) {
		
		God createdGod = new God();
		WriteAccessToSimulationObject god = new WriteAccessToSimulationObject(createdGod);
		

		initObject(god, objectID);	
		
		return createdGod;
	}

	protected void selectAllForID(int ObjectID){
		
	}
	
	public void createTable() throws Exception {
		Connection  connection;
		Class.forName("org.mariadb.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test1", "root", "Aragorn");
		Statement stmt;
		stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE a (id int not null primary key, value varchar(20))");
		stmt.close();
		connection.close();
	}
}
