package hh.learnj.httpclient.usecase.topease;

import hh.learnj.httpclient.usecase.qichacha.DB;

public class OAVerify {

	public static void main(String[] args) {
		DB.handle(new OAQueryHandler());
	}
}
