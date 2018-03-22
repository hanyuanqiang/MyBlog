package com.hyq.dao.impl;

import com.hyq.dao.BaseDao;
import com.hyq.entity.PageBean;
import com.hyq.util.CheckUtil;
import com.hyq.util.DateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

@Repository("baseDao")
@Transactional
@SuppressWarnings("all")
public class BaseDaoImpl<Entity> implements BaseDao<Entity> {

	private SessionFactory sessionFactory;

	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	private void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Serializable save(Entity entity) {
		return this.getCurrentSession().save(entity);
	}

	public void delete(Entity entity) {
		this.getCurrentSession().delete(entity);
	}

	public void update(Entity entity) {
		this.getCurrentSession().update(entity);
	}

	public void saveOrUpdate(Entity entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	public Object merge(Entity entity) {
		return this.getCurrentSession().merge(entity);
	}

	public Entity get(Class<Entity> c, Serializable id) {
		return this.getCurrentSession().get(c,id);
	}

	public void delete(Class<Entity> c, Set<Integer> ids) {
		for (Integer id : ids){
			Object entity = this.get(c,id);
			this.delete((Entity) entity);
		}
	}

	/**
	 * 通过某个唯一字段获取实体
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Entity getEntityByUniqueField(Class<Entity> clazz, String fieldName, String fieldValue) {
		String hql = "from "+clazz.getSimpleName()+" where "+fieldName+" = :"+fieldName;
		return (Entity) this.getCurrentSession().createQuery(hql).setParameter(fieldName,fieldValue).uniqueResult();
	}

	/**
	 * 通过id只更新实体的某一个字段
	 * @param c
	 * @param fieldName
	 * @param fieldValue
	 * @param id
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public void updateOneFieldOfEntity(Class<Entity> c, String fieldName, Object fieldValue, int id) throws IllegalAccessException, NoSuchFieldException {
		Entity entity = this.get(c,id);
		Field field = c.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(entity,fieldValue);
		this.update(entity);
	}

	public List<Entity> find(Class<Entity> c, Entity entity, PageBean pageBean) throws Exception{
		String other_hql = "";
		String className = c.getSimpleName();
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		Field[] fields = c.getDeclaredFields();
		if (CheckUtil.isNotNull(entity)){
			for (Field field: fields){
				field.setAccessible(true);
				String fieldName = field.getName();
				Object fieldValue = field.get(entity);
				if (CheckUtil.isNotEmpty(fieldValue)){
					String fieldValueClassName = fieldValue.getClass().getName();
					if (fieldValueClassName.startsWith("com.hyq.entity") && !fieldValueClassName.contains("enum_")){
						Field f = fieldValue.getClass().getDeclaredField("id");
						f.setAccessible(true);
						conditionMap.put(fieldName+"."+f.getName(),f.get(fieldValue));
					}else{
						if (field.getClass().getSimpleName().equals("String")){
							conditionMap.put(fieldName,"%"+fieldValue+"%");
						}else if(CheckUtil.isEqual(field.getType().getName(),"java.util.Date")){
							System.out.println(DateUtil.transDate2Str((Date) fieldValue));
							other_hql = " DATE_FORMAT(createTime,'%Y年%m月') like '"+DateUtil.transDate2Str((Date) fieldValue,DateUtil.DATE_FORMAT_PATTERN_MONTH_CN)+"'";
						}else{
							conditionMap.put(fieldName,fieldValue);
						}
					}
				}
			}
		}

		StringBuffer hql = new StringBuffer("from "+c.getSimpleName());
		Set<String> keys = conditionMap.keySet();
		if (keys.size()>0){
			hql.append(" where ");
		}
		boolean tip = false;
		for (String key : keys){
			if (tip){
				hql.append(" and ");
			}
			hql.append(key+" = :"+this.handePoint(key));
			tip = true;
		}
		if (CheckUtil.isNotEmpty(other_hql)){
			if (tip){
				hql.append(" and "+other_hql);
			}else{
				hql.append(" where "+other_hql);
			}
		}
		
		hql.append(" order by id DESC");

		System.out.println(hql.toString());

		Query query = this.getCurrentSession().createQuery(hql.toString());
		for (String key : keys){
			query = query.setParameter(this.handePoint(key),conditionMap.get(key));
		}
		if (CheckUtil.isNotNull(pageBean)){
			query = query.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getPageSize());
		}
		return query.getResultList();
	}
	
	public Entity find(Class<Entity> c, Map<String, Object> conditons) throws Exception {
		StringBuffer hql = new StringBuffer("from "+c.getSimpleName());
		Set<String> keys = conditons.keySet();
		if (keys.size()>0){
			hql.append(" where ");
		}
		boolean tip = false;
		for (String key : keys){
			if (tip){
				hql.append(" and ");
			}
			hql.append(key+" = :"+this.handePoint(key));
			tip = true;
		}
		
		System.out.println(hql.toString());
		
		Query query = this.getCurrentSession().createQuery(hql.toString());
		
		for (String key : keys){
			query = query.setParameter(this.handePoint(key),conditons.get(key));
		}
		try {
			return (Entity) query.getSingleResult();
		}catch (Exception e){
			return null;
		}
	}
	
	public List<Entity> find(String hql) {
		return this.getCurrentSession().createQuery(hql).getResultList();
	}
	
	private static String handePoint(String s){
		char[] chars = s.toCharArray();
		
		for (int i = 0;i<chars.length;i++){
			if (chars[i] == '.'){
				chars[i] = '_';
			}
		}
		
		return String.valueOf(chars);
	}
	
	
	public List<Object[]> executeSql(String sql){
		List<Object[]> objectList = this.getCurrentSession().createNativeQuery(sql).getResultList();
		return objectList;
	}
	
}