package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestReviewController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.ReviewRequest;
import com.emrednmz.dto.response.DtoReview;
import com.emrednmz.model.Review;
import com.emrednmz.services.IReviewService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review")
public class RestReviewControllerImpl extends RestBaseController implements IRestReviewController {
    private final IReviewService reviewService;

    public RestReviewControllerImpl(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<RestPageableEntity<DtoReview>> getAllReviews(RestPageableRequest request) {
        Page<Review> page = reviewService.findAllReviews(getPageable(request));
        List<DtoReview> dtoReviews = reviewService.getDtoReviews(page.getContent());
        return ok(toRestPageableResponse(page, dtoReviews));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<DtoReview> getReviewById(@PathVariable Long id) {
        return ok(reviewService.getReviewById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_USER')")
    public RootEntity<DtoReview> createReview(@RequestBody @Valid ReviewRequest request) {
        return ok(reviewService.createReview(request));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_USER')")
    public RootEntity<DtoReview> updateReview(@RequestBody @Valid ReviewRequest request,
                                              @PathVariable Long id) {
        return ok(reviewService.updateReview(request, id));
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER','ROLE_USER')")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
