package com.mk.service.Map;

import java.util.*;
import java.util.stream.Collectors;

public class HashMap {
    public static void main(String[] args) {
//        java.util.HashMap<String, Object> map = new java.util.HashMap<String, Object>();
//        map.put("zhangsan", "张三");
//        map.put("lisi", "李四");
//        map.put(null, "李四");
//        map.put("lisi1", "李四");

        java.util.HashMap<Object, Object> map = new java.util.HashMap<Object, Object>();
       for(int i = 0;i<1000;i++){
           KeyEntity keyEntity = new KeyEntity();
           if(i == 999){
               map.put(keyEntity, "李四");
           }else{
               map.put(keyEntity, "李四");
           }

       }
//        遍历方法1：
//        Set<String> keySet =  map.keySet();
//        for(String key : keySet){
//            System.out.println(map.get(key));
//        }
//            遍历方法2：
//        Collection<Object> values = map.values();
//        for(Object o : values){
//            System.out.println(o);
//        }
//        遍历方法3：
//        Iterator<Map.Entry<String,Object>> entryIterator = map.entrySet().iterator();
//        for(;entryIterator.hasNext();){
//           Map.Entry<String,Object> entry = entryIterator.next();
//            System.out.println(entry.getValue());
//        }
        //        遍历方法4：
//        for(Map.Entry<String,Object> item :map.entrySet()){
//            System.out.println(item.getValue());
//        }
        //        遍历方法5：
        map.forEach((k,v)->{System.out.println(k+" "+v);} );
    }
}
