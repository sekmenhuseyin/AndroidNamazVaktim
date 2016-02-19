package net.huseyinsekmenoglu.helpers;

//import com.google.android.gms.common.ConnectionResult;
//import com.mobilexsoft.ezanvakti.arcompass.Matrix4;
//import com.mobilexsoft.ezanvakti.kuran.ui.NumericWheelAdapter;

public class CalendarConverter {
/*    final double Acc;
    private int Lunation;
    private double MJD;
    final double MJD_J2000;
    final double MLunatBase;
    private Calendar cal;
    private double crescentMoonMoment;
    final double dT;
    final double dTc;
    private int hijriDay;
    private int hijriMonth;
    private int hijriYear;
    private boolean[] isFound;
    private double newMoonMoment;
    final double synmonth;

    public HicriHesaplayici(Calendar c, int duzeltme, Context ctx) {
        this.synmonth = 29.530588861d;
        this.dT = 1.916495550992471E-4d;
        this.dTc = 8.213552361396303E-5d;
        this.Acc = 9.506426344208685E-9d;
        this.MJD_J2000 = 51544.5d;
        this.MLunatBase = 23435.90347d;
        c.add(6, duzeltme);
        int Year = c.get(1);
        int Month = c.get(2) + 1;
        int Day = c.get(5);
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("HICRIGUNLER", 0);
        String hs = p.getString(Year + "-" + Month + "-" + Day, ZLFileImage.ENCODING_NONE);
        if (hs.equals(ZLFileImage.ENCODING_NONE)) {
            this.MJD = Zaman.Mjd(c.get(1), c.get(2) + 1, c.get(5), 0, 0, 0.0d);
            this.cal = Zaman.CalDat(this.MJD);
            double T1 = (this.MJD - 51544.5d) / 36525.0d;
            double T0 = T1 - 1.916495550992471E-4d;
            this.isFound = new boolean[1];
            this.isFound[0] = false;
            AyDurumlari newMoon = new YeniAy();
            AyDurumlari crescentMoon = new Hilal();
            double D1 = newMoon.calculatePhase(T1);
            double D0 = newMoon.calculatePhase(T0);
            while (true) {
                if (D0 * D1 <= 0.0d && D1 >= D0) {
                    break;
                }
                T1 = T0;
                D1 = D0;
                T0 -= 1.916495550992471E-4d;
                D0 = newMoon.calculatePhase(T0);
            }
            double TNewMoon = Hesap.Pegasus(newMoon, T0, T1, 9.506426344208685E-9d, this.isFound);
            this.newMoonMoment = (36525.0d * TNewMoon) + 51544.5d;
            this.Lunation = ((int) Math.floor(((this.newMoonMoment + 7.0d) - 23435.90347d) / 29.530588861d)) + 1;
            this.hijriYear = ((this.Lunation + 4) / 12) + 1341;
            this.hijriMonth = ((this.Lunation + 4) % 12) + 1;
            if (this.isFound[0]) {
                AyDurumlari ayDurumlari = crescentMoon;
                this.crescentMoonMoment = (36525.0d * Hesap.Pegasus(ayDurumlari, TNewMoon, TNewMoon + 8.213552361396303E-5d, 9.506426344208685E-9d, this.isFound)) + 51544.5d;
            }
            this.hijriDay = ((int) (this.MJD - ((double) Math.round(this.crescentMoonMoment + 0.279166666666667d)))) + 1;
            if (this.hijriDay == 0) {
                this.hijriDay = 30;
                this.hijriMonth--;
                if (this.hijriMonth == 0) {
                    this.hijriMonth = 12;
                    return;
                }
                return;
            }
            return;
        }
        try {
            this.cal = c;
            String[] s = hs.split("-");
            this.hijriYear = Integer.parseInt(s[0]);
            this.hijriMonth = Integer.parseInt(s[1]);
            this.hijriDay = Integer.parseInt(s[2]);
        } catch (Exception e) {
        }
    }

    public int getHijriYear() {
        return this.hijriYear;
    }

    public int getHijriMonthName() {
        return this.hijriMonth - 1;
    }

    public int getHijriMonth() {
        return this.hijriMonth;
    }

    public int getHijriDay() {
        return this.hijriDay;
    }

    public String getHicriTakvim() {
        return getHijriDay() + " " + getHijriMonthName() + " " + getHijriYear();
    }

    public int checkIfHolyDay() {
        if (this.cal == null) {
            this.cal = Calendar.getInstance();
        }
        int holyDay = 11;
        switch (this.hijriMonth) {
            case ZLTextView.SCROLLBAR_SHOW *//*1*//*:
                if (this.hijriDay == 1) {
                    return 0;
                }
                if (this.hijriDay == 10) {
                    return 1;
                }
                return 11;
            case FBView.SCROLLBAR_SHOW_AS_FOOTER *//*3*//*:
                if (this.hijriDay == 11 || this.hijriDay == 12) {
                    return 2;
                }
                return 11;
            case ConnectionResult.NETWORK_ERROR *//*7*//*:
                if (this.hijriDay == 1 && this.hijriMonth == 7) {
                    holyDay = 3;
                }
                if (this.cal.get(7) == 5 && this.hijriDay < 7) {
                    holyDay = 4;
                }
                if (this.hijriDay == 27) {
                    return 5;
                }
                return holyDay;
            case ConnectionResult.INTERNAL_ERROR *//*8*//*:
                if (this.hijriDay == 14 || this.hijriDay == 15) {
                    return 6;
                }
                return 11;
            case NumericWheelAdapter.DEFAULT_MAX_VALUE *//*9*//*:
                if (this.hijriDay == 26) {
                    return 7;
                }
                return 11;
            case ZLTextView.MAX_SELECTION_DISTANCE *//*10*//*:
                if (this.hijriDay == 1 || this.hijriDay == 2 || this.hijriDay == 3) {
                    return 8;
                }
                return 11;
            case Matrix4.M03 *//*12*//*:
                if (this.hijriDay == 9) {
                    holyDay = 9;
                }
                if (this.hijriDay == 10 || this.hijriDay == 11 || this.hijriDay == 12 || this.hijriDay == 13) {
                    return 10;
                }
                return holyDay;
            default:
                return 11;
        }
    }

    public int getDay() {
        return this.cal.get(7) - 1;
    }*/
}
