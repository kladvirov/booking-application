package by.andron.model;

import by.kladvirov.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("status <> 'DELETED'")
@Table(name = "service_providers")
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "type")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @PreRemove
    public void setStatus() {
        this.status = Status.DELETED;
        this.deletedAt = ZonedDateTime.now();
        this.updatedAt = this.deletedAt;
    }

    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = ZonedDateTime.now();
    }

    @PrePersist
    public void setField(){
        this.createdAt = ZonedDateTime.now();
    }

}