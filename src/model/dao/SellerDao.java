package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller getById(Integer id);
    List<Seller> getByDepartment(Department department);
    List<Seller> getAll();
}
