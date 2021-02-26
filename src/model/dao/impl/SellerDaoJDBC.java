package model.dao.impl;

import com.mysql.jdbc.Statement;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {
    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        try {
            String name = seller.getName();
            String email = seller.getEmail();
            long birthDate = seller.getBirthDate().getTime();
            double baseSalary = seller.getBaseSalary();
            int departmentId = seller.getDepartment().getId();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into seller (name, email, " +
                    "birthDate, baseSalary, departmentId) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setDate(3, new java.sql.Date(birthDate));
            preparedStatement.setDouble(4, baseSalary);
            preparedStatement.setInt(5, departmentId);
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    seller.setId(id);
                }
            } else {
                throw new DbException("Unexpected error. No update executed.");
            }
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        try {
            String name = seller.getName();
            String email = seller.getEmail();
            long birthDate = seller.getBirthDate().getTime();
            double baseSalary = seller.getBaseSalary();
            int departmentId = seller.getDepartment().getId();
            int sellerId = seller.getId();
            PreparedStatement preparedStatement = connection.prepareStatement("update seller set " +
                            "name = ?, email = ?, birthDate = ?, baseSalary = ?, departmentId = ? " +
                            "where id = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setDate(3, new java.sql.Date(birthDate));
            preparedStatement.setDouble(4, baseSalary);
            preparedStatement.setInt(5, departmentId);
            preparedStatement.setInt(6, sellerId);
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from seller where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public Seller getById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select seller.*, dep.name as departmentName " +
                    "from seller " +
                    "left join department dep on seller.departmentId = dep.id " +
                    "where seller.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return instantiateSeller(result, instantiateDepartment(result));
            }
            return null;
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public List<Seller> getByDepartment(Department department) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select seller.*, dep.name as departmentName " +
                    "from seller " +
                    "left join department dep on seller.departmentId = dep.id " +
                    "where dep.id = ? " +
                    "order by seller.name");
            preparedStatement.setInt(1, department.getId());
            ResultSet result = preparedStatement.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();
            while (result.next()) {
                department = departmentMap.get(result.getInt("departmentId"));
                if (department == null) {
                    department = instantiateDepartment(result);
                    departmentMap.put(department.getId(), department);
                }
                sellers.add(instantiateSeller(result, department));
            }
            return sellers;
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public List<Seller> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select seller.*, dep.name as departmentName " +
                    "from seller " +
                    "left join department dep on seller.departmentId = dep.id " +
                    "order by seller.name");
            ResultSet result = preparedStatement.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();
            Department department;
            while (result.next()) {
                department = departmentMap.get(result.getInt("departmentId"));
                if (department == null) {
                    department = instantiateDepartment(result);
                    departmentMap.put(department.getId(), department);
                }
                sellers.add(instantiateSeller(result, department));
            }
            return sellers;
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
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
