package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.model.Tag;
import com.epam.esm.controller.repository.TestTagRepository;
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
class TagControllerTest {
    private static final String TAGS_URI = "/tags";
    protected static final String HEADER_NAME_CONTENT_TYPE = "Content-type";
    protected static final String CONTENT_TYPE = "application/hal+json";
    private static final String TAG_NAME = "tag_name";
    private static final String NEW_TAG_NAME = "new_tag_name";
    private static final String UPDATED_TAG_NAME = "updated_tag_name";
    private static final String DELETED_TAG_NAME = "deleted_tag_name";
    private static final int NUMBER_OF_TAGS = 20;

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
    TestTagRepository testTagRepository;

    @Test
    void createTag_Ok() {
        TagRequestDto dto = new TagRequestDto();
        dto.setName(TAG_NAME);

        webTestClient.post()
                .uri(TAGS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("name").isEqualTo(TAG_NAME);


        Tag actual = factory.openSession().createQuery(
                        "FROM Tag WHERE name = :name", Tag.class)
                .setParameter("name", TAG_NAME).uniqueResult();

        Assertions.assertEquals(TAG_NAME, actual.getName());
        Assertions.assertNotNull(actual.getId());
    }

    @Test
    void updateTag_Ok() {
        Tag tag = testTagRepository.addTag(NEW_TAG_NAME);

        TagRequestDto dto = new TagRequestDto();
        dto.setName(UPDATED_TAG_NAME);

        webTestClient.put()
                .uri(ub -> ub
                        .path(TAGS_URI + "/{tagId}")
                        .build(tag.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("id").isEqualTo(tag.getId())
                .jsonPath("name").isEqualTo(UPDATED_TAG_NAME);


        Tag actual = factory.openSession().createQuery(
                        "FROM Tag WHERE id = :id", Tag.class)
                .setParameter("id", tag.getId()).uniqueResult();
        Assertions.assertEquals(UPDATED_TAG_NAME, actual.getName());
    }

    @Test
    void deleteTag_Ok() {
        Tag tag = testTagRepository.addTag(DELETED_TAG_NAME);

        webTestClient.delete()
                .uri(ub -> ub
                        .path(TAGS_URI + "/{tagId}")
                        .build(tag.getId()))
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("message")
                .isEqualTo("Tag with id " + tag.getId() + " was deleted");

        Optional<Tag> actual = factory.openSession().createQuery(
                        "FROM Tag WHERE id = :id", Tag.class)
                .setParameter("id", tag.getId()).uniqueResultOptional();
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    void getAllTags_Ok() {
        for (int i = 0; i < NUMBER_OF_TAGS; i++) {
            testTagRepository.addTag(TAG_NAME + i);
        }

        webTestClient.get()
                .uri(ub -> ub
                        .path(TAGS_URI)
                        .queryParam("count", "10")
                        .build())
                .exchange()
                .expectHeader().valueEquals(HEADER_NAME_CONTENT_TYPE, CONTENT_TYPE)
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody()
                .jsonPath("page").exists()
                .jsonPath("page.size").isEqualTo(10)
                .jsonPath("_embedded.tagModelList").isArray()
                .jsonPath("_embedded.tagModelList[9].name").exists();
    }
}
