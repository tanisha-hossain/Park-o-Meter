import java.util.*;
import java.io.*;

public class User {
    private String username;
    private double latitude;
    private double longitude;

    public User(String username, double latitude, double longitude) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    public double[] getCoordinates() {
        return new double[]{latitude, longitude};
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNewLocation(Scanner input) {
        System.out.print("Enter your latitude: ");
        double newLatitude = input.nextDouble();
        System.out.print("Enter your longitude: ");
        double newLongitude = input.nextDouble();
        input.nextLine(); 
        
        this.setLatitude(newLatitude);
        this.setLongitude(newLongitude);
    }
}
