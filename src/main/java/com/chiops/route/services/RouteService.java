package com.chiops.route.services;

import com.chiops.route.libs.dtos.RouteDTO;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public interface RouteService {

    RouteDTO createRoute(RouteDTO routeDTO);

    RouteDTO updateRoute(RouteDTO routeDTO);

    RouteDTO deleteRoute(String name);

    RouteDTO getRouteByVehicleVin(String vin);

    List<RouteDTO> getAllRoutes();
}
