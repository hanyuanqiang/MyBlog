package com.hyq.service;

import com.hyq.entity.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by genius on 2017/3/14.
 */
public interface BaseService<Entity> {

    public Serializable save(Entity entity);

    public void delete(Entity entity);

    public void update(Entity entity);

    public void saveOrUpdate(Entity entity);

    public Object merge(Entity entity);

    public Entity get(Class<Entity> c, Serializable id);
    
    public void  delete(Class<Entity> c, Set<Integer> ids);
    
    public Entity getEntityByUniqueField(Class<Entity> clazz,String fieldName,String fieldValue);
    
    public void updateOneFieldOfEntity(Class<Entity> c,String fieldName,Object fieldValue,int id) throws IllegalAccessException, NoSuchFieldException;
    
    public List<Entity> find(Class<Entity> c, Entity entity, PageBean pageBean) throws Exception;
    
    public Entity find(Class<Entity> c, Map<String,Object> conditons) throws Exception;
    
    public List<Entity> find(String hql);
    
    public Map<String,String> classifyArticleByMonth();
}
