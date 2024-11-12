package arquitectura.grupo19.admin_microservice.services;

import arquitectura.grupo19.admin_microservice.dto.AdminDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import arquitectura.grupo19.admin_microservice.entities.Admin;
import arquitectura.grupo19.admin_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.admin_microservice.feignClients.ScooterFeignClient;
import arquitectura.grupo19.admin_microservice.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ScooterFeignClient scooterFeignClient;

    private final double FARE_PER_MINUTE = 40;
    private final double FARE_INCREASE_PERCENTAGE = 0.20; // 20% de recargo

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

    public double getCostTrip(){
        return FARE_PER_MINUTE;
    }

    public double getCostTripWithSurcharge(){
        return FARE_PER_MINUTE * (1 + FARE_INCREASE_PERCENTAGE);
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
