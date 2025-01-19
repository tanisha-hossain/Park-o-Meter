/*Tanisha Hossain and Faiza Kibria
Park-O-Meter
Description: A text-based application for users to find parks nearby, rate and review park facilities, and generate personalized trails
Created: December 2024
Last Edited: January 2025
Ethics Declaration: "This code is our own work"
*/

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Park-O-Meter!");
        Scanner userInput = new Scanner(System.in);

        //get user information
        System.out.print("Enter your username: ");
        String username = userInput.nextLine();
        System.out.print("Enter your latitude: ");
        double latitude = userInput.nextDouble();
        System.out.print("Enter your longitude: ");
        double longitude = userInput.nextDouble();
        userInput.nextLine(); 
        User user = new User(username, latitude, longitude);

        ParkManager manager = new ParkManager();
        manager.readParksFile("betterdataset.csv"); //obtains park data through file reading
        manager.calculateAllDistances(manager.getParks(), user.getLatitude(), user.getLongitude());

        navigationMenu(user, manager, userInput); //user navigates through the program with the menu

        System.out.println("Thank you for using Park-O-Meter! Enjoy your day out :)");  //after user chooses to exit
    } //end main
    
    
    // Gives user choices of what to do in the program
    public static void navigationMenu(User user, ParkManager manager, Scanner userInput) {
        String boldText = "\033[0;1m";
        String unbold = "\033[0;0m";
        boolean exit = false;

        while(!exit) {
            System.out.println(boldText + "\nPark-O-Meter Main Menu" + unbold);
            System.out.println("What can Park-O-Meter do for you today? Select an option by entering the corresponding number from the list below:");
            System.out.println("\n1 - Display All Parks");
            System.out.println("2 - Display Parks by Distance");
            System.out.println("3 - Display Parks by Ratings");
            System.out.println("4 - Rate a Park");
            System.out.println("5 - Leave a Review");
            System.out.println("6 - Filter Parks");
            System.out.println("7 - Create a Park Loop");
            System.out.println("8 - Change your current Location");
            System.out.println("9 - Exit");
            System.out.print("Enter your choice: ");

            int choice; //user chooses what they want to do through an integer number representing actions as listed in the strings above
            try {
                choice = userInput.nextInt();
                userInput.nextLine(); 
                
                switch (choice) {
                    case 1: 
                        manager.displayAllParks();
                        break;
                    case 2: 
                        manager.displayAllParksByDistance(user);
                        break;
                    case 3:
                        manager.displayAllParksByRating(user);
                        break;
                    case 4: 
                        manager.ratePark(userInput);
                        break;
                    case 5: 
                        manager.leaveReview(userInput, user);
                        break;
                    case 6: 
                        manager.filterParks(userInput, manager);
                        break;
                    case 7: 
                        manager.generateParkLoop(userInput, user, manager);
                        break;
                    case 8:
                        user.setNewLocation(userInput);
                        break;
                    case 9: 
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                userInput.nextLine(); 
                continue;
            }
        }
    }

} //end class
