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
    
    manager.displayAllParks("src/main/java/betterdataset.csv"); //reminder: on replit we have to copy file path
    manager.ratePark(userInput);
    manager.leaveReview(userInput, user);
    manager.displayAllParks("src/main/java/betterdataset.csv");

  } //end main

} //end class
