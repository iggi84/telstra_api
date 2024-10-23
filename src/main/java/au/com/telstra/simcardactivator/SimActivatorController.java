package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SimActivatorController {

    @Autowired
    private SimActivationRecordRepository repository;

    @PostMapping("/activateSim")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationPayload requestData) {
        // Call to SimCardActuator
        String actuatorURL = "http://localhost:8444/actuate";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> payload = new HashMap<>();
        payload.put("iccid", requestData.getIccid());

        ResponseEntity<Map> response = restTemplate.postForEntity(actuatorURL, payload, Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            boolean success = (boolean)  response.getBody().get("success");
            SimActivationRecord record = new SimActivationRecord(requestData.getIccid(), requestData.getEmail(), success);
            repository.save(record);
            if (success) {
                return ResponseEntity.ok("SIM activated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SIM activation failed.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SIM activation failed.");
        }
    }

    @GetMapping("/querySim")
    public ResponseEntity<SimActivationRecord> querySim(@RequestParam String iccid) {
        return repository.findByIccid(iccid)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
