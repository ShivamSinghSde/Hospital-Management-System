package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;

    public Patient(Connection connection, Scanner sc){
        this.connection = connection;
        this.sc = sc;
    }

    public void addPatient(){
        System.out.println("Enter Patient Name: ");
        String name = sc.next();
        System.out.println("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.println("Gender: ");
        String gender = sc.next();

        try{
            String query = "insert into patients(name, age, gender) values (?, ?, ?)";
            PreparedStatement preparedStatement =connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient Registered Successfully...");
            }
            else{
                System.out.println("Patient Failed to Register...");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void viewPatient(){
        String query = "select * from Patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients : ");
            System.out.println("+------------+-------------------+----------+-----------+");
            System.out.println("| patient_id |    Patient_Name   |   Age    |   Gender  |");
            System.out.println("+------------+-------------------+----------+-----------+");
            while (resultSet.next()){
                int patient_id = resultSet.getInt("patient_id");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("| %-10s | %-17s | %-8s | %-9s |\n", patient_id, name, age, gender);
                System.out.println("+------------+-------------------+----------+-----------+");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkPatient(int patient_id){
        String query = "Select * from patients where patient_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,patient_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
