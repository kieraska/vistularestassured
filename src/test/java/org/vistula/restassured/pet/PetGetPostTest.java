package org.vistula.restassured.pet;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class PetGetPostTest extends RestAssuredTest {
    @Test
    public void shouldGetAddedPet() {
        JSONObject requestParams = new JSONObject();
        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);
        String randomName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("id", value);
        requestParams.put("name", randomName);

        createNewPet(requestParams);
        getPet(value, randomName);
        deletePet(value);


    }

    public void deletePet(int value) {
        int statusCode = given().delete("/pet/" + value)
                .then()
                .log().all()
                .statusCode(204)
                .extract().statusCode();
        assertThat(statusCode).isEqualTo(204);
    }

    private void getPet(int value, String randomName) {
        given().get("/pet/" + value)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(value))
                .body("name", equalTo(randomName));
    }

    private void createNewPet(JSONObject requestParams) {
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }
}
