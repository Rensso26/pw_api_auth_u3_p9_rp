package uce.edu.web.api.auth.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario-secuencia", allocationSize = 1)
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    public Long id;

    public String username;
    public String password;
    public String role;

}