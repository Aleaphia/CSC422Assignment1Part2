/*
Assignment 2 Part 2: GitHub issue and project
Due 11/10/24

I certify, that this computer program submitted by me is all of my own work.
Signed: Shoshana Altman
 */
package altmancsc422assignment1;

public class InvalidArgumentException extends Exception{
    //Constructor to pass message
    public InvalidArgumentException(String message){
        super(message);
    }
}
