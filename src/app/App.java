package app;

import model.entities.Department;

public class App {
    public static void main(String[] args) {
        Department department = new Department(1, "Electronics");
        System.out.println(department);
    }
}
