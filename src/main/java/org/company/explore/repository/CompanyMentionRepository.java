package org.company.explore.repository;

import org.company.explore.entity.CompanyMentionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyMentionRepository extends PagingAndSortingRepository<CompanyMentionEntity, Integer> {
}
