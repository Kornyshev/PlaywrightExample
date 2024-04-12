package org.example.tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Feature;
import org.example.pom.TodoMainPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <a href="https://playwright.dev/java/docs/writing-tests">Link to Playwright documentation</a>
 * <p>
 * <p>
 * This project test demo page: <a href="https://demo.playwright.dev/todomvc/">TodoMVC demo app</a>
 */
@Feature("ToDo App Main Page tests")
public class TodoMainPageTests {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private TodoMainPage todoMainPage;

    @BeforeAll
    public static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    public static void tearDownAll() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    public void setup() {
        context = browser.newContext();
        Page page = context.newPage();
        page.navigate("https://demo.playwright.dev/todomvc");
        todoMainPage = new TodoMainPage(page);
    }

    @Test
    @DisplayName("Test main title and To Do section")
    public void testMainTitleAndTodoSection() {
        assertEquals("todos", todoMainPage.getMainHeaderText());
        todoMainPage.typeTodoTextAndSubmit("Some ToDo");
        assertTrue(todoMainPage.isTodoSectionDisplayed());
    }

    @Test
    @DisplayName("Test adding To Do")
    public void testAddTodo() {
        String expectedText = "Write JUnit test";
        todoMainPage.typeTodoTextAndSubmit(expectedText);
        assertTrue(todoMainPage.getListOfTodoLabels().contains(expectedText));
    }

    @Test
    @DisplayName("Test To Do completion")
    public void testCompleteTodo() {
        String firstTask = "First Task";
        String secondTask = "Second Task";
        todoMainPage.typeTodoTextAndSubmit(firstTask);
        todoMainPage.typeTodoTextAndSubmit(secondTask);
        todoMainPage.findTodoByLabelAndComplete(firstTask);
        assertTrue(todoMainPage.isTodoByTextCompleted(firstTask));
    }

    @Test
    @DisplayName("Test To Do deletion")
    public void testDeleteTodo() {
        todoMainPage.typeTodoTextAndSubmit("Delete this task");
        assertFalse(todoMainPage.getListOfTodoLabels().isEmpty());
        todoMainPage.findTodoByTextAndDelete("Delete this task");
        assertTrue(todoMainPage.getListOfTodoLabels().isEmpty());
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }

}
