package uexcel.com.taskmanagerdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uexcel.com.taskmanagerdemo.task.TaskManager;

import java.util.List;

public interface TaskManagerRepository extends JpaRepository<TaskManager, Integer> {
    List<TaskManager> findByUsername(String username);
}
