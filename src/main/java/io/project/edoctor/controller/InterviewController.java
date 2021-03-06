package io.project.edoctor.controller;

import io.project.edoctor.model.Message;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.service.InterviewService;
import io.project.edoctor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InterviewController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    InterviewService interviewService;

    private String botName = "E-doctor";
    private String firstMessage = "Please write down your symptoms, and we'll try to help you with your preliminary diagnosis.";

    private List<Message> messages = new ArrayList<>();

    private String name;
    private String sex;

    @GetMapping("/chat")
    public String showChat(Model model, Authentication auth) {

        messages.clear();
        messages.add(new Message(botName, firstMessage));

        User user = userService.findByEmail(auth.getName());
        name = user.getUserData().getName();
        sex = user.getUserData().getGender();
        interviewService.setUser(user);

        interviewService.setMentionList(new ArrayList<>());
        interviewService.setEvidences(new ArrayList<>());
        interviewService.setPossibleAnswers(new ArrayList<>());
        interviewService.setItFirstRequest(true);
        interviewService.setItFirstTextMessage(true);
        interviewService.setItYesNoQuestion(false);
        interviewService.setItQuestionTime(false);
        interviewService.setInterviewFinished(false);

        model.addAttribute("inputType", 1);
        model.addAttribute("botName", botName);
        model.addAttribute("userSex", sex);
        model.addAttribute("userMessage", new Message());
        model.addAttribute("messages", messages);

        return "chat";
    }

    @PostMapping("/chat")
    public String updateMessages(@ModelAttribute("userMessage") @Valid Message userMessage, BindingResult bindingResult, Model model) {
        userMessage.setSender(name);
        messages.add(userMessage);
        model.addAttribute("userMessage", new Message());
        model.addAttribute("botName", botName);
        model.addAttribute("userSex", sex);

        String answer = interviewService.getBotAnswer(userMessage.getValue());

        messages.add(new Message(botName, answer));
        model.addAttribute("messages", messages);

        if (interviewService.isInterviewFinished()) {
            model.addAttribute("inputType", 3);
        }
        else if (interviewService.isItYesNoQuestion() || interviewService.isItQuestionTime()) {
            model.addAttribute("inputType", 2);
            model.addAttribute("answers", interviewService.getPossibleAnswers());
        }
        else {
            model.addAttribute("inputType", 1);
        }

        return "chat";
    }
}
