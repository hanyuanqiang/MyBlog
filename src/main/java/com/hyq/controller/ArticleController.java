package com.hyq.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyq.entity.Article;
import com.hyq.entity.PageBean;
import com.hyq.entity.Type;
import com.hyq.entity.enum_.Article_visitAuth;
import com.hyq.service.BaseService;
import com.hyq.util.CheckUtil;
import com.hyq.util.Constants;
import com.hyq.util.GetterUtil;
import com.hyq.util.PropUtil;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/article")
public class ArticleController {
    
    @Resource
    private BaseService baseService;
    
    /**
     * 格式化时间
     * @param dataBinder
     */
    @InitBinder
    public void intDate(WebDataBinder dataBinder){
        dataBinder.addCustomFormatter(new DateFormatter("yyyy年MM月"));
    }
    
    @RequestMapping( "/list")
    public String list(Article s_article,Map<String,Object> map,Integer page,HttpSession session) throws Exception {
        //当page为空的时候，清空所有查询条件
        if (CheckUtil.isEmpty(page)){
            session.setAttribute("createTime",null);
            session.setAttribute("typeId",null);
            session.setAttribute("visitAuth",null);
            page = 1;
        }
        
        if (CheckUtil.isNotNull(s_article)){
            if (CheckUtil.isNotNull(s_article.getCreateTime())){
                session.setAttribute("createTime",s_article.getCreateTime());
            }
            if (CheckUtil.isNotNull(s_article.getType())){
                if (CheckUtil.isNotNull(s_article.getType().getId())){
                    session.setAttribute("typeId",s_article.getType().getId());
                }
            }
            if (CheckUtil.isNotNull(s_article.getVisitAuth())){
                session.setAttribute("visitAuth",s_article.getVisitAuth());
            }
        }
        
        if (CheckUtil.isNotNull(session.getAttribute("createTime"))){
            s_article.setCreateTime((Date) session.getAttribute("createTime"));
        }
    
        if (CheckUtil.isNotNull(session.getAttribute("visitAuth"))){
            s_article.setVisitAuth((Article_visitAuth) session.getAttribute("visitAuth"));
        }
        
        if(CheckUtil.isNotNull(session.getAttribute("typeId"))){
            Integer typeId = (Integer) session.getAttribute("typeId");
            Type type = new Type();
            type.setId(typeId);
            s_article.setType(type);
        }
        
        int pageSize = GetterUtil.getInteger(PropUtil.getValue(Constants.PAGE_SIZE));
        List<Article> articleList = baseService.find(Article.class,s_article, new PageBean(page,pageSize));
        map.put("page",page);
        int total = baseService.find(Article.class,s_article,null).size();
        int count = (total/pageSize)+1;     //总页数
        map.put("count",count);
        map.put("articleList",articleList);
        map.put("subPage",PropUtil.getValue(Constants.ARTICLE_JSP_LOCATION)+"list.jsp");
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"main";
    }
    
    @RequestMapping(value = "/detail/{id}",method= RequestMethod.GET)
    public String detail(@PathVariable(value="id") Integer id, Map<String,Object> map) throws Exception {
        Article article = (Article) baseService.get(Article.class,id);
        map.put("article",article);
        map.put("subPage",PropUtil.getValue(Constants.ARTICLE_JSP_LOCATION)+"detail.jsp");
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"main";
    }
    
    @RequestMapping( "/edit")
    public String edit(@RequestParam(value="id",required = false) Integer id,Map<String,Object> map) throws Exception {
        if (CheckUtil.isNotNull(id)){
            Article article = (Article) baseService.get(Article.class,id);
            map.put("article",article);
        }
        map.put("subPage",PropUtil.getValue(Constants.ARTICLE_JSP_LOCATION)+"edit.jsp");
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"main";
    }
    
    @RequestMapping( "/save")
    public String save(Article article){
        if (CheckUtil.isNotNull(article)){
            Integer id = article.getId();
            if (CheckUtil.isNotNull(id)){
                baseService.update(article);
            }else {
                baseService.save(article);
            }
            return "redirect:/article/list";
        }else{
            return "redirect:/article/edit";
        }
    }
    
    @RequestMapping(value = "/delete/{id}",method= RequestMethod.GET)
    public String delete(@PathVariable(value="id") Integer id){
        Article article = (Article) baseService.get(Article.class,id);
        baseService.delete(article);
        return "redirect:/article/list";
    }
    
    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(HttpServletRequest request,@RequestParam(value = "articleFile",required = false)CommonsMultipartFile[] files) throws Exception {
        //保存文件的路径
        String filePath = request.getServletContext().getRealPath("/files/article_pic/");
        Map<String,Object> result = new HashMap<String, Object>();
        List<String> pics = new ArrayList<String>();
        if (CheckUtil.isNotNull(files)){
            for (CommonsMultipartFile file : files){
                String oldFileName = file.getOriginalFilename();
                String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
                String newFileName = GetterUtil.getUUID()+suffix;
                String finalFilePath = filePath+newFileName;
                file.transferTo(new File(finalFilePath));
                String newAvatarUrl = request.getContextPath()+"/files/article_pic/"+newFileName;
                pics.add(newAvatarUrl);
            }
        }
        result.put("data",pics);
        result.put("errno",0);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(result);
    }
    
}
