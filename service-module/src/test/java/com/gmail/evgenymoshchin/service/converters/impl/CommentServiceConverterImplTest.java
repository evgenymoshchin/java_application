package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.Comment;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.model.CommentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceConverterImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CommentServiceConverterImpl converter;

    @Test
    void shouldConvertCommentToDTOAndReturnCorrectId() {
        Comment comment = new Comment();
        Long testId = 1L;
        comment.setId(testId);
        CommentDTO commentDTO = converter.convertCommentToDTO(comment);
        Assertions.assertEquals(testId, commentDTO.getId());
    }

    @Test
    void shouldConvertCommentToDTOAndReturnCorrectCommentBody() {
        Comment comment = new Comment();
        String commentBody = "test";
        comment.setCommentBody(commentBody);
        CommentDTO commentDTO = converter.convertCommentToDTO(comment);
        Assertions.assertEquals(commentBody, commentDTO.getCommentBody());
    }

    @Test
    void shouldConvertCommentToDTOAndReturnCorrectDate() {
        Comment comment = new Comment();
        LocalDate date = LocalDate.now();
        comment.setCreatedBy(date);
        CommentDTO commentDTO = converter.convertCommentToDTO(comment);
        Assertions.assertEquals(date, commentDTO.getDate());
    }

    @Test
    void shouldConvertCommentToDTOAndReturnCorrectFirstName() {
        User user = new User();
        String firstName = "name";
        Long id = 1L;
        user.setId(id);
        user.setFirstName(firstName);
        when(userRepository.findById(id)).thenReturn(user);
        Comment comment = new Comment();
        comment.setUser(user);
        CommentDTO commentDTO = converter.convertCommentToDTO(comment);
        Assertions.assertEquals(firstName, commentDTO.getFirstName());
    }

    @Test
    void shouldConvertCommentToDTOAndReturnCorrectLastName() {
        User user = new User();
        String lastName = "name";
        Long id = 1L;
        user.setId(id);
        user.setLastName(lastName);
        when(userRepository.findById(id)).thenReturn(user);
        Comment comment = new Comment();
        comment.setUser(user);
        CommentDTO commentDTO = converter.convertCommentToDTO(comment);
        Assertions.assertEquals(lastName, commentDTO.getLastName());
    }

    @Test
    void shouldConvertCommentToDTOAndReturnCorrectArticleId() {
        Article article = new Article();
        Long id = 1L;
        article.setId(id);
        Comment comment = new Comment();
        comment.setArticle(article);
        CommentDTO commentDTO = converter.convertCommentToDTO(comment);
        Assertions.assertEquals(id, commentDTO.getArticleId());
    }
}