package at.ac.univie.dse.logic;

public class TaskFactory {
    public static Task<String> generateCrackHashTask(CrackHashRequest request) {
        Task<String> task = new Task<String>(request.getRequestorId(), request.getId());
        task.setExecutionBlock(new Runnable() {
            @Override
            public void run() {
                task.setTaskState(Task.TaskState.RUNNING);
                task.setTaskId(request.getId());
                String password = CrackingUtilities.crackHash(request.getHash());
                task.getCallback().onTaskCompleted(password, request.getId());
            }
        });
        return task;
    }

    public static Task<String> generatePlainTextAttackTask(PlainTextAttackRequest request) {
        Task<String> task = new Task<String>(request.getRequestorId(), request.getId());
        task.setExecutionBlock(new Runnable() {
            @Override
            public void run() {
                task.setTaskState(Task.TaskState.RUNNING);
                task.setTaskId(request.getId());
                String password = CrackingUtilities.crackPasswordUsingCeaserCipher(request.getClearText(), request.getCipherText());
                task.getCallback().onTaskCompleted(password, request.getId());
            }
        });
        return task;
    }
}
