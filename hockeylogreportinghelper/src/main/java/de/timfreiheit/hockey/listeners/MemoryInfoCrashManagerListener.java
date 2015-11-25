package de.timfreiheit.hockey.listeners;

/**
 * dumps some system information as description
 * Created by Tim on 25.11.2015.
 */
public class MemoryInfoCrashManagerListener extends BaseCrashManagerListener {

    private final static String MB_FORMAT = "%.3f";
    private final static double MB = 1024 * 1024;

    @Override
    public String getDescription() {

        StringBuilder b = new StringBuilder(200);

        double freeMemory = Runtime.getRuntime().freeMemory() / MB;
        b.append("FreeMemory: ");
        b.append(String.format(MB_FORMAT, freeMemory));
        b.append(" MB");
        b.append("\n");

        double totalMemory = Runtime.getRuntime().totalMemory() / MB;
        b.append("TotalMemory: ");
        b.append(String.format(MB_FORMAT, totalMemory));
        b.append(" MB");
        b.append("\n");

        double maxMemoryMB = (Runtime.getRuntime().maxMemory() / MB);
        b.append("MaxMemory: ");
        b.append(String.format(MB_FORMAT, maxMemoryMB));
        b.append(" MB");
        b.append("\n");

        return b.toString();
    }
}
