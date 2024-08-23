package utils;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotCreatorOnFail implements AfterTestExecutionCallback {
    private static WebDriver driver;

    // Метод для установки драйвера
    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
//        // Проверяем, было ли исключение во время выполнения теста
//        if (context.getExecutionException().isPresent()) {
//            Throwable exception = context.getExecutionException().get();
//            String assertComment = "";
//
//            // Если это AssertionError, то добавляем комментарий
//            if (exception instanceof AssertionError) {
//                assertComment = "_" + sanitizeFileName(exception.getMessage());
//            }

            // Создаем уникальное имя файла с названием теста, комментарием и текущим временем
            String screenshotName = context.getDisplayName() + ".png";
            // Указываем путь для сохранения скриншота
            Path screenshotPath = Paths.get("screenshots", screenshotName);
            // Создаем скриншот
            captureScreenshot(driver, screenshotPath);
        }

    // Метод для захвата и сохранения скриншота
    public static void captureScreenshot(WebDriver driver, Path destination) {
        if (driver == null) return;

        // Получаем скриншот
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Создаем директории, если их нет
            Files.createDirectories(destination.getParent());
            // Перемещаем файл скриншота в указанное место
            Files.move(screenshot.toPath(), destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления недопустимых символов из имени файла
    private String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
}
