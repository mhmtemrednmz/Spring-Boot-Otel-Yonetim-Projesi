package com.emrednmz.services.impl;

import com.emrednmz.dto.request.LikeRequest;
import com.emrednmz.dto.request.ReviewRequest;
import com.emrednmz.dto.response.DtoReview;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.ReviewMapper;
import com.emrednmz.model.Hotel;
import com.emrednmz.model.Like;
import com.emrednmz.model.Review;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.HotelRepository;
import com.emrednmz.repositories.ReviewRepository;
import com.emrednmz.repositories.UserRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.IReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements IReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final IAutenticationService authenticationService;


    public ReviewServiceImpl(ReviewMapper reviewMapper,
                             ReviewRepository reviewRepository,
                             HotelRepository hotelRepository,
                             IAutenticationService authenticationService) {
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
        this.hotelRepository = hotelRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Page<Review> findAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public List<DtoReview> getDtoReviews(List<Review> reviews) {
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoReview getReviewById(Long id) {
        return reviewMapper.toDto(findReviewById(id));
    }

    @Override
    public DtoReview createReview(ReviewRequest reviewRequest) {
        UserEntity curentUser = authenticationService.getCurrentUser();
        Review entity = reviewMapper.toEntity(reviewRequest);
        entity.setUser(curentUser);
        entity.setCreateTime(new Date());
        arrangeReview(entity, reviewRequest);
        return reviewMapper.toDto(reviewRepository.save(entity));
    }

    @Override
    public DtoReview updateReview(ReviewRequest reviewRequest, Long id) {
        Review review = findReviewById(id);

        UserEntity curentUser = authenticationService.getCurrentUser();

        if(!review.getUser().getId().equals(curentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, curentUser.getId().toString()));
        }

        reviewMapper.updateReviewFromRequest(reviewRequest, review);
        review.setUpdateTime(new Date());
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long id) {
        Review review = findReviewById(id);
        UserEntity curentUser = authenticationService.getCurrentUser();

        if(!review.getUser().getId().equals(curentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, curentUser.getId().toString()));
        }
        reviewRepository.deleteById(id);
    }

    private Review findReviewById(Long id) {
        return  reviewRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NOT_FOUND_AMENITY, id.toString())));
    }

    private void arrangeReview(Review review, ReviewRequest reviewRequest) {
        // Hotel kontrolü
        Hotel hotel = hotelRepository.findById(reviewRequest.getHotelId())
                .orElseThrow(() -> {
                    return new BaseException(new ErrorMessage(MessageType.NOT_FOUND_HOTEL, reviewRequest.getHotelId().toString()));
                });
        review.setHotel(hotel);
    }
}
