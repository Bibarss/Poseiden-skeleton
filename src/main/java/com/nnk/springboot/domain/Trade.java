package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type ;
    private double buyQuantity;
    private double sellQuantity;
    private double buyPrice;
    private double sellPrice;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trade;
    private String benchmark;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String  sourceListId;
    private String side;

    public Trade() {
    }

}
