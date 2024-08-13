package project.group14.authenticationservice.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;
import project.group14.authenticationservice.dto.AppointmentDTO;
import project.group14.authenticationservice.dto.PatientRecordDTO;
import project.group14.authenticationservice.model.User;
import project.group14.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private static final String PATIENT_SERVICE_URL = "http://localhost:8082/patients";
    private static final String APPOINTMENT_SERVICE_URL = "http://localhost:8081/appointments";
    private static final String NOTIFICATION_SERVICE_URL = "http://localhost:8083/notifications";

    @Autowired
    public ApplicationService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    /*
     *   User services
     */

    // ---------------------------
    // User-related methods
    // ---------------------------

    // Get the currently authenticated user
    public User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    }

    // Get all appointments for the authenticated user
    public List<AppointmentDTO> getUserAppointments() {
        User user = getAuthenticatedUser();
        if (user != null) {
            String url = APPOINTMENT_SERVICE_URL + "?patientRecordId=" + user.getPatientRecordId();
            return restTemplate.getForObject(url, List.class);
        }
        throw new IllegalStateException("User is not authenticated");
    }


    /*
     *   Admin services
     */

    // Fetch all patients
    public List<PatientRecordDTO> getAllPatients() {
        return restTemplate.getForObject(PATIENT_SERVICE_URL, List.class);
    }

    // Fetch a specific patient record by ID
    public PatientRecordDTO getPatientRecordById(Long id) {
        String url = PATIENT_SERVICE_URL + "/" + id;
        return restTemplate.getForObject(url, PatientRecordDTO.class);
    }

    // Fetch all appointments across the system
    public List<AppointmentDTO> getAllAppointments() {
        return restTemplate.getForObject(APPOINTMENT_SERVICE_URL, List.class);
    }

    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        String url = APPOINTMENT_SERVICE_URL + "/" + id;
        restTemplate.put(url, appointmentDTO);
        return appointmentDTO;
    }

    public List<String> getAllNotifications() {
        return restTemplate.getForObject(NOTIFICATION_SERVICE_URL, List.class);
    }

    public String sendNotification(String message) {
        return restTemplate.postForObject(NOTIFICATION_SERVICE_URL + "/send", message, String.class);
    }

    public String sendDailyNotifications() {
        return restTemplate.postForObject(NOTIFICATION_SERVICE_URL + "/daily", null, String.class);
    }


}
