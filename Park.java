import java.util.*;
import java.io.*;

public class Park {
    private String parkName;
    private String address;
    private String amenities;
    private double latitude;
    private double longitude;
    private int rating;

    public Park(String parkName, String address, String amenities, double latitude, double longitude, int rating) {
        this.parkName = parkName;
        this.address = address;
        this.amenities = amenities;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public String getParkName() {
        return parkName;
    }

    public String getAddress() {
        return address;
    }

    public String getAmenities() {
        return amenities;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRating() {
        return rating;
    }

    /*@Override
    public String toString() {
        return String.format("%s | %s | %s | %f | %f",
                parkName, address, amenities, latitude, longitude);
    }*/
}



