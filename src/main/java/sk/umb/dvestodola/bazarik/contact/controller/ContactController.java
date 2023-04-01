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
    public List<ContactDetailDto> searchContacts(@RequestParam(required = false) String email) {
        System.out.println("Search customer (was) called, he wants his money back.");
        return Strings.isEmpty(email) ?
			contactService.getAllContacts() :
			contactService.searchContactsByEmail(email);
    }

    @GetMapping("/api/contacts/{contactId}")
    public ContactDetailDto getContactById(@PathVariable Long contactId) {
        System.out.println("Get contact by id was called, " + contactId);
        return contactService.getContactById(contactId);
    }

    @PostMapping("/api/contacts")
    public Long createContact(@Valid @RequestBody ContactRequestDto contactRequest) {
        System.out.println("Create contact was called.");
        return contactService.createContact(contactRequest);
    }

    @PutMapping("/api/contacts/{contactId}")
    public void updateContact(@PathVariable Long contactId, @Valid @RequestBody ContactRequestDto contactRequest) {
        System.out.println("Update contact was called, " + contactId);
        contactService.updateContact(contactId, contactRequest);
    }

    @DeleteMapping("/api/contacts/{contactId}")
    public void deleteContact(@PathVariable Long contactId) {
        System.out.println("Delete contact was called, " + contactId);
        contactService.deleteContact(contactId);
    }
}
