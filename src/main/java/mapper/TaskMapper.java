package mapper;

import dto.TaskDTO;
import entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public TaskDTO toDto(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setYear(task.getYear());
        taskDTO.setGenre(task.getGenre());
        taskDTO.setDescription(task.getDescription());

        return taskDTO;
    }

    public static Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskId(taskDTO.getTaskId());
        task.setTitle(taskDTO.getTitle());
        task.setYear(taskDTO.getYear());
        task.setGenre(taskDTO.getGenre());
        task.setDescription(taskDTO.getDescription());

        return task;
    }

    public List<TaskDTO> toDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
