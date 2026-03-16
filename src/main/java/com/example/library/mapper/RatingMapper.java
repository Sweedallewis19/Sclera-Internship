package com.example.library.mapper;

import com.example.library.dto.BookRatingDTO;
import com.example.library.entity.BookRating;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    BookRatingDTO toDTO(BookRating rating);
    List<BookRatingDTO> toDTOList(List<BookRating> ratings);
}
