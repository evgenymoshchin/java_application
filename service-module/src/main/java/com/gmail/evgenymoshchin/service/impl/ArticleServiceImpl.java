package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ArticleRepository;
import com.gmail.evgenymoshchin.repository.CommentRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.Comment;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import com.gmail.evgenymoshchin.service.model.CommentDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleServiceImpl(UserRepository userRepository, ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ArticlePageDTO findArticlesWithPagination(int pageNumber, int pageSize) {
        ArticlePageDTO articlePage = new ArticlePageDTO();
        List<Article> articles = articleRepository.findWithPagination(pageNumber, pageSize);
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(convertArticleToDTO(article));
        }
        articleDTOS.sort(Comparator.comparing(ArticleDTO::getDate));
        articlePage.getArticles().addAll(articleDTOS);
        Long countOfArticles = articleRepository.getCount();
        articlePage.setPagesCount(countOfArticles);
        List<Integer> numbersOfPages = getNumbersOfPages(pageSize, countOfArticles);
        articlePage.getNumbersOfPages().addAll(numbersOfPages);
        return articlePage;
    }

    @Override
    @Transactional
    public ArticleDTO findArticleById(Long id) {
        return convertArticleToDTO(articleRepository.findById(id));
    }

    @Override
    @Transactional
    public List<ArticleDTO> findAll() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(convertArticleToDTO(article));
        }
        return articleDTOS;
    }

    @Override
    @Transactional
    public void addArticle(ArticleDTO articleDTO, String username) {
        articleRepository.persist(convertDTOtoArticle(articleDTO, username));
    }

    @Override
    @Transactional
    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id);
        articleRepository.remove(article);
    }

    private ArticleDTO convertArticleToDTO(Article article) {
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
                commentDTOS.add(convertCommentToDTO(comment));
            }
            commentDTOS.sort(Comparator.comparing(CommentDTO::getDate));
            articleDTO.setComments(commentDTOS);
        }
        return articleDTO;
    }

    private Article convertDTOtoArticle(ArticleDTO articleDTO, String username) {
        Article article = new Article();
        article.setName(articleDTO.getName());
        article.setArticleBody(articleDTO.getArticleBody());
        article.setSummary(articleDTO.getSummary());
        article.setCreatedBy(LocalDate.now());
        User user = userRepository.findByUsername(username);
        article.setUser(user);
        if (Objects.nonNull(articleDTO.getComments())) {
            List<Comment> comments = new ArrayList<>();
            List<CommentDTO> commentDTOS = articleDTO.getComments();
            for (CommentDTO commentDTO : commentDTOS) {
                Comment comment = commentRepository.findById(commentDTO.getId());
                comments.add(comment);
            }
            article.setComments(comments);
        }
        return article;
    }

    private CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getCreatedBy());
        commentDTO.setCommentBody(comment.getCommentBody());
        User user = userRepository.findById(comment.getUser().getId());
        commentDTO.setFirstName(user.getFirstName());
        commentDTO.setLastName(user.getLastName());
        return commentDTO;
    }
}
