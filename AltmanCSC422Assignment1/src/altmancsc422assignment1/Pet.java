/*
Assignment 1 Part 2: Version Control with git/GitHub
Due 11/3/24

I certify, that this computer program submitted by me is all of my own work.
Signed: Shoshana Altman
*/
package altmancsc422assignment1;

public class Pet {
    //Attributes
    private String name;
    private int age;
    
    //Constructor
    public Pet(String name, int age){
        this.name = name;
        this.age = age;
    }
    
    //Methods-------------------------------------------------------------------
    //Get methods
    public String getName(){return name;}
    public int getAge(){return age;}
    
    //Set methods
    public void setName(String name){this.name = name;}
    public void setAge(int age){this.age = age;}
}
