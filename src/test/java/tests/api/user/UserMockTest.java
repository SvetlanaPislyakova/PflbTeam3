package tests.api.user;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import api.models.user.UserRs;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PropertyReader;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@WireMockTest
public class UserMockTest {

    private static final Gson gson = new Gson();
    private static final String accessToken = "fake-test-token";

    @BeforeEach
    void setUp(WireMockRuntimeInfo wm) {
        WireMock wireMock = wm.getWireMock();
        PropertyReader.overrideProperty("baseUri", wm.getHttpBaseUrl());
        wireMock.register(
                post(urlEqualTo("/login"))
                        .willReturn(aResponse()
                                .withStatus(202)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"access_token\":\"" + accessToken + "\"}"))
        );
    }

    @Test
    public void createUserWithoutId(WireMockRuntimeInfo wm) {
        WireMock wireMock = wm.getWireMock();
        UserRq request = UserRqFactory.validUser();
        UserRs brokenContract = UserRs.builder()
                .firstName("Bruce")
                .secondName("Willis")
                .age(45)
                .sex("MALE")
                .money(BigDecimal.valueOf(100.34))
                .build();
        wireMock.register(
                post(urlEqualTo("/user"))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withHeader("Content-Type", "application/json")
                                .withBody(gson.toJson(brokenContract)))
        );

        UserAdapter userAdapter = new UserAdapter();
        assertThatThrownBy(() -> userAdapter.createUser(request))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("missing required properties");

        verify(1, postRequestedFor(urlEqualTo("/user")));
    }
}
