package org.requestBuilder;


import io.restassured.specification.RequestSpecification;
import org.enums.PropertiesType;
import org.utils.PropertyUtils;

import static io.restassured.RestAssured.given;

public class RequestBuilder {

    private RequestBuilder(){}

    public static RequestSpecification buildRequestForGetCalls(){
        return given()
                .baseUri(PropertyUtils.getValue(PropertiesType.BASEURL))
                .log()
                .all();
    }
}