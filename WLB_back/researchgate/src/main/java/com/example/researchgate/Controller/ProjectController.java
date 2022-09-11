package com.example.researchgate.Controller;

import com.example.researchgate.Dao.UserDao;
import com.example.researchgate.Dto.CreateprojectDTO;
import com.example.researchgate.Dto.JoinprojectDTO;
import com.example.researchgate.Dto.SelectprojDTO;
import com.example.researchgate.Entity.Project;
import com.example.researchgate.Utils.Result;
import com.example.researchgate.jpa.ProjectJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class ProjectController {
    @Autowired
    private ProjectJpa projectJpa;

    @Autowired
    private UserDao userDao;

    @PostMapping("/joinProject")
    public Result joinProject(@RequestBody JoinprojectDTO dto) {
        Optional<Project> projectOptional = projectJpa.findById(dto.getProjid());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            String userName = userDao.selectByUserId(String.valueOf(dto.getUserid())).getUserName();
            project.setProjectjoiner(project.getProjectjoiner() + "," + userName);
            projectJpa.save(project);
            return Result.succ();
        } else {
            return Result.fail("not exists");
        }
    }

    @PostMapping("/createProj")
    public Result createProject(@RequestBody CreateprojectDTO dto) {
        Project project = Project.builder()
                .projdate(new Date())
                .projuserid(1)
                .projdescription(dto.getProjectdescription())
                .projname(dto.getProjname())
                .projectjoiner("")
                .build();
        projectJpa.save(project);
        return Result.succ();
    }


    @PostMapping("/selectProj")
    public Result selectProj(@RequestBody SelectprojDTO dto) {
        List<Project> projects;

        if (dto.getKeyword().isEmpty()) {
            projects = projectJpa.findAll();
        } else {
            projects = projectJpa.findByProjdescriptionContains(dto.getKeyword());
        }

        List<Project> data = projects.stream().map(project -> {
            int projectflag = project.getProjectjoiner().contains(userDao.selectByUserId(String.valueOf(dto.getUserid())).getUserName()) ? 1 : 0;
            project.setProjectflag(projectflag);
            return project;
        }).collect(Collectors.toList());
        return Result.succ(data);
    }
}
