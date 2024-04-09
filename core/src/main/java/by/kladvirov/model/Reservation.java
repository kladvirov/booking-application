package by.kladvirov.model;

import by.kladvirov.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("status <> 'DELETED'")
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "date_from")
    private ZonedDateTime dateFrom;

    @Column(name = "date_to")
    private ZonedDateTime dateTo;

    @Column(name = "ordered_at")
    private ZonedDateTime orderedAt;

    @Column(name = "expires_at")
    private ZonedDateTime expiresAt;

    @Column(name = "user_id")
    private Long userId;

    @JoinColumn(name = "service_id")
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Service service;

    @PreRemove
    public void setStatus() {
        this.status = Status.DELETED;
    }

    @PrePersist
    public void setFields() {
        this.orderedAt = ZonedDateTime.now();
        this.expiresAt = ZonedDateTime.now().plusHours(1);
    }
}