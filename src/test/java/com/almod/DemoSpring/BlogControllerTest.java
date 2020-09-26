package com.almod.DemoSpring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/post-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/post-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "7777")
public class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private int postsUser_7777 = 2;

    @Test
    public void postListTest() throws Exception {
        this.mockMvc.perform(get("/blog"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='posts']/div").nodeCount(3));
    }

    @Test
    public void filterPostForNameTest() throws Exception {
        this.mockMvc.perform(get("/blog")
                .param("name", "Russian")
                .param("radiobutton", "rad2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(1))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='1']").exists());
    }

    @Test
    public void filterPostForUsernameTest() throws Exception {
        this.mockMvc.perform(get("/blog")
                .param("name", "7777")
                .param("radiobutton", "rad1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(postsUser_7777))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='1']").exists())
                .andExpect(xpath("//*[@id='posts']/div[@data-id='2']").exists());
    }

    @Test
    public void addPost() throws Exception {
        this.mockMvc.perform(post("/blog/add")
                .param("title", "German")
                .param("anons", "read me")
                .param("full_text", "Hallo Welt"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));

        this.mockMvc.perform(get("/blog"))
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(4))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='10']").exists())
                .andExpect(xpath("//*[@id='posts']/div[@data-id='10']/div/h4").string("German"))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='10']/div/span").string("read me"));
    }

    @Test
    public void removePost() throws Exception {
        this.mockMvc.perform(post("/blog/10/remove"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));

        this.mockMvc.perform(get("/blog"))
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(3))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='10']").doesNotExist());
    }

    @Test
    public void editPost() throws Exception {
        this.mockMvc.perform(post("/blog/1/edit")
                .param("title", "Kazakhstan")
                .param("anons", "Salem")
                .param("full_text", "Salem Alem"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/1"));

        this.mockMvc.perform(get("/blog"))
                .andExpect(xpath("//*[@id='posts']/div").nodeCount(3))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='1']").exists())
                .andExpect(xpath("//*[@id='posts']/div[@data-id='1']/div/h4").string("Kazakhstan"))
                .andExpect(xpath("//*[@id='posts']/div[@data-id='1']/div/span").string("Salem"));
    }

    @Test
    public void postListUserTest() throws Exception {
        this.mockMvc.perform(get("/blog/1/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='posts']/div").nodeCount(postsUser_7777));
    }
}
