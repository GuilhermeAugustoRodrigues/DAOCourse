package app;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = new Seller(null, "Potato", "Potato@head.com", new Date(), 1234.56, new Department(2, null));
        sellerDao.insert(seller);
        System.out.println(seller.getId());
        Department department = new Department(2, "Teste");
        List<Seller> sellers = sellerDao.getSellerByDepartment(department);
        sellers.forEach(System.out::println);
        List<Seller> allSellers = sellerDao.getAllSellers();
        allSellers.forEach(System.out::println);
    }
}
