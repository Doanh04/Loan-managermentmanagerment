package com.identity.Maper;

import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.response.UserCreationResponse;
import com.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMaper {
    @Mapping(source = "userName", target = "Username")
    @Mapping(source = "")
    User toUserEntity(UserCreationRequest userRequest);

    UserCreationResponse toUserResponse(User userEntity);
}
