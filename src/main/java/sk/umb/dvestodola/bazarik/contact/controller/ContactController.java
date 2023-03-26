package sk.umb.dvestodola.bazarik.contact.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.contact.service.ContactDetailDataTransferObject;
import sk.umb.dvestodola.bazarik.contact.service.ContactRequestDataTransferObject;
import sk.umb.dvestodola.bazarik.contact.service.ContactService;

@RestController
public class ContactController {
    
    private final ContactService contactService;
    
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    } 

    //TODO: searchContacts je redundantná ... len bola pridaná pri riešení GSS-API problému
    @GetMapping("/api/contacts")
    public List<ContactDetailDataTransferObject> searchContacts(@RequestParam(required = false) String phoneNumber) {
        System.out.println("Search customer called twice.");

        return Strings.isEmpty(phoneNumber) ? contactService.getAllContacts() : contactService.searchContactByPhoneNumber(phoneNumber);
    }

    @GetMapping("/api/contacts/{contactId}")
    public ContactDetailDataTransferObject getContact(@PathVariable Long contactId) {
        
        System.out.println("Searching for contact with ID: " + contactId);
        return contactService.getContactById(contactId);

    }

    @PostMapping("/api/contacts")
    public Long createContact(@Valid @RequestBody ContactRequestDataTransferObject contactRequestDataTransferObject) {
        
        System.out.println("Creating contact...");
        return contactService.createContact(contactRequestDataTransferObject);

    }

    @PutMapping("/api/contacts/{contactId}")
    public void updateContact(@PathVariable Long contactId, @Valid @RequestBody ContactRequestDataTransferObject contactRequestDataTransferObject) {

        System.out.println("Updating contact with ID: " + contactId);
        contactService.updateContact(contactId, contactRequestDataTransferObject);

    }

    @DeleteMapping("/api/contacts/{contactId}")
    public void deleteContact(@PathVariable Long contactId) {

        System.out.println("Deleting contact with ID: " + contactId);
        contactService.deleteContact(contactId);

    }

}
