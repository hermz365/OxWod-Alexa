package oxwodwebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author herman
 */
public class WebCrawler {

    private static WebCrawler instance;    
    private final WebDriver driver;
    
    private WebCrawler()
    {
        driver = new ChromeDriver(); 
    }
    
    public static WebCrawler getInstance(){
        if(instance == null){
            instance = new WebCrawler();
        }
        return instance;
    }
    
    public void GoToPage(String url) {
        driver.navigate().to(url);
        
        // Check the title of the page
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until((ExpectedCondition<Boolean>) (WebDriver d) -> d.getTitle().contains("WODTime"));
    }
    
    public String GetWod() {
        return driver.findElement(By.cssSelector("div.wod_blurb_small")).getText();
    }
    
    public String GetDate() {
        return driver.findElement(By.cssSelector("div.workout-date")).getText();
    }
    
    public void QuitBrowser()
    {
        //Close the browser
        driver.quit();
    }
}