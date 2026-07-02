package tests.api.user;

import api.adapters.CarAdapter;
import api.adapters.HouseAdapter;
import api.adapters.UserAdapter;
import api.models.car.CarRq;
import api.models.car.CarRqFactory;
import api.models.car.CarRs;
import api.models.house.HouseRq;
import api.models.house.HouseRs;
import api.models.user.UserInfoRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import api.models.user.UserRs;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.steps.DBSteps;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserApiTest {

    private final UserAdapter userAdapter = new UserAdapter();
    private final DBSteps dbSteps = new DBSteps();
    private final CarAdapter carAdapter = new CarAdapter();
    private final HouseAdapter houseAdapter = new HouseAdapter();
    private final Faker faker = new Faker();

    private void assertUserEquals(UserRs userRs, UserRq userRq) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.getFirstName()).isEqualTo(userRq.getFirstName());
            softly.assertThat(userRs.getSecondName()).isEqualTo(userRq.getSecondName());
            softly.assertThat(userRs.getAge()).isEqualTo(userRq.getAge());
            softly.assertThat(userRs.getSex()).isEqualTo(userRq.getSex());
            softly.assertThat(userRs.getMoney()).isEqualByComparingTo(userRq.getMoney());
        });
    }

    @Test
    @DisplayName("API - Создание нового пользователя")
    @Description("Проверка создания нового пользователя")
    public void createUser() {
        UserRq userRq = UserRqFactory.validUser();
        UserRs userRs = userAdapter.createUser(userRq);
        assertUserEquals(userRs, userRq);
        userAdapter.deleteUser(userRs.getId());
    }

    static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(UserRqFactory.userWithNullFirstName()),
                Arguments.of(UserRqFactory.userWithNullSecondName()),
                Arguments.of(UserRqFactory.userWithNullAge()),
                Arguments.of(UserRqFactory.userWithNullMoney())
        );
    }

    @DisplayName("API - Создание пользователя с невалидными данными")
    @ParameterizedTest(name = "Создание пользователя с невалидными данными - {0}")
    @MethodSource("invalidUsers")
    @Description("Проверка создания пользователя с невалидными данными")
    void createUserWithNullFields(UserRq userRq) {
        userAdapter.createUserWithNullFields(userRq);
    }

    @Test
    @DisplayName("API - Получение списка пользователей")
    @Description("Проверка получения списка пользователей")
    public void getUsers() {
        List<UserRs> users = userAdapter.getUsers();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(users).isNotNull();
            softly.assertThat(users).isNotEmpty();
            for(UserRs user : users) {
                softly.assertThat(user.getId()).isNotNull();
                softly.assertThat(user.getFirstName()).isNotNull();
                softly.assertThat(user.getSecondName()).isNotNull();
                softly.assertThat(user.getSex()).isNotNull();
                softly.assertThat(user.getAge()).isNotNull();
                softly.assertThat(user.getMoney()).isNotNull();
            }
        });
    }

    @Test
    @DisplayName("API - Удаление пользователя по id")
    @Description("Проверка удаления пользователя по id")
    public void deleteUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        userAdapter.deleteUser(userId);
        assertThat(dbSteps.isUserExistsInDB(userId)).isFalse();
    }

    @Test
    @DisplayName("API - Попытка удаления несуществующего пользователя")
    @Description("Проверка удаления несуществующего пользователя")
    public void deleteNotExistingUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        userAdapter.deleteUser(userId);
        assertThat(dbSteps.isUserExistsInDB(userId)).isFalse();
        userAdapter.deleteNotExistingUser(userId);
    }

    @Test
    @DisplayName("API - Получение пользователя по id")
    @Description("Проверка получения пользователя по id")
    public void getUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        UserRs userRs = userAdapter.getUserById(userId);
        assertUserEquals(userRs, userRq);
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("API - Попытка получения несуществующего пользователя по id")
    @Description("Проверка получения несуществующего пользователя")
    public void getNotExistingUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        userAdapter.deleteUser(userId);
        userAdapter.getNotExistingUserById(userId);
    }

    @Test
    @DisplayName("API - Изменение пользователя")
    @Description("Проверка изменения пользователя")
    public void changeUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        UserRq newUserRq = UserRqFactory.validUserWithId(userId);
        UserRs userRs = userAdapter.changeUser(userId, newUserRq);
        assertUserEquals(userRs, newUserRq);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userId).isEqualTo(newUserRq.getId());
        });
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("API - Попытка изменения несуществующего пользователя")
    @Description("Проверка изменения несуществующего пользователя")
    public void changeNotExistingUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        UserRq newUserRq = UserRqFactory.validUserWithId(userId);
        userAdapter.deleteUser(userId);
        userAdapter.changeNotExistingUser(userId, newUserRq);
    }

    @Test
    @DisplayName("API - Начисление денег пользователю")
    @Description("Проверка начисления денег пользователю")
    public void addMoneyToUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        UserRs userRs = userAdapter.addMoneyToUser(userId, money);
        assertThat(userRq.getMoney().add(money)).isEqualByComparingTo(userRs.getMoney());
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("API - Попытка начисления денег несуществующему пользователю")
    @Description("Проверка начисления денег несуществующему пользователю")
    public void addMoneyToNotExistingUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        userAdapter.deleteUser(userId);
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        userAdapter.addMoneyToNotExistingUser(userId, money);
    }

    @Test
    @DisplayName("API - Попытка начисления денег пользователю, отрицательная сумма")
    @Description("Проверка начисления отрицательной суммы")
    public void addInvalidMoneyToUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        userAdapter.addInvalidMoneyToUser(userId, BigDecimal.valueOf(-125.0236));
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("API - Получение списка автомобилей пользователя")
    @Description("Проверка получения списка автомобилей пользователя")
    public void getUserCars() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        CarAdapter carAdapter = new CarAdapter();
        CarRq carRq = CarRqFactory.validCar();
        CarRs carRs = carAdapter.createCar(carRq);
        Integer carId = carRs.getId();
        userAdapter.buyCar(userId, carId);
        List<CarRs> cars = userAdapter.getUserCars(userId);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cars).isNotEmpty();
        softly.assertThat(cars.get(0).getId()).isEqualTo(carId);
        softly.assertThat(cars.get(0).getMark()).isEqualTo(carRs.getMark());
        softly.assertAll();
        userAdapter.sellCar(userId, carId);
        carAdapter.deleteCar(carId);
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("API - Получение имущества несуществующего пользователя")
    @Description("Проверка получения имущества несуществующего пользователя")
    public void getNotExistingUserInfo() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        userAdapter.deleteUser(userId);
        userAdapter.getNotExistingUserInfo(userId);
    }

    @Test
    @DisplayName("API - Получение имущества пользователя")
    @Description("Проверка получения имущества пользователя")
    public void getUserInfo() {
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(1000000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        CarRq carRq = CarRqFactory
                .validCar()
                .toBuilder()
                .price(BigDecimal.valueOf(10000))
                .build();
        CarRs carRs = carAdapter.createCar(carRq);
        userAdapter.buyCar(userId, carRs.getId());
        UserInfoRs userInfoRs = userAdapter.getUserInfo(userId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userInfoRs.getFirstName()).isEqualTo(userRq.getFirstName());
            softly.assertThat(userInfoRs.getSecondName()).isEqualTo(userRq.getSecondName());
            softly.assertThat(userInfoRs.getAge()).isEqualTo(userRq.getAge());
            softly.assertThat(userInfoRs.getSex()).isEqualTo(userRq.getSex());
            softly.assertThat(userInfoRs.getMoney()).isEqualByComparingTo(userRq.getMoney().subtract(carRq.getPrice()));
        });
        for (CarRs car : userInfoRs.getCars()) {
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(carRs.getId()).isEqualTo(car.getId());
                softly.assertThat(carRs.getMark()).isEqualTo(car.getMark());
                softly.assertThat(carRs.getModel()).isEqualTo(car.getModel());
                softly.assertThat(carRs.getEngineType()).isEqualTo(car.getEngineType());
                softly.assertThat(carRs.getPrice()).isEqualByComparingTo(car.getPrice());
            });
        }
        userAdapter.sellCar(userId, carRs.getId());
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("Попытка удаления пользователя, у которого есть машина")
    @Description("Проверка удаления пользователя, у которого есть машина")
    public void deleteUserHavingCar() {
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(1000000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        CarRq carRq = CarRqFactory
                .validCar()
                .toBuilder()
                .price(BigDecimal.valueOf(10000))
                .build();
        CarRs car = carAdapter.createCar(carRq);
        userAdapter.buyCar(userId, car.getId());
        userAdapter.deleteUserNegative(userId);
        userAdapter.sellCar(userId, car.getId());
        carAdapter.deleteCar(car.getId());
        userAdapter.deleteUser(userId);
    }

    @Test
    @DisplayName("Попытка удаления пользователя, проживающего в доме")
    @Description("Проверка удаления пользователя, проживающего в доме")
    public void deleteUserLiveInHouse() {
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(1000000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq);
        HouseRq houseRq = HouseRq.builder()
                .floorCount(5)
                .price(BigDecimal.valueOf(10000))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq);
        houseAdapter.settleUser(houseRs.getId(), userId);
        userAdapter.deleteUserNegative(userId);
        houseAdapter.evictUser(houseRs.getId(), userId);
        userAdapter.deleteUser(userId);
    }
}
