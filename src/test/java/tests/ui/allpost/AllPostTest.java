package tests.ui.allpost;

import api.adapters.HouseAdapter;
import api.models.car.CarRq;
import api.models.car.CarRs;
import api.models.house.HouseRq;
import api.models.house.HouseRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import api.models.user.UserRs;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.ui.base.BaseTest;
import ui.dto.Car;
import ui.dto.House;
import ui.dto.HouseFactory;
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
    @Description("Открываем страницу All POST и проверяем, что отображаются все формы для POST-запросов")
    @Owner("OlgaBaidalina")
    public void checkAllPost() {
        allPostPage.isPageOpened();
    }

    @Test
    @DisplayName("All POST - создание дома")
    @Description("В форме Create new house вводим валидные данные дома, ожидаем успешное создание с кодом 201 и проверяем наличие дома в БД")
    @Owner("OlgaBaidalina")
    public void createHouseFromAllPostPage() {
        House house = House.builder()
                .floors(3)
                .price(BigDecimal.valueOf(1_000_000))
                .warmCoveredParking(1)
                .warmNotCoveredParking(0)
                .coldCoveredParking(0)
                .coldNotCoveredParking(0)
                .build();

        allPostPage.createHouse(house);
        Integer houseId = allPostPage.getCreateHouseId();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateHouseStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateHouseStatusCode()).isEqualTo(201);
            softly.assertThat(houseId).isPositive();
            softly.assertThat(dbSteps.isHouseExistsInDB(houseId)).isTrue();
        });
    }

    @Test
    @DisplayName("All POST - ошибка при создании дома с невалидной ценой")
    @Description("В форме Create new house вводим отрицательную цену дома и ожидаем ошибку валидации без успешного создания")
    @Owner("OlgaBaidalina")
    public void createHouseWithInvalidPriceFromAllPostPage() {
        House house = HouseFactory.houseWithNegativePrice();

        allPostPage.createHouse(house);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateHouseStatusMessage()).isEqualTo("Status: Invalid input data");
            softly.assertThat(houseAdapter.getHouses())
                    .noneMatch(houseRs -> houseRs.getPrice() != null
                            && houseRs.getPrice().compareTo(house.getPrice()) == 0);
        });
    }

    @Test
    @DisplayName("All POST - создание автомобиля")
    @Description("В форме Create new car вводим валидные данные автомобиля и ожидаем успешное создание с кодом 201 и новым id")
    @Owner("OlgaBaidalina")
    public void createCarFromAllPostPage() {
        Car car = Car.builder()
                .engineType("Gasoline")
                .mark("Toyota")
                .model("Corolla")
                .price(BigDecimal.valueOf(1_000))
                .build();

        allPostPage.createCar(car);
        Integer carId = allPostPage.getCreateCarId();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateCarStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateCarStatusCode()).isEqualTo(201);
            softly.assertThat(carId).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - создание пользователя")
    @Description("В форме Create new user вводим валидные данные пользователя с полом MALE и ожидаем успешное создание с кодом 201 и новым id")
    @Owner("OlgaBaidalina")
    public void createUserFromAllPostPage() {
        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .age(30)
                .sex("MALE")
                .money(BigDecimal.valueOf(1_000))
                .build();

        allPostPage.createUser(user);
        Integer userId = allPostPage.getCreateUserId();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateUserStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateUserStatusCode()).isEqualTo(201);
            softly.assertThat(userId).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - создание пользователя женского пола")
    @Description("В форме Create new user выбираем radio FEMALE, вводим валидные данные пользователя и ожидаем успешное создание с кодом 201 и новым id")
    @Owner("OlgaBaidalina")
    public void createFemaleUserFromAllPostPage() {
        User user = User.builder()
                .firstName("Anna")
                .lastName("Ivanova")
                .age(25)
                .sex("FEMALE")
                .money(BigDecimal.valueOf(1_000))
                .build();

        allPostPage.createUser(user);
        Integer userId = allPostPage.getCreateUserId();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateUserStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateUserStatusCode()).isEqualTo(201);
            softly.assertThat(userId).isPositive();
        });
    }

    @Test
    @DisplayName("All POST - начисление денег пользователю")
    @Description("В форме Add money вводим id существующего пользователя и сумму пополнения, ожидаем код 200 и обновленный баланс")
    @Owner("OlgaBaidalina")
    public void addMoneyFromAllPostPage() {
        UserRq userRq = UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq);
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
    @Description("В форме Buy or sell car выбираем radio buyCar, вводим id пользователя и автомобиля, ожидаем успешную покупку с кодом 200")
    @Owner("OlgaBaidalina")
    public void buyOrSellCarFromAllPostPage() {
        UserRq userRq = UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(100_000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        CarRq carRq = CarRq.builder()
                .engineType("Gasoline")
                .mark("Toyota")
                .model("Corolla")
                .price(BigDecimal.valueOf(1_000))
                .build();
        CarRs carRs = carAdapter.createCar(carRq);

        allPostPage.buyCarForUser(userId, carRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getBuyOrSellCarStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getBuyOrSellCarStatusCode()).isEqualTo(200);
        });
    }

    @Test
    @DisplayName("All POST - продажа автомобиля")
    @Description("В форме Buy or sell car сначала покупаем автомобиль, затем выбираем radio sellCar и ожидаем успешную продажу с кодом 200")
    @Owner("OlgaBaidalina")
    public void sellCarFromAllPostPage() {
        UserRq userRq = UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(100_000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        CarRq carRq = CarRq.builder()
                .engineType("Gasoline")
                .mark("Toyota")
                .model("Corolla")
                .price(BigDecimal.valueOf(1_000))
                .build();
        CarRs carRs = carAdapter.createCar(carRq);

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
    @Description("В форме Settle to house выбираем radio settle, вводим id пользователя и дома, ожидаем успешное заселение с кодом 200 и проверяем связь в БД")
    @Owner("OlgaBaidalina")
    public void settleUserToHouseFromAllPostPage() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq);

        allPostPage.settleUserToHouse(userId, houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getSettleOrEvictStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getSettleOrEvictStatusCode()).isEqualTo(200);
            softly.assertThat(dbSteps.isUserLivesInHouse(userId, houseRs.getId())).isTrue();
        });
    }

    @Test
    @DisplayName("All POST - ошибка при заселении несуществующего пользователя в дом")
    @Description("В форме Settle to house выбираем radio settle, вводим несуществующий id пользователя и id дома, ожидаем ошибку 404 и проверяем, что жилец не появился")
    @Owner("OlgaBaidalina")
    public void settleMissingUserToHouseFromAllPostPage() {
        Integer missingUserId = 999_999_999;
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq);

        allPostPage.settleUserToHouse(missingUserId, houseRs.getId());
        HouseRs houseAfterSettle = houseAdapter.getHouse(houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getSettleOrEvictStatusMessage())
                    .isEqualTo("Status: AxiosError: Request failed with status code 404");
            softly.assertThat(houseAfterSettle.getLodgers())
                    .extracting(UserRs::getId)
                    .doesNotContain(missingUserId);
        });
    }

    @Test
    @DisplayName("All POST - выселение пользователя из дома")
    @Description("В форме Settle to house выбираем radio evict для заранее заселенного пользователя, ожидаем успешное выселение с кодом 200 и проверяем связь в БД")
    @Owner("OlgaBaidalina")
    public void evictUserFromHouseFromAllPostPage() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq);
        houseAdapter.settleUser(houseRs.getId(), userId);

        allPostPage.evictUserFromHouse(userId, houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getSettleOrEvictStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getSettleOrEvictStatusCode()).isEqualTo(200);
            softly.assertThat(dbSteps.isUserLivesInHouse(userId, houseRs.getId())).isFalse();
        });
    }

    @Test
    @DisplayName("All POST - ошибка при выселении несуществующего пользователя из дома")
    @Description("В форме Settle to house выбираем radio evict, вводим несуществующий id пользователя и id дома, ожидаем ошибку 404 и проверяем, что существующий жилец остался")
    @Owner("OlgaBaidalina")
    public void evictMissingUserFromHouseFromAllPostPage() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        Integer missingUserId = 999_999_999;
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq);
        houseAdapter.settleUser(houseRs.getId(), userId);

        allPostPage.evictUserFromHouse(missingUserId, houseRs.getId());
        HouseRs houseAfterEvict = houseAdapter.getHouse(houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getSettleOrEvictStatusMessage())
                    .isEqualTo("Status: AxiosError: Request failed with status code 404");
            softly.assertThat(houseAfterEvict.getLodgers())
                    .extracting(UserRs::getId)
                    .contains(userId)
                    .doesNotContain(missingUserId);
        });
    }
}
