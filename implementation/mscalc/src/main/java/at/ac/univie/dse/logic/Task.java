package at.ac.univie.dse.logic;

public class Task<T>  {
    enum TaskState {
        RUNNING,
        PENDING,
        FINISHED,
        ABORTED;

        private TaskState() {
        }
    }

    private String requestorId;
    private Runnable executionBlock;
    private TaskState taskState;
    private TaskCompletionCallback<T> callback;
    private String taskId;

    public Task(String requestorId, String taskId) {
        this.requestorId = requestorId;
        this.taskId = taskId;
        this.taskState = TaskState.PENDING;
    }

    public void setExecutionBlock(Runnable executionBlock) {
        this.executionBlock = executionBlock;
    }

    public void setCallback(TaskCompletionCallback<T> callback) {
        this.callback = callback;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskCompletionCallback<T> getCallback() {
        return callback;
    }

    public boolean hasExecutionBlockSet() {
        return executionBlock != null;
    }

    public Runnable getExecutionBlock() {
        return executionBlock;
    }

    public String getTaskId() {
        return taskId;
    }
}
