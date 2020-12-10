import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;

public class Program {

	public static void main(String[] args) {
		
		boolean database_online = false;
		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> hashes = new ArrayList<>();
		ArrayList<String> phoneNumbers = new ArrayList<>();
		ArrayList<String> emails = new ArrayList<>();
		
		File database_folder = new File("Databases");
		File userAccount_database = new File("Databases/account_database.xlsx");
		File username_database = new File("Databases/username_database.xlsx");
		File hash_database = new File("Databases/hashes_database.xlsx");
		File phoneNumber_database = new File("Databases/phoneNumber_database.xlsx");
		File email_database = new File("Databases/email_database.xlsx");
		
		if(database_folder.exists())
			database_online = true;
		
		create_database_folder(database_folder);
		create_database_file(userAccount_database);
		create_database_file(username_database);
		create_database_file(hash_database);
		create_database_file(phoneNumber_database);
		create_database_file(email_database);
		
		try {
			FileWriter userAccount_writer = new FileWriter(userAccount_database);
			FileWriter username_writer = new FileWriter(username_database);
			FileWriter hash_writer = new FileWriter(hash_database);
			FileWriter phoneNumber_writer = new FileWriter(phoneNumber_database);
			FileWriter email_writer = new FileWriter(email_database);
			
			if(!database_online) {
				userAccount_writer.write("Username\tHash-Password\tPhone Number\tEmail Address");
				database_online = true;
			}
			else {
				//read files and fill up local storage databases
				fillArrayList(username_database, usernames);
				fillArrayList(hash_database, hashes);
				fillArrayList(phoneNumber_database, phoneNumbers);
				fillArrayList(email_database, emails);
			}
			
			userAccount_writer.close();
			username_writer.close();
			hash_writer.close();
			phoneNumber_writer.close();
			email_writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch  block
			e.printStackTrace();
		}
		
		int user_input;
		
		do{
			user_input = mainMenu();
		
			if(user_input == 1) {
				//create new user account
				UserAccount newAccount = createNewUserAccount(usernames, hashes, phoneNumbers, emails);
				
				
			}
			else if(user_input == 2) {
				//user sign in
				signin(usernames, hashes, phoneNumbers, emails);
			}
			else {
				//quit
				
			}
		}while(user_input != 3);
		
			
		//UPDATE FILES USING LOCAL DATABASES BEFORE TERMINATING PROGRAM!!
		
		
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
			String data;
			while(scanner.hasNextLine()) {
				data = scanner.nextLine();
				database.add(data);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int mainMenu() {
		
		char input;
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.println("\nUser Account Database");
			System.out.println("=====================");
			System.out.println("1) Create New Account");
			System.out.println("2) Sign In");
			System.out.println("3) Quit");
			System.out.print("User input: ");
			input = scanner.next().charAt(0);
			
			if(input != '1' && input != '2' && input != '3')
				System.out.println("\nERROR. Invalid user input.");
			
		}while(input != '1' && input != '2' && input != '3');
		
		scanner.close();
		int input_int = input - '0';
		return input_int;
	}
	
	public static void signin(ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		char menu_input;
		String username_input, password_input;
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.println("\nSign In");
			System.out.println("==================");
			System.out.println("1) Login");
			System.out.println("2) Forgot Password");
			System.out.println("3) Back");
			System.out.print("User input: ");
			menu_input = scanner.next().charAt(0);
			
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
			username_input = scanner.nextLine();
			System.out.print("\nPassword: ");
			password_input = scanner.nextLine();
			
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
			username_input = scanner.nextLine();
			
			if(usernames.contains(username_input)){
				
				index = usernames.indexOf(username_input);
				System.out.print("\nPhone Number: ");
				phoneNumber_input = scanner.nextLine();
				if(phoneNumberFound(phoneNumber_input, phoneNumbers)) {
					phoneNumber_input = formatPhoneNumber(phoneNumber_input);
					if(!phoneNumber_input.equals(phoneNumbers.get(index))) {
						System.out.println("ERROR. Phone number '" + phoneNumber_input + "' is NOT linked to this account (" + username_input + ").");
						scanner.close();
						return;
					}
						
				}
				else {
					System.out.println("ERROR. Phone number '" + phoneNumber_input + "' does NOT exist in our database");
					scanner.close();
					return;
				}
				
				System.out.print("\nEmail: ");
				email_input = scanner.nextLine();
				if(emails.contains(email_input)) {
					if(!email_input.equals(emails.get(index))) {
						System.out.println("ERROR. Email '" + email_input + "' is NOT linked to this account (" + username_input + ").");
						scanner.close();
						return;
					}
						
				}
				else {
					System.out.println("ERROR. Email '" + email_input + "' does NOT exist in our database");
					scanner.close();
					return;
				}
			}
			else {
				System.out.println("ERROR. Username '" + username_input + "' does NOT exist in our database.");
				scanner.close();
				return;
			}
			
			//change password and update database
			String newPassword_input;
			System.out.println("\nChange Password");
			System.out.println("===============");
			
			do {
				System.out.print("\nNew Password: ");
				newPassword_input = scanner.nextLine();
				
				if(!validatePassword(newPassword_input)) {
					System.out.println("ERROR. Invalid password. Password must contain:" 
							+ "\n-AT LEAST 8 CHARACTERS"
							+ "\n-a SPECIAL CHARACTER" 
							+ "\n-a NUMBER" 
							+ "\n-a LOWERCASE letter" 
							+ "\n-AND a CAPITAL letter");	
				}
				else {
					hash = newPassword_input;
					hashes.set(index, hash);
					System.out.println("Password accepted.");
				}
				//add *verify password* option by entering twice
				
			}while(!validatePassword(newPassword_input));
		}
		
		scanner.close();
	}
	
