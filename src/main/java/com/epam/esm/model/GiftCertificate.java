package com.epam.esm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private BigInteger id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @Min(value = 0)
    private BigDecimal price;
    @Min(value = 1)
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gift_certificates_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private List<Tag> tags;
}
