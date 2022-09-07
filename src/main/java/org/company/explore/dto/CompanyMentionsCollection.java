package org.company.explore.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyMentionsCollection {
    private long totalCount;
    private List<CompanyMention> items;
}
