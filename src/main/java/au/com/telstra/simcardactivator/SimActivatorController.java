package au.com.telstra.simcardactivator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SimActivatorController {

    @PostMapping("/activateSim")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationPayload requestData) {
        // Call to SimCardActuator
        String actuatorURL = "http://localhost:8444/actuate";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> payload = new HashMap<>();
        payload.put("iccid", requestData.getIccid());

        ResponseEntity<Map> response = restTemplate.postForEntity(actuatorURL, payload, Map.class);
        boolean success = (boolean)  response.getBody().get("success");


        if (success) {
            return ResponseEntity.ok("SIM activated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SIM activation failed.");
        }
    }

}
