package com.epam.esm.initializer;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.exception.AuthenticationException;
import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.initializer.dto.UserApiResponseDto;
import com.epam.esm.initializer.dto.UserApiResultsDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private static final String USER_ROLE_NAME = "USER";
    private static final String API_RANDOM_USER = "https://randomuser.me/api/?inc=email,login&noinfo&results=";
    private static final String API_RANDOM_WORD = "https://random-word-api.herokuapp.com/word?number=";
    private static final int NUMBER_OF_USERS = 2;
    private static final int NUMBER_OF_GIFT_CERTIFICATE = 2;
    private static final int MAX_NUMBER_OF_EXISTING_TAGS_PER_GIFT_CERTIFICATE = 2;
    private static final int MAX_NUMBER_OF_NEW_TAGS_PER_GIFT_CERTIFICATE = 2;
    private static final int MAX_DURATION = 356;
    private static final Random RANDOM = new Random();
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateService giftCertificateService;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final TagService tagService;
    private final RoleService roleService;
    private final UserService userService;
    private final HttpClient httpClient;

    public DataInitializer(GiftCertificateService giftCertificateService,
                           GiftCertificateMapper giftCertificateMapper,
                           OrderService orderService,
                           ShoppingCartService shoppingCartService,
                           TagService tagService,
                           RoleService roleService,
                           UserService userService,
                           HttpClient httpClient) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateMapper = giftCertificateMapper;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.tagService = tagService;
        this.roleService = roleService;
        this.userService = userService;
        this.httpClient = httpClient;
    }

    @PostConstruct
    public void inject() {
        Role adminRole;
        try {
            adminRole = roleService.getByName(Role.RoleName.ADMIN.name());
        } catch (DataProcessingException e) {
            adminRole = new Role();
            adminRole.setRoleName(Role.RoleName.ADMIN);
            roleService.create(adminRole);
        }
        Role userRole;
        try {
            userRole = roleService.getByName(Role.RoleName.USER.name());
        } catch (DataProcessingException e) {
            userRole = new Role();
            userRole.setRoleName(Role.RoleName.USER);
            roleService.create(userRole);
        }
        try {
            User admin = userService.register(
                    "admin@gmail.com", "12345678", Set.of(adminRole, userRole));
        } catch (AuthenticationException e) {
            System.out.println("Admin was added earlier");
        }
        try {
            User user = userService.register("user@gmail.com", "87654321", Set.of(userRole));
        } catch (AuthenticationException e) {
            System.out.println("User was added earlier");
        }
    }

    public void injectUsers() {
        Set<UserApiResultsDto> userApiResultsDtoSet = new HashSet<>();
        while (userApiResultsDtoSet.size() < NUMBER_OF_USERS) {
            userApiResultsDtoSet.addAll(Arrays.stream(
                    httpClient.get(API_RANDOM_USER + NUMBER_OF_USERS, UserApiResponseDto.class)
                            .getResults()).collect(Collectors.toSet()));
        }
        List<UserApiResultsDto> userApiResultsDtoList = new ArrayList<>(userApiResultsDtoSet);
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            UserApiResultsDto userApiResultsDto = userApiResultsDtoList.get(i);
            userService.register(userApiResultsDto.getEmail(),
                    userApiResultsDto.getLogin().getPassword(),
                    Set.of(roleService.getByName(USER_ROLE_NAME)));
        }
    }

    public void injectGiftCertificates() {
        List<String> listUniqueWords = getListUniqueWords(NUMBER_OF_GIFT_CERTIFICATE * 2 + 1);
        for (int i = 1; i <= NUMBER_OF_GIFT_CERTIFICATE * 2; i = i + 2) {
            GiftCertificateRequestDto randomGiftCertificate =
                    getRandomGiftCertificate(listUniqueWords.get(i), listUniqueWords.get(i + 1));
            giftCertificateService.create(
                    giftCertificateMapper.mapToGiftCertificate(randomGiftCertificate));
        }
    }

    public void injectRelationshipsShoppingCartWithGiftCertificates() {
        for (int i = 3; i < NUMBER_OF_USERS + 3; i++) {
            User user = userService.get(i);
            int numberOfGiftCertificates = RANDOM.nextInt(5 - 1) + 1;
            for (int j = 1; j <= numberOfGiftCertificates; j++) {
                GiftCertificate giftCertificate = giftCertificateService.get(
                        BigInteger.valueOf(RANDOM.nextLong(NUMBER_OF_GIFT_CERTIFICATE - 1) + 1));
                shoppingCartService.addGiftCertificate(giftCertificate, user);
            }
        }
    }

    public void injectCompletedOrders() {
        for (int i = 3; i < NUMBER_OF_USERS + 3; i++) {
            orderService.completeOrder(shoppingCartService.getByUser(userService.get(i)));
        }
    }

    private List<String> getListUniqueWords(int count) {
        Set<String> uniqueGiftCertificateNames = new HashSet<>();
        while (uniqueGiftCertificateNames.size() < count) {
            uniqueGiftCertificateNames.addAll(Arrays.stream(
                    httpClient.get(API_RANDOM_WORD + count, String[].class)
            ).collect(Collectors.toSet()));
        }
        return new ArrayList<>(uniqueGiftCertificateNames);
    }

    private GiftCertificateRequestDto getRandomGiftCertificate(String name, String description) {
        GiftCertificateRequestDto giftCertificateRequestDto = new GiftCertificateRequestDto();
        giftCertificateRequestDto.setName(name);
        giftCertificateRequestDto.setDescription(description);
        giftCertificateRequestDto.setPrice(BigDecimal.valueOf(RANDOM.nextDouble(9999.99)));
        giftCertificateRequestDto.setDuration(RANDOM.nextInt(MAX_DURATION - 1) + 1);

        List<String> strings = new ArrayList<>(Arrays.stream(getRandomWord(
                RANDOM.nextInt(MAX_NUMBER_OF_NEW_TAGS_PER_GIFT_CERTIFICATE - 1) + 1)).toList());
        strings.addAll(getExistingTagName());
        giftCertificateRequestDto.setTags(strings);
        return giftCertificateRequestDto;
    }

    private List<String> getExistingTagName() {
        List<String> existingTagNames = new ArrayList<>();
        long count = tagService.count();
        if (count > 1) {
            for (int i = 0; i < RANDOM.nextInt(
                    MAX_NUMBER_OF_EXISTING_TAGS_PER_GIFT_CERTIFICATE - 1) + 1; i++) {
                existingTagNames.add(tagService.get(BigInteger.valueOf(
                        RANDOM.nextLong(count - 1) + 1)).getName());
            }
        }
        return existingTagNames;
    }

    private String[] getRandomWord(int count) {
        return httpClient.get(
                API_RANDOM_WORD + count, String[].class);
    }
}
