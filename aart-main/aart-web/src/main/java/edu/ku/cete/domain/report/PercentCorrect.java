package edu.ku.cete.domain.report;

public class PercentCorrect {
	
	private Long below60;
	
	private Long from60To69;
	
	private Long from70To79;
	
	private Long from80To89;
	
	private Long from90To99;
	
	private Long hundred100;

	public Long getBelow60() {
		return below60;
	}

	public void setBelow60(Long below60) {
		this.below60 = below60;
	}

	public Long getFrom60To69() {
		return from60To69;
	}

	public void setFrom60To69(Long from60To69) {
		this.from60To69 = from60To69;
	}

	public Long getFrom70To79() {
		return from70To79;
	}

	public void setFrom70To79(Long from70To79) {
		this.from70To79 = from70To79;
	}

	public Long getFrom80To89() {
		return from80To89;
	}

	public void setFrom80To89(Long from80To89) {
		this.from80To89 = from80To89;
	}

	public Long getFrom90To99() {
		return from90To99;
	}

	public void setFrom90To99(Long from90To99) {
		this.from90To99 = from90To99;
	}

	public Long getHundred100() {
		return hundred100;
	}

	public void setHundred100(Long hundred100) {
		this.hundred100 = hundred100;
	}
	public void addValue(int value)
	{
		if(value<60)
		{
			this.below60++;
		}
		else if(value<70)
		{
			this.from60To69++;
		}else if(value<80)
		{
			this.from70To79++;
		}else if(value<90)
		{
			this.from80To89++;
		}else if(value<100)
		{
			this.from90To99++;
		}
		else
		{
			this.hundred100++;
		}
	}
	
	
	
	
	
	
	
	
	
	

}
