package peaksoft.dao.daoImpl;

import peaksoft.config.DBConfig;
import peaksoft.dao.EmployeeDao;
import peaksoft.models.Employee;
import peaksoft.models.Job;

import java.sql.*;
import java.util.*;

public class EmployeeDaoImpl implements EmployeeDao {
    public static final String GREEN = "\u001b[32m";
    public static final String RESET = "\u001b[0m";
    Connection connection = DBConfig.getConnection();

    public void createEmployee() {
        String createEmployee = "create table Employees(" +
                "id serial primary key," +
                "first_name varchar," +
                "last_name varchar," +
                "age smallint," +
                "email varchar unique," +
                "job_id int references Jobs(id))";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createEmployee);
            System.out.println(GREEN+"<<<Create Table Employee!>>>");
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Create Table Employees!>>>");
        }
    }

    public void addEmployee(Employee employee) {
        String addEmployee = "insert into Employees(first_name,last_name,age,email,job_id) values (?,?,?,?,?)";

        try(PreparedStatement ps = connection.prepareStatement(addEmployee)){
            ps.setString(1,employee.getFirstName());
            ps.setString(2,employee.getLastName());
            ps.setInt(3,employee.getAge());
            ps.setString(4,employee.getEmail());
            ps.setInt(5,employee.getJobId());
            ps.executeUpdate();
            System.out.println(GREEN+ "<<<" +employee.getFirstName()+" Add Employee!>>>");
        }catch (SQLException e){
            System.err.println("<<<Doesn't work methods addEmployee!>>>");
        }
    }

    public void dropTable() {
        String dropTable = "drop table Employees;";

        try(Statement st = connection.createStatement()){
            st.executeUpdate(dropTable);
            System.out.println(GREEN+"<<<Drop Table Employee!>>>");
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method drop table>>>");
        }
    }

    public void cleanTable() {
        String cleanTable = "truncate table Employees";

        try(Statement st = connection.createStatement()){
            st.executeUpdate(cleanTable);
            System.out.println(GREEN+"<<<Clean Table Employee!>>>");
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Clean Table!>>>");
        }

    }

    public void updateEmployee(Long employeeId, Employee employee) {
        String updateEmployee = "update employees set first_name = ?," +
                "last_name = ?," +
                "age = ?," +
                "email = ?," +
                "job_id = ?" +
                "where id = ?";

        try(PreparedStatement ps = connection.prepareStatement(updateEmployee)){
            ps.setString(1,employee.getFirstName());
            ps.setString(2,employee.getLastName());
            ps.setInt(3,employee.getAge());
            ps.setString(4,employee.getEmail());
            ps.setInt(5,employee.getJobId());
            ps.setLong(6,employeeId);
            ps.executeUpdate();
            System.out.println(GREEN+"<<<Update Employee!>>>");
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Update Employee!>>>");
        }
    }

    public List<Employee> getAllEmployees() {
        String getAll = "select * from Employees";
        ArrayList<Employee>list = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getByte("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                list.add(employee);
            }

        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Get.ALL.Employees!>>>");
        }
        System.out.println(GREEN+"<<<Get All Employees!>>>"+RESET);
        System.out.println(" ");
        return list;
    }

    public Employee findByEmail(String email) {
        String findByEmail = "select * from Employees where email = ?";
        Employee employee = new Employee();
        try(PreparedStatement ps = connection.prepareStatement(findByEmail)){
            ps.setString(1,email);
            ps.execute();

            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getByte("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                System.out.println(GREEN+"<<<Find By Email!>>>"+GREEN);
                return employee;
            }
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Find By Email!>>>");
        }
        return employee;
    }

    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        String getEmployeeById = "select * from Employees where id = ?";
        Map<Employee,Job> list = new LinkedHashMap<>();

        try(PreparedStatement ps = connection.prepareStatement(getEmployeeById)){
            ps.setLong(1,employeeId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getByte("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
//
                Job job = new Job();
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));

                list.put(employee,job);
                System.out.println(GREEN+"<<<Get Employee By Id!>>>");
            }
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Get Employee By Id>>>");

        }
        return list;
    }

    public List<Employee> getEmployeeByPosition(String position) {
        String getEmployeeByPosition = "select e.first_name,e.last_name,e.age,e.email from Employees e join Jobs j on j.id = e.id " +
                "where j.position = ?";

        List<Employee>list = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(getEmployeeByPosition)){
            ps.setString(1,"position");
            ps.execute();

            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getByte("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                list.add(employee);
                System.out.println(GREEN+"<<<Get Employee By Position!>>>"+GREEN);
            }
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Get Employee By Position!>>>");
        }
        return list;
    }
}
