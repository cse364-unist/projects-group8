package com.example.movinProject.main.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
public class MovieDto {

    private Long id;
    private String thumbnailUrl;
    private String name;

}