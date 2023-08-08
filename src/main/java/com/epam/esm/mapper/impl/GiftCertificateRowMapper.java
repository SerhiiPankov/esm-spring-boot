package com.epam.esm.mapper.impl;

import com.epam.esm.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getObject("id", BigInteger.class));
        giftCertificate.setName(rs.getNString("name"));
        giftCertificate.setDescription(rs.getNString("description"));
        giftCertificate.setPrice(rs.getBigDecimal("price"));
        giftCertificate.setDuration(rs.getInt("duration"));
        giftCertificate.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
        giftCertificate.setLastUpdateDate(rs.getTimestamp("lastUpdateDate").toLocalDateTime());
        return giftCertificate;
    }
}
