/*
Assignment 1 Part 2: Version Control with git/GitHub
Due 11/3/24

I certify, that this computer program submitted by me is all of my own work.
Signed: Shoshana Altman
*/
package altmancsc422assignment1;
import java.io.*;
import java.util.*;

public class PetDatabase {
    //Attributes ---------------------------------------------------------------
    //Constants
    public static final int CAPACITY = 5;
    public final static String FILENAME = "pets.txt";

    //Global variables
    //Scanner object
    public static Scanner userInputScnr = new Scanner(System.in);
    //Array for holding Pet objects
    private final static ArrayList<Pet> pets = new ArrayList<>();
    
    //Methods ------------------------------------------------------------------
    public static void main(String[] args) {
        //Load database
        loadDatabase();
        
        //Welcome message
        System.out.println("Pet Database Program.");
        
        //Loop and switch to handle user interactions
        boolean end = false;
        while(!end){
            switch (getUserChoice()){
                case 1 -> showAllPets();
                case 2 -> addPets();
                case 3 -> updatePet();
                case 4 -> removePet();
                case 5 -> searchPetsByName();
                case 6 -> searchPetsByAge();
                case 7 -> end = true;
            }
        }
        
        //Save database
        saveDatabase();
        
        //Ending message
        System.out.println("Goodbye!");
    }
    
    private static int getUserChoice(){
        int userChoice = -1;
        
        //Prompt user choice
        System.out.println("""
                           
                           What would you like to do?
                            1) View all pets
                            2) Add more pets
                            3) Update an existing pet
                           `4) Remove an existing pet
                            5) Search for pet by name
                            6) Search for pet by age
                            7) Exit program""");
        System.out.print("Your choice: ");
        
        //Validate input
        boolean validInput = false;
        while(!validInput){
            try{
                userChoice = userInputScnr.nextInt();
                validInput = true; //valid input found
            }
            catch(Exception e){
                //Invalid input, output error message
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            //Clear input line
            userInputScnr.nextLine();
        }
        
        System.out.println();//Formatting
        
        return userChoice;//Return input
    }
    private static void addPets(){
        while(true){
            //Prompt user input
            System.out.print("add pet (name, age): ");
            //Get input from user
            String input = userInputScnr.nextLine();

            //Check for exit condition
            if (input.split(" ")[0].equals("done"))
                return;
            
            //Add pet to database
            try{
                addPetInfoToDatabase(input);
            }
            catch(FullDatabaseException | InvalidAgeException | InvalidArgumentException e){
                System.out.println("Error: " + e.getMessage());
                if( e.getClass().equals(FullDatabaseException.class))
                    return;
            }
            

        }
    }
    private static String[] validatePetInfo(String petInfoLine) throws InvalidAgeException, InvalidArgumentException {
        //Check for valid input length and type
        String[] petInfo = petInfoLine.split(" ");
        int age = -1;
        
        try{
            //Check for valid input length
            if (petInfo.length != 2)
                throw new Exception();
            //check age
            age = Integer.parseInt(petInfo[1]);
        }
        catch (Exception e){
            throw new InvalidArgumentException(petInfoLine + " is not a valid input.");
        }
        
        if(age < 1 || age > 20)
            throw new InvalidAgeException(age + " is not a valid age.");
        
        return petInfo;
    }
    private static void addPetInfoToDatabase(String petInfoLine) throws FullDatabaseException, InvalidAgeException, InvalidArgumentException{
        //Check database size against capacity
        if(pets.size() >= CAPACITY)
            throw new FullDatabaseException();
        //Location to store parsed information
        String[] petInfo = validatePetInfo(petInfoLine);            
        String name = petInfo[0].toLowerCase(); //Store name as lower case
        int age = Integer.parseInt(petInfo[1]);
                
        //Check for valid input age
        if(age < 1 || age > 20)
            throw new InvalidAgeException(age + " is not a valid age.");
        
        //Add pet to pets array
        pets.add(new Pet(name, age));
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
    private static void updatePet(){
        //Display Pet list
        showAllPets();
        //Prompt user input
        System.out.print("Enter the pet ID to update: ");
        //Collect input
        int idInput;
        try{ 
            //Prompt user
            System.out.print("Enter the pet ID to update: ");
            //Get valid input
            idInput = getIdInput();
            
            //Prompt user
            System.out.print("Enter a new name and a new age: ");
            //Get valid input from user
            String[] petInfo = validatePetInfo(userInputScnr.nextLine());
            
            //Parse pet info
            String name = petInfo[0];
            int age = Integer.parseInt(petInfo[1]);
            
            //Update pet and output change message
            System.out.print(pets.get(idInput) + " changed to ");

            //Update pet
            pets.get(idInput).setName(name);
            pets.get(idInput).setAge(age);

            System.out.println(pets.get(idInput));
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void removePet(){
        //Display Pet list
        showAllPets();
        
        
        //Collect input
        int input;
        try{
            //Prompt user input
            System.out.print("Enter the pet ID to remove: ");
            //Get valid input
            input = getIdInput();
            
            //Remove pet
            System.out.println(pets.get(input) + " is removed.");
            pets.remove(input);
        }
        catch (InvalidIdException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void searchPetsByName(){
        //Get user input
        System.out.print("Enter a name to search: ");
        String input = userInputScnr.next();
        userInputScnr.nextLine();
        
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
            int input = userInputScnr.nextInt();
            userInputScnr.nextLine();
            
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
            userInputScnr.nextLine();
            System.out.println(e.getClass().getSimpleName());
        }
    }
    private static int getIdInput() throws InvalidIdException{
        //Collect input
        int input = userInputScnr.nextInt();
        //Clear remaining input line
        userInputScnr.nextLine();
        
        if(input < 0 || input >= pets.size())
            throw new InvalidIdException("ID " + input + " does not exist.");
        
        return input;
    }
    
    
    private static void loadDatabase(){
        //Open file with 'try using' statement
        try (FileInputStream  fileStream = new FileInputStream (FILENAME);//Open file
            Scanner fileScnr = new Scanner(fileStream)//Create Scanner object to read file
                ){
            //Read each line from file
            while( fileScnr.hasNextLine()){
                //Get input from file
                addPetInfoToDatabase(fileScnr.nextLine().trim());
            }
        }
        catch (Exception e){
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        } 
    }
    private static void saveDatabase(){
        //Write database to file
        try (FileOutputStream  fileStream = new FileOutputStream (FILENAME);//Open file
            PrintWriter fileWriter = new PrintWriter(fileStream)//Create PrintWriter
                ){
            
            //Write database to file
            for (int index = 0; index < pets.size(); index++) {
                fileWriter.println(pets.get(index));
            }
        }
        catch (Exception e){
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
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