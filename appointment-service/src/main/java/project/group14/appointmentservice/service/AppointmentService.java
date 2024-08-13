package project.group14.appointmentservice.service;

import project.group14.appointmentservice.dto.AppointmentDTO;
import project.group14.appointmentservice.model.Appointment;
import project.group14.appointmentservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<AppointmentDTO> getAppointmentsByPatientRecordId(Long patientRecordId) {
        return appointmentRepository.findByPatientRecordId(patientRecordId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<AppointmentDTO> getAppointmentById(Long id) {
        return appointmentRepository.findById(id).map(this::convertToDTO);
    }

    public List<AppointmentDTO> getAppointmentsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return appointmentRepository.findByAppointmentDateTimeBetween(startOfDay, endOfDay).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = convertToEntity(appointmentDTO);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);
        return convertToDTO(appointment);
    }

    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment existingAppointment = optionalAppointment.get();
            existingAppointment.setStatus(appointmentDTO.getStatus());
            existingAppointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
            existingAppointment.setDoctorName(appointmentDTO.getDoctorName());
            existingAppointment.setClinicalNotes(appointmentDTO.getClinicalNotes());
            existingAppointment.setAppointmentDuration(appointmentDTO.getAppointmentDuration());
            existingAppointment.setUpdatedAt(LocalDateTime.now());
            appointmentRepository.save(existingAppointment);
            return convertToDTO(existingAppointment);
        } else {
            throw new RuntimeException("Appointment not found");
        }
    }

    // no appointment need to be deleted
//    public void deleteAppointment(Long id) {
//        appointmentRepository.deleteById(id);
//    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        return new AppointmentDTO(appointment.getId(), appointment.getStatus(), appointment.getAppointmentDateTime(), appointment.getAppointmentDuration(), appointment.getDoctorName(), appointment.getClinicalNotes(), appointment.getPatientRecordId(), appointment.getCreatedAt(), appointment.getUpdatedAt());
    }

    private Appointment convertToEntity(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setPatientRecordId(appointmentDTO.getPatientRecordId());
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
        appointment.setAppointmentDuration(appointmentDTO.getAppointmentDuration());
        appointment.setDoctorName(appointmentDTO.getDoctorName());
        appointment.setClinicalNotes(appointmentDTO.getClinicalNotes());

        return appointment;
    }
}
