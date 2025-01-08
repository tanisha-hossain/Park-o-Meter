import java.util.*;
import java.io.*;

//REMINDER: the displayAllParks method works to show them all rn but we might wanna try making the displayParkInfo one so when users leave reviews it shows up under the specific parks info box
//also we should try coding a way for cleaning up the code of the original spreadsheet so if we decide to add more parks we dont gotta manually do it
public class ParkManager {
  private List<Park> parks;

  public ParkManager(){
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
          } else if (c == ',' && withinQuotes==false) { // Going to another column in the csv
              attributes.add(sb.toString().trim());
              sb = new StringBuilder(); // refresh and clear string builder for next column
          } else { // This is a regular character
              sb.append(c);
          }
      }
      attributes.add(sb.toString().trim()); // Add the last element
      return attributes.toArray(new String[0]);
  }


  public void displayAllParks(String filePath){  
    try  {
        File myFile = new File(filePath);
        Scanner scanner = new Scanner(myFile);
        
        /*if (myFile.exists()) {
            System.out.println("File name: " + myFile.getName());
            System.out.println("File size in bytes " + myFile.length());
            // print out first line which is header
            System.out.println("Header: " + scanner.nextLine());
        } 
        else {
            System.out.println("The file does not exist.");
        }
        if(!scanner.hasNext()==true){
            System.out.println("Error: File is empty");
        }*/
        if(!scanner.hasNext()){
            System.out.println("Error: File is empty");
        }
        if (!myFile.exists()) {
            System.out.println("The file does not exist.");
        }
        else{
            //System.out.println("The file exists.");
            System.out.println("Header: " + scanner.nextLine()); //i doubt we need this
        }
        

        while (scanner.hasNextLine()) {
          
          String line = scanner.nextLine();
          String[] parts = parseLine(line);

          String parkName = parts[1];
          String address = parts[3];
          String amenities = parts[2];
          double latitude = Double.parseDouble(parts[4]);
          double longitude = Double.parseDouble(parts[5]);
          //int rating = Integer.parseInt(parts[6]);

          this.parks.add(new Park(parkName, address, amenities, latitude, longitude, 0)); //initial rating of 0
        }
    } //end try
    catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());
    }
    
    for (int i= 0; i < this.parks.size(); i++) { // displays park attributes
        Park park = this.parks.get(i);
        System.out.printf("Park ID: %d\n", i + 1);
        System.out.printf("Name: %s\n", park.getParkName());
        System.out.printf("Address: %s\n", park.getAddress());
        System.out.printf("Amenities: %s\n", park.getAmenities());
        System.out.printf("Latitude: %f\n", park.getLatitude());
        System.out.printf("Longitude: %f\n", park.getLongitude());
        System.out.printf("Rating: %d\n", park.getRating());

        for (int j = 0; j < 100; j++){ //line separator 
            System.out.print("-"); 
        }
        System.out.println("");
    }
  }
  
}
//if # of commas < than the number of commas in a line without amenities, ignore the commas within the array
// other option - check iif in string cuz only the amenities is surrounded by quotes in the csv file (new one)
//ORDER ALPHABETICALLY METHOD
//ORDER BY CLOSEST DISTANCE
