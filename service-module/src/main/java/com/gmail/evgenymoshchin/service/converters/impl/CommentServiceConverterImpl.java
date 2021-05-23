package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Comment;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.converters.CommentServiceConverter;
import com.gmail.evgenymoshchin.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentServiceConverterImpl implements CommentServiceConverter {

    private final UserRepository userRepository;

    @Override
    public CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getCreatedBy());
        commentDTO.setCommentBody(comment.getCommentBody());
        if (Objects.nonNull(comment.getUser())) {
            User user = userRepository.findById(comment.getUser().getId());
            commentDTO.setFirstName(user.getFirstName());
            commentDTO.setLastName(user.getLastName());
        }
        if (Objects.nonNull(comment.getArticle())) {
            commentDTO.setArticleId(comment.getArticle().getId());
        }
        return commentDTO;
    }
}
