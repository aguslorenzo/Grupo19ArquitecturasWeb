package arquitectura.grupo19.api_gateway.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {
    @Autowired
    private RestTemplate restTemplate;

    //ADMIN

    @GetMapping(value = "admins/**")
    public ResponseEntity<String> redirectAdmin(HttpServletRequest request) {
        String url = "http://localhost:8085" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "admins/**")
    public ResponseEntity<String> postRedirectAdmin(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8085" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PutMapping(value = "admins/**")
    public ResponseEntity<String> putRedirectAdmin(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8085" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "admins/**")
    public ResponseEntity<String> deleteRedirectAdmin(HttpServletRequest request) {
        String url = "http://localhost:8085" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    //MAP

    @GetMapping(value = "maps/**")
    public ResponseEntity<String> redirectMap(HttpServletRequest request) {
        String url = "http://localhost:8088" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "maps/**")
    public ResponseEntity<String> postRedirectMap(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8088" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PutMapping(value = "maps/**")
    public ResponseEntity<String> putRedirectMap(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8088" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "maps/**")
    public ResponseEntity<String> deleteRedirectMap(HttpServletRequest request) {
        String url = "http://localhost:8088" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    //REPORT

    @GetMapping(value = "reports/**")
    public ResponseEntity<String> redirectReport(HttpServletRequest request) {
        String url = "http://localhost:8083" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "reports/**")
    public ResponseEntity<String> postRedirectReport(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8083" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PutMapping(value = "reports/**")
    public ResponseEntity<String> putRedirectReport(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8083" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "reports/**")
    public ResponseEntity<String> deleteRedirectReport(HttpServletRequest request) {
        String url = "http://localhost:8083" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    //SCOOTER

    @GetMapping(value = "scooters/**")
    public ResponseEntity<String> redirectScooter(HttpServletRequest request) {
        String url = "http://localhost:8087" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "scooters/**")
    public ResponseEntity<String> postRedirectScooter(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8087" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PutMapping(value = "scooters/**")
    public ResponseEntity<String> putRedirectScooter(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8087" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "scooters/**")
    public ResponseEntity<String> deleteRedirectScooter(HttpServletRequest request) {
        String url = "http://localhost:8087" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    //STOPS

    @GetMapping(value = "stops/**")
    public ResponseEntity<String> redirectStop(HttpServletRequest request) {
        String url = "http://localhost:8086" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "stops/**")
    public ResponseEntity<String> postRedirectStop(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8086" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PutMapping(value = "stops/**")
    public ResponseEntity<String> putRedirectStop(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8086" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "stops/**")
    public ResponseEntity<String> deleteRedirectStop(HttpServletRequest request) {
        String url = "http://localhost:8086" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    //TRIP

    @GetMapping(value = "trips/**")
    public ResponseEntity<String> redirectTrip(HttpServletRequest request) {
        String url = "http://localhost:8082" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "trips/**")
    public ResponseEntity<String> postRedirectTrip(HttpServletRequest request, @RequestBody(required = false) String body) {

        System.out.println("Cuerpo recibido en Gateway: " + body); // Debug
        String url = "http://localhost:8082" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PatchMapping(value = "trips/**")
    public ResponseEntity<String> patchRedirectTrip(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8082" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
    }

    @PutMapping(value = "trips/**")
    public ResponseEntity<String> putRedirectTrip(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8082" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "trips/**")
    public ResponseEntity<String> deleteRedirectTrip(HttpServletRequest request) {
        String url = "http://localhost:8082" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    //USER

    @GetMapping(value = "users/**")
    public ResponseEntity<String> redirectUser(HttpServletRequest request) {
        String url = "http://localhost:8084" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "users/**")
    public ResponseEntity<String> postRedirectUser(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8084" + request.getRequestURI();

        // Agregar los parámetros que se enviarán en el cuerpo
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userId", "1");
        map.add("scooterId", "1");

        // Convertir el Map a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = null;
        try {
            jsonBody = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  // Maneja el error de conversión a JSON
        }

        System.out.println("Estoy en postRedirect (GATEWAY CONTROLLER). JSONBODY= "+jsonBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Verifica si el cuerpo está presente y si es necesario, se agrega al HttpEntity
        HttpEntity<String> entity;
        if (body != null && !body.isEmpty()) {
            entity = new HttpEntity<>(body, headers);  // Si el cuerpo está presente, lo agregamos
        } else {
            entity = new HttpEntity<>(headers);  // Si no hay cuerpo, solo agregamos los headers
        }
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @PutMapping(value = "users/**")
    public ResponseEntity<String> putRedirectUser(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8084" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "users/**")
    public ResponseEntity<String> deleteRedirectUser(HttpServletRequest request) {
        String url = "http://localhost:8084" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

        //PAYMENT

    @GetMapping(value = "payment-accounts/**")
    public ResponseEntity<String> redirectPayment(HttpServletRequest request) {
        String url = "http://localhost:8084" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping(value = "payment-accounts/**")
    public ResponseEntity<String> postRedirectPayment(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8084" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @PutMapping(value = "payment-accounts/**")
    public ResponseEntity<String> putRedirectPayment(HttpServletRequest request, @RequestBody String body) {
        String url = "http://localhost:8084" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "payment-accounts/**")
    public ResponseEntity<String> deleteRedirectPayment(HttpServletRequest request) {
        String url = "http://localhost:8084" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

}
