package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.CommentRepository;
import com.gmail.evgenymoshchin.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
}
