package com.projekt.fuelprice.utils;

public class QueryStringBuilder {
    private StringBuilder query;
    private boolean f = true;

    public QueryStringBuilder(String url){
        query = new StringBuilder(url);
        if(query.charAt(query.length()-1) != '?')
            query.append('?');
    }

    public QueryStringBuilder put(String param, String value){
        if(f) {
            query.append(param + "=" + value);
            f = false;
        }else{
            query.append("&" + param + "=" + value);
        }
        return this;
    }

    public String build(){
        return query.toString();
    }
}
