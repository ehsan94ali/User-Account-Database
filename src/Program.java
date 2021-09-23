//import libraries
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;

public class Program {
	
	//scanner for user input
	static Scanner keyboard_input = new Scanner(System.in);
	
	//main method
	public static void main(String[] args) {
		
		//instantiate arraylists for "local memory" to store UserAccount variables
		boolean database_online = false;
		ArrayList<String> primaryKeys = new ArrayList<>();
		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> hashes = new ArrayList<>();
		ArrayList<String> phoneNumbers = new ArrayList<>();
		ArrayList<String> emails = new ArrayList<>();
		
		//create file paths to store UserAccount data after program is terminated
		File database_folder = new File("Databases");
		File primaryKey_database = new File("Databases/primaryKey_database.xlsx");
		File userAccount_database = new File("Databases/account_database.xlsx");
		File username_database = new File("Databases/username_database.xlsx");
		File hash_database = new File("Databases/hashes_database.xlsx");
		File phoneNumber_database = new File("Databases/phoneNumber_database.xlsx");
		File email_database = new File("Databases/email_database.xlsx");
		
		//checks if folder and files have already been created
		if(database_folder.exists())
			database_online = true;
		
		//creates files if not already in existence
		create_database_folder(database_folder);
		create_database_file(primaryKey_database);
		create_database_file(userAccount_database);
		create_database_file(username_database);
		create_database_file(hash_database);
		create_database_file(phoneNumber_database);
		create_database_file(email_database);
		
		try {
			//allow code to write to all the files, appends files
			FileWriter userAccount_writer = new FileWriter(userAccount_database, true);
			FileWriter primaryKey_writer = new FileWriter(primaryKey_database, true);
			FileWriter username_writer = new FileWriter(username_database, true);
			FileWriter hash_writer = new FileWriter(hash_database, true);
			FileWriter phoneNumber_writer = new FileWriter(phoneNumber_database, true);
			FileWriter email_writer = new FileWriter(email_database, true);
			
			//if files are new and empty, initializes files with titles for sorting UserAccount data
			if(!database_online) {
				userAccount_writer.write("Primary Key\tUsername\tHash-Password\tPhone Number\tEmail Address");
				primaryKey_writer.write("Primary Keys");
				username_writer.write("Usernames");
				hash_writer.write("Hashes");
				phoneNumber_writer.write("Phone Numbers");
				email_writer.write("Emails");
				database_online = true;
			}
			else {
				//read files and fills up arraylists (local memory) databases
				fill_local_memory(primaryKey_database, primaryKeys);
				fill_local_memory(username_database, usernames);
				fill_local_memory(hash_database, hashes);
				fill_local_memory(phoneNumber_database, phoneNumbers);
				fill_local_memory(email_database, emails);
			}
			
			//close all FileWriters
			userAccount_writer.close();
			primaryKey_writer.close();
			username_writer.close();
			hash_writer.close();
			phoneNumber_writer.close();
			email_writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch  block1
			e.printStackTrace();
		}
		
		//CODE BODY/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//declare variable for user's input
		int user_input;
		UserAccount logged_in;
		
		//do-while, loops until user chooses to QUIT
		do{
			//allows user to choose between "creating a new account", "signing in", and "quitting" or terminating the program
			user_input = mainMenu();
			
			if(user_input == 1) { //create new user account
				
				//create new UserAccount and update local database
				createNewUserAccount(primaryKeys, usernames, hashes, phoneNumbers, emails);				
			}
			else if(user_input == 2) { //user sign in
				
				//allow user to sign in and update local database
				logged_in = signin(primaryKeys, usernames, hashes, phoneNumbers, emails);
				
				/* CHECK TO SEE WHO IS LOGGED IN
				System.out.print("\nprimary key logged in: " + current.getPrimaryKey());
				System.out.print("\nusername: " + current.getUsername());
				System.out.print("\nhash code: " + current.getHash());
				System.out.print("\nphone number: " + current.getPhoneNumber());
				System.out.print("\nemail: " + current.getEmail());
				*/
				
				
				
			}
			else { //QUIT
				
				//update all files using local databases
				update_file(userAccount_database, primaryKeys, usernames, hashes, phoneNumbers, emails);
				update_file(primaryKey_database, primaryKeys, "Primary Keys");
				update_file(username_database, usernames, "Usernames");
				update_file(hash_database, hashes, "Hashes");
				update_file(phoneNumber_database, phoneNumbers, "Phone Numbers");
				update_file(email_database, emails, "Emails");
			}
		}while(user_input != 3); //do-while, loops until user chooses to QUIT
		
		
		//close user input scanner
		keyboard_input.close();
		System.out.println("program terminated."); //confirmation message
	}
	
