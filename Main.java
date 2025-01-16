import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {
          
    System.out.println("Welcome to Park-O-Meter!");
    Scanner userInput = new Scanner(System.in);
    
    System.out.print("Enter your username: ");
    String username = userInput.nextLine();
    System.out.print("Enter your latitude: ");
    double latitude = userInput.nextDouble();
    System.out.print("Enter your longitude: ");
    double longitude = userInput.nextDouble();
    userInput.nextLine(); 
  
    
    User user = new User(username, latitude, longitude); 
    ParkManager manager = new ParkManager();
    
    manager.readParksFile("src/main/java/betterdataset.csv"); //reminder: on replit we have to copy file path
    manager.displayAllParksByDistance(user);
    manager.ratePark(userInput);
    manager.leaveReview(userInput, user);
    manager.displayAllParks();



    /*public double[] userLocation(Scanner userInput) {
      double[] userCoordinates = new double[2];
      boolean valid = false;

    while (!valid) {
      try {
          System.out.println("Enter your latitude (-90 to 90):");
          double latitude = userInput.nextDouble();

          if (latitude < -90 || latitude > 90) {
              System.out.println("Invalid latitude. Please enter a value between -90 and 90.");
              continue;
          }
        
          System.out.println("Enter your longitude (-180 to 180):");
          double longitude = userInput.nextDouble();

          if (longitude < -180 || longitude > 180)
            System.out.println("Invalid longitude. Please enter a value between -180 and 180.");
            continue;
    
      
          userCoordinates[0] = latitude;
          userCoordinates[1] = longitude;
          valid = true;
      
    } catch(InputMismatchException e) {
      System.out.println("Invaild input. Please enter a numeric value for both latitude and longitude.");
    }
        }
      System.out.printf("Your location is set to Latitude: %.2f, Longitude: %.2f\n", userCoordinates[0], userCoordinates[1]);
      return userCoordinates;

}*/

        
        
  } //end main

} //end class
