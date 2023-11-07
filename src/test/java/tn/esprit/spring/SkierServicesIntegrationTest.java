package tn.esprit.spring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional
public class SkierServicesIntegrationTest {
    @Autowired
    private SkierServicesImpl skierService;

    @Autowired
    private ISkierRepository skierRepository;

    @BeforeEach
    public void setup() {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(LocalDate.of(1998, 5, 15));
        skier.setCity("New York");

        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        skier.setSubscription(subscription);

        skierRepository.save(skier);
    }

    @Test
    public void testAddSkier() {
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        skier.setSubscription(subscription);

        Skier result = skierService.addSkier(skier);

        assertEquals(skier, result);
    }

}
