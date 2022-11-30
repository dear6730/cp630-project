package ec.stats.sb;

import java.io.Serializable;

/**
 * Serializable class which contains the attributes of the simple statistics
 */
public class StatsSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count = 0;
    private double min = 0;
    private double max = 0;
    private double mean =0;
    private double std = 0;
    public int getCount() {return count;}
    public void setCount(int a) {count=a;}
    public double getMin() {return min;}
    public void setMin(double a) {min=a;}
    public double getMax() {return max;}
    public void setMax(double a) {max=a;}
    public double getMean() {return mean;}
    public void setMean(double a) {mean=a;}
    public double getSTD() {return std;}
    public void setSTD(double a) {std=a;}
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("count:"+count+"\n");
        buffer.append("min:"+min+"\n");
        buffer.append("max:"+max+"\n");
        buffer.append("mean:"+mean +"\n");
        buffer.append("std:"+std+"\n");
        return buffer.toString();
    }
}