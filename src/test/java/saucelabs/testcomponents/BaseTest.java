    package saucelabs.testcomponents;

    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;
    import org.apache.commons.io.FileUtils;
    import org.openqa.selenium.OutputType;
    import org.openqa.selenium.TakesScreenshot;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.openqa.selenium.chrome.ChromeOptions;
    import org.testng.annotations.AfterMethod;
    import org.testng.annotations.BeforeMethod;
    import saucelabs.pageobjects.LoginPage;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.lang.reflect.Type;
    import java.nio.charset.StandardCharsets;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Properties;

    public class BaseTest {

        public WebDriver driver;
        public LoginPage loginPage;


        WebDriver initializeDriver() throws IOException {

            Properties properties = new Properties();
            // String fileLocation = System.getProperty("user.dir"))+"//src//main//java//saucelabs//resources//globalData.properties";
            FileInputStream fis = new FileInputStream((System.getProperty("user.dir"))
                    + "//src//main//java//saucelabs//resources//globalData.properties");
            properties.load(fis);
            String browserName = properties.getProperty("browser");

            if (browserName.equals("chrome")) {
                String chromePath = properties.getProperty("chromeTestPath");
                String chromeDriverPath = properties.getProperty("chromeTestDriverPath");

                ChromeOptions cOptions = new ChromeOptions();
                cOptions.setBinary(chromePath);
                cOptions.addArguments("--remote-allow-origins=*");

                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                driver = new ChromeDriver(cOptions);
            } else if (browserName == "firefox") {
                //firefox initialization code
            } else if (browserName == "edge") {
                //firefox initialization code
            } else {
                System.out.println(browserName + " browser is not configured properly");
            }

            driver.manage().window().maximize();
            return driver;

        }

        @BeforeMethod
        LoginPage launchApplication() throws IOException {
            driver = initializeDriver();
            loginPage = new LoginPage(driver);
            loginPage.openApp();
            return loginPage;
        }

        @AfterMethod
        public void tearDown() {
            /* driver.close();
            faced below error for above code
                org.openqa.selenium.remote.http.WebSocket$Listener onError
                WARNING: Connection reset
                java.net.SocketException: Connection reset

             */
            driver.quit();

        }

        // Read JsonData and store it in HashMap
        public List<HashMap<String, Object>> getDataToMap(String dataFilePath) throws IOException {
            //1. Read json to string
            String jsonContent = FileUtils.
                    readFileToString(new File(dataFilePath), StandardCharsets.UTF_8);

            //2. String to HashMap - Jackson Databind
            ObjectMapper mapper = new ObjectMapper();
            List<HashMap<String, Object>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, Object>>>() {
            });


            //{map, map}
            return data;

        }
    /*
        //read json data and store it in hashmap (using gson)
        void getDataToMapAno(String dataFilePath) throws IOException {
            Type type = new TypeToken<HashMap<String, Object>>() {
            }.getType();
            String jsonContent = FileUtils.
                    readFileToString(new File(dataFilePath), StandardCharsets.UTF_8);
            HashMap<String, Object> jsonMap = new Gson().fromJson(jsonContent,type);

        }

     */
        //take screenshot
        public String takeScreenshot(String testCaseName, WebDriver driver) throws IOException {
            TakesScreenshot ts = (TakesScreenshot) this.driver;
            File screenshotSource = ts.getScreenshotAs(OutputType.FILE);
            File screenshotDestination = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
            FileUtils.copyFile(screenshotSource, screenshotDestination);
            return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
        }
    }