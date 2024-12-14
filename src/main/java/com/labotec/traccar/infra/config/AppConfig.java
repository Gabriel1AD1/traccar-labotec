    package com.labotec.traccar.infra.config;

    import com.labotec.traccar.app.implementation.*;
    import com.labotec.traccar.app.mapper.model.*;
    import com.labotec.traccar.app.ports.input.email.GoogleEmail;
    import com.labotec.traccar.app.ports.input.repository.*;
    import com.labotec.traccar.app.ports.out.services.*;
    import lombok.AllArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.transaction.annotation.Transactional;

    @Configuration
    @AllArgsConstructor
    @Transactional
    public class AppConfig {
        private final AlertRepository alertRepository;
        private final RouteBusStopResponseSegmentRepository routeBusStopSegmentResponseRepository;
        private final DeviceTraccarRepository deviceTraccarRepository;
        private final StopRegisterRepository stopRegisterRepository;
        private final BusStopRepository busStopRepository;
        private final CompanyRepository companyRepository;
        private final DriverRepository driverRepository;
        private final LocationRepository locationRepository;
        private final RouteRepository routeRepository;
        private final ScheduleRepository scheduleRepository;
        private final VehicleRepository vehicleRepository;
        private final RouteBusStopRepository routeBusStopRepository;
        private final BusStopModelMapper busStopModelMapper;
        private final CompanyModelMapper companyModelMapper;
        private final DriverModelMapper driverModelMapper;
        private final LocationModelMapper locationModelMapper;
        private final RouteModelMapper routeModelMapper;
        private final ScheduleModelMapper scheduleModelMapper;
        private final VehicleModelMapper vehicleModelMapper;
        private final RouteBusStopModelMapper routeBusStopModelMapper;
        private final VehicleTypeRepository vehicleTypeRepository;
        private final GeofenceCircularRepository geofenceCircularRepository;
        private final GeofenceCircularModelMapper geofencePoligonalModelMapper;
        private final UserRepository userRepository;
        private final GoogleEmail googleEmail;
        private final RouteBusStopSegmentRepository routeBusStopSegmentRepository;
        private final OverviewPolylineRepository overviewPolylineRepository;
        private final DriverScheduleRepository driverScheduleRepository;
        private final StateVehiclePositionRepository stateVehiclePositionRepository;
        private final VehiclePositionRepository vehiclePositionRepository;
        @Bean(name = "busStopService")
        public BusStopService busStop() {
            return new BusStopServiceI(busStopRepository,companyRepository,busStopModelMapper,userRepository);
        }
        @Bean(name = "alertService")
        public AlertService alertService(){
            return  new AlertServiceI(alertRepository);
        }
        @Bean(name = "companyService")
        public CompanyService company() {
            return new CompanyServiceI(companyRepository,companyModelMapper);
        }

        @Bean(name = "driverService")
        public DriverService driver() {
            return new DriverServiceI(
                    driverRepository,
                    companyRepository,
                    driverModelMapper,
                    userRepository
            );
        }

        @Bean(name = "locationService")
        public LocationService location() {
            return new LocationServiceI(
                    busStopRepository,
                    locationRepository,
                    companyRepository,
                    locationModelMapper,
                    userRepository
            );
        }



        @Bean(name = "routeService")
        public RouteService route() {
            return new RouteServiceI(
                    routeRepository,
                    companyRepository,
                    busStopRepository,
                    routeBusStopRepository,
                    routeModelMapper,
                    overviewPolylineRepository,
                    vehicleRepository,
                    scheduleRepository,
                    userRepository,
                    routeBusStopSegmentRepository
            );
        }

        @Bean(name = "scheduleService")
        public ScheduleService schedule() {
            return new ScheduleServiceI(
                    scheduleRepository,
                    vehicleRepository,
                    driverRepository,
                    locationRepository,
                    routeRepository,
                    companyRepository,
                    geofenceCircularRepository,
                    scheduleModelMapper,
                    driverScheduleRepository,
                    userRepository,
                    routeBusStopSegmentResponseRepository,
                    vehiclePositionRepository,
                    stopRegisterRepository
            );
        }

        @Bean(name = "vehicleService")
        public VehicleService vehicle() {
            return new VehicleServiceI(
                vehicleRepository,
                vehicleModelMapper,
                companyRepository,
                vehicleTypeRepository,
                    userRepository,
                    deviceTraccarRepository
            );}


        @Bean(name = "geofenceService")
        public GeofencePoligonalService geofencePoligonalService(){
            return new CircularGeofenceServiceI(
                    geofenceCircularRepository,
                    geofencePoligonalModelMapper,
                    userRepository
            );
        }
        @Bean(name = "integrationTraccarService")
        public IntegrationTraccarService traccarService(){
            return new IntegrationTraccarImpl(
                    routeRepository,
                     companyRepository,
                     busStopRepository,
                     routeBusStopRepository,
                     routeModelMapper,
                     overviewPolylineRepository,
                     vehicleRepository,
                     scheduleRepository,
                    googleEmail);
        }
        @Bean(name = "userService")
        public UserService userService(){
            return  new UserServiceI(
                    userRepository,
                    companyRepository
            );
        }

    }

