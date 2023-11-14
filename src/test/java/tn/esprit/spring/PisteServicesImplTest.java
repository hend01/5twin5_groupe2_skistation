package tn.esprit.spring;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.IPisteServices;
import tn.esprit.spring.services.ISkierServices;
import tn.esprit.spring.services.PisteServicesImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)// Use MockitoExtension for JUnit 5
class PisteServicesImplTest {

    @Mock
    IPisteRepository  pisteRepository;
    @InjectMocks
    PisteServicesImpl pisteServices;
    Piste piste1 = new Piste(1L, "Sfax", Color.BLACK,500,20, new HashSet<Skier>());
    List<Piste> listPiste = new ArrayList<Piste>(){
        {
            add(new Piste(1L, "Slope1", Color.RED,500,20, new HashSet<Skier>()));
            add(new Piste(2L, "Slope2", Color.RED,4000,20, new HashSet<Skier>()));
            add(new Piste(3L, "Slope3", Color.RED,4000,20, new HashSet<Skier>()));

        }
    };
    @Test
    void addPiste() {
        when(pisteRepository.save(piste1)).thenReturn(piste1);
        Piste result = pisteServices.addPiste(piste1);
        assertNotNull(result);
    }

    @Test
    void retrievePiste() {
        when(pisteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(piste1));
        Piste P = pisteServices.retrievePiste(1L);
        assertNotNull(P);
    }

    @Test
    void retrieveAllPistes() {
        // Simuler le comportement de pisteRepository.findAll()
        when(pisteRepository.findAll()).thenReturn(listPiste);

        // Appeler la méthode à tester
        List<Piste> pistes = pisteServices.retrieveAllPistes();

        System.out.println("Nombre de pistes retournées : " + pistes.size());

        // Vérifier que la liste des pistes n'est pas nulle
        assertNotNull(pistes);

        // Vérifier que la taille de la liste des pistes retournées correspond à la taille attendue
        assertEquals(listPiste.size(), pistes.size(), "Le nombre de pistes retournées doit correspondre au nombre attendu");
    }

    @Test
    void removePiste() {
        Long pisteId = 2L;
        pisteServices.removePiste(pisteId);
        verify(pisteRepository).deleteById(pisteId);
    }
}


