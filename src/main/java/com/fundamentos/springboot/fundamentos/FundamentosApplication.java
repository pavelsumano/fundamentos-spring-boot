package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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



	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;

	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args){
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
	}
	private void getInformationJpqlFromUser(){
		LOGGER.info("Usuario con el metodo "+
				userRepository.findByUserEmail("user5@gmail.com")
					.orElseThrow(() -> new RuntimeException("No se encontro el usuario")));
	}

	private void saveUsersInDataBase(){
		User user1 = new User("John", "jonathan@gmail.com", LocalDate.of(2021,11,29));
		User user2 = new User("Rafa", "user2@gmail.com", LocalDate.of(2021,10,21));
		User user3 = new User("Vocky", "jon3@gmail.com", LocalDate.of(2021,9,24));
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
