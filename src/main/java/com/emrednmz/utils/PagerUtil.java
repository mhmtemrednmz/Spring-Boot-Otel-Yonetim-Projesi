package com.emrednmz.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@UtilityClass
public class PagerUtil {

    public boolean isNullorEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public Pageable toPageable(RestPageableRequest request){
        if(!isNullorEmpty(request.getColumnName())){
            Sort sortBy = request.isAsc()
                    ? Sort.by(Sort.Direction.ASC, request.getColumnName())
                    : Sort.by(Sort.Direction.DESC, request.getColumnName());

            return PageRequest.of(request.getPageNumber(), request.getPageSize(), sortBy);
        }
        return PageRequest.of(request.getPageNumber(), request.getPageSize());
    }

    public <T> RestPageableEntity<T> toRestPageableResponse(Page<?> page, List<T> content){
        RestPageableEntity<T> entity = new RestPageableEntity<>();
        entity.setTotalElements(page.getTotalElements());
        entity.setPageSize(page.getPageable().getPageSize());
        entity.setPageNumber(page.getPageable().getPageNumber());
        entity.setContent(content);
        return entity;
    }
}
