import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {
          
    System.out.printf("Welcome to Park-O-Meter! Please enter your username and location to get started!!\n\n");
    ParkManager manager = new ParkManager();
    manager.displayAllParks("src/main/java/betterdataset.csv"); //reminder: on replit we have to copy file path
  } //end main

      /*List<Park> parkList = new ArrayList<>();
      parkList.add(new Park("Greenwood Park", "City Center", 5.0, Arrays.asList("Playground", "Washroom")));
      parkList.add(new Park("Sunset Trail", "Northside", 3.0, Arrays.asList("Hiking", "Picnic Area")));
      parkList.add(new Park("Lakeview Park", "Southside", 0.0, Arrays.asList("Free Entrance", "Boating")));

      ParkManager manager = new ParkManager(parkList);
      Scanner scanner = new Scanner(System.in);*/
} //end class
