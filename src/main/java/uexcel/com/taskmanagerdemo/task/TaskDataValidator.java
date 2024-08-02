package uexcel.com.taskmanagerdemo.task;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskDataValidator {

    public String validate(TaskManager task) {

        assert task != null;

//        if (task.getUsername().trim().isBlank() ||task.getUsername()==null || task.getUsername().trim().isEmpty()) {
//            return "taskNameError:Task name is required.";
//        }
//
//        if (task.getUsername().length() < 3 || task.getUsername().length() > 15) {
//            return "taskNameError:Task name must be ≥ 5 and ≤ 15 characters long.";
//        }
//
//        if(checkSpecialChars(task.getUsername(), null)){
//            return "taskNameError:Task name contains special characters.";
//        }

        if (task.getDescription().trim().isBlank() ||task.getDescription()==null ||
                task.getDescription().trim().isEmpty()) {
            return "taskDesError:Description is required.";
        }

        if (task.getDescription().length() < 5 || task.getDescription().length() > 50) {
            return "taskDesError:Description must be ≥ 5 and ≤ 50 characters long.";
        }

        if (checkSpecialChars(task.getDescription(), "-")){
            return "taskDesError:Task description contains special characters.";
        }

        if (task.getStarts() == null) {
            return "taskStartError:Start date is required field.";
        }

        if(checkDate(task.getStarts())){
            return "taskStartError:Oops! Past date.";
        }

        if (task.getEnds() == null) {
            return "taskEndError:End date is required field.";
        }

        if(checkDate(task.getEnds())){
            return "taskEndError:Oops! Past date.";
        }

        if (task.getStarts().isAfter(task.getEnds())) {
            return "taskEndError:Oops! Task target completion date is before the task start date.";
        }

        return "success";

    }

    public boolean checkSpecialChars(String value, String specialChars){
        if(specialChars != null) {
            return !value.matches("[a-zA-Z0-9 .-]+");
        }
        return !value.matches("[a-zA-Z0-9. ]+");
    }

    public boolean checkDate(LocalDate date){
        return !date.isAfter(LocalDate.now()) && !date.isEqual(LocalDate.now());
    }

}
