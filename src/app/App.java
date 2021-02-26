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
        Seller seller = new Seller(null, "Mister Potato", "mister_potato@head.com", new Date(), 1234.56, new Department(2,null));
        System.out.println(seller);
        sellerDao.insert(seller);
        System.out.println(seller);
        seller.setBaseSalary(9876.54);
        seller.setDepartment(new Department(1, null));
        sellerDao.update(seller);
        seller = sellerDao.getById(seller.getId());
        System.out.println(seller);
        sellerDao.deleteById(seller.getId());
        Department department = new Department(2, "Test");
        List<Seller> sellers = sellerDao.getByDepartment(department);
        sellers.forEach(System.out::println);
        List<Seller> allSellers = sellerDao.getAll();
        allSellers.forEach(System.out::println);
    }
}
