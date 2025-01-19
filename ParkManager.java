import java.util.*;
import java.io.*;
import java.lang.*;


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

    //below method used for file reading of csv so that park attributes like amenities are treated as one rather than split
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


    // reads csv file
    public void readParksFile(String filePath){
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
            
            int rowCount = 1; 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = parseLine(line);

                String parkName = parts[1];
                String address = parts[3];
                String amenities = parts[2];
                double longitude = Double.parseDouble(parts[4]);
                double latitude = Double.parseDouble(parts[5]);

                this.parks.add(new Park(rowCount, parkName, address, amenities, latitude, longitude)); //row count is the parks assigned id
                
                rowCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        
    }
    
    //displays parks by their id number
    public void displayAllParks() {
        orderByID(this.parks);
        for (int i = 0; i < this.parks.size(); i++) { // displays park attributes
            Park park = this.parks.get(i);
            //System.out.printf("Park ID: %d\n", i + 1); // +1 because we start at 0
            System.out.print(park);
    
            for (int j = 0; j < 100; j++) { // line separator
                System.out.print(color.whiteText + "-");
            }
            System.out.println(color.magentaText + "");
        }
    }
    
    public void displayAllParksByDistance(User user) {
        orderByDistance(this.parks, user.getLatitude(), user.getLongitude());
        for (int i = 0; i < this.parks.size(); i++) { // displays park attributes
            Park park = this.parks.get(i);
            //System.out.printf("\nPark ID: %d\n", i + 1); // +1 because we start at 0
            System.out.print(park);
    
            for (int j = 0; j < 100; j++) { // line separator
                System.out.print(color.whiteText + "-");
            }
            System.out.println(color.magentaText + "");
        }
    }
    
    //shows parks in order of highes tto lowest rating
    public void displayAllParksByRating(User user) {
        orderByRating(this.parks);
        for (int i = 0; i < this.parks.size(); i++) { // displays park attributes
            Park park = this.parks.get(i);
            //System.out.printf("\nPark ID: %d\n", i + 1); // +1 because we start at 0
            System.out.print(park);
    
            for (int j = 0; j < 100; j++) { // line separator
                System.out.print(color.whiteText + "-");
            }
            System.out.println(color.magentaText + "");
        }
    }

    
    public void ratePark(Scanner input) {
        boolean validPark = false; // flag for park ID validation

        while (!validPark) {
            try {
                System.out.println(color.greenText + "Enter the park ID of the park you want to rate:");
                int parkId = input.nextInt();

                if (parkId > 0 && parkId <= parks.size()) {
                    boolean validRating = false; // flag for rating validation

                    while (!validRating) {
                        System.out.println(color.greenText + "Enter your rating (1-5):");
                        int rating = input.nextInt();

                        if (rating >= 1 && rating <= 5) {
                            for (Park park : this.parks) {
                                if (park.getParkID() == parkId) {
                                    park.addRating(rating);
                                    System.out.printf(color.greenText + "Thank you for your rating! The updated rating for %s is %.2f\n",
                                            park.getParkName(), park.getAverageRating());
                                    validPark = true;
                                }
                            }
                            validRating = true;
                        } else {
                            System.out.println(color.redText + "Please enter a rating between 1 and 5!");
                        }
                    }
                } else {
                    System.out.println(color.redText + "Invalid park ID. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println(color.redText + "Invalid input. Please enter a numeric value.");
                input.nextLine(); // clear the invalid input
            }
        }
    }


    public void leaveReview(Scanner input, User user) {
        boolean validPark = false; // flag for error handling

        while (!validPark) {
            try {
                System.out.println(color.greenText + "Enter the park ID of the park you want to leave a review for:");
                int parkId = input.nextInt();

                if (parkId > 0 && parkId <= parks.size()) {
                    for (Park park : this.parks) {
                        if (park.getParkID() == parkId) {
                            System.out.println(color.greenText + "Enter your review:");
                            input.nextLine(); // clear the newline character
                            String text = input.nextLine();
                            
                            Review review = new Review(user.getUsername(), text);
                            park.addReview(review);
                            System.out.printf(color.yellowText + "Thank you for your review! The updated reviews for %s are:\n%s\n",
                                    park.getParkName(), park.getReviews());
                            validPark = true; 
                        }
                    }
                } else {
                    System.out.println(color.redText + "Invalid park ID. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println(color.redText + "Invalid input. Please enter a numeric value.");
                input.nextLine(); // clear the invalid input
            }
        }
    }
    
    // ORDER BY IDS
    public void orderByID(List<Park> parkList) {
        quickSortByID(parkList, 0, parkList.size() - 1);
        System.out.println(color.magentaText + "Parks ordered by their Park ID:");
    }
    
    private void quickSortByID(List<Park> parks, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionByID(parks, low, high);
            quickSortByID(parks, low, pivotIndex - 1);
            quickSortByID(parks, pivotIndex + 1, high);
        }
    }

    // Requires unique naming and cannot be added to method overloading group of Partition methods
    // due to same parameters being used --> it would create same method signature 
    private int partitionByID(List<Park> parks, int low, int high) {
        double pivot = parks.get(high).getParkID(); // Use the last park's ID as pivot
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (parks.get(j).getParkID() < pivot) { // Ascending order
                i++;
                Collections.swap(parks, i, j); //swaps using java.util.Collections instead of using temp variable
            }
        }
        Collections.swap(parks, i + 1, high);
        return i + 1;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    // ORDER BY CLOSEST DISTANCE METHOD
    public void orderByDistance(List<Park> parkList, double userLatitude, double userLongitude) {
        calculateAllDistances(parkList, userLatitude, userLongitude);
        quickSortByDistance(parkList, 0, parkList.size() - 1, userLatitude, userLongitude);

        System.out.println(color.boldText + color.magentaText + "Parks ordered by closest distance:" + color.unbold);
    }
    
    private void quickSortByDistance(List<Park> parks, int low, int high, double userLatitude, double userLongitude) {
        if (low < high) {
            int pivotIndex = partition(parks, low, high, userLatitude, userLongitude);
            quickSortByDistance(parks, low, pivotIndex - 1, userLatitude, userLongitude);
            quickSortByDistance(parks, pivotIndex + 1, high, userLatitude, userLongitude);
        }
    }

    //BELOW: METHOD OVERLOADING OF PARTITION
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
    
    public void calculateAllDistances(List<Park> parkList, double userLatitude, double userLongitude) {
        for (Park park : parkList) {
            double distance = calculateDistance(userLatitude, userLongitude, park.getLatitude(), park.getLongitude());
            park.setDistance(distance);
        }
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); //distance between two coordinates formula
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    // ORDER BY RATINGS
    public void orderByRating(List<Park> parkList) {
        quickSortByRating(parkList, 0, parkList.size() - 1);
        System.out.println(color.boldText + color.magentaText + "Parks ordered by their Rating:" + color.unbold);
    }
    
    public static void quickSortByRating(List<Park> parks, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(parks, low, high);
            quickSortByRating(parks, low, pivotIndex - 1);
            quickSortByRating(parks, pivotIndex + 1, high); //recursion
        }
    }
    
    //BELOW: METHOD OVERLOADING OF PARTITION
    private static int partition(List<Park> parks, int low, int high) {
        double pivot = parks.get(high).getAverageRating(); // Use the last park's rating as pivot
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (parks.get(j).getAverageRating() > pivot) { // Descending order
                i++;
                Collections.swap(parks, i, j);
            }
        }
        Collections.swap(parks, i + 1, high);
        return i + 1;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // allows users to choose to only display parks that meet a certain rating or with a certain amenity
    public static void filterParks(Scanner userInput, ParkManager manager) {
        System.out.println(color.greenText + "\nWould you like to filter parks by ratings or an amenity:");
        System.out.println(color.greenText + "1. Highest Ratings");
        System.out.println(color.greenText + "2. Specific Amenities");
        System.out.print(color.greenText + "Enter your choice: ");

        int choice = userInput.nextInt();
        userInput.nextLine();

        switch (choice) {
            case 1: // show all parks with an average rating equal or above the specified user rating
                System.out.print(color.magentaText + "Set minimum rating to filter by (1-5, integer only): ");
                int minRating = userInput.nextInt();
                
                boolean noParks_rating = true;
                for (Park park : manager.getParks()) {
                    if (park.getAverageRating() > minRating) {
                        noParks_rating = false;
                        System.out.println(park);
                        for (int j = 0; j < 100; j++) { // park profile separator
                            System.out.print("-");
                        }
                        System.out.println("");
                    }
                }
                
                if(noParks_rating) {
                    System.out.println(color.redText + "There are no parks known to have this rating or above.");
                }
                break;
            case 2: // shows all parks with the amenity the user searche dfor
                System.out.print(color.greenText + "Enter the amenity you are looking for: ");
                String amenity = userInput.nextLine();
                
                boolean noParks_amenity = true;
                for (Park park : manager.getParks()) {
                    if (park.getAmenities().toLowerCase().contains(amenity.toLowerCase())) { //linear search for amenity; not case sensitive
                        noParks_amenity = false;
                        System.out.println(park);
                        for (int j = 0; j < 100; j++) { // line separator
                            System.out.print("-");
                        }
                        System.out.println("");
                    }
                }
                
                if(noParks_amenity) {
                    System.out.println(color.redText + "There are no parks known to have those amenities.");
                }
                break;
            default:
                System.out.println(color.redText + "Invalid choice. Returning to main menu.");
                break;
        }
    }

    // the method below creates a park loop for the user using a starting park and the 4 other closest ones
    public static void generateParkLoop(Scanner userInput, User user, ParkManager manager) {
        System.out.println(color.greenText + "\nWhich park are you at right now? Or which one will you go to first?");
        System.out.println(color.greenText + "1. Enter by name of Park");
        System.out.println(color.greenText + "2. Enter by address of Park");
        System.out.println(color.greenText + "3. I don't know...just find the park nearest to me!");
        System.out.print(color.greenText + "Enter your choice: ");

        int choice = userInput.nextInt();
        userInput.nextLine();

        Park firstPark = null; // initialize Park instancr temporarily to null
        switch (choice) {
            case 1: // ask for park name
                System.out.print(color.greenText + "Enter first park name: ");
                String firstParkName = userInput.nextLine();
                firstPark = manager.findParkByName(firstParkName);
                break;
            case 2: // ask for park address
                System.out.print(color.greenText + "Enter first park address: ");
                String firstParkAddress = userInput.nextLine();
                firstPark = manager.findParkByAddress(firstParkAddress);
                break;
            case 3: // use user current location to order and find nearest park
                manager.orderByDistance(manager.getParks(), user.getLatitude(), user.getLongitude());
                firstPark = manager.getParks().get(0);
            default:
                System.out.println(color.redText + "Invalid choice. Returning to main menu.");
                break;
        }
        
        if (firstPark == null) {
            System.out.println(color.redText + "Park not found. Returning to main menu.");
            return;
        }

        List<Park> nearbyParks = new ArrayList<>(manager.getParks()); //new copy of the parks list (not tampering with the original)
        manager.orderByDistance(nearbyParks, firstPark.getLatitude(), firstPark.getLongitude());
        System.out.println(color.yellowText + "Here is your customized park loop:");
        for (int i = 0; i < 5; i++) { // Limit loop to 5 parks
            System.out.println(nearbyParks.get(i));
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //allows users to type in names of parks rather than ids for generating park loops
    public Park findParkByName(String parkName) {
        for (Park park : parks) {
            if (park.getParkName().equalsIgnoreCase(parkName)) {
                return park;
            }
        }
        return null;
    }
    
    public Park findParkByAddress(String parkAddress) {
        for (Park park : parks) {
            if (park.getAddress().equalsIgnoreCase(parkAddress)) {
                return park;
            }
        }
        return null;
    }

}  
