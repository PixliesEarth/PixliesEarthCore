package eu.pixliesearth.nations.entities;

/**
 * @author MickMMars
 *
 * All classes that count as Entities in the nation system (NationChunk excempt) extend this class.
 * This makes it easier for us to get a universal method to get the Id of an entity and cuts the type checks.
 */
public interface NationsEntity {

    String id();

}
