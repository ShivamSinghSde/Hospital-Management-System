package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String passward = "";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            Connection connection = DriverManager.getConnection(url, username, passward);
            Patient patient = new Patient(connection, sc);
            Doctor doctor = new Doctor(connection);
            Appointment appointment = new Appointment(connection);
            while (true){
                System.out.println("--- Hospital Management System ---");
                System.out.println(" 1) Register Patient.\n 2) View Patient. \n 3) View Doctor. " +
                        "\n 4) Book Appointment.  \n 5) View Appointment. \n 6) Exit.");
                int n = sc.nextInt();
                switch (n){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        appointment.bookAppointment(patient,doctor,connection,sc);
                        System.out.println();
                        break;
                    case 5:
                        appointment.viewAppointment();
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("--- THANK YOU ---");
                        return;
                    default:
                        System.out.println("Enter Valid No. :");
                        System.out.println();
                        break;

                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
