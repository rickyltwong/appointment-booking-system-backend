package project.group14.medicalrecordservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.group14.authenticationservice.model.User;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
