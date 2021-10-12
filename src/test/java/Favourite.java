import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;

public class Favourite {

    protected WebDriver driver;
    public static String loginUrl = "https://www.n11.com/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:/Users/testinium/IdeaProjects/N11/drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }


    public void login(String username, String password){
        driver.get(loginUrl);
        //Anasayfa açıldığı kontrolü
        WebElement srcBtn = Util.getWebElmbyID(driver, Elements.SEARCH_DATA_ID);
        Assert.assertTrue(srcBtn.isDisplayed());
        System.out.println("Anasayfa başarılı bir şekilde açıldı.");

        Util.getWebElmbyClass(driver, Elements.GIRIS_YAP_BTN_CLASS).click();
        Util.getWebElmbyID(driver, Elements.EMAIL_ID).click();
        Util.getWebElmbyID(driver, Elements.EMAIL_ID).sendKeys(username);
        Util.getWebElmbyID(driver, Elements.PASSWORD_ID).click();
        Util.getWebElmbyID(driver, Elements.PASSWORD_ID).sendKeys(password);
        Util.getWebElmbyXpath(driver, Elements.LOGIN_XPATH).click();

    }

    @Test
    public void favourite() {
        login("aysumuratoglu@gmail.com","test1234");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(Elements.SEARCH_DATA_ID)));

        Util.getWebElmbyID(driver, Elements.SEARCH_DATA_ID).click();
        Util.getWebElmbyID(driver, Elements.SEARCH_DATA_ID).sendKeys("Samsung");
        Util.getWebElmbyXpath(driver, Elements.SEARCH_BTN_XPATH).click();

        //Samsung'a ait ürünlerin listelendiği kontrolü
        String resultText = driver.findElement(By.className("resultText")).getText();
        boolean textContains = resultText.contains("Samsung");
        Assert.assertTrue(textContains);
        System.out.println("Samsung'a ait ürünler başarılı bir şekilde listelendi.");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(Elements.PAGE_BTN_XPATH)));

        Util.getWebElmbyXpath(driver, Elements.PAGE_BTN_XPATH).click();

        // 2. Sayfada olduğunun kontrolü
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://www.n11.com/arama?q=Samsung&pg=2");
        System.out.println("Arama sonuçlarının ikinci sayfası görüntülenmektedir.");

        JavascriptExecutor js4 = ((JavascriptExecutor) driver);
        js4.executeScript("window.scrollTo(0, 700)");

        WebElement favUrun = Util.getWebElmbyXpath(driver, Elements.URUN_3_XPATH);
        String urunAdi =  favUrun.getText();

        Util.getWebElmbyXpath(driver, Elements.FAV_EKLE_BTN_XPATH).click();

        //Sayfada yukarı scroll
        JavascriptExecutor js3 = ((JavascriptExecutor) driver);
        js3.executeScript("window.scrollTo(0, -350)");

        //Hesabım alanına mouse move
        WebElement menu = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div[2]/div[2]/div[2]/div[1]/a[1]"));
        Actions action = new Actions(driver);
        action.moveToElement(menu).perform();

        //Menüden Favorilerime tıkla
        Util.getWebElmbyXpath(driver, Elements.FAV_MENU_XPATH).click();
        //Favorilerim'e tıkla
        Util.getWebElmbyClass(driver, Elements.FAV_PAGE_CLASS).click();

        //Ürünün Favorilere eklendiği kontrolü
        WebElement favUrun2 = Util.getWebElmbyXpath(driver, Elements.FAV_URUN_XPATH);
        String urunAdi2 =  favUrun2.getText();
        Assert.assertEquals(urunAdi2, urunAdi);

        //Favori Ürünü sil
        Util.getWebElmbyXpath(driver, Elements.DEL_FAV_XPATH).click();
        Util.getWebElmbyClass(driver, Elements.POPUP_EXIT_CLASS).click();
        driver.navigate().refresh();

        //Favorilerden silindiği kontrolü
        boolean sonuc  = driver.findElements(By.xpath(Elements.FAV_URUN_XPATH)).isEmpty();
        System.out.println("sonuc:" + sonuc);
        Assert.assertTrue(sonuc);
        System.out.println("Ürün Favorilerde bulunmamaktadır");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}