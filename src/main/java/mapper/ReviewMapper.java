package mapper;

import dto.ReviewDTO;
import entity.Review;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setText(review.getText());

        return reviewDTO;
    }

    public static Review toEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setText(reviewDTO.getText());

        return review;
    }
}