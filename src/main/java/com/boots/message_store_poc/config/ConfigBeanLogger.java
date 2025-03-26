package com.boots.message_store_poc.config;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ConfigBeanLogger {

	@Bean
	public static BeanFactoryPostProcessor beanLogger() {
		return new BeanFactoryPostProcessor() {
			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
				System.out.println("---- Beans Defined in @Configuration classes ----");
				Arrays.stream(beanFactory.getBeanDefinitionNames())
						.filter(beanName -> {
							String beanClass = beanFactory.getBeanDefinition(beanName).getBeanClassName();
							return beanClass != null && beanClass.startsWith("com.boots.message_store_poc.config");
						})
						.sorted()
						.forEach(beanName -> System.out.println("🔹 " + beanName));
				System.out.println("-------------------------------------------------");
			}
		};
	}
}
