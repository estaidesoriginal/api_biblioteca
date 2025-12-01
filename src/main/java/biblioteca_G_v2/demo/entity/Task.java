package biblioteca_G_v2.demo.entity;

import jakarta.persistence.*;
import lombok.Data; // Si usas Lombok

@Entity
@Data // Genera getters y setters automáticamente
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean completed;
}