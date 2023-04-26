package at.ac.univie.dse.utilities.dependencies;

public class DependenciesContainerFactory {
    private static DependenciesContainerProtocol dependenciesContainerProtocol;

    public static DependenciesContainerProtocol getDependenciesContainer() {
        if (dependenciesContainerProtocol == null) {
            dependenciesContainerProtocol = new DependenciesContainer();
        }
        return dependenciesContainerProtocol;
    }
}
