package com.hyq.controller;

import com.hyq.entity.Type;
import com.hyq.service.BaseService;
import com.hyq.util.CheckUtil;
import com.hyq.util.Constants;
import com.hyq.util.PropUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/type")
public class TypeController {
    
    @Resource
    private BaseService baseService;
    
    @RequestMapping( "/list")
    public String list(Map<String,Object> map) throws Exception {
        map.put("subPage", PropUtil.getValue(Constants.TYPE_JSP_LOCATION)+"typeManager.jsp");
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"main";
    }
    
    @RequestMapping(value = "/delete/{id}",method= RequestMethod.GET)
    public String delete(@PathVariable(value="id") Integer id){
        Set<Integer> ids = new HashSet<Integer>();
        ids.add(id);
        baseService.delete(Type.class,ids);
        return "redirect:/type/list";
    }
    
    @RequestMapping("/save")
    public String save(Type type){
        if (CheckUtil.isNotNull(type)){
            Integer id = type.getId();
            if (CheckUtil.isNotNull(id)){
                baseService.update(type);
            }else {
                baseService.save(type);
            }
        }
        return "redirect:/type/list";
    }
    
}
