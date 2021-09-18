import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;

public class Program {
	
	static Scanner keyboard_input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		boolean database_online = false;
		ArrayList<String> primaryKeys = new ArrayList<>();
		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> hashes = new ArrayList<>();
		ArrayList<String> phoneNumbers = new ArrayList<>();
		ArrayList<String> emails = new ArrayList<>();
		
		File database_folder = new File("Databases");
		File primaryKey_database = new File("Databases/primaryKey_database.xlsx");
		File userAccount_database = new File("Databases/account_database.xlsx");
		File username_database = new File("Databases/username_database.xlsx");
		File hash_database = new File("Databases/hashes_database.xlsx");
		File phoneNumber_database = new File("Databases/phoneNumber_database.xlsx");
		File email_database = new File("Databases/email_database.xlsx");
		
		if(database_folder.exists())
			database_online = true;
		
		create_database_folder(database_folder);
		create_database_file(primaryKey_database);
		create_database_file(userAccount_database);
		create_database_file(username_database);
		create_database_file(hash_database);
		create_database_file(phoneNumber_database);
		create_database_file(email_database);
		
		
		try {
			FileWriter userAccount_writer = new FileWriter(userAccount_database, true);
			FileWriter primaryKey_writer = new FileWriter(primaryKey_database, true);
			FileWriter username_writer = new FileWriter(username_database, true);
			FileWriter hash_writer = new FileWriter(hash_database, true);
			FileWriter phoneNumber_writer = new FileWriter(phoneNumber_database, true);
			FileWriter email_writer = new FileWriter(email_database, true);
			
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
				//read files and fill up local storage databases
				fillArrayList(primaryKey_database, primaryKeys);
				fillArrayList(username_database, usernames);
				fillArrayList(hash_database, hashes);
				fillArrayList(phoneNumber_database, phoneNumbers);
				fillArrayList(email_database, emails);
			}
			
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
		
		////////////////////////////////////////////////////////////////////////
		int user_input;
		
