package com.chiops.route.services;

import com.chiops.route.libs.dtos.RouteDTO;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public interface RouteService {

    RouteDTO createRoute(RouteDTO routeDTO);

    RouteDTO getRouteByVehicleVin(String vin);

    List<RouteDTO> getAllRoutes();
}
