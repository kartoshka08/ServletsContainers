package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final ConcurrentMap<Long, Post> list = new ConcurrentHashMap<>();
    private final static AtomicLong ID = new AtomicLong(0);

    public Collection<Post> all() {
        return list.values();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(list.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            Long newId = ID.incrementAndGet();
            post.setId(newId);
            list.put(newId, post);
        }
        if (list.containsKey(post.getId())) {
            list.put(post.getId(), post);
        } else {
            throw new NotFoundException("Update error");
        }
        return post;
    }

    public void removeById(long id) {
        if (!list.containsKey(id)) {
            throw new NotFoundException("ID is not found");
        }
        list.remove(id);
    }
}

