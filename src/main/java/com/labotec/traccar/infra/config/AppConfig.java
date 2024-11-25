    package com.labotec.traccar.infra.config;

    import com.labotec.traccar.app.implementation.*;
    import com.labotec.traccar.app.mapper.model.*;
    import com.labotec.traccar.app.ports.input.email.GoogleEmail;
    import com.labotec.traccar.app.ports.input.repository.*;
    import com.labotec.traccar.app.ports.out.*;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import lombok.AllArgsConstructor;
    import org.springframework.transaction.annotation.Transactional;

    @Configuration
    @AllArgsConstructor
    @Transactional
    public class AppConfig {

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
        private final OverviewPolylineRepository overviewPolylineRepository;
        private final DriverScheduleRepository driverScheduleRepository;
        @Bean(name = "busStopService")
        public BusStopService busStop() {
            return new BusStopImpl(busStopRepository,companyRepository,busStopModelMapper,userRepository);
        }

        @Bean(name = "companyService")
        public CompanyService company() {
            return new CompanyImpl(companyRepository,companyModelMapper);
        }

        @Bean(name = "driverService")
        public DriverService driver() {
            return new DriverImpl(
                    driverRepository,
                    companyRepository,
                    driverModelMapper,
                    userRepository
            );
        }

        @Bean(name = "locationService")
        public LocationService location() {
            return new LocationImpl(
                    busStopRepository,
                    locationRepository,
                    companyRepository,
                    locationModelMapper,
                    userRepository
            );
        }

        @Bean(name = "routeService")
        public RouteService route() {
            return new RouteServiceImpl(
                    routeRepository,
                    companyRepository,
                    busStopRepository,
                    routeBusStopRepository,
                    routeModelMapper,
                    overviewPolylineRepository,
                    vehicleRepository,
                    scheduleRepository,
                    userRepository
            );
        }

        @Bean(name = "scheduleService")
        public ScheduleService schedule() {
            return new ScheduleImpl(
                    scheduleRepository,
                    vehicleRepository,
                    driverRepository,
                    locationRepository,
                    routeRepository,
                    companyRepository,
                    geofenceCircularRepository,
                    scheduleModelMapper,
                    driverScheduleRepository,
                    userRepository
            );
        }

        @Bean(name = "vehicleService")
        public VehicleService vehicle() {
            return new VehicleImpl(
                vehicleRepository,
                vehicleModelMapper,
                companyRepository,
                vehicleTypeRepository,
                    userRepository
            );}


        @Bean(name = "geofenceService")
        public GeofencePoligonalService geofencePoligonalService(){
            return new CircularGeofenceServiceImpl(
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
            return  new UserServiceImpl(
                    userRepository,
                    companyRepository
            );
        }

    }

