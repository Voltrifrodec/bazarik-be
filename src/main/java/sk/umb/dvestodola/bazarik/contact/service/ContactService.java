package sk.umb.dvestodola.bazarik.contact.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;
import sk.umb.dvestodola.bazarik.contact.persistence.repository.ContactRepository;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    
    private final ContactRepository contactRepository;
    
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDetailDataTransferObject> searchContactByPhoneNumber(String phoneNumber) {
        return mapToDtoList(contactRepository.findByPhoneNumber(phoneNumber));
    }
    
    public List<ContactDetailDataTransferObject> searchContactByEmail(String email) {
        return mapToDtoList(contactRepository.findByEmail(email));
    }

    public List<ContactDetailDataTransferObject> getAllContacts() {
        return mapToDtoList(contactRepository.findAll());
    }

    public ContactDetailDataTransferObject getContactById(Long contactId) {
        return mapToDto(getContactEntityById(contactId));
    }

    public ContactEntity getContactEntityById(Long contactId) {
        Optional<ContactEntity> contact = contactRepository.findById(contactId);

        if(contact.isEmpty()) {
            throw new LibraryApplicationException("Contact with ID: " + contactId + " couldn't be found!");
        }

        return contact.get();

    }


    //? CRUD
    @Transactional
    public Long createContact(ContactRequestDataTransferObject contactRequestDataTransferObject) {
        
        ContactEntity contact = mapToEntity(contactRequestDataTransferObject);
        
        return contactRepository.save(contact).getId();

    }

    @Transactional
    public void updateContact(Long contactId, ContactRequestDataTransferObject contactRequestDataTransferObject) {

        ContactEntity contact = getContactEntityById(contactId);
        if(!Strings.isEmpty(contact.getPhoneNumber())) {
            contact.setPhoneNumber(contactRequestDataTransferObject.getPhoneNumber());
        }
        if(!Strings.isEmpty(contact.getEmail())) {
            contact.setEmail(contactRequestDataTransferObject.getEmail());
        }
        
        contactRepository.save(contact);

    }

    @Transactional
    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }


    //? Mapping
    private ContactEntity mapToEntity(ContactRequestDataTransferObject contactRequestDataTransferObject) {

        ContactEntity contact = new ContactEntity();

        contact.setPhoneNumber(contactRequestDataTransferObject.getPhoneNumber());
        contact.setEmail(contactRequestDataTransferObject.getEmail());

        return contact;

    }

    private ContactDetailDataTransferObject mapToDto(ContactEntity contactEntity) {
        
        ContactDetailDataTransferObject contactDto = new ContactDetailDataTransferObject();
        contactDto.setId(contactEntity.getId());
        contactDto.setPhoneNumber(contactEntity.getPhoneNumber());
        contactDto.setEmail(contactEntity.getEmail());
        
        return contactDto;

    }

    //TODO: Redundantná metóda?
    private List<ContactDetailDataTransferObject> mapToDtoList(Iterable<ContactEntity> contactEntities) {
        
        List<ContactDetailDataTransferObject> contacts = new ArrayList<>();
        contactEntities.forEach(contactEntity -> {
            ContactDetailDataTransferObject contactDto = mapToDto(contactEntity);
            contacts.add(contactDto);
        });

        return contacts;
    
    }

}
