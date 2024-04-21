package com.example.movinProject.main.movie.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieSearchDto {
    private String keyword;
    private int page;
}
