package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.repository.RestaurantRepository;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && get(restaurant.id()) == null) {
            return null;
        }
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<RestaurantTo> getAll() {
        return convertFromObjectsToRestaurantTos(crudRestaurantRepository.getAll());
    }

    @Override
    public List<RestaurantTo> getAllForToday() {

        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        System.out.println(startDateTime);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return convertFromObjectsToRestaurantTos(crudRestaurantRepository.getAllForToday(startDateTime, endDateTime));
    }


    public List<RestaurantTo> convertFromObjectsToRestaurantTos(List<Object[]> objects) {
        return objects.stream()
                .map(row -> new RestaurantTo(
                        (Integer) row[0],
                        (String) row[1],
                        (String) row[2],
                        ((Long) row[3]).intValue(),
                        (LocalDateTime) row[4]))
                .collect(Collectors.toList());
    }

    public int vote(int id, int userId) {
        LocalDate date = LocalDate.now();
        crudRestaurantRepository.deleteVote(userId, date);
        return crudRestaurantRepository.vote(id, userId);
    }
}
