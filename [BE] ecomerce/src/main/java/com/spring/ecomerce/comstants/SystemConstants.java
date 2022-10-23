package com.spring.ecomerce.comstants;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemConstants {

    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static boolean DEL_FLG_ON = true;
    public final static boolean DEL_FLG_OFF = false;
    public final static List<String> IMG_PNG = new ArrayList<>(Arrays.asList("image/png","image/jpg"));

}
