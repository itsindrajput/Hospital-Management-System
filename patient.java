import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
    private Connection connection;
    private Scanner scanner;

    public patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatientName(){
        System.out.print("Add patient name : ");
        String name = scanner.next();
        System.out.print("Add patient age : ");
        int age = scanner.nextInt();
        System.out.print("Add Gender : ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patient (name,age,gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient data added successfully!!");
            }
            else{
                System.out.println("Failed to add patient!!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatient(){
        String query = "select * from patient";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients : ");
            System.out.println("+--------------+------------------------+-----------+-----------+");
            System.out.println("| Patient id   | Name                   |Age        | Gender    |");
            System.out.println("+--------------+------------------------+-----------+-----------+");

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-14s|%-24s|%-11s|%-11s\n",id,name,age,gender);
                System.out.println("+--------------+------------------------+-----------+-----------+");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public boolean checkPatientById(int id){
        String query = "select * from patient WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


}
