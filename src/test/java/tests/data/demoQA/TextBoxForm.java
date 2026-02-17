package tests.data.demoQA;

public class TextBoxForm {
	
	private String fullName;
	private String email;
	private String currentAddress;
	private String permanentAddress;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	
	@Override
	public String toString() {
		return "Name:" + this.fullName
				+ "\n"
				+ "Email:" + this.email
				+ "\n"
				+ "Current Address :"+ this.currentAddress
				+ "\n"
				+ "Permananet Address :" + this.permanentAddress;
		
	}
	

}