	public static UserAccount createNewUserAccount(ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		UserAccount newUser = new UserAccount();
		Scanner scanner = new Scanner(System.in);
		String input;
		
		System.out.println("\nCreate New Account");
		System.out.println("==================");
		
		//username
		do {
			System.out.print("\nUsername: ");
			input = scanner.nextLine();
			
			if(!validateUsername(input))
				System.out.println("ERROR. Username '" + input + "' is INVALID. Username CANNOT contain spaces");
			else if(usernames.contains(input))
				System.out.println("ERROR. Username '" + input + "' already exists.");
			else
				System.out.println("Username '" + input + "' is VALID and available.");
		}while(usernames.contains(input) || !validateUsername(input));
		newUser.setUsername(input);
		
		//password
		do {
			System.out.print("\nPassword: ");
			input = scanner.nextLine();
			
			if(!validatePassword(input)) {
				System.out.println("ERROR. Invalid password. Password must contain:" 
						+ "\n-AT LEAST 8 CHARACTERS"
						+ "\n-a SPECIAL CHARACTER" 
						+ "\n-a NUMBER" 
						+ "\n-a LOWERCASE letter" 
						+ "\n-AND a CAPITAL letter");	
			}
			else
				System.out.println("Password accepted.");
			
			//add *verify password* option by entering twice
			
		}while(!validatePassword(input));
		newUser.setHash(convertToHash(input));
		
		//phone number
		do {
			System.out.print("\nPhone Number: ");
			input = scanner.nextLine();
			
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
			input = scanner.nextLine();
			
			if(!validateEmail(input))
				System.out.println("ERROR. Email Address '" + input + "' is an INVALID email address.");
			else if(emails.contains(input))
				System.out.println("ERROR. Email Address '" + input + "' is already registered with another account.");
			else
				System.out.println("Email Address '" + input + "' is now SUCCESSFULLY linked to your account.");
		}while(emails.contains(input) || !validateEmail(input));
		newUser.setEmail(input);
		
		update_database(newUser, usernames, hashes, phoneNumbers, emails);
		System.out.println("Account SUCCESSFULLY created.");
		
		scanner.close();
		return newUser;
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
		String hash = "";
        int ascii_int;
		for(int i = 0; i < password.length(); i++) {
		    ascii_int = (int) password.charAt(i);
		    ascii_int += 26;
		    hash += Character.toString((char)ascii_int);
		}
		return hash;
	}
	
	public static void update_database(UserAccount user, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		//add UserAccount object to database
		usernames.add(user.getUsername());
		hashes.add(user.getHash());
		phoneNumbers.add(user.getPhoneNumber());
		emails.add(user.getEmail());
	}
	
}
