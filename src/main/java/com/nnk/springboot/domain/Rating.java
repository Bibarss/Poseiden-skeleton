package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

    public Rating() {
    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, int i) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = i;
    }
}
