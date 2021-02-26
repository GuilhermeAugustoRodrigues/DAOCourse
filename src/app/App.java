package app;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class App {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
//        Seller seller = sellerDao.getById(1);
        Department department = new Department(2, "Teste");
        List<Seller> sellers = sellerDao.getByDepartment(department);
        sellers.forEach(System.out::println);
//        System.out.println(seller);
    }
}
