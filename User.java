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

    public double[] getCoordinates() {
        return new double[]{latitude, longitude};
    }
}
