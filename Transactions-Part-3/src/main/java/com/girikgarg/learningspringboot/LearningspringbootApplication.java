package com.girikgarg.learningspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class LearningspringbootApplication {

    @Autowired
    private SampleService sampleService;

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(LearningspringbootApplication.class, args);
//        SampleService sampleService = context.getBean(SampleService.class);
//        sampleService.performTransactionalOperation();
//		context.close();

//        UserProgrammaticApproach1 userProgrammaticApproach1 = context.getBean(UserProgrammaticApproach1.class);
//        userProgrammaticApproach1.updateUser();

//        UserProgrammticApproach2 userProgrammticApproach2 = context.getBean(UserProgrammticApproach2.class);
//        userProgrammticApproach2.updateUser();

//        RequiredPropagationDemoService service = context.getBean(RequiredPropagationDemoService.class);
//        service.outerMethod();
        RequiresNewPropagationDemoService requiresNewService = context.getBean(RequiresNewPropagationDemoService.class);
        requiresNewService.outerMethod();
	}

}
