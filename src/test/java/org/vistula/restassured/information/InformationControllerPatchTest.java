package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class InformationControllerPatchTest extends RestAssuredTest {
//    @Test
//    public void addPerson() {
//        JSONObject requestParams = new JSONObject();
//        String myName = RandomStringUtils.randomAlphabetic(10);
//        String myNationality = RandomStringUtils.randomAlphabetic(10);
//        int mySalary = 1000;
//        requestParams.put("name", myName);
//        requestParams.put("nationality", myNationality);
//        requestParams.put("salary", mySalary);
//
//        given().header("Content-Type", "application/json")
//                .body(requestParams.toString())
//                .post("/information")
//                .then()
//                .log().all()
//                .statusCode(201)
//                .body("nationality", equalTo(myNationality) )
//                .body("name", equalTo(myName))
//                .body("salary", equalTo(mySalary))
//                .body("id", greaterThan(0));
//    }
}