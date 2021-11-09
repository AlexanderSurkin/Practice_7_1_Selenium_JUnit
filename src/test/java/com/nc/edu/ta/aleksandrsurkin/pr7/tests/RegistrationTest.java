package com.nc.edu.ta.aleksandrsurkin.pr7.tests;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;

public class RegistrationTest {
    private static WebDriver driver;
    private String actualText;
    private String expectedText;
    private final String REGISTER_ANSWER = "An email should have been sent to your address. " +
            "It contains easy instructions to complete your registration";
    private final String REGISTERED_EMAIL_ANSWER = "The user with such email address has been already registered. " +
            "Please fill out another email address";

    /**
     * This method is create screenshot of web-page after testing
     * @param file contains name of png-file
     */
    public static void getScreenShot(String file) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("src/test/java/com/nc/edu/ta/aleksandrsurkin/" +
                    "pr7/tests/screenshots/" + file));

        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    /**
     * This method is return web-element 'Registration'
     * @return web-element 'Registration'
     */
    public WebElement registerButtonClick() {
        return driver.findElement(By.name("registerForm:j_idt26"));
    }

    /**
     * This method is search coincidences with sought string
     * @return sought string or empty string
     */
    public String actualRegisterResult() {
        if (driver.getPageSource().contains("An email should have been sent to your address. " +
                "It contains easy instructions to complete your registration")) {
            return this.actualText = REGISTER_ANSWER;
        } else {
            return this.actualText = "";
        }
    }

    /**
     * This method is search coincidences with sought string from 'Username' field
     * @return sought string or empty string
     */
    public String checkCorrectionValueUsername() {
        if (driver.getPageSource().contains("Login must be alphanumeric string with length =&gt; 6 and &lt;= 50.")) {
            return this.actualText = driver.findElement(By.cssSelector("#registerForm > table > tbody > tr:nth-child(1) > " +
                    "td:nth-child(3) > span")).getText();
        } else {
            return this.actualText = "";
        }
    }

    /**
     * This method is search coincidences with sought string from 'Password' field
     * @return sought string or empty string
     */
    public String checkCorrectionValuePassword() {
        if (driver.getPageSource().contains("Please enter password.")) {
            return this.actualText = driver.findElement(By.cssSelector("#registerForm > table > tbody > tr:nth-child(2) > " +
                    "td:nth-child(3) > span")).getText();
        } else {
            return this.actualText = "";
        }
    }

    /**
     * This method is search coincidences with sought string from 'Email' field
     * @return string result of actions from value of email field
     */
    public String valueOfEmailField() {
        return driver.findElement(By.cssSelector("#registerForm > table > tbody > tr:nth-child(5) > " +
                "td:nth-child(3) > span")).getText();
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://inventory.edu-netcracker.com/pages/registration.xhtml");
    }

    @After
    public void afterTest() {
        driver.quit();
    }



    @Test
    public void testCase1_1() throws IOException, InterruptedException {
        //Вводим логин
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr35");
        RegistrationTest.getScreenShot("TC_1.1_Step1.png"); //TODO сделать везде

        //Вводим пароль
        driver.findElement(By.id("registerForm:password")).sendKeys("Password35+");
        RegistrationTest.getScreenShot("TC_1.1_Step2.png");

        //Вводим подтверждение пароля
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password35+");
        RegistrationTest.getScreenShot("TC_1.1_Step3.png");

        //Вводим email
        driver.findElement(By.id("registerForm:email")).sendKeys("userr35@testmail.com");
        RegistrationTest.getScreenShot("TC_1.1_Step4.png");

        //Выбираем в выпадающем меню 'Admin'
        new Select(driver.findElement(By.id("registerForm:role"))).selectByVisibleText("Admin");
        RegistrationTest.getScreenShot("TC_1.1_Step5.png");

        //TODO изменить поиск кнопки по name
        //Нажимаем кнопку 'Register' и ждём 5 секунд
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        //Ищем искомый текст сообщения об успешной регистрации, если его нет, то возвращаем пустую строку
        actualRegisterResult();

        RegistrationTest.getScreenShot("TC_1.1_Step6.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTER_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);


    }

    @Test
    public void testCase2_1() {
        //Не вводим ничего в поле 'Username'
        RegistrationTest.getScreenShot("TC_2.1_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        if (driver.getPageSource().contains("Login must not be empty.")) {
            this.actualText = driver.findElement(By.cssSelector("#registerForm > table > tbody > tr:nth-child(1) > " +
                    "td:nth-child(3) > span")).getText();
        } else {
            this.actualText = "";
        }
        RegistrationTest.getScreenShot("TC_2.1_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "Login must not be empty.";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase2_2() {
        //Вводим логин с превышением 6 символов
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr3+");
        RegistrationTest.getScreenShot("TC_2.2_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click(); //TODO сделать отдеьлоный метод и другой способ поика

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        checkCorrectionValueUsername();

        RegistrationTest.getScreenShot("TC_2.2_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "Login must be alphanumeric string with length => 6 and <= 50.";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase2_3() {
        //Вводим логин с превышением с другими символами, помимо букв и цифр
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr2+");
        RegistrationTest.getScreenShot("TC_2.3_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        checkCorrectionValueUsername();

        RegistrationTest.getScreenShot("TC_2.3_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "Login must be alphanumeric string with length => 6 and <= 50.";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase3_1() {
        //Не вводим ничего в поле 'Password'
        RegistrationTest.getScreenShot("TC_3.1_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();
        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        checkCorrectionValuePassword();

        RegistrationTest.getScreenShot("TC_3.1_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "Please enter password.";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase3_2() {
        //Вводим пароль менее 8 символов
        driver.findElement(By.id("registerForm:password")).sendKeys("Paswd3+");
        RegistrationTest.getScreenShot("TC_3.2_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        checkCorrectionValuePassword();

        RegistrationTest.getScreenShot("TC_3.2_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "Password length must me >= 8 and <= 50.";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase3_3() {
        //Вводим пароль менее 8 символов
        driver.findElement(By.id("registerForm:password")).sendKeys("password");
        RegistrationTest.getScreenShot("TC_3.3_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        if (driver.getPageSource().contains("At least one digit, one letter in uppercase and one " +
                "not alphanumeric symbol must be in password")) {
            this.actualText = "At least one digit, one letter in uppercase and one " +
                    "not alphanumeric symbol must be in password";
        } else {
            this.actualText = driver.findElement(By.cssSelector("#registerForm > table > tbody > tr:nth-child(2) " +
                    "> td:nth-child(3) > span")).getText();
        }
        RegistrationTest.getScreenShot("TC_3.3_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "At least one digit, one letter in uppercase and one " +
                "not alphanumeric symbol must be in password";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase4_1() {
        //Не вводим ничего в поле 'email'
        RegistrationTest.getScreenShot("TC_4.1_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        if (driver.getPageSource().contains("email field can't be empty")) { //TODO првоерить
            this.actualText = valueOfEmailField();
        } else {
            this.actualText = "";
        }
        RegistrationTest.getScreenShot("TC_4.1_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "email field can't be empty";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase4_2() {
        //Вводим email состоящий не из букв, не из цифр, не содержащий ни одного символа '@' и '.'
        driver.findElement(By.id("registerForm:email")).sendKeys("+!'_-com");
        RegistrationTest.getScreenShot("TC_4.2_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ищем искомый текст сообщения о превышении допустимого количества вводимых символов
        if (driver.getPageSource().contains("Email address can contain only letters, digits, minimum one symbol '@' and '.'")) {
            this.actualText = "Email address can contain only letters, digits, minimum one symbol '@' and '.'";
        } else {
            this.actualText = valueOfEmailField();
        }
        RegistrationTest.getScreenShot("TC_4.2_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = "Email address can contain only letters, digits, minimum one symbol '@' and '.'";

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase4_3() throws InterruptedException {
        //Вводим email состоящий не из букв, не из цифр, не содержащий ни одного символа '@' и '.'
        driver.findElement(By.id("registerForm:email")).sendKeys("userr6@testmail.com");
        RegistrationTest.getScreenShot("TC_4.3_Step1.png");

        //Вводим раннее не использованные логин и пароль
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr12");
        driver.findElement(By.id("registerForm:password")).sendKeys("Password12+");
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password12+");
        RegistrationTest.getScreenShot("TC_4.3_Step2.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        //Ищем искомый текст сообщения о превышении допустимого количества вводимых символов
        if (driver.getPageSource().contains("The user with such email address has been already registered. " +
                "Please fill out another email address")) {
            this.actualText = REGISTERED_EMAIL_ANSWER;
        } else {
            this.actualText = "";
        }
        RegistrationTest.getScreenShot("TC_4.3_Step3.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTERED_EMAIL_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase5_1() throws InterruptedException {
        //Вводим раннее не использованные логин, email и пароль
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr16");
        driver.findElement(By.id("registerForm:password")).sendKeys("Password16+");
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password16+");
        driver.findElement(By.id("registerForm:email")).sendKeys("userr16@testmail.com");
        RegistrationTest.getScreenShot("TC_5.1_Step1.png");

        //Ничего не трогаем в поле 'Role'

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        actualRegisterResult();

        RegistrationTest.getScreenShot("TC_5.1_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTER_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase5_2_1() throws InterruptedException {

        //Тестируем форму с выбранным значением "Admin" в поле 'Role'

        //Выбираем в выпадающем меню значение "Admin"
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr29");
        driver.findElement(By.id("registerForm:password")).sendKeys("Password29+");
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password29+");
        driver.findElement(By.id("registerForm:email")).sendKeys("userr29@testmail.com");
        new Select(driver.findElement(By.id("registerForm:role"))).selectByVisibleText("Admin");
        RegistrationTest.getScreenShot("TC_5.2_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        RegistrationTest.getScreenShot("TC_5.2_Step2.png");

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        actualRegisterResult();

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTER_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase5_2_2() throws InterruptedException {

        //Тестируем форму с выбранным значением "Read Only" в поле 'Role'

        //Выбираем в выпадающем меню значение "Read Only"
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr31");
        driver.findElement(By.id("registerForm:password")).sendKeys("Password31+");
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password31+");
        driver.findElement(By.id("registerForm:email")).sendKeys("userr31@testmail.com");
        new Select(driver.findElement(By.id("registerForm:role"))).selectByVisibleText("Read Only");
        RegistrationTest.getScreenShot("TC_5.2_Step3.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        RegistrationTest.getScreenShot("TC_5.2_Step4.png");

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        actualRegisterResult();

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTER_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase5_2_3() throws InterruptedException {

        //Тестируем форму с выбранным значением "Read / Write" в поле 'Role'

        //Выбираем в выпадающем меню значение "Read / Write"
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr28");
        driver.findElement(By.id("registerForm:password")).sendKeys("Password28+");
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password28+");
        driver.findElement(By.id("registerForm:email")).sendKeys("userr28@testmail.com");
        new Select(driver.findElement(By.id("registerForm:role"))).selectByVisibleText("Read / Write");
        RegistrationTest.getScreenShot("TC_5.2_Step5.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        RegistrationTest.getScreenShot("TC_5.2_Step6.png");

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        actualRegisterResult();

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTER_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);

    }

    @Test
    public void testCase6_1() throws InterruptedException {
        //Вводим раннее не использованные логин, email и пароль
        driver.findElement(By.id("registerForm:username")).sendKeys("Userr18");
        driver.findElement(By.id("registerForm:password")).sendKeys("Password18+");
        driver.findElement(By.id("registerForm:confirmPassword")).sendKeys("Password18+");
        driver.findElement(By.id("registerForm:email")).sendKeys("userr18@testmail.com");
        RegistrationTest.getScreenShot("TC_6.1_Step1.png");

        //Нажимаем кнопку 'Register'
        registerButtonClick().click();

        //Ждём 5 секунд
        TimeUnit.SECONDS.sleep(5);

        //Ищем искомый текст сообщения, если его нет, то возвращаем пустую строку
        actualRegisterResult();

        RegistrationTest.getScreenShot("TC_6.1_Step2.png");

        //Присваиваем ожидаемую строку в ожидаемый текст
        this.expectedText = REGISTER_ANSWER;

        //Сравниваем две строки, ожидаемую и фактическую
        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void testCase7_1() {
        //Скриншот успешного открытия страницы
        String expectedUrl = "https://inventory.edu-netcracker.com/pages/registration.xhtml";
        RegistrationTest.getScreenShot("TC_7.1_Step1.png");

        Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void testCase7_2() {
        //Проверяем что протокол HTTPS
        String expectedUrl = "https://inventory.edu-netcracker.com/pages/registration.xhtml";
        if (driver.getCurrentUrl().contains("https")) {
            String actualResult = "The URL protocol is HTTPS";
        } else {
            String actualResult = "The URL protocol is not HTTPS";
        }
        Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
        RegistrationTest.getScreenShot("TC_7.2_Step1.png");
    }
}
