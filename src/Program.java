import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

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
		//makes sure username doesn't contain spaces
		if(username.contains(" "))
			return false;
		
		return true;
	}
	
	
}
