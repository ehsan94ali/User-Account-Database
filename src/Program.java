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
	
	
	
	public static boolean verifyUsername(String username) {
		//valid username CANNOT contain any spaces
		if(username.contains(" "))
			return false;
		
		return true;
	}
	
	public static boolean verifyPassword(String password) {
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
	
	public static boolean verifyPhoneNumber(String phoneNumber) {
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
	
	public static boolean verifyEmail(String email) {
		Pattern pattern = Pattern.compile("[\\w.]+@\\w+\\.(com|net|gov|edu|org|ai|io)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.find())
            return false;
        
        return true;
	}
	
	
	
	
}
