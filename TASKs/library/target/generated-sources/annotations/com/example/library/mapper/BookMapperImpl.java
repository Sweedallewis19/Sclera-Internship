package com.example.library.mapper;

import com.example.library.dto.AuthorResponseDTO;
import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T18:45:19+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private RatingMapper ratingMapper;

    @Override
    public Book toEntity(BookRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setIsbnNumber( dto.getIsbnNumber() );
        book.setName( dto.getName() );
        book.setCategory( dto.getCategory() );
        book.setRating( dto.getRating() );
        book.setPrice( dto.getPrice() );

        return book;
    }

    @Override
    public BookResponseDTO toDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        bookResponseDTO.setId( book.getId() );
        bookResponseDTO.setIsbnNumber( book.getIsbnNumber() );
        bookResponseDTO.setName( book.getName() );
        bookResponseDTO.setCategory( book.getCategory() );
        bookResponseDTO.setRating( book.getRating() );
        bookResponseDTO.setPrice( book.getPrice() );
        bookResponseDTO.setAuthors( authorSetToAuthorResponseDTOSet( book.getAuthors() ) );
        bookResponseDTO.setRatings( ratingMapper.toDTOList( book.getRatings() ) );

        return bookResponseDTO;
    }

    @Override
    public List<BookResponseDTO> toDTOList(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookResponseDTO> list = new ArrayList<BookResponseDTO>( books.size() );
        for ( Book book : books ) {
            list.add( toDTO( book ) );
        }

        return list;
    }

    @Override
    public void updateFromDTO(BookRequestDTO dto, Book book) {
        if ( dto == null ) {
            return;
        }

        book.setIsbnNumber( dto.getIsbnNumber() );
        book.setName( dto.getName() );
        book.setCategory( dto.getCategory() );
        book.setRating( dto.getRating() );
        book.setPrice( dto.getPrice() );
    }

    protected Set<AuthorResponseDTO> authorSetToAuthorResponseDTOSet(Set<Author> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorResponseDTO> set1 = new LinkedHashSet<AuthorResponseDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Author author : set ) {
            set1.add( authorMapper.toDTO( author ) );
        }

        return set1;
    }
}
