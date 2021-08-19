package ab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  // http://localhost:8080/hello?name=world
  @GetMapping("/hello")
  public String greeting(@RequestParam(value = "name", defaultValue = "world") String name) {
    return "hello " + name;
  }

}
