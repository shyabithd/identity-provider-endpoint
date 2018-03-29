package org.wso2.carbon.identity.provider.mgt.endpoint.factories;

import org.wso2.carbon.identity.provider.mgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.provider.mgt.endpoint.impl.IdpsApiServiceImpl;

public class IdpsApiServiceFactory {

   private final static IdpsApiService service = new IdpsApiServiceImpl();

   public static IdpsApiService getIdpsApi()
   {
      return service;
   }
}
