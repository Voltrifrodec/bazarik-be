package sk.umb.dvestodola.bazarik.contact.service;

public class ContactRequestDataTransferObject {
    
    private String phoneNumber;
    private String email;
    

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
