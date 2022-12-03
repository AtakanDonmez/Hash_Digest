package messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(DigestRepository repository) {

		return args -> {
			log.info("Preloading " + repository.save(new Digest("Bilbo Baggins")));//, "ee276f71c22457e4bed50e684d99e2bac5908b2efeb7a25b97e3ed6c590592a9")));
			log.info("Preloading " + repository.save(new Digest("Frodo Baggins")));//, "4961e36f9958e5287810753e5340e27af060dcab9f81354c90d880fd4f264055")));
		};
	}
}
