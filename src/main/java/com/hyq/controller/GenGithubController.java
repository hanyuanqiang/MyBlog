package com.hyq.controller;

import com.hyq.entity.Article;
import com.hyq.entity.Type;
import com.hyq.entity.User;
import com.hyq.entity.enum_.Article_visitAuth;
import com.hyq.service.BaseService;
import com.hyq.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/github")
public class GenGithubController {
    
    //这种的公共部分(避免重复生成)
    private static String complete_public_part = null;
    
    //所有类型(避免重复查询)
    private static List<Type> static_typeList = null;
    
    //文章归档(避免重复查询)
    private static Map<String,String> static_classifyByMonth = null;
    
    //记录所有在文章内容中出现的图片文件名
    private static List<String> articlePicList = new ArrayList<String>();
    
    //存储用户头像的文件名(避免重复生成)
    private static String avatarPicFileName = null;
    
    @Resource
    private BaseService baseService;
    
    @RequestMapping( "/gen")
    public String gen(HttpServletRequest request) throws Exception {
        genIndex(request);  //生成首页
        genAllTypePage(request);    //生成所有类型页面
        genAllFilingPage(request);      //生成所有归档页面
        genAllArticlePage(request);     //生成所有文章页面
        syncFile(request);  //同步文件(包括清除多余、无用的文件)
        return "redirect:/article/list";
    }
    
    /**
     * 生成github主页（先生成主页的公共部分，再生成子页面中的内容）
     * @param request
     * @throws Exception
     */
    private void genIndex(HttpServletRequest request) throws Exception{
        String githubDir = PropUtil.getValue(Constants.GITHUB_DIR);
        String filePath = githubDir+"index.html";
        String replaceIndexSubPage = PropUtil.getValue(Constants.GITHUB_INDEX_SUBPAGE_REPLACE);
        //整个主页内容由公共部分和子页面组成，子页面显示所有文章列表
        String result = getComplete_public_part(request).replace(replaceIndexSubPage,genArticleListSubPage(null,null));
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
        FileUtils.writeStringToFile(new File(filePath),result,"UTF-8");
    }
    
    /**
     * 生成所有type页面(即type文件夹下的所有文件)
     * 各个页面的公共部分不变，只是子页面中的文章列表不同
     * @param request
     * @throws Exception
     */
    private void genAllTypePage(HttpServletRequest request) throws Exception{
        List<Type> typeList;
        if (CheckUtil.isNotNull(static_typeList)){
            typeList = static_typeList;
        }else{
            typeList = this.baseService.find(Type.class,null,null);
        }
        if (CheckUtil.isNull(complete_public_part)){
            complete_public_part = getComplete_public_part(request);
        }
        String replaceIndexSubPage = PropUtil.getValue(Constants.GITHUB_INDEX_SUBPAGE_REPLACE);
        String typeSaveDir = PropUtil.getValue(Constants.GITHUB_TYPES_SAVE_DIR);
        for (Type type : typeList){
            String htmlContent = complete_public_part.replace(replaceIndexSubPage,genArticleListSubPage(type,null));
            File file = new File(typeSaveDir);
            if (!file.exists()){
                file.mkdirs();
            }
            String filePath = typeSaveDir + type.getId() + ".html";
            file = new File(filePath);
            if (file.exists()){
                file.delete();
                file.createNewFile();
            }
            FileUtils.writeStringToFile(file,htmlContent,"UTF-8");
        }
    }
    
