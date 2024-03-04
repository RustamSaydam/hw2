package servlet;

import com.google.gson.Gson;
import dto.TaskDTO;
import mapper.TaskMapper;
import repository.DirectorDAO;
import repository.TaskDAO;
import repository.ReviewDAO;
import service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private final TaskService taskService;
    private final Gson gson;

    public TaskServlet() {
        this.taskService = new TaskService(new TaskDAO(), new TaskMapper(), new DirectorDAO(), new ReviewDAO());
        this.gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer taskId = parseIntegerParameter(request.getParameter("taskId"));
        String title = request.getParameter("title");
        Integer directorId = parseIntegerParameter(request.getParameter("directorId"));
        Integer year = parseIntegerParameter(request.getParameter("year"));
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");

        try {
            List<TaskDTO> tasks = taskService.findTasksByParams(taskId, title, directorId, year, genre, description);

            String json = gson.toJson(tasks);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            TaskDTO newTaskDTO = gson.fromJson(requestBody, TaskDTO.class);
            taskService.addTask(newTaskDTO);

            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        try {
            TaskDTO updatedTaskDTO = gson.fromJson(requestBody, TaskDTO.class);
            taskService.updateTask(updatedTaskDTO);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");

        try {
            taskService.deleteTask(title);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private Integer parseIntegerParameter(String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}