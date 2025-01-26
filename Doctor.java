package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner sc;

    public Doctor(Connection connection){
        this.connection = connection;
    }

    public void viewDoctor(){
        String query = "select * from Doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors : ");
            System.out.println("+-----------+----------------------+------------------+");
            System.out.println("| doctor_id |    Doctor_Name       |  Specialization  |");
            System.out.println("+-----------+----------------------+------------------+");
            while (resultSet.next()){
                int doctor_id = resultSet.getInt("doctor_id");
                String name = resultSet.getString("Name");
                String specialization = resultSet.getString("Specialization");
                System.out.printf("| %-9s | %-20s | %-16s |\n", doctor_id, name, specialization);
                System.out.println("+-----------+----------------------+------------------+");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkDoctor(int doctor_id){

        String query = "Select * from doctors where doctor_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
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
