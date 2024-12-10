package com.app.s03.cmn.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.service.UserAuthorityService;
@Controller
@RequestMapping(value = "/user")
public class SideTreeController {

	@Resource(name = "cmn.UserAuthorityService")
	private UserAuthorityService userAuthorityService;	
    /**
     * 로그인 View
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/sideTree.do")
    public String sideTree(Model model,
                            HttpServletRequest request) {
		Map<String, Object> param = new LinkedHashMap<>();
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority auth : userDetails.getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		param.put("authorities",authorities);
		String syscd = request.getParameter("syscd");
		if(StringUtils.isEmpty(syscd)) {
			syscd = userDetails.getSyscd();
		}
		param.put("userid",userDetails.getUsername());
		
		userDetails.setSyscd(syscd);
		param.put("syscd",syscd);
		List<?> subList = userAuthorityService.getUserMenuTreeData(param);
		request.setAttribute("sideMenu", subList);
		request.setAttribute("userMenu", subList);
		request.setAttribute("_syscd", syscd);
		return "sideTree";
    }
}
