
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
	public String toString(){
        return "User account credentials"
        + "\n========================"
        + "\nUsername: " + this.username
        + "\nPhone Number: " + this.phoneNumber
        + "\nEmail: " + this.email;
    }
	
}
