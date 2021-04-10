package com.izertis.eventhubprocessor;

import com.azure.spring.integration.core.api.reactor.Checkpointer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.izertis.eventhubprocessor.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import static com.azure.spring.integration.core.AzureHeaders.CHECKPOINTER;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.izertis.eventhubprocessor.model"})  // scan JPA entities
public class EventHubProcessorApplication {

	public static final Logger LOGGER = LoggerFactory.getLogger(EventHubProcessorApplication.class);

	@Autowired
	DataService dataService;


	public static void main(String[] args) {
		SpringApplication.run(EventHubProcessorApplication.class, args);
	}

	@Bean
	public Consumer<Message<String>> consume() {
		return message -> {
			Checkpointer checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);
			LOGGER.info("New message received: '{}'", message);

			JsonObject jTweet = new JsonParser().parse(message.getPayload()).getAsJsonObject();
			dataService.saveTweet(jTweet);

			checkpointer.success()
					.doOnSuccess(success -> LOGGER.info("Message '{}' successfully checkpointed", message))
					.doOnError(error -> LOGGER.error("Exception: {}", error.getMessage()))
					.subscribe();
		};
	}

}
