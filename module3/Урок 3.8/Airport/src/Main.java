import com.skillbox.airport.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        List<Aircraft> aircrafts = airport.getAllAircrafts();
        List<Terminal> terminals = airport.getTerminals();
        System.out.println("Общее количество самолетов: " + aircrafts.size());
        for (Terminal terminal : terminals) {
            List<Flight> flights = terminal.getFlights();
            int arrivals = 0;
            int departures = 0;
            for (Flight flight : flights) {
                if (flight.getType() == Flight.Type.ARRIVAL) arrivals++;
                else departures++;
            }
            System.out.println("Терминал: " + terminal.getName() + ", количество самолетов в резерве - " + terminal.getParkedAircrafts().size() + ", в рейсе/ожидающие рейс - " + arrivals + "/" + departures);
            System.out.println("Время прибытия самолетов в терминал '" + terminal.getName() + "': ");
            Collections.sort(flights, new Comparator<Flight>() {
                public int compare(Flight f1, Flight f2) {
                    return f1.getDate().compareTo(f2.getDate());
                }
            });
            for (Flight flight : flights) {
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                if (flight.getType() == Flight.Type.ARRIVAL) System.out.println(formatForDateNow.format(flight.getDate()) + " - " + flight.getCode());
            }
        }
    }
}
