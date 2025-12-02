package biblioteca_G_v2.demo.repository;

import biblioteca_G_v2.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}