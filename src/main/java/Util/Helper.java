package Util;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {
    public static int getIdFromRequest(HttpServletRequest request){return getIdFromRequest(request,"id");}
    public static int getIdFromRequest(HttpServletRequest request, String fieldName){
        String fieldPara = request.getParameter(fieldName);
        return Integer.parseInt(fieldPara);
    }
    //获得借阅时间
    public static String getTime() {
        //将日期格式转化为字符串格式
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        //不同年
        if(year1 != year2) {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++) {
                //闰年
                if(i%4==0 && i%100!=0 || i%400==0){
                    timeDistance += 366;
                } else {
                    timeDistance += 365;//不是闰年
                }
            }
            return timeDistance + (day2-day1) ;
        }else {
            //同一年
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
}