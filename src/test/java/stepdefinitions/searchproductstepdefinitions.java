package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import java.util.HashMap;
import java.util.Map;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;


public class searchproductstepdefinitions {

    private static   String sessionToken ="t3cqquyhj4auihcfcvypcme999br6mf5";
    public static String baseUri = "https://magento.abox.co.za/rest/V1/";

    Map<String, String> headers = new HashMap<>(); //Hash Map.

    RequestSpecification request;
    String responseString;
    Response response;

    @Given("that the customer is on the Home page")
    public void that_the_customer_is_on_the_Home_page() {
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", sessionToken);
        request = given().headers(headers).baseUri(baseUri);
    }

    @Given("enters a product name in the search field \"([^\"]*)\"$")
    public void enters_a_product_name_in_the_search_field(String productItem) {
                request.basePath("search")
                .queryParam("searchCriteria[requestName]","quick_search_container")
                .queryParam("searchCriteria[filter_groups][0][filters][0][field]","search_term")
                .queryParam("searchCriteria[filter_groups][0][filters][0][value]",productItem);
    }

    @When("the customers clicks the search icon to search")
    public void the_customers_clicks_the_search_icon_to_search() {
         response = request.when().get();
    }

    @Then("the system should return a list of search result")
    public void the_system_should_return_a_list_of_search_result() {
        responseString = response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("total_count",equalTo(9))
                .extract()
                .body().asString();
        System.out.println("Response String is: " + responseString);
    }

}
