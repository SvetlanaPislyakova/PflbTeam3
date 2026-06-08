package pages;

import dto.User;
import wrappers.Input;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CreateUserPage extends BasePage {

    @Override
    public CreateUserPage openPage() {
        open(baseUrl + "#/create/user");
        return this;
    }

    @Override
    public CreateUserPage isPageOpened() {
        $$x("//table").shouldHave(size(1));
        $(withText("User ID")).shouldBe(visible);
        $(withText("ID will be generated")).shouldBe(visible);
        return this;
    }

    public CreateUserPage fillTableCreateUser(User user) {
        new Input("Create new user", "First Name").write(user.getFirstName());
        new Input("Create new user", "Last Name").write(user.getLastName());
        new Input("Create new user", "Age").write(user.getAge());
        new Input("Create new user", "Money").write(user.getMoney());
        $x("//*[text()='FEMALE']/input").click(); // Гаяз сделает радиобаттон
        return this;
    }

    public CreateUserPage pushToApiBtnClick() {
        $(withText("PUSH")).click(); // Гаяз сделает кнопку
        return this;
    }

    public int getUserId () {
        $x("//*[contains(text(), 'Successfully pushed')]").shouldBe(visible);
        String message = $x("//*[contains(text(), 'Successfully pushed')]/following::button").getText();
        return Integer.parseInt(message.split(": ")[1]);
    }
}
