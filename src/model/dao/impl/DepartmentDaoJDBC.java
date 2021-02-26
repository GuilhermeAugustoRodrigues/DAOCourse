package model.dao.impl;

import com.mysql.jdbc.Statement;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DepartmentDaoJDBC implements DepartmentDao {
    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        try {
            String name = department.getName();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into department (name) values (?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    department.setId(id);
                }
            } else {
                throw new DbException("Unexpected error. No update executed.");
            }
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public void update(Department department) {
        try {
            String name = department.getName();
            int departmentId = department.getId();
            PreparedStatement preparedStatement = connection.prepareStatement("update department set " +
                    "name = ? where id = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, departmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from department where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public Department getById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from department " +
                    "where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return instantiateDepartment(result);
            }
            return null;
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    @Override
    public List<Department> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from department " +
                    "order by name");
            ResultSet result = preparedStatement.executeQuery();
            List<Department> departments = new ArrayList<>();
            while (result.next()) {
                departments.add(instantiateDepartment(result));
            }
            return departments;
        } catch (SQLException error) {
            throw new DbException(error.getMessage());
        }
    }

    private Department instantiateDepartment(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");
        return new Department(id, name);
    }
}
