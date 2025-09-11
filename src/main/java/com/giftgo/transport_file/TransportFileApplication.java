package com.giftgo.transport_file;

import com.giftgo.transport_file.controller.InputFileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportFileApplication {
    private static final Logger logger = LoggerFactory.getLogger(TransportFileApplication.class);

    public static boolean skipValidation = false;

	public static void main(String[] args) {
		SpringApplication.run(TransportFileApplication.class, args);
	}

}
