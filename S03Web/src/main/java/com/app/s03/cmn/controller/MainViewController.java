package com.app.s03.cmn.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.service.CommonCodeService;
import com.app.s03.cmn.service.UserAuthorityService;
import com.app.s03.cmn.utils.Utility;


/**
-===============================================================================================================
- 메인화면처리
-===============================================================================================================
*/
@Controller
public class MainViewController {

	@Resource(name = "cmn.UserAuthorityService")
	private UserAuthorityService userAuthorityService;
    
    @Resource(name = "cmn.CommonCodeService")
    private CommonCodeService commonCodeService;

    @GetMapping(value = "/main.do")
	public String getMain(Model model, HttpServletRequest request) {
		return main(model,request);
	}

    @GetMapping(value = "/intro.do")
    public String intro(
    			@AuthenticationPrincipal ComUserDetails userDetails,
    			Model model, HttpServletRequest request) {

		if (userDetails == null) {
			return "redirect: login.do";
		} 
    	
        Map<String, Object> param = new LinkedHashMap<>();
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority auth : userDetails.getAuthorities()) {
            authorities.add(auth.getAuthority());
        }
        param.put("authorities",authorities);
        
        String syscd = ComConstants.SYS_CD;
        
        param.put("userid",userDetails.getUsername());
        /*
        List<?> userSys = userAuthorityService.getUserSystemList(param);
        if(StringUtils.isEmpty(syscd)) {
            if(userSys.size() > 0) {
                syscd = (String)((Map<String,Object>)userSys.get(0)).get("syscd");
            }
        }
        */
        userDetails.setSyscd(syscd);
        param.put("syscd",syscd);
        param.put("authorities",authorities);
        List<?> allList = userAuthorityService.getUserMenuTreeData(param);
        param.put("syscd", ComConstants.SYS_CD);
        List<?> codeList = commonCodeService.getAllCodeList(param);
        request.setAttribute("codeList", codeList);
        request.setAttribute("sysMenu", allList);
        request.setAttribute("userMenu", allList);
        request.setAttribute("_syscd", syscd);
        //request.setAttribute("userSys", userSys);
        return "intro";
    }
    
	@PostMapping(value = "/main.do")
	public String postMain(Model model, HttpServletRequest request) {
		return main(model,request);
	}

	

	@SuppressWarnings("unchecked")
	private String main(Model model, HttpServletRequest request) {
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
		List<?> allList = userAuthorityService.getUserMenuTreeData(param);
		param.put("syscd",syscd);
		List<?> subList = userAuthorityService.getUserMenuTreeData(param);
		try {
		    request.setAttribute("sideJson", Utility.toJSON(allList));
		}catch(Exception e) {
		    
		}
		request.setAttribute("sysMenu", allList);
		try {
			request.setAttribute("sysMenuJson", Utility.toJSON(allList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("userMenu", subList);
		request.setAttribute("_syscd", syscd);
		return "main";
	}
}
