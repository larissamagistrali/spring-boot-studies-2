package br.pucrs.politecnica.assignments.file;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.pucrs.politecnica.assignments.View;

@RestController
public class DownloadController {

	@Autowired
	private FileRepository fileRepository;

	@JsonView(View.FileInfo.class)
	@GetMapping("/api/getFileTaskStudent/{taskStudentId}")
	public List<FileModel> getListFileTaskStudent(@PathVariable Long taskStudentId) throws Exception {
		Optional<List<FileModel>> fileModelOptList = fileRepository.findAllByTaskStudentId(taskStudentId);
		return fileModelOptList.orElseThrow(() -> new Exception());
	}
	
	@JsonView(View.FileInfo.class)
	@GetMapping("/api/getFileTaskProfessor/{taskProfessorId}")
	public List<FileModel> getListFileTaskProfessor(@PathVariable Long taskProfessorId) throws Exception {
		Optional<List<FileModel>> fileModelOptList = fileRepository.findAllByTaskProfessorId(taskProfessorId);
		return fileModelOptList.orElseThrow(() -> new Exception());
	}

	@GetMapping(value = "api/file/{id}")
	public ResponseEntity<ByteArrayResource> getFile(@PathVariable String id) {
		Optional<FileModel> fileOptional = fileRepository.findById(Long.valueOf(id));

		if (fileOptional.isPresent()) {
			FileModel file = fileOptional.get();
			ByteArrayResource resource = new ByteArrayResource(file.getData());

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
					.contentType(MediaType.valueOf(file.getMimetype())).body(resource);
		}

		return ResponseEntity.status(404).body(null);
	}
	
}
