package uexcel.com.taskmanagerdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uexcel.com.myfirstwebapp.service.TaskManagerService;
import uexcel.com.myfirstwebapp.task.TaskDataValidator;
import uexcel.com.myfirstwebapp.task.TaskManager;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

//@Controller
@SessionAttributes(value = {"username"})
public class TaskManagerController {

    private static final Logger log = LoggerFactory.getLogger(TaskManagerController.class);
    private final TaskManagerService taskManagerService;
    private  final TaskDataValidator taskDataValidator;

    public TaskManagerController(TaskManagerService taskManagerService, TaskDataValidator taskDataValidator) {
        this.taskManagerService = taskManagerService;
        this.taskDataValidator = taskDataValidator;
    }

    @GetMapping("task-list")
    public String showTaskPage(ModelMap modelMap, @SessionAttribute(required = false) String username){

        modelMap.put("tasks", taskManagerService.findByUserName(getLogInUsername.get()));
        return "viewTask";
    }
    @PostMapping("delete-task")
    public String deleteTask(@RequestParam int taskId,
                             @SessionAttribute(required = false) String username){
        taskManagerService.removeTaskById(taskId);
        return "redirect:/task-list";
    }

    @PostMapping("update-page")
    public String showUpdatePage(@RequestParam int taskId, ModelMap modelMap,
                             @SessionAttribute(required = false) String username ){
     Optional<TaskManager> task = taskManagerService.findTaskById(taskId);
     if(task.isPresent()) {
         modelMap.put("task", task.get());
         log.info("{}",task.get());
         return "updateTask";
     }
     return "redirect:/task-list";
    }

    @PostMapping("update-task")
    public String taskUpdate(@ModelAttribute("task") TaskManager task,ModelMap modelMap,
                             @SessionAttribute(required = false) String username ){
        String msg = taskDataValidator.validate(task);
        if (!msg.equals("success")) {
            String[] errorMessage = msg.split(":");
            modelMap.put(errorMessage[0], errorMessage[1]);
            return "updateTask";
        }
        task.setUsername(getLogInUsername.get());
        taskManagerService.updateTask(task);
        return "redirect:/task-list";
    }

    @GetMapping("add-task")
    public String showAddTaskPage(@ModelAttribute("task") TaskManager task, ModelMap modelMap,
                                  @SessionAttribute(required = false) String username ){
        task.setStarts(LocalDate.now());
        task.setEnds(LocalDate.now().plusDays(7));
        modelMap.put("task", task);
        return "addTask";
    }

    @PostMapping("add-task")
    public String addTask(@ModelAttribute("task") TaskManager task, ModelMap modelMap,
                          @SessionAttribute(required = false) String username ){

        String msg = taskDataValidator.validate(task);
        if (!msg.equals("success")) {
            String[] errorMessage = msg.split(":");
            modelMap.put(errorMessage[0], errorMessage[1]);
            log.info("Task added: {} ",task);
            return "addTask";
        }

        task.setUsername(getLogInUsername.get());
        taskManagerService.addTask(task);
        return "redirect:/task-list";
    }

    @PostMapping("task-done")
    public String taskDone(@RequestParam int taskId,
                           @SessionAttribute(required = false) String username){

        taskManagerService.taskDone(taskId);
        return "redirect:/task-list";
    }

   private final Supplier<String> getLogInUsername =()-> SecurityContextHolder.getContext().getAuthentication().getName();
}
