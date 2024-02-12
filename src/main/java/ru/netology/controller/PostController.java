package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson = new Gson();

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) {
        // TODO was deserialize request & serialize response:
        final var data = service.getById(id);
        serializeResonse(data, response);
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        // TODO was deserialize request & serialize response:
        service.removeById(id);
        response.getWriter().print("Successfully Removed");
    }

    public void serializeResonse(Post data, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        try {
            response.getWriter().print(gson.toJson(data));
        } catch (IOException e) {
            throw new NotFoundException("Исключение ввода или вывода");
        }
    }
}
