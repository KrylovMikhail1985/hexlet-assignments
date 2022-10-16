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
    private Map<String, Class> annotatedBeans = new HashMap<>();
    private Map<String, String> loggingLevels = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Object.class);
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            String level = bean.getClass().getAnnotation(Inspect.class).level();
            annotatedBeans.put(beanName, bean.getClass());
            loggingLevels.put(beanName, level);
        }
       return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!annotatedBeans.containsKey(beanName)) {
            return bean;
        }
        Class beanClass = annotatedBeans.get(beanName);
        String level = loggingLevels.get(beanName);

        return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                    String message = String.format(
                            "Was called method: %s() with arguments: %s",
                            method.getName(),
                            Arrays.toString(args)
                    );

                    if (level.equals("info")) {
                        LOGGER.info(message);
                    } else {
                        LOGGER.debug(message);
                    }

                    return method.invoke(bean, args);
                }
        );
    }
}
// END
