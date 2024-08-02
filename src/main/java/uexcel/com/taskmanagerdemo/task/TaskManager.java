package uexcel.com.taskmanagerdemo.task;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
@Entity
public class TaskManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @Pattern(regexp = ".{2,}", message = "Name should have one 2 or more characters!")
    private String description;
    @NotNull(message = "Start date is required!")
    private LocalDate starts;
    @NotNull(message = "End date is required!")
    private LocalDate ends;
    private boolean status;

    public TaskManager(){}

    public TaskManager(int id, String username, String description,
                       LocalDate starts, LocalDate ends, boolean done) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.starts = starts;
        this.ends = ends;
        this.status = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getStarts() {
        return starts;
    }

    public void setStarts(LocalDate starts) {
        this.starts = starts;
    }

    public LocalDate getEnds() {
        return ends;
    }

    public void setEnds(LocalDate ends) {
        this.ends = ends;
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "taskId=" + id +
                ", taskName='" + username + '\'' +
                ", taskDescription='" + description + '\'' +
                ", taskStart=" + starts +
                ", taskEnd=" + ends +
                ", taskStatus=" + status +
                '}';
    }
}