	//create database folder if it doesn't already exist
	public static void create_database_folder(File database_folder) {
		if(database_folder.mkdir()) {
			try {
				database_folder.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//create database file if it doesn't already exist
	public static void create_database_file(File database_file) {
		try {
			database_file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//fills up ArrayList with data from file
	public static void fill_local_memory(File database_file, ArrayList<String> database) {
		try {
			//initialize and instantiate scanner to read from file
			Scanner scanner = new Scanner(database_file);
			boolean first_line_buffer = true; //flag used to mark first/title line of file
			String data; 
			
			//loop until end of file
			while(scanner.hasNextLine()) {
				
				//if first/title line of file
				if(first_line_buffer) {
					scanner.nextLine(); //read line, to ignore
					first_line_buffer = false; //flag to show the first/title line has been read
				}
				else {
					data = scanner.nextLine(); //store line data in data
					database.add(data); //add data to ArrayList (local memory)
				}
			}
			scanner.close(); //close file reading scanner
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//display main menu, returns validated user input as integer
	public static int mainMenu() {
		
		//declare variables
		String line;
		char input;
		
		//do-while, loops until user enters a valid input
		do {
			//display main menu
			System.out.println("\nUser Account Database");
			System.out.println("=====================");
			System.out.println("1) Create New Account");
			System.out.println("2) Sign In");
			System.out.println("3) Quit");
			System.out.print("User input: ");
			line = keyboard_input.nextLine(); //take in user input with global scanner
			input = line.charAt(0); //only takes in the first character (accounts for user error of entering multiple characters)
			
			//if invalid input entered
			if(input != '1' && input != '2' && input != '3')
				System.out.println("\nERROR. Invalid user input."); //ERROR message
			
		}while(input != '1' && input != '2' && input != '3'); //do-while, loops until user enters a valid input
		
		int input_int = input - '0'; //convert user's valid input to integer
		return input_int; //return menu option as integer input 
	}
	
	//display sign-in menu, allow user to "sign in", change password if password is forgotten, or "back" and return to the main menu
	public static UserAccount signin(ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		//declare variables
		char menu_input;
		String line;
		String username_input, password_input;
		UserAccount logged_in;
		UserAccount empty = new UserAccount();
		
		
		//do-while, loops until user enters a valid input
		do {
			//display sign-in menu
			System.out.println("\nSign In");
			System.out.println("==================");
			System.out.println("1) Login");
			System.out.println("2) Forgot Password");
			System.out.println("3) Back");
			System.out.print("User input: ");
			line = keyboard_input.nextLine(); //take in user input with global scanner
			menu_input = line.charAt(0); //only takes in the first character (accounts for user error of entering multiple characters)
			
			//if invalid input entered
			if(menu_input != '1' && menu_input != '2' && menu_input != '3')
				System.out.println("\nERROR. Invalid user input."); //ERROR message
			
		}while(menu_input != '1' && menu_input != '2' && menu_input != '3'); //do-while, loops until user enters a valid input
		
		//declare variables for sign-in
		int index = -1; //invalid index marker
		String hash;
		
		if(menu_input == '1') { //login
			
			//display login screen
			System.out.println("\nLogin");
			System.out.println("====================");
			System.out.print("\nUsername: ");
			username_input = keyboard_input.nextLine();
			System.out.print("\nPassword: ");
			password_input = keyboard_input.nextLine();
			
			//if valid username
			if(usernames.contains(username_input))
				index = usernames.indexOf(username_input); //get index of username
			else {
				System.out.println("ERROR. Username or password is INCORRECT"); //ERROR message
				return empty;
			}
			
			hash = convertToHash(password_input); //hash user inputted password
			
			//if username was found in (local) database
			if(index >= 0) {
				
				//if password is correct for specific username
				if(hash.equals(hashes.get(index))) {
					logged_in = new UserAccount(primaryKeys.get(index), usernames.get(index), hashes.get(index), phoneNumbers.get(index), emails.get(index));
					System.out.println("\nSuccessful login."); //confirmation message
					return logged_in;
				}
				else {
					System.out.println("ERROR. Username or password is INCORRECT"); //ERROR message
					return empty;
				}
			}
			else
				return empty;
		}
		else if(menu_input == '2') { //forgot password
		
			forgot_password(primaryKeys, usernames, hashes, phoneNumbers, emails);
			return empty;
			
		}
		return empty;
	}
	
	//prompts user for other credentials and grants user access to create new password
	public static void forgot_password(ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		//declare variables
		String username_input, phoneNumber_input, email_input;
		int index;
		
		//display forgot password screen
		System.out.println("\nForgot Password");
		System.out.println("==============");
		System.out.print("\nUsername: ");
		username_input = keyboard_input.nextLine();
		
		//if valid username
		if(usernames.contains(username_input)){
			
			index = usernames.indexOf(username_input); //get index of valid username
			
			//prompt user for phone number synced to account
			System.out.print("\nPhone Number: ");
			phoneNumber_input = keyboard_input.nextLine();
			
			//format phone number to check (local memory) database
			phoneNumber_input = formatPhoneNumber(phoneNumber_input);
			
			//if phone number exists in database
			if(phoneNumbers.contains(phoneNumber_input)) {
				//if phone is not the one listed in the database synced to specific username
				if(!phoneNumber_input.equals(phoneNumbers.get(index))) {
					System.out.println("ERROR. Phone number '" + phoneNumber_input + "' is NOT linked to this account (" + username_input + ")."); //ERROR message
					return;
				}	
			}
			else {
				System.out.println("ERROR. Phone number '" + phoneNumber_input + "' does NOT exist in our database"); //ERROR message
				return;
			}
			
			//prompt user for email synced to account
			System.out.print("\nEmail: ");
			email_input = keyboard_input.nextLine();
			
			//if email exists in (local memory) database
			if(emails.contains(email_input)) {
				//if email is not the one listed in the database synced to specific username
				if(!email_input.equals(emails.get(index))) {
					System.out.println("ERROR. Email '" + email_input + "' is NOT linked to this account (" + username_input + ")."); //ERROR message
					return;
				}
			}
			else {
				System.out.println("ERROR. Email '" + email_input + "' does NOT exist in our database"); //ERROR message
				return;
			}
		}
		else {
			System.out.println("ERROR. Username '" + username_input + "' does NOT exist in our database."); //ERROR message
			return;
		}
		
		//declare variables for changing password			
		String newPassword_input;
		String newPassword_reenter = null;
		String newHash;
		
		//do-while, loops until a valid password is entered and re-entered for security purposes
		do {
			System.out.println("\nChange Password");
			System.out.println("===============");
			System.out.print("\nNew Password: ");
			newPassword_input = keyboard_input.nextLine();
			
			//if not a valid password
			if(!validatePassword(newPassword_input)) {
				System.out.println("ERROR. Invalid password. Password must contain:" 
						+ "\n-AT LEAST 8 CHARACTERS"
						+ "\n-a SPECIAL CHARACTER" 
						+ "\n-a NUMBER" 
						+ "\n-a LOWERCASE letter" 
						+ "\n-AND a CAPITAL letter"); //ERROR message
			}
			else {
				//re-enter password
				System.out.print("\nRe-enter New Password: ");
				newPassword_reenter = keyboard_input.nextLine();
				
				//if both passwords inputted match
				if(newPassword_reenter.equals(newPassword_input)) {
					newHash = convertToHash(newPassword_input); //encrypt password
					hashes.set(index, newHash); //add hash to (local memory) database
					System.out.println("Password SUCCESSFULLY changed."); //confirmation message
				}
				else
					System.out.println("ERROR. Passwords must match."); //ERROR message
			}
			
		}while(!validatePassword(newPassword_input) || !newPassword_reenter.equals(newPassword_input)); //do-while, loops until a valid password is entered and re-entered for security purposes
	}
	//create new UserAccount and add to/update (local memory) databases 
	public static void createNewUserAccount(ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		//declare variables
		UserAccount newUser = new UserAccount();
		String input;
		
		System.out.println("\nCreate New Account");
		System.out.println("==================");
		
		//username
		//do-while, loops until a valid and unused username is entered
		do {
			System.out.print("Username: ");
			input = keyboard_input.nextLine();
			
			//if invalid username
			if(!validateUsername(input))
				System.out.println("ERROR. Username '" + input + "' is INVALID. Username CANNOT contain spaces"); //ERROR message
			else if(usernames.contains(input)) //if username is already in use
				System.out.println("ERROR. Username '" + input + "' already exists."); //ERROR message
			else
				System.out.println("Username '" + input + "' is VALID and available."); //confirmation message
		}while(usernames.contains(input) || !validateUsername(input)); //do-while, loops until a valid and unused username is entered
		newUser.setUsername(input); //assign username to new UserAccount
		
		//password
		String password_input;
		boolean password_match = false;
		//do-while, loops until a valid password is entered and reentered
		do {
			System.out.print("\nPassword: ");
			input = keyboard_input.nextLine();
			
			//if valid pasword
			if(validatePassword(input)) {
				password_input = input;
				System.out.print("\nRe-enter password: ");
				input = keyboard_input.nextLine();
				
				//if reentered password matches initial input
				if(input.equals(password_input)) {
					password_match = true; //handle flag
					System.out.println("Password accepted."); //confirmation message
				}
				else
					System.out.println("ERROR. Passwords do NOT match."); //ERROR message
			}
			else {
				System.out.println("ERROR. Invalid password. Password must contain:" 
						+ "\n-AT LEAST 8 CHARACTERS"
						+ "\n-a SPECIAL CHARACTER" 
						+ "\n-a NUMBER" 
						+ "\n-a LOWERCASE letter" 
						+ "\n-AND a CAPITAL letter"); //ERROR message
			}
			
		}while(!validatePassword(input) || !password_match); //do-while, loops until a valid password is entered and reentered
		newUser.setHash(convertToHash(input)); //assign hash-password to new UserAccount
		
		//phone number
		//do-while, loops until valid and unused phone number is entered
		do {
			System.out.print("\nPhone Number: ");
			input = keyboard_input.nextLine();
			
			//if invalid phone number
			if(!validatePhoneNumber(input))
				System.out.println("ERROR. Phone Number '" + input + "' is invalid. Phone number must only contain 10 digits"); //ERROR message
			else if(phoneNumbers.contains(formatPhoneNumber(input)))
				System.out.println("ERROR. Phone Number '" + input + "' is already registered with another account."); //ERROR message
			else {
				System.out.println("Phone Number '" + input + "' is now SUCCESSFULLY linked to your account."); //confirmation message
				input = formatPhoneNumber(input); //format phone number for (local memory) database
			}
			
		}while(phoneNumbers.contains(formatPhoneNumber(input)) || !validatePhoneNumber(input)); //do-while, loops until valid and unused phone number is entered
		newUser.setPhoneNumber(input); //assign phone number to new UserAccount
		
		//email address
		//do-while, loops until valid and unused email is entered
		do {
			System.out.print("\nEmail Address: ");
			input = keyboard_input.nextLine();
			
			//if invalid email
			if(!validateEmail(input))
				System.out.println("ERROR. Email Address '" + input + "' is an INVALID email address."); //ERROR message
			else if(emails.contains(input))
				System.out.println("ERROR. Email Address '" + input + "' is already registered with another account."); //ERROR message
			else
				System.out.println("Email Address '" + input + "' is now SUCCESSFULLY linked to your account."); //confirmation message
		}while(emails.contains(input) || !validateEmail(input));
		newUser.setEmail(input); //assign email to new UserAccount
		
		//primary key
		input = generate_primaryKey(primaryKeys); //create new and unique primary key
		newUser.setPrimaryKey(input); //assigns primary key to new UserAccount
		
		add_user_to_local_database(newUser, primaryKeys, usernames, hashes, phoneNumbers, emails); //adds newUser
		System.out.println("Account SUCCESSFULLY created."); //confirmation message
		
	}
	
	//creates new and unique primary key
	public static String generate_primaryKey(ArrayList<String> primaryKeys) {
		
		//declare variables
		String primaryKey;
        int pk_int;
        boolean new_pk = false; //new primary key flag
        
        //do-while, loops until a new and unique is created
        do {
        	//empty primary key string
        	primaryKey = "";
        
        	//for-loop until 12 characters are inputted into primaryKey string
	        for(int i = 0; i < 12; i++){
	        	//do-while, loops until valid numbers are generated (numbers from 48 to 90 inclusive, excluding 58 to 64)
	            do{
	                pk_int = (int) ((Math.random() * (43)) + 48); //generate random number from 48 to 90, inclusive
	            }while(pk_int > 57 && pk_int < 65); //do-while, loops until valid numbers are generated (numbers from 48 to 90 inclusive, excluding 58 to 64)
	            
	            //hyphenate after 4th and 8th character
	            if(i == 4 || i == 8)
	                primaryKey += "-";
	            
	            //add ascii character equivalent of pk_int to primaryKey string
	            primaryKey += Character.toString((char)pk_int);
	        }
	        //if primary key does NOT exist in (local memory) database
	        if(!primaryKeys.contains(primaryKey))
	        	new_pk = true; //handle flag
        } while(!new_pk); //do-while, loops until a new and unique is created
        
		return primaryKey; //return new and unique primary key as string
	}
	
	//check if username is valid
	public static boolean validateUsername(String username) {
		//valid username CANNOT contain any spaces
		if(username.contains(" "))
			return false;
		
		return true;
	}

	//check if password is valid
	
	//check if password is valid
	public static boolean validatePassword(String password) {
		/*valid password requires:
		-minimum of 8 characters
		-at least one special character
		-at least one capital letter
		-at least one lower-case letter
		-at least one number*/
		
		//if password contains less than 8 characters
		if(password.length() < 8)
			return false;
		
		boolean containsSpecial=false, containsCapital=false, containsLower=false, containsNumber=false; //requirement flags
		
		//for loop through every character in password
		for (char c : password.toCharArray()) {
			
			//if all requirements met before end of password
			if (containsSpecial && containsCapital && containsLower && containsNumber)
				return true;
			else {
				//if upper case, lower case, digit, or special character encountered -> handle respective flag
				if (Character.isUpperCase(c))
					containsCapital = true;
				else if (Character.isLowerCase(c))
					containsLower = true;
				else if (Character.isDigit(c))
					containsNumber = true;
				else
					containsSpecial = true;
			}
		}

		return (containsSpecial && containsCapital && containsLower && containsNumber); //return boolean value of all flags
	}
	
	//check if phone number is valid
	public static boolean validatePhoneNumber(String phoneNumber) {
		//valid phone number contains 10 digits
		
		//if phone number is over 14 characters
		if(phoneNumber.length() > 14)
			return false;
		
		int digitCounter = 0;
		
		//for loop through every character in phoneNumber
		for(char c : phoneNumber.toCharArray()) {
			
			//if letter encountered
			if(Character.isLetter(c))
				return false;
			else if(Character.isDigit(c)) {
				digitCounter++;
				
				//if over 10 digits encountered
				if(digitCounter > 10)
					return false;
			}
		}
		//if NOT exactly 10 digits
		if(digitCounter != 10)
			return false;
		
		return true;
	}

	//modifies phoneNumber to uniform format
	
	//modifies phoneNumber to uniform format
	public static String formatPhoneNumber(String phoneNumber) {
		
		//initialize variable
		String formatted = "";
		
		//for loop through every character in phoneNumber
		for(char c : phoneNumber.toCharArray()) {
			
			//if character is a digit
			if(Character.isDigit(c))
				formatted += c; //add to formatted string
		}
		return formatted; //return formatted phone number as string
	}

	//checks for phoneNumber is (local memory) database
	public static boolean phoneNumberFound(String phoneNumber, ArrayList<String> phoneNumbers) {
		
		String phoneNumber_formatted = formatPhoneNumber(phoneNumber); //format phoneNumber
		return phoneNumbers.contains(phoneNumber_formatted); //check database for formatted phoneNumber
	}
	
	//check if email is valid
	public static boolean validateEmail(String email) {
		
		//initialize variables
		Pattern pattern = Pattern.compile("[\\w.]+@\\w+\\.(com|net|gov|edu|org|ai|io)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email); //match email entered to regex expression
        
        //if regex expression not found in email string
        if(!matcher.find())
            return false;
        
        return true;
	}

	//convert password using hash algorithm
	
	//convert password using hash algorithm
	public static String convertToHash(String password) {
		
		//declare and initialize variables
		String hash = "\\"; //start hash with two backslashes
        int ascii_int;
        
        //for loop through every character in password
        for(char c : password.toCharArray()) {
        	ascii_int = (int) c;
        	if(ascii_int > 100)
        		ascii_int -= 69;
        	else
        		ascii_int += 26;
        	hash += Character.toString((char) ascii_int);
        }
        
		hash += "\\"; //end hash with two backslashes
		return hash; //return hashed password as string
	}
	
	//(overloaded) update file, specifically for master file with ALL user credentials
	public static void update_file(File userAccounts, ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails){
		try {
			FileWriter userAccounts_writer = new FileWriter(userAccounts); //write to file
			userAccounts_writer.write("Primary Key\tUsername\tHash-Password\tPhone Number\tEmail Address"); //write title line in file
			
			//for loop through every element in database
			for(int i = 0; i < primaryKeys.size(); i++) {
				//write every credential of UserAccount on a single line, with every UserAccount having its own line
				userAccounts_writer.write("\n" + primaryKeys.get(i) + "\t" + usernames.get(i) + "\t" + hashes.get(i) + "\t" + phoneNumbers.get(i) + "\t" + emails.get(i));
			}
			userAccounts_writer.close(); //close FileWriter
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//(overloaded) update file, used for ALL individual credential file
	public static void update_file(File database_file, ArrayList<String> database, String title) {
		try {
			FileWriter database_writer = new FileWriter(database_file); //write to file
			database_writer.write(title + "\t"); //write title line in file
			
			//for loop through every element in database
			for(int i = 0; i < database.size(); i++) {
				//write data from (local memory) database on its own line
				database_writer.write("\n" + database.get(i));
			}
			database_writer.close(); //close FileWriter
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//add new UserAccount to (local memory) databases
	public static void add_user_to_local_database(UserAccount user, ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		primaryKeys.add(user.getPrimaryKey());
		usernames.add(user.getUsername());
		hashes.add(user.getHash());
		phoneNumbers.add(user.getPhoneNumber());
		emails.add(user.getEmail());
	}
	
	//methods used for editing UserAccount variables
}
