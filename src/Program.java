import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	public static void main(String[] args) {
		
		
	}
	
	public static int mainMenu() {
		
		char input;
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.println("\nUser Account Database");
			System.out.println("=====================");
			System.out.println("1) Create New Account");
			System.out.println("2) Sign In");
			System.out.print("User input: ");
			input = scanner.next().charAt(0);
			
			if(input != '1' && input != '2')
				System.out.println("\nERROR. Invalid user input.");
			
		}while(input != '1' && input != '2');
		
		scanner.close();
		int input_int = input - '0';
		return input_int;
	}
	
	public static UserAccount createNewUserAccount(ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		
		UserAccount newUser = new UserAccount();
		Scanner scanner = new Scanner(System.in);
		String input;
		
		System.out.println("Create New Account");
		System.out.println("==================");
		
		//username
		do {
			System.out.print("\nUsername: ");
			input = scanner.nextLine();
			
			if(!validateUsername(input))
				System.out.println("ERROR. Username '" + input + "' is INVALID. Username CANNOT contain spaces");
			else if(usernameFound(input, usernames))
				System.out.println("ERROR. Username '" + input + "' already exists.");
			else
				System.out.println("Username '" + input + "' is VALID and available.");
		}while(usernameFound(input, usernames) || !validateUsername(input));
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
			else if(emailFound(input, emails))
				System.out.println("ERROR. Email Address '" + input + "' is already registered with another account.");
			else
				System.out.println("Email Address '" + input + "' is now SUCCESSFULLY linked to your account.");
		}while(emailFound(input, emails) || !validateEmail(input));
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
	
	public static boolean usernameFound(String username, ArrayList<String> usernames) {
		//make sure username doesn't already exist in the database
		return usernames.contains(username);
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
	
	public static boolean emailFound(String email, ArrayList<String> emails) {
		//make sure email doesn't already exist in database
		return emails.contains(email);
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
	
	public static String reverseHash(String hash) {
		//reverse hash code to original password for validation using reverse hash algorithm
		String password = "";
        int ascii_int = 0;
        for(int i = 0; i < hash.length(); i++) {
		    ascii_int = (int) hash.charAt(i);
		    ascii_int -= 26;
		    password += Character.toString((char)ascii_int);
		}
        return password;
	}
	
	public static void update_database(UserAccount user, ArrayList<String> usernames, ArrayList<String> hashes, ArrayList<String> phoneNumbers, ArrayList<String> emails) {
		//add UserAccount object to database
		usernames.add(user.getUsername());
		hashes.add(user.getHash());
		phoneNumbers.add(user.getPhoneNumber());
		emails.add(user.getEmail());
	}
	
	
	
	
	
}
