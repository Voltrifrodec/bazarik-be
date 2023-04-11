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

	public List<ContactDetailDto> getAllContacts() {
		return mapToContactDetailList(contactRepository.findAll());
	}

	public List<ContactDetailDto> searchContactByPhoneNumber(String phoneNumber) {
		return mapToContactDetailList(contactRepository.findAllByPhoneNumber(phoneNumber));
	}
	
	public List<ContactDetailDto> searchContactsByEmail(String email) {
		return mapToContactDetailList(contactRepository.findAllByEmail(email));
	}

	public ContactDetailDto getContactById(Long contactId) {
		return mapToContactDetail(getContactEntityById(contactId));
	}

	@Transactional
	public Long createContact(ContactRequestDto contactRequest) {
		ContactEntity contactEntity = mapToContactEntity(contactRequest);
		
		return contactRepository.save(contactEntity).getId();
	}

	@Transactional
	public void updateContact(Long contactId, ContactRequestDto contactRequest) {
		ContactEntity contactEntity = getContactEntityById(contactId);

		if(! Strings.isEmpty(contactEntity.getEmail())) {
			contactEntity.setEmail(contactRequest.getEmail());
		}

		if(! Strings.isEmpty(contactEntity.getPhoneNumber())) {
			contactEntity.setEmail(contactRequest.getPhoneNumber());
		}
		
		contactRepository.save(contactEntity);
	}

	@Transactional
	public void deleteContact(Long contactId) {
		contactRepository.deleteById(contactId);
	}

	public ContactEntity getContactEntityById(Long contactId) {
		Optional<ContactEntity> contactEntity = contactRepository.findById(contactId);

		if(contactEntity.isEmpty()) {
			throw new BazarikApplicationException("Contact must have a valid id.");
		}

		return contactEntity.get();
	}

	private ContactEntity mapToContactEntity(ContactRequestDto contactRequest) {
		ContactEntity contactEntity = new ContactEntity();

		contactEntity.setEmail(contactRequest.getEmail());
		contactEntity.setPhoneNumber(contactRequest.getPhoneNumber());

		return contactEntity;
	}

	private List<ContactDetailDto> mapToContactDetailList(Iterable<ContactEntity> contactEntities) {
		List<ContactDetailDto> contactDetailList = new ArrayList<>();
		
		contactEntities.forEach(contactEntity -> {
			ContactDetailDto contactDetail = mapToContactDetail(contactEntity);
			contactDetailList.add(contactDetail);
		});

		return contactDetailList;
	}

	private ContactDetailDto mapToContactDetail(ContactEntity contactEntity) {
		ContactDetailDto contactDetail = new ContactDetailDto();

		contactDetail.setId(contactEntity.getId());
		contactDetail.setEmail(contactEntity.getEmail());
		contactDetail.setPhoneNumber(contactEntity.getPhoneNumber());
		
		return contactDetail;
	}
}
