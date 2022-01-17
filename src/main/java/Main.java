import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String select = sc.nextLine();

        if (select.equals("매월")) {
            System.out.println("매월입니다.");
            int day = sc.nextInt();
            sc.nextLine();
            String time = sc.nextLine();
            String fMonth = sc.nextLine();
            String sMonth = sc.nextLine();
            int count = 0;
            int f = 0;
            int s = 0;

            if (fMonth.charAt(0) == '0') f = Integer.parseInt(String.valueOf(fMonth.charAt(fMonth.length() - 1)));
            else f = Integer.parseInt(fMonth);
            if (sMonth.charAt(0) == '0') s = Integer.parseInt(String.valueOf(sMonth.charAt(sMonth.length() - 1)));
            else s = Integer.parseInt(sMonth);

            for (int i = f; i <= s; i++) {
                count++;
            }

            Calendar calendar = Calendar.getInstance();
            String[] cal = new String[count];
            LocalDate now = LocalDate.now();
            int currentM = now.getMonthValue()-1;

            for (int i = 0; i < cal.length; i++) {
                calendar.set(now.getYear(), f + i - 1, 1);
                int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if(currentM == calendar.get(Calendar.MONTH) && now.getDayOfMonth() > day) {
                    continue;
                }
                if (day <= actualMaximum) {
                    getDayPrint(day, time, f, cal, now, i);
                } else {
                    getLastDayPrint(time, f, cal, now, i, actualMaximum);
                }
            }
        }

        else if (select.equals("매주")) {
            System.out.println("매주입니다.");
            String dayOfWeek = sc.nextLine();
            String time = sc.nextLine();
            String fMonth = sc.nextLine();
            String sMonth = sc.nextLine();
            int count = 0;
            LocalDate now = LocalDate.now();

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = null;
            try {
                date = dateFormat.parse(now.getYear() + fMonth + "01");

            } catch (ParseException g) {
                g.printStackTrace();
            }
            int f = 0;
            int s = 0;

            if (fMonth.charAt(0) == '0') f = Integer.parseInt(String.valueOf(fMonth.charAt(fMonth.length() - 1)));
            else f = Integer.parseInt(fMonth);
            if (sMonth.charAt(0) == '0') s = Integer.parseInt(String.valueOf(sMonth.charAt(sMonth.length() - 1)));
            else s = Integer.parseInt(sMonth);

            for (int i = f; i <= s; i++) {
                count += 5;
            }

            String[] e = new String[count];

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, now.getDayOfMonth());

            int iDay = 2;
            switch (dayOfWeek) {
                case "일":
                    iDay = 1;
                    break;
                case "월":
                    iDay = 2;
                    break;
                case "화":
                    iDay = 3;
                    break;
                case "수":
                    iDay = 4;
                    break;
                case "목":
                    iDay = 5;
                    break;
                case "금":
                    iDay = 6;
                    break;
                case "토":
                    iDay = 7;
                    break;
                default:
                    iDay = 1;
                    break;
            }

            int day = now.getDayOfMonth();
            int week = 7;
            System.out.println("calendar = " + calendar.get(Calendar.DAY_OF_WEEK));

            //현재 달로 꼐산 할시
            if (calendar.get(Calendar.DAY_OF_WEEK) < iDay) {
                calendar.set(5, (iDay - calendar.get(Calendar.DAY_OF_WEEK)) + day);
                System.out.println("c.get(5) = " + calendar.get(5));
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == iDay) {
                calendar.set(5, day);
                System.out.println("c.get(5) = " + calendar.get(5));
            } else {
                calendar.set(5, ((week + iDay) - calendar.get(Calendar.DAY_OF_WEEK)) + day);
                System.out.println("c.get(5) = " + calendar.get(5));
            }

            //그다음달로 계산할시
            for (int i = 0; i < e.length; i++) {
                if (s < calendar.get(Calendar.MONTH)+1) break;
                getAddDayPrint(time, now, e, calendar, week, i);
            }
        }
    }

    private static void getAddDayPrint(String time, LocalDate now, String[] e, Calendar calendar, int week, int i) {
        LocalDate weekDate = LocalDate.of(now.getYear(), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        DayOfWeek dayOfWeek = weekDate.getDayOfWeek();
        String displayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        e[i] = weekDate + " " +displayName + " " + time;
        calendar.add(Calendar.DATE, week);
        System.out.println("e" + "[" + i + "]" + "=" + e[i]);
    }

    private static void getLastDayPrint(String time, int f, String[] cal, LocalDate now, int i, int actualMaximum) {
        LocalDate date = LocalDate.of(now.getYear(), f + i, actualMaximum);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String displayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        cal[i] = date + " " + displayName + " " + time;
        System.out.println(cal[i]);
    }

    private static void getDayPrint(int day, String time, int f, String[] cal, LocalDate now, int i) {
        getLastDayPrint(time, f, cal, now, i, day);
    }
}
