package dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {
    private Integer taskId;
    private String title;
    private Integer year;
    private String genre;
    private String description;
    private DirectorDTO director;
    private List<ReviewDTO> reviews;
}