package com.hyq.dao;

import com.hyq.entity.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface BaseDao<Entity> {

    /**
     * 保存一个实体
     * @param entity
     * @return
     */
    public Serializable save(Entity entity);

    /**
     * 删除一个实体
     * @param entity
     */
    public void delete(Entity entity);

    /**
     * 更新一个实体
     * @param entity
     */
    public void update(Entity entity);

    /**
     * 保存或更新一个实体
     * @param entity
     */
    public void saveOrUpdate(Entity entity);

    /**
     * 合并一个实体
     * @param entity
     * @return
     */
    public Object merge(Entity entity);

    /**
     * 根据id获取一个实体
     * @param c
     * @param id
     * @return
     */
    public Entity get(Class<Entity> c, Serializable id);

    /**
     * 通过id集合删除一批实体
     * @param c
     * @param ids
     */
    public void  delete(Class<Entity> c, Set<Integer> ids);

    public Entity getEntityByUniqueField(Class<Entity> clazz,String fieldName,String fieldValue);

    public void updateOneFieldOfEntity(Class<Entity> c,String fieldName,Object fieldValue,int id) throws IllegalAccessException, NoSuchFieldException;

    public List<Entity> find(Class<Entity> c, Entity entity, PageBean pageBean) throws Exception;
    
    public Entity find(Class<Entity> c, Map<String,Object> conditons) throws Exception;
    
    public List<Entity> find(String hql);
    
    public List<Object[]> executeSql(String sql);
}
