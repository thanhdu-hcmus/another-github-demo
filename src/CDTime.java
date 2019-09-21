public class CDTime {
    private int Day;
    private int Month;
    private int Year;
    private int DaysOfMonth;
    public CDTime(){
        Day =1;
        Month =1;
        Year = 1;
    }

    public CDTime(int day,int month,int year){
        Year = year;
        Month = month;
        Day = day;
    }

    public int GetDayOfWeek(int day,int month,int year){
        System.out.println(day+"/"+month+"/"+year);
        int JMD;
        JMD = (1 + ((153 * (month + 12 * ((14 - month) / 12) - 3) + 2) / 5) +
                (365 * (year + 4800 - ((14 - month) / 12))) +
                ((year + 4800 - ((14 - month) / 12)) / 4) -
                ((year + 4800 - ((14 - month) / 12)) / 100) +
                ((year + 4800 - ((14 - month) / 12)) / 400)  - 32045) % 7;

        System.out.println(JMD);

        if (JMD == 0) JMD = 1;
        else if (JMD == 1) JMD = 2;
        else if (JMD == 2) JMD = 3;
        else if (JMD == 3) JMD = 4;
        else if (JMD == 4) JMD = 5;
        else if (JMD == 5) JMD = 6;
        else if (JMD == 6) JMD = 0;
        return JMD;
    }

    public void setYear(int year) {
        Year = year;
    }
    public void setMonth(int month){
        Month = month;
    }

    public int GetDay(){

        return Day;
    }

    public int GetMonth(){
        if (Month<1){
            Month = 12;
        }
        if (Month>12){
            Month =1;
        }
        return Month;
    }
    public int GetYear(){
        return Year;
    }

    public int GetDaysOfMonth(int Month){
        switch (Month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                DaysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                DaysOfMonth = 30;
                break;
            case 2:
                if ((Year % 4 == 0) && (Year % 100 != 0) || (Year % 400 == 0)) {
                        DaysOfMonth = 29;
                } else  DaysOfMonth = 28;
                break;
        }
        return DaysOfMonth;
    }
    void gotoNextYear()
    {
        Year++;
    }
    void gotoPrvYear(){

        Year--;
    }

    void gotoNextMonth(){
        Month++;
        switch (Month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                DaysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                DaysOfMonth = 30;
                break;
            case 2:
                if ((Year % 4 == 0) && (Year % 100 != 0) || (Year % 400 == 0)) {
                    DaysOfMonth = 29;
                } else DaysOfMonth = 28;
                break;

        }
        if(Month>12){
            Month =1;
            gotoNextYear();
        }
        System.out.println(Month);
    }

    void gotoPrvMonth(){
        Month--;
        switch (Month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                DaysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                DaysOfMonth = 30;
                break;
            case 2:
                if ((Year % 4 == 0) && (Year % 100 != 0) || (Year % 400 == 0)) {
                    DaysOfMonth = 29;
                } else DaysOfMonth = 28;
                break;
        }
        if(Month<1){
            Month = 12;
            gotoPrvYear();
        }
        System.out.println(Month);

    }

    void gotoNextDay() {
        Day++;
        if (Day > DaysOfMonth){
            Day = 1;
            gotoNextMonth();
            System.out.println(DaysOfMonth);
        }

        System.out.println(Day);
    }

    void gotoPrvDay(){
        Day--;
        if (Day < 1) {
            gotoPrvMonth();
            Day = DaysOfMonth;


            System.out.println(DaysOfMonth);
        }

    }



    /*public int DaysOfWeek(int stt, int day) {
        // so thu tu cua ngay hien tai
        int a = stt;
        int i = day;
        while(i > 1){
            a = a - 1;
            if (a < 1) a=7;
            i = i - 1;
        }
        if(a==7) a = 0;
        return a;
    }*/
}
