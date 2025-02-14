package com.team5.secondhand.application.item.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.secondhand.application.item.domain.ItemDetailImage;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.validation.ValidationException;
import java.util.List;

public class ImageUrlConverter implements AttributeConverter<List<ItemDetailImage>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ItemDetailImage> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new ValidationException("이미지 직렬화에 실패했습니다.");
        }
    }

    @Override
    @SneakyThrows
    public List<ItemDetailImage> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<ItemDetailImage>>() {});
        } catch (JsonProcessingException e) {
            throw new ValidationException("이미지 URL 형식이 다릅니다.");
        }
    }
}