		do{
			user_input = mainMenu();
		
			if(user_input == 1) {
				//create new user account
				createNewUserAccount(primaryKeys, usernames, hashes, phoneNumbers, emails);
				//addAccount_to_files(newAccount, userAccount_database, primaryKey_database, username_database, hash_database, phoneNumber_database, email_database);
				update_file(userAccount_database, primaryKeys, usernames, hashes, phoneNumbers, emails);
				update_file(primaryKey_database, primaryKeys, "Primary Keys");
				update_file(username_database, usernames, "Usernames");
				update_file(hash_database, hashes, "Hashes");
				update_file(phoneNumber_database, phoneNumbers, "Phone Numbers");
				update_file(email_database, emails, "Emails");
				
			}
			else if(user_input == 2) {
				//user sign in
				signin(usernames, hashes, hash_database, phoneNumbers, emails);
				//only update local_database
				
				//only need to update files if changes have been made
				update_file(userAccount_database, primaryKeys, usernames, hashes, phoneNumbers, emails);
				update_file(primaryKey_database, primaryKeys, "Primary Keys");
				update_file(username_database, usernames, "Usernames");
				update_file(hash_database, hashes, "Hashes");
				update_file(phoneNumber_database, phoneNumbers, "Phone Numbers");
				update_file(email_database, emails, "Emails");
			}
			else {
				//quit
				
			}
		}while(user_input != 3);
		
		
		keyboard_input.close();
		System.out.println("program terminated.");
		
	}
	
	public static void create_database_folder(File database_folder) {
		//create database folder if it doesn't already exist
		if(database_folder.mkdir()) {
			try {
				database_folder.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void create_database_file(File database_file) {
		//create database file if it doesn't already exist
		try {
			database_file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void fillArrayList(File database_file, ArrayList<String> database) {
		try {
			Scanner scanner = new Scanner(database_file);
			int firstLine_buffer = 0;
			String data;
			while(scanner.hasNextLine()) {
				if(firstLine_buffer == 0) {
					scanner.nextLine();
					firstLine_buffer++;
				}
				else {
					data = scanner.nextLine();
					database.add(data);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int mainMenu() {
		
		String line;
		char input;
		
		do {
			System.out.println("\nUser Account Database");
			System.out.println("=====================");
			System.out.println("1) Create New Account");
			System.out.println("2) Sign In");
			System.out.println("3) Quit");
			System.out.print("User input: ");
			line = keyboard_input.nextLine();
			input = line.charAt(0);
			
			if(input != '1' && input != '2' && input != '3')
				System.out.println("\nERROR. Invalid user input.");
			
		}while(input != '1' && input != '2' && input != '3');
		
		int input_int = input - '0';
		return input_int;
	}
	
	public static void signin(ArrayList<String> usernames, ArrayList<String> hashes, File hash_file, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		char menu_input;
		String line;
		String username_input, password_input;
		
		do {
			System.out.println("\nSign In");
			System.out.println("==================");
			System.out.println("1) Login");
			System.out.println("2) Forgot Password");
			System.out.println("3) Back");
			System.out.print("User input: ");
			line = keyboard_input.nextLine();
			menu_input = line.charAt(0);
			
			if(menu_input != '1' && menu_input != '2' && menu_input != '3')
				System.out.println("\nERROR. Invalid user input.");
			
		}while(menu_input != '1' && menu_input != '2' && menu_input != '3');
		
		
		int index = -1;
		String phoneNumber_input, email_input, hash;
		
		if(menu_input == '1') {
			//login screen
			System.out.println("\nLogin");
			System.out.println("====================");
			System.out.print("\nUsername: ");
			username_input = keyboard_input.nextLine();
			System.out.print("\nPassword: ");
			password_input = keyboard_input.nextLine();
			
			if(usernames.contains(username_input))
				index = usernames.indexOf(username_input);
			else
				System.out.println("ERROR. Username or password is INCORRECT");
			
			hash = convertToHash(password_input);
			
			if(index >= 0) {
				if(hash.equals(hashes.get(index)))
					System.out.println("\nSuccessful login.");
				else
					System.out.println("ERROR. Username or password is INCORRECT");
			}
			
			
		}
		else if(menu_input == '2') {
			//forgot password
			System.out.println("\nForgot Password");
			System.out.println("==============");
			System.out.print("\nUsername: ");
			username_input = keyboard_input.nextLine();
			
			if(usernames.contains(username_input)){
				
				index = usernames.indexOf(username_input);
				System.out.print("\nPhone Number: ");
				phoneNumber_input = keyboard_input.nextLine();
				if(phoneNumberFound(phoneNumber_input, phoneNumbers)) {
					phoneNumber_input = formatPhoneNumber(phoneNumber_input);
					if(!phoneNumber_input.equals(phoneNumbers.get(index))) {
						System.out.println("ERROR. Phone number '" + phoneNumber_input + "' is NOT linked to this account (" + username_input + ").");
						return;
					}
						
				}
				else {
					System.out.println("ERROR. Phone number '" + phoneNumber_input + "' does NOT exist in our database");
					return;
				}
				
				System.out.print("\nEmail: ");
				email_input = keyboard_input.nextLine();
				if(emails.contains(email_input)) {
					if(!email_input.equals(emails.get(index))) {
						System.out.println("ERROR. Email '" + email_input + "' is NOT linked to this account (" + username_input + ").");
						return;
					}
						
				}
				else {
					System.out.println("ERROR. Email '" + email_input + "' does NOT exist in our database");
					return;
				}
			}
			else {
				System.out.println("ERROR. Username '" + username_input + "' does NOT exist in our database.");
				return;
			}
			
			//change password and update database
			String newPassword_input;
			String newPassword_reenter = null;
			String newHash;
			System.out.println("\nChange Password");
			System.out.println("===============");
			
			do {
				System.out.print("\nNew Password: ");
				newPassword_input = keyboard_input.nextLine();
				
				if(!validatePassword(newPassword_input)) {
					System.out.println("ERROR. Invalid password. Password must contain:" 
							+ "\n-AT LEAST 8 CHARACTERS"
							+ "\n-a SPECIAL CHARACTER" 
							+ "\n-a NUMBER" 
							+ "\n-a LOWERCASE letter" 
							+ "\n-AND a CAPITAL letter");	
				}
				else {
					System.out.print("\nRe-enter New Password: ");
					newPassword_reenter = keyboard_input.nextLine();
					if(newPassword_reenter.equals(newPassword_input)) {
						hash = newPassword_input;
						hashes.set(index, hash);
						System.out.println("Password accepted.");
					}
					else
						System.out.println("ERROR. Passwords must match.");
						
				}
				
			}while(!validatePassword(newPassword_input) || !newPassword_reenter.equals(newPassword_input));
			
			newHash = convertToHash(newPassword_input);
			hashes.set(index, newHash);	
		}
		
	}
	
	public static void createNewUserAccount(ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		UserAccount newUser = new UserAccount();
		String input;
		
		System.out.println("\nCreate New Account");
		System.out.println("==================");
		//username
		do {
			System.out.print("Username: ");
			input = keyboard_input.nextLine();
			
			if(!validateUsername(input))
				System.out.println("ERROR. Username '" + input + "' is INVALID. Username CANNOT contain spaces");
			else if(usernames.contains(input))
				System.out.println("ERROR. Username '" + input + "' already exists.");
			else
				System.out.println("Username '" + input + "' is VALID and available.");
		}while(usernames.contains(input) || !validateUsername(input));
		newUser.setUsername(input);
		
		//password
		String password_input;
		boolean password_match = false;
		do {
			System.out.print("\nPassword: ");
			input = keyboard_input.nextLine();
			
			if(validatePassword(input)) {
				password_input = input;
				System.out.print("\nRe-enter password: ");
				input = keyboard_input.nextLine();
				if(input.equals(password_input)) {
					password_match = true;
					System.out.println("Password accepted.");
				}
				else
					System.out.println("ERROR. Passwords do NOT match.");
			}
			else {
				System.out.println("ERROR. Invalid password. Password must contain:" 
						+ "\n-AT LEAST 8 CHARACTERS"
						+ "\n-a SPECIAL CHARACTER" 
						+ "\n-a NUMBER" 
						+ "\n-a LOWERCASE letter" 
						+ "\n-AND a CAPITAL letter");
			}
			
		}while(!validatePassword(input) || !password_match);
		
		
		//we can keep this line
		newUser.setHash(convertToHash(input));
		
		//phone number
		do {
			System.out.print("\nPhone Number: ");
			input = keyboard_input.nextLine();
			
			if(!validatePhoneNumber(input))
				System.out.println("ERROR. Phone Number '" + input + "' is invalid. Phone number must only contain 10 digits");
			else if(phoneNumberFound(input, phoneNumbers))
				System.out.println("ERROR. Phone Number '" + input + "' is already registered with another account.");
			else
				System.out.println("Phone Number '" + input + "' is now SUCCESSFULLY linked to your account.");
			
		}while(phoneNumberFound(input, phoneNumbers) || !validatePhoneNumber(input));
		input = formatPhoneNumber(input);
		newUser.setPhoneNumber(input);
		
		//email address
		do {
			System.out.print("\nEmail Address: ");
			input = keyboard_input.nextLine();
			
			if(!validateEmail(input))
				System.out.println("ERROR. Email Address '" + input + "' is an INVALID email address.");
			else if(emails.contains(input))
				System.out.println("ERROR. Email Address '" + input + "' is already registered with another account.");
			else
				System.out.println("Email Address '" + input + "' is now SUCCESSFULLY linked to your account.");
		}while(emails.contains(input) || !validateEmail(input));
		newUser.setEmail(input);
		
		//primary key
		input = generate_primaryKey(primaryKeys);
		newUser.setPrimaryKey(input);
		
		
		add_user_to_local_database(newUser, primaryKeys, usernames, hashes, phoneNumbers, emails);
		System.out.println("Account SUCCESSFULLY created.");
		
	}
	
	public static String generate_primaryKey(ArrayList<String> primaryKeys) {
		
		String primaryKey;
        int pk_int;
        boolean new_pk = false;
        
        do {
        primaryKey = "";
	        for(int i = 0; i < 12; i++){
	            do{
	                pk_int = (int) ((Math.random() * (43)) + 48);
	            }while(pk_int > 57 && pk_int < 65);
	            if(i == 4 || i == 8)
	                primaryKey += "-";
	            primaryKey += Character.toString((char)pk_int);
	        }
	        if(!primaryKeys.contains(primaryKey))
	        	new_pk = true;
        } while(!new_pk);
        
		return primaryKey;
	}
	
	public static boolean validateUsername(String username) {
		//valid username CANNOT contain any spaces
		if(username.contains(" "))
			return false;
		
		return true;
	}

	public static boolean validatePassword(String password) {
		/*valid password requires:
		-minimum of 8 characters
		-at least one special character
		-at least one capital letter
		-at least one lower-case letter
		-at least one number*/
		
		if(password.length() < 8)
			return false;
		
		boolean containsSpecial=false, containsCapital=false, containsLower=false, containsNumber=false;
		for (char c : password.toCharArray()) {
			
			if (containsSpecial && containsCapital && containsLower && containsNumber)
				return true;
			else {
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

		return (containsSpecial && containsCapital && containsLower && containsNumber);
	}
	
	public static boolean validatePhoneNumber(String phoneNumber) {
		//valid phone number contains 10 digits
		if(phoneNumber.length() > 14)
			return false;
		
		int digitCounter = 0;
		for(char c : phoneNumber.toCharArray()) {
			if(Character.isLetter(c))
				return false;
			else if(Character.isDigit(c)) {
				digitCounter++;
				if(digitCounter > 10)
					return false;
			}
		}
		if(digitCounter != 10)
			return false;
		
		return true;
	}
	
	public static String formatPhoneNumber(String phoneNumber) {
		String formatted = "";
        for(int i = 0; i < phoneNumber.length(); i++){
            if(Character.isDigit(phoneNumber.charAt(i)))
                formatted += phoneNumber.charAt(i);
        }
        return formatted;
	}

	public static boolean phoneNumberFound(String phoneNumber, ArrayList<String> phoneNumbers) {
		//make sure phone number (already formatted) doesn't already exist in the database
		String phoneNumber_formatted = formatPhoneNumber(phoneNumber);
		return phoneNumbers.contains(phoneNumber_formatted);
	}
	
	public static boolean validateEmail(String email) {
		//validate email format
		Pattern pattern = Pattern.compile("[\\w.]+@\\w+\\.(com|net|gov|edu|org|ai|io)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.find())
            return false;
        
        return true;
	}

	public static String convertToHash(String password) {
		//convert string using hash algorithm
		String hash = "\\";
        int ascii_int;
		for(int i = 0; i < password.length(); i++) {
		    ascii_int = (int) password.charAt(i);
		    if(ascii_int > 100)
		    	ascii_int -= 69;
		    else
		    	ascii_int += 26;
		    hash += Character.toString((char)ascii_int);
		}
		hash += "\\";
		return hash;
	}
	
	public static void update_file(File userAccounts, ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails){
		try {
			FileWriter userAccounts_writer = new FileWriter(userAccounts);
			userAccounts_writer.write("Primary Key\tUsername\tHash-Password\tPhone Number\tEmail Address");
			for(int i = 0; i < usernames.size(); i++) {
				userAccounts_writer.write("\n" + primaryKeys.get(i) + "\t" + usernames.get(i) + "\t" + hashes.get(i) + "\t" + phoneNumbers.get(i) + "\t" + emails.get(i));
			}
			userAccounts_writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void update_file(File database_file, ArrayList<String> database, String title) {
		try {
			FileWriter database_writer = new FileWriter(database_file);
			database_writer.write(title + "\t");
			for(int i = 0; i < database.size(); i++) {
				database_writer.write("\n" + database.get(i));
			}
			database_writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void add_user_to_local_database(UserAccount user, ArrayList<String> primaryKeys, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		//add UserAccount object to database
		primaryKeys.add(user.getPrimaryKey());
		usernames.add(user.getUsername());
		hashes.add(user.getHash());
		phoneNumbers.add(user.getPhoneNumber());
		emails.add(user.getEmail());
	}
	
}
