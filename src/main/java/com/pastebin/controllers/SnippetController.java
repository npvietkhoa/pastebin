package com.pastebin.controllers;


import com.pastebin.entities.Snippet;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
public class SnippetController {

    final private SnippetRestController snippetRestController;
    @Autowired
    public SnippetController(SnippetRestController snippetRestController) {
        this.snippetRestController = snippetRestController;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getCodePage(@NotNull HttpServletResponse response, @PathVariable int id)  {
        response.addHeader("Content-Type", "text/html");

        try {
            ModelAndView model = new ModelAndView("codePage");
            model.addObject("pageTitle", "Code");
            Snippet snippet = snippetRestController.getSnippetById(id);
            model.addObject("snippetList", List.of(snippet));
            model.setViewName("codePage");
            return model;
        } catch (Exception e) {
            return new ModelAndView("404");
        }
    }
    @GetMapping(value = "/latest")
    public ModelAndView getLatestCodePage(@NotNull HttpServletResponse response)  {
        response.addHeader("Content-Type", "text/html");

        try {

            ModelAndView model = new ModelAndView("codePage");
            model.addObject("pageTitle", "Latest");
            model.addObject("snippetList", snippetRestController.getLastSnippets());

            model.setViewName("codePage");
            return model;
        } catch (Exception e) {
            return new ModelAndView("404");
        }
    }


    @GetMapping(value = "/new", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHTMLSubmitForm(@NotNull HttpServletResponse response) {
        try {
            response.addHeader("Content-Type", "text/html");
            return new ModelAndView("newCodePage");
        } catch (Exception e) {
            return new ModelAndView("404");
        }
    }
}
