package hh.learnj.testj.util.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarGuide {

	public static void main(String[] args) {
		new CalendarGuide().myCalendar();
	}
	//实现日历的方法  
    public void myCalendar() {  
        GregorianCalendar now = new GregorianCalendar();  
        // 获得一个Date对象  
        Date date = new Date();  
        // 打印当前时间  
        System.out.println(date.toString());  
        // 设置当前时间  
        now.setTime(date);  
        // 从日期中取得当前的日  
        int toDay = now.get(Calendar.DAY_OF_MONTH);  
        // 从日期中取得当前的月  
        int month = now.get(Calendar.MONTH);  
        // 设置now的日期为1  
        now.set(Calendar.DAY_OF_MONTH, 1);  
        // 得到now是一周的第几天  
        int week = now.get(Calendar.DAY_OF_WEEK);  
        // 打印日历头部标示  
        System.out.println("日\t一\t二\t三\t四\t五\t六");  
        // 打印当前日期前面的空格  
        for (int i = Calendar.SUNDAY; i < week; i++) {  
            System.out.print("\t");  
        }  
        // 打印日历主体  
        while (now.get(Calendar.MONTH) == month) {  
            int day = now.get(Calendar.DAY_OF_MONTH);  
            // 对输出的日历进行对齐，小于10的加上一个空格  
            if (day < 10) {  
                // 如果是当前日期，加上标示  
                if (day == toDay) {  
                    System.out.print("▲" + day + "▲\t");  
                } else {  
                    System.out.print(" " + day + "\t");  
                }  
            } else {  
                // 如果是当前日期，加上标示  
                if (day == toDay) {  
                    System.out.print("▲" + day + "▲\t");  
                } else {  
                    System.out.print("" + day + "\t");  
                }  
            }  
            //如果是周六，进行换行  
            if (week == Calendar.SATURDAY) {  
                System.out.println();  
            }  
            //每次输出日期后，将日期增加一天  
            now.add(Calendar.DAY_OF_MONTH, 1);  
            //重新获得一周的第几天  
            week = now.get(Calendar.DAY_OF_WEEK);  
        }  
    }
}
