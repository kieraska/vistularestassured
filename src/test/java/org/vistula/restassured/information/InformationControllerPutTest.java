package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class InformationControllerPutTest extends RestAssuredTest {

    @Test
    public void putPersonWithExistingId() {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(10);
        String myNationality = RandomStringUtils.randomAlphabetic(10);
        Random rnd = new Random();
        int maxSalary = 5000;
        int minSalary = 1000;
        int mySalary = rnd.nextInt((maxSalary - minSalary) + 1) + minSalary;

        requestParams.put("name", myName);
        requestParams.put("nationality", myNationality);
        requestParams.put("salary", mySalary);

        JSONObject requestParams2 = new JSONObject();
        String myName2 = RandomStringUtils.randomAlphabetic(10);
        String myNationality2 = RandomStringUtils.randomAlphabetic(10);
        int mySalary2 = rnd.nextInt((maxSalary - minSalary) + 1) + minSalary;

        requestParams2.put("name", myName2);
        requestParams2.put("nationality", myNationality2);
        requestParams2.put("salary", mySalary2);

        long createdPersonId = createNewPerson(requestParams);

        getPerson(createdPersonId, myName, myNationality, mySalary);

        Information information = given().header("Content-Type", "application/json")
                .body(requestParams2.toString())
                .put("/information/" + createdPersonId)
                .then()
                .log().all()
                .statusCode(200)
                .body("nationality", equalTo(myNationality2))
                .body("name", equalTo(myName2))
                .body("salary", equalTo(mySalary2))
                .body("id", equalTo((int)createdPersonId))
                .extract().body().as(Information.class);

        assertThat(information.getName()).isEqualTo(myName2);
        assertThat(information.getSalary()).isEqualTo(mySalary2);
        assertThat(information.getNationality()).isEqualTo(myNationality2);
        assertThat(information.getId()).isEqualTo(createdPersonId);

        getPerson(createdPersonId, myName2, myNationality2, mySalary2);

        deletePerson(createdPersonId);
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

    private long createNewPerson(JSONObject personJSON) {
        long id = given().header("Content-Type", "application/json")
                .body(personJSON.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class).getId();
        return id;
    }


    private void getPerson(long id, String expectedName, String expectedNationality, int expectedSalary) {
        given().get("/information/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is((int)id))
                .body("name", equalTo(expectedName))
                .body("nationality", equalTo(expectedNationality))
                .body("salary", equalTo(expectedSalary));
    }

    public void deletePerson(long id) {
        int statusCode = given().delete("/information/" + id)
                .then()
                .log().all()
                .statusCode(204)
                .extract().statusCode();
        assertThat(statusCode).isEqualTo(204);
    }

}
