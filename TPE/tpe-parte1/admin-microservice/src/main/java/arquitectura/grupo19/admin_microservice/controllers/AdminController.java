package arquitectura.grupo19.admin_microservice.controllers;

import arquitectura.grupo19.admin_microservice.dto.AdminDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import arquitectura.grupo19.admin_microservice.dto.StopDto;
import arquitectura.grupo19.admin_microservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminDto> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping("/{id}")
    public AdminDto getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAdmin(@RequestBody AdminDto adminDto) {
    	adminService.saveAdmin(adminDto);
    }

    @PutMapping("/{id}")
    public void updateAdmin(@PathVariable Long id, @RequestBody AdminDto adminDto) {
    	adminService.updateAdmin(id, adminDto);
    }

    @DeleteMapping("/{id}")
    public AdminDto deleteAdmin(@PathVariable Long id) {
        return adminService.deleteAdmin(id);
    }
    
    @PostMapping("/scooter")
    @ResponseStatus(HttpStatus.CREATED)
    public void addScooter(@RequestBody ScooterDto scooter) { //TODO corregir tipo de retorno
        adminService.addScooter(scooter);
    }

    @DeleteMapping("/scooter/{scooterId}")
    public ScooterDto deleteScooter(@PathVariable long scooterId) { //TODO corregir tipo de retorno
        return adminService.deleteScooter(scooterId);
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStop(@RequestBody StopDto stop) { //TODO corregir tipo de retorno
        adminService.addStop(stop);
    }

    @DeleteMapping("/stop/{stopId}")
    public StopDto deleteStop(@PathVariable long stopId) {
        return adminService.deleteStop(stopId);
    }

    @GetMapping("/cost")
    public double getCostTrip() {
        return adminService.getCostTrip();
    }

    @GetMapping("/costwithsurcharge")
    public double getCostTripWithSurcharge() {
        return adminService.getCostTripWithSurcharge();
    }

    @DeleteMapping("/user/{userId}")
    public void cancelUserAccount(@PathVariable long userId) {
        adminService.cancelUserAccount(userId);
    }

}
