package com.mk.service.webservice;

import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;


@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class WebserviceImpl implements Webservice {
    public String sayHello(String name) {
        HttpServletResponse response;
        return name + ",welcome come !";
    }
}