    /**
     * 生成文章归档需要的各个页面，
     * 公共部分依然不变，只要要改变各个子页面的文章列表
     * @param request
     * @throws Exception
     */
    private void genAllFilingPage(HttpServletRequest request) throws Exception{
        Map<String,String> classifyByMonth;
        if (CheckUtil.isNotNull(static_classifyByMonth)){
            classifyByMonth = static_classifyByMonth;
        }else{
            classifyByMonth = this.baseService.classifyArticleByMonth();
        }
        if (CheckUtil.isNull(complete_public_part)){
            complete_public_part = getComplete_public_part(request);
        }
        String replaceIndexSubPage = PropUtil.getValue(Constants.GITHUB_INDEX_SUBPAGE_REPLACE);
        String filingSaveDir = PropUtil.getValue(Constants.GITHUB_FILING_SAVE_DIR);
        
        Iterator<Map.Entry<String, String>> iterator =  classifyByMonth.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            String htmlContent = complete_public_part.replace(replaceIndexSubPage,
                    genArticleListSubPage(null,DateUtil.transStr2Date(entry.getKey(),"yyyy年MM月")));
            File file = new File(filingSaveDir);
            if (!file.exists()){
                file.mkdirs();
            }
            String filePath = filingSaveDir + entry.getKey().replace("年","")
                    .replace("月","").trim()+ ".html";
            file = new File(filePath);
            if (file.exists()){
                file.delete();
                file.createNewFile();
            }
            FileUtils.writeStringToFile(file,htmlContent,"UTF-8");
        }
    }
    
    /**
     * 生成所有文章的详细显示页面
     * @param request
     * @throws Exception
     */
    private void genAllArticlePage(HttpServletRequest request) throws Exception{
        List<Article> articleList= baseService.find(Article.class,null,null);
        if (CheckUtil.isNull(complete_public_part)){
            complete_public_part = getComplete_public_part(request);
        }
        String replaceIndexSubPage = PropUtil.getValue(Constants.GITHUB_INDEX_SUBPAGE_REPLACE);
        String articleDetailBasicDir = PropUtil.getValue(Constants.GITHUB_ARTICLE_DETAIL_BASIC_DIR);
        for (Article article : articleList){
            String htmlContent = complete_public_part.replace(replaceIndexSubPage,genArticleDetailSubPage(article));
            
            String ymd = DateUtil.transDate2Str(article.getCreateTime(),DateUtil.DATE_FORMAT_PATTERN_DAY);
            ymd = ymd.replace("-","/")+"/";
            String fileDir = articleDetailBasicDir + ymd;
            File file = new File(fileDir);
            if (!file.exists()){
                file.mkdirs();
            }
            
            String fileName = DateUtil.transDate2Str(article.getCreateTime(),DateUtil.DATE_FORMAT_PATTERN_ONLY_TIME);
            fileName = fileName.replaceAll(":","")+".html";
            String filePath = fileDir + fileName;
            
            file = new File(filePath);
            if (file.exists()){
                file.delete();
                file.createNewFile();
            }
            FileUtils.writeStringToFile(file,htmlContent,"UTF-8");
        }
    }
    
    /**
     * 生成公共部分(即，头部，底部，右侧栏)
     * @param request
     * @return
     * @throws Exception
     */
    private String getComplete_public_part(HttpServletRequest request) throws Exception{
        User user = (User) request.getSession().getAttribute("currentUser");
        String index_model = getStringFromFile(PropUtil.getValue(Constants.GITHUB_INDEX_MODEL_PATH));
        
        String replaceUserNickName = PropUtil.getValue(Constants.GITHUB_USER_NICKNAME_REPLACE);
        String replaceUserAvatar = PropUtil.getValue(Constants.GITHUB_USER_AVATAR_REPLACE);
        String replaceUserSignature = PropUtil.getValue(Constants.GITHUB_USER_SIGNATURE_REPLACE);
        String replaceUserIntroduce = PropUtil.getValue(Constants.GITHUB_USER_INTRODUCE_REPLACE);
        String replaceIndexTypeList = PropUtil.getValue(Constants.GITHUB_INDEX_TYPE_LIST_REPLACE);
        String replaceIndexArticleFiling = PropUtil.getValue(Constants.GITHUB_INDEX_ARTICLE_FILING_REPLACE);
        
        String result = StringUtil.replaceAll(index_model,replaceUserNickName,user.getNickName())
                .replace(replaceUserAvatar,getAvatarUrl(request))
                .replace(replaceUserSignature,user.getSignature())
                .replace(replaceUserIntroduce,user.getIntroduce())
                .replace(replaceIndexTypeList,genAllTypeListUlLi())
                .replace(replaceIndexArticleFiling,genAllArticleFilingUlLi());
        
        complete_public_part = result;
        return result;
    }
    
    /**
     * 返回指定文件的字符内容
     * @param filePath
     * @return
     * @throws IOException
     */
    private String getStringFromFile(String filePath) throws IOException {
        return FileUtils.readFileToString(new File(filePath),"UTF-8");
    }
    
    /**
     * 根据查询条件生成文章索引目录子页面
     * 目前只支持类型和创建时间这两个查询条件
     * @param type
     * @return
     * @throws Exception
     */
    private String genArticleListSubPage(Type type,Date createTime) throws Exception {
        String article_list_model = getStringFromFile(PropUtil.getValue(Constants.GITHUB_ARTICLE_LIST_MODEL));
        String allArticleListTableTr = genAllArticleListTableTr(type,createTime);
        String replaceArticleListTableTr = PropUtil.getValue(Constants.GITHUB_ARTICLE_LIST_TABLE_TR_REPALCE);
        
        String result = article_list_model.replace(replaceArticleListTableTr,allArticleListTableTr);
        return result;
    }
    
    /**
     * 生成显示文章的子页面
     * @param article
     * @return
     * @throws Exception
     */
    private String genArticleDetailSubPage(Article article) throws Exception {
        String article_detail_model = getStringFromFile(PropUtil.getValue(Constants.GITHUB_ARTICLE_DETAIL_MODEL_PATH));
        String encrypt_article_script;
        
        String replaceArticleTitle = PropUtil.getValue(Constants.GITHUB_ARTICLE_TITLE_REPLACE);
        String replaceArticleCrateTime = PropUtil.getValue(Constants.GITHUB_ARTICLE_CREATETIME_REPLACE);
        String replaceArticleSource = PropUtil.getValue(Constants.GITHUB_ARTICLE_SOURCE_REPLACE);
        String replaceArticleTypeName = PropUtil.getValue(Constants.GITHUB_ARTICLE_TYPENAME_REPLACE);
        String replaceArticleContent = PropUtil.getValue(Constants.GITHUB_ARTICLE_CONTENT_REPLACE);
        String replaceArticleKeywords = PropUtil.getValue(Constants.GITHUB_ARTICLE_KEYWORDS_REPLACE);
        String replaceArticleEncryptScript = PropUtil.getValue(Constants.GITHUB_ARTICLE_ENCRYPT_SCRIPT_REPLACE);
        String replaceXorCryptoSeparator = PropUtil.getValue(Constants.GITHUB_XOR_CRYPTO_SEPARATOR_REPLACE);
        
        String articleContent;
        if (article.getVisitAuth().equals(Article_visitAuth.仅自己可见)){
            articleContent = StringUtil.XOREncrypt(handleImgTag(article.getContent()));
            encrypt_article_script = getStringFromFile(PropUtil.getValue(Constants.GITHUB_ENCRYPT_ARTICLE_SCRIPT));
            String separator = PropUtil.getValue(Constants.XOR_CRYPTO_SEPARATOR);
            encrypt_article_script = encrypt_article_script.replace(replaceXorCryptoSeparator,separator);
        }else{
            articleContent = handleImgTag(article.getContent());
            encrypt_article_script = "";
        }
        String result = article_detail_model.replace(replaceArticleTitle,article.getTitle())
                .replace(replaceArticleCrateTime,DateUtil.transDate2Str(article.getCreateTime()))
                .replace(replaceArticleSource,GetterUtil.getString(article.getSource()))
                .replace(replaceArticleTypeName,article.getType().getTypeName())
                .replace(replaceArticleContent,articleContent)
                .replace(replaceArticleKeywords,article.getKeywords())
                .replace(replaceArticleEncryptScript,encrypt_article_script);
        
        return result;
    }
    
    /**
     * 这里生成文章索引子页面中的table中的所有tr
     * @param type
     * @return
     * @throws Exception
     */
    private String genAllArticleListTableTr(Type type,Date createTime) throws Exception {
        String article_list_table_tr = getStringFromFile(PropUtil.getValue(Constants.GITHUB_ARTICLE_LIST_TABLE_TR));
        String article_lock_img = getStringFromFile(PropUtil.getValue(Constants.GITHUB_ARTICLE_LOCK_IMG));
        
        Article s_article = new Article();
        s_article.setType(type);
        s_article.setCreateTime(createTime);
        List<Article> articleList = baseService.find(Article.class,s_article,null);
        StringBuffer result = new StringBuffer("");
        for (Article article : articleList){
            String replaceTitleName = PropUtil.getValue(Constants.GITHUB_ARTICLE_TITLE_REPLACE);
            String replaceCreateTimeName = PropUtil.getValue(Constants.GITHUB_ARTICLE_CREATETIME_REPLACE);
            String replaceArticleIndex = PropUtil.getValue(Constants.GITHUB_ARTICLE_INDEX_REPLACE);
            String replaceArticleLockImg = PropUtil.getValue(Constants.GITHUB_ARTICLE_LOCK_IMG_REPLACE);
            
            String articleLockImg = "";
            if (article.getVisitAuth().equals(Article_visitAuth.仅自己可见)){
                articleLockImg = article_lock_img;
            }
            
            result.append(article_list_table_tr.replace(replaceTitleName,article.getTitle())
                    .replace(replaceCreateTimeName,DateUtil.transDate2Str(article.getCreateTime(),DateUtil.DATE_FORMAT_PATTERN_DAY))
                    .replace(replaceArticleIndex,genArticleUrl(article))
                    .replace(replaceArticleLockImg,articleLockImg));
        }
        return result.toString();
    }
    
    /**
     * 生成所有类型索引
     * @return
     * @throws Exception
     */
    private String genAllTypeListUlLi() throws Exception {
        String type_list_ul_li = getStringFromFile(PropUtil.getValue(Constants.GITHUB_TYPE_LIST_UL_LI));
        List<Type> typeList = baseService.find(Type.class,null,null);
        StringBuffer result = new StringBuffer("");
        for (Type type : typeList){
            String replaceTypeName = PropUtil.getValue(Constants.GITHUB_TYPE_TYPENAME_REPLACE);
            String replaceArticleNum = PropUtil.getValue(Constants.GITHUB_TYPE_ARTICLENUM_REPLACE);
            String replaceTypeIndex = PropUtil.getValue(Constants.GITHUB_TYPE_INDEX_REPLACE);
            result.append(type_list_ul_li.replace(replaceTypeName,type.getTypeName())
                    .replace(replaceArticleNum,GetterUtil.getString(type.getArticleList().size()))
                    .replace(replaceTypeIndex,genTypeUrl(type)));
        }
        return result.toString();
    }
    
    private String genAllArticleFilingUlLi() throws Exception {
        String article_filing_ul_li = getStringFromFile(PropUtil.getValue(Constants.GITHUB_ARTICLE_FILING_UL_LI));
        StringBuffer result = new StringBuffer("");
        Map<String,String> classifyByMonth = baseService.classifyArticleByMonth();
        static_classifyByMonth = classifyByMonth;
        Iterator<Map.Entry<String, String>> iterator =  classifyByMonth.entrySet().iterator();
        while (iterator.hasNext()){
            String replaceFilingMonth = PropUtil.getValue(Constants.GITHUB_FILING_MONTH_REPLACE);
            String replaceArticleNum = PropUtil.getValue(Constants.GITHUB_FILING_ARTICLENUM_REPLACE);
            String replaceFilingIndex = PropUtil.getValue(Constants.GITHUB_FILING_INDEX_REPLACE);
            Map.Entry<String,String> entry = iterator.next();
            result.append(article_filing_ul_li.replace(replaceFilingMonth,entry.getKey())
                    .replace(replaceArticleNum,entry.getValue())
                    .replace(replaceFilingIndex,genFilingUrl(entry.getKey())));
        }
        return result.toString();
    }
    
    //获取头型的url
    private String getAvatarUrl(HttpServletRequest request) throws Exception{
        User user = (User) request.getSession().getAttribute("currentUser");
        String oldAvatarUrl = user.getAvatar();
        String fileName = oldAvatarUrl.substring(oldAvatarUrl.lastIndexOf("/")+1);
        avatarPicFileName = fileName;
        String newAvatarUrl = PropUtil.getValue(Constants.GITHUB_USER_AVATAR_BASIC_URL)+fileName;
        return newAvatarUrl;
    }
    
    //获取文章的url
    private String genArticleUrl(Article article) throws Exception {
        String basicUrl = PropUtil.getValue(Constants.GITHUB_ARTICLE_BASIC_URL);
        String ymd = DateUtil.transDate2Str(article.getCreateTime(),DateUtil.DATE_FORMAT_PATTERN_DAY);
        ymd = ymd.replace("-","/")+"/";
        String fileName = DateUtil.transDate2Str(article.getCreateTime(),DateUtil.DATE_FORMAT_PATTERN_ONLY_TIME);
        fileName = fileName.replaceAll(":","")+".html";
        String articleUrl = basicUrl + ymd + fileName;
        return articleUrl;
    }
    
    //生成类型的url
    private String genTypeUrl(Type type) throws Exception{
        String basicUrl = PropUtil.getValue(Constants.GITHUB_TYPE_LIST_BASIC_URL);
        String fileName = type.getId()+".html";
        String typeUrl = basicUrl + fileName;
        return typeUrl;
    }
    
    private String genFilingUrl(String date) throws Exception{
        date = date.replace("年","").replace("月","").trim();
        String basicUrl = PropUtil.getValue(Constants.GITHUB_FILING_LIST_BASIC_URL);
        String fileName = date+".html";
        String typeUrl = basicUrl + fileName;
        return typeUrl;
    }
    
    /**
     * 处理文章内容中的图片
     */
    private String handleImgTag(String content) throws Exception {
        //先匹配所有的<img>标签
        Pattern p = Pattern.compile( "<(img|IMG)[^\\<\\>]*>" );
        Matcher m = p.matcher(content);
        while( m.find() ) {
            String oldImg = m.group();
            String old_src_str = getSrcFromImg(oldImg); //获取src值
            if (CheckUtil.isNotEmpty(old_src_str)){
                String fileName = old_src_str.substring(old_src_str.lastIndexOf("/")+1);    //获取文件名
                articlePicList.add(fileName);
                String new_src_str = PropUtil.getValue(Constants.GITHUB_ARTICLE_PIC_BASIC_URL)+fileName;    //生成新的访问路径
                String newImg = oldImg.replace(old_src_str,new_src_str);    //新的<img>标签替换旧的标签
                content = content.replace(oldImg,newImg);
            }
        }
        return content;
    }
    
    private void syncFile(HttpServletRequest request) throws Exception{
        //第一步清除多余的图片
        String local_article_pic_dir = request.getServletContext().getRealPath("/files/article_pic/");
        String local_user_avatar_dir = request.getServletContext().getRealPath("/files/avatar/");
        
        File articlePicFileDir = new File(local_article_pic_dir);
        File userAvatarFileDir = new File(local_user_avatar_dir);
        
        String[] articlePicFileList = articlePicFileDir.list();
        for (String fileName : articlePicFileList){
            if (!articlePicList.contains(fileName)){
                FileUtils.deleteQuietly(new File(local_article_pic_dir+fileName));
            }
        }
        
        String[] userAvatarFileList = userAvatarFileDir.list();
        for (String fileName : userAvatarFileList){
            if (!CheckUtil.isEqual(fileName,avatarPicFileName)){
                FileUtils.deleteQuietly(new File(local_user_avatar_dir+fileName));
            }
        }
        
        //第二部同步图片
        String github_article_pic_dir = PropUtil.getValue(Constants.GITHUB_ARTICLE_PIC_DIR);
        String github_user_avatar_dir = PropUtil.getValue(Constants.GITHUB_USER_AVATAR_DIR);
        File githubArticlePicDir = new File(github_article_pic_dir);
        File githubUserAvatarDir = new File(github_user_avatar_dir);
        if (!githubArticlePicDir.exists()){
            githubArticlePicDir.mkdirs();
        }
        if (!githubUserAvatarDir.exists()){
            githubUserAvatarDir.mkdirs();
        }
        
        FileUtils.cleanDirectory(new File(github_article_pic_dir));
        FileUtils.cleanDirectory(new File(github_user_avatar_dir));
        
        FileUtils.copyDirectory(new File(local_article_pic_dir),new File(github_article_pic_dir));
        FileUtils.copyDirectory(new File(local_user_avatar_dir),new File(github_user_avatar_dir));
    }
    
    /**
     * 获取<img>标签中src元素的值
     * @param img
     * @return
     * @throws Exception
     */
    private static String getSrcFromImg(String img) throws Exception {
        String ymbol = null;
        boolean tag = true;
        StringBuffer result = new StringBuffer();
        for (int i = img.indexOf("src");i<img.length();i++){
            if (tag && CheckUtil.isEqual(String.valueOf(img.charAt(i)),"\"") ||
                    CheckUtil.isEqual(String.valueOf(img.charAt(i)),"'")){
                ymbol = String.valueOf(img.charAt(i));
                tag = false;
                continue;
            }
            if (!tag){
                if (!CheckUtil.isEqual(String.valueOf(img.charAt(i)),ymbol)){
                    result.append(img.charAt(i));
                }else{
                    break;
                }
            }
        }
        return result.toString();
    }
}
