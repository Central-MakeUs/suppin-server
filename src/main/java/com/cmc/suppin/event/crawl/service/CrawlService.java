package com.cmc.suppin.event.crawl.service;

import com.cmc.suppin.event.crawl.controller.dto.CrawlResponseDTO;
import com.cmc.suppin.event.crawl.domain.Comment;
import com.cmc.suppin.event.crawl.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CrawlService {

    private final CommentRepository commentRepository;

    public List<CrawlResponseDTO.CrawlResultDTO> crawlYoutubeComments(String url, String userId) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.get(url);

        List<CrawlResponseDTO.CrawlResultDTO> commentList = new ArrayList<>();
        Set<String> uniqueComments = new HashSet<>();

        try {
            Thread.sleep(5000);

            long endTime = System.currentTimeMillis() + 120000;
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            while (System.currentTimeMillis() < endTime) {
                jsExecutor.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
                Thread.sleep(1000);

                String pageSource = driver.getPageSource();
                Document doc = Jsoup.parse(pageSource);
                Elements comments = doc.select("ytd-comment-thread-renderer");

                for (Element commentElement : comments) {
                    String author = commentElement.select("#author-text span").text();
                    String text = commentElement.select("#content yt-attributed-string#content-text").text();
                    String time = commentElement.select("#header-author #published-time-text").text().replace("(수정됨)", "");

                    if (!uniqueComments.contains(text)) {
                        uniqueComments.add(text);
                        commentList.add(new CrawlResponseDTO.CrawlResultDTO(author, text, time));

                        // 엔티티 저장
                        Comment comment = Comment.builder()
                                .author(author)
                                .commentText(text)
                                .commentDate(time)
                                .url(url)
                                .build();
                        commentRepository.save(comment);
                    }
                }
            }

            return commentList;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            driver.quit();
        }
    }
}
