package com.thomasdang.as.model.entity;


import javax.persistence.*;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "CREATED_DATE", nullable = false)
    private java.time.LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE", nullable = false)
    private java.time.LocalDateTime updatedDate;

    @Version
    @Column(name = "VERSION", nullable = false)
    private int version;

    @PrePersist
    protected void onCreate() {
        createdDate = java.time.LocalDateTime.now();
        updatedDate = createdDate;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = java.time.LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
