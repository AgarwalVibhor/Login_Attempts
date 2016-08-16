package com.tcs.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/")
	public ModelAndView start(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("redirect:/hello");
		return model;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView admin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("admin");
		model.addObject("title", "Spring Security Login - Database Authentication");
		model.addObject("message", "This page is only for Admin people !");
		return model;
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ModelAndView hello(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("hello");
		model.addObject("title", "Spring Security Login - Database Authentication");
		model.addObject("message", "This page is for both the Users and Admin guys !");
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout)
	{
		ModelAndView model = new ModelAndView("login");
		if(error != null)
		{
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		
		if(logout != null)
		{
			model.addObject("message", "You have logged out successfully!!");
		}
		return model;
	}
	
	// Customize the error message
	public String getErrorMessage(HttpServletRequest request, String key)
	{
		String error = null;
		Exception exception = (Exception) request.getSession().getAttribute(key);
		if(exception instanceof BadCredentialsException)
		{
			error = "Invalid Username and Password !!";
		}
		else if(exception instanceof LockedException)
		{
			error = exception.getMessage();
		}
		else
		{
			error = "Invaid Username and Password !!";
		}
		return error;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("403");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) auth.getPrincipal();
		model.addObject("username", user.getUsername());
		return model;
	}

}
