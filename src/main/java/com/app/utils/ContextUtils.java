package com.app.utils;

import com.app.common.configs.DatabaseConfig;
import com.app.common.helper.MessageBox;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextUtils {

    private static AnnotationConfigApplicationContext context;

    static {
        try {
            context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.error(null, "Không thể kết nối tới cơ sở dữ liệu!!!");
            System.exit(1);
        }

    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
