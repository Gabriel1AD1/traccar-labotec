    package com.labotec.traccar.infra.config;

    import com.labotec.traccar.app.implemenatition.*;
    import com.labotec.traccar.app.mapper.*;
    import com.labotec.traccar.app.usecase.ports.input.*;
    import com.labotec.traccar.app.usecase.ports.out.*;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import lombok.AllArgsConstructor;

    @Configuration
    @AllArgsConstructor
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
        @Bean(name = "busStopService")
        public BusStopService busStop() {
            return new BusStopImpl(busStopRepository,companyRepository,busStopModelMapper);
        }

        @Bean(name = "companyService")
        public CompanyService company() {
            return new CompanyImpl(companyRepository,companyModelMapper);
        }

        @Bean(name = "driverService")
        public DriverService driver() {
            return new DriverImpl(driverRepository,companyRepository,driverModelMapper);
        }

        @Bean(name = "locationService")
        public LocationService location() {
            return new LocationImpl(locationRepository,companyRepository,locationModelMapper);
        }

        @Bean(name = "routeService")
        public RouteService route() {
            return new RouteImpl(
                    routeRepository,
                    companyRepository,
                    busStopRepository,
                    routeBusStopRepository,
                    routeModelMapper);
        }

        @Bean(name = "scheduleService")
        public ScheduleService schedule() { return new ScheduleImpl(
                scheduleRepository,
                vehicleRepository,
                driverRepository,
                locationRepository,
                routeRepository,
                companyRepository,
                scheduleModelMapper); }

        @Bean(name = "vehicleService")
        public VehicleService vehicle() {return new VehicleImpl(
                vehicleRepository,
                vehicleModelMapper,
                companyRepository,
                vehicleTypeRepository);}

        @Bean(name = "routeBusStopService")
        public RouteBusStopService routeBusStopService(){
            return new RouteBusStopImpl(
                    busStopRepository,
                    routeRepository,
                    routeBusStopRepository,
                    routeBusStopModelMapper);
        }
    }

