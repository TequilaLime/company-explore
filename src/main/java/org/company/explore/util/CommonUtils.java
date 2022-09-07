package org.company.explore.util;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.company.explore.entity.NewsArticleEntity;
import org.elasticsearch.common.TriFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
@Slf4j
public class CommonUtils {

    @SneakyThrows
    public static List<File> getListOfNewsArticles(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }
    }

    @SneakyThrows
    public static CompletableFuture<NewsArticleEntity> extractNewsArticleFromFile(File file, XmlMapper mapper, UnaryOperator<NewsArticleEntity> supplier) {
        return CompletableFuture.supplyAsync(() -> {
                    log.debug("starting to extract article {}", file.getName());
                    NewsArticleEntity newsArticleEntity;
                    try {
                        newsArticleEntity = mapper.readValue(file, NewsArticleEntity.class);
                    } catch (IOException e) {
                        log.error("error reading news article from file {}: {}", file.getName(), e);
                        return null;
                    }
                    newsArticleEntity.setId(FilenameUtils.removeExtension(file.getName()));
                    return newsArticleEntity;
                })
                .thenApply(articleEntity -> {
                    if (articleEntity != null) {
                        log.debug("saving article {}", file.getName());
                        return supplier.apply(articleEntity);
                    } else {
                        log.debug("article was null, please check error log above {}", file.getName());
                        return null;
                    }
                })
                .exceptionally(e -> {
                    log.error("error extracting news article from file {}: {}", file.getName(), e);
                    return null;
                });
    }

    public static <R> List<R> getAllItems(String searchTerm, int totalLimit, int fetchLimit, TriFunction<String, Integer, Integer, List<R>> triFunction) {
        boolean continueFetching;
        int page = 0;
        List<R> result = new ArrayList<>();
        do {
            List<R> data = getListOrNew(triFunction.apply(searchTerm, fetchLimit, page));
            result.addAll(data);
            page += 1;
            continueFetching = (data.size() == fetchLimit) && (result.size() < totalLimit);
        } while (continueFetching);
        return result;
    }

    public static <T> List<T> getListOrNew(List<T> list) {
        return Optional.ofNullable(list).orElseGet(ArrayList::new);
    }
}
