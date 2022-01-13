package com.mk.service.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployerUtils {
    private static int sumImportance;
    /**
     * 所有员工信息
     */
    private static Map<Integer,Employer> allData = new HashMap<>();
    public static void getChildEmployer(int id){
        Employer rootEmployer = allData.get(id);
        if(rootEmployer != null){
            sumImportance += rootEmployer.getImportantVal();
        }
        if(rootEmployer.getChildEmployer().size() != 0){
            rootEmployer.getChildEmployer().forEach(item ->{
                getChildEmployer(item.getId());
            });
        }
    }

//    public static List<Integer> getChildEmployer(int id){
//        List<Integer> result = new ArrayList<>();
//        Employer rootEmployer = allData.get(id);
//        if(rootEmployer == null){
//            return result;
//        }
//        result.add(rootEmployer.getImportantVal());
//        if(rootEmployer.getChildEmployer().size() != 0){
//            rootEmployer.getChildEmployer().forEach(item ->{
//                result.addAll(getChildEmployer(item.getId()));
//            });
//        }
//        return result;
//    }
    public static int sumImportantVal(List<Integer> datas){
        return datas.stream().mapToInt(item -> item).sum();
    }

    public static void main(String[] args) {
        getChildEmployer(2);
        System.out.println(sumImportance);
    }

    static {
        Employer e_1 = new Employer(1,1);
        allData.put(1,e_1);
        Employer e_2 = new Employer(2,2);
        allData.put(2,e_2);
        Employer e_3 = new Employer(3,3);
        allData.put(3,e_3);
        Employer e_4 = new Employer(4,4);
        allData.put(4,e_4);
        Employer e_5 = new Employer(5,5);
        allData.put(5,e_5);
        Employer e_6 = new Employer(6,6);
        allData.put(6,e_6);
        e_1.getChildEmployer().add(e_3);
        e_1.getChildEmployer().add(e_2);
        e_2.getChildEmployer().add(e_5);
        e_2.getChildEmployer().add(e_4);
        e_5.getChildEmployer().add(e_6);
    }
}
