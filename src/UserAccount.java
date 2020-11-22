
public class UserAccount {

	private String username;
	private String hash_password;
	private String phoneNumber;
	private String email;
		
	public UserAccount(){
		this.username = "";
		this.hash_password = "";
		this.phoneNumber = "";
		this.email = "";
	}
		
	public UserAccount(String username, String hashpassword, String phonenumber, String email) {
		
		this.username = username;
		this.hash_password = hashpassword;
		this.phoneNumber = phonenumber;
		this.email = email;
	}
	
	void setUsername(String username) {
		this.username = username;
	}
	void setHash(String hashpassword) {
		this.hash_password = hashpassword;
	}
	void setPhoneNumber(String phonenumber) {
		this.phoneNumber = phonenumber;
	}
	void setEmail(String email) {
		this.email = email;
	}
	
	String getUsername() {
		return this.username;
	}
	String getHash() {
		return this.hash_password;
	}
	String getPhoneNumber() {
		return this.phoneNumber;
	}
	String getEmail() {
		return this.email;
	}
	
	
}
