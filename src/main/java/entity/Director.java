package entity;

import lombok.*;

/**
 * Класс для реализации One-to-One к фильмам
 */
@Data
@NoArgsConstructor
public class Director {
    private Integer  directorId;
    private String name;
    //One-to-One
    private Task task;

}
