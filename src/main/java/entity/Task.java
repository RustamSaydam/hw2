package entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Task {
    private Integer taskId;
    private String title;
    private Integer year;
    private String genre;
    private String description;
    // One-to-One
    private Director director;
    // One-to-Many
    private List<Review> reviews;

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
    }
}