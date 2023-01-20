package peaksoft.dao.daoImpl;

import peaksoft.config.DBConfig;
import peaksoft.dao.JobDao;
import peaksoft.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

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
            System.out.println("<<<Create Table Job!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Create Table Job!>>>");
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
            System.out.println("<<<add Job!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method add Job!>>>");
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
                job.setId(resultSet.getLong("jobId"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
                System.out.println("<<<Get By Id Job!>>>");
                return job;
            }
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method add Job!>>>");
        }
        return job;
    }

    public List<Job> sortByExperience(String ascOrDesc) {
        String asc = "select * from Jobs order by experience";
        String desc = "select * from Jobs order by experience desc";
        ArrayList<Job>list = new ArrayList<>();
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
                        System.out.println("<<<Sort By Experience {ASC}!>>>");
                    }

                }catch (SQLException e){
                    System.out.println("<<<Doesn't work method sort-By-Experience!>>>");
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
                        System.out.println("<<<Sort By Experience {DESC}!>>>");
                    }

                }catch (SQLException e){
                    System.out.println("<<<Doesn't work method sort-By-Experience!>>>");
                }
                break;
        }


        return list;
    }

    public Job getJobByEmployeeId(Long employeeId) {
        String getJobByEmployeeId = "select * from Jobs join Employees e on e.id = Jobs.id " +
                "where e.id = ?";
        Job job = new Job();
        try(PreparedStatement ps = connection.prepareStatement(getJobByEmployeeId)){
            ps.setLong(1,employeeId);
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            while(resultSet.next()){
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
                System.out.println("<<<Get-Job-By-EmployeeId>>>");
                return job;
            }
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Get-JobBy-Employee-Id!>>>");
        }
        return job;
    }

    public void deleteDescriptionColumn() {
        String deleteColumn = "alter table Jobs" +
                "drop column description";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(deleteColumn);
            System.out.println("<<<Delete Column Description!>>>");
        }catch (SQLException e){
            System.out.println("<<<Doesn't work method Delete Description Column!>>>");
        }
    }
}
