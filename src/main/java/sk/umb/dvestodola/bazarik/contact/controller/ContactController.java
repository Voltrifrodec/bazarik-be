package sk.umb.dvestodola.bazarik.contact.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.contact.service.ContactDetailDto;
import sk.umb.dvestodola.bazarik.contact.service.ContactRequestDto;
import sk.umb.dvestodola.bazarik.contact.service.ContactService;

@RestController
public class ContactController {
    
    private final ContactService contactService;
    
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    } 

    @GetMapping("/api/contacts")
    public List<ContactDetailDto> searchContacts(@RequestParam(required = false) String phoneNumber) {
        System.out.println("Search customer called twice.");
        return Strings.isEmpty(phoneNumber) ?
			contactService.getAllContacts() :
			contactService.searchContactByPhoneNumber(phoneNumber);
    }

    @GetMapping("/api/contacts/{contactId}")
    public ContactDetailDto getContact(@PathVariable Long contactId) {
        System.out.println("Get contact by id: " + contactId);
        return contactService.getContactById(contactId);
    }

    @PostMapping("/api/contacts")
    public Long createContact(@Valid @RequestBody ContactRequestDto contactRequestDataTransferObject) {
        System.out.println("Create contact was called.");
        return contactService.createContact(contactRequestDataTransferObject);
    }

    @PutMapping("/api/contacts/{contactId}")
    public void updateContact(@PathVariable Long contactId, @Valid @RequestBody ContactRequestDto contactRequestDataTransferObject) {
        System.out.println("Updating contact, id: " + contactId);
        contactService.updateContact(contactId, contactRequestDataTransferObject);
    }

    @DeleteMapping("/api/contacts/{contactId}")
    public void deleteContact(@PathVariable Long contactId) {
        System.out.println("Deleting contact, id: " + contactId);
        contactService.deleteContact(contactId);
    }
}
