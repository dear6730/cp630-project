package ec.imad.business.model;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Quarter {

    private String name;
    private Integer quarter;
    private Integer year;
    private List<String> monthList = new ArrayList<String>();
    private List<Integer> monthListValues = new ArrayList<Integer>();
    private Quarter previousQuarter = null;

    // empty constructor gives current quarter
    public Quarter() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        quarter = determineQuarter(cal.get(Calendar.MONTH));
        setMonthLists(quarter);
        name = "Q" + quarter;
    }

    public Quarter( Integer quarter, Integer year ) {
        this.quarter = quarter;
        this.year = year;
        setMonthLists(quarter);
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

    public List<Integer> getMonthListValues() {
        return monthListValues;
    }

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

    private void setMonthLists(Integer quarter) {
        switch(quarter) {
            case 1:
                monthList.add("Jan");
                monthList.add("Feb");
                monthList.add("Mar");
                monthListValues.add(1);
                monthListValues.add(2);
                monthListValues.add(3);
                break;
            case 2:
                monthList.add("Apr");
                monthList.add("May");
                monthList.add("Jun");
                monthListValues.add(4);
                monthListValues.add(5);
                monthListValues.add(6);                
                break;
            case 3:
                monthList.add("Jul");
                monthList.add("Aug");
                monthList.add("Sep");
                monthListValues.add(7);
                monthListValues.add(8);
                monthListValues.add(9);
                break;
            case 4:
                monthList.add("Oct");
                monthList.add("Nov");
                monthList.add("Dec");
                monthListValues.add(10);
                monthListValues.add(11);
                monthListValues.add(12);                
                break;                
        }
    }

    public Quarter getPreviousQuarter() {

        if(previousQuarter == null) {
            Integer prevQuarter = quarter - 1;
            Integer prevQuarterYear = year;

            if(prevQuarter == 0) {
                prevQuarter = 4;
                prevQuarterYear = prevQuarterYear - 1;
            }
            previousQuarter = new Quarter(prevQuarter, prevQuarterYear);
            
        } else {
            // skip
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