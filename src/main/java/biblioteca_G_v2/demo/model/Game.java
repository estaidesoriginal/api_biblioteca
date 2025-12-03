package biblioteca_G_v2.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonProperty("image_url")
    @Column(name = "image_url")
    private String imageUrl;

    @ElementCollection
    private List<String> tags;

    @JsonProperty("external_links")
    @ElementCollection
    @CollectionTable(name = "game_links", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "link")
    private List<String> externalLinks;

    @JsonProperty("protection_status_id")
    @Column(name = "protection_status_id")
    private int protectionStatusId;

    public Game() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public List<String> getExternalLinks() { return externalLinks; }
    public void setExternalLinks(List<String> externalLinks) { this.externalLinks = externalLinks; }
    public int getProtectionStatusId() { return protectionStatusId; }
    public void setProtectionStatusId(int protectionStatusId) { this.protectionStatusId = protectionStatusId; }
}