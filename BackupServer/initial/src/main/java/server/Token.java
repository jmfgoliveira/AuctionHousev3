package server;

import java.io.Serializable;

public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String randomNum;
	private long timeStamp;

	public Token() {

	}

	public Token(String randomNum, long timeStamp) {
		this.randomNum=randomNum;
		this.timeStamp=timeStamp;
	}

	

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Token))
			return false;
		Token t = (Token) o;

		return (this.randomNum.equals(t.getRandomNum())   ) && (t.getTimeStamp() == timeStamp);

	}

	public String getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}

}
