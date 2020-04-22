package com.humuson.imc.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ImcCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImcCrawlerApplication.class, args);
	}

	private WebDriver driver;

	@EventListener(classes = { ApplicationReadyEvent.class })
	public void init() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		driver.get("https://eclogin.cafe24.com/Shop/");
		driver.findElement(By.id("mall_id")).click();
		driver.findElement(By.id("mall_id")).clear();
		driver.findElement(By.id("mall_id")).sendKeys("asdf");
		driver.findElement(By.id("userpasswd")).click();
		driver.findElement(By.id("userpasswd")).clear();
		driver.findElement(By.id("userpasswd")).sendKeys("asdf!");
		driver.findElement(By.linkText("로그인")).click();

		List<WebElement> exists = driver.findElements(By.id("iptBtnEm"));
		if (!exists.isEmpty()) {
			exists.get(0).click();
		}
		driver.findElement(By.xpath("//A[@id='QA_Gnb_member']")).click();
		WebElement tbody = driver.findElement(By.xpath("//TBODY[@class='center']"));
		List<WebElement> trs = tbody.findElements(By.tagName("tr"));

		for (WebElement tr : trs) {
			List<WebElement> tds = tr.findElements(By.tagName("td"));

			for (WebElement td : tds) {
				System.out.print(td.getText() + " / ");
			}
			System.out.println();
		}
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("destroy web driver");
		if (driver != null) {
			driver.quit();
		}
	}

}
