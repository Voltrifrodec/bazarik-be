package sk.umb.dvestodola.bazarik.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {
	@InjectMocks
	SecurityService securityService;

	@Test
	public void hashFunctionOk() {
		String stringToHash = "86";

		String hashedString = this.securityService.hashFunction(stringToHash);

		assertEquals("e8f7ed5d43510cf0e9d193bb29ab07a2990744fe248abe28f8cfaf5d8ee879ff", hashedString);
	}

	@Test
	public void hashFunctionNotOk() {
		String stringToHash = "86";

		String hashedString = this.securityService.hashFunction(stringToHash);

		assertNotEquals("f103cf1a38c628a421f9ae232d5ea7efd9193d7fce638c3d876e2e29cafa7d43", hashedString);
	}
}
