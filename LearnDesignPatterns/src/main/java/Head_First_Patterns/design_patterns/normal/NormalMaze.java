package Head_First_Patterns.design_patterns.normal;

/**
 * @author halfOfGame
 * @create 2020-05-24,17:45
 */
public class NormalMaze {

    enum Direction {
        North, South, East, West
    }

    abstract class MapSite {
        abstract void Enter();
    }

    class Room extends MapSite {
        @Override
        void Enter() {

        }
    }


}
