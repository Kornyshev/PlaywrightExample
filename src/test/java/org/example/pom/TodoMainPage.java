package org.example.pom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.pom.elements.TodoElement;

import java.util.List;

public class TodoMainPage {
    private static final String MAIN_TITLE = "header.header h1";
    private static final String TODO_INPUT = "header.header input";
    private static final String CREATED_TODO_AREA = "section.main";
    private static final String LIST_OF_CREATED_TODOS = "ul.todo-list li";
    private final Page page;

    public TodoMainPage(Page page) {
        this.page = page;
    }

    @Step("Get main header on Main Page")
    public String getMainHeaderText() {
        return page.locator(MAIN_TITLE).textContent();
    }

    @Step("Is To Do section displayed on Main Page")
    public boolean isTodoSectionDisplayed() {
        return page.locator(CREATED_TODO_AREA).isVisible();
    }

    @Step("Type text and submit To Do on Main Page")
    public void typeTodoTextAndSubmit(String todoText) {
        Locator todoInput = page.locator(TODO_INPUT);
        todoInput.fill(todoText);
        todoInput.press("Enter");
    }

    @Step("Get list of To Do from Main Page")
    public List<String> getListOfTodoLabels() {
        return page.locator(LIST_OF_CREATED_TODOS).allTextContents();
    }

    @Step("Find To Do by label text and complete on Main Page")
    public void findTodoByLabelAndComplete(String label) {
        getTodoElementByLabelText(label).clickToggleIcon();
    }

    @Step("Find To Do by label text and delete on Main Page")
    public void findTodoByTextAndDelete(String label) {
        getTodoElementByLabelText(label).clickCrossIcon();
    }

    @Step("Is To Do by label text completed on Main Page")
    public boolean isTodoByTextCompleted(String label) {
        return getTodoElementByLabelText(label).isTodoCompleted();
    }

    private TodoElement getTodoElementByLabelText(String label) {
        return page.locator(LIST_OF_CREATED_TODOS).all().stream()
                .map(TodoElement::new).filter(elem -> elem.getLabelText().equals(label))
                .findFirst().orElseThrow();
    }

}
