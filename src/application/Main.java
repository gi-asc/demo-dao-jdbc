package application;

import model.DAO.FactoryDAO;
import model.DAO.SellerDAO;
import model.entities.Seller;

public class Main {

	public static void main(String[] args) {
		SellerDAO sellerDAO = FactoryDAO.createSellerDAO();
		Seller seller = sellerDAO.findById(1);
		System.out.println(seller);
	}

}
