import java.util.*;
import java.io.*;
import java.lang.*;

//REMINDER: the displayAllParks method works to show them all rn but we might wanna try making the displayParkInfo one so when users leave reviews it shows up under the specific parks info box
//also we should try coding a way for cleaning up the code of the original spreadsheet so if we decide to add more parks we dont gotta manually do it
public class ParkManager {
    private List<Park> parks;

    public ParkManager() {
        this.parks = new ArrayList<>();
    }

    public ParkManager(List<Park> parks) {
        this.parks = parks;
    }

    public List<Park> getParks() {
        return parks;
    }

    private String[] parseLine(String line) {
        List<String> attributes = new ArrayList<>();
        boolean withinQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                withinQuotes = !withinQuotes; // toggle the bool flag when entering/leaving a quotation
            } else if (c == ',' && !withinQuotes) { // Going to another column in the csv
                attributes.add(sb.toString().trim());
                sb = new StringBuilder(); // refresh and clear string builder for next column
            } else { // This is a regular character
                sb.append(c);
            }
        }
        attributes.add(sb.toString().trim()); // Add the last element
            return attributes.toArray(new String[0]);
        }

    public void displayAllParks(String filePath) {
        try {
            File myFile = new File(filePath);
            Scanner scanner = new Scanner(myFile);

        if (!scanner.hasNext()) {
            System.out.println("Error: File is empty");
        return;
    }
        if (!myFile.exists()) {
            System.out.println("The file does not exist.");
        return;
    }
        scanner.nextLine(); //skips header
        System.out.println("Here are the parks listed closest to your location:\n"); 
            //ADD CODE TO PAUSE THE PROGRAM FOR A BIT SO THE USER CAN ACTUALLY READ IT BEFORE GETTING BOMBARDED WITH INFO (maybe like a "Loading..." message and using Thread.sleep)

        while (scanner.hasNextLine()) {
            
            String line = scanner.nextLine();
            String[] parts = parseLine(line);

            String parkName = parts[1];
            String address = parts[3];
            String amenities = parts[2];
            double longitude = Double.parseDouble(parts[4]);
            double latitude = Double.parseDouble(parts[5]);

            this.parks.add(new Park(parkName, address, amenities, latitude, longitude));
    }

    for (int i = 0; i < this.parks.size(); i++) { // displays park attributes
        Park park = this.parks.get(i);
        System.out.printf("Park ID: %d\n", i + 1); // +1 because we start at 0
        System.out.print(park);

        for (int j = 0; j < 100; j++) { // line separator
            System.out.print("-");
        }
        System.out.println("");
    }
} catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
        }
    }

    
    public void ratePark(Scanner input) {
        boolean validPark = false; // flag for park ID validation

        while (!validPark) {
            try {
                System.out.println("Enter the park ID of the park you want to rate:");
                int parkId = input.nextInt();

                if (parkId > 0 && parkId <= parks.size()) {
                    boolean validRating = false; // flag for rating validation

                    while (!validRating) {
                        System.out.println("Enter your rating (1-5):");
                        int rating = input.nextInt();

                        if (rating >= 1 && rating <= 5) {
                            Park park = parks.get(parkId - 1); // -1 to get the right index
                            park.addRating(rating);
                            System.out.printf("Thank you for your rating! The updated rating for %s is %.2f\n",
                                    park.getParkName(), park.getAverageRating());
                            validRating = true;
                            validPark = true;
                        } else {
                            System.out.println("Please enter a rating between 1 and 5!");
                        }
                    }
                } else {
                    System.out.println("Invalid park ID. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.nextLine(); // clear the invalid input
            }
        }
    }


    public void leaveReview(Scanner input, User user) {
        boolean valid = false; // flag for error handling

        while (!valid) {
            try {
                System.out.println("Enter the park ID of the park you want to leave a review for:");
                int parkId = input.nextInt();

                if (parkId > 0 && parkId <= parks.size()) {
                    Park park = parks.get(parkId - 1); // -1 to get the right index

                    System.out.println("Enter your review:");
                    input.nextLine(); // clear the newline character
                    String text = input.nextLine();

                    Review review = new Review(user.getUsername(), text);
                    park.addReview(review);
                    System.out.printf("Thank you for your review! The updated reviews for %s are:\n%s\n",
                            park.getParkName(), park.getReviews());
                    valid = true; 
                } else {
                    System.out.println("Invalid park ID. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.nextLine(); // clear the invalid input
            }
        }
    }



//ORDER BY CLOSEST DISTANCE METHOD
    public void orderByDistance(List<Park> parkList, double userLatitude, double userLongitude) {
        quickSortByDistance(parkList, 0, parkList.size() - 1, userLatitude, userLongitude);

        System.out.println("Parks ordered by closest distance:");
        for (Park park : parkList) {
            double distance = calculateDistance(userLatitude, userLongitude, park.getLatitude(), park.getLongitude());
            System.out.printf("%s - Distance: %.2f units\n", park.getParkName(), distance);
        }
    }

    private void quickSortByDistance(List<Park> parks, int low, int high, double userLatitude, double userLongitude) {
        if (low < high) {
            int pivotIndex = partition(parks, low, high, userLatitude, userLongitude);
            quickSortByDistance(parks, low, pivotIndex - 1, userLatitude, userLongitude);
            quickSortByDistance(parks, pivotIndex + 1, high, userLatitude, userLongitude);
        }
    }

    private int partition(List<Park> parks, int low, int high, double userLatitude, double userLongitude) {
        double pivotDistance = calculateDistance(userLatitude, userLongitude, parks.get(high).getLatitude(), parks.get(high).getLongitude());
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            double currentDistance = calculateDistance(userLatitude, userLongitude, parks.get(j).getLatitude(), parks.get(j).getLongitude());
            if (currentDistance < pivotDistance) {
                i++;
                Collections.swap(parks, i, j);
            }
        }
        Collections.swap(parks, i + 1, high);
        return i + 1;
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
    
