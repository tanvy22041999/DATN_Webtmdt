package com.spring.ecomerce;

import com.spring.ecomerce.entities.Account;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.repositories.UserRepository;
import com.spring.ecomerce.services.AccountService.AccountService;
import com.spring.ecomerce.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling

public class MainApplication implements CommandLineRunner {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(accountService.count() <= 0){
            Account accountAdmin = new Account();
            accountAdmin.setPhoneNumber("0123456789");
            accountAdmin.setActive(true);
            accountAdmin.setPassword("123456");
            accountAdmin.setRoles(Arrays.asList(EnumRole.ROLE_ADMIN.toString()));
            accountAdmin.setUpdatedDate(LocalDateTime.now());
            accountService.createNewAccount(accountAdmin);

            UserEntity userAdmin = new UserEntity();
//            userAdmin.setFullName("Admin");
//            userAdmin.setPhoneNumber(accountAdmin.getPhoneNumber());
            userRepository.save(userAdmin);

        }
    }

}
