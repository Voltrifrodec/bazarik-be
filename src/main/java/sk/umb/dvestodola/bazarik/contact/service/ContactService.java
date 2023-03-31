package sk.umb.dvestodola.bazarik.contact.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;
import sk.umb.dvestodola.bazarik.contact.persistence.repository.ContactRepository;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
	
	private final ContactRepository contactRepository;
	
	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	public List<ContactDetailDto> searchContactByPhoneNumber(String phoneNumber) {
		return mapToConctactDetailList(contactRepository.findAllByPhoneNumber(phoneNumber));
	}
	
	public List<ContactDetailDto> searchContactByEmail(String email) {
		return mapToConctactDetailList(contactRepository.findAllByEmail(email));
	}

	public List<ContactDetailDto> getAllContacts() {
		return mapToConctactDetailList(contactRepository.findAll());
	}

	public ContactDetailDto getContactById(Long contactId) {
		return mapToContactDetail(getContactEntityById(contactId));
	}

	public ContactEntity getContactEntityById(Long contactId) {
		Optional<ContactEntity> contact = contactRepository.findById(contactId);

		if(contact.isEmpty()) {
			throw new BazarikApplicationException("Contact not found, id: " + contactId);
		}

		return contact.get();
	}

	@Transactional
	public Long createContact(ContactRequestDto contactRequestDataTransferObject) {
		ContactEntity contact = mapToContactEntity(contactRequestDataTransferObject);
		
		return contactRepository.save(contact).getId();
	}

	@Transactional
	public void updateContact(Long contactId, ContactRequestDto contactRequestDataTransferObject) {
		ContactEntity contact = getContactEntityById(contactId);

		/* if(!Strings.isEmpty(contact.getPhoneNumber())) {
			contact.setPhoneNumber(contactRequestDataTransferObject.getPhoneNumber());
		} */

		if(!Strings.isEmpty(contact.getEmail())) {
			contact.setEmail(contactRequestDataTransferObject.getEmail());
		}
		
		contactRepository.save(contact);
	}

	@Transactional
	public void deleteContact(Long contactId) {
		contactRepository.deleteById(contactId);
	}

	private ContactEntity mapToContactEntity(ContactRequestDto contactRequestDataTransferObject) {
		ContactEntity contact = new ContactEntity();

		// contact.setPhoneNumber(contactRequestDataTransferObject.getPhoneNumber());
		contact.setEmail(contactRequestDataTransferObject.getEmail());

		return contact;
	}

	private ContactDetailDto mapToContactDetail(ContactEntity contactEntity) {
		ContactDetailDto contactDto = new ContactDetailDto();
		contactDto.setId(contactEntity.getId());
		// contactDto.setPhoneNumber(contactEntity.getPhoneNumber());
		contactDto.setEmail(contactEntity.getEmail());
		
		return contactDto;
	}

	private List<ContactDetailDto> mapToConctactDetailList(Iterable<ContactEntity> contactEntities) {
		List<ContactDetailDto> contacts = new ArrayList<>();
		
		contactEntities.forEach(contactEntity -> {
			ContactDetailDto contactDto = mapToContactDetail(contactEntity);
			contacts.add(contactDto);
		});

		return contacts;
	}
}
