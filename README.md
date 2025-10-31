# Java Secure Credential Storage (PBKDF2 & Salt)

This project is a complete re-engineering of an older, insecure login system I built. After gaining hands-on experience at a cybersecurity MSP, I realized the critical flaws in my original approach. I rebuilt the entire persistence layer to demonstrate the correct, modern standards for secure credential storage.

It's a complete, command-line-driven application that securely handles user registration and login, built from the ground up to be safe against common attacks.

## Key Features

* **Full Command-Line Interface (CLI):** `Main.java` runs a complete menu-driven system for creating accounts, signing in, and exiting.
* **Secure User Registration:** Includes password validation (ensuring the user's two password attempts match) and secure-by-default account creation.
* **Dynamic User Database:** The `User.java` class features a C-style dynamic array (`User[]`) that automatically resizes itself (using `System.arraycopy`) as more users are added, demonstrating low-level data structure management.

## Security Principles Implemented

The `PasswordManager.java` class is the core of this project's security, built to defeat modern attacks.

### 1. Strong Hashing Algorithm
* **PBKDF2 with HmacSHA256:** This project uses the industry-standard key derivation function, `PBKDF2`, which is designed specifically for passwords. It is vastly more secure than a simple, fast hash like SHA-256.

### 2. Cryptographic Salting (Defeats Rainbow Tables)
* **16-Byte Random Salt:** A unique, 16-byte cryptographic salt is generated for *every single user* using `SecureRandom.getInstanceStrong()`.
* **How it Works:** This salt is combined with the user's password *before* hashing. This ensures that even if two users have the exact same password, their stored hashes will be completely different, rendering pre-computed "rainbow table" attacks useless.
* **Storage:** The salt and the hash are encoded in Base64 and stored together in a single string (`salt:hash`) in the `User` object for easy verification.

### 3. Key Stretching (Defeats Brute-Force)
* **10,000 Iterations:** The `PBKDF2` algorithm is configured to run **10,000 times** for every password.
* **How it Works:** This makes the hashing process *intentionally slow* (milliseconds for a real user, but an eternity for an attacker). It makes a brute-force attack—trying billions of password guesses—computationally impossible.

### 4. Secure Exception Handling
* The code is built to "fail securely" by properly handling all `NoSuchAlgorithmException` and `InvalidKeySpecException` errors.

## How to Run

1.  Compile all `.java` files:
    ```bash
    javac *.java
    ```
2.  Run the `Main` class:
    ```bash
    java Main
    ```
3.  Follow the on-screen prompts to create an account or sign in.
