package entity;

import lombok.*;

/**
 * Класс для реализации Many-to-One к фильмам
 */
@Data
@NoArgsConstructor
public class Review {
//    @NonNull
    private Integer  reviewId;
    private String text;
    // Many-to-One
    private Task task;
}