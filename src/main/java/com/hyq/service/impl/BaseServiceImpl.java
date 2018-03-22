package com.hyq.service.impl;

import com.hyq.dao.BaseDao;
import com.hyq.entity.PageBean;
import com.hyq.service.BaseService;
import com.hyq.util.GetterUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * Created by genius on 2017/3/13.
 */
@Service("baseService")
public class BaseServiceImpl<Entity> implements BaseService<Entity> {

    @Resource
    private BaseDao<Entity> baseDao;

    public Serializable save(Entity entity) {
        return baseDao.save(entity);
    }

    public void delete(Entity entity) {
        baseDao.delete(entity);
    }

    public void update(Entity entity) {
        baseDao.update(entity);
    }

    public void saveOrUpdate(Entity entity) {
        baseDao.saveOrUpdate(entity);
    }

    public Object merge(Entity entity) {
        return baseDao.merge(entity);
    }

    public Entity get(Class<Entity> c, Serializable id) {
        return baseDao.get(c,id);
    }

    public void delete(Class<Entity> c, Set<Integer> ids) {
        baseDao.delete(c,ids);
    }

    public Entity getEntityByUniqueField(Class<Entity> clazz, String fieldName, String fieldValue) {
        return baseDao.getEntityByUniqueField(clazz,fieldName,fieldValue);
    }

    public void updateOneFieldOfEntity(Class<Entity> c, String fieldName, Object fieldValue, int id) throws NoSuchFieldException, IllegalAccessException {
        baseDao.updateOneFieldOfEntity(c,fieldName,fieldValue,id);
    }
    
    public List<Entity> find(Class<Entity> c, Entity entity, PageBean pageBean) throws Exception {
        return baseDao.find(c,entity,pageBean);
    }
    
    public Entity find(Class<Entity> c, Map<String, Object> conditons) throws Exception {
        return baseDao.find(c,conditons);
    }
    
    public List<Entity> find(String hql) {
        return baseDao.find(hql);
    }
    
    public Map<String, String> classifyArticleByMonth() {
        Map<String,String> map = new LinkedHashMap<String, String>();
        String sql = "SELECT DATE_FORMAT(createTime,'%Y年%m月') AS MONTH,COUNT(*) AS COUNT " +
                "FROM t_article GROUP BY DATE_FORMAT(createTime,'%Y年%m月') ORDER BY DATE_FORMAT(createTime,'%Y年%m月') DESC";
        List<Object[]> result = baseDao.executeSql(sql);
        for (Object[] object : result){
            map.put(GetterUtil.getString(object[0]),GetterUtil.getString(object[1]));
        }
        return map;
    }
}
