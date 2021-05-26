package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.CommentRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.Comment;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.ArticleSummaryMaker;
import com.gmail.evgenymoshchin.service.converters.ArticleServiceConverter;
import com.gmail.evgenymoshchin.service.converters.CommentServiceConverter;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ArticleServiceConverterImpl implements ArticleServiceConverter {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ArticleSummaryMaker summaryMaker;
    private final CommentServiceConverter commentServiceConverter;

    @Override
    public ArticleDTO convertArticleToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setArticleBody(article.getArticleBody());
        articleDTO.setName(article.getName());
        articleDTO.setSummary(article.getSummary());
        articleDTO.setDate(article.getCreatedBy());
        if (Objects.nonNull(article.getUser())) {
            User user = userRepository.findById(article.getUser().getId());
            articleDTO.setUserId(user.getId());
            articleDTO.setFirstName(user.getFirstName());
            articleDTO.setLastName(user.getLastName());
        }
        if (Objects.nonNull(article.getComments())) {
            List<CommentDTO> commentDTOS = new ArrayList<>();
            List<Comment> comments = article.getComments();
            for (Comment comment : comments) {
                commentDTOS.add(commentServiceConverter.convertCommentToDTO(comment));
            }
            commentDTOS.sort(Comparator.comparing(CommentDTO::getDate).reversed());
            articleDTO.setComments(commentDTOS);
        }
        return articleDTO;
    }

    @Override
    public Article convertDTOtoArticle(ArticleDTO articleDTO, String username) {
        Article article = new Article();
        article.setName(articleDTO.getName());
        String articleBody = articleDTO.getArticleBody();
        if (Objects.nonNull(articleBody)) {
            article.setArticleBody(articleBody);
            String summary = summaryMaker.getSummaryOfArticle(articleBody);
            article.setSummary(summary);
        }
        article.setCreatedBy(LocalDate.now());
        User user = userRepository.findByUsername(username);
        if (Objects.nonNull(user)) {
            article.setUser(user);
        }
        if (Objects.nonNull(articleDTO.getComments())) {
            List<Comment> comments = getCommentsByDTOsIds(articleDTO);
            article.setComments(comments);
        }
        return article;
    }

    private List<Comment> getCommentsByDTOsIds(ArticleDTO articleDTO) {
        List<Comment> comments = new ArrayList<>();
        List<CommentDTO> commentDTOS = articleDTO.getComments();
        for (CommentDTO commentDTO : commentDTOS) {
            if (Objects.nonNull(commentDTO.getId())) {
                Comment comment = commentRepository.findById(commentDTO.getId());
                comments.add(comment);
            }
        }
        return comments;
    }
}
