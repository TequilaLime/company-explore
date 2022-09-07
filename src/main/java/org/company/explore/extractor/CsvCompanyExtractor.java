package org.company.explore.extractor;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.util.List;

@Slf4j
@Data
public class CsvCompanyExtractor {

    private final String path;

    private final CsvToBean<CsvCompanyExtractor.Company> companyCsvToBean;

    public CsvCompanyExtractor(String path) {
        this.path = path;
        this.companyCsvToBean = createDefaultBean();
    }

    @SneakyThrows
    private CsvToBean<CsvCompanyExtractor.Company> createDefaultBean() {
        return new CsvToBeanBuilder<CsvCompanyExtractor.Company>(new FileReader(getPath())).withType(CsvCompanyExtractor.Company.class).withSeparator(';').withSkipLines(1).build();
    }


    /**
     * Designed to be used once in the application context.
     * Should you use multiple times, create additional object of this instance.
     *
     * @return
     */
    public List<CsvCompanyExtractor.Company> getCompaniesListFromPath() {
        return companyCsvToBean.parse();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Company {
        @CsvBindByPosition(position = 0)
        private Integer id;
        @CsvBindByPosition(position = 1)
        private String name;
    }
}
