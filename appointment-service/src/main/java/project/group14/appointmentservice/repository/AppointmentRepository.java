package project.group14.appointmentservice.repository;

import project.group14.appointmentservice.model.Appointment;
import project.group14.medicalrecordservice.model.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientRecord(PatientRecord patientRecord);
}
