import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/hospital_management";
    private static final String userName = "root";
    private static final String password = "Rishabh@124";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,userName,password);
            patient patient = new patient(connection,scanner);
            doctor doctor = new doctor(connection);

            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice : ");

                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        // Add patient
                        patient.addPatientName();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //View Doctor
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        // Book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;

                    default:
                        System.out.println("Enter valid choice!!!");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment (patient patient, doctor doctor,Connection connection, Scanner scanner){
        System.out.print("Enter Patient id : ");
        int patient_id = scanner.nextInt();
        System.out.print("Enter Doctor id : ");
        int doctor_id = scanner.nextInt();
        System.out.print("Enter the date (YYYY-MM-DD) : ");
        String date = scanner.next();

        if(patient.checkPatientById(patient_id) && doctor.checkDoctorById(doctor_id)){
            if(doctorIsAvailable(doctor_id,date,connection)){
                String appointmentQuery = "INSERT INTO appointments (patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,date);

                    int rowAffected = preparedStatement.executeUpdate();
                    if(rowAffected>0){
                        System.out.println("Appointment Booked!!");
                    }else{
                        System.out.println("Failed to book Appointment");
                    }

                }catch(SQLException e){
                    e.printStackTrace();
                }

            }
        }else{
            System.out.println("Either doctor or patient is not available!!!");
        }
    }

    public static boolean doctorIsAvailable(int doctor_id , String date,Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count == 0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
