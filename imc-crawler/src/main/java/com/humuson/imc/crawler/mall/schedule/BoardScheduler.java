package com.humuson.imc.crawler.mall.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.crawler.model.BoardMessage;
import com.humuson.imc.crawler.template.TemplateUtils;
import com.humuson.imc.crawler.template.code.Board;
import com.humuson.imc.crawler.template.code.Mall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class BoardScheduler extends CrawlerBaseScheduler {

    private final ConcurrentHashMap<String, BoardMessage> boardMap;
    private final ObjectMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected String parseShopInfo(ChromeDriver driver) {
        // 상점관리 메뉴
        driver.findElement(By.id("QA_Gnb_store")).click();
        // 기본정보관리 메뉴
        driver.findElement(By.id("QA_Lnb_Menu10")).click();
        // 내쇼핑몰 정보 메뉴
        driver.findElement(By.id("QA_Lnb_Menu11")).click();

        String template = TemplateUtils.BOARD_ANSWER_TEMPLATE;

        // 쇼핑몰 이름
        String shopName = driver.findElement(By.name("mall_name")).getAttribute("value");
        // 대표 휴대전화
        String phoneNumber = driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[5]/td/input")).getAttribute("value");
        // 도메인
        String mallUrl = driver.findElement(By.xpath("//div[@id='QA_myShop1']/div[2]/table/tbody/tr[7]/td")).getText();

        template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_NAME.getRegex(), shopName);
        template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_TEL_NUMBER.getRegex(), phoneNumber);
        template = TemplateUtils.replaceTemplateVariable(template, Mall.MALL_URL.getRegex(), mallUrl);

        return template;
    }

    @Override
    protected void navigateTemplateMenu(ChromeDriver driver) {
        // 게시판관리 메뉴 클릭
        driver.findElement(By.id("QA_Gnb_board")).click();
        // 게시물 관리 메뉴 클릭
        driver.findElement(By.id("QA_Lnb_Menu390")).click();
    }

    @Override
    protected void searchCondition(ChromeDriver driver) {
        // 작성일 3일전 부터 검색
        driver.findElement(By.cssSelector("#frm > div:nth-child(13) > div.mBoard.gSmall.typeSearch > table > tbody > tr:nth-child(1) > td > a:nth-child(2)")).click();
        // 답변이 완료된 것만 검색 (왜 일부는 안보일까.. 우선 전체검색)
//        driver.findElement(By.id("is_reply4")).click();
        // option:nth-child(N): N==1 ? 10개씩 보기 / N==2? 20개씩 보기 / N==3? 30개씩 보기 / N==4? 50개씩 보기 / N==5? 100개씩 보기
        driver.findElement(By.cssSelector("#list_limit > option:nth-child(1)")).click();
        // 검색버튼 클릭
        driver.findElement(By.xpath("//a[@id='eBtnSearch']/span")).click();
    }

    @Scheduled(cron = "${imc.crawler.scheduled.board}")
    @Override
    protected void schedule() {
        if (super.isReady()) {
            log.info("[UNREADY BOARD SCHEDULER...]");
            return;
        }

        String mallId = "";
        String pw = "";
        ChromeDriver driver = super.getChromeDriver(mallId, pw);

        log.info("[TRY BOARD SCHEDULER...]");

        synchronized (driver) {
            String template = this.parseShopInfo(driver);

            this.navigateTemplateMenu(driver);
            this.searchCondition(driver);

            // 메인 페이지
            String parentWindow = driver.getWindowHandle();
            log.info("BEFORE SWITCHING WINDOW: {}, TITLE: {}", parentWindow, driver.getTitle());

            WebElement pageElement = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
            List<WebElement> liTags = pageElement.findElements(By.tagName("li"));

            int l = 0;
            while (l < liTags.size()) {
                WebElement tmpPageElement = driver.findElement(By.xpath("//DIV[@class='mPaginate']/ol"));
                liTags = tmpPageElement.findElements(By.tagName("li"));

                if (l > 0) {
                    liTags.get(l).click();
                }

                WebElement tbody = driver.findElement(By.xpath("//TBODY[@class='center']"));
                List<WebElement> trs = tbody.findElements(By.tagName("tr"));

                for (int i=1; i <= trs.size(); i++) {
                    String answerStatus = driver.findElement(By.cssSelector("#frm > div:nth-child(14) > div.mBoard.gScroll.gCell.typeList > table > tbody > tr:nth-child(" + i + ") > td:nth-child(7)")).getText();
                    if ("답변완료".equals(answerStatus)) {
                        driver.findElement(By.cssSelector("#frm > div:nth-child(14) > div.mBoard.gScroll.gCell.typeList > table > tbody > tr:nth-child(" + i + ") > td:nth-child(10) > a")).click();
                        String registeredDate = driver.findElement(By.cssSelector("#frm > div:nth-child(14) > div.mBoard.gScroll.gCell.typeList > table > tbody > tr:nth-child(" + i + ") > td:nth-child(12)")).getText();

                        for (String window : driver.getWindowHandles()) {
                            // 새로 띄운 창으로 포커스 변경
                            if (!window.equals(parentWindow)) {
                                driver.switchTo().window(window);
                                log.info("AFTER SWITCHING WINDOW: {}, TITLE: {}", window, driver.getTitle());
                                break;
                            }
                        }

                        String userId = driver.findElement(By.cssSelector("#QA_main1 > div.mBoard > table > tbody > tr:nth-child(1) > td")).getText();
                        String username = driver.findElement(By.cssSelector("#QA_main1 > div.mBoard > table > tbody > tr:nth-child(4) > td")).getText();
                        String phoneNumber = driver.findElement(By.cssSelector("#QA_main1 > div.mBoard > table > tbody > tr:nth-child(8) > td")).getText();

                        log.info("[PARSE DATA] MALL_ID: {}, USER_ID: {}, USERNAME: {}, PHONE: {}, REGISTERED_DATE: {}", mallId, userId, username, phoneNumber, registeredDate);

                        String contents = template;
                        contents = TemplateUtils.replaceTemplateVariable(contents, Board.BOARD_NAME.getRegex(), username);

                        // 새로 띄운 창 닫음
                        driver.close();
                        // 메인 페이지로 포커스 이동
                        driver.switchTo().window(parentWindow);
                        log.info("BACK TO PARENT WINDOW: {}, TITLE: {}", driver.getWindowHandle(), driver.getTitle());

                        BoardMessage message = new BoardMessage();
                        message.setMallId(mallId);
                        message.setUserId(userId);
                        message.setUsername(username);
                        message.setPhoneNumber(phoneNumber);
                        message.setRegisteredDate(registeredDate);
                        message.setMessage(contents);

                        String hashKey = super.generateHashKey(message.getMallId(), message.getUserId(), message.getRegisteredDate());

                        if (boardMap.containsKey(hashKey)) {
                            continue;
                        }

                        if (TemplateUtils.isFinishedTemplate(contents)) {
                            log.info("\nFINISHED TEMPLATE:\n{}", contents);
                        } else {
                            log.error("\nUNFINISHED TEMPLATE:\n{}", contents);
                        }

                        try {
                        // 발송

                        // 발송된건 메모리에 올림 ( +redis)
                            String key = "CRAWLER-BOARD";
                            redisTemplate.opsForHash().put(key, hashKey, mapper.writeValueAsString(message));
//                            redisTemplate.expire(key, 120, TimeUnit.SECONDS);
                            boardMap.put(hashKey, message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                log.info("PAGE# {}", l++);

                // N 개의 페이지 번호를 전부 클릭했고, Next 버튼이 활성화된 경우
                if (l >= liTags.size()) {
                    String href = driver.findElement(By.cssSelector("#frm > div:nth-child(14) > div.mPaginate > a.next")).getAttribute("href");

                    if (!href.contains("#none")) {
                        driver.findElement(By.cssSelector("#frm > div:nth-child(14) > div.mPaginate > a.next")).click();
                        l = 0;
                        log.info("[CLICK THE NEXT BUTTON]");
                    }
                }
            }

        }
    }
}
