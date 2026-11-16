package parking.app;

public final class CastDemo {
    private CastDemo() {
    }

    public static void main(String[] args) {
        int minutes = 130;
        int truncated = minutes / 60;
        int startedHours = (minutes + 59) / 60;
        double castAfterDivision = (double) (minutes / 60);
        double floatingPointHours = (double) minutes / 60;

        System.out.println("Integer division: 130 / 60 = " + truncated);
        System.out.println("Cast after division: (double) (130 / 60) = " + castAfterDivision);
        System.out.println("Floating-point division: (double) 130 / 60 = " + floatingPointHours);
        System.out.println("Correct started hours: (130 + 59) / 60 = " + startedHours);
    }
}