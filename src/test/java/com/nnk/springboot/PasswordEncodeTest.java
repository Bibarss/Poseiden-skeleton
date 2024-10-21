package com.nnk.springboot;


import com.nnk.springboot.controllers.BidListController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@ExtendWith(SpringExtension.class)  // JUnit 5: Extension pour les tests Spring
@SpringBootTest
public class PasswordEncodeTest {

    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Test
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("123456");
        logger.info("Le mot de passe '123456' encodé est le suivant {} ", pw);

         pw = encoder.encode("5574QQ");
        logger.info("Le mot de passe encodé '5574QQ' est le suivant {} ", pw);
    }
}
