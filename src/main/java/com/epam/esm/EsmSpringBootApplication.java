package com.epam.esm;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class EsmSpringBootApplication {
    private final EntityManagerFactory factory;

    public EsmSpringBootApplication(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(EsmSpringBootApplication.class, args);
    }

    public SessionFactory getSessionFactory() {
        return factory.unwrap(SessionFactory.class);
    }
}
