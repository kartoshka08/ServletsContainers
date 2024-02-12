package ru.netology.servlet;


import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;

    public static final String api_posts = "/api/posts";
    public static final String api_posts_d = "/api/posts/\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals("GET") && path.equals(api_posts)) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches(api_posts_d)) {
                // easy way
                final var id = easyWayParse(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals(api_posts)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches(api_posts_d)) {
                // easy way
                final var id = easyWayParse(path);
                System.out.println(id);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long easyWayParse(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }

}
