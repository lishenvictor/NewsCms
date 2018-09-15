package news.ssp.entity;

import java.util.Date;

/**
 * ����ʵ����
 * @author user
 *
 */
public class Article {

	private Integer id; // ���
	private String title; // ����
	private String content; // ����
	private String summary; // ժҪ
	private Date crawlerDate; // ץȡ����
	private Date releaseDate; // ��������
	private Integer clickHit; // �������
	private ArcType arcType; // �������
	private String tags; // ���ӱ�ǩ
	private String orUrl; // ԭʼ��ַ
	private Integer state; // ����״̬ 0 δ���� 1 ���� Ĭ��0
	private String releaseDateStr; // �������ڵ��ַ��� ֻȡ�����
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
