package leiphotos.domain.core;

import leiphotos.domain.facade.IPhoto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A trash library where photos are permanently deleted after being
 * in the trash for a specified duration. Cleaning verification only
 * happens when enough time has passed since the last check.
 */
public class RecentlyDeletedLibrary extends ATrashLibrary {

    private static final long DEFAULT_DELETE_AFTER_SECONDS = 15;
    private static final long DEFAULT_CHECK_INTERVAL_SECONDS = 5;

    private final Map<IPhoto, LocalDateTime> deletionTimes;
    private final long deleteAfterSeconds;
    private final long checkIntervalSeconds;
    private LocalDateTime lastCheckTime;

    /**
     * Creates a RecentlyDeletedLibrary with default timings
     * (15s to delete, 5s check interval).
     */
    public RecentlyDeletedLibrary() {
        this(DEFAULT_DELETE_AFTER_SECONDS, DEFAULT_CHECK_INTERVAL_SECONDS);
    }

    /**
     * Creates a RecentlyDeletedLibrary with custom timings.
     *
     * @param deleteAfterSeconds   seconds a photo stays before permanent deletion
     * @param checkIntervalSeconds minimum seconds between cleaning checks
     */
    public RecentlyDeletedLibrary(long deleteAfterSeconds, long checkIntervalSeconds) {
        super();
        this.deletionTimes = new HashMap<>();
        this.deleteAfterSeconds = deleteAfterSeconds;
        this.checkIntervalSeconds = checkIntervalSeconds;
        this.lastCheckTime = LocalDateTime.now();
    }

    @Override
    public boolean addPhoto(IPhoto photo) {
        boolean added = super.addPhoto(photo);
        if (added) {
            deletionTimes.put(photo, LocalDateTime.now());
        }
        return added;
    }

    @Override
    public boolean deletePhoto(IPhoto photo) {
        boolean removed = super.deletePhoto(photo);
        if (removed) {
            deletionTimes.remove(photo);
        }
        return removed;
    }

    @Override
    public boolean deleteAll() {
        boolean result = super.deleteAll();
        deletionTimes.clear();
        return result;
    }

    /**
     * Checks if enough time has passed since the last cleaning check.
     */
    @Override
    protected boolean cleaningTime() {
        long elapsed = ChronoUnit.SECONDS.between(lastCheckTime, LocalDateTime.now());
        return elapsed >= checkIntervalSeconds;
    }

    /**
     * Removes photos that have exceeded their allowed time in the trash.
     */
    @Override
    protected void clean() {
        LocalDateTime now = LocalDateTime.now();
        lastCheckTime = now;

        Iterator<IPhoto> it = getTrashedPhotos().iterator();
        while (it.hasNext()) {
            IPhoto photo = it.next();
            LocalDateTime addedTime = deletionTimes.get(photo);
            if (addedTime != null) {
                long elapsed = ChronoUnit.SECONDS.between(addedTime, now);
                if (elapsed >= deleteAfterSeconds) {
                    it.remove();
                    deletionTimes.remove(photo);
                }
            }
        }
    }
}
