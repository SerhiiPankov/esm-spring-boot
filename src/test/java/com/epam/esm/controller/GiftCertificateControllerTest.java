package com.epam.esm.controller;

import com.epam.esm.controller.repository.TestGiftCertificateRepository;
import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.model.GiftCertificate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GiftCertificateControllerTest {
    private static final String CERTIFICATES_URI = "/certificates";
    protected static final String HEADER_NAME_CONTENT_TYPE = "Content-type";
    protected static final String CONTENT_TYPE = "application/hal+json";

    private static final int NUMBER_OF_TAGS = 5;
    private static final int NUMBER_OF_CERTIFICATES = 20;

    private static final String CREATED_TAG_NAME = "created_tag_name_cert";
    private static final String CREATED_CERTIFICATE_NAME = "CREATED_TEST_CERT_NAME";
    private static final String CREATED_CERTIFICATE_DESCRIPTION = "CREATED_TEST_CERT_DESC";
    private static final int CREATED_CERTIFICATE_DURATION = 10;
    private static final BigDecimal CREATED_CERTIFICATE_PRICE = BigDecimal.valueOf(99.99);

    private static final String NEW_TAG_NAME = "new_tag_name_cert";
    private static final String NEW_CERTIFICATE_NAME = "NEW_TEST_CERT_NAME";
    private static final String UPDATED_CERTIFICATE_NAME = "UPDATED_TEST_CERT_NAME";
    private static final String NEW_CERTIFICATE_DESCRIPTION = "NEW_TEST_CERT_DESC";
    private static final String UPDATED_CERTIFICATE_DESCRIPTION = "NEW_TEST_CERT_DESC";
    private static final int NEW_CERTIFICATE_DURATION = 10;
    private static final BigDecimal NEW_CERTIFICATE_PRICE = BigDecimal.valueOf(99.99);
    private static final BigDecimal UPDATED_CERTIFICATE_PRICE = BigDecimal.valueOf(999.99);

    private static final String DELETED_TAG_NAME = "deleted_tag_name_cert";
    private static final String DELETED_CERTIFICATE_NAME = "DELETED_TEST_CERT_NAME";
    private static final String DELETED_CERTIFICATE_DESCRIPTION = "DELETED_TEST_CERT_DESC";
    private static final int DELETED_CERTIFICATE_DURATION = 10;
    private static final BigDecimal DELETED_CERTIFICATE_PRICE = BigDecimal.valueOf(99.99);

    private static final String TAG_NAME = "tag_name_cert";
    private static final String CERTIFICATE_NAME = "TEST_CERT_NAME";
    private static final String CERTIFICATE_DESCRIPTION = "TEST_CERT_DESC";
    private static final int CERTIFICATE_DURATION = 10;
    private static final BigDecimal CERTIFICATE_PRICE = BigDecimal.valueOf(99.99);


    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
            .withDatabaseName("springboot")
            .withUsername("springboot")
            .withPassword("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Autowired
    SessionFactory factory;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TestGiftCertificateRepository giftCertificateRepository;

    @Test
    void createCertificate_ok() {
        GiftCertificateRequestDto dto = new GiftCertificateRequestDto();
        dto.setName(CREATED_CERTIFICATE_NAME);
        dto.setDescription(CREATED_CERTIFICATE_DESCRIPTION);
        dto.setDuration(CREATED_CERTIFICATE_DURATION);
        dto.setPrice(CREATED_CERTIFICATE_PRICE);
        dto.setTags(getTags(CREATED_TAG_NAME, NUMBER_OF_TAGS));

        webTestClient.post()
                .uri(CERTIFICATES_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("name").isEqualTo(CREATED_CERTIFICATE_NAME)
                .jsonPath("id").isNotEmpty();


    }

    @Test
    void updateCertificate_Ok() {
        GiftCertificate giftCertificate = giftCertificateRepository.addCertificate(
                NEW_CERTIFICATE_NAME, NEW_CERTIFICATE_DESCRIPTION,
                NEW_CERTIFICATE_DURATION, NEW_CERTIFICATE_PRICE,
                getTags(NEW_TAG_NAME, NUMBER_OF_TAGS)
        );

        GiftCertificateRequestDto dto = new GiftCertificateRequestDto();
        dto.setName(UPDATED_CERTIFICATE_NAME);
        webTestClient.put()
                .uri(ub -> ub
                        .path(CERTIFICATES_URI + "/{giftCertificateId}")
                        .build(giftCertificate.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("name").isEqualTo(UPDATED_CERTIFICATE_NAME)
                .jsonPath("id").isEqualTo(giftCertificate.getId())
                .jsonPath("description").isEqualTo(NEW_CERTIFICATE_DESCRIPTION);

        GiftCertificateRequestDto updatedDto = new GiftCertificateRequestDto();
        updatedDto.setDescription(UPDATED_CERTIFICATE_DESCRIPTION);
        updatedDto.setPrice(UPDATED_CERTIFICATE_PRICE);
        webTestClient.put()
                .uri(ub -> ub
                        .path(CERTIFICATES_URI + "/{giftCertificateId}")
                        .build(giftCertificate.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedDto)
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("id").isEqualTo(giftCertificate.getId())
                .jsonPath("name").isEqualTo(UPDATED_CERTIFICATE_NAME)
                .jsonPath("description").isEqualTo(UPDATED_CERTIFICATE_DESCRIPTION)
                .jsonPath("price").isEqualTo(UPDATED_CERTIFICATE_PRICE);

        GiftCertificate actual = factory.openSession().createQuery(
                        "FROM GiftCertificate WHERE id = :id", GiftCertificate.class)
                .setParameter("id", giftCertificate.getId()).uniqueResult();
        Assertions.assertEquals(UPDATED_CERTIFICATE_NAME, actual.getName());
        Assertions.assertEquals(UPDATED_CERTIFICATE_DESCRIPTION, actual.getDescription());
        Assertions.assertEquals(UPDATED_CERTIFICATE_PRICE, actual.getPrice());
    }

    @Test
    void deleteCertificate() {
        GiftCertificate giftCertificate = giftCertificateRepository.addCertificate(
                DELETED_CERTIFICATE_NAME, DELETED_CERTIFICATE_DESCRIPTION,
                DELETED_CERTIFICATE_DURATION, DELETED_CERTIFICATE_PRICE,
                getTags(DELETED_TAG_NAME, NUMBER_OF_TAGS)
        );

        webTestClient.delete()
                .uri(ub -> ub
                        .path(CERTIFICATES_URI + "/{tagId}")
                        .build(giftCertificate.getId()))
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("message")
                .isEqualTo("Gift certificate with id " + giftCertificate.getId() + " was deleted");

        Optional<GiftCertificate> actual = factory.openSession().createQuery(
                        "FROM GiftCertificate WHERE id = :id", GiftCertificate.class)
                .setParameter("id", giftCertificate.getId()).uniqueResultOptional();
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    void getCertificate_Ok() {
        GiftCertificate giftCertificate = giftCertificateRepository.addCertificate(
                CERTIFICATE_NAME, CERTIFICATE_DESCRIPTION,
                CERTIFICATE_DURATION, CERTIFICATE_PRICE,
                getTags(TAG_NAME, NUMBER_OF_TAGS)
        );

        webTestClient.get()
                .uri(ub -> ub
                        .path(CERTIFICATES_URI + "/{tagId}")
                        .build(giftCertificate.getId()))
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("id").isEqualTo(giftCertificate.getId())
                .jsonPath("name").isEqualTo(CERTIFICATE_NAME)
                .jsonPath("description").isEqualTo(CERTIFICATE_DESCRIPTION)
                .jsonPath("duration").isEqualTo(CERTIFICATE_DURATION)
                .jsonPath("price").isEqualTo(CERTIFICATE_PRICE)
                .jsonPath("tags").isArray()
                .jsonPath("tags").isNotEmpty();
    }

    @Test
    void getAllCertificate_Ok() {
        for (int i = 0; i < NUMBER_OF_CERTIFICATES; i++) {
            giftCertificateRepository.addCertificate(
                    CERTIFICATE_NAME + i, CERTIFICATE_DESCRIPTION + i,
                    CERTIFICATE_DURATION, CERTIFICATE_PRICE.add(BigDecimal.valueOf(i)),
                            getTags(TAG_NAME + i, NUMBER_OF_TAGS));
        }

        webTestClient.get()
                .uri(ub -> ub
                        .path(CERTIFICATES_URI)
                        .queryParam("count", "10")
                        .build())
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("page").exists()
                .jsonPath("page.size").isEqualTo(10)
                .jsonPath("_embedded.giftCertificateModelList").isArray()
                .jsonPath("_embedded.giftCertificateModelList[9].name").exists();
    }

    private List<String> getTags(String sample, int number) {
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            tags.add(sample + i);
        }
        return tags;
    }
}
