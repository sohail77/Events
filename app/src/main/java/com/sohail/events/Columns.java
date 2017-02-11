package com.sohail.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SOHAIL on 10/02/17.
 */
public class Columns {

    private List<String> timestamp = null;
    private List<String> name = null;
    private List<Integer> phoneno =null ;
    private List<String> eventName = null;
    private List<String> branch = null;
    private List<String> year = null;

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(List<Integer> phoneno) {
        this.phoneno = phoneno;
    }

    public List<String> getEventName() {
        return eventName;
    }

    public void setEventName(List<String> eventName) {
        this.eventName = eventName;
    }

    public List<String> getBranch() {
        return branch;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public List<String> getYear() {
        return year;
    }

    public void setYear(List<String> year) {
        this.year = year;
    }


}
