package thongdanghoang.resourceserver.shared;

import jakarta.persistence.*;

import java.util.Objects;

@lombok.Getter
@lombok.Setter
@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdDate;

    @Column(name = "updated_at", nullable = false)
    private java.time.LocalDateTime updatedDate;

    @Version
    @Column(name = "version", nullable = false)
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
        if (!(o instanceof BaseEntity that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
