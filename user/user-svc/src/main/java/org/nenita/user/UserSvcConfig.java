package org.nenita.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.nenita.*.svc"})
public class UserSvcConfig {

}
