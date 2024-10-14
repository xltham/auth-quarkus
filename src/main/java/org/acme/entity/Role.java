package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends PanacheEntity {
    @Column(unique = true)
    public String roleName;

    public Role(){}

    public Role(String roleName) throws Exception {
        this.roleName = roleName;
    }
}
