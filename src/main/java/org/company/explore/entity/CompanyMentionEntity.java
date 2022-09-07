package org.company.explore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("CompanyMention")
public class CompanyMentionEntity implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    private Integer id;
    private String companyName;
    @Builder.Default
    private List<NewsArticleEntity> mentions = new ArrayList<>();
}
