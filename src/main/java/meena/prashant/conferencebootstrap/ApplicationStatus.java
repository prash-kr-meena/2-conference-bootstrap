package meena.prashant.conferencebootstrap;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationStatus {
  @GetMapping("/status")
  public Map<String, String> applicationStatus() {
    return Map.of("status", "Application Up :)");
  }
}
