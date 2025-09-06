package com.emrednmz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "refresh_token")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity{
    @Column
    private String refreshToken;
    private Date expiredDate;

    @ManyToOne
    private UserEntity user;
}
