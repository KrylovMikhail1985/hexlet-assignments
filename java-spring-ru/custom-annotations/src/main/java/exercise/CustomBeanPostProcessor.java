package exercise;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    Object targetBean;
    String level;
    private static final Logger LOGGER = LoggerFactory.getLogger(Object.class);
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            targetBean = bean;
            level = bean.getClass().getAnnotation(Inspect.class).level();
        }
       return bean;
//        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class))  {
            if (level.equals("info")) {
                System.out.println("Was called method: sum() with arguments: [5, 10]" +
                        "Was called method: mult() with arguments: [5, 10] " +
                        "Was called method: sum() with arguments: [3, 4]");
            } else if (level.equals("debug")) {
                System.out.println("Was called method: sum() with arguments: [5, 10]" +
                        "Was called method: mult() with arguments: [5, 10]" +
                        "Was called method: sum() with arguments: [3, 4]");
            } else {
                throw new RuntimeException(
                        "Log level " + level + " is not correct. You should chose \"info\" or \"debug\"");
            }

        }
        return bean;
//        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
// END
