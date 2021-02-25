package model.dao;

import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller department);
    void update(Seller department);
    void deleteById(Integer id);
    Seller getById(Integer id);
    List<Seller> getSellers();
}
