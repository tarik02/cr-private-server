package royaleserver.network.protocol;

public interface FactoryTarget<Target extends FactoryTarget> {
	Target create();
}
