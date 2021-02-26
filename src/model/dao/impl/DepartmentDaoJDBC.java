package model.dao.impl;

import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    public DepartmentDaoJDBC(Connection connection) {
    }

    @Override
    public void insert(Department department) {

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department getById(Integer id) {
        return null;
    }

    @Override
    public List<Department> getDepartments() {
        return null;
    }
}
