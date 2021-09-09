/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
@Qualifier("InMemoryBlueprint")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();
    private static ConcurrentHashMap<Tuple<String,String>, Boolean> inUse = new ConcurrentHashMap<Tuple<String,String>, Boolean>(); 
    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts;
        Blueprint bp;

        pts=new Point[]{new Point(140, 140),new Point(115, 115),new Point(215, 115),new Point(100, 120),new Point(50, 50)};
        bp=new Blueprint("Jose", "Escuela",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        pts=new Point[]{new Point(140, 140),new Point(115, 100),new Point(175, 125)};
        bp=new Blueprint("Jose", "Casa Ibai",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        pts=new Point[]{new Point(140, 140),new Point(115, 115),new Point(112, 140),new Point(90, 90),new Point(10, 10)};
        bp=new Blueprint("Angie", "Casa Mateo",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getAllBlueprints(){
        return new HashSet<>(blueprints.values());
    }

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> blueprintsByAuthor = new HashSet<>();
        boolean authorExists = false;
        for(Blueprint blueprint : getAllBlueprints()){
            if(blueprint.getAuthor().equals(author)){
                authorExists = true;
                blueprintsByAuthor.add(blueprint);
            }
        }
        if (!authorExists){
            throw new BlueprintNotFoundException("The given author doesn't exist");
        }
        return blueprintsByAuthor;
    }

    @Override
    public void editBlueprint(String author, String bpname, Blueprint bp) throws BlueprintNotFoundException {
        Tuple<String, String> key = new Tuple<>(author, bpname);
        if (!blueprints.containsKey(key)) throw new BlueprintNotFoundException("The given bluprint doesn't exist");

        synchronized(blueprints.get(key)){
            try {
                while (inUse.contains(key) && inUse.get(key)) blueprints.get(key).wait();
                if(!inUse.contains(key)) inUse.put(key, true);
                else inUse.replace(key, true);
                blueprints.replace(key, bp);
                inUse.replace(key, false);
                blueprints.get(key).notifyAll();
            } catch (Exception e) {
                //TODO: handle exception
            }           
        }
        
    }
}
