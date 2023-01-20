package peaksoft.dao.daoImpl;

import peaksoft.config.DBConfig;
import peaksoft.dao.EmployeeDao;
import peaksoft.models.Employee;
import peaksoft.models.Job;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {

    Connection connection = DBConfig.getConnection();

    public void createEmployee() {
        String createEmployee = "create table Employees(" +
                "id serial primary key," +
                "first_name varchar," +
                "last_name varchar," +
                "age smallint," +
                "email varchar unique," +
                "job_id int references Job(id)";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createEmployee);
            System.out.println("<<<Create Table Employee!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Create Table Employees!>>>");
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
            System.out.println("<<<add Employee!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work methods addEmployee!>>>");
        }
    }

    public void dropTable() {
        String dropTable = "drop table Employees;";

        try(Statement st = connection.createStatement()){
            st.executeUpdate(dropTable);
            System.out.println("<<<Drop Table Employee!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method drop table>>>");
        }
    }

    public void cleanTable() {
        String cleanTable = "truncate table Employees";

        try(Statement st = connection.createStatement()){
            st.executeUpdate(cleanTable);
            System.out.println("Clean Table Employee");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Clean Table!>>>");
        }

    }

    public void updateEmployee(Long id, Employee employee) {
        String updateEmployee = "update Employees set first_name = ?," +
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
            ps.executeUpdate();
            System.out.println("<<<Update Employee!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Update Employee!>>>");
        }
    }

    public List<Employee> getAllEmployees() {
        String getAll = "select * from Employees";
        ArrayList<Employee>list = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                list.add(employee);
                System.out.println("<<<Get All Employees!>>>");
            }

        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Get.ALL.Employees!>>>");
        }
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
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                System.out.println("<<<Find By Id!>>>");
                return employee;
            }
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Find.By.ID>>>");
        }
        return employee;
    }

    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        String getEmployeeById = "select * from Employees where id = ?";
        Map<List<Employee>, List<Job>> list = new HashMap<>();
        List<Employee>list1 = new ArrayList<>();
        List<Job>list2 = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(getEmployeeById)){
            ps.setLong(1,employeeId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                list1.add(employee);
//
                Job job = new Job();
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
                list2.add(job);

                list.put(list1,list2);
            }
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Find.By.ID>>>");

        }
        return null;
    }

    public List<Employee> getEmployeeByPosition(String position) {
        String getEmployeeByPosition = "select * from Employees join Job j on j.id = Employees.id" +
                "where j.position = ?";

        ArrayList<Employee>list = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(getEmployeeByPosition)){
            ps.setString(1,"position");
            ps.execute();

            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                list.add(employee);
                System.out.println("<<<Get Employee By Id!>>>");
            }
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Get Employee By Id!>>>");
        }
        return list;
    }
}
