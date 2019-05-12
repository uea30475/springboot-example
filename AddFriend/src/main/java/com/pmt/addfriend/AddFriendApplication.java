package com.pmt.addfriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AddFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddFriendApplication.class, args);
	}

}
