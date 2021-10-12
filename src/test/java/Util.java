import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Util {

    public static WebElement getWebElmbyID(WebDriver wd, String id){
        return wd.findElement(By.id(id));
    } 
    public static WebElement getWebElmbyXpath(WebDriver wd, String xpath){
        return wd.findElement(By.xpath(xpath));
    }
    public static WebElement getWebElmbyClass(WebDriver wd, String className){
        return wd.findElement(By.className(className));
    }
}
