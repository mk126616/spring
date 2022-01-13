package com.mk.service.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Webservice {
    String sayHello(String name);
}
