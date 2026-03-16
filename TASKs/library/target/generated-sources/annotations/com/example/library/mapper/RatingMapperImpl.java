package com.example.library.mapper;

import com.example.library.dto.BookRatingDTO;
import com.example.library.entity.BookRating;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T18:45:20+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class RatingMapperImpl implements RatingMapper {

    @Override
    public BookRatingDTO toDTO(BookRating rating) {
        if ( rating == null ) {
            return null;
        }

        BookRatingDTO bookRatingDTO = new BookRatingDTO();

        bookRatingDTO.setUsername( rating.getUsername() );
        bookRatingDTO.setRating( rating.getRating() );

        return bookRatingDTO;
    }

    @Override
    public List<BookRatingDTO> toDTOList(List<BookRating> ratings) {
        if ( ratings == null ) {
            return null;
        }

        List<BookRatingDTO> list = new ArrayList<BookRatingDTO>( ratings.size() );
        for ( BookRating bookRating : ratings ) {
            list.add( toDTO( bookRating ) );
        }

        return list;
    }
}
