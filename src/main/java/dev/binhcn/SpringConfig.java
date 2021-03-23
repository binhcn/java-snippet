package dev.binhcn;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
	@Bean
	@ConditionalOnProperty(prefix = "module", name = "enabled")
	public SpringService springService() {
		return new SpringService();
	}
}