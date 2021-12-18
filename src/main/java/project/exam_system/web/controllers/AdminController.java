package project.exam_system.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.exam_system.model.entities.Exam;
import project.exam_system.model.view.UsersAllViewModel;
import project.exam_system.service.ExamService;
import project.exam_system.service.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ExamService examService;

    public AdminController(ModelMapper modelMapper, UserService userService, ExamService examService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.examService = examService;
    }

    @GetMapping("/panel")
    public String admin() {
        return "admin_dashboard";
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAllView(ModelAndView modelAndView) {
        modelAndView.addObject("users", userService.getAll()
                .stream()
                .map(userServiceModel -> modelMapper.map(userServiceModel, UsersAllViewModel.class))
                .collect(Collectors.toList()));

        modelAndView.setViewName("usersInfo");
        return modelAndView;
    }


    @GetMapping("/create-new")
    public String newExam(@ModelAttribute Exam exam,
                              ModelMap model) {


        return "new-exam";
    }


    @PostMapping("/create-new")
    public String addQuestion(@Valid Exam exam, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("numberOfQuestions", exam.getQuestions().size());
            return "new-exam";
        }
            examService.save(exam);

        return "redirect:panel";
    }
}
