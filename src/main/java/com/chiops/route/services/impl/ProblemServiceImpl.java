package com.chiops.route.services.impl;

import com.chiops.route.entities.Problem;
import com.chiops.route.entities.Route;
import com.chiops.route.libs.dtos.ProblemDTO;
import com.chiops.route.libs.exceptions.exception.*;
import com.chiops.route.repositories.ProblemRepository;
import com.chiops.route.repositories.RoutesRepository;
import com.chiops.route.services.ProblemService;
import jakarta.inject.Singleton;

@Singleton
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final RoutesRepository routeRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository, RoutesRepository routeRepository) {
        this.problemRepository = problemRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public ProblemDTO assignProblem(ProblemDTO problem) {
        Route route = routeRepository.findByVehicleVin(problem.getVin())
                .orElseThrow(() -> new NotFoundException("Route not found: " + problem.getVin()));

        if (route.getProblem() != null) {
            throw new ConflictException("Problem already exists for this route: " + problem.getVin());
        }

        Problem problemEntity = new Problem();
        problemEntity.setDescription(problem.getDescription());
        problemEntity.setComment(problem.getComment());
        problemEntity.setName(problem.getName());

        Problem savedProblem = problemRepository.save(problemEntity);

        route.setProblem(savedProblem);
        routeRepository.update(route);

        return problemToDto(savedProblem);
    }

    @Override
    public ProblemDTO updateProblem(ProblemDTO problem) {
        if (routeRepository.findByVehicleVin(problem.getVin()).isEmpty()) {
            throw new NotFoundException("Route not found for this vehicle: " + problem.getVin());
        }

        Problem problemEntity = new Problem();
        problemEntity.setDescription(problem.getDescription());
        problemEntity.setComment(problem.getComment());
        problemEntity.setName(problem.getName());

        return problemToDto(problemRepository.update(problemEntity));
    }

    @Override
    public ProblemDTO getProblemById(String vin) {
        Route route = routeRepository.findByVehicleVin(vin)
                .orElseThrow(() -> new NotFoundException("Route not found: " + vin));
        
        if (route.getProblem() == null) {
            throw new NotFoundException("No problem assigned to this route: "+ vin);
        }
        
        return problemToDto(route.getProblem());
    }

    private ProblemDTO problemToDto(Problem problem) {
        ProblemDTO problemDTO = new ProblemDTO();
        problemDTO.setDescription(problem.getDescription());
        problemDTO.setComment(problem.getComment());
        problemDTO.setName(problem.getName());
        return problemDTO;
    }
}