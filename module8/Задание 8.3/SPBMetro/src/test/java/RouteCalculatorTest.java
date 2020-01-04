import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List<Station> route; //маршрут
    RouteCalculator routeCalculator; //класс для поиска маршрута и расчета времени поездки
    StationIndex stationIndex; //список станции

    @Override
    protected void setUp() {
        //создаем маршрут для проверки расчета времени поездки
        route = new ArrayList<>();
        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        route.add(new Station("Площадь Маркса", line1));
        route.add(new Station("Студенческая", line1));
        route.add(new Station("Речной вокзал", line1));
        route.add(new Station("Березовая роща", line2));
        route.add(new Station("Площадь Маркса", line2));

        stationIndex = new StationIndex();
        //создаем 3 ветки метро
        line1 = new Line(1, "Первая");
        line2 = new Line(2, "Вторая");
        Line line3 = new Line(3, "Третья");
        //добавляем ветки в обобщенный класс
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        //добавляем 6 станции
        Station from = new Station("Чернышевская", line1);
        Station sl1 = new Station("Площадь Восстания", line1);
        Station sl2 = new Station("Маяковская", line3);
        Station sl3 = new Station("Гостиный двор", line3);
        Station sl4 = new Station("Невский проспект", line2);
        Station to = new Station("Горьковская", line2);
        //добавляем станции на ветки метро
        line1.addStation(from);
        line1.addStation(sl1);
        line3.addStation(sl2);
        line3.addStation(sl3);
        line2.addStation(sl4);
        line2.addStation(to);
        //добавляем станции в обобщенный класс
        stationIndex.addStation(from);
        stationIndex.addStation(sl1);
        stationIndex.addStation(sl2);
        stationIndex.addStation(sl3);
        stationIndex.addStation(sl4);
        stationIndex.addStation(to);
        //создаем 2 соединения веток на определенных станциях
        List<Station> cs1 = new ArrayList<>();
        cs1.add(stationIndex.getStation(sl1.getName(), sl1.getLine().getNumber()));
        cs1.add(stationIndex.getStation(sl2.getName(), sl2.getLine().getNumber()));
        List<Station> cs2 = new ArrayList<>();
        cs2.add(stationIndex.getStation(sl3.getName(), sl3.getLine().getNumber()));
        cs2.add(stationIndex.getStation(sl4.getName(), sl4.getLine().getNumber()));
        //добавляем соединнения в обобщенный клас
        stationIndex.addConnection(cs1);
        stationIndex.addConnection(cs2);
        //создаем экземпляр класса расчета маршрута с нашим собранным обобщеным классом
        routeCalculator = new RouteCalculator(stationIndex);
    }

    //получаем приватный статичный метод класса
    @SuppressWarnings("all")
    private Method getMethod(Class cls, String method){
        for (Method func:cls.getDeclaredMethods()){
            if (func.getName().equals(method)){
                func.setAccessible(true);
                return func;
            }
        }
        return null;
    }

    /**
     * тестируем метод поиска короткого маршрута
     */
    public void testGetShortestRoute() throws InvocationTargetException, IllegalAccessException {
        List<Station> route = routeCalculator.getShortestRoute(stationIndex.getStation("Чернышевская"), stationIndex.getStation("Горьковская"));
        //выводим в консоль полученный маршрут
        Method printRoute = getMethod(Main.class, "printRoute");
        if (printRoute != null){
            printRoute.invoke(null, route);
        }
    }

    /**
     * тестируем метод расчета времени на маршрут
     */
    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 11.0;
        assertEquals(expected, actual);
    }

    @Override
    protected void tearDown() {

    }
}
