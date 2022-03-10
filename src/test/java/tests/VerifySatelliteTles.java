package tests;

import io.restassured.response.Response;
import org.requestBuilder.RequestBuilder;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VerifySatelliteTles {

    @Test
    public void getTles() {// to verify 200OK
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544)
                .get("satellites/{id}/tles");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .isEqualTo(200);
    }

    @Test
    public void getTlesForInvalidSatellite() { // to verify 404
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",2554)
                .get("satellites/{id}/tles");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .isEqualTo(404);
    }

    @Test
    public void getTlesResponseHeaders() { // to verify a constant response header
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("satellites/{id}/tles");
        response.prettyPrint();

        assertThat(response.jsonPath().getString("name"))
                .as("Verify the satellite name")
                .isEqualTo("iss");
    }

    @Test
    public void getTlesResponseSize() { // to verify we are getting all response Headers
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .get("satellites/{id}/tles");
        response.prettyPrint();

        assertThat(response.jsonPath().getMap("$").size())
                .isEqualTo(7);
    }

    @Test
    public void getPositionModification() { // to verify deletion is not allowed without authentication

        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id",25544 )
                .delete("satellites/{id}/tles");
        response.prettyPrint();

        assertThat(response.getStatusCode())
                .as("To verify deletion is restricted")
                .isEqualTo(401);
    }

    @Test
    public void getPositionInTextFormat() {
        Response response = RequestBuilder
                .buildRequestForGetCalls()
                .pathParam("id", 25544)
                .queryParam("format", "text")
                .get("satellites/{id}/tles");
        response.prettyPrint();

        assertThat(response.getContentType())
                .as("Verify response is plain text")
                .isEqualToIgnoringCase("text/plain");
    }
}