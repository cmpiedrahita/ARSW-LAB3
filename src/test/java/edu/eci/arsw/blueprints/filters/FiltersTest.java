package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {

    @Test
    void identityFilterShouldReturnSameBlueprint() {
        IdentityFilter filter = new IdentityFilter();
        Blueprint bp = new Blueprint("john", "house", List.of(new Point(0, 0), new Point(1, 1)));
        
        Blueprint result = filter.apply(bp);
        
        assertEquals(bp, result);
        assertEquals(2, result.getPoints().size());
    }

    @Test
    void redundancyFilterShouldRemoveDuplicates() {
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint bp = new Blueprint("john", "house", 
            List.of(new Point(0, 0), new Point(0, 0), new Point(1, 1), new Point(1, 1), new Point(2, 2)));
        
        Blueprint result = filter.apply(bp);
        
        assertEquals(3, result.getPoints().size());
        assertEquals(new Point(0, 0), result.getPoints().get(0));
        assertEquals(new Point(1, 1), result.getPoints().get(1));
        assertEquals(new Point(2, 2), result.getPoints().get(2));
    }

    @Test
    void undersamplingFilterShouldKeepEvenIndices() {
        UndersamplingFilter filter = new UndersamplingFilter();
        Blueprint bp = new Blueprint("john", "house", 
            List.of(new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4)));
        
        Blueprint result = filter.apply(bp);
        
        assertEquals(3, result.getPoints().size());
        assertEquals(new Point(0, 0), result.getPoints().get(0));
        assertEquals(new Point(2, 2), result.getPoints().get(1));
        assertEquals(new Point(4, 4), result.getPoints().get(2));
    }

    @Test
    void undersamplingFilterShouldNotModifySmallBlueprints() {
        UndersamplingFilter filter = new UndersamplingFilter();
        Blueprint bp = new Blueprint("john", "house", List.of(new Point(0, 0), new Point(1, 1)));
        
        Blueprint result = filter.apply(bp);
        
        assertEquals(2, result.getPoints().size());
    }
}
