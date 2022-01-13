package com.mk.service.employee;

import java.util.ArrayList;
import java.util.List;

public class Employer {
    private int id;
    private int importantVal;
    private List<Employer> childEmployer = new ArrayList<>();

    public Employer() {
    }

    public Employer(int id, int importantVal) {
        this.id = id;
        this.importantVal = importantVal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImportantVal() {
        return importantVal;
    }

    public void setImportantVal(int importantVal) {
        this.importantVal = importantVal;
    }

    public List<Employer> getChildEmployer() {
        return childEmployer;
    }

    public void setChildEmployer(List<Employer> childEmployer) {
        this.childEmployer = childEmployer;
    }
}
