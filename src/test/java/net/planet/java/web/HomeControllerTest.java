package net.planet.java.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Bazlur Rahman Rokon
 * @since 7/31/16.
 */

//ref: http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
//ref: http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#test-method-withmockuser
@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@WithMockUser
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex_ShouldReturnOk() throws Exception {

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}