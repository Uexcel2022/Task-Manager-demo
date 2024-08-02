package uexcel.com.taskmanagerdemo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uexcel.com.taskmanagerdemo.service.JpaTaskManagerService;
import uexcel.com.taskmanagerdemo.service.TaskManagerService;
import uexcel.com.taskmanagerdemo.task.TaskManager;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

@Controller
@SessionAttributes(value = {"username"})
public class TaskManagerJpaController {

    private static final Logger log = LoggerFactory.getLogger(TaskManagerJpaController.class);
    private  final JpaTaskManagerService jpaTaskManagerService;

    public TaskManagerJpaController(JpaTaskManagerService jpaTaskManagerService, TaskManagerService taskManagerService) {
        this.jpaTaskManagerService = jpaTaskManagerService;
    }

    @GetMapping("task-list")
    public String showTaskPage(ModelMap modelMap, @SessionAttribute(required = false) String username){
        modelMap.put("tasks", jpaTaskManagerService.findByUserName(logInUsername.get()));
        return "viewTask";
    }


    @PostMapping("delete-task")
    public String deleteTaskById(@RequestParam int id){
        jpaTaskManagerService.deleteTaskById(id);
        return "redirect:/task-list";
    }

    @PostMapping("update-page")
    public String showUpdatePage(@RequestParam int id, ModelMap modelMap){
     Optional<TaskManager> task = jpaTaskManagerService.findTaskById(id);
     if(task.isPresent() && !task.get().getUsername().equals("non")) {
         modelMap.put("task", task.get());
         return "updateTask";
     }
        log.info("{}","Something went wrong: Could not find task by ID="+id);
     return "redirect:/task-list";
    }

    @PostMapping("update-task")
    public String taskUpdate(@Valid @ModelAttribute("task") TaskManager task,
                              BindingResult bindingResult,ModelMap modelMap){
        task.setUsername(logInUsername.get());
        String msg = jpaTaskManagerService.saveTask(task);
        if (!msg.equals("success")) {
            String[] errorMessage = msg.split(":");
            modelMap.put(errorMessage[0], errorMessage[1]);
            return "updateTask";
        }
        return "redirect:/task-list";
    }

    @GetMapping("add-task")
    public String showAddTaskPage(@ModelAttribute("task") TaskManager task,
                                   ModelMap modelMap){
        task.setStarts(LocalDate.now());
        task.setEnds(LocalDate.now().plusDays(7));
        modelMap.put("task", task);
        return "addTask";
    }

    @PostMapping("add-task")
    public String addTask(@Valid @ModelAttribute("task") TaskManager task,
                          BindingResult bindingResult, ModelMap modelMap){

        task.setUsername(logInUsername.get());
        String msg = jpaTaskManagerService.saveTask(task);
        if (!msg.equals("success")) {
            String[] errorMessage = msg.split(":");
            modelMap.put(errorMessage[0], errorMessage[1]);
            return "addTask";
        }

        return "redirect:/task-list";
    }

    @PostMapping("task-done")
    public String taskDone(@RequestParam int id){
        String msg = jpaTaskManagerService.taskDone(id);
        if (msg.equals("success")) {
            return "redirect:/task-list";
        }

        log.info("{}",msg);
        return "redirect:/task-list";
    }

   private final Supplier<String> logInUsername =()-> SecurityContextHolder.getContext().getAuthentication().getName();
}
