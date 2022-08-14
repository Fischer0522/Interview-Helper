package com.fischer;

import com.fischer.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NewCoderExcelApplicationTests {
    @Autowired
    private EmailService emailService;

    @Test
    void contextLoads() {

    }

}
