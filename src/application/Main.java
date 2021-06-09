package application;

import model.DAO.DepartamentDAO;
import model.DAO.FactoryDAO;
import model.DAO.SellerDAO;
import model.entities.Departament;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		SellerDAO sellerDAO = FactoryDAO.createSellerDAO();
		Seller seller = sellerDAO.findById(1);
		System.out.println(seller);
		DepartamentDAO dpDAO = FactoryDAO.createDepartamentDAO();
		Departament dp = dpDAO.findById(2);
		System.out.println(dp);
	}

}
