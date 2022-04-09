package com.hong_hoan.iuheducation;

import com.hong_hoan.iuheducation.resolvers.input.CreateAccountInput;
import com.hong_hoan.iuheducation.resolvers.input.Role;
import com.hong_hoan.iuheducation.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class IuhEducationApplication {

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(IuhEducationApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		CreateAccountInput _createAccountInput = CreateAccountInput.builder()
				.username("admin")
				.password("admin")
				.roles(new ArrayList<>(Arrays.asList(Role.ADMIN)))
				.build();

		boolean _isAccountExist = accountService.exists(_createAccountInput.getUsername());

		if(!_isAccountExist) {
			accountService.createAccount(null, _createAccountInput);
		}
	}


}
