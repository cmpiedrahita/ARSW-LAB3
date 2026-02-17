package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.BlueprintsFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlueprintsServicesTest {

    @Mock
    private BlueprintPersistence persistence;

    @Mock
    private BlueprintsFilter filter;

    private BlueprintsServices services;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        services = new BlueprintsServices(persistence, filter);
    }

    @Test
    void shouldAddNewBlueprint() throws BlueprintPersistenceException {
        Blueprint bp = new Blueprint("john", "house", List.of(new Point(0, 0)));
        
        services.addNewBlueprint(bp);
        
        verify(persistence, times(1)).saveBlueprint(bp);
    }

    @Test
    void shouldGetAllBlueprints() {
        Set<Blueprint> expected = Set.of(
            new Blueprint("john", "house", List.of(new Point(0, 0)))
        );
        when(persistence.getAllBlueprints()).thenReturn(expected);
        
        Set<Blueprint> result = services.getAllBlueprints();
        
        assertEquals(expected, result);
        verify(persistence, times(1)).getAllBlueprints();
    }

    @Test
    void shouldGetBlueprintsByAuthor() throws BlueprintNotFoundException {
        String author = "john";
        Set<Blueprint> expected = Set.of(
            new Blueprint(author, "house", List.of(new Point(0, 0)))
        );
        when(persistence.getBlueprintsByAuthor(author)).thenReturn(expected);
        
        Set<Blueprint> result = services.getBlueprintsByAuthor(author);
        
        assertEquals(expected, result);
        verify(persistence, times(1)).getBlueprintsByAuthor(author);
    }

    @Test
    void shouldGetBlueprintWithFilter() throws BlueprintNotFoundException {
        String author = "john";
        String name = "house";
        Blueprint original = new Blueprint(author, name, List.of(new Point(0, 0)));
        Blueprint filtered = new Blueprint(author, name, List.of(new Point(0, 0)));
        
        when(persistence.getBlueprint(author, name)).thenReturn(original);
        when(filter.apply(original)).thenReturn(filtered);
        
        Blueprint result = services.getBlueprint(author, name);
        
        assertEquals(filtered, result);
        verify(persistence, times(1)).getBlueprint(author, name);
        verify(filter, times(1)).apply(original);
    }

    @Test
    void shouldAddPoint() throws BlueprintNotFoundException {
        String author = "john";
        String name = "house";
        int x = 5;
        int y = 10;
        
        services.addPoint(author, name, x, y);
        
        verify(persistence, times(1)).addPoint(author, name, x, y);
    }
}
