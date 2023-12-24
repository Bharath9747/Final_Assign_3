package com.accolite.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenCheck {
    @Id
    private Long id;
    @Column(unique = true)
    public String jwttoken;
    @Column(unique = true)
    public String refreshtoken;
}
