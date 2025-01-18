import java.util.*;
import java.io.*;

public class Park {
    private String parkName;
    private String address;
    private String amenities;
    private double latitude;
    private double longitude;
    private ArrayList<Integer> ratings;
    private ArrayList<Review> reviews;
    private double distance;

    public Park(String parkName, String address, String amenities, double latitude, double longitude) { 
        this.parkName = parkName;
        this.address = address;
        this.amenities = amenities;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
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

    public double getDistance(){
        return distance;
    }
    
    public void setDistance(double distance){
        this.distance = distance;
    }

    public void addRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            ratings.add(rating);
        }else{
            System.out.println("Please enter a rating between 1 and 5!");
        }
    }

    public double getAverageRating(){
        if(ratings.size()==0){
            return 0;
        } 

        int sum = 0;
        for(int rating: ratings){
            sum += rating;  
        }
        return (double) sum / ratings.size();
    }

    public String getReviews() {
        if (reviews.isEmpty()) {
            return "";
        }
        String revlist = "";
        for (Review rev: reviews) {
            revlist += rev.toString();
        }
        return revlist;
    }

    public void addReview(Review review){
        reviews.add(review);
    }



    @Override
    public String toString() {
        return String.format("\nName: %s\nAddress: %s\nAmenities: %s\nLongitude: %f\nLatitude: %f\nDistance: %.2f units\nRating: %f\nReviews:\n%s\n", parkName, address, amenities, longitude, latitude, distance, getAverageRating(), getReviews());
    }
}
