package project.group14.authenticationservice.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;
import project.group14.authenticationservice.dto.AppointmentDTO;
import project.group14.authenticationservice.dto.NotificationDTO;
import project.group14.authenticationservice.dto.PatientRecordDTO;
import project.group14.authenticationservice.dto.PatientRecordWithAppointmentsDTO;
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
     ---------------------------
     User-related methods
     ---------------------------
*/

    public PatientRecordDTO createPatientRecord(PatientRecordDTO patientRecordDTO) {
        User user = getAuthenticatedUser();
        patientRecordDTO.setUserId((long) user.getId());
        return restTemplate.postForObject(PATIENT_SERVICE_URL, patientRecordDTO, PatientRecordDTO.class);
    }

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        PatientRecordDTO patientRecordDTO = getPatientRecordByUserId();
        appointmentDTO.setPatientRecordId(patientRecordDTO.getId());
        AppointmentDTO appointment = restTemplate.postForObject(APPOINTMENT_SERVICE_URL, appointmentDTO, AppointmentDTO.class);
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setAppointmentDate(appointment.getAppointmentDateTime());
        notificationDTO.setAppointmentId(appointment.getId());
        notificationDTO.setTitle("Appointment created");
        notificationDTO.setMessage("Your appointment created with doctor " + appointment.getDoctorName() + " duration :" + appointment.getAppointmentDuration() + " appointment date :" + appointment.getAppointmentDateTime());
        notificationDTO.setPatientId(appointment.getPatientRecordId());
        restTemplate.postForObject(NOTIFICATION_SERVICE_URL, notificationDTO, NotificationDTO.class);
        return  appointment;
    }

    public AppointmentDTO updateUserAppointment(long id, AppointmentDTO appointmentDTO) {
        User user = getAuthenticatedUser();
        PatientRecordDTO patientRecordDTO = getPatientRecordById((long) user.getId());
        appointmentDTO.setPatientRecordId(patientRecordDTO.getId());

        String url = APPOINTMENT_SERVICE_URL + "/" + id;

        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(appointmentDTO),
                AppointmentDTO.class
        ).getBody();
    }

    public AppointmentDTO getUserAppointment(long id) {
        String url = APPOINTMENT_SERVICE_URL + "/" + id;

        return restTemplate.getForObject(url, AppointmentDTO.class);
    }

    // Get all appointments for the authenticated user
    public List<AppointmentDTO> getUserAppointments() {
        PatientRecordDTO patientRecordDTO = getPatientRecordByUserId();
        if (patientRecordDTO != null) {
            String url = APPOINTMENT_SERVICE_URL + "?userId=" + patientRecordDTO.getId();
            return new RestTemplate().exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<AppointmentDTO>>() {
                    }
            ).getBody();

        }
        throw new IllegalStateException("No Patient Record found in the system!");
    }


    // Get the currently authenticated user
    public User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    }

    // Fetch a specific patient record by ID
    public PatientRecordDTO getPatientRecordByUserId() {
        User user = getAuthenticatedUser();
        String url = PATIENT_SERVICE_URL + "/user/" + user.getId();
        return restTemplate.getForObject(url, PatientRecordDTO.class);
    }

    /*
     *   Admin services
     */

    // Fetch all patients
    public List<PatientRecordDTO> getAllPatients() {
        return restTemplate.exchange(
                PATIENT_SERVICE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientRecordDTO>>() {
                }
        ).getBody();
    }

    // Fetch a specific patient record by ID
    public PatientRecordDTO getPatientRecordById(Long id) {
        String url = PATIENT_SERVICE_URL + "/" + id;
        return restTemplate.getForObject(url, PatientRecordDTO.class);
    }

    public PatientRecordWithAppointmentsDTO getPatientRecordWithAppointmentsById(Long id) {

        PatientRecordDTO patientRecordDTO = getPatientRecordById(id);

        String url = APPOINTMENT_SERVICE_URL + "?patientRecordId=" + id;
        List<AppointmentDTO> appointments = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AppointmentDTO>>() {
                }
        ).getBody();

        return new PatientRecordWithAppointmentsDTO(patientRecordDTO, appointments);
    }

    // Fetch all appointments across the system
    public List<AppointmentDTO> getAllAppointments(String date) {
        String url = APPOINTMENT_SERVICE_URL;
        if (date != null) {
            url += "?date=" + date;
        }

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AppointmentDTO>>() {
                }
        ).getBody();
    }

    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        String url = APPOINTMENT_SERVICE_URL + "/" + id;
        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(appointmentDTO),
                AppointmentDTO.class
        ).getBody();
    }

    public List<String> getAllNotifications() {
        return restTemplate.exchange(
                NOTIFICATION_SERVICE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                }
        ).getBody();
    }

//    public String sendNotification(String message) {
//        return restTemplate.postForObject(NOTIFICATION_SERVICE_URL + "/send", message, String.class);
//    }
//
//    public String sendDailyNotifications() {
//        return restTemplate.postForObject(NOTIFICATION_SERVICE_URL + "/daily", null, String.class);
//    }

}
