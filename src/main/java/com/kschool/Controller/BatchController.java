package com.kschool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kschool.Entity.Batch;
import com.kschool.Entity.Student;
import com.kschool.Entity.Teacher;
import com.kschool.Repository.BatchRepository;
import com.kschool.Service.BatchService;
import com.kschool.Service.TeacherService;

import jakarta.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;

@Controller
public class BatchController {

	@Autowired
	private BatchService batchService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private BatchRepository batchRepository;

	@GetMapping("/batches/add")
	public String showAddBatchPage(HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("batch", new Batch());
		model.addAttribute("teachers", teacherService.getAllTeachers());
		return "add-batch";
	}

	@PostMapping("/batches/add")
	public String addBatch(@RequestParam String batchcode, @RequestParam String startTime, @RequestParam String endTime,
			@RequestParam Long teacherId, HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");

		if (startTime == null || startTime.trim().isEmpty() || endTime == null || endTime.trim().isEmpty()) {
			model.addAttribute("errorMessage", "Start time and end time cannot be empty.");
			return "add-batch";
		}

		Teacher teacher = teacherService.getTeacherById(teacherId);
		Batch batch = new Batch();
		batch.setBatchcode(batchcode);
		batch.setTeacher(teacher);

		try {
			LocalTime start = LocalTime.parse(startTime);
			LocalTime end = LocalTime.parse(endTime);

			if (start.isAfter(end)) {
				model.addAttribute("errorMessage", "Start time must be before end time.");
				return "add-batch";
			}

			batch.setStartTime(start);
			batch.setEndTime(end);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Invalid time format. Please use HH:mm format.");
			return "add-batch";
		}

		if (!batchService.addBatch(batch)) {
			model.addAttribute("errorMessage", "Batch name already assigned to another teacher or overlapping time!");
			return "add-batch";
		}

		model.addAttribute("userEmail", userEmail);
		model.addAttribute("successMessage", "Batch assigned successfully!");
		return "redirect:/batches/view";
	}

	@GetMapping("/batches/view")
	public String viewBatch(HttpSession session, Model model) {
		String userEmail = (String) session.getAttribute("LoggedInUser");
		List<Batch> batches = batchService.getAllBatch();

		for (Batch batch : batches) {
			if (batch.getStartTime() == null) {
				batch.setStartTime(LocalTime.of(0, 0));
			}
			if (batch.getEndTime() == null) {
				batch.setEndTime(LocalTime.of(0, 0));
			}
		}

		model.addAttribute("userEmail", userEmail);
		model.addAttribute("batches", batches);
		return "view-batch";
	}

	@GetMapping("/batches/search")
	public String searchBatches(@RequestParam("batchcode") String batchcode, Model model) {
		List<Batch> batches = batchRepository.findByBatchcodeContainingIgnoreCase(batchcode);
		model.addAttribute("batches", batches);
		return "view-batch";
	}

	@GetMapping("/batches/delete/{id}")
	public String deleteBatch(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    boolean isDeleted = batchService.deleteBatch(id);

	    if (isDeleted) {
	        redirectAttributes.addFlashAttribute("success", "Batch has been deleted successfully.");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Cannot delete batch. It is assigned to students.");
	    }

	    return "redirect:/batches/view";
	}




	

	   
	@GetMapping("/batches/dashboard")
	public String dashboard(HttpSession session, Model model) {
	    String userEmail = (String) session.getAttribute("LoggedInUser");
	    model.addAttribute("userEmail", userEmail);

	    // Add dashboard statistics
	    model.addAttribute("totalTeachers", teacherService.getTotalTeachers());
	    model.addAttribute("totalBatches", batchService.getTotalBatches());

	    return "dashboard";
	}

	@GetMapping("/batches/total")
	@ResponseBody
	public long getTotalBatches() {
	    return batchService.getTotalBatches();
	}
	}


