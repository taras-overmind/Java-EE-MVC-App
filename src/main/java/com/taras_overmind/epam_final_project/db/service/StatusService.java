package com.taras_overmind.epam_final_project.db.service;

import com.taras_overmind.epam_final_project.db.entity.StatusEntity;
import com.taras_overmind.epam_final_project.db.repository.StatusRepo;

import java.util.List;

public class StatusService {
    private final StatusRepo statusRepo;

    public StatusService(StatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    public List<StatusEntity> getAllStatuses() {
        return statusRepo.getAllStatuses();
    }
}
