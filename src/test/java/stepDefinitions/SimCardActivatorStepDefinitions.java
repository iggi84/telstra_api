package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String iccid;

    @Given("a SIM card with ICCID {string} and customer email {string}")
    public void a_sim_card_with_iccid_and_customer_email(String iccid, String customerEmail) {
        this.iccid = iccid;

        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("iccid", iccid);
        requestPayload.put("customerEmail", customerEmail);

        // Send POST request to activate the SIM card
        response = restTemplate.postForEntity("http://localhost:8080/activateSim", requestPayload, String.class);
    }

    @When("I submit a POST request to activate the SIM card")
    public void i_submit_a_post_request_to_activate_the_sim_card() {
        assertNotNull(response);
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        assertEquals(500, response.getStatusCodeValue());
    }

    @Then("the query for the ICCID {string} should return {string}")
    public void the_query_for_the_iccid_should_return(String iccid, String expectedStatus) {
        ResponseEntity<Map> queryResponse = restTemplate.getForEntity(
                "http://localhost:8080/querySim?iccid=" + iccid, Map.class);

        assertNotNull(queryResponse.getBody());
        assertEquals(Boolean.parseBoolean(expectedStatus), queryResponse.getBody().get("active"));
    }
}