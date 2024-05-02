package com.theraven.task.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    /*
     * Field for the soft deletion.
     *
     * When a customer is deleted, this field is set to false
     * to mark the record as logically deleted,
     * preserving it in the database for future reference. (recovery)
     */
    @Column(name = "is_active")
    private boolean active;

    @PrePersist
    protected void onCreate() {
        long time = new Date().getTime();
        createdAt = time;
        updatedAt = time;
        active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date().getTime();
    }
}
