package br.pucrs.politecnica.assignments.feedback;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FeedbackControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FeedbackService feedbackService;
    
    @Test 
    void showFeedbackListTest() throws Exception{	
		this.mockMvc.perform(get("/feedbackList").with(user("student@teste.com").password("teste123").roles("STUDENT")))
        .andExpect(model().attributeExists("tasksProfessorList"))
        .andExpect(model().attributeExists("student"))
		.andExpect(status().isOk());
    }
    
    @Test 
    void shouldRedirectFeedbackListTest() throws Exception{	
		this.mockMvc.perform(get("/feedbackList").with(user("professor@teste.com").password("teste123").roles("PROFESSOR")))
		.andExpect(status().is3xxRedirection());
    }

    @Test
    void getFeedbackDetailsTest() throws Exception{	
      this.mockMvc.perform(get("/feedbackList/viewFeedback/1/3").with(user("student@teste.com").password("teste123").roles("STUDENT")))
          .andExpect(model().attributeExists("classes"))
      .andExpect(status().isOk());
      }
    
    
	
	
}
