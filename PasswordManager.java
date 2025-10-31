/*
This passwordManager class stores all the functions that actually salt and hash
passwords, enabling secure storage of data in user.

I heavily leaned on the java.security library to properly salt and the java.crypto library
in order to creat the secret key
 */



import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordManager {

    //This function gets a salt number from SecureRandom, inserting it into the salt
    private static byte[] getSalt() throws NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;

    }
    /*This function sets up to create the hashed password with the PBEKeySpec class,
    then inserts this class into SecretKeyFactory using SHA256 to generate the hashed key
     */
    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec setup =  new PBEKeySpec(password.toCharArray(), salt, 10000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        //After generating key, we need to turn this into bits, so we can then concatenate
        byte[] hash = factory.generateSecret(setup).getEncoded();
        //Here we concatenate the salt and the hash, with a colon, intot he user class
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);

    }

    //This function is publicly facing, simple way to implement the getSalt and hashPassword,
    //Properly creating the account.
    public static String makePassword(String password) {
        try {
            byte[] salt = getSalt();
            return hashPassword(password, salt);
        }
        //Error handling if the SKF has invalid instructions, or using an odd version of java
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException("Error ", e);
        }
    }

    //Verifies if password enters matches the account
    public static boolean verifyPassword( String passAttempt, String hashPassword ){

       try {
           //Get the hash out of the password stored in user
           String[] saltAndPassword = hashPassword.split(":");

           //Decode string to byte
           byte[] salt = Base64.getDecoder().decode(saltAndPassword[0]);

           //Hash the attempt with the salt we got
           String attempt = hashPassword(passAttempt, salt);

           //Return if succesful
           return attempt.equals(hashPassword);
       }
       //Error handlers
       catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

           e.printStackTrace();
           throw new RuntimeException("Error ", e);
       }


        }


}
