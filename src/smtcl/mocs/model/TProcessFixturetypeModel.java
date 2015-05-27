package smtcl.mocs.model;

public class TProcessFixturetypeModel {
	
	private Long id;
	private Long fixturetypeId;
	private String fixtureNO;
	private String fixtureName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFixturetypeId() {
		return fixturetypeId;
	}
	public void setFixturetypeId(Long fixturetypeId) {
		this.fixturetypeId = fixturetypeId;
	}
	public String getFixtureNO() {
		return fixtureNO;
	}
	public void setFixtureNO(String fixtureNO) {
		this.fixtureNO = fixtureNO;
	}
	public String getFixtureName() {
		return fixtureName;
	}
	public void setFixtureName(String fixtureName) {
		this.fixtureName = fixtureName;
	}
	
}
