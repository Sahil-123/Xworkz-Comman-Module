package com.xworkz.entity;

import com.xworkz.enums.ComplaintStatus;
import com.xworkz.enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "complaintHistory")
@NamedQueries({
        @NamedQuery(name = "findHistoryByComplaintID", query = "SELECT c FROM ComplaintHistoryDTO c WHERE c.complaintID = :complaintID")
})
public class ComplaintHistoryDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId; // Auto-increment primary key

    @Column(name = "complaintID")
    private Long complaintID;

    @Column(name = "Role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "status", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

//    @PrePersist
//    protected void onCreate() {
//        this.createdDate = LocalDateTime.now();
//    }
}
