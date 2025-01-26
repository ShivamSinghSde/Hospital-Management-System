package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment {
    private Connection connection;
    private Scanner sc;

    public Appointment(Connection connection){
        this.connection = connection;
    }

    public void viewAppointment(){
        String query = "select * from Appointments";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Appointments : ");
            System.out.println("+---------+-------------+-------------+--------------------+");
            System.out.println("|   Id    | Patient_id  |  Doctor_id  |  Appointment_Date  |");
            System.out.println("+---------+-------------+-------------+--------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int patient_id = resultSet.getInt("patient_id");
                int doctor_id = resultSet.getInt("doctor_id");
                String date = resultSet.getString("Appointment_date");
                System.out.printf("| %-7s | %-11s | %-11s | %-18s |\n",id,patient_id, doctor_id, date);
                System.out.println("+---------+-------------+-------------+--------------------+");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc){
        System.out.println("Enter Patient_id:  ");
        int patient_id = sc.nextInt();
        System.out.println("Enter Doctor_id: ");
        int doctor_id = sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String appointment_date = sc.next();
        if(patient.checkPatient(patient_id) && doctor.checkDoctor(doctor_id)){
            if(checkDoctorAvailable(doctor_id,appointment_date,connection)){
                String query = "insert into appointments(patient_id, doctor_id, appointment_Date) values (?, ?, ?)";
                try{
                    PreparedStatement preparedStatement =connection.prepareStatement(query);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);
                    int rowaffected = preparedStatement.executeUpdate();
                    if(rowaffected>0){
                        System.out.println("Patient Id: "+patient_id+ " is Booked Appointment On "+ appointment_date +" with Doctor Id: "+doctor_id);
                    }
                    else {
                        System.out.println("Failed to Book Appointment.");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Doctor with Id: "+ doctor_id + " is not available on "+ appointment_date);
            }
        }
        else {
            System.out.println("Either Patient or Doctor Doesn't Exit.");
        }
    }
    public static boolean checkDoctorAvailable(int doctor_id , String date, Connection connection){
        String query = "select count(*) from appointments where doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
}
