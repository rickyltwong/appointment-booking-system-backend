package project.group14.medicalrecordservice.PatientRecordRepository;

import project.group14.medicalrecordservice.model.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {
}
