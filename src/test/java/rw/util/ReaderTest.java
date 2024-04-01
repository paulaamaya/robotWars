package rw.util;

import org.junit.jupiter.api.Test;
import rw.battle.Battle;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.battle.Wall;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test
    public void loadBattleTest() {
        File file = new File("battle_test.txt");
        Battle battle = Reader.loadBattle(file);
        assertInstanceOf(PredaCon.class, battle.getEntity(0,0));
        assertInstanceOf(Wall.class, battle.getEntity(2,0));
        assertInstanceOf(Maximal.class, battle.getEntity(2,2));
    }
}