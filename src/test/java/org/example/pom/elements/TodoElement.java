package org.example.pom.elements;

import com.microsoft.playwright.Locator;

public class TodoElement {

    Locator root;

    public TodoElement(Locator root) {
        this.root = root;
    }

    public String getLabelText() {
        return root.locator("label[data-testid='todo-title']").textContent();
    }

    public boolean isTodoCompleted() {
        return root.getAttribute("class").equals("completed");
    }

    public void clickToggleIcon() {
        root.locator("input.toggle").click();
    }

    public void clickCrossIcon() {
        root.hover();
        root.locator("button.destroy").click();
    }

}
