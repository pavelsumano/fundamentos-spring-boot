package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;

	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;



	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService ){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;

	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args){
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("test1", "test1@gmail.com", LocalDate.of(2021,12,29));
		User test2 = new User("test2", "test2@gmail.com", LocalDate.of(2021,10,3));
		User test3 = new User("test3", "test1@gmail.com", LocalDate.of(2021,9,9));
		User test4 = new User("test4", "test4@gmail.com", LocalDate.of(2021,8,2));
		List<User> users = Arrays.asList(test1,test2,test3,test4);
		try {
			userService.saveTransactional(users);
		} catch (Exception e){
			LOGGER.error("Exeption dentro del transactional");
		}
		userService.getAllUsers().stream().forEach(user -> LOGGER.info("Este es el usuario transaccional: "+user));
	}


	private void getInformationJpqlFromUser(){
		LOGGER.info("Usuario con el metodo "+
				userRepository.findByUserEmail("user5@gmail.com")
					.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

		userRepository.findAndSort("Jo", Sort.by("id").ascending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con metodo Sort: "+user));

		userRepository.findByName("John")
			.stream()
			.forEach(user -> LOGGER.info("Usuario con query method: "+user));

		LOGGER.info("Usuario con el metodo findByEmailAndName"+
				userRepository.findByEmailAndName("user6@gmail.com", "Dani")
						.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

		userRepository.findByNameLike("%R%")
				.stream()
				.forEach(user -> LOGGER.info("usuarios findByNameLike "+user));

		userRepository.findByNameOrEmail(null, "user2@gmail.com")
				.stream()
				.forEach(user -> LOGGER.info("usuarios findByNameOrEmail "+user));

		userRepository.findByBirthdayBetween(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 7, 1))
				.stream()
				.forEach(user -> LOGGER.info("usuarios findByBirthdayBetween "+user));

		userRepository.findByNameLikeOrderByIdDesc("%a%")
				.stream()
				.forEach(user -> LOGGER.info("usuarios findByNameLikeOrderByIdDesc "+user));

		userRepository.findByEmailContainingOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("usuarios findByEmailContainingOrderByIdDesc "+user));

		LOGGER.info("Usuario desde named parameter getAllByBirthdayAndEmail: "+
		userRepository.getAllByBirthdayAndEmail(LocalDate.of(2021,9,24), "jon3@gmail.com")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));
	}

	private void saveUsersInDataBase(){
		User user1 = new User("John", "jonathan@gmail.com", LocalDate.of(2021,11,29));
		User user2 = new User("Rafa", "user2@gmail.com", LocalDate.of(2021,10,21));
		User user3 = new User("Vicky", "jon3@gmail.com", LocalDate.of(2021,9,24));
		User user4 = new User("Paco", "user4@gmail.com", LocalDate.of(2021,6,25));
		User user5 = new User("Angel", "user5@gmail.com", LocalDate.of(2021,1,2));
		User user6 = new User("Dani", "user6@gmail.com", LocalDate.of(2021,2,27));
		User user7 = new User("Sergio", "user7@gmail.com", LocalDate.of(2021,3,12));
		User user8 = new User("Eufro", "user8@gmail.com", LocalDate.of(2021,5,2));
		User user9 = new User("Ragul", "user9@gmail.com", LocalDate.of(2021,7,3));
		User user10 = new User("Ghandi", "user0@gmail.com", LocalDate.of(2021,2,7));
		List<User> list = Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10);
		list.stream().forEach(userRepository::save);
	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+" - "+userPojo.getPassword());
		try {
			//error
			int value = 10/0;
			LOGGER.debug("mi valor: "+value);
		} catch (Exception e){
			LOGGER.error("esto es un error al dividir por 0 " + e.getMessage());
		}
	}
}
