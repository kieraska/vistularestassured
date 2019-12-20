package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class InformationControllerPutTest extends RestAssuredTest {
    @Test
    public void putPersonWithExistingId() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);
        String myNationality = RandomStringUtils.randomAlphabetic(10);
        Random randomSalary = new Random();
        int maxSalary = 5000;
        int minSalary = 1000;
        int mySalary = randomSalary.nextInt((maxSalary - minSalary) + 1) + minSalary;
        int myId = 2;

        requestParams.put("name", myName);
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", mySalary);
        requestParams.put("id", myId);

        Information information = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/information/" + myId)
                .then()
                .log().all()
                .statusCode(200)
                .body("nationality", equalTo(myNationality))
                .body("name", equalTo(myName))
                .body("salary", equalTo(mySalary))
                .body("id", equalTo(myId))
                .extract().body().as(Information.class);

        assertThat(information.getName()).isEqualTo(myName);
        assertThat(information.getSalary()).isEqualTo(mySalary);
        assertThat(information.getNationality()).isEqualTo(myNationality);
        assertThat(information.getId()).isEqualTo(myId);
    }


    @Test
    public void putPersonWithNotExistingId() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);
        String myNationality = RandomStringUtils.randomAlphabetic(10);
        Random randomSalary = new Random();
        int maxSalary = 5000;
        int minSalary = 1000;
        int mySalary = randomSalary.nextInt((maxSalary - minSalary) + 1) + minSalary;
        int myId = 100;

        requestParams.put("name", myName);
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", mySalary);
        requestParams.put("id", myId);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/information/" + myId)
                .then()
                .log().all()
                .statusCode(406)
                .body(equalTo("ID not found. Please use POST method to create new entries"));
    }
}
