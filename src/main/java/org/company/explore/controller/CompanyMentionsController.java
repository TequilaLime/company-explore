package org.company.explore.controller;


import lombok.RequiredArgsConstructor;
import org.company.explore.dto.CompanyMentionsCollection;
import org.company.explore.service.CompanyMentionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company-mentions")
@RequiredArgsConstructor
public class CompanyMentionsController {
    private final CompanyMentionService companyMentionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyMentionsCollection getCompanyMentions(@RequestParam Integer page, @RequestParam Integer size) {
        return companyMentionService.getCompanyMentions(page, size);
    }
}
