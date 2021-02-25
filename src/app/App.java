package app;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class App {
    public static void main(String[] args) {
        Department department = new Department(1, "Electronics");
        Seller seller = new Seller(1, "Bob", "bob@gmail.com", new Date(), 3000D, 1);
        System.out.println(department);
    }
}
