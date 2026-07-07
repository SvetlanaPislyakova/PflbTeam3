package tests;

import api.adapters.HouseAdapter;
import api.models.CarRq;
import api.models.CarRs;
import api.models.HouseRq;
import api.models.HouseRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.dto.Car;
import ui.dto.User;
import ui.pages.AllPostPage;

import java.math.BigDecimal;
import java.util.List;

public class AllPostTest extends BaseTest {

    private final AllPostPage allPostPage = new AllPostPage();
    private final HouseAdapter houseAdapter = new HouseAdapter();

    @BeforeEach
    public void openAllPost() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        allPostPage.openPage()
                .isPageOpened();
    }

    @Test
    @DisplayName("All POST - отображение всех POST-форм")
    public void checkAllPost() {
        allPostPage.isPageOpened();
    }

    @Test
    @DisplayName("All POST - создание дома")
    public void createHouseFromAllPostPage() {
        allPostPage.createHouse(3, BigDecimal.valueOf(1_000_000));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateHouseStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateHouseStatusCode()).isEqualTo(201);
            softly.assertThat(allPostPage.getCreateHouseId()).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - создание автомобиля")
    public void createCarFromAllPostPage() {
        Car car = Car.builder()
                .engineType("Gasoline")
                .mark("Toyota")
                .model("Corolla")
                .price(BigDecimal.valueOf(1_000))
                .build();

        allPostPage.createCar(car);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateCarStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateCarStatusCode()).isEqualTo(201);
            softly.assertThat(allPostPage.getCreateCarId()).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - создание пользователя")
    public void createUserFromAllPostPage() {
        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .age(30)
                .sex("MALE")
                .money(BigDecimal.valueOf(1_000))
                .build();

        allPostPage.createUser(user);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateUserStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateUserStatusCode()).isEqualTo(201);
            softly.assertThat(allPostPage.getCreateUserId()).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - создание пользователя женского пола")
    public void createFemaleUserFromAllPostPage() {
        User user = User.builder()
                .firstName("Anna")
                .lastName("Ivanova")
                .age(25)
                .sex("FEMALE")
                .money(BigDecimal.valueOf(1_000))
                .build();

        allPostPage.createUser(user);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateUserStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateUserStatusCode()).isEqualTo(201);
            softly.assertThat(allPostPage.getCreateUserId()).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - начисление денег пользователю")
    public void addMoneyFromAllPostPage() {
        UserRq userRq = UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        BigDecimal amount = BigDecimal.valueOf(500);

        allPostPage.addMoneyToUser(userId, amount);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getAddMoneyStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getAddMoneyStatusCode()).isEqualTo(200);
            softly.assertThat(allPostPage.getAddMoneyResult())
                    .isEqualTo(userRq.getMoney().add(amount).doubleValue());
        });
    }

    @Test
    @DisplayName("All POST - покупка автомобиля")
    public void buyOrSellCarFromAllPostPage() {
        UserRq userRq = UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(100_000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        CarRq carRq = CarRq.builder()
                .engineType("Gasoline")
                .mark("Toyota")
                .model("Corolla")
                .price(BigDecimal.valueOf(1_000))
                .build();
        CarRs carRs = carAdapter.createCar(carRq, token);

        allPostPage.buyCarForUser(userId, carRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getBuyOrSellCarStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getBuyOrSellCarStatusCode()).isEqualTo(200);
        });
    }

    @Test
    @DisplayName("All POST - продажа автомобиля")
    public void sellCarFromAllPostPage() {
        UserRq userRq = UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(100_000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        CarRq carRq = CarRq.builder()
                .engineType("Gasoline")
                .mark("Toyota")
                .model("Corolla")
                .price(BigDecimal.valueOf(1_000))
                .build();
        CarRs carRs = carAdapter.createCar(carRq, token);

        allPostPage.buyCarForUser(userId, carRs.getId());
        allPostPage.openPage().isPageOpened();
        allPostPage.sellCarForUser(userId, carRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getBuyOrSellCarStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getBuyOrSellCarStatusCode()).isEqualTo(200);
        });
    }

    @Test
    @DisplayName("All POST - заселение пользователя в дом")
    public void settleUserToHouseFromAllPostPage() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build(), token);
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq, token);

        allPostPage.settleUserToHouse(userId, houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getSettleOrEvictStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getSettleOrEvictStatusCode()).isEqualTo(200);
        });
    }

    @Test
    @DisplayName("All POST - выселение пользователя из дома")
    public void evictUserFromHouseFromAllPostPage() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build(), token);
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq, token);
        houseAdapter.settleUser(houseRs.getId(), userId, token);

        allPostPage.evictUserFromHouse(userId, houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getSettleOrEvictStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getSettleOrEvictStatusCode()).isEqualTo(200);
        });
    }
}
