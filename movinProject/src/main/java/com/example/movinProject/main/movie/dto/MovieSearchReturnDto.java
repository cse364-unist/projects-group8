package com.example.movinProject.main.movie.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieSearchReturnDto {
    private Long id;
    private String thumbnailUrl;
    private int year;
    private String name;
}
