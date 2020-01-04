import java.util.stream.Stream;

public class Main {
    final static int PATIENTS = 30;
    final static double MIN_TEMP = 32.0;
    final static double MAX_TEMP = 40.0;
    final static double GOOD_TEMP_FOR = 36.2;
    final static double GOOD_TEMP_BEFORE = 36.9;

    public static void main(String[] args) {
        /*double middleTemperature = 0.;
        int healthyPatients = 0;
        double[] patients = new double[PATIENTS];
        for (var i = 0; i < patients.length; i++){
            double temperature = MIN_TEMP + Math.random() * (MAX_TEMP - MIN_TEMP);
            patients[i] = temperature;
            if (temperature >= GOOD_TEMP_FOR && temperature <= GOOD_TEMP_BEFORE) {
                healthyPatients++;
            }
            middleTemperature += temperature;
        }*/

        /*-------------------------------------------------------------------------------------------------------
          индексы массива 'm' (patients[0]) по порядку:
          0. общая температура; 1. средняя температура; 2. минимальная температура; 3. максимальная температура;
          4. количество пациентов, 5. количество здоровых пациентов, 6. количество больных пациентов
         -------------------------------------------------------------------------------------------------------*/
        double[][] patients = new Object() {
            double[][] init() {
                double[] m = new double[7];
                double[] p = Stream.generate(() -> {
                    double t = MIN_TEMP + Math.random() * (MAX_TEMP - MIN_TEMP);
                    m[0] += t;
                    m[4]++;
                    m[1] = m[0] / m[4];
                    m[2] = m[2] > t && m[2] != 0 ? t : m[2];
                    m[3] = m[3] < t && m[3] != 0 ? t : m[3];
                    m[5] += t >= GOOD_TEMP_FOR && t <= GOOD_TEMP_BEFORE ? 1 : 0;
                    m[6] += t < GOOD_TEMP_FOR || t > GOOD_TEMP_BEFORE ? 1 : 0;
                    return t;
                }).limit(PATIENTS).mapToDouble(x -> x).toArray();
                return new double[][]{m, p};
            }
        }.init();
        System.out.printf("Пациентов в больнице %.0f\n" +
                "Средняя температура пациентов по больнице %.1f°C\n" +
                "Количество здоровых пациентов %.0f\n" +
                "Количество больных пациентов %.0f\n",
                patients[0][4], patients[0][1], patients[0][5], patients[0][6]);

        /*middleTemperature = middleTemperature / patients.length;
        System.out.printf("Средняя температура пациентов по больнице %.1f°C\n", middleTemperature);
        System.out.printf("Количество здоровых пациентов %d", healthyPatients);*/
    }
}
