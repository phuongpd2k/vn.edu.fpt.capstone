package vn.edu.fpt.capstone.service;

public interface ThanhPhoService {
	void findById(Long id);
	void findBySlug(String slug);
	void findAll();
}
