package com.example.library.mapper;

import com.example.library.dto.AuthorRequestDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.entity.Author;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avgPrice", ignore = true)
    Author toEntity(AuthorRequestDTO dto);

    AuthorResponseDTO toDTO(Author author);

    List<AuthorResponseDTO> toDTOList(List<Author> authors);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avgPrice", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateFromDTO(AuthorRequestDTO dto, @MappingTarget Author author);
}
