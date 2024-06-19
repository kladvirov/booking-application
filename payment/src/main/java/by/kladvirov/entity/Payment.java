package by.kladvirov.entity;

import by.kladvirov.enums.PaymentStatus;
import by.kladvirov.dto.payment.json.Info;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "info", columnDefinition = "jsonb")
    private Info info;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "expires_at")
    private ZonedDateTime expiresAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reservation_id")
    private Long reservationId;

    @PrePersist
    private void setFields(){
        this.createdAt = ZonedDateTime.now();
        this.expiresAt = createdAt.plusHours(1);
        this.status = PaymentStatus.WAITING;
    }

}
