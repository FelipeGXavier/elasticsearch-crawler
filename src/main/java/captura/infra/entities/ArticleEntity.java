package captura.infra.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class ArticleEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private Long id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String object;

    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
