package com.hyq.entity;

import com.hyq.entity.enum_.Article_source;
import com.hyq.entity.enum_.Article_visitAuth;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_article")
public class Article {

    private Integer id;     //主键
    private String title;       //标题
    private String content;     //内容
    private Date createTime;        //创建时间
    private Date updateTime;        //最后一次更新时间
    private String keywords;        //本篇文章关键词
    private Article_visitAuth visitAuth;        //访问权限

    private Article_source source;      //文章来源

    private Type type;      //文章归属类型

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(length = 100,nullable = false,unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Lob
    @Column(columnDefinition = "TEXT",nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(updatable = false,columnDefinition = "datetime default now()",insertable = false)  //不允许插入和更新该字段
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(updatable = false,columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP default CURRENT_TIMESTAMP",insertable = false)  //不允许插入和更新该字段
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @Column(length = 200)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    public Article_visitAuth getVisitAuth() {
        return visitAuth;
    }

    public void setVisitAuth(Article_visitAuth visitAuth) {
        this.visitAuth = visitAuth;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    public Article_source getSource() {
        return source;
    }

    public void setSource(Article_source source) {
        this.source = source;
    }
    
    @ManyToOne
    @JoinColumn(name = "typeId",nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
