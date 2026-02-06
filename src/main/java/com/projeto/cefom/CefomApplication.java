package com.projeto.cefom;

import com.projeto.cefom.configuration.FileStoregeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStoregeConfig.class})
public class CefomApplication {

	public static void main(String[] args) {
		SpringApplication.run(CefomApplication.class, args);
	}

}
