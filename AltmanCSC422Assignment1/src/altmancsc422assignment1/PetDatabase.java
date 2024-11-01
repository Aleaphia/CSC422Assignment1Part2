/*
Assignment 1 Part 2: Version Control with git/GitHub
Due 11/3/24

I certify, that this computer program submitted by me is all of my own work.
Signed: Shoshana Altman
*/
package altmancsc422assignment1;
import java.util.*;

public class PetDatabase {
    //Attributes ---------------------------------------------------------------
    //Scanner object
    public static Scanner scnr = new Scanner(System.in);
    //Array for holding Pet objects
    private final static ArrayList<Pet> pets = new ArrayList<>();
    
    public static void main(String[] args) {
        //Welcome message
        System.out.println("Pet Database Program.");
        
        //Loop and switch to handle user interactions
        boolean end = false;
        while(!end){
            switch (getUserChoice()){
                case 1 -> showAllPets();
                case 2 -> addPets();
                case 3 -> searchPetsByName();
                case 4 -> searchPetsByAge();
                case 5 -> end = true;
            }
        }
        
        //Ending message
        System.out.println("Goodbye!");
    }
    
    private static int getUserChoice(){
        int userChoice = -1;
        
        //Prompt user choice
        System.out.println("""
                           
                           What wouldyou like to do?
                            1) View all pets
                            2) Add more pets
                            3) Search for pet by name
                            4) Search for pet by age
                            5) Exit program""");
        System.out.print("Your choice: ");
        
        //Validate input
        boolean validInput = false;
        while(!validInput){
            try{
                userChoice = scnr.nextInt();
                validInput = true; //valid input found
            }
            catch(Exception e){
                //Invalid input, output error message
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            //Clear input line
            scnr.nextLine();
        }
        
        System.out.println();//Formatting
        
        return userChoice;//Return input
    }
    private static void addPets(){
        while(true){
            //Prompt user input
            System.out.print("add pet (name, age): ");
            //Exception handling
            try {
                //Get input from user
                String[] input = scnr.nextLine().split(" ");
                
                //Check for exit condition
                if (input[0].equals("done"))
                    return;
                
                //Parse input
                String name = input[0].toLowerCase(); //Store name as lower case
                int age = Integer.parseInt(input[1]);
                
                //Add pet to pets array
                pets.add(new Pet(name, age));
            }
            catch (Exception e){
                //Print out message for any exception
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
    private static void showAllPets(){
        //Print header
        printTableHeader();
        //Print all rows
        for (int index = 0; index < pets.size(); index++){
            printTableRow(index, pets.get(index).getName(), pets.get(index).getAge());
        }
        //Print footer
        printTableFooter();
        //Print number of rows
        System.out.println("" + pets.size() + " rows in set.");
    }
    private static void searchPetsByName(){
        //Get user input
        System.out.print("Enter a name to search: ");
        String input = scnr.next();
        scnr.nextLine();
        
        //Track number of elements fitting search criteria
        int nameCount = 0;
        //Print header
        printTableHeader();
        //Print all rows
        for (int index = 0; index < pets.size(); index++){
            if (input.toLowerCase().equals(pets.get(index).getName())){
                printTableRow(index, pets.get(index).getName(), pets.get(index).getAge());
                nameCount++;
            }
        }
        //Print footer
        printTableFooter();
        //Print number of rows
        System.out.println("" + nameCount + " rows in set.");
    }
    private static void searchPetsByAge(){
         //Get user input
        System.out.print("Enter age to search: ");
        try{
            int input = scnr.nextInt();
            scnr.nextLine();
            
            //Track number of elements fitting search criteria
            int ageCount = 0;
            //Print header
            printTableHeader();
            //Print all rows
            for (int index = 0; index < pets.size(); index++){
                if (input== pets.get(index).getAge()){
                    printTableRow(index, pets.get(index).getName(), pets.get(index).getAge());
                    ageCount++;
                }
            }
            //Print footer
            printTableFooter();
            //Print number of rows
            System.out.println("" + ageCount + " rows in set.");
        }
        catch (Exception e){
            scnr.nextLine();
            System.out.println(e.getClass().getSimpleName());
        }
    }
    private static void printTableHeader(){
        System.out.println("+----------------------+");
        System.out.printf("|%3s | %-10s|%4s |\n", "ID", "NAME", "AGE");
        System.out.println("+----------------------+");
    }
    private static void printTableRow(int id, String name, int age){
        //Modify name so that the first letter is capitalized
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        
        //Prints id, name, and age into table with sizes matching the assignment criteria
        System.out.printf("|%3s | %-10.10s|%4s |\n", id, name, age);
    }
    private static void printTableFooter(){
        System.out.println("+----------------------+");
    }
}
