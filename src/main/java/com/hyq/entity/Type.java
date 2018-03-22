package com.hyq.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class Type {

    private Integer id;
    private String typeName;
    private List<Article> articleList = new ArrayList<Article>();
    private int articleNum = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 100,nullable = false,unique = true)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @OneToMany(targetEntity = Article.class,mappedBy = "type",fetch = FetchType.EAGER)
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
    
    @Transient
    public int getArticleNum() {
        return articleNum;
    }
    
    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }
}
