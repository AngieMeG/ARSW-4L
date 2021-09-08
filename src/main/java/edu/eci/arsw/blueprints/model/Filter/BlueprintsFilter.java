package edu.eci.arsw.blueprints.model.Filter;

import java.util.HashSet;
import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

public abstract class BlueprintsFilter {
    
    /**
     * @return A detailed description of the filter to be applied
     */
    public abstract String getFilterDescription();

    /**
     * Applies the filter to a Set of blueprints
     * @return Filtered set of blueprints
     */
    public Set<Blueprint> applyFilter(Set<Blueprint> blueprints){
        HashSet<Blueprint> filteredBlueprints = new HashSet<Blueprint>();
        for(Blueprint blueprint: blueprints){
            filteredBlueprints.add(applyFilter(blueprint));
        }
        return filteredBlueprints;

    }

    /**
     * Applies the filter to blueprint
     * @return Filtered set of blueprints
     */
    public abstract Blueprint applyFilter(Blueprint blueprint);

}
