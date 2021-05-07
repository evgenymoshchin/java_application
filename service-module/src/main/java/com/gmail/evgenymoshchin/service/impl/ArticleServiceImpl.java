package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ArticleRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.User;
import com.gmail.evgenymoshchin.service.ArticleService;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.ArticlePageDTO;
import com.gmail.evgenymoshchin.service.model.UserDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public ArticlePageDTO findArticlesWithPagination(Integer pageNumber, Integer pageSize) {
        ArticlePageDTO articlePage = new ArticlePageDTO();
        List<Article> articles = articleRepository.findWithPagination(pageNumber, pageSize);
        List<ArticleDTO> articleDTOS = new ArrayList<>();
        for (Article article : articles) {
            articleDTOS.add(convertArticleToDTO(article));
        }
        articleDTOS.sort(Comparator.comparing(ArticleDTO::getDate));
        articlePage.getArticles().addAll(articleDTOS);
        Long countOfReviews = articleRepository.getCount();
        articlePage.setPagesCount(countOfReviews);
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, Math.toIntExact(countOfReviews / pageSize + 1))
                .boxed()
                .collect(Collectors.toList());
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
        return articleDTO;
    }

    private Article convertDTOtoArticle(ArticleDTO articleDTO, String username) {
        Article article = new Article();
        article.setName(articleDTO.getName());
        article.setArticleBody(articleDTO.getArticleBody());
        article.setSummary(articleDTO.getSummary());
        User user = userRepository.findByUsername(username);
        article.setUser(user);
//        if (Objects.nonNull(article.getUser())) {
//
//        }
        return article;
    }
}
