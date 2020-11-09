package captura.converters;

import captura.domain.Article;
import captura.domain.ArticleObject;
import captura.domain.ArticleUrl;
import captura.entities.ArticleEntity;

import java.time.LocalDateTime;

public class ArticleConverter {

    public static Article toDomainArticle(ArticleEntity entity) {
        ArticleUrl url = ArticleUrl.of(entity.getUrl());
        var object = ArticleObject.of(entity.getObject());
        return Article.of(object, url);
    }

    public static ArticleEntity toEntityArticle(Article article) {
        ArticleEntity entity = new ArticleEntity();
        entity.setObject(article.getTextObject());
        entity.setUrl(article.getTextUrl());
        entity.setTimestamp(LocalDateTime.now());
        return new ArticleEntity();
    }
}
