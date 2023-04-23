package sk.umb.dvestodola.bazarik;

import java.util.ArrayList;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;
import sk.umb.dvestodola.bazarik.currency.persistence.repository.CurrencyRepository;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.repository.SubcategoryRepository;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository.SubsubcategoryRepository;

@SpringBootApplication
public class BazarikApplication implements CommandLineRunner {
	
	@Autowired
	CurrencyRepository currencyRepository;
	
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    SubcategoryRepository subcategoryRepository;
    
    @Autowired
    SubsubcategoryRepository subsubcategoryRepository;
    
    
    String[] categories = new String[]{
        "Autá", "Ovocie", "Elektronika", "Zvieratá",
        "Oblečenie", "Šport", "Knihy", "Hudba",
        "Práca a Služby", "Škola", "Stroje", "Pre deti",
        "Ostatné"  
    };

    String[] subcategories = new String[]{
        // Kategória: Autá
        "Škoda", "Mercedes-Benz", "Opel", "Nissan",
        "Renault", "Volkswagen", "Fiat", "Lada",
        "Kia", "Audi", "Dacia", "Ostatné"
        //TODO: Pridať sekcie pre ostatné kategórie
    };
    String[] subsubcategories = new String[]{
        // Podategória: Autá
        "Staršie ako 2000", "Novšie ako 2000", "Pre rodiny", "Pre luxus",
        "Veterány", "Pracovné", "Ostatné"
        //TODO: Pridať sekcie pre ostatné podkategórie
    };


	public static void main(String[] args) {
		SpringApplication.run(BazarikApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (currencyRepository.count() == 0) {
			insertToCurrencies("€", "Euro");
		}
        if(categoryRepository.count() == 0) { // TODO: Zmeniť na == 0
            for(String category : categories) {
                System.out.println("Inserting category: " + category);
                insertToCategoriesAndSections(category);
            }
        }
	}

	public void insertToCurrencies(String symbol, String name) {
		if (Strings.isBlank(name)) return;
		if (Strings.isBlank(symbol)) return;
		
		CurrencyEntity currencyEntity = new CurrencyEntity();
		currencyEntity.setName(name);
		currencyEntity.setSymbol(symbol);
		currencyRepository.save(currencyEntity);
	}

    public void insertToCategoriesAndSections(String categoryName) {
        if(Strings.isBlank(categoryName))
            return;
        
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName);
        categoryRepository.save(categoryEntity);

        // for(String subcategory : subcategories) {
        //    SubcategoryEntity subcategoryEntity = new SubcategoryEntity();
        //    subcategoryEntity.setCategory(categoryEntity);
        //    System.out.println("Inserting new subcategory into " + categoryName + ": " + subcategory);
        //    subcategoryEntity.setName(subcategory);
            
        //    if(subcategory == subcategories[0]) {
        //        for(String subsubcategory : subsubcategories) {
        //            SubsubcategoryEntity subsubcategoryEntity = new SubsubcategoryEntity();
        //            subsubcategoryEntity.setCategory(subcategoryEntity);
        //            subsubcategoryEntity.setName(subsubcategory);
        //            subsubcategoryRepository.save(subsubcategoryEntity);
        //        }
        //    }

        //     subcategoryRepository.save(subcategoryEntity);
        // }

    }

}
