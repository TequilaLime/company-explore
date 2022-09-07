package org.company.explore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyMention {

    private Integer id;
    private String companyName;
    @Builder.Default
    private List<String> mentions = new ArrayList<>();
}
