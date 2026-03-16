package com.example.library.mapper;

import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, RatingMapper.class})
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    Book toEntity(BookRequestDTO dto);

    BookResponseDTO toDTO(Book book);

    List<BookResponseDTO> toDTOList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    void updateFromDTO(BookRequestDTO dto, @MappingTarget Book book);
}
