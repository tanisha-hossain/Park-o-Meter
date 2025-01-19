import java.util.*;
import java.io.*;

public class Park {
	private int parkID;
	private String parkName;
	private String address;
	private String amenities;
	private double latitude;
	private double longitude;
	private ArrayList<Integer> ratings;
	private ArrayList<Review> reviews;
	private double distance;

	public Park(int parkID, String parkName, String address, String amenities, double latitude, double longitude) {
		this.parkID = parkID;
		this.parkName = parkName;
		this.address = address;
		this.amenities = amenities;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ratings = new ArrayList<>();
		this.reviews = new ArrayList<>();
	}

	public int getParkID() {
		return this.parkID;
	}

	public String getParkName() {
		return this.parkName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getAmenities() {
		return this.amenities;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public double getDistance() {
		return this.distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void addRating(int rating) {
		if (rating >= 1 && rating <= 5) {
			this.ratings.add(rating);
		} else {
			System.out.println("Please enter a rating between 1 and 5!");
		}
	}

	public double getAverageRating() {
		if(this.ratings.size()==0) {
			return 0;
		}

		int sum = 0;
		for(int rating: this.ratings) {
			sum += rating;
		}
		return (double) sum / this.ratings.size();
	}

    public int[] getRatingsDistribution() {
        // Array to store counts for ratings 1-5 (with 1st index of array --> # of times rating 1 given, 2nd index of array --> # of times rating 2 given, ...)
        int[] ratingCounts = {0, 0, 0, 0, 0}; 
        for (int rating : this.ratings) {
            ratingCounts[rating - 1]++;
        }
        return ratingCounts;
    }

	public String getReviews() {
		if (this.reviews.isEmpty()) {
			return "";
		}
		String revlist = "";
		for (Review rev: this.reviews) {
			revlist += rev.toString();
		}
		return revlist;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	@Override
	public String toString() {
	    //Using StringBuilder to help ease construction of rating distribution string portion.
	    StringBuilder ratingDistributions = new StringBuilder();
	    int[] ratingsDist = getRatingsDistribution();
	    for (int i = 0; i < ratingsDist.length; i++) {
	        double percentageOfRatings = 0;
	        if (!this.ratings.isEmpty()) {
	            percentageOfRatings = (double) ratingsDist[i] / this.ratings.size() * 100;
	        }
	        ratingDistributions.append(String.format("%d stars: %d (%.2f%%), ", i+1, ratingsDist[i], percentageOfRatings));
	    }
		return String.format("\nPark ID: %d\n\nName: %s\nAddress: %s\nAmenities: %s\nLongitude: %f\nLatitude: %f\nDistance: %.2f units\nAverage Rating: %f\nRatings Distribution: %s\nReviews:\n%s\n", parkID, parkName, address, amenities, longitude, latitude, distance, getAverageRating(), ratingDistributions.toString(), getReviews());
	}
}
