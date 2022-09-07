package org.company.explore.extractor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.company.explore.util.CommonUtils;

import java.io.File;
import java.util.List;

@Slf4j
@Data
public class NewsArticleExtractor {

    private final String path;
    private final XmlMapper xmlMapper;

    public NewsArticleExtractor(String path) {
        this.path = path;
        this.xmlMapper = createDefaultXmlMapper();
    }

    private XmlMapper createDefaultXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.findAndRegisterModules();
        return mapper;
    }

    public List<File> getNewsArticlesFromPath() {
        return CommonUtils.getListOfNewsArticles(getPath());
    }
}
