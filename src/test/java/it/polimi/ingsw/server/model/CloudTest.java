package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    Cloud cloud2, cloud3, cloud4;

    @BeforeEach
    void SetUp(){
        cloud2 = new Cloud(2, 2);
        cloud3 = new Cloud(3, 0);
        cloud4 = new Cloud(4, 1);
        }

    @Test
    public void testCloud(){
        //Test getId
        assertEquals(2, cloud2.getId());
        assertEquals(0, cloud3.getId());
        assertEquals(1, cloud4.getId());
    }
}