package com.management.student.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity {

    private static final long serialVersionUID = 5714250117051423322L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createDateTime;
    private LocalDateTime lastModifyDateTime;

    @PrePersist
    public void init() {
        if (this.createDateTime == null) {
            this.createDateTime = LocalDateTime.now ();
        }
        this.lastModifyDateTime = LocalDateTime.now ();
    }
}
