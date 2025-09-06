package com.emrednmz.mapper;

import com.emrednmz.dto.response.DtoUser;
import com.emrednmz.dto.request.RegisterRequest;
import com.emrednmz.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

   // RegisterRequest → UserEntity dönüşümü
   UserEntity toEntity(RegisterRequest registerRequest);

   // UserEntity → DtoUser dönüşümü
   @Mapping(target = "roles",
           expression = "java(userEntity.getRoles() != null ? " +
                   "userEntity.getRoles().stream().map(r -> r.getName().name()).toList() : null)")
   DtoUser toDto(UserEntity userEntity);
}

