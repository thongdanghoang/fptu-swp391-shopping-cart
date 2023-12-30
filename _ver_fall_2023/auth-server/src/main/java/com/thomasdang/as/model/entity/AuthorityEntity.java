package com.thomasdang.as.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@lombok.RequiredArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "AUTHORITY")
public class AuthorityEntity extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @lombok.NonNull
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityEntity that = (AuthorityEntity) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
