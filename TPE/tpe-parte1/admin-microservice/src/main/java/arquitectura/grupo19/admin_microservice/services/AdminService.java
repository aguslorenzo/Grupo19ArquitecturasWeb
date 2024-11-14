package arquitectura.grupo19.admin_microservice.services;

import arquitectura.grupo19.admin_microservice.dto.AdminDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterStatusCountDto;
import arquitectura.grupo19.admin_microservice.dto.StopDto;
import arquitectura.grupo19.admin_microservice.entities.Admin;
import arquitectura.grupo19.admin_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.admin_microservice.feignClients.ScooterFeignClient;
import arquitectura.grupo19.admin_microservice.feignClients.StopFeignClient;
import arquitectura.grupo19.admin_microservice.feignClients.TripFeignClient;
import arquitectura.grupo19.admin_microservice.feignClients.UserFeignClient;
import arquitectura.grupo19.admin_microservice.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {

    // Precios actuales
    private double farePerMinute = 40;
    private double fareIncreasePercentage = 0.20; //20% de recargo
    private LocalDate priceChangeDate; // Fecha a partir de la cual se aplican los nuevos precios

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ScooterFeignClient scooterFeignClient;
    @Autowired
    private StopFeignClient stopFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private TripFeignClient tripFeignClient;

    // Establecer el precio inicial
    public void setInitialPricing(double fare, double surchargePercentage) {
        this.farePerMinute = fare;
        this.fareIncreasePercentage = surchargePercentage;
    }

    @Transactional
    public AdminDto saveAdmin(AdminDto adminDto){
        // Convertir el DTO a entidad admin
        Admin admin = convertDtoToEntity(adminDto);

        // Si pasa las validaciones, guardar parada en base de datos
        Admin save = adminRepository.save(admin);

        // Retornar DTO
        return convertEntityToDto(save);
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public AdminDto getAdminById(Long id){
        return adminRepository.findById(id)
                .map(AdminDto::new)
                .orElseThrow(()->new NotFoundException("Admin", id));
    }

    @Transactional(readOnly = true)
    public List<AdminDto> getAdmins(){
        return adminRepository.findAll()
                .stream().map(AdminDto::new).toList();
    }

    public void updateAdmin(Long id, AdminDto adminDto){
        // Buscar por id
        Admin admin = adminRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Stop", id));

        // Actualizar los campos de admin con los datos nuevos
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());
        admin.setCellphone(adminDto.getCellphone());

        // Guardar los cambios
        adminRepository.save(admin);
    }

    public AdminDto deleteAdmin(Long id){
        Admin admin = adminRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User", id));
        adminRepository.delete(admin);
        return convertEntityToDto(admin);
    }
    
    
    //********************************************************************************************************************
	// SERVICIOS DE GESTION DE SCOOTERS

	public void addScooter(ScooterDto scooter) { //TODO revisar de poner el tipo de retorno en el micro de scooter si no queremos que sea void
        scooterFeignClient.saveScooter(scooter);
	}

    public ScooterDto deleteScooter(long scooterId){
        return scooterFeignClient.deleteScooter(scooterId);
    }

    public void addStop(StopDto stop) {
        stopFeignClient.saveStop(stop);
    }

    public StopDto deleteStop(long stopId) {
        return stopFeignClient.deleteStop(stopId);
    }

    public double getCostTrip(){
        return farePerMinute;
    }

    public double getCostTripWithSurcharge(){
        return farePerMinute * (1 + fareIncreasePercentage);
    }

    public void cancelUserAccount(long userId){
        userFeignClient.toggleAccountStatus(userId);
    }

    public List<ScooterDto> getScootersWithTrips(int year, int minTrips) {
        return scooterFeignClient.getScootersWithTrips(year, minTrips);
    }

    public double getTotalBilledInPeriod(int year, int startMonth, int endMonth) {
        return tripFeignClient.getTotalBilledInPeriod(year, startMonth, endMonth);
    }

    public ScooterStatusCountDto getScooterStatusCounts() {
        return scooterFeignClient.getScooterStatusCounts();
    }

    // Ajustar el precio y programar el cambio para una fecha futura
    public void adjustPricing(double newFare, double newSurchargePercentage, LocalDate adjustmentDate) {
        // Solo actualizamos los precios si la fecha de ajuste es futura
        if (LocalDate.now().isBefore(adjustmentDate)) {
            this.farePerMinute = newFare;
            this.fareIncreasePercentage = newSurchargePercentage;
            this.priceChangeDate = adjustmentDate;
        } else {
            throw new IllegalArgumentException("La fecha de ajuste debe ser futura.");
        }
    }

	// ********************************************************************************************************************
    
    
    
    private Admin convertDtoToEntity(AdminDto adminDto) {
    	Admin admin = new Admin();
    	admin.setFirstName(adminDto.getFirstName());
    	admin.setLastName(adminDto.getLastName());
    	admin.setEmail(adminDto.getEmail());
    	admin.setCellphone(adminDto.getCellphone());

        return admin;
    }

    private AdminDto convertEntityToDto(Admin admin) {
    	AdminDto adminDto = new AdminDto();
    	adminDto.setFirstName(admin.getFirstName());
    	adminDto.setLastName(admin.getLastName());
    	adminDto.setEmail(admin.getEmail());
    	adminDto.setCellphone(admin.getCellphone());
        return adminDto;
    }
}
