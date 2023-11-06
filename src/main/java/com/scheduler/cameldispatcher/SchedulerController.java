package com.scheduler.cameldispatcher;

// */ 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.cameldispatcher.repository.scheduler.SchedulerRepo;
import com.scheduler.cameldispatcher.repository.user.UserRepo;
//  */

@RestController
public class SchedulerController {

    // */
    @Autowired
    private SchedulerRepo repo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add")
    public String saveScheduler(@RequestBody Scheduler scheduler) {
        repo.save(scheduler);

        return "Added Successfully";
    }

    @PostMapping("/addUser")
    public String saveUser(@RequestBody User user) {
        userRepo.save(user);

        return "Added Successfully";
    }

    @GetMapping("/all")
    public List<Scheduler> getSchedulers() {

        return repo.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteScheduler(@PathVariable String id) {
        repo.deleteById(id);

        return "Deleted Successfully";
    }
    // */

}
