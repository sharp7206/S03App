package com.app.s03.cmn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/page/*")
public class PageViewController {

	private final String PRGM_NM = "_prgmcd_";

	@GetMapping(value = "{view}.do")
	public String view(@PathVariable("view") String view, Model model) {
		return view;
	}

	@PostMapping(value = "{view}.do")
	public String view2(@PathVariable("view") String view, Model model) {
		return view;
	}

	@GetMapping(value = "{module}/{view}.do")
	public String moduleView(@PathVariable("module") String module, @PathVariable("view") String view, Model model) {
		setModel(model, module, null, view);
		return module + "/" + view;
	}

	@PostMapping(value = "{module}/{view}.do")
	public String moduleView2(@PathVariable("module") String module, @PathVariable("view") String view, Model model) {
		setModel(model, module, null, view);
		return module + "/" + view;
	}

	@GetMapping(value = "{module}/{sub}/{view}.do")
	public String menuView(@PathVariable("module") String module, @PathVariable("sub") String sub, @PathVariable("view") String view, Model model) {
		setModel(model, module, sub, view);
		return module + "/" + sub + "/" + view;
	}
    
	@PostMapping(value = "{module}/{sub}/{view}.do")
	public String menuView2(@PathVariable("module") String module, @PathVariable("sub") String sub, @PathVariable("view") String view, Model model) {
		setModel(model, module, sub, view);
		return module + "/" + sub + "/" + view;
	}

	private void setModel(Model model, String module, String sub, String view) {
		if (module.length() == 2) {
			if (sub == null) {
				//model.addAttribute(PRGM_NM, view.length() > 6 ? view.substring(0, 6) : view);
				model.addAttribute(PRGM_NM, view);
			} else {
				if (sub.length() >= 3) {
					model.addAttribute(PRGM_NM, view);
				}
			}
		}
	}

}