package vn.edu.iuh.fit.backend;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repository.ProductRepository;

import java.util.Random;

@SpringBootApplication
public class WwwWeek07Application {
	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(WwwWeek07Application.class, args);
	}

	@Bean
	CommandLineRunner add(){
		return  args -> {
			Faker  dt = new Faker();
			Random rd = new Random();
			for (int i = 0; i < 10; i++) {
				int amount = 50 + rd.nextInt(50);
				Product product = new Product(dt.commerce().productName(), dt.lorem().paragraph(10),
						dt.lorem().characters(10,15),
						dt.aviation().manufacturer(),
						ProductStatus.ACTIVE
						);
				Random random = new Random();
				double price = random.nextDouble(10000);
				ProductPrice productPrice = new ProductPrice(price,"note #"+i);
				ProductImage productImage = new ProductImage(dt.internet().image().toString(),"anh #"+i);
				product.addPrice(productPrice);
				product.addProductImage(productImage);
				productRepository.save(product);
			}

		};
	}

}
