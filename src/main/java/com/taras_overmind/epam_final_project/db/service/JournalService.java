package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.command.commandResult.RedirectResult;
import com.taras_overmind.epam_final_project.db.repository.JournalRepo;

public class JournalService {
    private final JournalRepo journalRepo;

    public JournalService(JournalRepo journalRepo) {
        this.journalRepo = journalRepo;
    }

    public void setMarkForStudentByStudentCourseId(int mark, int id, String status) throws NumberFormatException{
        if (mark < 0 || mark > 100)
            throw new NumberFormatException();
        journalRepo.setMarkForStudentByStudentCourseId(mark, id, status);
    }
}
