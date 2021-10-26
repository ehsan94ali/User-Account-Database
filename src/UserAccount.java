
public class UserAccount {

	//private variables
	private String primaryKey;
	private String username;
	private String hash_password;
	private String phoneNumber;
	private String email;
		
	//default constructor
	public UserAccount(){
		this.primaryKey = "";
		this.username = "";
		this.hash_password = "";
		this.phoneNumber = "";
		this.email = "";
	}
		
	//overloaded constructor
	public UserAccount(String primaryKey, String username, String hashpassword, String phonenumber, String email) {
		
		this.primaryKey = primaryKey;
		this.username = username;
		this.hash_password = hashpassword;
		this.phoneNumber = phonenumber;
		this.email = email;
	}
	
	//set methods
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setHash(String hashpassword) {
		this.hash_password = hashpassword;
	}
	public void setPhoneNumber(String phonenumber) {
		this.phoneNumber = phonenumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	//get methods
	public String getPrimaryKey() {
		return this.primaryKey;
	}
	public String getUsername() {
		return this.username;
	}
	public String getHash() {
		return this.hash_password;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public String getEmail() {
		return this.email;
	}
	
	//other methods
	public boolean isEmpty() {
		if(this.primaryKey.equals("") && this.username.equals("") && this.hash_password.equals("") && this.phoneNumber.equals("") && this.email.equals(""))
			return true;
		
		return false;
	}
	public void setToDelete() {
		this.setUsername("");
		this.setHash("");
		this.setPhoneNumber("");
		this.setEmail("");
	}
	public boolean toBeDeleted() {
		if(this.username.equals("") && this.hash_password.equals("") && this.phoneNumber.equals("") && this.email.equals(""))
			return true;
		
		return false;
	}
	@Override
	public String toString(){
        return "\n\nUser account credentials"
        + "\n========================"
        + "\nUsername: " + decrypt(this.username, "USERNAME")
        + "\nPhone Number: " + decrypt(this.phoneNumber, "PHONE_NUMBER")
        + "\nEmail: " + decrypt(this.email, "EMAIL");
    }
	public String displayUsername(){
		return (decrypt(this.username, "USERNAME"));
	}
	public String decrypt(String encrypted, String field){

		//declare and initialize variables
		String decrypted = "";
		int ascii_int;
		int indexOfLastAt = -1;

		//handle decryption whether or not encrypted data is an EMAIL, determine index of final @ symbol
		if(!field.equals("EMAIL"))
			encrypted = encrypted.substring(1);
		else
			indexOfLastAt = encrypted.lastIndexOf("@");
		
		char current;
		for(int i = 0; i < encrypted.length()-1; i++){
			
			current = encrypted.charAt(i);
			ascii_int = (int) current;

			switch(field){
				case "USERNAME":
					//for loop through every character in encrypted username
					if(ascii_int <= 93)
						ascii_int += 60;
					else
						ascii_int -= 33;
					break;
				case "PHONE_NUMBER":
					//for loop through every character in encrypted phoneNumber
					ascii_int += 15;
					break;
				case "EMAIL":
					//for loop through every character in encrypted email until @ symbol (final @ symbol if multiple present)
					
					//if @ encountered, end decryption and return with original domain name
					if(ascii_int == 64){
						if(i == indexOfLastAt){
							decrypted += encrypted.substring(i);
							return decrypted;
						}
					}
					else if(ascii_int >= 64)
						ascii_int -= 32;
					else if(ascii_int <= 64 && ascii_int >= 96)
						ascii_int -= 31;
					else
						ascii_int += 63;
					break;
				default:
			}
			decrypted += Character.toString((char) ascii_int);
		}
		return decrypted;
	}
	
}
