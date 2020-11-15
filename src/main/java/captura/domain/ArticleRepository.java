package captura.domain;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDateTime;

public interface ArticleRepository {

    @SqlUpdate("INSERT INTO article (content, url, created_at)\n" +
            "VALUES (:content, :url, :created) ON CONFLICT (url) DO\n" +
            "UPDATE\n" +
            "SET content = :content;")
    void create(@Bind("content") String object, @Bind("url") String url, @Bind("created") LocalDateTime created);
}
