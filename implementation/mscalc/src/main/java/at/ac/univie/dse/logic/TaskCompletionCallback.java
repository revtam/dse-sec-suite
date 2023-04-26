package at.ac.univie.dse.logic;

public interface TaskCompletionCallback<T> {
    void onTaskCompleted(T result, String id);
}
