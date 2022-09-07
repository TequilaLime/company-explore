package org.company.explore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "article")
@Data
@Builder
public class NewsArticleEntity implements NewsArticleSearchable {
    @Id
    @JsonIgnore
    private String id;

    @Field(type = FieldType.Text, name = NewsArticleSearchable.TEXT)
    private String title;
    @Field(type = FieldType.Text, name = NewsArticleSearchable.SOURCE)
    private String source;
    @Field(type = FieldType.Text, name = NewsArticleSearchable.AUTHOR)
    private String author;
    @Field(type = FieldType.Text, name = NewsArticleSearchable.TEXT)
    private String text;
    @Field(type = FieldType.Date, name = NewsArticleSearchable.DATE)
    private LocalDate date;

    // had to add this setter, as during IntegrationTest there is an error: No fallback setter/field defined for creator property 'id'
    @JsonProperty
    public void setId(String id) {
        this.id = id;
    }
}
