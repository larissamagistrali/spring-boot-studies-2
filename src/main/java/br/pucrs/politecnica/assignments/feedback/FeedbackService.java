package br.pucrs.politecnica.assignments.feedback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.pucrs.politecnica.assignments.classes.Classes;
import br.pucrs.politecnica.assignments.file.FileModel;
import br.pucrs.politecnica.assignments.student.Student;
import br.pucrs.politecnica.assignments.taskProfessor.TaskProfessor;
import br.pucrs.politecnica.assignments.taskStudent.TaskStudent;

@Service
public class FeedbackService {

	private FeedbackRepository feedbackRepository;

	public FeedbackService(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	public Feedback saveFeedback(FeedbackDTO feedback, TaskStudent taskStudent) throws Exception {
		return this.feedbackRepository.save(transformToFeedback(feedback, taskStudent));
	}

	public HashMap<Long, Integer> listFeedbacksAvailable(List<Classes> set) {
		HashMap<Long, Integer> list = new HashMap<>();
		for (Classes classes : set) {
			for (TaskProfessor taskProfessor : classes.getTasks()) {
				list.put(taskProfessor.getId(), 0);// inicializando com zero, pois quando vazia retornava NULL na view
			}
		}
		for (Feedback feedback : this.feedbackRepository.findAll()) {
			for (Classes classes : set) {
				for (Student student : classes.getStudents()) {
					// metodo coleta o IDtaskProfessor do feedback e retorna a lista atualizada.
					if (feedback.getTaskStudent().getStudent().getId() == student.getId()) {
						list.put(feedback.getTaskStudent().getTaskProfessor().getId(),
								list.get(feedback.getTaskStudent().getTaskProfessor().getId()) + this.feedbackRepository
										.findByTaskStudent(student.getId(), Boolean.TRUE).get());
					}
				}
			}
		}
		return list;
	}

	public Feedback transformToFeedback(FeedbackDTO feedbackDTO, TaskStudent taskStudent) throws IOException {
		Feedback feedback = new Feedback();
		feedback.setId(taskStudent.getFeedback().getId());
		feedback.setComment(feedbackDTO.getComment());
		FileModel filemodel = new FileModel(feedbackDTO.getFiles().getOriginalFilename(),
				feedbackDTO.getFiles().getContentType(), feedbackDTO.getFiles().getBytes());
		feedback.setFileModel(filemodel);
		feedback.setSent(false);
		feedback.setStatus(feedbackDTO.getFinalizada());
		feedback.setTaskStudent(taskStudent);
		return feedback;
	}

	public HashMap<Long, Integer> listFeedbacksNull(List<Classes> set) {
		HashMap<Long, Integer> list = new HashMap<>();
		for (Classes classes : set) {
			for (TaskProfessor taskProfessor : classes.getTasks()) {
				list.put(taskProfessor.getId(), 0);// inicializando com zero, pois quando vazia retornava NULL na view
			}
		}
		return list;
	}

	public Feedback getFeedbackById(Long id) throws Exception{
		
		Optional<Feedback> feedback = this.feedbackRepository.findById(id);
		return feedback.orElseThrow(() -> new Exception());
	}

	public void setSentAll(Set<TaskStudent> setTasksStudent) throws Exception{
		setTasksStudent.stream().map(t -> t.getFeedback()).filter(t -> t.getStatus()).forEach(t -> t.setSent(true));
		setTasksStudent.stream().map(t -> t.getFeedback()).forEach(t -> this.feedbackRepository.save(t));
	}

}
