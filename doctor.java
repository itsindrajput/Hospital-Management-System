import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class doctor {
    private Connection connection;

    public doctor(Connection connection){
        this.connection = connection;
    }


    public void viewDoctor(){
        String query = "select * from doctor";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors : ");
            System.out.println("+--------------+------------------------+------------------+");
            System.out.println("| Doctor id    | Name                   | Specialization   |");
            System.out.println("+--------------+------------------------+------------------+");

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-14s|%-24s|%-18s|\n",id,name,specialization);
                System.out.println("+--------------+------------------------+------------------+");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public boolean checkDoctorById(int id){
        String query = "select * from doctor WHERE id=?";
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


