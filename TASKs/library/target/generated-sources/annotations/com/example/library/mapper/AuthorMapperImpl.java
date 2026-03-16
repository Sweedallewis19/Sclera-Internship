package com.example.library.mapper;

import com.example.library.dto.AuthorRequestDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.entity.Author;
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
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public Author toEntity(AuthorRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Author author = new Author();

        author.setFirstName( dto.getFirstName() );
        author.setLastName( dto.getLastName() );
        author.setCountry( dto.getCountry() );
        author.setUsername( dto.getUsername() );
        author.setPassword( dto.getPassword() );
        author.setRole( dto.getRole() );

        return author;
    }

    @Override
    public AuthorResponseDTO toDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();

        authorResponseDTO.setId( author.getId() );
        authorResponseDTO.setFirstName( author.getFirstName() );
        authorResponseDTO.setLastName( author.getLastName() );
        authorResponseDTO.setCountry( author.getCountry() );
        authorResponseDTO.setUsername( author.getUsername() );
        authorResponseDTO.setRole( author.getRole() );

        return authorResponseDTO;
    }

    @Override
    public List<AuthorResponseDTO> toDTOList(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorResponseDTO> list = new ArrayList<AuthorResponseDTO>( authors.size() );
        for ( Author author : authors ) {
            list.add( toDTO( author ) );
        }

        return list;
    }

    @Override
    public void updateFromDTO(AuthorRequestDTO dto, Author author) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getFirstName() != null ) {
            author.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            author.setLastName( dto.getLastName() );
        }
        if ( dto.getCountry() != null ) {
            author.setCountry( dto.getCountry() );
        }
        if ( dto.getUsername() != null ) {
            author.setUsername( dto.getUsername() );
        }
        if ( dto.getRole() != null ) {
            author.setRole( dto.getRole() );
        }
    }
}
