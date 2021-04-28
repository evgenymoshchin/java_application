package com.gmail.evgenymoshchin.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.gmail.evgenymoshchin.repository.model")
public class PersistenceConfiguration {
}
