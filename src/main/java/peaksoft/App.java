package peaksoft;

import peaksoft.models.Employee;
import peaksoft.models.Job;
import peaksoft.service.EmployeeService;
import peaksoft.service.JobService;
import peaksoft.service.serviceImpl.EmployeeServiceImpl;
import peaksoft.service.serviceImpl.JobServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 *

 */
public class App {
    public static final String GREEN = "\u001b[32m";
    public static final String RESET = "\u001b[0m";
    public static final String BLUE = "\u001b[34m";
    public static final String WHITE = "\u001b[37m";
    public static final String RED = "\u001b[31m";
    public static final String CYAN = "\u001b[36m";
    public static void main( String[] args ) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        JobService jobService = new JobServiceImpl();

        while(true){
            System.out.println(BLUE+"<<<---Job Methods--->>>\n" +
                    "1-button-> Create Table Job\n" +
                    "2-button-> Add Job\n" +
                    "3-button-> Get Job By Id\n" +
                    "4-button-> Sort By experience\n" +
                    "5-button-> Get Job By Employee Id\n" +
                    "6-button-> Delete Description Column\n" +
                    "<<<---Employee Methods--->>>\n" +
                    "7-button-> Create Table Employee\n" +
                    "8-button-> Add Employee\n" +
                    "9-button-> Drop Table Employee\n" +
                    "10-button-> Clean Table Employee\n" +
                    "11-button-> Update Employee\n" +
                    "12-button-> Get All Employees\n" +
                    "13-button-> Find By Email\n" +
                    "14-button-> Get Employee By Id\n" +
                    "15-button-> Get Employee By Position\n" +
                    "16-button-> Stop!"+BLUE);
            System.out.print(RED+"Write Command:"+RED);
            int num = new Scanner(System.in).nextInt();
            switch (num){
                case 1:
                    jobService.createJobTable();
                    break;
                case 2:
                    System.out.print(CYAN+"Write Position:"+CYAN);
                    String position = new Scanner(System.in).nextLine();
                    System.out.print(CYAN+"Write Profession:"+CYAN);
                    String profession = new Scanner(System.in).nextLine();
                    System.out.print(CYAN+"Write Description:"+CYAN);
                    String description = new Scanner(System.in).nextLine();
                    System.out.print(CYAN+"Write Experience:"+CYAN);
                    int experience = new Scanner(System.in).nextInt();
                    Job job = new Job(position,profession,description,experience);
                    jobService.addJob(job);
                    break;
                case 3:
                    System.out.print(CYAN+"Write id:"+CYAN);
                    Long id = new Scanner(System.in).nextLong();
                    System.out.println(RESET+jobService.getJobById(id)+RESET);
                    break;
                case 4:
                    System.out.print(CYAN+"Write ASC/DESC:"+CYAN);
                    String ascOrDesc = new Scanner(System.in).nextLine();
                    jobService.sortByExperience(ascOrDesc).forEach(System.out::println);
                    System.out.println(" ");
                    break;
                case 5:
                    System.out.print(CYAN+"Write EmployeeId:"+CYAN);
                    Long employeeId = new Scanner(System.in).nextLong();
                    jobService.getJobByEmployeeId(employeeId);
                    break;
                case 6:
                    System.err.println("Are you sure you want to delete column Description? Yes/No");
                    String yesOrNo = new Scanner(System.in).nextLine();
                    switch (yesOrNo){
                        case "Yes":
                            jobService.deleteDescriptionColumn();
                            break;
                        case "No":
                            break;
                    }
                    break;
                case 7:
                    employeeService.createEmployee();
                    break;
                case 8:
                    Scanner scanner = new Scanner(System.in);
                    Scanner scannerInt = new Scanner(System.in);
                    System.out.print(CYAN+"Write first_name:"+CYAN);
                    String first_name = scanner.nextLine();
                    System.out.print(CYAN+"Write last_name:"+CYAN);
                    String last_name = scanner.nextLine();
                    System.out.print(CYAN+"Write age:"+CYAN);
                    Byte age = scannerInt.nextByte();
                    System.out.print(CYAN+"Write email:"+CYAN);
                    String email = scanner.nextLine();
                    System.out.print(CYAN+"Write job_id:"+CYAN);
                    int job_id = scannerInt.nextInt();
                    Employee employee = new Employee(first_name,last_name,age,email,job_id);
                    employeeService.addEmployee(employee);
                    break;
                case 9:
                    System.err.println("Are you sure you want to drop Employee table? Yes/No");
                    String yesOrNos = new Scanner(System.in).nextLine();
                    switch (yesOrNos){
                        case "Yes":
                            employeeService.dropTable();
                            break;
                        case "No":
                            break;
                    }
                    break;
                case 10:System.err.println("Are you sure you want to Clean Employee table? Yes/No");
                    String yesOrNo1 = new Scanner(System.in).nextLine();
                    switch (yesOrNo1){
                        case "Yes":
                            employeeService.cleanTable();
                            break;
                        case "No":
                            break;
                    }
                    break;
                case 11:
                    Scanner scanner1 = new Scanner(System.in);
                    Scanner scannerInt2 = new Scanner(System.in);
                    System.out.print(CYAN+"Write EmployeeId:"+CYAN);
                    Long idem = new Scanner(System.in).nextLong();
                    System.out.print(CYAN+"Write first_name:"+CYAN);
                    String first_name2 = scanner1.nextLine();
                    System.out.print(CYAN+"Write last_name:"+CYAN);
                    String last_name2 = scanner1.nextLine();
                    System.out.print(CYAN+"Write age:"+CYAN);
                    Byte age2 = scannerInt2.nextByte();
                    System.out.print(CYAN+"Write email:"+CYAN);
                    String email2 = scanner1.nextLine();
                    System.out.print(CYAN+"Write job_id:"+CYAN);
                    int job_id2 = scannerInt2.nextInt();
                    Employee employee1 = new Employee(first_name2,last_name2,age2,email2,job_id2);
                    employeeService.updateEmployee(idem,employee1);
                    break;
                case 12:
                    employeeService.getAllEmployees().forEach(System.out::println);
                    System.out.println(" ");
                    break;
                case 13:
                    System.out.print(CYAN+"Write email:"+CYAN);
                    String findByEmail = new Scanner(System.in).nextLine();
                    System.out.println(RED+employeeService.findByEmail(findByEmail)+RED);
                    break;
                case 14:
                    System.out.print(CYAN+"Write EmployeeId:"+CYAN);
                    Long EmplId = new Scanner(System.in).nextLong();
                    System.out.println(RESET+employeeService.getEmployeeById(EmplId)+RESET);
                    System.out.println(" ");
                    break;
                case 15:
                    System.out.print(CYAN+"Write: Position:"+CYAN);
                    String position1 = new Scanner(System.in).nextLine();
                    employeeService.getEmployeeByPosition(position1).forEach(System.out::println);
                    break;
                default:
                    throw new RuntimeException("Stop Project!");

//                    System.out.println("Stop Project!");
//                    break;
            }
        }
    }
}
