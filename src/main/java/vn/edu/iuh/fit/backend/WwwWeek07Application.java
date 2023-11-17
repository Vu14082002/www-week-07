package vn.edu.iuh.fit.backend;

import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.CollectionUtils;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.repository.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Random;

@SpringBootApplication
@AllArgsConstructor
public class WwwWeek07Application {
	private ProductRepository productRepository;
	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;
	private EmployeeRepository employeeRepository;
	private OrderDetailRepository orderDetailRepository;

	public static void main(String[] args) {
		SpringApplication.run(WwwWeek07Application.class, args);
	}

	@Bean
	CommandLineRunner add(){
		return  args -> {
			Faker  dt = new Faker();
			Random rd = new Random();
			for (int i = 0; i < 10; i++) {
				Product product = new Product(dt.commerce().productName(), dt.lorem().paragraph(10),
						dt.lorem().characters(10,15),
						dt.aviation().manufacturer(),
						ProductStatus.ACTIVE
						);
				Random random = new Random();
				double price = random.nextDouble(10000);
				ProductPrice productPrice = new ProductPrice(price, "note #" + i);
				ProductImage productImage = new ProductImage(dt.internet().image(), "anh #" + i);
				product.addPrice(productPrice);
				product.addProductImage(productImage);
				productRepository.save(product);
			}
			for (int i = 0; i < 20; i++) {
				Customer customer = new Customer(dt.name().name(), dt.internet().emailAddress(),
						"1235003246", dt.address().fullAddress());
				customerRepository.save(customer);
			}
			// Employee
			for (int i = 0; i < 15; i++) {
				Employee employee = new Employee();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				employee.setDob(LocalDate.now());
				employee.setStatus(EmployeeStatus.ACTIVE);
				employee.setPhone(dt.phoneNumber().cellPhone());
				employee.setEmail(dt.internet().emailAddress());
				employee.setFullname(dt.name().fullName());
				employee.setAddress(dt.address().streetAddress());
				employeeRepository.save(employee);
			}
			// Order
			for (int i = 0; i < 15; i++) {
				Order order = new Order();
				order.setEmployee(employeeRepository.findById(Long.parseLong(i+1+"")).get());
				order.setCustomer(customerRepository.findById(Long.parseLong(i+1+"")).get());
				order.setOrderDate(LocalDateTime.now());
				orderRepository.save(order);
			}
			// Order detail
			for (int i = 0; i < 10; i++) {
				OrderDetail orderDetail = new OrderDetail();
				Order order = orderRepository.findById(Long.parseLong(i+1+"")).get();
				for (int j = 0; j <8; j++) {
					Product product = productRepository.findById(Long.parseLong(j+1+"")).get();
					Random random = new Random();
					double quantity = random.nextInt(1000);
					double price = CollectionUtils.lastElement(product.getProductPrices()).getPrice();
					orderDetail.setProduct(product);
					orderDetail.setOrder(order);
					orderDetail.setNote(dt.lorem().characters(10,20));
					orderDetail.setQuantity(quantity);
					orderDetail.setPrice(price);
					orderDetailRepository.save(orderDetail);
				}

			}
		};
	}

}
