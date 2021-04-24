package br.pucrs.politecnica.assignments.taskProfessor;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.classes.ClassesService;
import br.pucrs.politecnica.assignments.discipline.Discipline;
import br.pucrs.politecnica.assignments.discipline.DisciplineService;
import br.pucrs.politecnica.assignments.feedback.FeedbackService;
import br.pucrs.politecnica.assignments.professor.Professor;
import br.pucrs.politecnica.assignments.professor.ProfessorService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskProfessorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskProfessorService taskProfessorService;

	@MockBean
	private ClassesService classesService;

	@MockBean
	private ProfessorService professorService;

	@MockBean
	private DisciplineService disciplineService;

	@MockBean
	private FeedbackService feedbackService;

	@Test
	void shouldNewTaskProfessorGet() throws Exception {
		List<Discipline> disciplines = new ArrayList<>();
		given(disciplineService.getDisciplinesByClasses(classesService.getClassesByUser(Mockito.any())))
				.willReturn(disciplines);

		List<Classes> classesDiscipline = new ArrayList<>();
		given(classesService.getClassesByUser(Mockito.any())).willReturn(classesDiscipline);

		this.mockMvc
				.perform(get("/taskProfessor/new")
						.with(user("student@teste.com").password("teste123").roles("USER", "STUDENT")))
				.andExpect(model().attributeExists("disciplines"))
				.andExpect(model().attributeExists("classesDiscipline"))
				.andExpect(model().attributeExists("taskProfessorDTO")).andExpect(status().isOk());
	}

	@Test
	void shouldNewTaskProfessorPost() throws Exception {
		this.mockMvc.perform(
				post("/taskProfessor/new").with(user("student@teste.com").password("teste123").roles("USER", "STUDENT"))
						.param("classes", "1").param("title", "abc").param("details", "abc").param("status", "true")
						.param("taskTime", "2021-04-01T00:00"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	void shouldShowTaskStudentList() throws Exception {
		TaskProfessor task = new TaskProfessor();
		given(this.taskProfessorService.getTaskProfessor(Mockito.any())).willReturn(task);

		Classes classes = new Classes();
		classes.setDiscipline(new Discipline());
		given(this.classesService.getClassesById(Mockito.any())).willReturn(classes);

		this.mockMvc
				.perform(get("/taskProfessor/viewDelivery/{classesId}/{taskProfessorId}", 1, 1)
						.with(user("student@teste.com").password("teste123").roles("USER", "STUDENT")))
				.andExpect(model().attributeExists("classes")).andExpect(model().attributeExists("taskProfessor"))
				.andExpect(status().isOk());
	}

	@Test
	void shouldShowClassesTask() throws Exception {
		List<Classes> classes = new ArrayList<Classes>();
		Classes aux = new Classes();
		aux.setId(Long.valueOf("1"));
		classes.add(aux);

		Professor professor = new Professor();
		professor.setClasses(classes);
		given(this.professorService.getUserProfessor(Mockito.any())).willReturn(new Professor());

		List<TaskProfessor> tasksProf = new ArrayList<>();
		given(this.taskProfessorService.listClassesTaskProfessor(Mockito.any(), Mockito.any())).willReturn(tasksProf);

		HashMap<Long, Integer> classes2 = new HashMap<Long, Integer>();
		given(this.feedbackService.listFeedbacksAvailable(Mockito.any())).willReturn(classes2);

		this.mockMvc
				.perform(get("/taskProfessor/viewDelivery")
						.with(user("student@teste.com").password("teste123").roles("USER", "STUDENT")))
				.andExpect(model().attributeExists("classesList")).andExpect(model().attributeExists("feedbacks"))
				.andExpect(status().isOk());

	}

	@Test
	void shouldShowListTaskWithoutClassesTasks() throws Exception {
		Discipline discipline = new Discipline();
		given(this.disciplineService.findById(Mockito.anyLong())).willReturn(discipline);
		Professor professor = new Professor();
		given(this.professorService.getUserProfessor(Mockito.any())).willReturn(professor);

		this.mockMvc
				.perform(get("/taskProfessor/listTaskProfessor")
						.with(user("professor@teste.com").password("teste123").roles("USER", "PROFESSOR")))
				.andExpect(model().attributeExists("disciplines")).andExpect(status().isOk());

	}

	@Test
	void shouldShowListTask() throws Exception {
		Discipline discipline = new Discipline();
		given(this.disciplineService.findById(Mockito.anyLong())).willReturn(discipline);
		Professor professor = new Professor();
		given(this.professorService.getUserProfessor(Mockito.any())).willReturn(professor);

		this.mockMvc
				.perform(get("/taskProfessor/listTaskProfessor")
						.with(user("professor@teste.com").password("teste123").roles("USER", "PROFESSOR"))
						.param("disciplineId", "1"))
				.andExpect(model().attributeExists("classesTasks")).andExpect(model().attributeExists("disciplines"))
				.andExpect(status().isOk());
	}

	@Test
	void shouldShowListTaskException() throws Exception {
		given(this.professorService.getUserProfessor(Mockito.any())).willThrow(new Exception());
		
		this.mockMvc
				.perform(get("/taskProfessor/listTaskProfessor")
						.with(user("professor@teste.com").password("teste123").roles("USER", "PROFESSOR")))
				.andExpect(status().is3xxRedirection());
	}
}
