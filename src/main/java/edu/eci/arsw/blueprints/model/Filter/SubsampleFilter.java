package edu.eci.arsw.blueprints.model.Filter;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Service
@Qualifier("SubsampleFilter")  
public class SubsampleFilter extends BlueprintsFilter{

    @Override
    public String getFilterDescription() {
        return "Removes 1 of each 2 points in the blueprint, in an interleaved way.";
    }

    @Override
    public Blueprint applyFilter(Blueprint blueprint) {
        LinkedList<Point> points = new LinkedList<Point>(blueprint.getPoints());
        int deleted = 0, initialSize = points.size();
        for(int i = 2; i < initialSize; i += 3) {
            points.remove(i-deleted);
            deleted += 1;
        }

        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), points.toArray(new Point[points.size()]));
    }

}
