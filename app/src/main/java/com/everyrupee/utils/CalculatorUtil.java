package com.everyrupee.utils;

import java.math.BigDecimal;

/**
 * Created by aman on 24-09-2018.
 */

public class CalculatorUtil {
    private String preValue = "";
    private String postValue = "";
    private char operator = 'a';

    private String calcValue = "";

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        String[] slit = preValue.split("\\.");
        if (slit.length > 1) {
            String value = slit[1].length() < 3 ? slit[1] : slit[1].substring(0, 2);
            this.preValue = slit[0] + "." + value;
        } else if (preValue.length() <= 6 || preValue.contains(".")){
            this.preValue = preValue;
        }
    }

    public String getPostValue() {
        return postValue;
    }

    public void setPostValue(String postValue) {
        String[] slit = postValue.split("\\.");
        if (slit.length > 1) {
            String value = slit[1].length() < 3 ? slit[1] : slit[1].substring(0, 2);
            this.postValue = slit[0] + "." + value;
        } else if (postValue.length() <=6 || postValue.contains(".")){
            this.postValue = postValue;
        }
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void calValue() {
        switch (operator) {
            case '+':
                calcValue = String.valueOf(BigDecimal.valueOf(Double.parseDouble(preValue) + Double.parseDouble(postValue)));
                break;
            case '-':
                calcValue = String.valueOf(BigDecimal.valueOf(Double.parseDouble(preValue) - Double.parseDouble(postValue)));
                break;
            case 'X':
                calcValue = String.valueOf(BigDecimal.valueOf(Double.parseDouble(preValue) * Double.parseDouble(postValue)));
                break;
            case '/':
                calcValue = String.valueOf(BigDecimal.valueOf(Double.parseDouble(preValue) / Double.parseDouble(postValue)));
                break;
        }
    }

    public String getCalcValue() {
        return calcValue;
    }
}
