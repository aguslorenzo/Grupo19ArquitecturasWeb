package arquitectura.grupo19.admin_microservice.controllers;

import arquitectura.grupo19.admin_microservice.dto.AdminDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterStatusCountDto;
import arquitectura.grupo19.admin_microservice.dto.StopDto;
import arquitectura.grupo19.admin_microservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public StopDto deleteStop(@PathVariable String stopId) {
        return adminService.deleteStop(stopId);
    }

    @GetMapping("/cost")
    public double getCostTrip() {
        return adminService.getCostTrip();
    }

    @GetMapping("/cost-with-surcharge")
    public double getCostTripWithSurcharge() {
        return adminService.getCostTripWithSurcharge();
    }

    /**
     * b) Como administrador quiero poder anular cuentas para inhabilitar el uso momentáneo de la
     * misma.
     */
    @PutMapping("/user/{userId}")
    public void cancelUserAccount(@PathVariable long userId) {
        adminService.cancelUserAccount(userId);
    }

    /**
     * c) Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
     */
    @GetMapping("/scooters/trips")
    public List<ScooterDto> getScootersWithTrips(@RequestParam int year, @RequestParam int minTrips) {
        return adminService.getScootersWithTrips(year, minTrips);
    }

    /**
     * d) Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
     */
    @GetMapping("/billing")
    public double getTotalBilled(@RequestParam int year,
                                 @RequestParam int startMonth,
                                 @RequestParam int endMonth) {
        return adminService.getTotalBilledInPeriod(year, startMonth, endMonth);
    }

    /**
     * e) Como administrador quiero consultar la cantidad de monopatines actualmente en operación,
     * versus la cantidad de monopatines actualmente en mantenimiento.
     */
    @GetMapping("/scooters/status-count")
    public ScooterStatusCountDto getScooterStatusCounts() {
        return adminService.getScooterStatusCounts();
    }

    /**
     * Endpoint para establecer el precio inicial
     */
    @PostMapping("/pricing/initial")
    @ResponseStatus(HttpStatus.CREATED)
    public void setInitialPricing(@RequestParam double fare, @RequestParam double surchargePercentage) {
        adminService.setInitialPricing(fare, surchargePercentage);
    }

    /**
     * f) Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
     * habilite los nuevos precios.
     */
    @PostMapping("/pricing/adjust")
    @ResponseStatus(HttpStatus.CREATED)
    public void adjustPricing(@RequestParam double newFare,
                              @RequestParam double newSurchargePercentage,
                              @RequestParam String adjustmentDate) {
        LocalDate date = LocalDate.parse(adjustmentDate); // Convertir fecha desde String
        adminService.adjustPricing(newFare, newSurchargePercentage, date);
    }

}
