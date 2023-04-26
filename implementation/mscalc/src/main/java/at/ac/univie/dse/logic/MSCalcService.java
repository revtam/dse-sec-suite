package at.ac.univie.dse.logic;

import at.ac.univie.dse.utilities.configuration.ConfigurationPropertiesProtocol;
import at.ac.univie.dse.utilities.dependencies.HasConfigurationProperties;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MSCalcService implements WorkerListener{
    private ListenerThreadPoolExecutor executorService;
    private HashMap<String, Task> tasksDictionary;
    private long idleDurationForTermination;
    private int queueTasksLimit;
    private Timer timer;
    private MSCalcServiceCallback callback;
    private boolean isTimerScheduled = false;
    private ConfigurationPropertiesProtocol configurationProperties;

    public MSCalcService(MSCalcServiceCallback callback, HasConfigurationProperties configurationPropertiesDependency) {
        configurationProperties = configurationPropertiesDependency.getConfigurationPropertiesProtocol();
        idleDurationForTermination = configurationProperties.getIdleTerminationDelay();
        queueTasksLimit = configurationProperties.getQueueTasksLimit();
        this.timer = new Timer();
        executorService = new ListenerThreadPoolExecutor(2, this);
        this.tasksDictionary = new HashMap<>();
        this.callback = callback;
    }

    public void dispatchTask(CrackHashRequest request) throws IOException {
        if (tasksDictionary.size() >= queueTasksLimit) {
            callback.shouldDuplicateInstance();
            return;
        }
        Task<String> task = TaskFactory.generateCrackHashTask(request);
        if(!task.hasExecutionBlockSet()) {
            return;
        }
        task.setCallback(new TaskCompletionCallback<String>() {
                @Override
                public void onTaskCompleted(String result, String id) {
                    Task executedTask = tasksDictionary.get(id);
                    executedTask.setTaskState(Task.TaskState.FINISHED);
                    System.out.println("Task " + id + " finished with result: " + result);
                    tasksDictionary.remove(id);
                    callback.didCrackHash(result, request.getRequestorId(), executedTask.getTaskId());
                }
            });
        tasksDictionary.put(request.getId(), task);
        executorService.execute(task.getExecutionBlock());
    }

    public void dispatchTask(PlainTextAttackRequest request) throws IOException {
        if (tasksDictionary.size() >= queueTasksLimit) {
            callback.shouldDuplicateInstance();
            return;
        }
        Task<String> task = TaskFactory.generatePlainTextAttackTask(request);
        if (!task.hasExecutionBlockSet()) {
            return;
        }
        task.setCallback(new TaskCompletionCallback<String>() {
            @Override
            public void onTaskCompleted(String result, String id) {
                Task executedTask = tasksDictionary.get(id);
                executedTask.setTaskState(Task.TaskState.FINISHED);
                System.out.println("Task " + id + " finished with result: " + result);
                tasksDictionary.remove(id);
                callback.didCrackPlainText(result, request.getRequestorId(), executedTask.getTaskId());
            }
        });
        tasksDictionary.put(request.getId(), task);
        executorService.execute(task.getExecutionBlock());
    }

    @Override
    public void didStartExecuting() {
        synchronized (this) {
            if(isTimerScheduled) {
            timer.cancel();
            isTimerScheduled = false;
            }
        }
    }

    @Override
    public void didBecomeIdle() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isTimerScheduled = false;
                callback.shouldTerminateInstance();
            }
        };
        if(!isTimerScheduled) {
            timer = new Timer();
            timer.schedule(timerTask, idleDurationForTermination);
            isTimerScheduled = true;
        }
    }
}
