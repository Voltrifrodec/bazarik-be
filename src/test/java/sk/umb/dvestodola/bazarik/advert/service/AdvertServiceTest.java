package sk.umb.dvestodola.bazarik.advert.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.repository.SubcategoryRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdvertServiceTest {
    @InjectMocks
    private AdvertService advertService;

	@Mock
	private AdvertRepository advertRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Test
    public void getAllCustomersOk() {
        AdvertEntity advertEntity = new AdvertEntity();
        advertEntity.setId(UUID.randomUUID());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Kategoria 1");

        advertEntity.setCategory(categoryEntity);

        when(advertRepository.findAll())
                .thenReturn(List.of(advertEntity));

        List<AdvertDetailDto> adverts = advertService.getAllAdverts();

        verify(categoryRepository, times(1)).findAll();

        assertEquals(adverts.size(), 1);
        assertEquals(adverts.get(0).getCategory().getName(), "Kategoria 1");
    }
}
