package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;

    public CarService(CarRepository repository, MapsClient mapsClient,PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient=mapsClient;
        this.priceClient=priceClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        List<Car> cars = repository.findAll();
        for(Car car: cars) {
            car.setPrice(priceClient.getPrice(car.getId()));
            car.setLocation(mapsClient.getAddress(car.getLocation()));
        }

        return cars;
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car = repository.findById(id).orElse(null);
        if(car == null) throw new CarNotFoundException("Car Does not exist!");

        car.setPrice(priceClient.getPrice(id));
        car.setLocation(mapsClient.getAddress(car.getLocation()));
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null && car.getId() != 0) {
            Car oldCar = car;
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(oldCar.getDetails());
                        carToBeUpdated.setLocation(oldCar.getLocation());
                        carToBeUpdated.setCondition(oldCar.getCondition());
                        carToBeUpdated.setModifiedAt(oldCar.getModifiedAt());
                         repository.save(carToBeUpdated);
                        carToBeUpdated.setPrice(priceClient.getPrice(oldCar.getId()));
                        carToBeUpdated.setLocation(mapsClient.getAddress(carToBeUpdated.getLocation()));
                        return carToBeUpdated;

                    }).orElseThrow(CarNotFoundException::new);
        }
        car = repository.save(car);
        car.setPrice(priceClient.getPrice(car.getId()));
        car.setLocation(mapsClient.getAddress(car.getLocation()));
        return car;
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Car car = repository.findById(id).orElse(null);
        if(car == null) throw new CarNotFoundException("Car does not exist to delete!");
        repository.deleteById(id);
    }
}
