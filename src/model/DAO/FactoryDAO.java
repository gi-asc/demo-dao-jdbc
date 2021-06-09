package model.DAO;

import java.sql.Connection;

import db.DB;

public class FactoryDAO {

	private static Connection conn = DB.getConnection();

	public static SellerDAO createSellerDAO() {
		return new SellerDAO(conn);
	}
	
	public static DepartamentDAO createDepartament() {
		return new DepartamentDAO(conn);
	}
	
}
