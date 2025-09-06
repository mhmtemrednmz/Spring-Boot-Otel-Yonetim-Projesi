package com.emrednmz.services;

import com.emrednmz.dto.request.ReviewRequest;
import com.emrednmz.dto.response.DtoReview;
import com.emrednmz.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReviewService {
    Page<Review> findAllReviews(Pageable pageable);
    List<DtoReview> getDtoReviews(List<Review> reviews);
    DtoReview getReviewById(Long id);
    DtoReview createReview(ReviewRequest reviewRequest);
    DtoReview updateReview(ReviewRequest reviewRequest, Long id);
    void deleteReview(Long id);

}
