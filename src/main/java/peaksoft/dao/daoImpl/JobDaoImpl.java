package peaksoft.dao.daoImpl;

import peaksoft.config.DBConfig;
import peaksoft.dao.JobDao;
import peaksoft.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    public static final String GREEN = "\u001b[32m";
    public static final String RESET = "\u001b[0m";
    Connection connection = DBConfig.getConnection();

    public void createJobTable() {
        String createJobTable = "create table Jobs(" +
                "id serial primary key," +
                "position varchar," +
                "profession varchar," +
                "description varchar," +
                "experience int)";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createJobTable);
            System.out.println(GREEN+"<<<Create Table Job!>>>"+GREEN);
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Create Table Job!>>>");
        }
    }

    public void addJob(Job job) {
        String addJob = "insert into Jobs(position,profession,description,experience) values (?,?,?,?)";

        try(PreparedStatement ps = connection.prepareStatement(addJob)){
            ps.setString(1,job.getPosition());
            ps.setString(2,job.getProfession());
            ps.setString(3,job.getDescription());
            ps.setInt(4,job.getExperience());
            ps.executeUpdate();
            System.out.println(GREEN+"<<<add Job!>>>"+GREEN);
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method add Job!>>>");
        }
    }

    public Job getJobById(Long jobId) {
        String getJobById = "select * from Jobs where id = ?";
        Job job = new Job();
        try(PreparedStatement ps = connection.prepareStatement(getJobById)){
            ps.setLong(1,jobId);
            ps.execute();

            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                job.setId(resultSet.getLong(1));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
                System.out.println(GREEN+"<<<Get By Id Job!>>>"+GREEN);
                return job;
            }
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Get-Job-By-ID!>>>");
        }

        return job;
    }

    public List<Job> sortByExperience(String ascOrDesc) {
        String asc = "select * from Jobs order by experience";
        String desc = "select * from Jobs order by experience desc";
        List<Job>list = new ArrayList<>();
        switch (ascOrDesc){
            case "asc":
                try(Statement statement = connection.createStatement()){
                    ResultSet resultSet = statement.executeQuery(asc);
                    while(resultSet.next()) {
                        Job job = new Job();
                        job.setPosition(resultSet.getString("position"));
                        job.setProfession(resultSet.getString("profession"));
                        job.setDescription(resultSet.getString("description"));
                        job.setExperience(resultSet.getInt("experience"));
                        list.add(job);

                    }

                }catch (SQLException e){
                    System.err.println("<<<Doesn't work method sort-By-Experience!>>>");
                }
                break;
            case "desc":
                try(Statement statement = connection.createStatement()){
                    ResultSet resultSet = statement.executeQuery(desc);
                    while(resultSet.next()) {
                        Job job = new Job();
                        job.setPosition(resultSet.getString("position"));
                        job.setProfession(resultSet.getString("profession"));
                        job.setDescription(resultSet.getString("description"));
                        job.setExperience(resultSet.getInt("experience"));
                        list.add(job);
                    }

                }catch (SQLException e){
                    System.err.println("<<<Doesn't work method sort-By-Experience!>>>");
                }
                break;
        }

        if (ascOrDesc == "asc") {
            System.out.println(GREEN+"<<<Sort By Experience {ASC}!>>>"+RESET);
        }else {
            System.out.println(GREEN+"<<<Sort By Experience {DESC}!>>>"+RESET);
        }
        System.out.println(" ");
        return list;
    }

    public Job getJobByEmployeeId(Long employeeId) {
        String getJobByEmployeeId = "select * from Jobs j join Employees e on e.id = j.id" +
                "where e.id = ?";
        Job job = new Job();
        try(PreparedStatement ps = connection.prepareStatement(getJobByEmployeeId)){
            ps.setLong(1,employeeId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                job.setId(resultSet.getLong(1));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
                System.out.println(GREEN+"<<<Get-Job-By-EmployeeId>>>"+GREEN);
                return job;
            }
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Get-Job-By-Employee-Id!>>>");
        }
        return job;
    }

    public void deleteDescriptionColumn() {
        String deleteColumn = "ALTER TABLE Jobs drop column description";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(deleteColumn);
            System.out.println(GREEN+"<<<Delete Column Description!>>>"+GREEN);
        }catch (SQLException e){
            System.err.println("<<<Doesn't work method Delete Description Column!>>>");
        }
    }
}
