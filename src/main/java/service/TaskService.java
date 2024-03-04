package service;

import dto.TaskDTO;
import entity.Director;
import entity.Task;
import entity.Review;
import mapper.DirectorMapper;
import mapper.TaskMapper;
import repository.DirectorDAO;
import repository.TaskDAO;
import repository.ReviewDAO;

import java.util.List;

public class TaskService {

    private final TaskDAO taskDAO;
    private final TaskMapper taskMapper;
    private final DirectorDAO directorDAO;
    private final ReviewDAO reviewDAO;


    public TaskService(TaskDAO taskDAO, TaskMapper taskMapper, DirectorDAO directorDAO, ReviewDAO reviewDAO) {
        this.taskDAO = taskDAO;
        this.taskMapper = taskMapper;
        this.directorDAO = directorDAO;
        this.reviewDAO = reviewDAO;
    }

    public List<TaskDTO> findTasksByParams(Integer taskId, String title, Integer directorId, Integer year, String genre, String description) {
        List<Task> tasks = taskDAO.findTasksByParams(taskId, title, directorId, year, genre, description);

        for (Task task : tasks) {
            try {
                Director director = directorDAO.findDirectorBytaskId(task.getTaskId());
                task.setDirector(director);

                List<Review> reviews = reviewDAO.findReviewsBytaskId(task.getTaskId());
                task.setReviews(reviews);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return taskMapper.toDtoList(tasks);
    }

    public void addTask(TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);

        if (taskDTO.getDirector() != null) {
            Director director = DirectorMapper.toEntity(taskDTO.getDirector());
            Director existingDirector = directorDAO.findDirectorByName(director.getName());

            if (existingDirector != null) {
                task.setDirector(existingDirector);
            } else {
                directorDAO.addDirector(director);
                task.setDirector(director);
            }
        }

        taskDAO.addTask(task);
    }

    public void updateTask(TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);

        if (taskDTO.getDirector() != null) {
            Director director = DirectorMapper.toEntity(taskDTO.getDirector());
            Director existingDirector = directorDAO.findDirectorByName(director.getName());

            if (existingDirector != null) {
                task.setDirector(existingDirector);
            } else {
                directorDAO.addDirector(director);
                task.setDirector(director);
            }
        }

        taskDAO.updateTask(task);
    }

    public void deleteTask(String title) {
        taskDAO.deleteTask(title);
    }
}