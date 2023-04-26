package at.ac.univie.dse.logic;

public interface WorkerListener {
    void didStartExecuting();
    void didBecomeIdle();
}
