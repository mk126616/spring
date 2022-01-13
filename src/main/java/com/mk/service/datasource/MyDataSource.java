package com.mk.service.datasource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyDataSource extends AbstractRoutingDataSource {
    protected Object determineCurrentLookupKey() {
        return "datasource";
    }

}
