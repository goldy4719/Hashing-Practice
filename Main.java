/* Created by Miles Goldberger 10/28/25
    This is a practice program, messing with hashes using SHA-256.
    By utilizing the Java.Security and Javax.Crypto libraries,
    I hash passwords and store it so that it stays secure.
    
    Main contains a menu to allow the user to sign in, create an account, or ext


 */



import java.util.Scanner;
import java.lang.Character;





public class Main {

//Menu function to print out the menu and get the users input
   public static char menu(){
       char choice;
       Scanner sc = new Scanner(System.in);
       System.out.println("Type 's' to sign in , 'c' to create an account, or 'x' to exit.");
       choice =  sc.next().charAt(0);
       Character.toUpperCase(choice);
       return choice;
   }

    public static void main(String[] args) {

       /*
       Need to record count of users, an array of users, length of that array,
       a scanner, and a char to store the choice in the menu.
        */
        int count = 0;
        //User Length will start at 5 (should increase every 5)
        int userLength = 5;
        Scanner input = new Scanner(System.in);
        User user = null;

        char choice;

        User[] users = new User[userLength];


        //Do while loop letting user create, sign in, and exit
        do {

            choice = menu();

            choice = Character.toUpperCase(choice);

            //Signing in process
            if (choice == 'S') {

                //Check to see if an account has been made yet
                if (count == 0) {
                    System.out.println("There are no accounts in memory, please create one. ");
                    choice = 'C';
                }
                else {
                    String username;
                    String password;
                    boolean checked = false;

                    System.out.println("Enter username : ");
                    username = input.nextLine();
                    System.out.println("Enter password : ");
                    password = input.nextLine();

                    user = User.checkUser(users, password, username, count);

                    if (user == null)
                    {
                        checked = false;
                    }
                    else {
                        checked = true;
                    }
                    if (checked) {
                        System.out.println("Welcome, " + user.getName());
                        do {
                            System.out.println("Type 'X' to exit, or 'S' to sign in again");
                            choice = input.next().charAt(0);
                            input.nextLine();
                            choice = Character.toUpperCase(choice);
                        } while (choice != 'X' && choice != 'S');


                    } else {
                        System.out.println("Invalid username or password");
                    }
                }



            }

            //Use an if instead of else if so that directly redirected to creating account
            //If it is the first account
            if (choice == 'C') {
                //Create a new user to put info in in future, as well as necessary data
                User newUser = new User();
                String username;
                String password;
                String name;
                //Boolean to see if passwords match
                boolean match = false;

                System.out.println("Enter desired username: ");
                username = input.nextLine();

                //Do loop to validate password
                do {
                    System.out.println("Enter desired password: ");
                    password = input.nextLine();
                    System.out.println("Validate password: ");
                    String password2 = input.nextLine();

                    if (password.equals(password2)) {
                        match = true;
                    }
                    else{
                        System.out.println("Passwords do not match, try again.");
                    }
                } while (!match);
                System.out.println("Enter your first name: ");
                name = input.nextLine();
                //Make user and increase count
                newUser.createUser(users, count, userLength, username, password, name);
                count++;

                System.out.println("User created successfully, log in again!");

            }

        }while (choice != 'X');

        System.out.println("Thank you for using this application!");
    }
}