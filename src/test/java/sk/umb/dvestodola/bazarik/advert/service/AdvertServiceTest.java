package sk.umb.dvestodola.bazarik.advert.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertServiceTest {
	@InjectMocks
	private AdvertService advertService;

	@Mock
	private AdvertRepository advertRepository;

	@Test
	public void getNumberOfAdvertsInCategoryByCategoryIdOk() {
		AdvertEntity advertEntity = new AdvertEntity();
		advertEntity.setId(UUID.randomUUID());

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(1L);

		advertEntity.setCategory(categoryEntity);

		when(advertRepository.getNumberOfAdvertsInCategoryByCategoryId(categoryEntity.getId()))
			.thenReturn(1L);

		Long numberOfAdvertsInCategoryByCategoryId = advertService.getNumberOfAdvertsInCategoryByCategoryId(categoryEntity.getId());

		assertEquals(numberOfAdvertsInCategoryByCategoryId, 1L);
	}

	@Test
	public void getNumberOfAdvertsInCategoryByCategoryIdNotOk() {
		AdvertEntity advertEntity = new AdvertEntity();
		advertEntity.setId(UUID.randomUUID());

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(1L);

		advertEntity.setCategory(categoryEntity);

		when(advertRepository.getNumberOfAdvertsInCategoryByCategoryId(categoryEntity.getId()))
			.thenReturn(1L);

		Long numberOfAdvertsInCategoryByCategoryId = advertService.getNumberOfAdvertsInCategoryByCategoryId(categoryEntity.getId());

		assertNotEquals(numberOfAdvertsInCategoryByCategoryId, 0L);
	}
}
