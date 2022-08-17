package com.fischer;

import com.fischer.pojo.UserWithScore;
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
        UserWithScore user = new UserWithScore();
        user.setQq("845630236");
        emailService.sendHtmlFile(user);

    }

}
