package com.emrednmz.controllers;

import com.emrednmz.dto.request.ReviewRequest;
import com.emrednmz.dto.response.DtoReview;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;


public interface IRestReviewController {
    RootEntity<RestPageableEntity<DtoReview>> getAllReviews(RestPageableRequest request);
    RootEntity<DtoReview> getReviewById(Long id);
    RootEntity<DtoReview> createReview(ReviewRequest request);
    RootEntity<DtoReview> updateReview(ReviewRequest request, Long id);
    void deleteReview(Long id);
}
