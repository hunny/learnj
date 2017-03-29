package hh.learnj.httpclient.usecase.topease.chinaexport;

import org.springframework.beans.BeanUtils;

public class App {
	
	public static void main(String[] args) {
//		china();
		final CompanyData companyData = new CompanyData();
		companyData.setBegin("2016-03");
		companyData.setEnd("2017-03");
		companyData.setCategory("");
		companyData.setCompanyFilter("上海");
		companyData.setToken("");
		companyData.setSessionId("rv3d1o2u0czxbuzss4ijkkrs");
		companyData.setRecordCount("1300");
		companyData.setHscode("56039410");
		CountryParser.TokenHander tokenHanlder = new CountryParser.TokenHander() {
			@Override
			public void handler(String name, String token, String url) {
				CompanyData mData = new CompanyData();
				BeanUtils.copyProperties(companyData, mData);
				mData.setCategory(name);
				mData.setToken(token);
				new CompanyDataService(mData).justDoIt();
			}
		};
		TopEaseHttp http = new TopEaseHttp(companyData.getSessionId());
		CountryParser countryParser = new CountryParser(tokenHanlder, companyData);
		try {
			http.get(countryParser.getUrl(), "UTF-8", countryParser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void china() {
		CompanyData companyData = new CompanyData();
		companyData.setBegin("2016-03");
		companyData.setEnd("2017-03");
		companyData.setCategory("中国出口");
		companyData.setCompanyFilter("上海");
		companyData.setToken("90A16018F2E2B480BDF7F835C52ECF10A2E1B3094AA8D6C3B3B93055BE368D09B385F6679963F2FC591DBD5E7A2CB8520C23C19BC67C51EEC8E5E47611371AFCB3892A2DA110F1EE");
		companyData.setSessionId("rv3d1o2u0czxbuzss4ijkkrs");
		companyData.setRecordCount("1000");
		companyData.setHscode("56039410");
		new CompanyDataService(companyData).justDoIt();
		//39069090-90A16018F2E2B4800BF65F266B3C15A004746A4B7A8DAEFF74C851B1A067BB1B81E75495A478163858D28419EFF1B1AADCB52392E177EE9848FF5E3C51E2610D6D7E13682835CF56
		//56039410-90A16018F2E2B480BDF7F835C52ECF10A2E1B3094AA8D6C3B3B93055BE368D09B385F6679963F2FC591DBD5E7A2CB8520C23C19BC67C51EEC8E5E47611371AFCB3892A2DA110F1EE
	}

}
