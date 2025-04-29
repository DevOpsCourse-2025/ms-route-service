package com.chiops.route.services;

import com.chiops.route.entities.Problem;
import com.chiops.route.libs.dtos.ProblemDTO;


public interface ProblemService {

    ProblemDTO assignProblem(ProblemDTO problem);

    ProblemDTO updateProblem(ProblemDTO problem);

    ProblemDTO getProblemById(String vin);
}
