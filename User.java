public class User {

    private String username;
    private String hashedPassword;
    private String name;

    //Default constructor
    public User(){
        username = "";
        hashedPassword = "";
        name = "";
    }

    //Simple getters for each data point
    public String getHashedPassword(){
        return hashedPassword;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    /*Function to create user, and add to list of arrays, current count, and length.
   Function also takes in user object, username, and password pre hash.
   Function calls makePassword to salt and hash and store

   Function also maintains storage space, increasing and decreasing the array as there
   are more sign ins
   */
    public void createUser(User[] users, int count, int userLength, String username, String Password, String name) {

        this.username = username;
        hashedPassword = PasswordManager.makePassword(Password);
        this.name = name;


        users[count] = this;
        count++;

        //Check length, make the array bigger if needing more space, and smaller if needing less
        if (count == userLength) {
            userLength += 5;

            User[] newUsers = new User[userLength];
            System.arraycopy(users, 0, newUsers, 0, count);
            users = newUsers;

            newUsers = null;

        } else if (userLength - count > 5) {

            userLength -= 5;
            User[] newUsers = new User[userLength];
            System.arraycopy(users, 0, newUsers, 0, count);

        }

    }

        /*
            This function checks if a sign in matches any user and returns that user.
            Returning the user enables us to then give the user the proper page once
            signed in. If the sign in does not match anything in the data, then return null.

         */
        public static User checkUser(User[] users, String passAttempt, String username, int count){


        int index;

        for (int i = 0; i < count; ++i) {
            if (users[i].getUsername().equals(username)) {

               if (PasswordManager.verifyPassword(passAttempt, users[i].getHashedPassword())) {
                   return users[i];
               }

            }
        }

        return null;
    }
}
