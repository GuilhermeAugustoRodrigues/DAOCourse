package model.dao.impl;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller department) {

    }

    @Override
    public void update(Seller department) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller getById(Integer id) {
        try {
            PreparedStatement request = connection.prepareStatement("select seller.*, dep.name as departmentName " +
                    "from seller " +
                    "left join department dep on seller.departmentId = dep.id " +
                    "where seller.id = ?");
            request.setInt(1, id);
            ResultSet result = request.executeQuery();
            if (result.next()) {
                return instantiateSeller(result, instantiateDepartment(result));
            }
            return null;
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public List<Seller> getSellers() {
        return null;
    }

    private Seller instantiateSeller(ResultSet result, Department department) throws SQLException {
        int id = result.getInt("id");
        double baseSalary = result.getDouble("baseSalary");
        String name = result.getString("name");
        String email = result.getString("email");
        Date birthDate = result.getDate("birthDate");
        return new Seller(id, name, email, birthDate, baseSalary, department);
    }

    private Department instantiateDepartment(ResultSet result) throws SQLException {
        int id = result.getInt("departmentId");
        String name = result.getString("departmentName");
        return new Department(id, name);
    }
}
