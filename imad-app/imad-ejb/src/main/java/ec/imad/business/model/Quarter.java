package ec.imad.business.model;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Quarter {

    private String name;
    private Integer quarter;
    private Integer year;
    private List<String> monthList = new ArrayList<String>();
    private Quarter previousQuarter = null;

    // empty constructor gives current quarter
    public Quarter() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        quarter = determineQuarter(cal.get(Calendar.MONTH));
        setMonthList(quarter);
        name = "Q" + quarter;
    }

    // public Quarter( Integer quarter, Integer year ) {
    //     this.quarter = quarter;
    //     this.year = year;
    //     setMonthList(quarter);
    //     name = "Q" + quarter;
    // }

    public Quarter( Integer month, Integer year ) {
        // this.quarter = quarter;
        this.year = year;
        quarter = determineQuarter(month);
        setMonthList(quarter);
        name = "Q" + quarter;
    }

    public String getName() {
        return name;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public Integer getYear() {
        return year;
    }

    public List<String> getMonthList() {
        return monthList;
    }

    // public Integer determineQuarter(Calendar cal) {
    //     Integer month = cal.get(Calendar.MONTH);

    //     switch(month) {
    //         case Calendar.JANUARY:
    //         case Calendar.FEBRUARY:
    //         case Calendar.MARCH:
    //             return 1;
    //         case Calendar.APRIL:
    //         case Calendar.MAY:
    //         case Calendar.JUNE:
    //             return 2;
    //         case Calendar.JULY:
    //         case Calendar.AUGUST:
    //         case Calendar.SEPTEMBER:
    //             return 3;
    //         case Calendar.OCTOBER:
    //         case Calendar.NOVEMBER:
    //         case Calendar.DECEMBER:
    //             return 4;
    //     }

    //     return -1;
    // }

    public Integer determineQuarter(Integer month) {

        switch(month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                return 1;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                return 2;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return 3;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                return 4;
        }

        return -1;
    }

    // public static Integer determineQuarter(Integer month) {

    //     switch(month) {
    //         case Calendar.JANUARY:
    //         case Calendar.FEBRUARY:
    //         case Calendar.MARCH:
    //             return 1;
    //         case Calendar.APRIL:
    //         case Calendar.MAY:
    //         case Calendar.JUNE:
    //             return 2;
    //         case Calendar.JULY:
    //         case Calendar.AUGUST:
    //         case Calendar.SEPTEMBER:
    //             return 3;
    //         case Calendar.OCTOBER:
    //         case Calendar.NOVEMBER:
    //         case Calendar.DECEMBER:
    //             return 4;
    //     }

    //     return -1;
    // }

    private void setMonthList(Integer quarter) {
        switch(quarter) {
            case 1:
                monthList.add("Jan");
                monthList.add("Feb");
                monthList.add("Mar");
                break;
            case 2:
                monthList.add("Apr");
                monthList.add("May");
                monthList.add("Jun");
                break;
            case 3:
                monthList.add("Jul");
                monthList.add("Aug");
                monthList.add("Sep");
                break;
            case 4:
                monthList.add("Oct");
                monthList.add("Nov");
                monthList.add("Dec");
                break;                
        }
    }

    public Quarter getPreviousQuarter() {

        // previous quarter hasn't been set/used before
        if(previousQuarter == null) {
            Integer prev_Q = quarter - 1;
            Integer prev_Y = year;
            if(prev_Q == 0) {
                prev_Q = 4;
                prev_Y = prev_Y - 1;
            }
            previousQuarter = new Quarter(prev_Q, prev_Y);
        }

        return previousQuarter;
    }

    @Override
    public String toString() {
        String s = name + ", " + year + "\n" + monthList;

        if(previousQuarter != null) {
            s += "\nPrevious Quarter: " + previousQuarter.getName();
        }

        return s;
    }

}