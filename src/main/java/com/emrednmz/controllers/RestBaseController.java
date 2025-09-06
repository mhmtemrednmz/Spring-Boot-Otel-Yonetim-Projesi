package com.emrednmz.controllers;

import com.emrednmz.utils.PagerUtil;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RestBaseController {

    public <T> RootEntity<T> ok(T payload) {
        return RootEntity.ok(payload);
    }
    public <T> RootEntity<T> error(String errorMessage) {
        return RootEntity.error(errorMessage);
    }

    public Pageable getPageable(RestPageableRequest request) {
        return PagerUtil.toPageable(request);
    }

    public <T> RestPageableEntity<T> toRestPageableResponse(Page<?> page, List<T> content) {
        return PagerUtil.toRestPageableResponse(page, content);
    }
}
