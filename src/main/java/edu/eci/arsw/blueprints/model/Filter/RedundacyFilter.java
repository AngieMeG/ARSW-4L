package edu.eci.arsw.blueprints.model.Filter;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Service
@Qualifier("RedundancyFilter")  
public class RedundacyFilter extends BlueprintsFilter{

    @Override
    public String getFilterDescription() {
        return "Removes all consecutively repeated points from the blueprint.";
    }

    @Override
    public Blueprint applyFilter(Blueprint blueprint) {
        LinkedList<Point> points = new LinkedList<Point>(blueprint.getPoints());
        LinkedList<Integer> toDelete = new LinkedList<Integer>();
        for(int i = 1; i < points.size(); i++){
            if(points.get(i).equals(points.get(i-1))) toDelete.add(i);
        }

        int deleted = 0;
        for(int i: toDelete){
            points.remove(i-deleted);
            deleted += 1;
        }

        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), points.toArray(new Point[points.size()]));
    }

    
    
}
