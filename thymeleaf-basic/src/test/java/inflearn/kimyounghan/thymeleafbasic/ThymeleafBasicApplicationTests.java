package inflearn.kimyounghan.thymeleafbasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ThymeleafBasicApplicationTests {

	@Autowired
	private ApplicationContext ac;

	@Test
	void contextLoads() {
		for (String beanDefinitionName : ac.getBeanDefinitionNames())
			System.out.println(beanDefinitionName);
	}

}
