package com.bluekhata.data.model.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by aman on 21-09-2018.
 */

public class CurrencyModel {

    private String currencySymbol;
    private String currencyName;
    private ArrayList<CurrencyModel> list;
    private Comparator<CurrencyModel> currencyNameComparator = new Comparator<CurrencyModel>() {
        @Override
        public int compare(CurrencyModel jc1, CurrencyModel jc2) {
            return (int) (jc1.getCurrencySymbol().compareTo(jc2.getCurrencySymbol()));
        }
    };

    public CurrencyModel(){
        list = new ArrayList<>();
    }

    public CurrencyModel(String currencySymbol, String currencyName) {
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public ArrayList<CurrencyModel> getAllCurrencies() {
        list.add(new CurrencyModel("UAE Dirham","د.إ"));
        list.add(new CurrencyModel("Afghani","Af"));
        list.add(new CurrencyModel("Lek","L"));
        list.add(new CurrencyModel("Armenian Dram","Դ"));
        list.add(new CurrencyModel("Kwanza","Kz"));
        list.add(new CurrencyModel("Argentine Peso","$"));
        list.add(new CurrencyModel("Australian Dollar","$"));
        list.add(new CurrencyModel("Aruban Guilder/Florin","ƒ"));
        list.add(new CurrencyModel("Konvertibilna Marka","КМ"));
        list.add(new CurrencyModel("Barbados Dollar","$"));
        list.add(new CurrencyModel("Bulgarian Lev","лв"));
        list.add(new CurrencyModel("Bahraini Dinar","ب.د"));
        list.add(new CurrencyModel("Burundi Franc","₣"));

        list.add(new CurrencyModel("Bermudian Dollar","$"));
        list.add(new CurrencyModel("Brunei Dollar","$"));
        list.add(new CurrencyModel("Boliviano","Bs"));
        list.add(new CurrencyModel("Brazilian Real","R$"));
        list.add(new CurrencyModel("Bahamian Dollar","$"));
        list.add(new CurrencyModel("Pula","P"));
        list.add(new CurrencyModel("Belarussian Ruble","Br"));
        list.add(new CurrencyModel("Belize Dollar","$"));
        list.add(new CurrencyModel("Azerbaijanian Manat","ман"));
        list.add(new CurrencyModel("Konvertibilna Marka","КМ"));
        list.add(new CurrencyModel("Canadian Dollar","$"));
        list.add(new CurrencyModel("Congolese Franc","₣"));
        list.add(new CurrencyModel("Swiss Franc","₣"));
        list.add(new CurrencyModel("Chilean Peso","$"));
        list.add(new CurrencyModel("Yuan","¥"));

        list.add(new CurrencyModel("Colombian Peso","$"));
        list.add(new CurrencyModel("Costa Rican Colon","₡"));
        list.add(new CurrencyModel("Cuban Peso","$"));
        list.add(new CurrencyModel("Cape Verde Escudo","$"));
        list.add(new CurrencyModel("Czech Koruna","Kč"));
        list.add(new CurrencyModel("Djibouti Franc","₣"));
        list.add(new CurrencyModel("Dominican Peso","$"));
        list.add(new CurrencyModel("Algerian Dinar","د.ج"));
        list.add(new CurrencyModel("Egyptian Pound","£"));
        list.add(new CurrencyModel("US Dollar","$"));
        list.add(new CurrencyModel("Indian Rupee","₹"));
        list.add(new CurrencyModel("Pakistan Rupee","₨"));
        list.add(new CurrencyModel("Philippine Peso","₱"));
        list.add(new CurrencyModel("Naira","₦"));

        list.add(new CurrencyModel("Euro","€"));
        list.add(new CurrencyModel("Fiji Dollar","$"));
        list.add(new CurrencyModel("Danish Krone","kr"));
        list.add(new CurrencyModel("Swedish Krona","kr"));
        list.add(new CurrencyModel("Norwegian Krone","kr"));
        list.add(new CurrencyModel("Nepalese Rupee","Rs"));
        list.add(new CurrencyModel("Sri Lanka Rupee","Rs"));
        list.add(new CurrencyModel("Taka","৳"));
        list.add(new CurrencyModel("Dong","₫"));
        list.add(new CurrencyModel("Kyat","K"));

        Collections.sort(list, currencyNameComparator);

        return list;
    }
}
