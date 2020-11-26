import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	public static void main(String[] args) {
		
		
	}
	
	public static int mainMenu() {
		
		char userInput;
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.println("\nUser Account Database");
			System.out.println("=====================");
			System.out.println("1) Create New Account");
			System.out.println("2) Sign In");
			System.out.print("User input: ");
			userInput = scanner.next().charAt(0);
			
			if(userInput != '1' && userInput != '2')
				System.out.println("\nERROR. Invalid user input.");
			
		}while(userInput != '1' && userInput != '2');
		
		scanner.close();
		int userInput_int = userInput - '0';
		return userInput_int;
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
