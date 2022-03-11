package tests;

import io.restassured.response.Response;
import org.requestBuilder.RequestBuilder;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VerifySatellitePositions {

    @Test
    public void getPosition() { // to verify 200OK
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .queryParam("timestamps", System.currentTimeMillis()/1000)
                .get("/satellites/{id}/positions");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test
    public void getPositionForInvalidSatellite() { // to verify 404
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",2554)
                .get("/satellites/{id}/positions?timestamps=1644185093");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .isEqualTo(404);
    }

    @Test
    public void getPositionResponseHeaders() { // to verify a constant response header
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("/satellites/{id}/positions?timestamps=1644184742");
        response.prettyPrint();

        assertThat(response.jsonPath().getString("name"))
                .as("Verify the satellite name")
                .isEqualTo("[iss]");
        // can be optimised to verify not as an array
    }

    @Test
    public void getPositionResponseHeaderWithKilometers() { // to verify a kilometers is default response
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("/satellites/{id}/positions?timestamps=1644184742");
        response.prettyPrint();

        assertThat(response.jsonPath().getString("units"))
                .as("Verify the units")
                .isEqualTo("kilometers");
    }

    @Test
    public void getPositionResponseHeaderWithMiles() { // to verify a units can be changes to miles in Query parameters
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("/satellites/{id}/positions?timestamps=1644184742&units=miles");
        response.prettyPrint();

        assertThat(response.jsonPath().getString("units"))
                .as("Verify the units")
                .isEqualTo("miles");
    }

    @Test
    public void getPositionResponseSize() { // to verify we are getting response for each timestamp

        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("/satellites/{id}/positions?timestamps=1644185093,1644185093");
        response.prettyPrint();

        assertThat(response.jsonPath().getList("$").size())
                .isPositive()
                .isEqualTo(2);
    }

    @Test
    public void getPositionWithoutRequiredQueryParameters() { // to verify a bad request
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("/satellites/{id}/positions");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .isEqualTo(400);
    }

    @Test
    public void getPositionModification() { // to verify deletion is not allowed without authentication

        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .delete("/satellites/{id}/positions?timestamps=1644185093,1644185093");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .as("To verify modification is restricted")
                .isEqualTo(401);
    }
}
