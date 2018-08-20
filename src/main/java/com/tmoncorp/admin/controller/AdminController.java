package com.tmoncorp.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class AdminController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView redirectAdminMain() {
        //return getModelAndView("redirect:dashboard");
        return null;
    }

    @RequestMapping(value = {"/*", "/**"}, method = RequestMethod.GET)
    public ModelAndView routeAdminMain() {
        //return getModelAndView("admin");
        return null;
    }

}
