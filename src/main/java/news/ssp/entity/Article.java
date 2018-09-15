package news.ssp.entity;

import java.util.Date;

/**
 * 帖子实体类
 * @author user
 *
 */
public class Article {

	private Integer id; // 编号
	private String title; // 标题
	private String content; // 内容
	private String summary; // 摘要
	private Date crawlerDate; // 抓取日期
	private Date releaseDate; // 发布日期
	private Integer clickHit; // 浏览次数
	private ArcType arcType; // 帖子类别
	private String tags; // 帖子标签
	private String orUrl; // 原始地址
	private Integer state; // 帖子状态 0 未发布 1 发布 默认0
	private String releaseDateStr; // 发布日期的字符串 只取年和月
	private String contentNoTag;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getCrawlerDate() {
		return crawlerDate;
	}
	public void setCrawlerDate(Date crawlerDate) {
		this.crawlerDate = crawlerDate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getClickHit() {
		return clickHit;
	}
	public void setClickHit(Integer clickHit) {
		this.clickHit = clickHit;
	}
	public ArcType getArcType() {
		return arcType;
	}
	public void setArcType(ArcType arcType) {
		this.arcType = arcType;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getOrUrl() {
		return orUrl;
	}
	public void setOrUrl(String orUrl) {
		this.orUrl = orUrl;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	
	
}
